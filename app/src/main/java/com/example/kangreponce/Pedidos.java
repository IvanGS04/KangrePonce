package com.example.kangreponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Pedidos {

    @POST("pedidos")
    Call<PedidosClass> createPedido(
            @Body PedidosClass pedidosClass
    );
    @GET("pedidos/")
    Call<List<PedidosClass>> getPedidos();

    @GET("pedidos/filtro/{correo}")
    Call<List<PedidosClass>> getPedidosUser(@Path("correo") String correo);
}
