package jml.alura.forohub.domain.respuesta;

import jml.alura.forohub.domain.perfil.Perfil;
import jml.alura.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RespuestaRepository   extends JpaRepository<Respuesta,Long> {

    Page<Respuesta> findAll(Pageable paginacion);

    Respuesta findByTopicoId(Long id);
}