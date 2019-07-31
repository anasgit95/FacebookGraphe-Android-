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


import org.json.JSONException;
import org.json.JSONObject;


import java.util.Arrays;

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
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("email","");
                    intent.putExtra("nom","");
                    intent.putExtra("prenom","");
                    intent.putExtra("image_url","");
                    startActivity(intent);

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




                    SOC_DB soc_db = new SOC_DB(this);
                    soc_db.execute(email,first_name,last_name);
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("nom",first_name);
                    intent.putExtra("prenom",last_name);
                    intent.putExtra("image_url",image_url);
                    startActivity(intent);









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
