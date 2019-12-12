package com.nmadcreations.newsmarthomev2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nmadcreations.newsmarthomev2.ui.main.SectionsPagerAdapter;

public class MainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ImageView imageView1;
    private TextView uName,navmail;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        drawerLayout = findViewById(R.id.dlayout);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(this);

        View navView = navigationView.getHeaderView(0);
        uName = (TextView) navView.findViewById(R.id.userName);
        navmail = (TextView) navView.findViewById(R.id.navmail);
        imageView1=(ImageView) navView.findViewById(R.id.imageView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainHome.this,QrReader.class));
            }
        });

//        FirebaseHanlder firebaseHanlder = new FirebaseHanlder();
//        firebaseHanlder.addUser("user02","Dilshan","Perera","Perera","dial@gmail.com");
//        firebaseHanlder.addUser("user03","Lakshan","Bandara","bandara","lakshan@gmail.com");
//        firebaseHanlder.addUser("user04","Chathun","Randika","Randika","Chathun@gmail.com");
//
//        firebaseHanlder.addHome("home1");
//        firebaseHanlder.addHome("home2");
//
//        firebaseHanlder.addToHome("user01","home1","u1");
//        firebaseHanlder.addToHome("user02","home1","u2");
//        firebaseHanlder.addToHome("user03","home2","u1");
//        firebaseHanlder.addToHome("user04","home2","u2");
//
//        firebaseHanlder.addDevice("S!-RGB-2314","room1","RGB BULB");
//        firebaseHanlder.addDevice("S!-RGB-2378","room2","RGB BULB");
//        firebaseHanlder.addDevice("S!-RGB-1314","room3","RGB BULB");
//        firebaseHanlder.addDevice("S!-RGB-4378","room4","RGB BULB");
//        firebaseHanlder.addDevice("S!-RGB-1394","room5","RGB BULB");
//        firebaseHanlder.addDevice("S!-RGB-4378","room6","RGB BULB");
//        firebaseHanlder.addDevice("S!-PLG-4380","room1","PLUG");
//        firebaseHanlder.addDevice("S!-PLG-4088","room2","PLUG");
//        firebaseHanlder.addDevice("S!-PLG-4988","room3","PLUG");
//        firebaseHanlder.addDevice("S!-PLG-4288","room4","PLUG");
//        firebaseHanlder.addDevice("S!-PLG-3288","room5","PLUG");
//        firebaseHanlder.addDevice("S!-PLG-2348","room6","PLUG");
//        firebaseHanlder.addDevice("S!-BLB-1388","room1","BULB");
//        firebaseHanlder.addDevice("S!-BLB-2388","room2","BULB");
//        firebaseHanlder.addDevice("S!-BLB-3388","room3","BULB");
//        firebaseHanlder.addDevice("S!-BLB-4588","room4","BULB");
//
//        firebaseHanlder.ReadData();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {

            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_changePsw) {

        } else if (id == R.id.nav_singout) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

        }
    }
}