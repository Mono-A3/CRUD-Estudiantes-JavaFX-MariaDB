package com.escuela.javaescuelasena.model;

public class Estudiante {
    private int id;
    private String nombre;
    private int edad;
    private String carrera;
    private String ciudad;
    private String estado;

    public Estudiante(int id, String nombre, int edad, String carrera, String ciudad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
        this.ciudad = ciudad;
        this.estado = estado;
    }
}