CREATE TABLE tarefas (
 id UUID PRIMARY KEY,
 titulo VARCHAR(150) NOT NULL,
 descricao VARCHAR(4000) NOT NULL,
 prioridade VARCHAR(10) NOT NULL CHECK (prioridade IN ('BAIXA','MEDIA','ALTA')),
 email_criador VARCHAR(254) NOT NULL
);
