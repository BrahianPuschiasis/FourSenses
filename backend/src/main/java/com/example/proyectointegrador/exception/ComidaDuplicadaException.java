package com.example.proyectointegrador.exception;

public class ComidaDuplicadaException extends RuntimeException {
    public ComidaDuplicadaException(String nombre) {
        super(nombre);
    }
}
