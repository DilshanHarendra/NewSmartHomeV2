package com.nmadcreations.newsmarthomev2;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHanlder {

    public FirebaseHanlder(){

    }

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    public void addUser(String uid,String ufname, String ulname, String ugivenname,String uemail){
        firebaseDatabase.getReference().child("users").child(uid).child("UID").setValue(uid);
        firebaseDatabase.getReference().child("users").child(uid).child("UFname").setValue(ufname);
        firebaseDatabase.getReference().child("users").child(uid).child("ULname").setValue(ulname);
        firebaseDatabase.getReference().child("users").child(uid).child("UGivenName").setValue(ugivenname);
        firebaseDatabase.getReference().child("users").child(uid).child("UEmail").setValue(uemail);
    }

}
