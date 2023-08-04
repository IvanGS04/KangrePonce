package com.example.kangreponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Login {
    @FormUrlEncoded
    @POST("usuarios/login")
    Call<LoginResponse> loginUser(@Field("Correo") String Correo, @Field("Password") String Password);
}
