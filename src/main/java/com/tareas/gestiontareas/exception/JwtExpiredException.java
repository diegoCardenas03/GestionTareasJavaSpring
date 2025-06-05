package com.tareas.gestiontareas.exception;

public class JwtExpiredException extends RuntimeException {
    public JwtExpiredException(String mensaje) {
        super(mensaje);
    }
}
