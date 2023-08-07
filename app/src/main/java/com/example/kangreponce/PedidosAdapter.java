package com.example.kangreponce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {

    private List<PedidosClass> postsList;

    public PedidosAdapter(List<PedidosClass> postsList) {
        this.postsList = postsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pedidos, parent, false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.ViewHolder holder, int position) {
        holder.nombre.setText(postsList.get(position).getComida());
        holder.precio.setText(String.valueOf(postsList.get(position).getPrecio()));
        holder.cliente.setText(postsList.get(position).getCliente());
        holder.direccion.setText(postsList.get(position).getDireccion());
        holder.telefono.setText(postsList.get(position).getTelefono());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre, precio, cliente, direccion, telefono;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            cliente = itemView.findViewById(R.id.cliente);
            direccion = itemView.findViewById(R.id.direccion);
            telefono = itemView.findViewById(R.id.telefono);
        }
    }
}
