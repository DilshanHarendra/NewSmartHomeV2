package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public  class  CreateSmartHome extends AppCompatActivity {


   public CreateSmartHome(){

   }
    String personId;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_smart_home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            personId = acct.getId();
            Log.d("nsmart", "id"+personId );

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("nsmart", "on back" );
        close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("nsmart", "stop" );
        close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("nsmart", "start" );
        if (acct == null) {

            startActivity( new Intent(this,Login.class));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("nsmart", "pause" );
        close();

    }
private void close(){
        try {
            FirebaseDatabase.getInstance().getReference().child("Users").
                    child(personId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("SHname")){



                        startActivity( new Intent(CreateSmartHome.this,MainHome.class));
                        finish();
                    }else{
                        FirebaseDatabase.getInstance().getReference().child("Users").child(personId).removeValue();

                        mGoogleSignInClient.signOut();

                        startActivity(new Intent(CreateSmartHome.this,Login.class));
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }catch (Exception e){

        }



}

}
