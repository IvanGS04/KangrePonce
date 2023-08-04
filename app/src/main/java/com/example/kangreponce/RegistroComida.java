package com.example.kangreponce;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;


import android.widget.ImageView;
import android.widget.Toast;


import android.widget.EditText;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroComida extends AppCompatActivity {

    ImageView imageView;
    Button btnUpload, btnRegistrar;
    Toolbar toolbar;
    ActivityResultLauncher<Intent> resultLauncher;
    EditText TXTnombre, TXTingredientes, TXTprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comida);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageUpload);
        btnUpload = findViewById(R.id.btnUpload);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        TXTnombre = findViewById(R.id.txtnombreP);
        TXTingredientes = findViewById(R.id.txtIngredientes);
        TXTprecio = findViewById(R.id.txtPrecio);
        registerResult();

        btnUpload.setOnClickListener( view -> pickImage());
        btnRegistrar.setOnClickListener(view -> validarComida() );


    }//onCreate

        private void validarComida () {
            String nombre = TXTnombre.getText().toString();
            String ingredientes = TXTingredientes.getText().toString();
            int precio = Integer.parseInt(TXTprecio.getText().toString());

            if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(ingredientes) || Integer.toString(precio).isEmpty() ) {
                Toast.makeText(RegistroComida.this, "Rellena todos los campos vacios ", Toast.LENGTH_SHORT).show();
                return;
            }
            registrarComida(nombre, ingredientes, precio);
        }

    private void registrarComida(String nombre, String ingredientes, int precio){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.21:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Comida comida = retrofit.create(Comida.class);

        Call<ComidaPOST> call = comida.RegistrarComida(new ComidaPOST(nombre, ingredientes, precio));

        call.enqueue(new Callback<ComidaPOST>() {
            @Override
            public void onResponse(Call<ComidaPOST> call, Response<ComidaPOST> response) {
                if (response.isSuccessful()) {
                    ComidaPOST comidaPOST = response.body();
                    Toast.makeText(RegistroComida.this, "Platillo registrado exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroComida.this, Index.class));
                }else{
                    Toast.makeText(RegistroComida.this, "el platillo no se pudo registrar", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ComidaPOST> call, Throwable t) {
                Toast.makeText(RegistroComida.this, "error en el servidor", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(RegistroComida.this, RegistroComida.class);
            startActivity(intent);
            return false;
        } else if (item.getItemId() == R.id.ver_menu_principal) {
            Intent intent = new Intent(RegistroComida.this,Index.class);
            startActivity(intent);
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            imageView.setImageURI(imageUri);
                        }catch (Exception e){
                            Toast.makeText(RegistroComida.this, "Imagen no seleccionada", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}//Class