package com.tareas.gestiontareas.service;

import com.tareas.gestiontareas.model.dto.Tarea.TareaDto;
import com.tareas.gestiontareas.model.dto.Tarea.TareaResponseDto;
import com.tareas.gestiontareas.model.entity.Tarea;
import com.tareas.gestiontareas.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TareaService {

   private final TareaRepository tareaRepository;


   public List<TareaResponseDto> obtenerTareas() {
       List<Tarea> tareas = tareaRepository.findAll();
       return  tareas.stream()
               .map(this::toDto)
               .toList();

   }

   public TareaResponseDto obtenerPorId (Long id){
        Tarea tarea = tareaRepository.findById(id).
               orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        return toDto(tarea);
   }

   public TareaResponseDto guardar(TareaDto tareaDto){
       Tarea tarea = toEntity(tareaDto);
       tareaRepository.save(tarea);
       return toDto(tarea);
   }

   public TareaResponseDto actualizar(Long id, TareaDto tareaDto){
       Tarea tareaExistente = tareaRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

       tareaExistente.setTitulo(tareaDto.getTitulo());
       tareaExistente.setDescripcion(tareaDto.getDescripcion());
       tareaExistente.setEstado(tareaDto.getEstado());
       tareaExistente.setFechaCaducidad(tareaDto.getFechaCaducidad());
       
       Tarea tareaActualizada = tareaRepository.save(tareaExistente);

       return toDto(tareaActualizada);
   }

   public void eliminar(Long id){
       tareaRepository.deleteById(id);
   }

   // MAPPPERS
    private Tarea toEntity (TareaDto tareaDto){
       return Tarea.builder()
               .titulo(tareaDto.getTitulo())
               .descripcion(tareaDto.getDescripcion())
               .estado(tareaDto.getEstado())
               .fechaCaducidad(tareaDto.getFechaCaducidad())
               .build();
    }
    private TareaResponseDto toDto (Tarea tarea){
       return TareaResponseDto.builder()
               .titulo(tarea.getTitulo())
               .descripcion(tarea.getDescripcion())
               .estado(tarea.getEstado())
               .fechaCreacion(tarea.getFechaCreacion())
               .fechaCaducidad(tarea.getFechaCaducidad())
       .build();
    }

}