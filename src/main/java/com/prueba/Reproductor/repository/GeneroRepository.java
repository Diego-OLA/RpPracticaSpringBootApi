package com.prueba.Reproductor.repository;

import com.prueba.Reproductor.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero,Long> {
    Genero findByNombre(String nombre);
}
