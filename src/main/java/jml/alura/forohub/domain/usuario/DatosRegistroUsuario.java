package jml.alura.forohub.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jml.alura.forohub.domain.perfil.DatosRegistroPerfil;
import jml.alura.forohub.domain.perfil.Perfil;

import java.util.Set;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,
        @NotBlank
        String correoElectronico,
        @NotBlank
        String contraseña,
        @NotNull
        Set<DatosRegistroPerfil> perfiles
) {
}

