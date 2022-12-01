# Repo tabr
Technical and business radar. Spring boot based application

# Installation antora
* install nvm by command: brew install nvm and make post configuration
* install nodejs by command: nvm install --lts
* install nvm by command: brew install asciidoctor

# Useful commands:
* build package by command: mvnw clean package -Dmaven.test.skip
* run postsgresql by command: docker-compose -f ./postgresql.yml up
* run application by command: mvnw spring-boot:run
* run application with profile by command:  mvnw spring-boot:run -Pdev
* run database migration by command: mvnw liquibase:update
* obtain metrics by command: curl http://127.0.0.1:8080/actuator/prometheus

