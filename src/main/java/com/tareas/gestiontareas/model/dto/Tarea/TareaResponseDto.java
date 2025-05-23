package com.tareas.gestiontareas.model.dto.Tarea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import com.tareas.gestiontareas.model.enums.Estado;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TareaResponseDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaCaducidad;
    private UsuarioResponseDto usuario;
}
