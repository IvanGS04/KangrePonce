package com.example.kangreponce;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    public  AdapterDelete(List<ComidaClass> postsList) {
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
        holder.precio.setText(String.valueOf(postsList.get(position).getPrecio()) );
        holder.ingredientes.setText(postsList.get(position).getIngredientes());
        Glide.with(holder.itemView)
                .load("http://192.168.0.21:3000/public/"+postsList.get(position).getImgUrl())
                .into(holder.imageView);

        holder.btnDelete.setOnClickListener(v ->{
            String id = postsList.get(position).getId();

            Retrofit retrofit = new  Retrofit.Builder()
                    .baseUrl("http://192.168.0.21:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Comida api = retrofit.create(Comida.class);

            Call<Void> call = api.eliminarRegistro(id);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Comida eliminada exitosamente", Toast.LENGTH_SHORT).show();



                    } else {
                        Toast.makeText(v.getContext(), "Comida NO eliminada ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(v.getContext(), "Error en el servidor ", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }



    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView precio;
        TextView ingredientes;
        ImageView imageView;

        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            ingredientes = itemView.findViewById(R.id.ingredientes);
            imageView = itemView.findViewById(R.id.imageComida);
            btnDelete = itemView.findViewById(R.id.buttonDelete);

        }
    }
}
