package com.tareas.gestiontareas.model.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tareas.gestiontareas.model.dto.Tarea.TareaResponseDto;
import com.tareas.gestiontareas.model.enums.Rol;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioResponseDto {
    private Long id;
    private String nombreUsuario;
    private Rol rol;
    private List<TareaResponseDto> tareas;
}
