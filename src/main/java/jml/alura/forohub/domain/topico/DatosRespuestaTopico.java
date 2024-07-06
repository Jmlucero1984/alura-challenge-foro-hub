package jml.alura.forohub.domain.topico;

import jml.alura.forohub.domain.curso.Curso;
import jml.alura.forohub.domain.curso.DatosRespuestaCurso;
import jml.alura.forohub.domain.usuario.DatosRespuestaUsuario;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        String status,
        DatosRespuestaUsuario datosRespuestaUsuario,
        DatosRespuestaCurso datosRespuestaCurso
) {
}

