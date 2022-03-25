package com.darksoul.chatapp.Network.Api;

import com.darksoul.chatapp.Models.SearchModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserSearchApi {

    @POST("/emailSearch/")
    Call<SearchModel> emailSearch(@Body HashMap<String,String> map);

    @POST("/phoneSearch/")
    Call<SearchModel> phoneSearch(@Body HashMap<String,String>map);
}
