package com.tareas.gestiontareas.model.dto.Usuario;

import com.tareas.gestiontareas.model.entity.Tarea;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String nombreUsuario;
    private String password;
    private List<Tarea> tareas;
}
