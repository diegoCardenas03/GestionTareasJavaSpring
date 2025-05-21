package com.tareas.gestiontareas.repository;

import com.tareas.gestiontareas.model.entity.Usuario;
import com.tareas.gestiontareas.model.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRol(Rol rol);
}
