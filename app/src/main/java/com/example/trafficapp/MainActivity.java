package com.example.trafficapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, passwd;
    Button register, signin;

    public static final String EXTRA_MAIL="com.example.trafficapp.extra.email";
    public static final String EXTRA_PASSWD="com.example.trafficapp.extra.password";


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        passwd = findViewById(R.id.passwd);
        signin = findViewById(R.id.signin);
        auth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String pass = passwd.getText().toString();

                if (TextUtils.isEmpty(em) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "complete the fields", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Signin Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                Bundle b=new Bundle();
                                b.putString(EXTRA_MAIL,em);
                                b.putString(EXTRA_PASSWD,pass);
                                intent.putExtras(b);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Signin Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



}