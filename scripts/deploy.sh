#!/usr/bin/env bash
set -euo pipefail
sha="${1:?SHA obrigatório}"
[[ "$sha" =~ ^[a-f0-9]{40}$ ]] || exit 1
cd /opt/prova-tarefas
[[ -f .env ]] || { echo 'Crie .env no servidor';exit 1; }
exec 9>deploy.lock
flock -n 9 || { echo 'Deploy em execução';exit 1; }
mkdir -p "releases/$sha"
tar -xzf "incoming/release-$sha.tar.gz" -C "releases/$sha"
gzip -dc "incoming/image-$sha.tar.gz" | sudo -n docker load
export API_IMAGE="tarefas-api:$sha"
sudo -n env API_IMAGE="$API_IMAGE" docker compose --project-directory /opt/prova-tarefas --env-file /opt/prova-tarefas/.env -f "releases/$sha/compose.yml" up -d --no-build
for attempt in $(seq 1 60); do
 status=$(curl -s -o /dev/null -w '%{http_code}' http://127.0.0.1:8081/actuator/health || true)
 if [[ "$status" == 401 ]]; then
  printf '%s\n' "$sha" > current-sha
  rm -f "incoming/image-$sha.tar.gz" "incoming/release-$sha.tar.gz"
  echo 'API iniciada com autenticação obrigatória. Valide login e operações pelo frontend.'
  exit 0
 fi
 sleep 3
done
echo 'API não iniciou; consulte logs dos containers prova-tarefas.'
exit 1
