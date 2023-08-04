package com.example.kangreponce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroComida extends AppCompatActivity {
    Button btnEnviarImagen;

    Button btnRegistrarComida;
    Toolbar toolbar;

    EditText TXTnombre;
    EditText TXTingredientes;
    EditText TXTprecio;


    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comida);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnEnviarImagen = findViewById(R.id.buttonUploadImage);
        btnRegistrarComida = findViewById(R.id.button3);
        TXTnombre = findViewById(R.id.txtnombreP);
        TXTingredientes = findViewById(R.id.txtIngredientes);
        TXTprecio = findViewById(R.id.txtPrecio);

        btnEnviarImagen.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });

        btnRegistrarComida.setOnClickListener(v -> {
            String nombre = TXTprecio.getText().toString();
            String ingredientes = TXTingredientes.getText().toString();
            int precio = Integer.parseInt(TXTprecio.getText().toString());



            if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(ingredientes) || Integer.toString(precio).isEmpty() ) {
                Toast.makeText(RegistroComida.this, "Rellena todos los campos vacios ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedImageUri == null){
                Toast.makeText(RegistroComida.this, "Añade una imagen", Toast.LENGTH_SHORT).show();
                return;
            }
            String file = selectedImageUri.toString();
            RegistrarComida(nombre, ingredientes, precio, file);


        });



    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

        }
    }

    private void RegistrarComida(String nombre, String ingredientes, int precio, String file){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.21:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Comida comida = retrofit.create(Comida.class);

        Call<ComidaPOST> call = comida.RegistrarComida(new ComidaPOST(nombre, ingredientes, precio, file));

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
        Intent intent = new Intent(RegistroComida.this, RegistroComida.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    void VerMenu(){
        Intent intent = new Intent(RegistroComida.this,Index.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}