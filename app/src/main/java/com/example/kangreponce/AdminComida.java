package com.example.kangreponce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminComida extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    PostAdapterAdmin adapter;
    List<ComidaClass> postsList = new ArrayList<>();

    Button btnEliminar;

    TextView txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_comida);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar4);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapterAdmin(postsList);
        recyclerView.setAdapter(adapter);

        txtNombre = findViewById(R.id.nombre);





        fetchPosts();








    }



    private void  fetchPosts(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new  Retrofit.Builder()

                .baseUrl("http://192.168.0.21:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Comida api = retrofit.create(Comida.class);
        Call<List<ComidaClass>> comidaClassCall = api.getPosts();

        comidaClassCall.request().url().toString();

        comidaClassCall.enqueue(new Callback<List<ComidaClass>>() {
            @Override
            public void onResponse(Call<List<ComidaClass>> call, Response<List<ComidaClass>> response) {
                if(response.isSuccessful() && response.body() != null){
                    postsList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<ComidaClass>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AdminComida.this, "ERROR"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*RetrofitClient.getRetrofitClient().getPosts().enqueue(new Callback<List<ComidaClass>>() {
            ComidaClass.request().url().toString();
            @Override
            public void onResponse(Call<List<ComidaClass>> call, Response<List<ComidaClass>> response) {
                if(response.isSuccessful() && response.body() != null){
                    postsList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<List<ComidaClass>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Index.this, "ERROR"+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });//Client
        */

    }//fethPosts


}