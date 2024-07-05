package jml.alura.forohub.domain.topico;

import java.util.Date;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion,
        String autor,
        String curso
) {

    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor(), topico.getCurso());
    }
}

