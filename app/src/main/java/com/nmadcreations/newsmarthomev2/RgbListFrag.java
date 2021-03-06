package com.nmadcreations.newsmarthomev2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private String uid="user01",homeName=null;
    private SharedPreferences sharedPreferences;



    public RgbListFrag(){


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rgb_list, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerlist_rgbFrag);
        singeldevices = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        sharedPreferences = getContext().getSharedPreferences("smartHome",getContext().MODE_PRIVATE);
        homeName=sharedPreferences.getString("homeName","");
/*
        DatabaseReference rootRef = firebaseDatabase.getReference().child("Users").child(uid);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
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
*/

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
        singeldevices.add(new SingleDevice(did,R.drawable.smartbulb,t1,t2));

    }

        public void buildRecycleView(){
            new ItemTouchHelper(itemTouchhelperCallback).attachToRecyclerView(mRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            adapter = new DeviceAdapter(singeldevices,getActivity());

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(adapter);

        }





          ItemTouchHelper.SimpleCallback itemTouchhelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
              @Override
              public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                  return false;
              }
              @Override
              public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                  //Log.d("nsmart","dir : "+direction);
                  final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                  if (direction == ItemTouchHelper.LEFT) {
                      //builder.setIcon(R.drawable.delete);
                      builder.setTitle("Device Remove");
                      builder.setMessage("If you want to really delete your device. All settings will be removed can not undo.!");
                      builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              //removeItem((Integer) viewHolder.itemView.getTag());
                              firebaseDatabase.getReference().child("Device").child(viewHolder.itemView.getTag().toString().trim()).removeValue();
                          }
                      });
                      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              buildRecycleView();
                          }
                      });
                  }else {
                      final EditText nickname = new EditText(getActivity());
                      builder.setTitle("Enter new device name:");
                      nickname.setHint("Nickname:");
                      nickname.setInputType(InputType.TYPE_CLASS_TEXT);
                      builder.setView(nickname);
                      builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              if (nickname.getText().length()>=4) {
                                  firebaseDatabase.getReference().child("Device").child(viewHolder.itemView.getTag().toString().trim()).child("deviceName").setValue(nickname.getText().toString());
                              }else {
                                  Toast.makeText(getActivity(), "Please enter at least 4 letters nickname to change your nickname", Toast.LENGTH_LONG).show();
                                  buildRecycleView();
                              }
                          }
                      });
                      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              buildRecycleView();
                          }
                      });
                  }
                  builder.setCancelable(false);
                  AlertDialog alert = builder.create();
                  alert.show();
              }
              @Override
              public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                      RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                      int actionState, boolean isCurrentlyActive) {
                  if (dX<=-10 || dX>=10) {
                      ((DeviceAdapter.ExampleViewHolder) viewHolder).view_background.setVisibility(View.VISIBLE);
                      if (dX<=-10){
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).edit_text.setVisibility(View.INVISIBLE);
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).editIcon.setVisibility(View.INVISIBLE);
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).delete_text.setVisibility(View.VISIBLE);
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).deleteIcon.setVisibility(View.VISIBLE);
                      }else if (dX>=10){
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).delete_text.setVisibility(View.INVISIBLE);
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).deleteIcon.setVisibility(View.INVISIBLE);
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).edit_text.setVisibility(View.VISIBLE);
                          ((DeviceAdapter.ExampleViewHolder) viewHolder).editIcon.setVisibility(View.VISIBLE);
                      }
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

