package com.nmadcreations.newsmarthomev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AddUserQr extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String homeName,pass;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_qr);

        imageView=findViewById(R.id.showqr);
        sharedPreferences = this.getSharedPreferences("smartHome",this.MODE_PRIVATE);
        homeName=sharedPreferences.getString("homeName","");
        pass=sharedPreferences.getString("homePassword","");
        WindowManager manager =(WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        Display display =manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width =point.x;
        int height=point.y;
        int small=width<height ? width:height;
        small=small*3/4;
        qrgEncoder = new QRGEncoder(homeName+"/"+pass,null, QRGContents.Type.TEXT,small);
        try {
            bitmap=qrgEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);

        }catch (Exception e){
            Log.d("nsmart","error"+e);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this,MainHome.class));
        finish();
    }
}
