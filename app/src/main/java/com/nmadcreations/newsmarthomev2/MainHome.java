package com.nmadcreations.newsmarthomev2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nmadcreations.newsmarthomev2.ui.main.SectionsPagerAdapter;

public class MainHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainHome.this,QrReader.class));
            }
        });

        FirebaseHanlder firebaseHanlder = new FirebaseHanlder();
        firebaseHanlder.addUser("user02","Dilshan","Perera","Perera","dial@gmail.com");
        firebaseHanlder.addUser("user03","Lakshan","Bandara","bandara","lakshan@gmail.com");
        firebaseHanlder.addUser("user04","Chathun","Randika","Randika","Chathun@gmail.com");

        firebaseHanlder.addHome("home1");
        firebaseHanlder.addHome("home2");

        firebaseHanlder.addToHome("user01","home1","u1");
        firebaseHanlder.addToHome("user02","home1","u2");
        firebaseHanlder.addToHome("user03","home2","u1");
        firebaseHanlder.addToHome("user04","home2","u2");

       firebaseHanlder.getDevices("user01");
    }
}