package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    TextView username;
    Button addUser,makeChallan;

    public static final String EXTRA_MAIL="com.example.trafficapp.home.email";
    public static final String EXTRA_PASSWD="com.example.trafficapp.home.password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle=getIntent().getExtras();

        username=findViewById(R.id.username);
        addUser=findViewById(R.id.addUser);
        makeChallan=findViewById(R.id.makeChallan);

        String uname="Login As "+bundle.getString("com.example.trafficapp.extra.email");
        username.setText(uname);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,RegisterUserActivity.class));

            }
        });


        makeChallan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,MakeChallanActivity.class);
                Bundle b=new Bundle();
                b.putString(EXTRA_MAIL,bundle.getString(EXTRA_MAIL));
                b.putString(EXTRA_PASSWD,bundle.getString(EXTRA_PASSWD));
                intent.putExtras(b);
                startActivity(intent);
            }
        });



    }
}