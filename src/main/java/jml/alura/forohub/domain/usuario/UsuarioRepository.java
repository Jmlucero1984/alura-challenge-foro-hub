package jml.alura.forohub.domain.usuario;

import jml.alura.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    Page<Usuario> findAll(Pageable paginacion);
    Usuario findByNombre(String nombre);
    @Query("SELECT e FROM Usuario e WHERE e.nombre = :nombre")
    UserDetails findByNombreToUserDetails(@Param("nombre") String nombre);



}
