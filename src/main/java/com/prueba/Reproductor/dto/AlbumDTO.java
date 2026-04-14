package com.prueba.Reproductor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {
    private Long id;
    private String titulo;
    private ArtistaDTO artistaDTO;


}
