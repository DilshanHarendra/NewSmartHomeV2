package com.nmadcreations.newsmarthomev2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHanlder {

    private String homeName="home1";
    int c=0;
    long x=0;
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

    public void addHometoUser(String uid,String hname){
        firebaseDatabase.getReference().child("Users").child(uid).child("SHname").setValue(hname);
    }
    public void addUserToHome(String uid,String shName,String uPosition){
            firebaseDatabase.getReference().child("SmartHome").child(shName).child("User").child("u"+getUserCount()).setValue(uid);
        }

    public void createHome(String homeid){
        firebaseDatabase.getReference().child("SmartHome").setValue(homeid);
    }

    public void addRgbDevice(String did,String name,String type){
        firebaseDatabase.getReference().child("Device").child(did).child("SHname").setValue(homeName);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceID").setValue(did);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceName").setValue(name);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceType").setValue(type);
        firebaseDatabase.getReference().child("Device").child(did).child("alarmId").setValue(getAlarmId());
        firebaseDatabase.getReference().child("Device").child(did).child("alarmset").setValue("none");
        firebaseDatabase.getReference().child("Device").child(did).child("function").setValue("enargy");
        firebaseDatabase.getReference().child("Device").child(did).child("cHex").setValue("0");
        firebaseDatabase.getReference().child("Device").child(did).child("state").setValue("0");
        firebaseDatabase.getReference().child("Device").child(did).child("cR").setValue("0");
        firebaseDatabase.getReference().child("Device").child(did).child("cG").setValue("0");
        firebaseDatabase.getReference().child("Device").child(did).child("cB").setValue("0");
    }

    public void addPlugDevice(String did,String name,String type){
        firebaseDatabase.getReference().child("Device").child(did).child("SHname").setValue(homeName);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceID").setValue(did);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceName").setValue(name);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceType").setValue(type);
        firebaseDatabase.getReference().child("Device").child(did).child("state").setValue("0");

    }

    public void addBlubDevice(String did,String name,String type){
        firebaseDatabase.getReference().child("Device").child(did).child("SHname").setValue(homeName);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceID").setValue(did);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceName").setValue(name);
        firebaseDatabase.getReference().child("Device").child(did).child("deviceType").setValue(type);
        firebaseDatabase.getReference().child("Device").child(did).child("function").setValue("enargy");
        firebaseDatabase.getReference().child("Device").child(did).child("state").setValue("0");

    }

private int getAlarmId(){
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Device");

    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                try {
                    if (snapshot.child("SHname").getValue().toString().trim().equals(homeName)) {
                        Log.d("nsmart","check No value");
                        c++;
                    }
                }catch (Exception e){
                    //     Log.d("nsmart", "error "+e);
                }
            }
            Log.d("nsmart","check No value"+c);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    });
    c++;
    return  c;
}
       private long getUserCount(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SmartHome").child(homeName).child("User");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    try {
                        if (dataSnapshot.hasChildren()){
                            Log.d("nsmart","childern"+dataSnapshot.getChildrenCount());
                            x=dataSnapshot.getChildrenCount();
                        }else{
                            x=1;
                        }



                    }catch (Exception e){
                        //     Log.d("nsmart", "error "+e);
                    }

                Log.d("nsmart","check No value"+x);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return  x;
    }
}




