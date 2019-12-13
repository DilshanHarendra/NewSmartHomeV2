package com.nmadcreations.newsmarthomev2;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

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


    public  static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mremoveDevice;
        public ToggleButton switch1;
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
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getMtext2());

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("nsmart","clicked "+currentItem.getmText1().toString());
            }
        });

        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(mcontext, "fefefefe", Toast.LENGTH_SHORT).show();
                    Log.d("nsmart","on "+currentItem.getmDeviceId().toString());
                    compoundButton.setBackgroundResource(R.drawable.onbutton);
                }else {
                    Log.d("nsmart","off "+currentItem.getmDeviceId().toString());
                    compoundButton.setBackgroundResource(R.drawable.offbutton);
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
                    if (device.getMtext2().toLowerCase().contains(filterPattern)){
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
