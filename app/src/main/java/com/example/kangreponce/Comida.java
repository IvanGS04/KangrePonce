package com.example.kangreponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Comida {

    @GET("comida/")
    Call<List<ComidaClass>> getPosts();

    @POST("comida/")
    Call<PostComida> createComida(
            @Field("nombre") String nombre,
            @Field("ingredientes") String ingredientes,
            @Field("precio") int precio,
            @Field("file") String file);
}
