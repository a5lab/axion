# Repo tabr
Technical and business radar. Spring boot based application

# Installation antora
* install nvm by command: brew install nvm and make post configuration
* install nodejs by command: nvm install --lts
* install nvm by command: brew install asciidoctor

# Installation checkstyle-ide
* see overview https://plugins.jetbrains.com/plugin/1065-checkstyle-idea
* see GitHub https://github.com/jshiell/checkstyle-idea/blob/main/README.md

# Useful commands:
* run postsgresql by command: docker-compose -f ./postgresql.yml up
* build package by command: mvnw clean package -Dmaven.test.skip
* run application by command: mvnw spring-boot:run
* run application with profile by command: mvnw spring-boot:run -Pdev
* run database migration by command: mvnw liquibase:update
* get coverage report by command: mvn jacoco:report -Pdev
* create checkstyle report by command: mvn checkstyle:checkstyle
* create site by command: mvn site
* create javadoc by command: mvn javadoc:javadoc
