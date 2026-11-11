package br.edu.prova;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TarefaRepository extends JpaRepository<Tarefa,UUID> {}
