package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {
    private CircleImageView circleImageView ;
    private TextView txtName ,txtEmail ;
    private CallbackManager callbackManager ;
    private Button btn1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtName = findViewById(R.id.profile_name);
        txtEmail =findViewById(R.id.profile_email);
        circleImageView = findViewById(R.id.profile_pic);
        btn1 = findViewById(R.id.btn1);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String first_name = intent.getStringExtra("nom");
        final  String last_name = intent.getStringExtra("prenom");
        final String image_url = intent.getStringExtra("image_url") ;
        txtEmail.setText(email);
        txtName.setText(first_name +" "+last_name);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(intent);


            }
        });
        Glide.with(Main2Activity.this).load(image_url).into(circleImageView);
        if(email.equals("")) {
            Intent intent1 = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent1);
        }


    }
}
