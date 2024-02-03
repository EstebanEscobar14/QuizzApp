package com.esteban.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.esteban.examenapp.Activities.NivelesActivity;
import com.esteban.examenapp.Activities.NivelesActivity2;

// Actividad principal que muestra las opciones de niveles para Java y Android
public class MainActivity extends AppCompatActivity {

    // Elementos de la interfaz de usuario (CardViews)
    CardView java, android;

    // Método llamado cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecer el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_main);

        // Ocultar la barra de acción (ActionBar)
        getSupportActionBar().hide();

        // Obtener referencias a los CardViews en el diseño
        java = findViewById(R.id.java);
        android = findViewById(R.id.android);

        // Configurar un OnClickListener para el CardView de Java
        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una nueva intención para abrir la actividad de niveles de Java
                Intent intent = new Intent(MainActivity.this, NivelesActivity.class);

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });

        // Configurar un OnClickListener para el CardView de Android
        android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una nueva intención para abrir la actividad de niveles de Android
                Intent intent = new Intent(MainActivity.this, NivelesActivity2.class);

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });
    }
}
