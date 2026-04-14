package com.prueba.Reproductor.repository;

import com.prueba.Reproductor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsuarioRepository extends JpaRepository<Usuario,Long> {
}
