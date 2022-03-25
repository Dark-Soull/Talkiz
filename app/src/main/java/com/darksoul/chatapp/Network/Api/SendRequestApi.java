package com.darksoul.chatapp.Network.Api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendRequestApi {

    @POST("/request/")
    Call<String> sendRequest(@Body HashMap<String,String> map);
}
