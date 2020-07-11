#!/bin/bash
mvn clean package -f ./auth/pom.xml -DskipTests 
mvn clean package -f ./config-server/pom.xml -DskipTests 
mvn clean package -f ./eureka-server/pom.xml -DskipTests  
mvn clean package -f ./fornecedor/pom.xml -DskipTests 
mvn clean package -f ./loja/pom.xml -DskipTests 
mvn clean package -f ./transportador/pom.xml -DskipTests 
mvn clean package -f ./zuul/pom.xml -DskipTests

docker-compose build
docker-compose up