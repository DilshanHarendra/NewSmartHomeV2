package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


public class CreateSmartHomeFrag2 extends Fragment {

    private Button next,back;
    private EditText name,pass1,pass2;
    private SharedPreferences sharedPreferences;
    private TextView textView;
    private String uid="user01";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create_smart_home_frag2, container, false);
        back=view.findViewById(R.id.createnewback);
        next=view.findViewById(R.id.createnewnext);
        name=view.findViewById(R.id.ghomename);
        pass1=view.findViewById(R.id.gpassword1);
        pass2=view.findViewById(R.id.gpassword2);
        textView=view.findViewById(R.id.texterror);
        sharedPreferences = this.getActivity().getSharedPreferences("smartHome",getContext().MODE_PRIVATE);

        try {
            uid=getActivity().getIntent().getStringExtra("uid");
        }catch (Exception e){

        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                Fragment fragment = new CreateSmartHomeFrag1();
                fragmentTransaction.replace(R.id.fragmentcreate,fragment);
                fragmentTransaction.commit();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.length()==0){
                    name.setError("Enter SmartHome name");
                }else if (pass1.length()==0){
                    pass1.setError("Enter Password");
                }else if (pass2.length()==0){
                    pass2.setError("Re-Enter password");
                }else if (pass1.length()<6){
                    pass1.setError("Password must be 6 Characters long");
                }else if (!pass2.getText().toString().trim().equals(pass1.getText().toString().trim())){
                    pass2.setError("Don't match with the above password");
                    Log.d("nsmart","value"+pass1.getText()+" "+pass2.getText());
                }else {
                    FirebaseDatabase.getInstance().getReference().child("SmartHome").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(name.getText().toString().trim())){
                                textView.setText("Smart Home name is  taken. Enter another name");
                            }else{
                                textView.setText("");
                                FirebaseDatabase.getInstance().getReference().child("SmartHome").child(name.getText().toString().trim()).child("password").setValue(pass1.getText().toString().trim());
                                Date currentTime = Calendar.getInstance().getTime();
                                Log.d("nsmart", "time "+currentTime);
                                FirebaseDatabase.getInstance().getReference().child("SmartHome").child(name.getText().toString().trim()).child("User").child(uid).setValue("Admin/"+currentTime);
                                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("SHname").setValue(name.getText().toString().trim());
                                SharedPreferences.Editor editor =sharedPreferences.edit();
                                editor.putString("homeName",name.getText().toString().trim());
                                editor.putString("homePassword",pass1.getText().toString().trim());
                                editor.commit();

                                startActivity(new Intent(getActivity(),MainHome.class));
                                getActivity().finish();

                            }

                            Log.d("nsmart","value "+dataSnapshot.hasChild("home5"));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
        return view;
    }


}
