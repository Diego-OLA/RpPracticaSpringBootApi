package com.prueba.Reproductor.repository;

import com.prueba.Reproductor.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CancionRepository extends JpaRepository<Cancion,Long> {
   Cancion findByNombreAndArtista_Id(String nombre, Long artista_id);
}
