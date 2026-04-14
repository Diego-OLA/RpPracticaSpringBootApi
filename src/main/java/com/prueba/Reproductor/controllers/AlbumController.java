package com.prueba.Reproductor.controllers;

import com.prueba.Reproductor.dto.AlbumDTO;
import com.prueba.Reproductor.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumDTO>> findAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> findById(@PathVariable Long id) {
        AlbumDTO dto = albumService.findById(id);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<AlbumDTO> findByTitulo(@PathVariable String titulo) {
        AlbumDTO dto = albumService.findByTitulo(titulo);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/albums")
    public ResponseEntity<AlbumDTO> save(@RequestBody AlbumDTO dto) {
        return ResponseEntity.ok(albumService.save(dto));
    }

    @PutMapping("/albums/{id}")
    public ResponseEntity<AlbumDTO> update(@RequestBody AlbumDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(albumService.save(dto));
    }
}
