package jml.alura.forohub.domain.topico;

import jml.alura.forohub.domain.curso.Curso;
import jml.alura.forohub.domain.curso.DatosRespuestaCurso;
import jml.alura.forohub.domain.usuario.DatosRespuestaUsuario;

import java.util.Date;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion,
        Boolean status,
        DatosRespuestaUsuario datosRespuestaUsuario,
        DatosRespuestaCurso datosRespuestaCurso
) {
}

