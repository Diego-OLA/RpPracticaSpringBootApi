package com.prueba.Reproductor.controllers;

import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.dto.UsuarioDTO;
import com.prueba.Reproductor.services.GeneroService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class GeneroController {
@Autowired
private GeneroService generoService;
    @GetMapping("generos")
    public ResponseEntity<?>getAll(){
        List<GeneroDTO> generoDTOList = generoService.findAll();
        return ResponseEntity.ok(generoDTOList);
    }

    @PostMapping("generos")
    public ResponseEntity<?>save(@RequestBody GeneroDTO generoDTO){
        Map<String, Object> response = new HashMap<>();

        try{
            GeneroDTO existente = generoService.findByNombre(generoDTO.getNombre());

            if (existente != null) {
                response.put("message", "Ya existe un género con ese nombre");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            GeneroDTO nuevoGenero = generoService.save(generoDTO);
            response.put("message","Se registro el genero correctamente");
            response.put("genero",nuevoGenero);
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (Exception e){
            response.put("message", "Error al crear genero.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("generos/{id}")
    public ResponseEntity<?> update(@RequestBody GeneroDTO generoDTO, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            GeneroDTO generoExist = generoService.findById(id);
            if (generoExist == null) {
                response.put("message", "Genero no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            GeneroDTO generoRep = generoService.findByNombre(generoDTO.getNombre());

            if (generoRep != null && !generoRep.getId().equals(id)) {
                response.put("message", "Ya existe un genero con ese nombre");
                response.put("genero", generoRep.getNombre());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // actualizar SOLO campos
            generoExist.setNombre(generoDTO.getNombre());

            GeneroDTO actualizado = generoService.save(generoExist);

            response.put("message", "Se ha editado el genero correctamente");
            response.put("genero", actualizado);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("message", "Error al editar genero.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("generos/{id}")
    public ResponseEntity<GeneroDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.findById(id));
    }
}
