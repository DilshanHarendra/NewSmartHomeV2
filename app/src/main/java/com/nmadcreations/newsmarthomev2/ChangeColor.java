package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

public class ChangeColor extends AppCompatActivity {

    String did;
    private ToggleButton buttonOnOff;
    private GradientDrawable gradientDrawable;
    private ScrollView constraintLayout;
    private TextView textViewNickname;
    private ColorPickerView colorPickerView;
    private int state=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);

        textViewNickname =  findViewById(R.id.rgbConName);

        buttonOnOff = findViewById(R.id.Onoff);
        constraintLayout=findViewById(R.id.controllerbg);
        colorPickerView=findViewById(R.id.colorPicker);


        did=getIntent().getStringExtra("id").trim();
        Log.d("nsmart","get Intent "+did);
        FirebaseDatabase.getInstance().getReference().child("Device").child(did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("function").getValue()==null){
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("function").setValue("enargy");
                }else{
                    textViewNickname.setText(dataSnapshot.child("deviceName").getValue().toString());
                    Log.d("nsmart","firebase data "+dataSnapshot.child("function").getValue());
                }
                if (dataSnapshot.child("state").getValue()==null){
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("0");
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

        FirebaseDatabase.getInstance().getReference().child("Device").child(did).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("cHex").getValue()==null){
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("cHex").setValue("0000");
                }else{
                    try {
                        int color =Integer.parseInt(dataSnapshot.child("cHex").getValue().toString());
                        colorPickerView.setInitialColor(color);
                        String hex = colorHex(color);
                        // Log.d("error", "meka okakda" + hex);

                        constraintLayout.setBackgroundColor(color);
                        textViewNickname.setTextColor(color);

                        int colorg[] = {Color.parseColor("#223553"), Color.parseColor("#223553"), Color.parseColor(hex)};
                        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorg);
                        gradientDrawable.setCornerRadius(0f);
                        constraintLayout.setBackground(gradientDrawable);
                    }catch (Exception e){
                        Log.d("nsmart","error "+e);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser) {
                if (state!=0) {
                    //    Log.d("error", "r" + Color.red(color));
                    //    Log.d("error", "g" + Color.green(color));
                    //   Log.d("error", "b" + Color.blue(color));
                    String hex = colorHex(color);
                    //    Log.d("error", "meka okakda" + hex);

                    constraintLayout.setBackgroundColor(color);
                    textViewNickname.setTextColor(color);

                    int colorg[] = {Color.parseColor("#223553"), Color.parseColor("#223553"), Color.parseColor(hex)};
                    gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorg);
                    gradientDrawable.setCornerRadius(0f);
                    constraintLayout.setBackground(gradientDrawable);
                    int r, g, b;
                    r = Color.red(color);
                    g = Color.green(color);
                    b = Color.blue(color);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("cR").setValue(r);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("cG").setValue(g);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("cB").setValue(b);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("cHex").setValue(color);
                }else{
                    Toast.makeText(ChangeColor.this, "Plase Turn On Switch", Toast.LENGTH_SHORT).show();
                }
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
    private String colorHex(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "#%02X%02X%02X",  r, g, b);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, SubMenuList.class).putExtra("Id",did));
        finish();
    }
}
