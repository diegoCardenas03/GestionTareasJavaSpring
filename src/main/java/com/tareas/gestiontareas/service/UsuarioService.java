package com.tareas.gestiontareas.service;

import com.tareas.gestiontareas.model.dto.LoginRequestDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import com.tareas.gestiontareas.model.entity.Usuario;
import com.tareas.gestiontareas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperService mapperService;
    private final Set<String> tokenBlackList = ConcurrentHashMap.newKeySet();


    public UsuarioResponseDto crearUsuario (UsuarioDto usuarioDto){
        Usuario usuario = mapperService.usuarioToEntity(usuarioDto);
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuarioRepository.save(usuario);
        return mapperService.usuarioToDtoSinTareas(usuario);
    }

    public UsuarioResponseDto iniciarSesion (LoginRequestDto loginRequestDto){
        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getNombreUsuario().equals(loginRequestDto.getNombreUsuario()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if ( !passwordEncoder.matches(loginRequestDto.getPassword(), usuario.getPassword()) ){
            throw new RuntimeException("Nombre de usuario y/o contraseña incorrectos");
        }
        return mapperService.usuarioToDto(usuario);
    }

    public boolean isTokenBlackListed(String token) {
        return tokenBlackList.contains(token);
    }

    public void cerrarSesion(String token) {
        tokenBlackList.add(token);
    }

    public UsuarioResponseDto obtenerUsuarioPorId (Long id){
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapperService.usuarioToDto(usuario);
    }

    public List<UsuarioResponseDto> obtenerUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(mapperService::usuarioToDto)
                .toList();
    }

    public UsuarioResponseDto actualizarUsuario(Long id, UsuarioDto usuarioDto){
        Usuario usuarioExistente = usuarioRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String nombreUsuarioAuth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!usuarioExistente.getNombreUsuario().equals(nombreUsuarioAuth)) {
            throw new RuntimeException("No tienes permiso para modificar este usuario");
        }

        usuarioExistente.setNombreUsuario(usuarioDto.getNombreUsuario());
        if(usuarioDto.getPassword() != null && !usuarioDto.getPassword().isEmpty()){
            usuarioExistente.setPassword(usuarioDto.getPassword());
        }
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return mapperService.usuarioToDto(usuarioActualizado);
    }

    public void eliminarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String nombreUsuarioAuth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!usuario.getNombreUsuario().equals(nombreUsuarioAuth)){
            throw new RuntimeException("No tienes permiso para modificar este usuario");
        }
        usuarioRepository.delete(usuario);
    }

}
