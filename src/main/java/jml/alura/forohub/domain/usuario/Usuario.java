package jml.alura.forohub.domain.usuario;

import jakarta.persistence.*;
import jml.alura.forohub.domain.perfil.Perfil;
import jml.alura.forohub.domain.topico.DatosRegistroTopico;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
Esta restricción no resultó, no quedó otra que especificarla en el momento mismo de la creaciòn de la tabla.
Tampoco hubo resultados favorables al hacer una migracion adicional para agregar la restriccion.
@Table(name= "usuarios", uniqueConstraints = {
        @UniqueConstraint(name="UQ_EMAIL", columnNames = "correo_electronico")
})*/
@Table(name= "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="correo_electronico")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String correo_electronico;
    private String contraseña;


    @ManyToMany
    @JoinTable(
            name = "usuarios_perfiles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private Set<Perfil> perfiles;




    public Usuario(DatosRegistroUsuario datosRegistroUsuario,Set<Perfil> perfiles) {
        this.nombre = datosRegistroUsuario.nombre();
        this.correo_electronico = datosRegistroUsuario.correoElectronico();
        this.contraseña = datosRegistroUsuario.contraseña();
        this.perfiles = perfiles;


    }
}
