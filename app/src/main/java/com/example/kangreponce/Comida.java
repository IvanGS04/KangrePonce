package com.example.kangreponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Comida {

    @GET("comida/")
    Call<List<ComidaClass>> getPosts();

    @POST("comida")
    Call<ComidaPOST> RegistrarComida(@Body  ComidaPOST comidaPOST);

    @DELETE("comida/{id}")
    Call<Void> eliminarRegistro(@Path("id") String id);




}
