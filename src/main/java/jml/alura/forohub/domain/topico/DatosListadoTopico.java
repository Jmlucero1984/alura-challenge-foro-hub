package jml.alura.forohub.domain.topico;

import jml.alura.forohub.domain.curso.Curso;
import jml.alura.forohub.domain.curso.DatosRespuestaCurso;

import java.util.Date;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion,
        String autor,
        DatosRespuestaCurso curso
) {

    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getNombre(), new DatosRespuestaCurso(topico.getCurso()));
    }
}

