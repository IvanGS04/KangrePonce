package com.example.kangreponce;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroComida extends AppCompatActivity {

    ImageView imageView;
    Button btnUpload;
    Button btnRegistrar;
    Toolbar toolbar;
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comida);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageUpload);
        btnUpload = findViewById(R.id.btnUpload);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        registerResult();

        btnUpload.setOnClickListener( view -> pickImage());


    }//onCreate



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