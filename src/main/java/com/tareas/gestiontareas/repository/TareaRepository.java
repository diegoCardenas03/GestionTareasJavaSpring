package com.tareas.gestiontareas.repository;

import com.tareas.gestiontareas.model.entity.Tarea;
import com.tareas.gestiontareas.model.entity.Usuario;
import com.tareas.gestiontareas.model.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByEstado(Estado estado);
    List<Tarea> findByUsuario(Usuario usuario);
}
