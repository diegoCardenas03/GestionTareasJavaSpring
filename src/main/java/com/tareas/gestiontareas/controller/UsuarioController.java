package com.tareas.gestiontareas.controller;

import com.tareas.gestiontareas.model.dto.Usuario.UsuarioDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import com.tareas.gestiontareas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    public final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obtenerUsuarios() {
        List<UsuarioResponseDto> usuarios = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId(@PathVariable @Valid Long id){
        UsuarioResponseDto usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<String> crearUsuario(UsuarioDto usuarioDto){
        usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado usuario correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable @Valid Long id, UsuarioDto usuarioDto){
        usuarioService.actualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable @Valid Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
