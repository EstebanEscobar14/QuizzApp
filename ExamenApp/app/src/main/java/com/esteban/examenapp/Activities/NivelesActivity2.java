package com.esteban.examenapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.esteban.examenapp.ListaDeNiveles.ListaDeNiveles;
import com.esteban.examenapp.ListaDeNiveles.ListaDeNiveles2;
import com.esteban.examenapp.MainActivity;
import com.esteban.examenapp.Modelos.ListaDeModelos;
import com.esteban.examenapp.R;
import com.esteban.examenapp.databinding.ActivityNiveles2Binding;
import com.esteban.examenapp.databinding.ActivityNivelesBinding;

import java.util.ArrayList;

public class NivelesActivity2 extends AppCompatActivity {

    ArrayList<ListaDeModelos>list;
    ActivityNiveles2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNiveles2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.nivelRecy.setLayoutManager(linearLayoutManager);


        list.add(new ListaDeModelos("Nivel-Basico"));
        list.add(new ListaDeModelos("Nivel-Medio"));
        list.add(new ListaDeModelos("Nivel-Dificil"));


        ListaDeNiveles2 listaDeNiveles2 = new ListaDeNiveles2(this, list);
        binding.nivelRecy.setAdapter(listaDeNiveles2);

    }

    public void volverACategoriasA(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}