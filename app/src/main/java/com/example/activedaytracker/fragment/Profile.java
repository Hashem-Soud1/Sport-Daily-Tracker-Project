package com.example.activedaytracker.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.Result;
import com.example.activedaytracker.model.User;
import com.example.activedaytracker.shpref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {


    TextView name,phone,email,pass,title,logout;
    Button edit,del;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.pofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        name=getView().findViewById(R.id.profile_name);

        pass=getView().findViewById(R.id.profile_pass);
        title=getView().findViewById(R.id.profile_titl_name);
        edit=getView().findViewById(R.id.edit);
        del=getView().findViewById(R.id.delete);
        logout=getView().findViewById(R.id.logout);
        User user= SharedPrefManager.getInstance(getContext()).getUsers();

        name.setText(user.getName());
        pass.setText(user.getPassword());
        title.setText(user.getName());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure ?");

                builder.setPositiveButton("Yes",(dialog, which) -> {

                    getActivity().finish();
                    SharedPrefManager.getInstance(getContext()).logout();

                });
                builder.setNegativeButton("NO",(dialog, which) -> {
                    dialog.dismiss();
                });

                builder.show();

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());

              // text operation
                String title = "Warning: Your account and all your activities will be deleted";
                SpannableStringBuilder spannableTitle = new SpannableStringBuilder(title);
                spannableTitle.setSpan(new RelativeSizeSpan(1f), 0, title.length(), 0);

                builder.setTitle(spannableTitle);
                builder.setIcon(R.drawable.ic_info);

                builder.setPositiveButton("Yes",(dialog, which) -> {

                    Call<Result> call= RestApiConnection.getInstance().getMyApi().deleteAc((int)user.getId());
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {

                            if(response.body().getError()==false) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                SharedPrefManager.getInstance(getContext()).logout();

                            }
                            else  {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.d("eeeee",t.getMessage());
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


    }
    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edite_profile, null);
        builder.setView(dialogView);

        // Initialize EditTexts
        EditText editName = dialogView.findViewById(R.id.editName);

        EditText editPassword = dialogView.findViewById(R.id.editPassword);
        EditText editTitle = dialogView.findViewById(R.id.editName);

        // Set initial values from user data
        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        editName.setText(user.getName());

        editPassword.setText(user.getPassword());


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle save button click

                // Retrieve edited values from EditTexts
                String newName = editName.getText().toString().trim();
                String newPassword = editPassword.getText().toString().trim();
                String newTitle = editName.getText().toString().trim();


                Call<Result> call= RestApiConnection.getInstance().getMyApi().updateUser(user.getId(),newName,newPassword);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.body().getError()==false) {
                            // Update user object or save to SharedPreferences as needed
                            User updatedUser = new User(user.getId(), newName, newPassword);
                            SharedPrefManager.getInstance(getContext()).userUpdate(updatedUser);

                            // Update TextViews in the fragment with the new values
                            name.setText(newName);

                            pass.setText(newPassword);
                            title.setText(newTitle);
                            Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), " Not Saved successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}