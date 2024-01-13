package com.example.activedaytracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.MAC;

import java.util.List;

import javax.crypto.Mac;

import pl.droidsonroids.gif.GifImageView;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {


    List<MAC> macList;
    Context context;

    ItemClickL itemClickL;


    public ActivityAdapter(List<MAC> macList, Context context, ItemClickL itemClickL) {
        this.macList = macList;
        this.context = context;
        this.itemClickL = itemClickL;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.all_ac,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
      MAC mac= macList.get(position);

      holder.opt.setText(mac.getOptimalT());
      holder.dur.setText(mac.getDuration());
      holder.name.setText(mac.getName());

        Glide.with(context)
                .load(RestApiConnection.photoUrl +mac.getImag())
                .apply(new RequestOptions().override(400,400))
                .error(R.drawable.ic_favorite)
                .into(holder.gif);

        holder.add.setOnClickListener(v -> {
            itemClickL.onItemClick(mac);
        });

    }

    @Override
    public int getItemCount() {
        return macList.size();
    }


    public  interface ItemClickL{
      public  void onItemClick(MAC mac);
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {

        GifImageView gif;
        TextView name,opt,dur;

        Button add;
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            gif=itemView.findViewById(R.id.gif);
            name=itemView.findViewById(R.id.name);
            opt=itemView.findViewById(R.id.optimal_timing);
            dur=itemView.findViewById(R.id.time);
            add=itemView.findViewById(R.id.add);
        }
    }
}
