package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BPSubMenu extends AppCompatActivity {

    private String name,did;
    private TextView textView;
    private int state;
    private ToggleButton buttonOnOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpsub_menu);
        buttonOnOff=findViewById(R.id.plugOnOff);
       textView=findViewById(R.id.plugname);
       did=getIntent().getStringExtra("Id");
        FirebaseDatabase.getInstance().getReference().child("Device").child(did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("state").getValue()==null){
                    //FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("0");
                }else{
                    if (dataSnapshot.child("state").getValue().equals("0")){
                        buttonOnOff.setBackgroundResource(R.drawable.offbutton);
                        state=0;
                    }else{
                        buttonOnOff.setBackgroundResource(R.drawable.onbutton);
                        state=1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (state==0){
                    state=1;
                    Log.d("nsmart","state "+state);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("1");
                }else if (state==1){
                    state=0;
                    Log.d("nsmart","state "+state);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("0");
                }
            }
        });





    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, MainHome.class));
        finish();
    }
}
