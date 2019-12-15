package com.nmadcreations.newsmarthomev2;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.media.app.NotificationCompat;

import com.google.firebase.database.FirebaseDatabase;

public class AlarmReciver extends BroadcastReceiver  {


    @Override
    public void onReceive(Context context, Intent intent) {


        String did= intent.getStringExtra("did");
        String name= intent.getStringExtra("name");
        String aid= intent.getStringExtra("aid");
        FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("alarmset").setValue("none");
        FirebaseDatabase.getInstance().getReference().child("Device").child(did).child("state").setValue("0");

        Log.d("nsmart", "Alarm wadinawa"+did);
        Log.d("nsmart", "name"+name);
        Log.d("nsmart", "name"+name);











    }
}

