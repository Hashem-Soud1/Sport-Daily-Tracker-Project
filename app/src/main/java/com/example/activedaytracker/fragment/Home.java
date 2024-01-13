package com.example.activedaytracker.fragment;



import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activedaytracker.R;
import com.example.activedaytracker.activties.TimerActivity;
import com.example.activedaytracker.adapter.ActivityAdapter;
import com.example.activedaytracker.adapter.myActivityAdapter;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.MAC;
import com.example.activedaytracker.model.Result;
import com.example.activedaytracker.shpref.SharedPrefManager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    RecyclerView recyclerView;
    SearchView searchView;

    List<MAC> macList;
    myActivityAdapter myActivityAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rec_all,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=view.findViewById(R.id.rec);


        // Toolbar
        Toolbar toolbar=view.findViewById(R.id.toolbar_my);
        toolbar.setVisibility(View.VISIBLE);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.about) {

                    Fragment fragment = new infoFr();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
                else if (item.getItemId()==R.id.search) {

                    searchView.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);

                } else if (item.getItemId()==R.id.filter) {

                    Collections.reverse(macList);
                    myActivityAdapter.newfilterlist(macList);
                }

                return  true;
            }
        });


        // searchView
        searchView=view.findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFiltrList(newText);
                return false;
            }
        });




        int userId= SharedPrefManager.getInstance(getContext()).getUsers().getId();

        Call<List<MAC>> call = RestApiConnection.getInstance().getMyApi().getMyAc(userId);

        call.enqueue(new Callback<List<MAC>>() {
            @Override
            public void onResponse(Call<List<MAC>> call, Response<List<MAC>> response) {
                macList=response.body();

                myActivityAdapter=new myActivityAdapter(macList, requireContext(), new myActivityAdapter.ItemClickL() {
                    @Override
                    public void onItemClickstart(MAC mac) {

                        Intent intent =new Intent(getContext(), TimerActivity.class);

                        intent.putExtra("dur",mac.getDuration());
                        intent.putExtra("img",mac.getImag());
                        intent.putExtra("ida",mac.getId());
                        intent.putExtra("date",mac.getDate());
                        intent.putExtra("idu",userId);
                        startActivity(intent);

                    }

                    @Override
                    public void onItemClikedelete(int pos,MAC mac) {
                        int userId= SharedPrefManager.getInstance(getContext()).getUsers().getId();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Are you sure you have deleted your \"" + mac.getName() + "\" activity?");

                        builder.setPositiveButton("Yes ", (dialog, which) -> {

                            Call<Result> call = RestApiConnection.getInstance().getMyApi().deleteAct(userId, mac.getId());
                            call.enqueue(new Callback<Result>() {
                                @Override
                                public void onResponse(Call<Result> call, Response<Result> response) {
                                    if (response.body().getError() == false) {

                                        macList.remove(pos);
                                        myActivityAdapter.newfilterlist(macList);
                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onFailure(Call<Result> call, Throwable t) {
                                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        });

                        builder.setNegativeButton("No",(dialog, which) -> {
                            dialog.dismiss();
                        });
                        builder.show();

                    }


                });

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(myActivityAdapter);

            }

            @Override
            public void onFailure(Call<List<MAC>> call, Throwable t) {
                Log.d("***********",t.getMessage());
            }
        });











    }

    private void searchFiltrList(String newText) {

        List<MAC> filterdlist =new ArrayList<>();

        for(MAC mac : macList)
            if (mac.getDate().toLowerCase().contains(newText.toLowerCase())||mac.getName().toLowerCase().contains(newText.toLowerCase()))
                filterdlist.add(mac);


        myActivityAdapter.newfilterlist(filterdlist);

    }

    //




}
