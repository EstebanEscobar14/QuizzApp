package com.esteban.examenapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.esteban.examenapp.ListaDeNiveles.ListaDeNiveles;
import com.esteban.examenapp.MainActivity;
import com.esteban.examenapp.Modelos.ListaDeModelos;
import com.esteban.examenapp.R;
import com.esteban.examenapp.databinding.ActivityNivelesBinding;
import java.util.ArrayList;

public class NivelesActivity extends AppCompatActivity {

    ArrayList<ListaDeModelos> list;
    ActivityNivelesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout utilizando View Binding
        binding = ActivityNivelesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ocultar la barra de acción
        getSupportActionBar().hide();

        // Inicializar la lista de modelos
        list = new ArrayList<>();

        // Configurar el LinearLayoutManager para el RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.nivelRecy.setLayoutManager(linearLayoutManager);

        // Agregar datos de ejemplo a la lista de modelos
        list.add(new ListaDeModelos("Nivel-1"));
        list.add(new ListaDeModelos("Nivel-2"));
        list.add(new ListaDeModelos("Nivel-3"));

        // Crear un adaptador personalizado para el RecyclerView
        ListaDeNiveles listaDeNiveles = new ListaDeNiveles(this, list);

        // Establecer el adaptador en el RecyclerView
        binding.nivelRecy.setAdapter(listaDeNiveles);
    }

    // Método llamado al hacer clic en el botón "Volver a Categorías"
    public void volverACategorias(View view) {
        // Crear un intent para volver a la actividad principal (MainActivity)
        Intent intent = new Intent(this, MainActivity.class);

        // Iniciar la actividad principal
        startActivity(intent);
    }
}
