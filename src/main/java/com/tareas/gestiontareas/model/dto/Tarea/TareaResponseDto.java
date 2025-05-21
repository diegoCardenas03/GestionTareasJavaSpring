package com.tareas.gestiontareas.model.dto.Tarea;

import com.tareas.gestiontareas.model.entity.Usuario;
import com.tareas.gestiontareas.model.enums.Estado;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TareaResponseDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaCaducidad;
    private Usuario usuario;
}
