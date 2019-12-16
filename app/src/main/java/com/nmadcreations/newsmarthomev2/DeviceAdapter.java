package com.nmadcreations.newsmarthomev2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ExampleViewHolder> implements Filterable {

    private ArrayList<SingleDevice> mExampleList;
    private ArrayList<SingleDevice> mExamplelistwithSearch;

    private Context mcontext;

    public DeviceAdapter(ArrayList<SingleDevice> exampleList, Context context) {
        mExampleList = exampleList;
        mExamplelistwithSearch = new ArrayList<>(mExampleList);
        mcontext = context;
    }



    public  class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView name;
        public TextView type;
        public ImageView mremoveDevice;
        public ToggleButton switch1;

        public ImageView deleteIcon, editIcon;
        public TextView edit_text, delete_text;






        public RelativeLayout view_forground, view_background;


        public ExampleViewHolder(View itemView){
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            type = itemView.findViewById(R.id.textView2);
            mremoveDevice = itemView.findViewById(R.id.deviceRemove);
            switch1 = itemView.findViewById(R.id.switch1);

            deleteIcon = itemView.findViewById(R.id.delete_icon);
            editIcon = itemView.findViewById(R.id.edit_Icon);
            edit_text = itemView.findViewById(R.id.text_edit);
            delete_text = itemView.findViewById(R.id.text_delete);

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
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, final int position) {

        final SingleDevice currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.type.setText(currentItem.gettype());
        holder.name.setText(currentItem.getname());

        holder.itemView.setTag(currentItem.getmDeviceId().toString());

        FirebaseDatabase.getInstance().getReference().child("Device").child(currentItem.getmDeviceId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("state").getValue()==null){
                   // FirebaseDatabase.getInstance().getReference().child("Device").child(currentItem.getmDeviceId()).child("state").setValue("0");
                }else{
                    if (dataSnapshot.child("state").getValue().equals("0")){
                        holder.switch1.setBackgroundResource(R.drawable.offbutton);
                        holder.switch1.setChecked(false);
                        //state=0;
                    }else{
                        holder.switch1.setBackgroundResource(R.drawable.onbutton);
                        holder.switch1.setChecked(true);
                        //state=1;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("nsmart","clicked "+currentItem.getmDeviceId().toString());

                if (currentItem.getmDeviceId().startsWith("S!-RGB")){
                    mcontext.startActivity( new Intent(mcontext, SubMenuList.class).putExtra("Id",currentItem.getmDeviceId().toString().trim()));
                }else if(currentItem.getmDeviceId().startsWith("S!-BLB")){
                    mcontext.startActivity( new Intent(mcontext, BPSubMenu.class).putExtra("Id",currentItem.getmDeviceId().toString().trim()));
                }else if (currentItem.getmDeviceId().startsWith("S!-PLG")){
                    mcontext.startActivity( new Intent(mcontext, BPSubMenu.class).putExtra("Id",currentItem.getmDeviceId().toString().trim()));
                }else if (currentItem.getmDeviceId().startsWith("S!-GAS")){
                    mcontext.startActivity( new Intent(mcontext, GasController.class).putExtra("Id",currentItem.getmDeviceId().toString().trim()));
                }

            }
        });

        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Log.d("nsmart","on "+currentItem.getmDeviceId().toString());
                    compoundButton.setBackgroundResource(R.drawable.onbutton);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(currentItem.getmDeviceId()).child("state").setValue("1");
                }else {
                    Log.d("nsmart","off "+currentItem.getmDeviceId().toString());
                    compoundButton.setBackgroundResource(R.drawable.offbutton);
                    FirebaseDatabase.getInstance().getReference().child("Device").child(currentItem.getmDeviceId()).child("state").setValue("0");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<SingleDevice> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mExamplelistwithSearch);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (SingleDevice device : mExamplelistwithSearch){
                    if (device.getname().toLowerCase().contains(filterPattern)){
                        filteredList.add(device);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mExampleList.clear();
            mExampleList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

}
