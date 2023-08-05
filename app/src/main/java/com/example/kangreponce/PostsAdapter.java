package com.example.kangreponce;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
                .load("http://192.168.100.12:3000/public/"+postsList.get(position).getImgUrl())
                .into(holder.imageView);

        String nombre = postsList.get(position).getNombre();
        int precio = postsList.get(position).getPrecio();

        // Set an OnClickListener to show the popup window when the item is clicked
        holder.btnAgregar.setOnClickListener(view -> showPopupWindow(holder.itemView,nombre, precio));

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView precio;
        TextView ingredientes;
        ImageView imageView;
        Button btnAgregar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            ingredientes = itemView.findViewById(R.id.ingredientes);
            imageView = itemView.findViewById(R.id.imageComida);
            btnAgregar = itemView.findViewById(R.id.btnAgregar);
        }
    }

    // Method to show the popup window
    private void showPopupWindow(View anchorView, String nombre,  int precio) {
        SharedPreferences sharedPreferences;
        View popUpView = LayoutInflater.from(anchorView.getContext())
                .inflate(R.layout.mainpopup, null);

        // Create the popup window
        int width= ViewGroup.LayoutParams.WRAP_CONTENT;
        int height= ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);

        // Show the popup window at the center of the screen
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Show the popup window
        popupWindow.showAsDropDown(anchorView); // or any other desired location

        // Find views inside the popup window
        Button btnCancelar = popUpView.findViewById(R.id.btnCancelar);
        Button btnAgregar = popUpView.findViewById(R.id.btnAgregar);
        EditText txtDireccion = popUpView.findViewById(R.id.txtDireccion);
        EditText txtTelefono = popUpView.findViewById(R.id.txtTelefono);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss(); // Close the popup window
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direccion = txtDireccion.getText().toString();
                String telefono = txtTelefono.getText().toString();
                if( direccion.equals("") || telefono.equals("")){
                    Toast.makeText(view.getContext(), "Agrega la información faltante", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", view.getContext());
                    String cliente = sharedPreferences.getString("correo", "");
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.100.12:3000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Pedidos pedidos = retrofit.create(Pedidos.class);

                    Call<PedidosClass> call = pedidos.createPedido(new PedidosClass(nombre, precio, cliente, direccion, telefono));
                    call.enqueue(new Callback<PedidosClass>() {
                        @Override
                        public void onResponse(Call<PedidosClass> call, Response<PedidosClass> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(view.getContext(), "Pedido Agregado!!", Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                                //startActivity(new Intent(RegistroUsuarios.this, MainActivity.class));
                            }
                        }
                        @Override
                        public void onFailure(Call<PedidosClass> call, Throwable t) {
                            Toast.makeText(view.getContext(), "No se agregó el pedido", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        // You can also set an OnDismissListener to handle events when the popup is dismissed
        /*popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(anchorView.getContext(), "se cerró popup", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private static SharedPreferences getSharedPreferences(String name, Context context) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}
