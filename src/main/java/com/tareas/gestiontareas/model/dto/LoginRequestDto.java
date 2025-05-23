package com.tareas.gestiontareas.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String nombreUsuario;
    private String password;
}
