package com.example.kangreponce;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;



public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {






    private List<ComidaClass> postsList;

    public PostsAdapter(List<ComidaClass> postsList) {
        this.postsList = postsList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);



    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(postsList.get(position).getNombre());
        holder.precio.setText(String.valueOf(postsList.get(position).getPrecio()) );
        holder.ingredientes.setText(postsList.get(position).getIngredientes());
        Glide.with(holder.itemView)
                .load("http://192.168.0.21:3000/public/"+postsList.get(position).getImgUrl())
                .into(holder.imageView);



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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            ingredientes = itemView.findViewById(R.id.ingredientes);
            imageView = itemView.findViewById(R.id.imageComida);

        }
    }



}
