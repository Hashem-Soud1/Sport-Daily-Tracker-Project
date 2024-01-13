package com.example.activedaytracker.activties;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.activedaytracker.R;
import com.example.activedaytracker.api.RestApiConnection;
import com.example.activedaytracker.model.MAC;
import com.example.activedaytracker.model.Result;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimerActivity extends AppCompatActivity {

    private Button startPauseButton,finishbtn;
    private TextView timerbtn;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timerDuration ;
    private long timeRemaining;
int aid,uid;
Toolbar toolbar;
    GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startPauseButton = findViewById(R.id.startPauseButton);
        finishbtn = findViewById(R.id.finish);
        timerbtn = findViewById(R.id.timerbtn);
        gif=findViewById(R.id.gif_timer);
        toolbar=findViewById(R.id.tool);

        Intent intent=getIntent();
        String dur=intent.getStringExtra("dur");
        String img=intent.getStringExtra("img");

         String date=intent.getStringExtra("date");
         aid =intent.getIntExtra("ida",-1);
         uid=intent.getIntExtra("idu",-2);

        // Regex لاستخراج الأرقام
        String regex = "\\d+";
        // إعداد المطابقة
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dur);

        if (matcher.find()) {
           dur = matcher.group();
        }

        //
     timerDuration = Integer.parseInt(dur)  * 60 * 1000 ;

//
        Glide.with(getApplicationContext())
                        .load(RestApiConnection.photoUrl +img)
                        .apply(new RequestOptions().override(400,400))
                                .into(gif);

        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =new AlertDialog.Builder(TimerActivity.this);
                builder.setTitle("Did you complete the activity ? ");

                builder.setPositiveButton("Yes", (dialog, which) -> {



                    Call<Result> call1=RestApiConnection.getInstance().getMyApi().insertdone(uid,aid);
                    call1.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                           // Toast.makeText(TimerActivity.this, " Well done, you completed the activity successfully ", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG);

                        }
                    });

                    Call<Result> call2=RestApiConnection.getInstance().getMyApi().insertPrevRes(uid,aid,date);
                    call2.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if(response.body().getError()==false)
                            Toast.makeText(TimerActivity.this, "good job", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(TimerActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                });

                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();

            }
        });

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });



    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeRemaining > 0 ? timeRemaining : timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerDisplay();
            }

            @Override
            public void onFinish() {
                timerbtn.setText("finish");
                isTimerRunning = false;
                startPauseButton.setText("start");
            }
        };

        countDownTimer.start();
        isTimerRunning = true;
        startPauseButton.setText("stop");

        // Remove log to keep the code clean
        // Log.d("TimerActivity", "startTimer executed");
    }

    private void updateTimerDisplay() {
        long totalSecondsRemaining = timeRemaining / 1000;
        long minutesRemaining = totalSecondsRemaining / 60;
        long secondsInMinuteRemaining = totalSecondsRemaining % 60;

        timerbtn.setText(minutesRemaining + " : " + secondsInMinuteRemaining);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        startPauseButton.setText("pause");

        // Remove log to keep the code clean
        // Log.d("TimerActivity", "pauseTimer executed");
    }

    private void resetTimer() {
        // Reset variables to default or required values
        timeRemaining = 0;
        // Stop the timer if it's running
        if (isTimerRunning) {
            pauseTimer();
        }

        // Remove log to keep the code clean
        // Log.d("TimerActivity", "resetTimer executed");
    }

    @Override
    protected void onStop() {
        super.onStop();

        resetTimer();


    }
}
