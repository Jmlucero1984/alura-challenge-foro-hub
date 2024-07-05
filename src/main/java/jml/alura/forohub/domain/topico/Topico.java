package jml.alura.forohub.domain.topico;
import jakarta.persistence.*;
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
    private String autor;
    private String curso;


    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.status=true;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion =  Date.from(Instant.now());
        this.autor = datosRegistroTopico.autor();
        this.curso = datosRegistroTopico.curso();

    }
}