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
* obtain metrics by command: curl http://127.0.0.1:8080/actuator/prometheus
* build package by command: mvnw clean package -Dmaven.test.skip
* run application by command: mvnw spring-boot:run
* run application with profile by command: mvnw spring-boot:run -Pdev
* run database migration by command: mvnw liquibase:update
* create checkstyle report by command: mvn checkstyle:checkstyle
* get coverage report by command: mvn jacoco:report -Pdev
* create javadoc by command: mvn javadoc:javadoc
* create cross reference source by command: mvn jxr:jxr
* create site by command: mvn site
* obtain metrics by command: curl http://127.0.0.1:8080/actuator/prometheus
* run spotbugs GUI command: mvn spotbugs:gui
* run sonarqube analysis command: mvn sonar:sonar (pre-condition: sonar is running locally using docker-compose
  sonar.yml file)


