package com.example.activedaytracker.api;

import android.util.Log;
import android.widget.Toast;

import com.example.activedaytracker.model.MAC;
import com.example.activedaytracker.model.Result;

import java.util.List;

import javax.crypto.Mac;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @FormUrlEncoded
    @POST("signup.php")
    Call<Result>  insertUsers(
            @Field("name") String name,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("login.php")
    Call<Result> checkUser(
            @Field("name") String phone,
            @Field("password") String password
    );

    @GET("getAllAc.php")
    Call<List<MAC>> getAllAc();


    @GET("insertActivity.php")
    Call<Result> insertActivity(
            @Query("userid") int idu,
            @Query("activityid") int ida,
            @Query("date") String date
    );

    @GET("getMyAc.php")
    Call<List<MAC>>getMyAc(
            @Query("user_id") int id
    );


    @GET("deleteAct.php")
    Call<Result> deleteAct(
            @Query("userid") int idu,
            @Query("activityid") int ida

    );

    @FormUrlEncoded
    @POST("UpdateUser.php")
    Call<Result> updateUser(
            @Field("id") int id,
            @Field("name") String name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("deleteAc.php")
    Call<Result> deleteAc (
            @Field("userId") int id

    );
@FormUrlEncoded
    @POST("insertdone.php")
    Call<Result> insertdone(
        @Field("uid") int idu,
        @Field("aid") int ida
    );

   @GET("restAc.php")
    Call<Result> restAc();
    @FormUrlEncoded
    @POST("insertPrevRes.php")
    Call<Result>insertPrevRes(
            @Field("uid") int idu,
            @Field("aid") int ida,
            @Field("date") String date
    );

    @GET("getPrevRes.php")
    Call<List<MAC>>getPrevRes(
            @Query("user_id") int id
    );



}
