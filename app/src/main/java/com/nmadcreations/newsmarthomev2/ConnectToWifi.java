package com.nmadcreations.newsmarthomev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ConnectToWifi extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_wifi);


        webView = (WebView) findViewById(R.id.wificon);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.4.1");





    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, MainHome.class));
        finish();
    }
}
