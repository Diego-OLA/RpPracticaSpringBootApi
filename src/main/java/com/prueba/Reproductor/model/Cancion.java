package com.prueba.Reproductor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cancion")
public class Cancion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artista_id", nullable = false, referencedColumnName = "id")
    private Artista artista;

    @Column(name = "numero_track", nullable = false)
    private int numeroTrack;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "duracion_minutos", nullable = false, precision = 5, scale = 2)
    private BigDecimal duracionMinutos;
}