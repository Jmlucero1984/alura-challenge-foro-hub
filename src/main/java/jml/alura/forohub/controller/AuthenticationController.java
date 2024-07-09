package jml.alura.forohub.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jml.alura.forohub.domain.usuario.DatosAutenticacionUsuario;

import jml.alura.forohub.domain.usuario.Usuario;
import jml.alura.forohub.infra.security.DatosJWTToken;
import jml.alura.forohub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacion", description = "obtiene el token para el usuario asignado que da acceso al resto de endpoint")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @Operation(
            summary = "Devuelve un token para un usuario autenticado",
            description = "En el body se envía el JSON con el nombre de usuario registrado y su contraseña. " +
                    "En caso de existir el usuario y de ser válida la contraseña ingresada, se devuelve un token (con un tiempo de " +
                    "expiración) que confirma la autenticación y permite ejecutar el resto de solicitudes a la api.",

            tags = { "login", "post" })
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.nombre(),datosAutenticacionUsuario.contraseña());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
