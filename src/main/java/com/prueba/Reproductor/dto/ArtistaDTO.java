package com.prueba.Reproductor.dto;

import lombok.*;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArtistaDTO {
    private Long id;
    private String nombre;
    private GeneroDTO generoDTO;

}
