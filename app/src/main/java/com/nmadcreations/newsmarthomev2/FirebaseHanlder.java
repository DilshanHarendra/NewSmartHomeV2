package com.nmadcreations.newsmarthomev2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHanlder {

   private String homeName;
   private  ArrayList<SingleDevice> rgb,nbulb,plug,gas;
    public FirebaseHanlder(){
        this.rgb= new ArrayList<>() ;
        this.nbulb= new ArrayList<>() ;
        this.plug= new ArrayList<>() ;
        this.gas= new ArrayList<>() ;
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
            firebaseDatabase.getReference().child("Users").child(uid).child("SHname").setValue(shName);
        }

    public void addHome(String homeid){
        firebaseDatabase.getReference().child("SmartHome").setValue(homeid);

    }
    public void addDevice(String uid,String name,String type,String shname){
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceID").setValue(uid);
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceName").setValue(name);
        firebaseDatabase.getReference().child("Device").child(uid).child("deviceType").setValue(type);
        firebaseDatabase.getReference().child("Device").child(uid).child("SHname").setValue(shname);
    }



public void getDevices( String uid){

    DatabaseReference rootRef = firebaseDatabase.getReference().child("Users").child(uid);
    rootRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot data: dataSnapshot.getChildren()){
                try{
                        if (data.getKey().equals("SHname")){
                     //   Log.d("nsmart", "home name "+data.getValue());
                        homeName=data.getValue().toString();
                        break;
                    }
                }catch (Exception e){
                    Log.d("nsmart", "error "+e);
                }
            }
         }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    DatabaseReference homeroof = firebaseDatabase.getReference().child("Device");
    homeroof.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot hdata:dataSnapshot.getChildren()){
                try{
                    plug.clear();
                    nbulb.clear();
                    rgb.clear();
                    gas.clear();

                    if (hdata.child("SHname").getValue().toString().trim().equals(homeName)){
                  //      Log.d("nsmart", "values|"+hdata.child("SHname").getValue());
                        if (hdata.child("deviceType").getValue().toString().trim().equals("PLUG")){
                            plug.add( new SingleDevice(hdata.child("deviceID").getValue().toString(),hdata.child("deviceName").getValue().toString(),hdata.child("deviceType").getValue().toString()));
                        }else if (hdata.child("deviceType").getValue().toString().trim().equals("BLUB")){
                            nbulb.add( new SingleDevice(hdata.child("deviceID").getValue().toString(),hdata.child("deviceName").getValue().toString(),hdata.child("deviceType").getValue().toString()));

                        }else if (hdata.child("deviceType").getValue().toString().trim().equals("RGBBLUB")){
                            rgb.add( new SingleDevice(hdata.child("deviceID").getValue().toString(),hdata.child("deviceName").getValue().toString(),hdata.child("deviceType").getValue().toString()));

                        }else if (hdata.child("deviceType").getValue().toString().trim().equals("GAS")){
                            gas.add( new SingleDevice(hdata.child("deviceID").getValue().toString(),hdata.child("deviceName").getValue().toString(),hdata.child("deviceType").getValue().toString()));

                        }

                   }
                }catch (Exception e){
                //    Log.d("nsmart", "error "+e);
                }

            }
            Log.d("nsmart", "fclz "+rgb.size());

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });



}

    public ArrayList<SingleDevice> getRgb() {
        return rgb;
    }

    public ArrayList<SingleDevice> getNbulb() {
        return nbulb;
    }

    public ArrayList<SingleDevice> getPlug() {
        return plug;
    }

    public ArrayList<SingleDevice> getGas() {
        return gas;
    }
}




