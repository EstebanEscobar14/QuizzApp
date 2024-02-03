package com.esteban.examenapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.esteban.examenapp.ListaDeNiveles.ListaDeNiveles;
import com.esteban.examenapp.Modelos.PreguntasModelo;
import com.esteban.examenapp.R;
import com.esteban.examenapp.databinding.ActivityAndroidBinding;
import com.esteban.examenapp.databinding.ActivityNivelesBinding;
import com.esteban.examenapp.databinding.ActivityPreguntasBinding;

import java.util.ArrayList;

public class Android extends AppCompatActivity {

    ArrayList<PreguntasModelo> list = new ArrayList<>();
    private int contador = 0;
    private int posicion = 0;
    private int puntos = 0;
    CountDownTimer timer;


    ActivityAndroidBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        resetTimer();
        timer.start();
        String nombre = getIntent().getStringExtra("nombre");

        if (nombre.equals("Nivel-Basico")) {
            setOneA();
        } else if (nombre.equals("Nivel-Medio")) {
            setTwoA();
        } else if (nombre.equals("Nivel-Dificil")) {
            setThreeA();
        }

        for (int i = 0; i < 4; i++) {
            binding.opcionesContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        playAnimation(binding.preguntas, 0, list.get(posicion).getPregunta());

        binding.btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (timer != null) {
                    timer.cancel();
                }

                timer.start();
                binding.btnSiguiente.setEnabled(false);
                binding.btnSiguiente.setAlpha((float) 0.3);
                enableOption(true);
                posicion++;

                if (posicion == list.size()) {
                    Intent intent = new Intent(Android.this, PuntosActivity.class);
                    intent.putExtra("puntos", puntos);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                contador = 0;

                playAnimation(binding.preguntas, 0, list.get(posicion).getPregunta());
            }
        });
    }

    private void resetTimer() {

        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                binding.tiempo.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(Android.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.volverAIntertarlo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Android.this, ListaDeNiveles.class);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        };
    }

    private void playAnimation(View view, int valor, String datos) {
        view.animate().alpha(valor).scaleX(valor).scaleY(valor).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                        if (valor == 0 && contador < 4) {
                            String opcion = "";
                            if (contador == 0) {
                                opcion = list.get(posicion).getOpcion1();
                            } else if (contador == 1) {
                                opcion = list.get(posicion).getOpcion2();
                            } else if (contador == 2) {
                                opcion = list.get(posicion).getOpcion3();
                            } else if (contador == 3) {
                                opcion = list.get(posicion).getOpcion4();
                            }

                            playAnimation(binding.opcionesContainer.getChildAt(contador), 0, opcion);
                            contador++;

                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {

                        if (valor == 0) {
                            try {

                                ((TextView) view).setText(datos);
                                binding.totalPreguntas.setText(posicion + 1 + "/" + list.size());
                            } catch (Exception e) {
                                ((Button) view).setText(datos);
                            }

                            view.setTag(datos);
                            playAnimation(view, 1, datos);
                        }

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                });
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            binding.opcionesContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                binding.opcionesContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    private void checkAnswer(Button selecccionarOpcion) {

        if (timer != null) {
            timer.cancel();
        }
        binding.btnSiguiente.setEnabled(true);
        binding.btnSiguiente.setAlpha(1);

        if (selecccionarOpcion.getText().toString().equals(list.get(posicion).getOpcionCorrecta())) {
            puntos++;
            selecccionarOpcion.setBackgroundResource(R.drawable.respuesta_correcta);
        } else {
            selecccionarOpcion.setBackgroundResource(R.drawable.respuesta_incorrecta);

            Button opcionCoreccta = (Button) binding.opcionesContainer.findViewWithTag(list.get(posicion).getOpcionCorrecta());
            opcionCoreccta.setBackgroundResource(R.drawable.respuesta_correcta);
        }
    }

    private void setOneA() {
        list.add(new PreguntasModelo("¿Qué es Android Studio?", "Un sistema operativo para dispositivos móviles", "Un entorno de desarrollo integrado (IDE) para aplicaciones Android", "Una tienda de aplicaciones", "Una red social para desarrolladores", "Un entorno de desarrollo integrado (IDE) para aplicaciones Android"));
        list.add(new PreguntasModelo("¿Cuál es el nombre del emulador de Android en Android Studio?", "Simulador", "AVD (Android Virtual Device)", "EmuAndroid", "DroidSim", "AVD (Android Virtual Device)"));
        list.add(new PreguntasModelo("¿Qué es 'Logcat' en Android Studio?", "Una función para realizar seguimiento de paquetes de red", "Una herramienta de diseño de interfaces", "Una ventana de registro que muestra mensajes de registro del sistema y de la aplicación", "Un sistema de catálogo de dispositivos Android", "Una ventana de registro que muestra mensajes de registro del sistema y de la aplicación"));
        list.add(new PreguntasModelo("¿Cómo se agrega una biblioteca externa (librería) en Android Studio?", "Copiándola manualmente en el directorio del proyecto", "Descargándola desde la tienda de aplicaciones de Android", "Añadiendo una línea en el archivo 'AndroidManifest.xml'", "Editando el código fuente directamente", "Copiándola manualmente en el directorio del proyecto"));
        list.add(new PreguntasModelo("¿Qué es 'ADB' en el contexto de Android Studio?", "Una base de datos de aplicaciones", "Un servidor web para aplicaciones Android", "Android Debug Bridge, una herramienta de línea de comandos para interactuar con dispositivos Android", "Un framework para desarrollo de bases de datos", "Android Debug Bridge, una herramienta de línea de comandos para interactuar con dispositivos Android"));
    }

    private void setTwoA() {
        list.add(new PreguntasModelo("¿En qué lenguaje de programación se desarrolla principalmente en Android Studio?", "Java", "Python", "Kotlin", "C++", "Java"));

        list.add(new PreguntasModelo("¿Qué es un 'Layout' en Android Studio?", "Una interfaz gráfica", "Una base de datos", "Un archivo de configuración", "Una forma de organizar y controlar la disposición de los elementos de la interfaz de usuario", "Una forma de organizar y controlar la disposición de los elementos de la interfaz de usuario"));
        list.add(new PreguntasModelo("¿Cuál es el propósito de un 'Intent' en Android?", "Iniciar una actividad", "Almacenar datos en una base de datos", "Crear un servicio en segundo plano", "Definir un diseño de interfaz", "Iniciar una actividad"));
        list.add(new PreguntasModelo("¿Qué es el archivo 'AndroidManifest.xml'?", "Un archivo de configuración para la interfaz de usuario", "Un archivo de código fuente en Java", "Un archivo de recursos multimedia", "Un archivo de configuración que describe la estructura de la aplicación", "Un archivo de configuración que describe la estructura de la aplicación"));
        list.add(new PreguntasModelo("¿Cómo se agrega una nueva actividad en Android Studio?", "Cambiando el nombre del paquete", "Haciendo clic con el botón derecho en el proyecto y seleccionando 'New Activity'", "Editando el archivo 'MainActivity.java'", "Desinstalando y reinstalando la aplicación", "Haciendo clic con el botón derecho en el proyecto y seleccionando 'New Activity'"));
    }

    private void setThreeA() {
        list.add(new PreguntasModelo("¿Qué es un 'Fragment' en Android?", "Una sección de interfaz de usuario independiente y reutilizable", "Una función matemática en Java", "Un archivo de recursos de imagen", "Un servicio en segundo plano", "Una sección de interfaz de usuario independiente y reutilizable"));
        list.add(new PreguntasModelo("¿Qué es Gradle en Android Studio?", "Un lenguaje de programación", "Un sistema de construcción y automatización", "Un emulador de dispositivos Android", "Una base de datos de Android", "Un sistema de construcción y automatización"));
        list.add(new PreguntasModelo("¿Qué es el 'adb' en Android Studio?", "Una interfaz de usuario gráfica", "Una base de datos", "Un servidor web para aplicaciones Android", "Android Debug Bridge, una herramienta de línea de comandos para interactuar con dispositivos Android", "Android Debug Bridge, una herramienta de línea de comandos para interactuar con dispositivos Android"));
        list.add(new PreguntasModelo("¿Cuál es el propósito del archivo 'strings.xml' en Android?", "Almacenar imágenes", "Definir colores", "Definir cadenas de texto que se pueden traducir fácilmente", "Configurar la conectividad de red", "Definir cadenas de texto que se pueden traducir fácilmente"));
        list.add(new PreguntasModelo("¿Cómo se implementa la persistencia de datos en Android?", "Con el uso de tarjetas SD", "Almacenando datos en la nube", "Utilizando bases de datos locales como SQLite", "No es posible persistir datos en Android", "Utilizando bases de datos locales como SQLite"));
    }


}