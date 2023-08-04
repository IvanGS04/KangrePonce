package com.example.kangreponce;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Usuarios {


    @POST("usuarios")
    Call<RegistrosResponse> createUsuario(
           @Body RegistrosResponse registrosResponse
    );
}
