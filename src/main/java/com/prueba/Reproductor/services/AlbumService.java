package com.prueba.Reproductor.services;

import com.prueba.Reproductor.dto.AlbumDTO;
import com.prueba.Reproductor.mapper.AlbumMapper;
import com.prueba.Reproductor.model.Album;
import com.prueba.Reproductor.model.Artista;
import com.prueba.Reproductor.repository.AlbumRepository;
import com.prueba.Reproductor.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistaRepository artistaRepository;

    public List<AlbumDTO> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(AlbumMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AlbumDTO save(AlbumDTO dto) {
        List<Album> exists = albumRepository.findByTituloAndArtista_Id(
                dto.getTitulo(),
                dto.getId()
        );

        if (!exists.isEmpty()) {
            throw new RuntimeException("Ya existe este album para este artista");
        }

        Album album;

        if (dto.getId() != null) {
            album = albumRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Album no encontrado"));
        }else{
            album = new Album();
        }

        album.setTitulo(dto.getTitulo());

        // Mapear Artista (solo referencia por ID)
        if (dto.getArtistaDTO() != null) {

            Artista artista = artistaRepository.findById(dto.getArtistaDTO().getId()).orElseThrow(null);

            album.setArtista(artista);
        }

        Album persisted = albumRepository.save(album);
        return AlbumMapper.toDTO(persisted);
    }

    public AlbumDTO findById(Long id) {
        Album album = albumRepository.findById(id)
                .orElse(null);

        if (album == null) return null;

        return AlbumMapper.toDTO(album);
    }

    public AlbumDTO findByTitulo(String titulo) {
        Album album = albumRepository.findByTitulo(titulo);

        if (album == null) return null;

        return AlbumMapper.toDTO(album);
    }
}
