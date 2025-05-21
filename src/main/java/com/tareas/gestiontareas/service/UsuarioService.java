package com.tareas.gestiontareas.service;

import com.tareas.gestiontareas.model.dto.Usuario.UsuarioDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import com.tareas.gestiontareas.model.entity.Usuario;
import com.tareas.gestiontareas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDto crearUsuario (UsuarioDto usuarioDto){
        Usuario usuario = toEntity(usuarioDto);
        usuarioRepository.save(usuario);
        return toDto(usuario);
    }

    public UsuarioResponseDto obtenerUsuarioPorId (Long id){
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDto(usuario);
    }

    public List<UsuarioResponseDto> obtenerUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::toDto)
                .toList();
    }

    public UsuarioResponseDto actualizarUsuario(Long id, UsuarioDto usuarioDto){
        Usuario usuarioExistente = usuarioRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioExistente.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuarioExistente.setPassword(usuarioDto.getPassword());
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return toDto(usuarioActualizado);
    }

    public void eliminarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    private Usuario toEntity(UsuarioDto usuarioDto){
        return Usuario.builder()
                .nombreUsuario(usuarioDto.getNombreUsuario())
                .password(usuarioDto.getPassword())
                .tareas(usuarioDto.getTareas())
                .build();
    }
    private UsuarioResponseDto toDto(Usuario usuario){
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .rol(usuario.getRol())
                .tareas(usuario.getTareas())
                .build();
    }
}
