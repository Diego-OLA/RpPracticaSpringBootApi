package com.prueba.Reproductor.services;

import com.prueba.Reproductor.dto.UsuarioDTO;
import com.prueba.Reproductor.mapper.UsuarioMapper;
import com.prueba.Reproductor.model.Genero;
import com.prueba.Reproductor.model.Usuario;
import com.prueba.Reproductor.model.UsuarioGenero;
import com.prueba.Reproductor.repository.GeneroRepository;
import com.prueba.Reproductor.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private void validarGeneros(UsuarioDTO dto) {

        if (dto.getGeneros() == null) {
            throw new RuntimeException("Debe seleccionar géneros");
        }

        int cantidad = dto.getGeneros().size();

        if (dto.getTipoCuenta() == 0) { // básica

            if (cantidad < 2) {
                throw new RuntimeException("Usuario básico debe tener mínimo 2 géneros");
            }

            if (cantidad > 3) {
                throw new RuntimeException("Usuario básico solo puede tener máximo 3 géneros");
            }
        }
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional
    public UsuarioDTO save(UsuarioDTO dto) {

        validarGeneros(dto);

        Usuario usuario = new Usuario();

        usuario.setNombres(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreo(dto.getCorreo());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setTipoCuenta(dto.getTipoCuenta());
        usuario.setTarjetaCredito(dto.getTarjetaCredito());

        List<UsuarioGenero> relaciones = dto.getGeneros().stream().map(g -> {

            Genero genero = generoRepository.findById(g.getId())
                    .orElseThrow(() -> new RuntimeException("Genero no existe"));

            UsuarioGenero ug = new UsuarioGenero();
            ug.setUsuario(usuario);
            ug.setGenero(genero);

            return ug;
        }).toList();

        usuario.setGeneros(relaciones);

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
    }


    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        validarGeneros(dto);

        usuario.setNombres(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTipoCuenta(dto.getTipoCuenta());
        usuario.setTarjetaCredito(dto.getTarjetaCredito());

        //  esto activa orphanRemoval
        usuario.getGeneros().clear();

        List<UsuarioGenero> nuevas = dto.getGeneros().stream().map(g -> {

            Genero genero = generoRepository.findById(g.getId())
                    .orElseThrow(() -> new RuntimeException("Genero no existe"));

            UsuarioGenero ug = new UsuarioGenero();
            ug.setUsuario(usuario);
            ug.setGenero(genero);

            return ug;
        }).toList();

        usuario.getGeneros().addAll(nuevas);

        return UsuarioMapper.toDTO(usuarioRepository.save(usuario));
    }
}
