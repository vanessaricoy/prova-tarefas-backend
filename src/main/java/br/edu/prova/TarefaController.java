package br.edu.prova;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/tarefa")
public class TarefaController {
 private final TarefaRepository repository;
 public TarefaController(TarefaRepository repository){this.repository=repository;}
 @PostMapping public ResponseEntity<Tarefa> criar(@Valid @RequestBody NovaTarefa dto){
  return ResponseEntity.status(HttpStatus.CREATED).body(repository.saveAndFlush(new Tarefa(dto)));
 }
 @GetMapping public List<Tarefa> listar(){return repository.findAll(Sort.by("titulo").ascending().and(Sort.by("id")));}
 // DELETE /tarefa?id=UUID mantém exatamente o caminho solicitado no enunciado.
 @DeleteMapping public ResponseEntity<Void> deletar(@RequestParam UUID id){
  var found=repository.findById(id);
  if(found.isEmpty())return ResponseEntity.notFound().build();
  repository.delete(found.get());return ResponseEntity.noContent().build();
 }
}
