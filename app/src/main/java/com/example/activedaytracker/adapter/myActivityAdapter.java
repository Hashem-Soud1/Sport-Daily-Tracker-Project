package com.example.activedaytracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.MAC;
import com.example.activedaytracker.model.Result;
import com.example.activedaytracker.shpref.SharedPrefManager;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myActivityAdapter extends RecyclerView.Adapter<myActivityAdapter.myActivityViewHolder> {
    List<MAC> macList;
    Context context;

  ItemClickL itemClickL;
Boolean f= true;


public  void newfilterlist(List<MAC> newmacList)
{
    this.macList=newmacList;
    notifyDataSetChanged();
}

    public myActivityAdapter(List<MAC> macList, Context context, ItemClickL itemClickL) {
        this.macList = macList;
        this.context = context;
        this.itemClickL = itemClickL;
    }

    @NonNull
    @Override
    public myActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.homeac,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myActivityViewHolder holder, int position) {
        MAC mac= macList.get(position);

        holder.opt.setText(mac.getOptimalT());
        holder.dur.setText(mac.getDuration());
        holder.name.setText(mac.getName());
        holder.date.setText(mac.getDate());

        if(mac.getDone()==1)
            holder.done.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(RestApiConnection.photoUrl +mac.getImag())
                .apply(new RequestOptions().override(400,400))
                .error(R.drawable.ic_favorite)
                .into(holder.gif);


        holder.start.setOnClickListener(v -> {
            itemClickL.onItemClickstart(mac);
        });



        holder.delete.setOnClickListener(v -> {
            itemClickL.onItemClikedelete(position,mac);
        });

        holder.arrow.setOnClickListener(v -> {

           if(f) { holder.hidden.setVisibility(View.VISIBLE);  holder.arrow.setImageResource(R.drawable.arrow_up_24);   f=false; }
           else {  holder.hidden.setVisibility(View.GONE);     holder.arrow.setImageResource(R.drawable.ic_bottom_arrow);  f=true;  }

        });

    }

    @Override
    public int getItemCount() {
        return macList.size();
    }


    public  interface ItemClickL{
        public  void onItemClickstart(MAC mac);
        public void onItemClikedelete(int pos,MAC mac);

    }

    class myActivityViewHolder extends RecyclerView.ViewHolder {

        GifImageView gif;
        TextView name,opt,dur,date,done;

        ImageView arrow;
        Button start,delete;
        LinearLayout hidden;
        public myActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            gif=itemView.findViewById(R.id.gif);
            name=itemView.findViewById(R.id.mname);
            opt=itemView.findViewById(R.id.opti);
            dur=itemView.findViewById(R.id.mtime);
            start=itemView.findViewById(R.id.start);
            date=itemView.findViewById(R.id.date);
            arrow=itemView.findViewById(R.id.arrow);
            hidden=itemView.findViewById(R.id.hidden);
            done =itemView.findViewById(R.id.done);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
