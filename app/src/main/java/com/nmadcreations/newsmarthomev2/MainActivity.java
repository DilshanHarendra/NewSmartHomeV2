package com.nmadcreations.newsmarthomev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this,Login.class));



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("nsmart", "on back" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("nsmart", "stop" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("nsmart", "start" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("nsmart", "pause" );
    }



}
