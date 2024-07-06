package jml.alura.forohub.domain.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record DatosRegistroPerfil(

        @NotBlank
        String nombre
) {
}
