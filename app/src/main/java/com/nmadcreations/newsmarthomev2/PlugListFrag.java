package com.nmadcreations.newsmarthomev2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PlugListFrag extends Fragment{

    private ArrayList<SingleDevice> mExampleList;
    private RecyclerView mRecyclerView;
    private static DeviceAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private String uid="user01",homeName=null;
    private SharedPreferences sharedPreferences;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_plug_list, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerlist_plugFrag);
        mExampleList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        sharedPreferences = getContext().getSharedPreferences("smartHome",getContext().MODE_PRIVATE);
        homeName=sharedPreferences.getString("homeName","");


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Device");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mExampleList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        if (snapshot.child("SHname").getValue().toString().trim().equals(homeName)) {
                            if (snapshot.child("deviceType").getValue().toString().trim().equals("PLUG")) {
                                String did = snapshot.child("deviceID").getValue().toString();
                                String t1 = snapshot.child("deviceType").getValue().toString();
                                String t2 = snapshot.child("deviceName").getValue().toString();
                                createExampleList(did, t1, t2);
                             //   Log.d("nsmart", "values| " + snapshot.child("deviceType").getValue().toString() + " - " + snapshot.child("deviceID").getValue().toString());
                            }
                        }
                    }catch (Exception e){
                       // Log.d("nsmart", "error "+e);
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
        mExampleList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void createExampleList(String did, String t1, String t2){
        mExampleList.add(new SingleDevice(did,R.drawable.smartplug,t1,t2));
    }

    public void buildRecycleView(){
        new ItemTouchHelper(itemTouchhelperCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new DeviceAdapter(mExampleList,getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        //String s = "room5";
        //mAdapter.getFilter().filter(s.toString().trim());
    }

    public void test1(String s){
        Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
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
