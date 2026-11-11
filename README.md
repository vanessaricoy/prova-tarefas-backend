# Backend da prova — tarefas

Este diretório é a raiz de um repositório independente. Consulte o README principal no ZIP para o roteiro Mac. POST /tarefa, GET /tarefa, DELETE /tarefa?id=UUID. Todos exigem access token Auth0; nenhum papel adicional é exigido. PostgreSQL + Flyway. Java 21. Execute `cp .env.example .env`, preencha e depois `docker compose up -d --build`.

A EC2 foi identificada como Ubuntu 24.04 x86_64, IP 3.236.36.11. O deploy usa porta interna do host 8081 e precisa de proxy público ou rede configurada para essa porta. Não publique a senha do banco. `/actuator/health` também é autenticado: 401 sem token é esperado. O pipeline aguarda a API responder 401; a verificação funcional das tarefas deve ser feita após o login.

GitHub Actions: configure EC2_HOST, EC2_USER, EC2_SSH_KEY e EC2_KNOWN_HOSTS nos secrets, e DEPLOY_ENABLED=true nas repository variables. O `.env` real fica somente na EC2, em /opt/prova-tarefas/.env. O pipeline gera imagem no runner e a transfere, sem compilar na EC2. Preserva volumes; sem testes obrigatórios.

## Publicação realizada

API: http://3.236.36.11/tarefa. Frontend: https://prova-tarefas-vanessaricoy.vercel.app. Fluxo: navegador → /api/tarefa na Vercel → /tarefa no Caddy da EC2 → 127.0.0.1:8081 → container API:8080.

GitHub Actions concluído: https://github.com/vanessaricoy/prova-tarefas-backend/actions/runs/43034450145

A API e o banco estão em containers próprios do projeto prova-tarefas. Memória limitada a 512 MB para API e 256 MB para banco. Senha do banco fica apenas no .env do servidor. CORS permite a origem Vercel e localhost.
