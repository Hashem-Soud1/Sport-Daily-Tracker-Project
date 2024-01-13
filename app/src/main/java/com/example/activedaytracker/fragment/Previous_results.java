package com.example.activedaytracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.activedaytracker.adapter.PrevResultAdapter;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.MAC;
import com.example.activedaytracker.shpref.SharedPrefManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Previous_results  extends Fragment {
RecyclerView recyclerView;
    SearchView searchView;
    List<MAC> macList;
    PrevResultAdapter prevResultAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.rec_all,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView=view.findViewById(R.id.rec);


        // Toolbar
        Toolbar toolbar=view.findViewById(R.id.toolbar_prev);
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
                    prevResultAdapter.newfilterlist(macList);
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

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });


        int userid= SharedPrefManager.getInstance(getContext()).getUsers().getId();

        Call<List<MAC>> call= RestApiConnection.getInstance().getMyApi().getPrevRes(userid);
        call .enqueue(new Callback<List<MAC>>() {
            @Override
            public void onResponse(Call<List<MAC>> call, Response<List<MAC>> response) {
       macList=response.body();

                prevResultAdapter=new PrevResultAdapter(macList,getContext());

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(prevResultAdapter);
                prevResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MAC>> call, Throwable t) {

            }
        });

    }
    private void searchFiltrList(String newText) {

        List<MAC> filterdlist =new ArrayList<>();

        for(MAC mac : macList)
            if (mac.getDate().toLowerCase().contains(newText.toLowerCase())||mac.getName().toLowerCase().contains(newText.toLowerCase()))
                filterdlist.add(mac);


            prevResultAdapter.newfilterlist(filterdlist);



    }
}
