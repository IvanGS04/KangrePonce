package com.example.kangreponce;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Comida {

    @GET("comida/")
    Call<List<ComidaClass>> getPosts();

    @Multipart
    @POST("comida/")
    Call<ComidaPOST> addDish(@Part MultipartBody.Part file,
                                     @Part("nombre") RequestBody nombre,
                                     @Part("ingredientes") RequestBody ingredientes,
                                     @Part("precio") RequestBody precio);





}
