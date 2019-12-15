package com.nmadcreations.newsmarthomev2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RgbListFrag extends Fragment {


    private ArrayList<SingleDevice> singeldevices;

    private static RecyclerView mRecyclerView;
    private static DeviceAdapter adapter;
    private   RecyclerView.LayoutManager mLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private String uid="user01",homeName="home1";



    public RgbListFrag(){
        singeldevices = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = firebaseDatabase.getReference().child("Users").child(uid);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    try{
                        if (data.getKey().equals("SHname")){
                            //     Log.d("nsmart", "home name "+data.getValue());
                            homeName=data.getValue().toString();
                            break;
                        }
                    }catch (Exception e){
                        //   Log.d("nsmart", "error "+e);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Device");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                singeldevices.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        if (snapshot.child("SHname").getValue().toString().trim().equals(homeName)) {
                            if (snapshot.child("deviceType").getValue().toString().trim().equals("RGB")) {

                                singeldevices.add (new SingleDevice( snapshot.child("deviceID").getValue().toString(),R.drawable.ic_android, snapshot.child("deviceType").getValue().toString(), snapshot.child("deviceName").getValue().toString()));
                                //         Log.d("nsmart", "values| " + snapshot.child("deviceType").getValue().toString() + " - " + snapshot.child("deviceID").getValue().toString());
                            }
                        }
                    }catch (Exception e){
                        //     Log.d("nsmart", "error "+e);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rgb_list, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerlist_rgbFrag);
        singeldevices = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = firebaseDatabase.getReference().child("Users").child(uid);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    try{
                        if (data.getKey().equals("SHname")){
                            //     Log.d("nsmart", "home name "+data.getValue());
                            homeName=data.getValue().toString();
                            break;
                        }
                    }catch (Exception e){
                        //   Log.d("nsmart", "error "+e);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Device");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                singeldevices.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        if (snapshot.child("SHname").getValue().toString().trim().equals(homeName)) {
                            if (snapshot.child("deviceType").getValue().toString().trim().equals("RGB")) {

                                createDevicesList(snapshot.child("deviceID").getValue().toString(), snapshot.child("deviceType").getValue().toString(), snapshot.child("deviceName").getValue().toString());
                                //         Log.d("nsmart", "values| " + snapshot.child("deviceType").getValue().toString() + " - " + snapshot.child("deviceID").getValue().toString());
                            }
                        }
                    }catch (Exception e){
                        //     Log.d("nsmart", "error "+e);
                    }
                }
                buildRecycleView();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });







    return view;
    }

    public void removeItem(int position){
        singeldevices.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void createDevicesList(String did, String t1, String t2){
        singeldevices.add(new SingleDevice(did,R.drawable.ic_android,t1,t2));

    }

        public void buildRecycleView(){
            new ItemTouchHelper(itemTouchhelperCallback).attachToRecyclerView(mRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            adapter = new DeviceAdapter(singeldevices,getActivity());

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(adapter);

        }





          ItemTouchHelper.SimpleCallback itemTouchhelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
              @Override
              public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                  return false;
              }

              @Override
              public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                  builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          removeItem((Integer) viewHolder.itemView.getTag());
                      }
                  });
                  builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          //  refresh();
                      }
                  });
                  //builder.setIcon(R.drawable.delete);
                  builder.setTitle("Device Remove");
                  builder.setMessage("If you want to really delete your device. All settings will be removed can not undo.!");
                  builder.setCancelable(false);
                  AlertDialog alert = builder.create();
                  alert.show();
              }
              @Override
              public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                      RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                      int actionState, boolean isCurrentlyActive) {
                  if (dX<=-10) {
                      ((DeviceAdapter.ExampleViewHolder) viewHolder).view_background.setVisibility(View.VISIBLE);
                  }else {
                      ((DeviceAdapter.ExampleViewHolder) viewHolder).view_background.setVisibility(View.GONE);
                  }
                  final View foregroundView = ((DeviceAdapter.ExampleViewHolder) viewHolder).view_forground;
                  getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                          actionState, isCurrentlyActive);


              }

          };

public static void search(String word){

    try {

            adapter.getFilter().filter(word.trim());


    }catch (Exception e){
        Log.d("nsmart","Text: "+e);
    }


}





}

