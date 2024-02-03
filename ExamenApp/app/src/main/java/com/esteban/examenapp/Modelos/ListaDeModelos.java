package com.esteban.examenapp.Modelos;

// Clase que representa el modelo de datos para un nivel en la lista
public class ListaDeModelos {

    // Atributo que almacena el nombre del nivel
    String nombre;

    // Constructor de la clase que inicializa el nombre del nivel
    public ListaDeModelos(String nombre){
        this.nombre = nombre;
    }

    // Método que devuelve el nombre del nivel
    public String getNombre(){
        return nombre;
    }

    // Método para establecer un nuevo nombre para el nivel
    public void Setnombre(String nombre){
        this.nombre = nombre;
    }
}
