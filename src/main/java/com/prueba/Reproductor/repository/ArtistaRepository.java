package com.prueba.Reproductor.repository;

import com.prueba.Reproductor.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista,Long> {
    Artista findByNombre (String nombre);
}
