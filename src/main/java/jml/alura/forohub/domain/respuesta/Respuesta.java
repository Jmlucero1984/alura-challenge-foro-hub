package jml.alura.forohub.domain.respuesta;

import jakarta.persistence.*;
import jml.alura.forohub.domain.topico.Topico;
import jml.alura.forohub.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;


@Table(name= "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")

public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;
    private Date fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private String solucion;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta,Topico topico, Usuario usuario){
       this.mensaje = datosRegistroRespuesta.mensaje();
       this.topico =topico;
       this.fechaCreacion=  Date.from(Instant.now());
       this.autor = usuario;
       this.solucion = datosRegistroRespuesta.solucion();
    }

}

