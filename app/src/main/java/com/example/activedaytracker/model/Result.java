package com.example.activedaytracker.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;

    public Result(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }



    public Result(Boolean error, String message){
        this.error = error;
        this.message = message;
    }
    public Result(Boolean error){
        this.error = error;

    }




    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}

