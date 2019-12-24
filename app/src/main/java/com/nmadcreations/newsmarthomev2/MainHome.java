package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nmadcreations.newsmarthomev2.ui.main.SectionsPagerAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.Calendar;
import java.util.Date;

public class MainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ImageView imageView1;
    private TextView hName,navmail;
    private DrawerLayout drawerLayout;
    private Vibrator vibrator;
    private FirebaseDatabase firebaseDatabase;
    private String uid="default user";
    private SearchView searchView;

    private GoogleSignInClient mGoogleSignInClient;


//--
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        drawerLayout = findViewById(R.id.dlayout);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

            //PlugListFrag plugListFrag = new PlugListFrag();
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RgbListFrag.search(query);
                PlugListFrag.search(query);
                NbulbListFrag.search(query);
                GasListFrag.search(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
               // plugListFrag.test1(newText);
                RgbListFrag.search(newText);
                PlugListFrag.search(newText);
                NbulbListFrag.search(newText);
                GasListFrag.search(newText);
                return false;
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();


        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT>=21){
            nav_Menu.findItem(R.id.nav_addnewDevice).setVisible(false);
            nav_Menu.findItem(R.id.nav_wifiConnect).setVisible(false);
            nav_Menu.findItem(R.id.nav_settings).setVisible(false);
        }

        View navView = navigationView.getHeaderView(0);
        hName = (TextView) navView.findViewById(R.id.userName);
        navmail = (TextView) navView.findViewById(R.id.navmail);
        imageView1=(ImageView) navView.findViewById(R.id.imageView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            uid = acct.getId().toString();
            DatabaseReference rootRef = firebaseDatabase.getReference().child("Users").child(uid);
            rootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        hName.setText(dataSnapshot.child("SHname").getValue().toString().trim());
                        navmail.setText(dataSnapshot.child("UEmail").getValue().toString().trim());
                    }catch (Exception e){
                        hName.setText("hname Error");
                        navmail.setText("email Error");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }

        if (Build.VERSION.SDK_INT>=21){
            ImageView icon = new ImageView(this); // Create an icon
            icon.setImageDrawable(getDrawable(R.drawable.moreic));
            com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                    .setContentView(icon)
                    .build();

            SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:

            ImageView itemIcon1 = new ImageView(this);
            itemIcon1.setImageDrawable(getDrawable(R.drawable.wifi));
            SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();

            ImageView itemIcon2 = new ImageView(this);
            itemIcon2.setImageDrawable(getDrawable(R.drawable.plus));
            SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

            ImageView itemIcon3 = new ImageView(this);
            itemIcon3.setImageDrawable(getDrawable(R.drawable.settingw));
            SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();



            itemIcon1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vibrator.vibrate(50);
                    startActivity(new Intent(MainHome.this,ConnectToWifi.class));
                }
            });
            itemIcon2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vibrator.vibrate(50);
                    startActivity(new Intent(MainHome.this,QrReader.class));

                }
            });
            itemIcon3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vibrator.vibrate(50);
                    startActivity(new Intent(MainHome.this,AdminDashBoard.class));
                }
            });


            SubActionButton.LayoutParams layoutParams1 = (SubActionButton.LayoutParams) button3.getLayoutParams();
            layoutParams1.width = 160;
            layoutParams1.height = 160;
            layoutParams1.bottomMargin = 40;
            layoutParams1.rightMargin = 100;



            com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams layoutParams2 = (com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams) actionButton.getLayoutParams();
            layoutParams2.width = 200;
            layoutParams2.rightMargin = 75;

            layoutParams2.bottomMargin = 110;
            layoutParams2.height = 200;

            FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).setRadius(500)
                    .addSubActionView(button1)
                    .addSubActionView(button2)
                    .addSubActionView(button3)
                    .attachTo(actionButton)
                    .build();


        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_addnewDevice) {
            startActivity(new Intent(MainHome.this,QrReader.class));
        } else if (id == R.id.nav_addUser) {
            Toast.makeText(this, "Add User", Toast.LENGTH_SHORT).show();
            startActivity( new Intent(this,AddUserQr.class));
            finish();
        }else if (id == R.id.nav_wifiConnect){
            startActivity(new Intent(MainHome.this,ConnectToWifi.class));
        }else if (id == R.id.nav_settings){
            startActivity(new Intent(MainHome.this,AdminDashBoard.class));
        }else if (id == R.id.nav_singout){
            singout();
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
    public  void goToSunMenuList(String id){
        startActivity( new Intent(this,SubMenuList.class));
    }


    private void singout(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainHome.this, "Singout Done", Toast.LENGTH_SHORT).show();
                    }
                });
        startActivity(new Intent(MainHome.this,Login.class));
        finish();
    }



}