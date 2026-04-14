package com.prueba.Reproductor.repository;

import com.prueba.Reproductor.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    Album findByTitulo(String titulo);
    List<Album> findByTituloAndArtista_Id(String titulo, Long artistaId);
}

