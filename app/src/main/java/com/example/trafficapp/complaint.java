package com.example.trafficapp;

import java.util.Date;


public class complaint {
    String fname,lname,phone,plateNo,rcNo,dlNo,typeVehicle,violation,Image,email,policeEmail,fine;
    Date date;

    public complaint(String fname, String lname, String phone, String plateNo, String rcNo, String dlNo, String typeVehicle, String violation, String image, String email, String policeEmail, Date date,String fine) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.plateNo = plateNo;
        this.rcNo = rcNo;
        this.dlNo = dlNo;
        this.typeVehicle = typeVehicle;
        this.violation = violation;
        Image = image;
        this.email = email;
        this.policeEmail = policeEmail;
        this.date = date;
        this.fine=fine;
    }


//    private DatabaseReference databaseRef;


    public complaint() {

    }


    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getRcNo() {
        return rcNo;
    }

    public String getDlNo() {
        return dlNo;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public void setRcNo(String rcNo) {
        this.rcNo = rcNo;
    }

    public void setDlNo(String dlNo) {
        this.dlNo = dlNo;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getViolation() {
        return violation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setPoliceEmail(String policeEmail) {
        this.policeEmail = policeEmail;
    }

    public String getPoliceEmail() {
        return policeEmail;
    }
//    public void storeImage(Bitmap imageBitmap, String imageKey) {
//        // Convert the Bitmap to a byte array
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] imageData = baos.toByteArray();
//
//        // Encode the byte array as a base64-encoded string
//        String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
//
//        // Store the base64-encoded image string in the database
//        databaseRef.child("images").child(imageKey).setValue(base64Image);
//    }
//
//



//    private StorageReference storageRef;
//
//    public void FirebaseStorageHelper() {
//        // Get the root reference to the Firebase Storage
//        storageRef = FirebaseStorage.getInstance().getReference();
//    }
//
//    public void uploadImage(Bitmap imageBitmap, String filename, OnImageUploadListener listener) {
//        // Convert the Bitmap to a byte array
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageData = baos.toByteArray();
//
//        // Create a reference to the image file in Firebase Storage
//        StorageReference imageRef = storageRef.child("images").child(filename);
//
//        // Upload the image byte array to Firebase Storage
//        UploadTask uploadTask = imageRef.putBytes(imageData);
//
//        // Set the listener to monitor the upload progress and completion
//        uploadTask.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                // Image upload successful
//                // Retrieve the download URL of the uploaded image
//                imageRef.getDownloadUrl().addOnCompleteListener(urlTask -> {
//                    if (urlTask.isSuccessful()) {
//                        String imageUrl = urlTask.getResult().toString();
//                        // Invoke the listener callback with the image URL
//                        listener.onImageUploadSuccess(imageUrl);
//                    } else {
//                        // Error retrieving the download URL
//                        listener.onImageUploadFailure(urlTask.getException());
//                    }
//                });
//            } else {
//                // Image upload failed
//                listener.onImageUploadFailure(task.getException());
//            }
//        });
//    }
//
//    public interface OnImageUploadListener {
//        void onImageUploadSuccess(String imageUrl);
//        void onImageUploadFailure(Exception e);
//    }
}
