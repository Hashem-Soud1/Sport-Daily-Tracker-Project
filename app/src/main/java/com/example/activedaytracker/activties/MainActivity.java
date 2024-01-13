package com.example.activedaytracker.activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.databinding.MainBinding;
import com.example.activedaytracker.fragment.Home;
import com.example.activedaytracker.fragment.Previous_results;
import com.example.activedaytracker.fragment.Profile;
import com.example.activedaytracker.fragment.all_Activity;
import com.example.activedaytracker.model.Result;
import com.example.activedaytracker.shpref.SharedPrefManager;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    MainBinding binding;
    FragmentTransaction fragmentTransaction;
    Boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
//            finish();
//            startActivity(new Intent(getApplicationContext(),SignIn.class));
//        }



        // tody date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());


        String savedate =SharedPrefManager.getInstance(getApplicationContext()).getDate();


        // Delete the done mark on each new day
          if(savedate==null || !savedate.equals(currentDate)){
              SharedPrefManager.getInstance(getApplicationContext()).updatedate(currentDate);
            Call<Result> call= RestApiConnection.getInstance().getMyApi().restAc();
           call.enqueue(new Callback<Result>() {
               @Override
               public void onResponse(Call<Result> call, Response<Result> response) {}

               @Override
               public void onFailure(Call<Result> call, Throwable t) {}
           });
        }



        binding.bottomNav.setBackground(null);

        initialFragment();


      binding.fab.setOnClickListener(v -> {
             fabOperation();
           });


          binding.fabAdd.setOnClickListener(v -> {

          FragmentManager fragmentManager = getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.replace(R.id.container, new all_Activity());
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
      });

        binding.fabRes.setOnClickListener(v -> {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Previous_results());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });



        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              if(item.getItemId()==R.id.home)
              {
                  fragmentTransaction=getSupportFragmentManager().beginTransaction();
                  fragmentTransaction.replace(R.id.container,new Home());
                  fragmentTransaction.addToBackStack(null);
                  fragmentTransaction.commit();
              }

              if(item.getItemId()==R.id.profile)
              {
                  fragmentTransaction=getSupportFragmentManager().beginTransaction();
                  fragmentTransaction.replace(R.id.container,new Profile());
                  fragmentTransaction.addToBackStack(null);
                  fragmentTransaction.commit();
              }
              return  true;
          }
      });

    }





    private void initialFragment(){
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new Home());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public  void fabOperation(){



        if(flag)
        {
            binding.fabAdd.setVisibility(View.VISIBLE);
            binding.fabRes.setVisibility(View.VISIBLE);

            binding.txAdd.setVisibility(View.VISIBLE);
            binding.txRes.setVisibility(View.VISIBLE);

            binding.fab.setImageResource(R.drawable.baseline_cancel_24);


            flag=false;
        }
        else {
            binding.fabAdd.setVisibility(View.GONE);
            binding.fabRes.setVisibility(View.GONE);

            binding.txAdd.setVisibility(View.GONE);
            binding.txRes.setVisibility(View.GONE);

            binding.fab.setImageResource(R.drawable.baseline_add_24);

            flag=true;

        }

    }



}