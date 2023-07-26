package com.example.kangreponce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KangrePonce extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kangre_ponce);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.203:4000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Vehiculos api = retrofit.create(Vehiculos.class);

        Call<VehiculosClass> vehuculosObject = api.getVehiculoByMarca("Marca");

        vehuculosObject.enqueue(new Callback<VehiculosClass>() {
            @Override
            public void onResponse(Call<VehiculosClass> call, Response<VehiculosClass> response) {
                VehiculosClass vehiculo = (VehiculosClass) response.body();
            }

            @Override
            public void onFailure(Call<VehiculosClass> call, Throwable t) {
                Toast.makeText(KangrePonce.this,"Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registro_comida) {
            verRegistroComida();
            return false;
        } if (item.getItemId() == R.id.ver_menu_principal){
            VolverAlMenu();
            return false;
        }if (item.getItemId() == R.id.registro_usuarios){
            VerRegistroUsuarios();
            return false;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }

    void VolverAlMenu(){
        Intent intent = new Intent(KangrePonce.this, KangrePonce.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void VerRegistroUsuarios(){
        Intent intent = new Intent(KangrePonce.this,RegistroUsuarios.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    void verRegistroComida(){
        Intent intent = new Intent(KangrePonce.this,RegistroComida.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}