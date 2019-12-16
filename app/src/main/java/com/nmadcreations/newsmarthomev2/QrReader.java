package com.nmadcreations.newsmarthomev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QrReader extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static final String EXTRA_MESSAGE = "QwMessage";

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private FirebaseHanlder firebaseHanlder;
    private FirebaseDatabase firebaseDatabase;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                Toast.makeText(QrReader.this,"Permission is Granted!", Toast.LENGTH_LONG).show();
            }else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(QrReader.this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResults(int requestCode, String permission[], int grantResults[]){
        switch (requestCode){
            case REQUEST_CAMERA :
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(QrReader.this,"Permission granted", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(QrReader.this,"Permission denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if (scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }
    }

    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }
    public void onStop(){
        super.onStop();
        scannerView.stopCamera();
    }

    public void onBackPressed(){
        super.onBackPressed();
        onStop();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(QrReader.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        final String scanResults = result.getText();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        final EditText nickname = new EditText(QrReader.this);

        if (scanResults.startsWith("S!")) {
            if(checkavailability(scanResults.toString())==0) {
                vibrator.vibrate(100);
                builder.setTitle("Enter device name:");
                //builder.setMessage(scanResults);
                nickname.setHint("Nickname:");
                nickname.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(nickname);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QrReader.this, MainHome.class);
                        String deviceId = scanResults.toString();
                        String nn = nickname.getText().toString();
                        if (nn.equals("")) {
                            nn = "My Device";
                        }
//                        intent.putExtra("deviceIdQr", deviceId);
//                        intent.putExtra("nickname", nn);
                        addDevicetoFirebase(deviceId,nn);
                        onStop();
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.resumeCameraPreview(QrReader.this);
                    }
                });
            }else {
                vibrator.vibrate(500);
                builder.setTitle("Error!..");
                builder.setMessage("This device already have in your device list!");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.resumeCameraPreview(QrReader.this);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QrReader.this, MainHome.class);
                        onStop();
                        startActivity(intent);
                        finish();
                    }
                });
            }
        } else {
            vibrator.vibrate(500);
            builder.setTitle("Error!..");
            builder.setMessage("This is not a Smart Device. Please scan correct smart device!!.");
            builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    scannerView.resumeCameraPreview(QrReader.this);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(QrReader.this, MainHome.class);
                    onStop();
                    startActivity(intent);
                    finish();
                }
            });

        }
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private int checkavailability(final String did){

        return 0;
//        final int[] val = {0};
//        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Device");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    try{
//                        if (snapshot.child("deviceID").getValue().toString().trim().equals(did)){
//                           val[0] = 1;
//                        }else {
//                            val[0] = 0;
//                        }
//                    }catch (Exception e){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        return val[0];
    }

    private void addDevicetoFirebase(String qrId, String nickName){
        firebaseHanlder = new FirebaseHanlder();
        if (qrId.startsWith("S!-RGB")){
            firebaseHanlder.addRgbDevice(qrId,nickName,"RGB");
        }else if (qrId.startsWith("S!-PLG")){
            firebaseHanlder.addPlugDevice(qrId,nickName,"PLUG");
        }else if (qrId.startsWith("S!-BLB")){
            firebaseHanlder.addBlubDevice(qrId,nickName,"BULB");
        }
    }

}
