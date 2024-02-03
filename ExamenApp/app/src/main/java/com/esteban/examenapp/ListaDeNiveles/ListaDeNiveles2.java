package com.esteban.examenapp.ListaDeNiveles;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esteban.examenapp.Activities.Android;
import com.esteban.examenapp.Activities.PreguntasActivity;
import com.esteban.examenapp.Modelos.ListaDeModelos;
import com.esteban.examenapp.Modelos.PreguntasModelo;
import com.esteban.examenapp.R;
import com.esteban.examenapp.databinding.ItemNivelesBinding;

import java.util.ArrayList;

public class ListaDeNiveles2 extends RecyclerView.Adapter<ListaDeNiveles2.viewHolder> {

    Context context;
    ArrayList<ListaDeModelos> list;

    public ListaDeNiveles2(Context context, ArrayList<ListaDeModelos> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_niveles, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaDeNiveles2.viewHolder holder, int position) {
        final ListaDeModelos model = list.get(position);

        holder.binding.setName.setText(model.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Android.class);
                intent.putExtra("nombre", model.getNombre());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  viewHolder extends RecyclerView.ViewHolder{
        ItemNivelesBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemNivelesBinding.bind(itemView);
        }
    }
}

