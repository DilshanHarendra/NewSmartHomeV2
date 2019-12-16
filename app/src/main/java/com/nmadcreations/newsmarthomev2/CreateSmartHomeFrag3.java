package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public   class CreateSmartHomeFrag3 extends Fragment {

    private Button btnNext,btnBack;
    private RadioButton scanqr,manual;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create_smart_home_frag3, container, false);
        btnNext=view.findViewById(R.id.addhomenext);
        btnBack=view.findViewById(R.id.addhomeback);
        scanqr=view.findViewById(R.id.adduqr);
        manual=view.findViewById(R.id.addmanual);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scanqr.isChecked()){
                    startActivity(new Intent(getActivity(),QrReader.class));
                }else if (manual.isChecked()){
                    fragment = new CreateSmartHomeFrag4();
                    fragmentTransaction.replace(R.id.fragmentcreate,fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    fragment = new CreateSmartHomeFrag1();
                    fragmentTransaction.replace(R.id.fragmentcreate,fragment);
                    fragmentTransaction.commit();

            }
        });




        return view;
    }


}
