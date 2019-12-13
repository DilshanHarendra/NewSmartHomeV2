package com.nmadcreations.newsmarthomev2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ExampleViewHolder>  {

    private ArrayList<SingleDevice> mExampleList;
    private Context mcontext;

    public DeviceAdapter(ArrayList<SingleDevice> exampleList, Context context) {
        mExampleList = exampleList;
        mcontext = context;
    }

    public  static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mremoveDevice;
        public Switch switch1;
        public RelativeLayout view_forground, view_background;


        public ExampleViewHolder(View itemView){
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mremoveDevice = itemView.findViewById(R.id.deviceRemove);
            switch1 = itemView.findViewById(R.id.switch1);

            view_forground = itemView.findViewById(R.id.view_forground);
            view_background = itemView.findViewById(R.id.view_background);
            view_background.setVisibility(View.GONE);

        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {

        final SingleDevice currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getId());
        holder.mTextView2.setText(currentItem.getName());

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("newmad","clicked "+currentItem.getMdeviceId().toString());
            }
        });

        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(mcontext, "fefefefe", Toast.LENGTH_SHORT).show();
                    Log.d("newmad","on "+currentItem.getMdeviceId().toString());
                }else {
                    Log.d("newmad","off possion "+currentItem.getMdeviceId().toString());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
