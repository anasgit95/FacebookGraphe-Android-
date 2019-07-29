    package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

    public class MainActivity extends AppCompatActivity {
private LoginButton loginButton;
private CircleImageView circleImageView ;
private TextView  txtName ,txtEmail ;
private CallbackManager callbackManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
         txtName = findViewById(R.id.profile_name);
         txtEmail =findViewById(R.id.profile_email);
         circleImageView = findViewById(R.id.profile_pic);


         callbackManager= CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
         loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
             @Override
             public void onSuccess(LoginResult loginResult) {


             }

             @Override
             public void onCancel() {

             }

             @Override
             public void onError(FacebookException error) {

             }
         });


     }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
           callbackManager.onActivityResult(requestCode,resultCode,data);
            super.onActivityResult(requestCode, resultCode, data);
        }
        AccessTokenTracker tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if(currentAccessToken==null)
                {
                    txtName.setText("");
                    txtEmail.setText("");
                    circleImageView.setImageResource(0);
                    Toast.makeText(MainActivity.this,"User Logged out",Toast.LENGTH_LONG).show();
                }
                else
                    loadUserProfile(currentAccessToken);
            }
        };

        @Override
        protected void onStart() {
            super.onStart();

        }

        private void loadUserProfile(AccessToken newAccesToken )
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccesToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name = object.getString("first_name");
                     String last_name =object.getString("last_name") ;
                     String email = object.getString("email");
                     String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";

                    txtEmail.setText(email);
                    txtName.setText(first_name +" "+last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(MainActivity.this).load(image_url).into(circleImageView);
                    SOC_DB soc_db = new SOC_DB(this);
                    soc_db.execute(email,first_name,last_name);










                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        Bundle parameters = new Bundle() ;
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();


    }
    }
