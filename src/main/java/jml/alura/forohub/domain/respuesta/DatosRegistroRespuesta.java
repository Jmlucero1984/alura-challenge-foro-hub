package jml.alura.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,
        @NotBlank
        String topico,
        @NotBlank
        String autor,
        @NotNull
        String solucion
) {

}
