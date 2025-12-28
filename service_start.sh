#! /bin/bash
git restore .
git pull --rebase
docker compose down
docker image prune -fa
docker compose pull dommern
docker compose up -d dommern
curl --fail --retry 20 --retry-delay 5 http://localhost:8080/actuator/health
