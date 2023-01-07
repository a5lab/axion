# Repo tabr

Technical and business radar. Spring boot based application

# Release application
* update version at antora.yml file
* run command mvn release and provide information
* commit changes and create a new git tag
* run manual release actions at GitHub action with a new tag
* provide information about release at GitHub
* press button publish release at GitHub

# Idea configuration

## Java checkstyle

* see overview https://plugins.jetbrains.com/plugin/1065-checkstyle-idea
* see GitHub https://github.com/jshiell/checkstyle-idea/blob/main/README.md
* import google_checkstyle.xml into idea java import schema at java code style

## JavaScript checkstyle

* setup ident 2 at JavaScript code style

## HTML checkstyle

* setup ident 2 at html code style
* remove all items from "do not indent child of" field

# Useful commands:

* run postsgresql by command: docker-compose -f ./postgresql.yml up
* obtain metrics by command: curl http://127.0.0.1:8080/actuator/prometheus
* build package by command: ./mvnw clean package -Dmaven.test.skip
* run application by command: ./mvnw spring-boot:run
* run application with profile by command: ./mvnw spring-boot:run -Pdev
* run application with by command: java -jar tabr-x.y.z.jar --application.keys.google_analytics=123
* run database migration by command: ./mvnw liquibase:update
* create checkstyle report by command: ./mvnw checkstyle:checkstyle
* get coverage report by command: ./mvnw jacoco:report -Pdev
* create javadoc by command: ./mvnw javadoc:javadoc
* create cross reference source by command: ./mvnw jxr:jxr
* create site by command: ./mvnw site
* run spotbugs GUI by command: ./mvnw spotbugs:gui
* run sonarqube analysis by command: ./mvnw sonar:sonar
* install js minifier by command: npm install uglify-js -g t
* minify jscript by command: uglifyjs application.js -o application.min.js -c -m
* install css minifier by command: npm install -g uglifycss
* minify css by command: uglifycss application.css --output application.min.css 
* install nvm by command: brew install nvm and make post configuration
* install nodejs by command: nvm install --lts
* install asciidoctor by command: brew install asciidoctor

# Working with embedded H2 DB(In-Memory)

* run application by command: "mvn spring-boot:run -Pdev"
* enter http://localhost:8080/h2-console to browser
* enter "jdbc:h2:mem:tabr" into JDBC URL field
* enter "tabr" into User Name field
* enter "secret" into Password field

# Inspired by 

* https://github.com/thoughtworks/build-your-own-radar
* https://github.com/zalando/tech-radar
