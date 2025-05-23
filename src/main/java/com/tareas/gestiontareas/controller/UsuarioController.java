package com.tareas.gestiontareas.controller;

import com.tareas.gestiontareas.config.security.JwtUtil;
import com.tareas.gestiontareas.model.dto.LoginRequestDto;
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
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obtenerUsuarios() {
        List<UsuarioResponseDto> usuarios = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId(@PathVariable Long id){
        UsuarioResponseDto usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDto usuarioDto){
        usuarioService.crearUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado usuario correctamente");
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestBody LoginRequestDto loginRequestDto){
       UsuarioResponseDto usuarioDto = usuarioService.iniciarSesion(loginRequestDto);
       return jwtUtil.generateToken(usuarioDto.getNombreUsuario(), usuarioDto.getRol());
    }

    @PostMapping("/Logout")
    public ResponseEntity<String> cerrarSesion(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            usuarioService.cerrarSesion(token);
        }
        return ResponseEntity.ok("Sesión finalizada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable @Valid Long id, UsuarioDto usuarioDto){
        usuarioService.actualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

}
