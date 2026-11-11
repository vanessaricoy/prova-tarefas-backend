# Backend da prova — tarefas

Este diretório é a raiz de um repositório independente. Consulte o README principal no ZIP para o roteiro Mac. POST /tarefa, GET /tarefa, DELETE /tarefa?id=UUID. Todos exigem access token Auth0; nenhum papel adicional é exigido. PostgreSQL + Flyway. Java 21. Execute `cp .env.example .env`, preencha e depois `docker compose up -d --build`.

A EC2 foi identificada como Ubuntu 24.04 x86_64, IP 3.236.36.11. O deploy usa porta interna do host 8081 e precisa de proxy público ou rede configurada para essa porta. Não publique a senha do banco. `/actuator/health` também é autenticado: 401 sem token é esperado. O pipeline aguarda a API responder 401; a verificação funcional das tarefas deve ser feita após o login.

GitHub Actions: configure EC2_HOST, EC2_USER, EC2_SSH_KEY e EC2_KNOWN_HOSTS nos secrets, e DEPLOY_ENABLED=true nas repository variables. O `.env` real fica somente na EC2, em /opt/prova-tarefas/.env. O pipeline gera imagem no runner e a transfere, sem compilar na EC2. Preserva volumes; sem testes obrigatórios.
