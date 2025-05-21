package com.tareas.gestiontareas.model.dto.Usuario;

import com.tareas.gestiontareas.model.entity.Tarea;
import com.tareas.gestiontareas.model.enums.Rol;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String nombreUsuario;
    private Rol rol;
    private List<Tarea> tareas;
}
