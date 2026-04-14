package com.prueba.Reproductor.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CancionDTO {
    private Long id;
    private int numeroTrack;
    private String nombre;
    private BigDecimal duracionMinutos;

    private AlbumDTO albumDTO;   // puede ser null
    private ArtistaDTO artistaDTO; // obligatorio


}
