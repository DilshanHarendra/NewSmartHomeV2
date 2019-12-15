package com.nmadcreations.newsmarthomev2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmShedule extends AppCompatActivity {

    private Button buttonOk;
    private Button buttonCancel;
    private TimePicker alarmTimePicker;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_shedule);


        buttonCancel = findViewById(R.id.AlarmCancel);
        buttonOk = findViewById(R.id.AlarmOk);

        textView = findViewById(R.id.alarmset);
        alarmTimePicker = (TimePicker) findViewById(R.id.altimePicker);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);


    }
}
