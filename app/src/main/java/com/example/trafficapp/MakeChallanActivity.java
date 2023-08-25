package com.example.trafficapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MakeChallanActivity extends AppCompatActivity {
    private static final String HOST = "smtp.gmail.com"; // Replace with your email host
    private static final int PORT = 587; // Replace with your email port

    private static final String EMAIL_SENDER = "trafficsquad76@gmail.com"; // Replace with your email address
    private static final String EMAIL_PASSWORD = "ldhnqgzhrigeynsb"; // Replace with your email password


    private DatabaseReference databaseRef;
    FirebaseDatabase db;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private Bitmap capturedImage;
    EditText firstname, lastname, phoneNo, plateNo, rcNo, dlNo, typeVehicle, violation, email, fine;
    Button submit;

    private ActivityResultLauncher<Intent> imageCaptureLauncher;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_challan);

        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        phoneNo = findViewById(R.id.phone);
        plateNo = findViewById(R.id.plateNo);
        rcNo = findViewById(R.id.rcNo);
        dlNo = findViewById(R.id.dlNo);
        typeVehicle = findViewById(R.id.typeVehicle);
        violation = findViewById(R.id.violation);
        email = findViewById(R.id.email);
        fine = findViewById(R.id.fine);
//        pemail=findViewById(R.id.pemail);
        submit = findViewById(R.id.submit);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted, proceed with capturing image

            imageCaptureLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            capturedImage = (Bitmap) extras.get("data");
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
            );

            Button captureButton = findViewById(R.id.capture_button);
            imageView = findViewById(R.id.image_view);

            captureButton.setOnClickListener(v -> {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageCaptureLauncher.launch(intent);
            });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseDatabase.getInstance();
                databaseRef =db.getReference("complaint");

                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String phone = phoneNo.getText().toString();
                String plate = plateNo.getText().toString();
                String rc = rcNo.getText().toString();
                String dl = dlNo.getText().toString();
                String violate = violation.getText().toString();
                String typev = typeVehicle.getText().toString();
                String em = email.getText().toString();
                String fin = fine.getText().toString();
//                String pem=pemail.getText().toString();
                Bundle bundle = getIntent().getExtras();
                String pem = bundle.getString("com.example.trafficapp.home.email");


                ByteArrayOutputStream baops = new ByteArrayOutputStream();
                capturedImage.compress(Bitmap.CompressFormat.PNG, 100, baops);
                byte[] imageData = baops.toByteArray();
                String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
                Date currentDate = new Date();


                complaint ob = new complaint(fname, lname, phone, plate, rc, dl, typev, violate, base64Image, em, pem, currentDate, fin);



                databaseRef.child(fname).setValue(ob)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                // Format the date as a string
                                String dateString = dateFormat.format(currentDate);
                                String subject = "Your Challan for " + violate;
                                String msg = "Dear " + fname + " " + lname + " ,\n" +
                                        "\n" +
                                        "I hope this email finds you well. I am writing to bring to your attention an unpaid traffic challan (ticket) that was issued to you on " + dateString + " for the violation of [Specify Violation]. As of today, the payment for the challan remains outstanding.\n" +
                                        "\n" +
                                        "--------------Challan Details--------------:\n" +
                                        "\n" +
                                        "Officer email account: " + pem + "\n" +
                                        "Date: " + dateString + "\n" +
                                        "Type Of Vehicle: " + typev + "\n" +
                                        "Amount: " + fin;


                                sendEmail("rathannayak300@gmail.com", subject, msg);
                                // Data successfully saved to the database
                                // Perform any desired further actions
                                Toast.makeText(MakeChallanActivity.this, "Successfully Created Challan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MakeChallanActivity.this, HomeActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while saving the data
                                // Handle the failure case
                                Toast.makeText(MakeChallanActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public static void sendEmail(String recipientEmail, String subject, String msg) {
        // Set up properties for the mail server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);



        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_SENDER, EMAIL_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            // Email sent successfully
        } catch (MessagingException e) {
            // Email sending failed
        }
    }
}