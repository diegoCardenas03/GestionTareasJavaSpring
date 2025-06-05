package com.tareas.gestiontareas.controller;

import com.tareas.gestiontareas.model.dto.Tarea.TareaDto;
import com.tareas.gestiontareas.model.dto.Tarea.TareaResponseDto;
import com.tareas.gestiontareas.service.TareaService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tareas")
public class TareaController {
    private final TareaService tareaService;

    @PostMapping
    public ResponseEntity<TareaResponseDto> crearTarea(@Valid @RequestBody TareaDto tareaDto){
        TareaResponseDto tareaCreada = tareaService.guardar(tareaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaCreada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponseDto> obtenerTareaPorId(@PathVariable Long id){
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TareaResponseDto>> obtenerListaTareas(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return ResponseEntity.ok(tareaService.obtenerTareas(token));

        } else {
            throw new RuntimeException("Token invalido");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponseDto> actualizarTarea(@PathVariable Long id, @Valid @RequestBody TareaDto tareaDto){
        TareaResponseDto tareaResponseDto = tareaService.actualizar(id, tareaDto);
        return ResponseEntity.ok(tareaResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id){
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}



