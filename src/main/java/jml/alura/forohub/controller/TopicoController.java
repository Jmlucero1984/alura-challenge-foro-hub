package jml.alura.forohub.controller;

import jakarta.validation.Valid;
import jml.alura.forohub.domain.topico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/topicos")
public class TopicoController {


    @Autowired
    // No recomendable, por ejemplo, para hacer mocks. Para inyeccion de dependencia podria ser con un SEtter
    private TopicoRepository topicoRepository;


    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoMedicos (@PageableDefault(size=2) Pageable paginacion) { // Cuidado porque esta el Pageable de AWT y el de Spring
        //  return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);

        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new));
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico (@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                 UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion().toString(),
                topico.getStatus().toString(),
                topico.getAutor(),
                topico.getCurso()
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
                    topico.getAutor(),
                    topico.getCurso()

            );
            return ResponseEntity.ok(datosTopico);
        }


}
