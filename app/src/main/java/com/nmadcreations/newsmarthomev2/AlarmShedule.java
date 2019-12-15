package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AlarmShedule extends AppCompatActivity {

    private Button buttonOk;
    private Button buttonCancel;
    private TimePicker alarmTimePicker;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private TextView textView;
    private  TextView alarmset;
    private String did;
    private int alarmid=0,h,m;
    private String alarmTime;
    private  Intent intentalarm;
    private String isbulbOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_shedule);


        buttonCancel = findViewById(R.id.AlarmCancel);
        buttonOk = findViewById(R.id.AlarmOk);

        textView = findViewById(R.id.textView2);
        alarmset = findViewById(R.id.alarmset);
        alarmTimePicker = (TimePicker) findViewById(R.id.altimePicker);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            did=getIntent().getStringExtra("id").trim();


        Log.d("nsmart","get Intent "+did);
     intentalarm = new Intent(AlarmShedule.this, AlarmReciver.class);
        intentalarm.putExtra("did",did);
        FirebaseDatabase.getInstance().getReference().child("Device").child(did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
            alarmTime= dataSnapshot.child("alarmset").getValue().toString();
          alarmid=Integer.parseInt(dataSnapshot.child("alarmId").getValue().toString());
          isbulbOff=dataSnapshot.child("state").getValue().toString();

                  if (alarmTime.equals("none")){
                      buttonCancel.setEnabled(false);
                      buttonOk.setEnabled(true);
                      alarmset.setText("Alarm Not Set");
                      alarmTimePicker.setEnabled(true);
                  }else{
                      buttonCancel.setEnabled(true);
                      buttonOk.setEnabled(false);
                      String[] temp=alarmTime.split("/");
                      if (Integer.parseInt(temp[0])>12){

                          alarmset.setText("Alarm Set : "+(Integer.parseInt(temp[0])-12)+":"+temp[1]+" PM");
                      }else{
                          alarmset.setText("Alarm Set : "+temp[0]+":"+temp[1]+" AM");
                      }

                      alarmTimePicker.setEnabled(false);
                  }
                intentalarm.putExtra("name",dataSnapshot.child("deviceName").getValue().toString());
                intentalarm.putExtra("aid",dataSnapshot.child("alarmId").getValue().toString());
                pendingIntent = PendingIntent.getBroadcast(AlarmShedule.this, alarmid, intentalarm, PendingIntent.FLAG_ONE_SHOT);
            }catch (Exception e){
                Log.d("nsmart","error"+e);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonOk.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!isbulbOff.equals("0")){
                            long time;
                            Toast.makeText(AlarmShedule.this, "Alarm ON", Toast.LENGTH_SHORT).show();

                            Calendar calendar = Calendar.getInstance();
                            h=alarmTimePicker.getCurrentHour();
                            m=alarmTimePicker.getCurrentMinute();
                            calendar.set(Calendar.HOUR_OF_DAY,h );
                            calendar.set(Calendar.MINUTE,m );
                            Log.d("nsmart", alarmTimePicker.getCurrentHour().toString());
                            Log.d("nsmart", alarmTimePicker.getCurrentMinute().toString());
                            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                            Log.d("nsmart", h+" "+m);
                            if (System.currentTimeMillis() > time) {
                                if (calendar.AM_PM == 0) {
                                    time = time + (1000 * 60 * 60 * 12);
                                } else {
                                    time = time + (1000 * 60 * 60 * 24);
                                }
                            }
                            String colock= h+"/"+m;
                            FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("alarmset").setValue(colock);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);

                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                        }else{
                            Toast.makeText(AlarmShedule.this, "Turn on Device First", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        buttonCancel.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alarmManager.cancel(pendingIntent);
                        FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("alarmset").setValue("none");
                        Toast.makeText(AlarmShedule.this, "Alarm Cancel", Toast.LENGTH_SHORT).show();
                        Log.d("nsmart", "Alarm cancel");


                    }
                });






    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, SubMenuList.class).putExtra("Id",did));
        finish();
    }
}
