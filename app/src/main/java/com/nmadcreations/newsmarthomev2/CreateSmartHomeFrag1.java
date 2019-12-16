package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class CreateSmartHomeFrag1 extends Fragment {

    private Button button;
    private RadioButton oldOnew,newOnew;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_smart_home_frag1, container, false);
        button=view.findViewById(R.id.createnext1);
        oldOnew=view.findViewById(R.id.existone);
        newOnew=view.findViewById(R.id.newone);
        fragmentManager =getActivity().getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldOnew.isChecked()){
                    fragment = new CreateSmartHomeFrag3();
                    fragmentTransaction.replace(R.id.fragmentcreate,fragment);
                    fragmentTransaction.commit();

                    Log.d("nsmart","old");
                }else if (newOnew.isChecked()){

                    fragment = new CreateSmartHomeFrag2();
                    fragmentTransaction.replace(R.id.fragmentcreate,fragment);
                    fragmentTransaction.commit();
                }
            }
        });


        return view;
    }


}
