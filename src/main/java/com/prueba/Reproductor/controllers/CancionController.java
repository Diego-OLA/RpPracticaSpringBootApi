package com.prueba.Reproductor.controllers;

import com.prueba.Reproductor.dto.AlbumDTO;
import com.prueba.Reproductor.dto.CancionDTO;
import com.prueba.Reproductor.services.AlbumService;
import com.prueba.Reproductor.services.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class CancionController {
    @Autowired
    private CancionService cancionService;
    @Autowired
    private AlbumService albumService;

    @GetMapping("canciones")
    public ResponseEntity<?> findAll(){
        List<CancionDTO> canciones = cancionService.findAll();
        return ResponseEntity.ok(canciones);
    }
    @PostMapping("canciones")
    public ResponseEntity<?>save(@RequestBody CancionDTO cancionDTO){
        Map<String,Object> response = new HashMap<>();

        try{
        CancionDTO cancionExist = cancionService.findByNombreAndArtistaId(cancionDTO.getNombre(),cancionDTO.getArtistaDTO().getId());

        if(cancionExist!= null){
            response.put("message","Ya existe esta cancion en el sistema");
            response.put("cancion",cancionExist);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

         CancionDTO persisted = cancionService.save(cancionDTO);
        response.put("message","Se ha añadido una nueva cancion");
        response.put("cancion",persisted);


        }catch (Exception e){
            response.put("message","error al guardar cancion");
            response.put("error",e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("canciones/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody CancionDTO cancionDTO) {

        Map<String, Object> response = new HashMap<>();

        try {
            // validar si existe la canción
            CancionDTO exist = cancionService.findById(id);
            CancionDTO dupli = cancionService.findByNombreAndArtistaId(cancionDTO.getNombre(),cancionDTO.getArtistaDTO().getId());


            if (exist == null) {
                response.put("message", "Canción no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            if(dupli != null){
                response.put("message", "Ya existe esta cancion en el sistema");
                response.put("cancion",dupli);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // forzar el ID del path
            cancionDTO.setId(id);

            CancionDTO updated = cancionService.save(cancionDTO);

            response.put("message", "Canción actualizada correctamente");
            response.put("cancion", updated);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error al actualizar canción");
            response.put("error", e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            cancionService.delete(id);
            response.put("message", "Canción eliminada correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error al eliminar canción");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
