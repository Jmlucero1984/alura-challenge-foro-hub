package jml.alura.forohub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jml.alura.forohub.domain.respuesta.DatosRegistroRespuesta;
import jml.alura.forohub.domain.respuesta.DatosRespuestaDeRespuesta;
import jml.alura.forohub.domain.respuesta.Respuesta;
import jml.alura.forohub.domain.respuesta.RespuestaRepository;
import jml.alura.forohub.domain.topico.TopicoRepository;
import jml.alura.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name="bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @Operation(
            summary = "Registra una nueva respuesta",
            description = "Registra una nueva respuesta en la base de datos especificando el título del tópico al cual se refiere.",
            tags = { "respuesta", "post" })
    @PostMapping
    public ResponseEntity<DatosRespuestaDeRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                                                        UriComponentsBuilder uriComponentsBuilder) {

        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, topicoRepository.findByTitulo(datosRegistroRespuesta.topico()), usuarioRepository.findByNombre(datosRegistroRespuesta.autor())));
        DatosRespuestaDeRespuesta datosRespuestaRespuesta = new DatosRespuestaDeRespuesta(
                datosRegistroRespuesta.topico(), datosRegistroRespuesta.mensaje(), datosRegistroRespuesta.autor(), datosRegistroRespuesta.solucion()
        );
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaRespuesta);
    }

    @Operation(
            summary = "Devuelve una respuesta",
            description = "Devuelve una respuesta de la base de datos según su id.",
            tags = { "respuesta", "get" })
    @GetMapping("/{id}")
    public ResponseEntity retornaDatosRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        var datosRespuesta = new DatosRespuestaDeRespuesta(
                respuesta.getTopico().getTitulo(),
                respuesta.getMensaje(),
                respuesta.getAutor().getNombre(),
                respuesta.getSolucion()

        );
        return ResponseEntity.ok(datosRespuesta);
    }
}