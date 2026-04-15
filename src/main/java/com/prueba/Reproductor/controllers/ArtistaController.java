package com.prueba.Reproductor.controllers;

import com.prueba.Reproductor.dto.ArtistaDTO;
import com.prueba.Reproductor.dto.UsuarioDTO;
import com.prueba.Reproductor.services.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @GetMapping("artistas")
    public ResponseEntity<?> getAll(){
        List<ArtistaDTO> artistaDTOList = artistaService.findAll();
        return ResponseEntity.ok(artistaDTOList);
    }

    @PostMapping("artistas")
    public ResponseEntity<?>save(@RequestBody ArtistaDTO artistaDTO){
        Map<String, Object> response = new HashMap<>();

        try{
            ArtistaDTO existente = artistaService.findByNombre(artistaDTO.getNombre());

            if (existente != null) {
                response.put("message", "Ya existe un artista con ese nombre");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            ArtistaDTO nuevoArtista = artistaService.save(artistaDTO);
            response.put("message","Se registro el artista correctamente");
            response.put("artista",nuevoArtista);
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("message", "Error al crear artista.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("artistas/{id}")
    public ResponseEntity<?> update(@RequestBody ArtistaDTO artistaDTO, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            ArtistaDTO existente = artistaService.findById(id);
            if (existente == null) {
                response.put("message", "Genero no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            ArtistaDTO artistaNom = artistaService.findByNombre(artistaDTO.getNombre());

            if (artistaNom != null && !artistaNom.getId().equals(id)) {
                response.put("message", "Ya existe un artista con ese nombre");
                response.put("artista", artistaNom.getNombre());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // actualizar SOLO campos
            existente.setNombre(artistaDTO.getNombre());

            ArtistaDTO artistaUpdated = artistaService.save(existente);

            response.put("message", "Se ha editado el artista correctamente");
            response.put("genero", artistaUpdated);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("message", "Error al editar artista.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("artistas/{id}")
    public ResponseEntity<ArtistaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(artistaService.findById(id));
    }



}
