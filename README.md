# Repo tabr
Technical and business radar. Spring boot based application

# Installation antora
* install nvm by command: brew install nvm and make post configuration
* install nodejs by command: nvm install --lts
* install nvm by command: brew install asciidoctor

# Useful commands:
* build package by command: mvn clean package -Dmaven.test.skip
* run postsgresql by command: docker-compose -f ./postgresql.yml up
* run application by command: mvn spring-boot:run
* run application with profile by command:  mvn spring-boot:run -Pdev
* run database migration by command: mvn liquibase:update
