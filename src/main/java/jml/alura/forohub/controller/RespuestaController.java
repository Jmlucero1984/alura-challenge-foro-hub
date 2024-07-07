package jml.alura.forohub.controller;

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
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;


    @PostMapping
    public ResponseEntity<DatosRespuestaDeRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                                                        UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("-----");
        System.out.println(datosRegistroRespuesta);
        System.out.println("-----");
        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, topicoRepository.findByTitulo(datosRegistroRespuesta.topico()), usuarioRepository.findByNombre(datosRegistroRespuesta.autor())));
        DatosRespuestaDeRespuesta datosRespuestaRespuesta = new DatosRespuestaDeRespuesta(
                datosRegistroRespuesta.topico(), datosRegistroRespuesta.mensaje(), datosRegistroRespuesta.autor(), datosRegistroRespuesta.solucion()
        );
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaRespuesta);
    }


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