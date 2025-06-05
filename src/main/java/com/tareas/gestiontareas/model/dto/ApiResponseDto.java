package com.tareas.gestiontareas.model.dto;

import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApiResponseDto{
//    private boolean ok;
    private UsuarioResponseDto usuarioDto;
    private String token;
}
