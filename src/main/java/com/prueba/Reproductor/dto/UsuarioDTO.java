package com.prueba.Reproductor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private LocalDate fechaNacimiento;
    private Integer tipoCuenta;
    private String tarjetaCredito;
    private List<GeneroDTO> generos;

}

