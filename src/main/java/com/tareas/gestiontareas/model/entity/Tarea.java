package com.tareas.gestiontareas.model.entity;
import com.tareas.gestiontareas.model.enums.Estado;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tarea {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    @Enumerated (EnumType.STRING)
    private Estado estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaCaducidad;
    @ManyToOne
    @JoinColumn (name = "idUsuario")
    private Usuario usuario;

}
