package com.tareas.gestiontareas.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tareas.gestiontareas.model.dto.Tarea.TareaDto;
import com.tareas.gestiontareas.model.dto.Tarea.TareaResponseDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioDto;
import com.tareas.gestiontareas.model.dto.Usuario.UsuarioResponseDto;
import com.tareas.gestiontareas.model.entity.Tarea;
import com.tareas.gestiontareas.model.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {


        public Usuario usuarioToEntity(UsuarioDto usuarioDto){
            return Usuario.builder()
                    .nombreUsuario(usuarioDto.getNombreUsuario())
                    .password(usuarioDto.getPassword())
                    .rol(usuarioDto.getRol())
                    .build();
        }


        public UsuarioResponseDto usuarioToDto(Usuario usuario){
            return UsuarioResponseDto.builder()
                    .id(usuario.getId())
                    .nombreUsuario(usuario.getNombreUsuario())
                    .rol(usuario.getRol())
                    .tareas(usuario.getTareas().stream()
                            .map(this::tareaToDtoSinUsuario)
                            .toList())
                    .build();
        }

        public UsuarioResponseDto usuarioToDtoSinTareas(Usuario usuario){
            return UsuarioResponseDto.builder()
                    .id(usuario.getId())
                    .nombreUsuario(usuario.getNombreUsuario())
                    .rol(usuario.getRol())
                    .build();
        }

        public Tarea tareaToEntity (TareaDto tareaDto){
            return Tarea.builder()
                    .titulo(tareaDto.getTitulo())
                    .descripcion(tareaDto.getDescripcion())
                    .estado(tareaDto.getEstado())
                    .fechaCaducidad(tareaDto.getFechaCaducidad())
                    .build();
        }


        public TareaResponseDto tareaToDto (Tarea tarea){
            return TareaResponseDto.builder()
                    .id(tarea.getId())
                    .titulo(tarea.getTitulo())
                    .descripcion(tarea.getDescripcion())
                    .estado(tarea.getEstado())
                    .fechaCreacion(tarea.getFechaCreacion())
                    .fechaCaducidad(tarea.getFechaCaducidad())
                    .usuario(this.usuarioToDtoSinTareas(tarea.getUsuario()))
                    .build();
        }

        public TareaResponseDto tareaToDtoSinUsuario(Tarea tarea){
            return TareaResponseDto.builder()
                    .id(tarea.getId())
                    .titulo(tarea.getTitulo())
                    .descripcion(tarea.getDescripcion())
                    .estado(tarea.getEstado())
                    .fechaCreacion(tarea.getFechaCreacion())
                    .fechaCaducidad(tarea.getFechaCaducidad())
                    .build();
        }
    }


