package com.tareas.gestiontareas.model.dto.Usuario;

import com.tareas.gestiontareas.model.dto.Tarea.TareaDto;
import com.tareas.gestiontareas.model.entity.Tarea;
import com.tareas.gestiontareas.model.enums.Rol;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String nombreUsuario;
    private String password;
    private Rol rol;
    private List<TareaDto> tareas;
}
