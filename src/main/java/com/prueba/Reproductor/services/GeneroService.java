package com.prueba.Reproductor.services;

import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.mapper.GeneroMapper;
import com.prueba.Reproductor.model.Genero;
import com.prueba.Reproductor.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeneroService {
    @Autowired
    private GeneroRepository generoRepository;


    public List<GeneroDTO> findAll(){
            return generoRepository.findAll().stream().map(g->GeneroMapper.toDTO(g)).collect(Collectors.toList());
        }

    public GeneroDTO save(GeneroDTO generoDTO){
         Genero genero = new Genero();
         if(generoDTO.getId() != null){
        //editar registro
             genero.setId(generoDTO.getId());

         }

             genero.setNombre(generoDTO.getNombre());

         return GeneroMapper.toDTO(generoRepository.save(genero));

    }

    public GeneroDTO findById(Long id) {
        Genero genero = generoRepository.findById(id).orElseThrow(null);
        if(genero==null)return null;
        return GeneroMapper.toDTO(genero);
    }

    public GeneroDTO findByNombre(String nombre) {
        Genero genero = generoRepository.findByNombre(nombre);
        if(genero==null)return null;
        return GeneroMapper.toDTO(genero);
    }

    public void delete(Long id) {
        generoRepository.deleteById(id);
    }


}
