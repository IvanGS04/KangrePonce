package com.example.kangreponce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Index extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    PostsAdapter adapter;
    List<ComidaClass> postsList = new ArrayList<>();

    Toolbar toolbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostsAdapter(postsList);
        recyclerView.setAdapter(adapter);





        fetchPosts();

    }//On create

    private void  fetchPosts(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("http://192.168.137.225:3000/")
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
                Toast.makeText(Index.this, "ERROR"+ t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registro_comida) {
            VerRegistroComida();
            return false;

        } else if (item.getItemId() == R.id.ver_menu_principal) {

            VerMenu();
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    void VerRegistroComida(){
        Intent intent = new Intent(Index.this,RegistroComida.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    void VerMenu(){
        Intent intent = new Intent(Index.this,Index.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}//class