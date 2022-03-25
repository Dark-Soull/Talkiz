package com.darksoul.chatapp.Models;

import com.google.gson.annotations.SerializedName;

public class SearchModel {
    @SerializedName("name")
    private String name;

    @SerializedName("uid")
    private String userID;

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }
}
