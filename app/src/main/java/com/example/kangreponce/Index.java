package com.example.kangreponce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    SharedPreferences sharedPreferences;

    Toolbar toolbar;

    Button buttonAgregar;





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
        buttonAgregar = findViewById(R.id.btnAgregar);
        fetchPosts();

    }//On create

    public boolean checkAdmin(){
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        boolean admin = sharedPreferences.getBoolean("admin", false);
        return admin;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean check = checkAdmin();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if(check != true){
            MenuItem registroComidaItem = menu.findItem(R.id.registro_comida);
            registroComidaItem.setVisible(false);
           /* MenuItem registroUserItem = menu.findItem(R.id.ver_pedidos);
            registroUserItem.setVisible(false);*/
            MenuItem crudItem = menu.findItem(R.id.admin_comida);
            crudItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registro_comida) {
            VerRegistroComida();
            return false;
        } else if (item.getItemId() == R.id.ver_menu_principal) {
            VerMenu();
            return false;
        } else if (item.getItemId() == R.id.admin_comida) {
            VerAdminComida();
            return false;
        }
        else if (item.getItemId() == R.id.cerrar_sesion) {
            eraseSession();
            logOut();
            return false;
        }
        else if (item.getItemId() == R.id.ver_pedidos) {
            showPed();
            return false;
        }
        else {
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

    void VerAdminComida(){
        Intent intent = new Intent(Index.this,AdminComida.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void showPed(){
        Intent intent = new Intent(Index.this, PedidosView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    void eraseSession(){
        sharedPreferences.edit().clear().apply();
    }

    void logOut(){
        Intent intent = new Intent(Index.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void  fetchPosts(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("http://10.20.39.219:3000/")
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
    }//fethPosts
}//class