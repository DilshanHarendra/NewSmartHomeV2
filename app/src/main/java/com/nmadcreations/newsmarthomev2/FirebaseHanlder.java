package com.nmadcreations.newsmarthomev2;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHanlder {

    public FirebaseHanlder(){

    }

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    public void addUser(String uid,String ufname, String ulname, String ugivenname,String uemail) {
        firebaseDatabase.getReference().child("Users").child(uid).child("UId").setValue(uid);
        firebaseDatabase.getReference().child("Users").child(uid).child("UFname").setValue(ufname);
        firebaseDatabase.getReference().child("Users").child(uid).child("ULname").setValue(ulname);
        firebaseDatabase.getReference().child("Users").child(uid).child("UGivenName").setValue(ugivenname);
        firebaseDatabase.getReference().child("Users").child(uid).child("UEmail").setValue(uemail);
    }

    public void addToHome(String uid,String shName,String uPosition){
            firebaseDatabase.getReference().child("SmartHome").child(shName).child("User").child(uPosition).setValue(uid);
        }

    public void addHome(String uid){
        firebaseDatabase.getReference().child("SmartHome").setValue(uid);

    }
    public void addDevice(String uid,String name,String type){
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceID").setValue(uid);
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceName").setValue(name);
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceType").setValue(type);

    }

}
