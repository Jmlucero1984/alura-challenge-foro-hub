package jml.alura.forohub.domain.topico;

import jakarta.validation.constraints.Size;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository topicoRepository;


    @DisplayName("Debería retornar los topicos con el año de creación especificado")
    @ParameterizedTest
    @ValueSource(ints = {2020,2021,2022,2023,2024})
    void findAllByCreationDateBetween(int año) {


        var topicosTodos =topicoRepository.findAll();
       int cantTopicosSegunAño =  (int) topicosTodos.stream().filter(topico -> (topico.getFechaCreacion().getYear()+1900)==año).count();
        System.out.println("CANTIDAD CONTADOS: "+cantTopicosSegunAño);
        Date startDate = obtenerDateInicioAño(año);
        Date endDate =obtenerDateInicioAño(año+1);
        Pageable pageable= Pageable.ofSize(10);
        var topicosDelAño = (int) topicoRepository.findAllByCreationDateBetween(pageable,startDate,endDate).stream().count() ;
        Assertions.assertEquals(cantTopicosSegunAño,topicosDelAño);

    }

    public Date obtenerDateInicioAño (int año ){
        Calendar startCal = Calendar.getInstance();
        startCal.set(año, Calendar.JANUARY, 1, 0, 0, 0);
        return startCal.getTime();
    }
}