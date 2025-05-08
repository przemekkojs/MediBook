#!/bin/bash

echo "Shutting down docker compose"

docker compose down

docker network rm bridge_network

echo "Rebuilding main service"

docker compose build main-service --no-cache

echo Rebuilding keycloak service

docker compose build keycloak_web --no-cache

echo "Rebuilding notification service"

docker compose build notification-service --no-cache

echo "Starting docker compose"

docker network create --gateway 172.16.1.1 --subnet 172.16.1.0/24 bridge_network

docker compose up

echo "Docker compose started"