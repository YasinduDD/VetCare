#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

pkill -9 -f spring-petclinic || echo "Failed to kill any apps"

docker compose kill || echo "No docker containers are running"

echo "Running infra"
docker compose up -d grafana-server prometheus-server tracing-server

echo "Running apps"
mkdir -p target
nohup java -jar spring-petclinic-config-server/target/*.jar --server.port=8888 --spring.profiles.active=mysql > target/config-server.log 2>&1 &
echo "Waiting for config server to start"
sleep 20
nohup java -jar spring-petclinic-discovery-server/target/*.jar --server.port=8761 --spring.profiles.active=mysql > target/discovery-server.log 2>&1 &
echo "Waiting for discovery server to start"
sleep 20
nohup java -jar spring-petclinic-customers-service/target/*.jar --server.port=8081 --spring.profiles.active=mysql > target/customers-service.log 2>&1 &
nohup java -jar spring-petclinic-visits-service/target/*.jar --server.port=8082 --spring.profiles.active=mysql > target/visits-service.log 2>&1 &
nohup java -jar spring-petclinic-vets-service/target/*.jar --server.port=8083 --spring.profiles.active=mysql > target/vets-service.log 2>&1 &
nohup java -jar spring-petclinic-genai-service/target/*.jar --server.port=8084 --spring.profiles.active=mysql > target/genai-service.log 2>&1 &
nohup java -jar spring-petclinic-api-gateway/target/*.jar --server.port=8080 --spring.profiles.active=mysql > target/gateway-service.log 2>&1 &
nohup java -jar spring-petclinic-admin-server/target/*.jar --server.port=9090 --spring.profiles.active=mysql > target/admin-server.log 2>&1 &
echo "Waiting for apps to start"
sleep 60
