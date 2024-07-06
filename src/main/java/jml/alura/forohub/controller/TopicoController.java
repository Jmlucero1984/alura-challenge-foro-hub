package jml.alura.forohub.controller;

import jakarta.validation.Valid;
import jml.alura.forohub.domain.curso.CursoRepository;
import jml.alura.forohub.domain.curso.DatosRespuestaCurso;
import jml.alura.forohub.domain.topico.*;
import jml.alura.forohub.domain.usuario.DatosRespuestaUsuario;
import jml.alura.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {


    @Autowired
    // No recomendable, por ejemplo, para hacer mocks. Para inyeccion de dependencia podria ser con un SEtter
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoMedicos (@PageableDefault(size=2) Pageable paginacion) { // Cuidado porque esta el Pageable de AWT y el de Spring
        //  return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);

        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new));
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico (@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                 UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("-----");
        System.out.println(datosRegistroTopico);
        System.out.println("-----");
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, usuarioRepository.findByNombre(datosRegistroTopico.autor()), cursoRepository.findByNombre(datosRegistroTopico.curso())));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion().toString(),
                topico.getStatus().toString(),
                new DatosRespuestaUsuario(topico.getAutor()),
                new DatosRespuestaCurso(topico.getCurso())
        );
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }


    @GetMapping("/{id}")
    public ResponseEntity retornaDatosTopico(@PathVariable Long id) {
    Topico topico = topicoRepository.getReferenceById(id);
            var datosTopico = new DatosRespuestaTopico(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion().toString(),
                    topico.getStatus().toString(),
                    new DatosRespuestaUsuario(topico.getAutor()),
                    new DatosRespuestaCurso(topico.getCurso())

            );
            return ResponseEntity.ok(datosTopico);
        }


}
