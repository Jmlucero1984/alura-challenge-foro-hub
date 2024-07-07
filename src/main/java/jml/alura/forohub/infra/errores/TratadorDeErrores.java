package jml.alura.forohub.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratadorDeErrores {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity errorHandlerEmailDuplicado(Exception e){
        String cause = e.getCause().toString();
       if(cause.contains("Duplicate") &&  cause.contains("usuarios.correo_electronico")) {
           return ResponseEntity.badRequest().body("El email ya está registrado");
       }
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity errorDeIntegridad(Exception e){
        String cause = e.getCause().toString();
        if(cause.contains("could not execute statement")) {
            return ResponseEntity.badRequest().body("No se pudo hacer la consulta");
        }
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity errorElementoInexistente(Exception e){


        return ResponseEntity.badRequest().body("No existe el elemento buscado");
    }

}