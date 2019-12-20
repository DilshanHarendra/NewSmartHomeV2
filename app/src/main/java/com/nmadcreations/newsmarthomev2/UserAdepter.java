package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdepter extends ArrayAdapter<String> {

private ArrayList<String> userDetails;
private Context context;
private int r;
private SharedPreferences sharedPreferences;
private String homeName;
private String d[];
    public UserAdepter(Context context, int resource, ArrayList<String> userDetails) {
        super(context, resource,userDetails);
        this.context=context;
        this.userDetails=userDetails;
        this.r=resource;

        sharedPreferences = getContext().getSharedPreferences("smartHome",getContext().MODE_PRIVATE);
        homeName=sharedPreferences.getString("homeName","");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =LayoutInflater.from(context);
        convertView = inflater.inflate(r,parent,false);
        TextView name= convertView.findViewById(R.id.usernametext);
        final CheckBox checkBox =convertView.findViewById(R.id.checkBoxuser);
        d=getItem(position).split("/");
        name.setText(d[2]);
        if (d[4].equals("1")){
            checkBox.setChecked(false);
        }else {
            checkBox.setChecked(true);
        }
            if (d[1].equals("user")){
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBox.isChecked()){
                            FirebaseDatabase.getInstance().getReference()
                                    .child("SmartHome").child(homeName).child("User").child(d[0])
                                    .setValue(d[1]+"/"+d[2]+"/"+d[3]+"/0");
                        }else {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("SmartHome").child(homeName).child("User").child(d[0])
                                    .setValue(d[1]+"/"+d[2]+"/"+d[3]+"/1");
                        }

                    }
                });

            }else{
                checkBox.setEnabled(false);
            }

        return convertView;
    }



}
