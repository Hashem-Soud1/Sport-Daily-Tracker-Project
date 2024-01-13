package com.example.activedaytracker.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.databinding.SignInBinding;
import com.example.activedaytracker.model.Result;
import com.example.activedaytracker.model.User;
import com.example.activedaytracker.shpref.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    SignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        TextView textView=findViewById(R.id.NoAccountHint);

        binding.NoAccountHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userLogin();
            }
        });
    }

    private void userLogin() {
        String name = binding.nameLogin.getEditText().getText().toString().trim();
        String password = binding.passwordLogin.getEditText().getText().toString().trim();

        Call<Result> call = RestApiConnection.getInstance().getMyApi().checkUser(name, password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getError() == false) {

                    User user = new User((int) response.body().getUser().getId(), response.body().getUser().getName(), response.body().getUser().getPassword());
                    Toast.makeText(getApplicationContext(), "Welcom " + user.getName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                    finish();
                } else {
                    Toast.makeText(SignIn.this, "phone or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                 Log.d("Retrofit ERROR -->", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
