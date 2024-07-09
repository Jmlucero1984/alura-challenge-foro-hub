package jml.alura.forohub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jml.alura.forohub.domain.perfil.Perfil;
import jml.alura.forohub.domain.perfil.PerfilRepository;
import jml.alura.forohub.domain.topico.*;
import jml.alura.forohub.domain.usuario.DatosRegistroUsuario;
import jml.alura.forohub.domain.usuario.DatosRespuestaUsuario;
import jml.alura.forohub.domain.usuario.Usuario;
import jml.alura.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name="bearer-key")
public class UsuarioController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Operation(
            summary = "Registra un nuevo usuario",
            description = "Registra un nuevo usuario en la base de datos, validando los datos ingresados",
            tags = { "usuario", "post" })
    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario (@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        Set<Perfil> perfiles =datosRegistroUsuario.perfiles().stream()
                .map(e -> {
                    Optional<Perfil> perfil = Optional.ofNullable(perfilRepository.findByNombre(e.nombre()));
                    if (perfil.isPresent()) {
                        return perfil.get();
                    } else {
                        System.out.println("NULL");
                        return null;
                    }
                })
                .collect(Collectors.toSet());
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario,bCryptPasswordEncoder.encode(datosRegistroUsuario.contraseña()),perfiles));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo_electronico(),
                usuario.getContraseña()
        );
        URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }

    @Operation(
            summary = "Devuelve un usuario",
            description = "Devuelve un usuario de la base de datos según su id.",
            tags = { "usuario", "get" })
    @GetMapping("/{id}")
    public ResponseEntity retornaDatosUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosUsuario = new DatosRespuestaUsuario(
             usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo_electronico(),
                usuario.getContraseña()
        );
        return ResponseEntity.ok(datosUsuario);
    }

}
