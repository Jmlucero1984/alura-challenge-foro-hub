package jml.alura.forohub.domain.perfil;

import jml.alura.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository   extends JpaRepository<Perfil,Long> {

    Page<Perfil> findAll(Pageable paginacion);

    Perfil findByNombre(String nombre);
}
