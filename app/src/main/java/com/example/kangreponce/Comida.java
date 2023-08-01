package com.example.kangreponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Comida {

    @GET("comida/")
    Call<List<ComidaClass>> getPosts();

}
