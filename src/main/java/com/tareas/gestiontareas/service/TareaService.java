package com.tareas.gestiontareas.service;

import com.tareas.gestiontareas.config.security.JwtUtil;
import com.tareas.gestiontareas.model.dto.Tarea.TareaDto;
import com.tareas.gestiontareas.model.dto.Tarea.TareaResponseDto;
import com.tareas.gestiontareas.model.entity.Tarea;
import com.tareas.gestiontareas.model.entity.Usuario;
import com.tareas.gestiontareas.model.enums.Rol;
import com.tareas.gestiontareas.repository.TareaRepository;
import com.tareas.gestiontareas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TareaService {

   private final TareaRepository tareaRepository;
   private final UsuarioRepository usuarioRepository;
   private final MapperService mapperService;
   private final JwtUtil jwtUtil;


    public List<TareaResponseDto> obtenerTareas(String token) {
        Rol rol = jwtUtil.extractRol(token);
        String nombreUsuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (rol == Rol.ADMIN) {
            Optional<Usuario> admin = usuarioRepository.findByNombreUsuario(nombreUsuario);
            if (admin.isEmpty()){
                throw new RuntimeException("Admin no encontrado");
            }
            return tareaRepository.findAll().stream()
                    .map(mapperService::tareaToDto)
                    .toList();
        } else {
            Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            return tareaRepository.findByUsuario(usuario).stream()
                    .map(mapperService::tareaToDto)
                    .toList();
        }
    }

   public TareaResponseDto obtenerPorId (Long id){
        Tarea tarea = tareaRepository.findById(id).
               orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        return mapperService.tareaToDto(tarea);
   }

   public TareaResponseDto guardar(TareaDto tareaDto){
       Tarea tarea = mapperService.tareaToEntity(tareaDto);
       tarea.setFechaCreacion(LocalDate.now());
       // Obtener el nombre de usuario autenticado
       String nombreUsuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
               .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
       tarea.setUsuario(usuario);
       tareaRepository.save(tarea);
       return mapperService.tareaToDto(tarea);
   }

   public TareaResponseDto actualizar(Long id, TareaDto tareaDto){
       Tarea tareaExistente = tareaRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

       String nombreUsuarioAuth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       if (!tareaExistente.getUsuario().getNombreUsuario().equals(nombreUsuarioAuth)){
           throw new RuntimeException("No tienes permiso para modificar esta tarea");
       }

       tareaExistente.setTitulo(tareaDto.getTitulo());
       tareaExistente.setDescripcion(tareaDto.getDescripcion());
       tareaExistente.setEstado(tareaDto.getEstado());
       tareaExistente.setFechaCaducidad(tareaDto.getFechaCaducidad());
       
       Tarea tareaActualizada = tareaRepository.save(tareaExistente);

       return mapperService.tareaToDto(tareaActualizada);
   }

   public void eliminar(Long id){
       Tarea tareaExistente = tareaRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

       String nombreUsuarioAuth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       if (!tareaExistente.getUsuario().getNombreUsuario().equals(nombreUsuarioAuth)){
           throw new RuntimeException("No tienes permiso para modificar esta tarea");
       }
       tareaRepository.deleteById(id);
   }




}