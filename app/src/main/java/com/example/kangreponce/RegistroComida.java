package com.example.kangreponce;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;


import android.widget.ImageView;
import android.widget.Toast;


import android.widget.EditText;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    String path;

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
        btnRegistrar.setOnClickListener(view -> validarComida());


    }//onCreate

        private void validarComida () {
            String nombre = TXTnombre.getText().toString();
            String ingredientes = TXTingredientes.getText().toString();
            int precio = Integer.parseInt(TXTprecio.getText().toString());

            if( nombre.equals("") || ingredientes.equals("") || precio <= 0 ) {
                Toast.makeText(RegistroComida.this, "Rellena todos los campos vacios ", Toast.LENGTH_SHORT).show();
            }else{
                registrarComida(nombre,ingredientes,precio);
            }
        }

    private void registrarComida(String nombre, String ingredientes, int precio){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.12:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(path);
        if(file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nombre);
            RequestBody ingre = RequestBody.create(MediaType.parse("multipart/form-data"), ingredientes);
            RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(precio));

            Comida comida = retrofit.create(Comida.class);
            Call<ComidaPOST> call = comida.addDish(body, name, ingre, price);
            call.enqueue(new Callback<ComidaPOST>() {
                @Override
                public void onResponse(Call<ComidaPOST> call, Response<ComidaPOST> response) {
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().toString().equals("200")) {
                            Toast.makeText(RegistroComida.this, "Platillo Agregado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistroComida.this, Index.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegistroComida.this, "not Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ComidaPOST> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(RegistroComida.this, "Te falta una imagen", Toast.LENGTH_SHORT).show();
        }

    }//registrar comida


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
                            Context context = RegistroComida.this;
                            path = RealPathUtil.getRealPath(context, imageUri);
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            imageView.setImageURI(imageUri);
                            //imageview.setImageBitmap(bitmap);
                        }catch (Exception e){
                            Toast.makeText(RegistroComida.this, "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}//Class