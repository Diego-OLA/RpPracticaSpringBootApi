package com.prueba.Reproductor.controllers;

import com.prueba.Reproductor.dto.UsuarioDTO;

import com.prueba.Reproductor.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("usuarios")
    public ResponseEntity<?> save(@RequestBody UsuarioDTO dto) {
        Map<String,Object> response = new HashMap<>();
        try{
            UsuarioDTO persisted = usuarioService.save(dto);
            return ResponseEntity.ok(persisted);
        }catch (Exception e){
            response.put("message","ha ocurrido un error");
            response.put("error",e.toString());
            return new  ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("usuarios/{id}")
    public ResponseEntity<UsuarioDTO> update(
            @PathVariable Long id,
            @RequestBody UsuarioDTO dto) {

        UsuarioDTO response = usuarioService.update(id, dto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("usuarios")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }
*/

   /* @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }*/
}