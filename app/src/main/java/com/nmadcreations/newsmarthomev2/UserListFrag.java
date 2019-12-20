package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserListFrag extends Fragment {

    private ListView listView;
    private String homeName=null;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> users;
    private UserAdepter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_list, container, false);
        listView=view.findViewById(R.id.userList);
        users = new ArrayList<>();


        sharedPreferences = getContext().getSharedPreferences("smartHome",getContext().MODE_PRIVATE);
        homeName=sharedPreferences.getString("homeName","");

        FirebaseDatabase.getInstance().getReference().child("SmartHome").child(homeName).child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                  try {
                      Log.d("nsmart", "error "+snapshot.getValue());


                          users.add(snapshot.getKey().toString()+"/"+snapshot.getValue().toString());


                  }catch (Exception e){

                  }

                }
arrayAdapter= new UserAdepter(getContext(),R.layout.singel_user_row,users);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


}
