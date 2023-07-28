package com.example.kangreponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Vehiculos {

@GET("Marca/{Marca}")
    Call<VehiculosClass> GetID(@Path("Marca") String marca);
}
