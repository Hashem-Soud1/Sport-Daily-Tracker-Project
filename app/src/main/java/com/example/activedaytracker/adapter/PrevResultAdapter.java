package com.example.activedaytracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.MAC;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class PrevResultAdapter extends RecyclerView.Adapter<PrevResultAdapter.ActivityViewHolder> {


    List<MAC> macList;
    Context context;




    public PrevResultAdapter(List<MAC> macList, Context context) {
        this.macList = macList;
        this.context = context;

    }

    public  void newfilterlist(List<MAC> newmacList)
    {
        this.macList=newmacList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.prev_results,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
      MAC mac= macList.get(position);

      holder.dur.setText(mac.getDuration());
      holder.name.setText(mac.getName());
      holder.date.setText(mac.getDate());

    }

    @Override
    public int getItemCount() {
        return macList.size();
    }


    class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView name,date,dur;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.namep);
            dur=itemView.findViewById(R.id.durp);
            date=itemView.findViewById(R.id.datep);
        }
    }
}
