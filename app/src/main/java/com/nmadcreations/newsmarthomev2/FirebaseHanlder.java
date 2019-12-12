package com.nmadcreations.newsmarthomev2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void addHome(String homeid){
        firebaseDatabase.getReference().child("SmartHome").setValue(homeid);

    }
    public void addDevice(String uid,String name,String type){
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceID").setValue(uid);
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceName").setValue(name);
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceType").setValue(type);

    }

public void ReadData() {
    // Read from the database
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = rootRef.child("General");
    //  DatabaseReference getData=usersRef.child("Dilshan");// methanata set karnna ona id eka
    // String id="abc";
    // ValueEventListener valueEventListener = new ValueEventListener() {
    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for (DataSnapshot data : dataSnapshot.getChildren()){

                // data.getKey() general eke value key
                DataSnapshot oneUserphone=data.child("phone");
                DataSnapshot oneUseremail=data.child("name");
                DataSnapshot oneUsername=data.child("email");
                DataSnapshot oneUsermessage=data.child("message");
                Log.d("mytest",oneUserphone.getValue().toString()+" "+oneUseremail.getValue().toString()+" "+oneUsername.getValue().toString()+" "+oneUsermessage.getValue().toString())  ;
            }


            //Do what you need to do with your list
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d("mytest", databaseError.getMessage()); //Don't ignore errors!
        }
    });

    // usersRef.addListenerForSingleValueEvent(valueEventListener);

}

}




