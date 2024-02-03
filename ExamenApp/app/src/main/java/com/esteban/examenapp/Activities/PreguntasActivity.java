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
import com.esteban.examenapp.databinding.ActivityNivelesBinding;
import com.esteban.examenapp.databinding.ActivityPreguntasBinding;
import java.util.ArrayList;

public class PreguntasActivity extends AppCompatActivity {

    ArrayList<PreguntasModelo> list = new ArrayList<>();
    private int contador = 0;
    private int posicion = 0;
    private int puntos = 0;
    CountDownTimer timer;
    ActivityPreguntasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout utilizando View Binding
        binding = ActivityPreguntasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ocultar la barra de acción
        getSupportActionBar().hide();

        // Inicializar y comenzar el temporizador
        resetTimer();
        timer.start();

        // Obtener el nombre del nivel desde la actividad anterior
        String nombre = getIntent().getStringExtra("nombre");

        // Configurar preguntas según el nivel seleccionado
        if (nombre.equals("Nivel-1")) {
            setOne();
        } else if (nombre.equals("Nivel-2")) {
            setTwo();
        } else if (nombre.equals("Nivel-3")) {
            setThree();
        }

        // Configurar OnClickListener para las opciones de respuesta
        for (int i = 0; i < 4; i++) {
            binding.opcionesContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        // Reproducir animación para la primera pregunta
        playAnimation(binding.preguntas, 0, list.get(posicion).getPregunta());

        // Configurar OnClickListener para el botón "Siguiente"
        binding.btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Cancelar el temporizador actual
                if (timer != null) {
                    timer.cancel();
                }

                // Reiniciar el temporizador y deshabilitar el botón "Siguiente"
                timer.start();
                binding.btnSiguiente.setEnabled(false);
                binding.btnSiguiente.setAlpha((float) 0.3);
                enableOption(true);
                posicion++;

                // Verificar si se han respondido todas las preguntas
                if (posicion == list.size()) {
                    // Iniciar la actividad de puntos y pasar la puntuación
                    Intent intent = new Intent(PreguntasActivity.this, PuntosActivity.class);
                    intent.putExtra("puntos", puntos);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                // Reiniciar el contador y reproducir animación para la siguiente pregunta
                contador = 0;
                playAnimation(binding.preguntas, 0, list.get(posicion).getPregunta());
            }
        });
    }

    // Metodo para inicializar y configurar el temporizador
    private void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                // Actualizar el temporizador en la interfaz de usuario
                binding.tiempo.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                // Mostrar un diálogo de tiempo agotado
                Dialog dialog = new Dialog(PreguntasActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.volverAIntertarlo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Volver a la lista de niveles
                        Intent intent = new Intent(PreguntasActivity.this, ListaDeNiveles.class);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        };
    }

    // Metodo para reproducir la animación de preguntas y respuestas
    private void playAnimation(View view, int valor, String datos) {
        view.animate().alpha(valor).scaleX(valor).scaleY(valor).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                        // Cuando la animación comienza, mostrar las opciones de respuesta
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

                        // Cuando la animación termina, mostrar la pregunta o respuesta
                        if (valor == 0) {
                            try {
                                // Manejar texto para preguntas
                                ((TextView) view).setText(datos);
                                binding.totalPreguntas.setText(posicion + 1 + "/" + list.size());
                            } catch (Exception e) {
                                // Manejar texto para respuestas (botones)
                                ((Button) view).setText(datos);
                            }

                            // Etiquetar la vista con los datos y reproducir la animación
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

    // Metodo para habilitar o deshabilitar las opciones de respuesta
    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            binding.opcionesContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                binding.opcionesContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    // Metodo para verificar la respuesta seleccionada
    private void checkAnswer(Button seleccionarOpcion) {
        // Cancelar el temporizador actual
        if (timer != null) {
            timer.cancel();
        }

        // Habilitar el botón "Siguiente" y restaurar la opacidad
        binding.btnSiguiente.setEnabled(true);
        binding.btnSiguiente.setAlpha(1);

        // Verificar si la opción seleccionada es correcta
        if (seleccionarOpcion.getText().toString().equals(list.get(posicion).getOpcionCorrecta())) {
            // Incrementar los puntos y mostrar fondo verde para respuesta correcta
            puntos++;
            seleccionarOpcion.setBackgroundResource(R.drawable.respuesta_correcta);
        } else {
            // Mostrar fondo rojo para respuesta incorrecta
            seleccionarOpcion.setBackgroundResource(R.drawable.respuesta_incorrecta);

            // Obtener el botón con la respuesta correcta y mostrar fondo verde
            Button opcionCorrecta = (Button) binding.opcionesContainer.findViewWithTag(list.get(posicion).getOpcionCorrecta());
            opcionCorrecta.setBackgroundResource(R.drawable.respuesta_correcta);
        }
    }

    // Metodos para configurar preguntas para diferentes niveles
    private void setTwo() {
        list.add(new PreguntasModelo("1. ¿Qué es un bucle 'for' en Java?", "Un bucle de condición", "Un bucle de conteo", "Un bucle infinito", "Un bucle de selección", "Un bucle de conteo"));
        list.add(new PreguntasModelo("2. ¿Cuál es el operador lógico 'AND' en Java?", "&&", "||", "!", "&", "&&"));
        list.add(new PreguntasModelo("3. ¿Qué es un ArrayList en Java?", "Una clase para manipular imágenes", "Una colección de elementos con tamaño dinámico", "Un tipo de dato primitivo", "Una clase para manejar fechas y horas", "Una colección de elementos con tamaño dinámico"));
        list.add(new PreguntasModelo("4. ¿Cómo se declara una variable constante en Java?", "const int x = 10;", "final int x = 10;", "static int x = 10;", "const x = 10;", "final int x = 10;"));
    }


    private void setOne() {
        list.add(new PreguntasModelo("¿Cuál es el paradigma de programación principal de Java?", "Orientado a Objetos", "Procedural", "Funcional", "Declarativo", "Orientado a Objetos"));
        list.add(new PreguntasModelo("¿Qué significa JVM?", "Máquina Virtual de Java", "Máquina Virtual de Juegos", "Máquina Virtual de Juegos", "Java Virtual Machine", "Java Virtual Machine"));
        list.add(new PreguntasModelo("¿Cuál es el propósito de la palabra clave 'final' en Java?", "Indica que un método no puede ser sobrescrito", "Indica que una variable no puede cambiar su valor", "Indica que una clase no puede ser extendida", "Todas las anteriores", "Todas las anteriores"));
        list.add(new PreguntasModelo("¿Qué es un constructor en Java?", "Un método que destruye objetos", "Un método que inicializa objetos", "Un tipo especial de variable", "Un tipo de bucle", "Un método que inicializa objetos"));
        list.add(new PreguntasModelo("¿Cuál es la diferencia entre '==' y 'equals()' en Java?", "No hay diferencia, se pueden usar indistintamente", "==' compara referencias, 'equals()' compara contenido", "==' compara contenido, 'equals()' compara referencias", "Son sinónimos en Java", "==' compara referencias, 'equals()' compara contenido"));
    }

    private void setThree() {
        list.add(new PreguntasModelo("¿Qué es el polimorfismo en Java?", "La capacidad de una clase para heredar múltiples clases", "La capacidad de una clase para tener varios métodos con el mismo nombre", "La capacidad de una clase para tener múltiples constructores", "La capacidad de una clase para ocultar sus atributos", "La capacidad de una clase para tener varios métodos con el mismo nombre"));
        list.add(new PreguntasModelo("¿Cuál es el concepto de encapsulamiento en programación orientada a objetos?", "Ocultar la implementación interna de un objeto y solo mostrar la interfaz", "Permitir el acceso directo a los atributos de un objeto", "Forzar la herencia entre clases", "Restringir el acceso a los métodos de una clase", "Ocultar la implementación interna de un objeto y solo mostrar la interfaz"));
        list.add(new PreguntasModelo("¿Cuál es el propósito de la palabra clave 'static' en Java?", "Indicar que un método puede ser sobrescrito", "Indicar que una variable es constante", "Indicar que una variable o método pertenece a la clase en lugar de a una instancia específica", "Indicar que una clase no puede ser extendida", "Indicar que una variable o método pertenece a la clase en lugar de a una instancia específica"));
        list.add(new PreguntasModelo("¿Qué es un método abstracto en Java?", "Un método que no tiene implementación y debe ser implementado por las clases derivadas", "Un método que se hereda automáticamente de la clase Object", "Un método que no puede ser llamado desde fuera de la clase", "Un método que es privado y no puede ser modificado", "Un método que no tiene implementación y debe ser implementado por las clases derivadas"));
        list.add(new PreguntasModelo("¿Cuál es el objetivo del operador 'this' en Java?", "Referenciar la instancia actual de la clase", "Indicar que un método no puede ser sobrescrito", "Crear una nueva instancia de la clase", "Acceder a métodos estáticos de la clase", "Referenciar la instancia actual de la clase"));
    }
}
