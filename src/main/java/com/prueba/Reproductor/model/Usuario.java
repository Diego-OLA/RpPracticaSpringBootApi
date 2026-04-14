package com.prueba.Reproductor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "correo",nullable = false,unique = true,length = 150)
    private String correo;
    @Column(name = "nombres",nullable = false,unique = false,length = 100)
    private String nombres;
    @Column(name = "apellidos",nullable = false,unique = false,length = 100)
    private String apellidos;
    @Column(name = "fecha_nacimiento",nullable = false)
    private LocalDate fechaNacimiento;
    @Column(name = "tipo_cuenta",nullable = false)
    private int tipoCuenta;
    @Column(name = "tarjeta_credito",nullable = false,unique = false,length = 20)
    private String tarjetaCredito;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioGenero> generos = new ArrayList<>();




}
