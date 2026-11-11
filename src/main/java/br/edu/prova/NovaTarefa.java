package br.edu.prova;
import jakarta.validation.constraints.*;
public record NovaTarefa(
 @NotBlank @Size(max=150) String titulo,
 @NotBlank @Size(max=4000) String descricao,
 @NotNull Prioridade prioridade,
 @NotBlank @Email @Size(max=254) String emailCriador
) {}
