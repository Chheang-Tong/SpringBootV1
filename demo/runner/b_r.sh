#!/usr/bin/env bash

docker compose up --build -d
docker compose ps
docker compose logs -f app
