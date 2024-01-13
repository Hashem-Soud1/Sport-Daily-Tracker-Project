package com.example.activedaytracker.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.databinding.SignUpBinding;
import com.example.activedaytracker.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    SignUpBinding binding;

    public String name,pass,pass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
            }
        });


        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertUser();
            }
        });
    }

    private void insertUser() {
        name = binding.userSignUp.getEditText().getText().toString().trim();
        pass = binding.passwordSignUp.getEditText().getText().toString().trim();
        pass2 = binding.passwordSignUp2.getEditText().getText().toString().trim();

        if (name.isEmpty()||pass.isEmpty())
            Toast.makeText(this, "You must enter Username and password", Toast.LENGTH_SHORT).show();

        else if (!pass.equals(pass2)) {

            binding.passwordSignUp2.setError("Passwords do not match");
        }
        else {
            binding.passwordSignUp2.setError(null);
            Call<Result> call = RestApiConnection.getInstance().getMyApi().insertUsers(name, pass);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {

                    if (response.body().getError() == false) {
                        Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(SignUp.this, "A user already exists", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "Failed to Insert Data --> " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}