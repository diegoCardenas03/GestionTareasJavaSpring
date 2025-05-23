package com.tareas.gestiontareas.model.entity;

import com.tareas.gestiontareas.model.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombreUsuario;
    private String password;
    @Enumerated (EnumType.STRING)
    private Rol rol;
    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareas;
}
