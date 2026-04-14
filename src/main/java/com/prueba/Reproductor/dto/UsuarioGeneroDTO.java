package com.prueba.Reproductor.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioGeneroDTO {
    private Long id;
    private long usuarioId;
    private long generoId;
}
