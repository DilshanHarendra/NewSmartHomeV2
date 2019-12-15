package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubMenuList extends AppCompatActivity {
    private Button colorb,enargyb,danceb,partyb,readb,sleepb,onoff,shedule;
    private String did;
    private TextView topic;
    private String state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu_list);

        colorb= findViewById(R.id.changecolor);
        enargyb= findViewById(R.id.enargy);
        danceb= findViewById(R.id.dance);
        partyb= findViewById(R.id.party);
        readb= findViewById(R.id.read);
        sleepb= findViewById(R.id.sleep);
        topic=findViewById(R.id.btnfunction);
        onoff= findViewById(R.id.onoff);
        shedule=findViewById(R.id.shedule);

        did=getIntent().getStringExtra("Id").trim();
        Log.d("nsmart","get Intent "+did);

        FirebaseDatabase.getInstance().getReference().child("Device").child(did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("function").getValue()==null){
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("enargy");
                }else{
                    topic.setText(dataSnapshot.child("function").getValue().toString());
                    Log.d("nsmart","firebase data "+dataSnapshot.child("function").getValue());
                }
                if (dataSnapshot.child("state").getValue().equals("0")){
                    state="0";
                    onoff.setText("Off");
                }else {
                    state="1";
                    onoff.setText("On");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals("0")){
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("1");
                    state="1";
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("0");
                    state="0";
                }

            }
        });


        colorb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("color");
                startActivity(new Intent(SubMenuList.this,ChangeColor.class).putExtra("id",did));
                finish();
            }
        });
        shedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SubMenuList.this,AlarmShedule.class).putExtra("id",did));
                finish();
            }
        });
        enargyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("enargy");
            }
        });
        danceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("dance");
            }
        });
        partyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("party");
            }
        });
        readb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("read");
            }
        });
        sleepb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("sleep");
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
