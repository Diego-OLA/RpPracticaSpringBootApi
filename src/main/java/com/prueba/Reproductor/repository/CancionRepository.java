package com.prueba.Reproductor.repository;

import com.prueba.Reproductor.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CancionRepository extends JpaRepository<Cancion,Long> {
   Cancion findByNombreAndArtista_Id(String nombre, Long artista_id);
   @Query("""
SELECT DISTINCT c
FROM Cancion c
JOIN c.artista a
JOIN a.genero g
JOIN UsuarioGenero ug
WHERE ug.usuario.id = :usuarioId
AND g.id = ug.genero.id
""")
   List<Cancion> findCancionesByUsuario(
           @Param("usuarioId") Long usuarioId
   );
}
