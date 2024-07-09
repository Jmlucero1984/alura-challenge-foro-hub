package jml.alura.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jml.alura.forohub.domain.perfil.DatosRegistroPerfil;
import jml.alura.forohub.domain.perfil.Perfil;

import java.util.Set;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,
        @Email
        @NotBlank
        String correoElectronico,
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La contraseña debe tener al menos 8 caracteres, incluyendo letras, números y al menos un carácter especial.")
        String contraseña,
        @NotNull
        Set<DatosRegistroPerfil> perfiles
) {
}

