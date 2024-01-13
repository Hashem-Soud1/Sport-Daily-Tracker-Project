package com.example.activedaytracker.model;

import com.google.gson.annotations.SerializedName;

public class MAC {
    @SerializedName("id")
           int id;
    @SerializedName("name")
            String name;
    @SerializedName("OptimalT")
            String OptimalT;
    @SerializedName("Duration")
            String Duration;
    @SerializedName("imag")
            String imag;

    @SerializedName("date")
    String date;

    @SerializedName("done")
    int done;

    public int getDone() {
        return done;
    }

    public MAC(int id, String name, String optimalT, String duration, String imag) {
        this.id = id;
        this.name = name;
        OptimalT = optimalT;
        Duration = duration;
        this.imag = imag;
    }
    public int getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }

    public String getOptimalT() {
        return OptimalT;
    }

    public String getDuration() {
        return Duration;
    }

    public String getImag() {
        return imag;
    }


}
