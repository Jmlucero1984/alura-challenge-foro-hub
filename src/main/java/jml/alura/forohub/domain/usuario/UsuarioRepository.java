package jml.alura.forohub.domain.usuario;

import jml.alura.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    Page<Usuario> findAll(Pageable paginacion);
    Usuario findByNombre(String nombre);
}
