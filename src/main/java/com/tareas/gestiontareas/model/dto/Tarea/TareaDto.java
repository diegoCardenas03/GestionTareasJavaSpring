package com.tareas.gestiontareas.model.dto.Tarea;

import com.tareas.gestiontareas.model.enums.Estado;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TareaDto {
    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;
    private String descripcion;
    @NotNull(message = "El estado es obligatorio")
    private Estado estado;
    private LocalDate fechaCaducidad;
    
}
