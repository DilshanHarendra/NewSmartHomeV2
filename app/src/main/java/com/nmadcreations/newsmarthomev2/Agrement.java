package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Agrement extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button agree,cancel;
    String personName,personGivenName,personEmail,personId,personFamilyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrement);
        agree=findViewById(R.id.agree);
        cancel=findViewById(R.id.cancelagree);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseHanlder(Agrement.this).addUser(personId,personName,personFamilyName,personGivenName,personEmail);
                startActivity(new Intent(Agrement.this,CreateSmartHome.class).putExtra("uid",personId));
                finish();
            }
        });
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
             personName = acct.getDisplayName();
             personGivenName = acct.getGivenName();
             personFamilyName = acct.getFamilyName();
             personEmail = acct.getEmail();
             personId = acct.getId();


        }else{
            startActivity( new Intent(this,MainActivity.class));
            finish();
        }
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signOut();
        }
    });

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        startActivity(new Intent(Agrement.this, Login.class));
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( getIntent());
        finish();
    }
}
