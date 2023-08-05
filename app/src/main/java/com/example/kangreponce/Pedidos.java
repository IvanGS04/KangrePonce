package com.example.kangreponce;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Pedidos {

    @POST("pedidos")
    Call<PedidosClass> createPedido(
            @Body PedidosClass pedidosClass
    );
}
