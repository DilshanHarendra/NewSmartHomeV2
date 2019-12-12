package com.nmadcreations.newsmarthomev2.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmadcreations.newsmarthomev2.DeviceAdapter;
import com.nmadcreations.newsmarthomev2.R;
import com.nmadcreations.newsmarthomev2.SingleDevice;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;


    private ArrayList<SingleDevice> mExampleList;
    private RecyclerView mRecyclerView;
    private DeviceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

       // final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //textView.setText(s);
//                Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_home, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerviewfrag);


        createExampleList();
        buildRecycleView();



        Intent intent = getActivity().getIntent();
        String did = intent.getStringExtra("deviceIdQr");
        String nickname = intent.getStringExtra("nickname");
        if (did!=null){
            insertItem(did,nickname);
        }
        Toast.makeText(getActivity(),"fefefefefefefef", Toast.LENGTH_SHORT).show();
        return root;
    }

    public void insertItem(String did, String nn){
        mExampleList.add(new SingleDevice(did,R.drawable.ic_android,nn,did));
        mAdapter.notifyDataSetChanged();

    }

    public void removeItem(int position){
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createExampleList(){
        mExampleList = new ArrayList<>();
        mExampleList.add(new SingleDevice("S!-RGB-4533",R.drawable.ic_android,"Plug","TV"));
        mExampleList.add(new SingleDevice("S!-RGB-4533",R.drawable.ic_android,"Plug","TV"));
        mExampleList.add(new SingleDevice("S!-RGB-4533",R.drawable.ic_android,"Bulb","TV"));
        mExampleList.add(new SingleDevice("S!-RGB-4533",R.drawable.ic_android,"Bulb","TV"));

        new ItemTouchHelper(itemTouchhelperCallback).attachToRecyclerView(mRecyclerView);

    }
    public void buildRecycleView(){
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new DeviceAdapter(mExampleList,getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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


}