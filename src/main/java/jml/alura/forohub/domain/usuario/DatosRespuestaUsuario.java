package jml.alura.forohub.domain.usuario;

import jml.alura.forohub.domain.perfil.Perfil;

import java.util.List;
import java.util.Set;

public record DatosRespuestaUsuario(
    Long id,
    String nombre,
    String correo_electronico,
    String contraseña

) {

    public DatosRespuestaUsuario(Usuario autor){
        this(autor.getId(), autor.getNombre(), autor.getCorreo_electronico(), autor.getContraseña());
    }
    }

