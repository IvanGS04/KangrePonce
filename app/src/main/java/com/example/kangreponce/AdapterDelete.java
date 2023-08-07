package com.example.kangreponce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterDelete extends RecyclerView.Adapter<AdapterDelete.ViewHolder> {

    private List<ComidaClass> postsList;

    public AdapterDelete(List<ComidaClass> postsList) {
        this.postsList = postsList;
    }

    private Context context;

    @NonNull
    @Override
    public AdapterDelete.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_administrador, parent, false);
        return new AdapterDelete.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDelete.ViewHolder holder, int position) {


        holder.nombre.setText(postsList.get(position).getNombre());
        holder.precio.setText(String.valueOf(postsList.get(position).getPrecio()));
        holder.ingredientes.setText(postsList.get(position).getIngredientes());
        Glide.with(holder.itemView)
                .load("http://192.168.100.12:3000/public/" + postsList.get(position).getImgUrl())
                .into(holder.imageView);

        holder.btnDelete.setOnClickListener(view -> {
            String id = postsList.get(position).getId();
            String nombre = postsList.get(position).getNombre();

            showPopupWindowDelete(holder.itemView, id, nombre);

        });
        holder.btnEdit.setOnClickListener(view -> {
            String id = postsList.get(position).getId();
            String nombre = postsList.get(position).getNombre();
            int precio = postsList.get(position).getPrecio();
            String ingredientes = postsList.get(position).getIngredientes();

            showPopupWindow(holder.itemView, id, nombre, precio, ingredientes);
        });

    }

    private void startIntentWithItemData(Context context) {
        Intent intent = new Intent(context, AdminComida.class);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView precio;
        TextView ingredientes;
        ImageView imageView;
        Button btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            ingredientes = itemView.findViewById(R.id.ingredientes);
            imageView = itemView.findViewById(R.id.imageComida);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
            btnEdit = itemView.findViewById(R.id.buttonEdit);

        }
    }

    private void showPopupWindow(View anchorView, String id, String nombre, int precio, String ingredientes) {
        View popUpView = LayoutInflater.from(anchorView.getContext())
                .inflate(R.layout.mainpopup_edit, null);

        // Create the popup window
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);

        // Show the popup window at the center of the screen
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Show the popup window
        popupWindow.showAsDropDown(anchorView); // or any other desired location

        // Find views inside the popup window
        Button btnCancelar = popUpView.findViewById(R.id.btnCancelar);
        Button btnEditar = popUpView.findViewById(R.id.btnEditar);
        EditText txtNombre = popUpView.findViewById(R.id.txtNombre);
        EditText txtPrecio = popUpView.findViewById(R.id.txtPrecio);
        EditText txtIngredientes = popUpView.findViewById(R.id.txtIngredientes);

        txtNombre.setText(nombre);
        txtPrecio.setText(Integer.toString(precio));
        txtIngredientes.setText(ingredientes);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss(); // Close the popup window
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tNombre = txtNombre.getText().toString();
                int tPrecio = Integer.parseInt(txtPrecio.getText().toString());
                String tIngredientes = txtIngredientes.getText().toString();

                if (tNombre.equals(nombre) && tPrecio == precio && tIngredientes.equals(ingredientes)) {
                    Toast.makeText(view.getContext(), "Moodifica al menos un campo", Toast.LENGTH_SHORT).show();
                } else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.100.12:3000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Comida comida = retrofit.create(Comida.class);

                    Call<ComidaEdit> call = comida.updateDish(id, new ComidaEdit(tNombre, tIngredientes, tPrecio));
                    call.enqueue(new Callback<ComidaEdit>() {
                        @Override
                        public void onResponse(Call<ComidaEdit> call, Response<ComidaEdit> response) {
                            if (response.isSuccessful()) {
                                popupWindow.dismiss();
                                Toast.makeText(view.getContext(), "Comida editada exitosamente", Toast.LENGTH_SHORT).show();
                                //para recargar la página se manda llamar el método y se envia por parametro el contexto
                                startIntentWithItemData(view.getContext());
                            } else {
                                Toast.makeText(view.getContext(), "Error al eliminar la comida", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ComidaEdit> call, Throwable t) {
                            Toast.makeText(view.getContext(), "Error en el servidor " + t, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private void showPopupWindowDelete(View anchorView, String id, String nombre) {
        View popUpView = LayoutInflater.from(anchorView.getContext())
                .inflate(R.layout.mainpopup_delete, null);

        // Create the popup window
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);

        // Show the popup window at the center of the screen
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Show the popup window
        popupWindow.showAsDropDown(anchorView); // or any other desired location

        // Find views inside the popup window
        Button btnCancelar = popUpView.findViewById(R.id.btnCancelar);
        Button btnDelete = popUpView.findViewById(R.id.btnDelete);
        TextView title = popUpView.findViewById(R.id.viewTitle);

        title.setText(title.getText().toString()+" "+nombre+"?");

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss(); // Close the popup window
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.100.12:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Comida api = retrofit.create(Comida.class);

                Call<Void> call = api.eliminarRegistro(id);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(view.getContext(), "Comida eliminada exitosamente", Toast.LENGTH_SHORT).show();
                            //para recargar la página se manda llamar el método y se envia por parametro el contexto
                            startIntentWithItemData(view.getContext());
                        } else {
                            Toast.makeText(view.getContext(), "Comida NO eliminada ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Error en el servidor ", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }
}//class
