package jml.alura.forohub.domain.topico;

import jml.alura.forohub.domain.curso.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico,Long>  {

    Optional<Topico> findTopicoById(Long id);
    Page<Topico> findByStatusTrue(Pageable paginacion);
    Topico findByTitulo(String titulo);

    Page<Topico> findByCurso(Pageable paginacion, Curso curso);

    @Query("SELECT e FROM Topico e WHERE e.fechaCreacion BETWEEN :startDate AND :endDate")
    Page<Topico> findAllByCreationDateBetween(Pageable paginacion, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
