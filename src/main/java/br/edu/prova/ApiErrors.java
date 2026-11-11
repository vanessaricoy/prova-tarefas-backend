package br.edu.prova;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
@RestControllerAdvice
public class ApiErrors {
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){
  var fields=new TreeMap<String,String>();e.getBindingResult().getFieldErrors().forEach(f->fields.put(f.getField(),f.getDefaultMessage()));
  return ResponseEntity.badRequest().body(Map.of("message","Dados inválidos","fields",fields));
 }
 @ExceptionHandler(HttpMessageNotReadableException.class) ResponseEntity<?> json(Exception e){return ResponseEntity.badRequest().body(Map.of("message","JSON inválido. Prioridade deve ser BAIXA, MEDIA ou ALTA."));}
}
