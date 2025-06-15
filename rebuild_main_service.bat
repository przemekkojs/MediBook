@echo off
echo Shutting down docker compose

docker compose down

echo Rebuilding main service

docker compose build main-service --no-cache

echo Starting docker compose

docker compose up

echo Docker compose started