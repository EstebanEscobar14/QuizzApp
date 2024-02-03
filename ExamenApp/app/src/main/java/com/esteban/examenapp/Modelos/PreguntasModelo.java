package com.esteban.examenapp.Modelos;

// Clase que representa el modelo de datos para una pregunta en la aplicación
public class PreguntasModelo {

    // Atributos que almacenan la pregunta, opciones y la opción correcta
    private String pregunta, opcion1, opcion2, opcion3, opcion4, opcionCorrecta;

    // Constructor de la clase que inicializa los atributos con los valores proporcionados
    public PreguntasModelo(String pregunta, String opcion1, String opcion2, String opcion3, String opcion4, String opcionCorrecta){
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.opcionCorrecta = opcionCorrecta;
    }

    // Constructor vacío por si es necesario crear una instancia sin proporcionar valores iniciales
    public PreguntasModelo(){

    }

    // Método que devuelve la primera opción
    public String getOpcion1(){
        return opcion1;
    }

    // Método que devuelve la pregunta
    public String getPregunta(){
        return pregunta;
    }

    // Método para establecer una nueva pregunta
    public void setPregunta(String pregunta){
        this.pregunta = pregunta;
    }

    // Métodos que devuelven las opciones
    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    // Método que devuelve la opción correcta
    public String getOpcionCorrecta() {
        return opcionCorrecta;
    }
}
