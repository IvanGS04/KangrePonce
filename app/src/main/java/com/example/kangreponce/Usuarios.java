package com.example.kangreponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Usuarios {


    @POST("usuarios")
    Call<RegistrosResponse> createUsuario(
           @Body RegistrosResponse registrosResponse
    );
}
