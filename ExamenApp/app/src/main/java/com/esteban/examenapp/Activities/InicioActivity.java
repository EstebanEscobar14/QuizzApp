package com.esteban.examenapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.esteban.examenapp.MainActivity;
import com.esteban.examenapp.R;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecer el diseño de la actividad a partir de "activity_inicio.xml"
        setContentView(R.layout.activity_inicio);

        // Ocultar la barra de acción
        getSupportActionBar().hide();

        // Utilizar un Handler para retrasar la ejecución de ciertas acciones
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crear un intent para iniciar la actividad principal (MainActivity)
                Intent intent = new Intent(InicioActivity.this, MainActivity.class);

                // Iniciar la actividad principal después de un retraso de 2000 milisegundos (2 segundos)
                startActivity(intent);
            }
        }, 2000); // Tiempo de retraso en milisegundos
    }
}
