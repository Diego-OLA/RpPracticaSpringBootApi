package com.prueba.Reproductor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "album")
public class Album implements Serializable {
    @Serial
    private static final long  serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo",nullable = false, length = 100)
    private String titulo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false,referencedColumnName = "id")
    private Artista artista;


}
