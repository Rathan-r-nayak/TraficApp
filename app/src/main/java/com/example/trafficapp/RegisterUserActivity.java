package com.example.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {
    private DatabaseReference databaseRef;
    FirebaseDatabase db;

    EditText email,passwd,name,city,phone,district,department,stationid;
    Button register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        email=findViewById(R.id.email);
        passwd=findViewById(R.id.passwd);
        passwd=findViewById(R.id.passwd);
        name=findViewById(R.id.name);
        city=findViewById(R.id.city);
        phone=findViewById(R.id.phone);
        district=findViewById(R.id.district);
        department=findViewById(R.id.department);
        stationid=findViewById(R.id.stationid);
        register=findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=email.getText().toString();
                String pass=passwd.getText().toString();
                String  nam=name.getText().toString();
                String phn=phone.getText().toString();
                String cit=city.getText().toString();
                String dept=department.getText().toString();
                String dist=district.getText().toString();
                String sid=stationid.getText().toString();
                db = FirebaseDatabase.getInstance();
                databaseRef=db.getReference("police");

                if(TextUtils.isEmpty(em) && TextUtils.isEmpty(pass))
                {
                    Toast.makeText(RegisterUserActivity.this, "Fill the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                police p1= new police(em,nam,cit,phn,dept,dist,sid);

                                databaseRef.child(nam).setValue(p1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Data successfully saved to the database
                                                // Perform any desired further actions
                                                Toast.makeText(RegisterUserActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterUserActivity.this,HomeActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Error occurred while saving the data
                                                // Handle the failure case
                                                Toast.makeText(RegisterUserActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(RegisterUserActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });



    }
}