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
    private static final String EMAIL_HOST = "smtp.gmail.com"; // Replace with your email host
    private static final int EMAIL_PORT = 587; // Replace with your email port

    private static final String EMAIL_USERNAME = ""; // Replace with your email address
    private static final String EMAIL_PASSWORD = ""; // Replace with your email password


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
                databaseRef = FirebaseDatabase.getInstance().getReference();

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


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                capturedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageData = baos.toByteArray();
                String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);


//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference imagesRef = database.getReference("complaint/user");
//
//                String imageKey = imagesRef.push().getKey(); // Generate a unique key for the image
//                imagesRef.child(imageKey).setValue(base64Image)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // Image stored successfully in the database
//                                Toast.makeText(MakeChallanActivity.this, "success", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Failed to store the image in the database
//                                Toast.makeText(MakeChallanActivity.this, "exception", Toast.LENGTH_SHORT).show();
//                            }
//                        });


                Date currentDate = new Date();


                complaint ob = new complaint(fname, lname, phone, plate, rc, dl, typev, violate, base64Image, em, pem, currentDate, fin);
//                ob.uploadImage(capturedImage);
//                userData.setName("John Doe");
//                userData.setAge(30);

//                databaseRef.child("images").child("imageKey").setValue(base64Image);


                databaseRef.child("complaint").child("user").setValue(ob)
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

    public static void sendEmail(String recipientEmail, String subject, String message) {
        // Set up properties for the mail server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        properties.put("mail.smtp.port", "587"); // Port for TLS on STARTTLS

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage mimeMessage = new MimeMessage(session);

            // Set the sender and recipient addresses
            mimeMessage.setFrom(new InternetAddress(EMAIL_USERNAME));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set the subject and actual message
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            // Send the email
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}