package jml.alura.forohub.domain.topico;
import jakarta.persistence.*;
import jml.alura.forohub.domain.curso.Curso;
import jml.alura.forohub.domain.curso.CursoRepository;
import jml.alura.forohub.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;


@Table(name= "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")


public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private Date fechaCreacion;
    private Boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")

    private Curso curso;


    public Topico(DatosRegistroTopico datosRegistroTopico, Usuario autor, Curso curso) {
        this.status=true;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion =  Date.from(Instant.now());
        this.autor = autor;
        this.curso = curso;

    }
}
