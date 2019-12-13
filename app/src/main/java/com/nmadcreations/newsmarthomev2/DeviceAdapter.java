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
    private OnItemClickListener mListener;
    private Context mContext;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public DeviceAdapter(ArrayList<SingleDevice> exampleList) {
        mExampleList = exampleList;
        //mContext = context;
    }

    public void setOnItemClickListner(OnItemClickListener listener){
        mListener = listener;
    }

    public  static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mremoveDevice;
        public Switch switch1;
        public RelativeLayout view_forground, view_background;


        public ExampleViewHolder(View itemView, final OnItemClickListener listner){
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mremoveDevice = itemView.findViewById(R.id.deviceRemove);
            switch1 = itemView.findViewById(R.id.switch1);

            view_forground = itemView.findViewById(R.id.view_forground);
            view_background = itemView.findViewById(R.id.view_background);
            view_background.setVisibility(View.GONE);
            //to itemClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner !=null){
                        int possition = getAdapterPosition();
                        if (possition != RecyclerView.NO_POSITION){
                            listner.onItemClick(possition);
                        }
                    }
                }
            });

            //to delete button
//            mremoveDevice.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(listner !=null){
//                        int possition = getAdapterPosition();
//                        if (possition != RecyclerView.NO_POSITION){
//                            listner.onDeleteClick(possition);
//                        }
//                    }
//                }
//            });

            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Log.d("newmad","on possion "+getAdapterPosition());
                    }else {
                        Log.d("newmad","off possion "+getAdapterPosition());
                    }
                }
            });


        }
    }



    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {

        SingleDevice currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getId());
        holder.mTextView2.setText(currentItem.getName());

        holder.itemView.setTag(position);



    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
