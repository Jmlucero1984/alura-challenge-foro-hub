package jml.alura.forohub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jml.alura.forohub.domain.curso.Curso;
import jml.alura.forohub.domain.curso.CursoRepository;
import jml.alura.forohub.domain.curso.DatosRespuestaCurso;
import jml.alura.forohub.domain.topico.*;
import jml.alura.forohub.domain.usuario.DatosRespuestaUsuario;
import jml.alura.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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
                topico.getFechaCreacion(),
                topico.getStatus(),
                new DatosRespuestaUsuario(topico.getAutor()),
                new DatosRespuestaCurso(topico.getCurso())
        );
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }


    @PutMapping
    @Transactional

    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Optional<Topico> topicoActualizar =  topicoRepository.findTopicoById(datosActualizarTopico.id());
        if(topicoActualizar.isPresent()) {
            var topico = topicoActualizar.get();
            topico.actualizarDatos(datosActualizarTopico);
            return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion(),
                    topico.getStatus(),
                    new DatosRespuestaUsuario(topico.getAutor()),
                    new DatosRespuestaCurso(topico.getCurso())));
        } else {
            System.out.println("ERROR !!!!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("dfsdf");
        }
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoEliminar = Optional.of(topicoRepository.getReferenceById(id));
        if(topicoEliminar.isPresent()) {
            var topico = topicoEliminar.get();
            //topico.desactivarTopico();
            topicoRepository.deleteById(topico.getId());
            return ResponseEntity.ok(topicoEliminar);
        } else {
            System.out.println("ERROR !!!!!");
            return ResponseEntity.notFound().build();
        }
    }








    @GetMapping("/{id}")
    public ResponseEntity retornaDatosTopico(@PathVariable Long id) {
    Topico topico = topicoRepository.getReferenceById(id);
            var datosTopico = new DatosRespuestaTopico(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion(),
                    topico.getStatus(),
                    new DatosRespuestaUsuario(topico.getAutor()),
                    new DatosRespuestaCurso(topico.getCurso())

            );
            return ResponseEntity.ok(datosTopico);
        }

    @GetMapping("/año/{año}")
    public ResponseEntity<Page<DatosListadoTopico>> retornaDatosTopicoPorAño(@PageableDefault(size=2) Pageable paginacion, @PathVariable int año) {
        Calendar startCal = Calendar.getInstance();
        startCal.set(año, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = startCal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.set(año, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endDate = endCal.getTime();
        return ResponseEntity.ok(topicoRepository.findAllByCreationDateBetween(paginacion, startDate,endDate).map(DatosListadoTopico::new));
    }


    @GetMapping("/busca")
    public ResponseEntity<Page<DatosListadoTopico>> retornaDatosTopicoPorCurso(@PageableDefault(size=2) Pageable paginacion, @RequestParam String curso) {
        Curso cursoBuscado = cursoRepository.findByNombre(curso);
        System.out.println(cursoBuscado);


        return ResponseEntity.ok(topicoRepository.findByCurso(paginacion,cursoBuscado).map(DatosListadoTopico::new));
    }




}
