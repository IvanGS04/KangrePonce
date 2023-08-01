package com.example.kangreponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Vehiculos {

@GET("{api}")
    Call<VehiculosClass> GetAPI(@Path("api") String api);
}
