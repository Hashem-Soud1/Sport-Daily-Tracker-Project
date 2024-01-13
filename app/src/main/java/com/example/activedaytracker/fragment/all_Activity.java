package com.example.activedaytracker.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.activedaytracker.R;
import com.example.activedaytracker.adapter.ActivityAdapter;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.databinding.AllAcBinding;
import com.example.activedaytracker.model.MAC;
import com.example.activedaytracker.model.Result;
import com.example.activedaytracker.shpref.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class all_Activity extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.rec_all  , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
           recyclerView=view.findViewById(R.id.rec);

           //toolbar
        Toolbar toolbar=view.findViewById(R.id.toolbar_all);
        toolbar.setVisibility(View.VISIBLE);


        Call<List<MAC>> call= RestApiConnection.getInstance().getMyApi().getAllAc();
        call.enqueue(new Callback<List<MAC>>() {
            @Override
            public void onResponse(Call<List<MAC>> call, Response<List<MAC>> response) {
             List<MAC> maclist=response.body();

                ActivityAdapter activityAdapter=new ActivityAdapter(maclist, requireContext(), new ActivityAdapter.ItemClickL() {

                    public void onItemClick(MAC mac) {
                        addActivity(mac);
                    }
                });
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(activityAdapter);

            }

            @Override
            public void onFailure(Call<List<MAC>> call, Throwable t) {
                Log.d("************",t.getMessage());
            }
        });


    }

    public  void addActivity(MAC mac){

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure you want to add \"" + mac.getName() + "\" to your daily activities?");

 builder.setPositiveButton("Yes",(dialog, which) -> {

//date

     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
     String currentDate = dateFormat.format(new Date());

//id
     int userid= SharedPrefManager.getInstance(getContext()).getUsers().getId();

     Call<Result> call=RestApiConnection.getInstance().getMyApi().insertActivity(userid ,mac.getId(),currentDate);
      call.enqueue(new Callback<Result>() {
          @Override
          public void onResponse(Call<Result> call, Response<Result> response) {
           if(response.body().getError()==false) {

               Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
           }
           else
               Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onFailure(Call<Result> call, Throwable t) {
              Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
              Log.d("****",t.getMessage());

          }
      });

});


 builder.setNegativeButton("No",(dialog, which) -> {
     dialog.dismiss();

 });
 builder.show();


    }


}