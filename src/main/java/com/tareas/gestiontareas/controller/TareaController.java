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
    public ResponseEntity<String> crearTarea(@Valid @RequestBody TareaDto tareaDto){
        tareaService.guardar(tareaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tarea guardada correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponseDto> obtenerTareaPorId(@PathVariable Long id){
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TareaResponseDto>> obtenerListaTareas(){
        return ResponseEntity.ok(tareaService.obtenerTareas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTarea(@PathVariable Long id, @Valid @RequestBody TareaDto tareaDto){
        tareaService.actualizar(id, tareaDto);
        return ResponseEntity.ok("Tarea actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id){
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}



