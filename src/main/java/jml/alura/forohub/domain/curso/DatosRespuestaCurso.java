package jml.alura.forohub.domain.curso;

import jml.alura.forohub.domain.topico.Topico;

public record DatosRespuestaCurso(
            String name,
            String categoria

    ) {
    public DatosRespuestaCurso(Curso curso) {
        this(curso.getNombre(),curso.getCategoria().toString());
    }
    }
