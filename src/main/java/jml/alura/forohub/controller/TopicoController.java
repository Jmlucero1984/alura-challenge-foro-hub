package jml.alura.forohub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name="bearer-key")
public class TopicoController {


    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(
            summary = "Devuelve listado de tópicos",
            description = "Devuelve listado de paginación de tópicos de la base de datos, por defecto, size =2, " +
                    "pero con la posibilidad de especificar otros parámetros. Por ejempplo: " +
                    "/topicos?size=5&sort=fechaCreacion,desc",
            tags = { "tópico", "get" })
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos (@PageableDefault(size=2) Pageable paginacion) { // Cuidado porque esta el Pageable de AWT y el de Spring
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new));
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "Registra un tópico",
            description = "Registra un nuevo tópico en la base de datos.",
            tags = { "tópico", "post" })
    public ResponseEntity<DatosRespuestaTopico> registrarTopico (@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                 UriComponentsBuilder uriComponentsBuilder) {

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
    @Operation(
            summary = "Actualiza un tópico",
            description = "Actualiza un tópico en la base de datos según el id especificado.",
            tags = { "tópico", "put" })
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina un tópico",
            description = "Elimina (Deep) un tópico en la base de datos según el id especificado.",
            tags = { "tópico", "delete" })
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoEliminar = Optional.of(topicoRepository.getReferenceById(id));
        if(topicoEliminar.isPresent()) {
            var topico = topicoEliminar.get();
            topicoRepository.deleteById(topico.getId());
            return ResponseEntity.ok(topicoEliminar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un tópico",
            description = "Devuelve un tópico de la base de datos según su id.",
            tags = { "tópico", "get" })
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

    @Operation(
            summary = "Devuelve tópicos por año",
            description = "Devuelve tópicos de la base de datos cuyo año de registro coincide con el año especificado como 'path variable'.",
            tags = { "tópico", "get" })
    @GetMapping("/año/{año}")
    public ResponseEntity<Page<DatosListadoTopico>> retornaDatosTopicoPorAño(@PageableDefault(size=2) Pageable paginacion, @PathVariable int año) {
        Date startDate = obtenerDateInicioAño(año);
        Date endDate = obtenerDateInicioAño(año+1);
        return ResponseEntity.ok(topicoRepository.findAllByCreationDateBetween(paginacion, startDate,endDate).map(DatosListadoTopico::new));
    }

    @Operation(
            summary = "Devuelve tópicos por curso",
            description = "Devuelve tópicos de la base de datos registrados buscando por nombre del curso, que debe coincidir exactamente. Ejemplo: " +
                    "/topicos/busca?curso=PenTesting",
            tags = { "tópico", "get" })
    @GetMapping("/busca")
    public ResponseEntity<Page<DatosListadoTopico>> retornaDatosTopicoPorCurso(@PageableDefault(size=2) Pageable paginacion, @RequestParam String curso) {
        Curso cursoBuscado = cursoRepository.findByNombre(curso);
        System.out.println(cursoBuscado);
        return ResponseEntity.ok(topicoRepository.findByCurso(paginacion,cursoBuscado).map(DatosListadoTopico::new));
    }

    public Date obtenerDateInicioAño (int año ){
        Calendar startCal = Calendar.getInstance();
        startCal.set(año, Calendar.JANUARY, 1, 0, 0, 0);
        return startCal.getTime();
    }

}
