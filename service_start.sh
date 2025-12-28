#! /bin/bash
git restore .
git pull --rebase
docker compose down
docker image prune -fa
docker compose pull dommern
docker compose up dommern
