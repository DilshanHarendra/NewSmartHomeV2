package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RgbListFrag extends Fragment {


    private String uid="user01",homeName="home1";
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<SingleDevice> rgb ;
    private ArrayList<String> test ;
    private ListView listView;
    private  DeviceAdapter deviceAdapter;
    public RgbListFrag(){


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rgb_list, container, false);
        listView=view.findViewById(R.id.rgblist);
        firebaseDatabase=FirebaseDatabase.getInstance();
        rgb= new ArrayList<>();
        test= new ArrayList<>();

        DatabaseReference rootRef = firebaseDatabase.getReference().child("Users").child(uid);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    try{

                        if (data.getKey().equals("SHname")){
                          //  Log.d("nsmart", "home name "+data.getValue());
                            homeName=data.getValue().toString();
                            break;
                        }
                    }catch (Exception e){
                        //    Log.d("nsmart", "error "+e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("nsmart", "SmartHome name|"+homeName);
        DatabaseReference homeroof = firebaseDatabase.getReference().child("Device");
        homeroof.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                test.clear();
                for (DataSnapshot hdata:dataSnapshot.getChildren()){
                    try{
                          if (hdata.child("SHname").getValue().toString().trim().equals(homeName)){
                          if (hdata.child("deviceType").getValue().toString().trim().equals("RGB")){
                                test.add( hdata.child("deviceName").getValue().toString());
                            //    Log.d("nsmart", "values|"+hdata.child("deviceType").getValue());
                            }
                       }
                    }catch (Exception e){
                        //    Log.d("nsmart", "error "+e);
                    }

                }
                Log.d("nsmart", "fclz "+test.size());
                ArrayAdapter arrayAdapter= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,test);
                listView.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    return view;
    }


}
