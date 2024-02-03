package com.esteban.examenapp.ListaDeNiveles;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esteban.examenapp.Activities.PreguntasActivity;
import com.esteban.examenapp.MainActivity;
import com.esteban.examenapp.Modelos.ListaDeModelos;
import com.esteban.examenapp.R;
import com.esteban.examenapp.databinding.ItemNivelesBinding;

import java.util.ArrayList;

public class ListaDeNiveles extends RecyclerView.Adapter<ListaDeNiveles.viewHolder> {

    Context context;
    ArrayList<ListaDeModelos> list;

    // Constructor de la clase
    public ListaDeNiveles(Context context, ArrayList<ListaDeModelos> list){
        this.context = context;
        this.list = list;
    }

    // Método llamado cuando se necesita crear una nueva ViewHolder
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de la vista de nivel desde el archivo XML
        View view = LayoutInflater.from(context).inflate(R.layout.item_niveles, parent, false);

        // Devolver una nueva instancia de viewHolder
        return new viewHolder(view);
    }

    // Método llamado para actualizar el contenido de una ViewHolder específica
    @Override
    public void onBindViewHolder(@NonNull ListaDeNiveles.viewHolder holder, int position) {
        // Obtener el modelo de nivel en la posición actual
        final ListaDeModelos model = list.get(position);

        // Establecer el nombre del nivel en la vista correspondiente
        holder.binding.setName.setText(model.getNombre());

        // Configurar un OnClickListener para la vista del nivel
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una nueva intención para abrir la actividad de preguntas
                Intent intent = new Intent(context, PreguntasActivity.class);

                // Adjuntar el nombre del nivel como dato extra
                intent.putExtra("nombre", model.getNombre());

                // Iniciar la nueva actividad
                context.startActivity(intent);
            }
        });
    }

    // Método que devuelve la cantidad total de elementos en el conjunto de datos
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Clase interna que representa una ViewHolder
    public class  viewHolder extends RecyclerView.ViewHolder{
        ItemNivelesBinding binding;

        // Constructor de la clase ViewHolder
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar la variable de enlace utilizando View Binding
            binding = ItemNivelesBinding.bind(itemView);
        }
    }
}
