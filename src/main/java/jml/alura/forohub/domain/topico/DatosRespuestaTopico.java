package jml.alura.forohub.domain.topico;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        String status,
        String autor,
        String curso
) {
}

