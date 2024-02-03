package com.esteban.examenapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.esteban.examenapp.R;
import com.esteban.examenapp.databinding.ActivityPuntosBinding;

public class PuntosActivity extends AppCompatActivity {

    ActivityPuntosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout utilizando View Binding
        binding = ActivityPuntosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ocultar la barra de acción
        getSupportActionBar().hide();

        // Obtener la puntuación total y las respuestas correctas desde la actividad anterior
        int totalPuntos = getIntent().getIntExtra("total", 0);
        int respuestaCorrecta = getIntent().getIntExtra("puntos", 0);

        // Calcular las respuestas incorrectas
        int respuestasIncorrectas = totalPuntos - respuestaCorrecta;

        // Mostrar la información en la interfaz de usuario
        binding.totalPreguntasRespondidas.setText(String.valueOf("Total De Preguntas: " + totalPuntos));
        binding.acertadas.setText(String.valueOf("Respuestas Correctas: " + respuestaCorrecta));
        binding.falladas.setText(String.valueOf("Respuestas Incorrectas: " + respuestasIncorrectas));

        // Configurar OnClickListener para el botón "Intentar De Nuevo"
        binding.btnIntentarDeNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cerrar la actividad actual
                finish();
            }
        });
    }
}
