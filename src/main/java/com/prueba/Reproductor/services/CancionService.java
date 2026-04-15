package com.prueba.Reproductor.services;

import com.prueba.Reproductor.dto.AlbumDTO;
import com.prueba.Reproductor.dto.ArtistaDTO;
import com.prueba.Reproductor.dto.CancionDTO;
import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.mapper.CancionMapper;
import com.prueba.Reproductor.model.Album;
import com.prueba.Reproductor.model.Artista;
import com.prueba.Reproductor.model.Cancion;
import com.prueba.Reproductor.model.Usuario;
import com.prueba.Reproductor.repository.AlbumRepository;
import com.prueba.Reproductor.repository.ArtistaRepository;
import com.prueba.Reproductor.repository.CancionRepository;
import com.prueba.Reproductor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CancionService {

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


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

        validarAlbumYArtista(dto);
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


    public CancionDTO obtenerCancionAleatoria(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Cancion> canciones = cancionRepository.findCancionesByUsuario(usuarioId);

        if (canciones.isEmpty()) {
            throw new RuntimeException("No hay canciones disponibles para los géneros del usuario");
        }

        int randomIndex = new Random().nextInt(canciones.size());
        Cancion cancion = canciones.get(randomIndex);

        CancionDTO dto = new CancionDTO();
        dto.setId(cancion.getId());
        dto.setNombre(cancion.getNombre());
        dto.setNumeroTrack(cancion.getNumeroTrack());
        dto.setDuracionMinutos(cancion.getDuracionMinutos());

        if (cancion.getAlbum() != null) {
            AlbumDTO albumDTO = new AlbumDTO();
            albumDTO.setId(cancion.getAlbum().getId());
            albumDTO.setTitulo(cancion.getAlbum().getTitulo());
            dto.setAlbumDTO(albumDTO);
        }

        if (cancion.getArtista() != null) {
            ArtistaDTO artistaDTO = new ArtistaDTO();
            artistaDTO.setId(cancion.getArtista().getId());
            artistaDTO.setNombre(cancion.getArtista().getNombre());

            if (cancion.getArtista().getGenero() != null) {
                GeneroDTO generoDTO = new GeneroDTO();
                generoDTO.setId(cancion.getArtista().getGenero().getId());
                generoDTO.setNombre(cancion.getArtista().getGenero().getNombre());
                artistaDTO.setGeneroDTO(generoDTO);
            }

            dto.setArtistaDTO(artistaDTO);
        }

        return dto;
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
