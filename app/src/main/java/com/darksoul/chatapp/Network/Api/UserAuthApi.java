package com.darksoul.chatapp.Network.Api;


import com.darksoul.chatapp.Models.UserModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAuthApi {
    @POST("/register/")
    Call<String> registerUser(@Body HashMap<String,String>map);

    @POST("/login/")
    Call<UserModel> loginUser(@Body HashMap<String,String>map);
}
