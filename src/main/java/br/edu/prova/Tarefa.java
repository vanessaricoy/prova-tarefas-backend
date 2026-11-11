package br.edu.prova;
import jakarta.persistence.*;
import java.util.UUID;
@Entity @Table(name="tarefas")
public class Tarefa {
 @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id;
 @Column(nullable=false,length=150) public String titulo;
 @Column(nullable=false,length=4000) public String descricao;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) public Prioridade prioridade;
 @Column(nullable=false,length=254) public String emailCriador;
 protected Tarefa() {}
 public Tarefa(NovaTarefa dto){titulo=dto.titulo().trim();descricao=dto.descricao().trim();prioridade=dto.prioridade();emailCriador=dto.emailCriador().trim();}
}
