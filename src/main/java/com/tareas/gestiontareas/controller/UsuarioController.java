package com.tareas.gestiontareas.controller;

import com.tareas.gestiontareas.config.security.JwtUtil;
import com.tareas.gestiontareas.model.dto.ApiResponseDto;
import com.tareas.gestiontareas.model.dto.LoginRequestDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import com.tareas.gestiontareas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> crearUsuario(@RequestBody UsuarioDto usuarioDto){
//    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDto usuarioDto){
        usuarioService.crearUsuario(usuarioDto);
        Map<String, String> response = new HashMap<>();
        response.put("ok", "true");
        response.put("mensaje", "Creado usuario correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        return ResponseEntity.status(HttpStatus.CREATED).body("usuario creado");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> iniciarSesion(@RequestBody LoginRequestDto loginRequestDto){
       UsuarioResponseDto usuarioDto = usuarioService.iniciarSesion(loginRequestDto);
       String token = jwtUtil.generateToken(usuarioDto.getNombreUsuario(), usuarioDto.getRol());
       ApiResponseDto response = new ApiResponseDto(
               usuarioDto, token);
       return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> cerrarSesion(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            usuarioService.cerrarSesion(token);
        }
        return ResponseEntity.ok("Sesión finalizada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto){
        UsuarioResponseDto usuarioResponseDto = usuarioService.actualizarUsuario(id, usuarioDto);
        String token = jwtUtil.generateToken(usuarioResponseDto.getNombreUsuario(), usuarioResponseDto.getRol());
        ApiResponseDto response = new ApiResponseDto(
                usuarioResponseDto, token );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> eliminarUsuarioDesdeAdmin(@PathVariable Long id, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        usuarioService.eliminarUsuarioDesdeAdmin(id, token);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    @PostMapping("/token_validate")
    public ResponseEntity<String> verificarToken(@RequestBody Map<String, String> body){
        String token = body.get("token");
        try {
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado");
            }
            return ResponseEntity.ok("Token válido");
        } catch (io.jsonwebtoken.security.SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Firma de token inválida");
        } catch (io.jsonwebtoken.JwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido o corrupto");
        }
    }


}
