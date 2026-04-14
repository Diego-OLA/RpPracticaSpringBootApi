package com.prueba.Reproductor.services;

import com.prueba.Reproductor.dto.CancionDTO;
import com.prueba.Reproductor.mapper.CancionMapper;
import com.prueba.Reproductor.model.Album;
import com.prueba.Reproductor.model.Artista;
import com.prueba.Reproductor.model.Cancion;
import com.prueba.Reproductor.repository.AlbumRepository;
import com.prueba.Reproductor.repository.ArtistaRepository;
import com.prueba.Reproductor.repository.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CancionService {

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private AlbumRepository albumRepository;


    public List<CancionDTO> findAll() {
        return cancionRepository.findAll()
                .stream()
                .map(CancionMapper::toDTO)
                .collect(Collectors.toList());
    }


    public CancionDTO findById(Long id) {
        Cancion cancion = cancionRepository.findById(id)
                .orElse(null);

        if (cancion == null) return null;

        return CancionMapper.toDTO(cancion);
    }

    public CancionDTO findByNombreAndArtistaId(String nombre,Long artista_id) {
        Cancion cancion = cancionRepository.findByNombreAndArtista_Id(nombre,artista_id);

        if(cancion==null)return null;

        return CancionMapper.toDTO(cancion);



    }


    public CancionDTO save(CancionDTO dto) {
        Cancion cancion;

        // UPDATE
        if (dto.getId() != null) {
            cancion = cancionRepository.findById(dto.getId()).orElseThrow(()-> new RuntimeException("cancion no encontrada"));

        }else{
            cancion = new Cancion();
        }

        cancion.setNumeroTrack(dto.getNumeroTrack());
        cancion.setNombre(dto.getNombre());
        cancion.setDuracionMinutos(dto.getDuracionMinutos());


        // ARTISTA (OBLIGATORIO)

        if (dto.getArtistaDTO() == null || dto.getArtistaDTO().getId() == null) {
            throw new RuntimeException("Artista es obligatorio");
        }

        Artista artista = artistaRepository.findById(dto.getArtistaDTO().getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        cancion.setArtista(artista);


        // ALBUM (OPCIONAL)

        if (dto.getAlbumDTO() != null && dto.getAlbumDTO().getId() != null) {

            Album album = albumRepository.findById(dto.getAlbumDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Album no encontrado"));

            cancion.setAlbum(album);

        } else {
            cancion.setAlbum(null);
        }
        
        Cancion persisted = cancionRepository.save(cancion);
        return CancionMapper.toDTO(persisted);
    }


    public void delete(Long id) {
        cancionRepository.deleteById(id);
    }

    private void validarAlbumYArtista(CancionDTO dto) {

        if (dto.getArtistaDTO() == null || dto.getArtistaDTO().getId() == null) {
            throw new RuntimeException("Artista es obligatorio");
        }

        if (dto.getAlbumDTO() != null && dto.getAlbumDTO().getId() != null) {

            Album album = albumRepository.findById(dto.getAlbumDTO().getId())
                    .orElseThrow(() -> new RuntimeException("Album no encontrado"));

            if (!album.getArtista().getId().equals(dto.getArtistaDTO().getId())) {
                throw new RuntimeException("El álbum no pertenece al artista");
            }

        }
    }
}
