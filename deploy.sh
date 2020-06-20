#!/usr/bin/env bash
docker-compose build
docker-compose down
docker-compose up -d
docker-compose logs -f --tail=100 auth | ccze -o nolookups
