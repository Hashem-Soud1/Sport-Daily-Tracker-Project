package com.example.activedaytracker.shpref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.activedaytracker.activties.SignIn;
import com.example.activedaytracker.model.User;

public class SharedPrefManager {
    private static final String SharedPref_Name = "StoreSharedPref";
    private static final String Key_Id = "a";
    private static final String Key_Username = "b";
    private static final String Key_Password = "d";
    private static final String Key_date = "h";
    private static SharedPrefManager mInstance;

    private static Context mCtx;

    private SharedPrefManager(Context context) { mCtx = context; }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedPref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key_Id, user.getId());
        editor.putString(Key_Username, user.getName());
        editor.putString(Key_Password, user.getPassword());

        editor.apply();
    }

    public void userUpdate(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedPref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key_Id, user.getId());
        editor.putString(Key_Username, user.getName());
        editor.putString(Key_Password, user.getPassword());

        editor.apply();
    }
    public User getUsers(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SharedPref_Name,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(Key_Id,0),
                sharedPreferences.getString(Key_Username,null),
                sharedPreferences.getString(Key_Password,null)

        );
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SharedPref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_Username,null)!=null;
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedPref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent i = new Intent(mCtx, SignIn.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(i);
    }

    public  void updatedate(String newdate){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedPref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Key_date",newdate);
        editor.apply();

    }

    public String  getDate(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedPref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString("Key_date",null);
    }



}
