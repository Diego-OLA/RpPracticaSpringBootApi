package com.prueba.Reproductor.services;

import com.prueba.Reproductor.dto.ArtistaDTO;
import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.mapper.ArtistaMapper;
import com.prueba.Reproductor.mapper.GeneroMapper;
import com.prueba.Reproductor.model.Artista;
import com.prueba.Reproductor.model.Genero;
import com.prueba.Reproductor.repository.ArtistaRepository;
import com.prueba.Reproductor.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistaService {
    @Autowired
    private ArtistaRepository artistaRepository;
    @Autowired
    private GeneroRepository generoRepository;

    public List<ArtistaDTO> findAll(){
        return artistaRepository.findAll().stream().map(g-> ArtistaMapper.toDTO(g)).collect(Collectors.toList());
    }

    public ArtistaDTO save(ArtistaDTO artistaDTO){
        Artista artista = new Artista();
        if(artistaDTO.getId() != null){
            //nuevo registro
            artista.setId(artistaDTO.getId());


        }

            artista.setNombre(artistaDTO.getNombre());
            Genero genero = generoRepository.findById(artistaDTO.getGeneroDTO().getId()).orElseThrow(null);

            artista.setGenero(genero);


        return ArtistaMapper.toDTO(artistaRepository.save(artista));

    }

    public ArtistaDTO findById(Long id) {
        Artista artista = artistaRepository.findById(id).orElseThrow(null);
        if(artista==null)return null;
        return ArtistaMapper.toDTO(artista);
    }

    public ArtistaDTO findByNombre(String nombre) {
        Artista artista = artistaRepository.findByNombre(nombre);
        if(artista==null)return null;
        return ArtistaMapper.toDTO(artista);
    }

}
