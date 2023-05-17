# Repo axion

Technical and business radar. Spring boot based application

# Release application
* update version at antora.yml file
* run command mvn release and provide information
* create archive by command: tar -zcvf Binaries.tar.gz axion*.jar
* commit changes and create a new git tag
* create a new release at GitHub and provide information
* press button publish release at GitHub

# Setup environment

## Windows environment
* download and install java, at least jdk-18
* create JAVA_HOME environment variable with value C:\Program Files\Java\jdk-19
* exit and run console again to apply environment variables
* download and install maven, at least 3.8.7
* create JAVA_HOME environment variable with value C:\apache-maven-3.8.7
* exit and run console again to apply environment variables
* download and setup nodeJS, at least 18
* install uglifycss by command: npm install -g uglifycss
* install uglifyjs by command: npm install uglify-js -gt
* setup GitHub account and add ssh keys to GitHub profile
* clone repo by command git clone: git@github.com:a5lab/axion.git
* build application by command: mvn clean package -Pdev -Dmaven.test.skip from root folder
* run application by command: mvn spring-boot:run -Pdev from root folder
* open browser with url http://127.0.0.1:8080 to view application

## Working with embedded H2 DB(In-Memory)

* run application by command: "mvn spring-boot:run -Pdev"
* enter http://localhost:8080/h2-console to browser
* enter "jdbc:h2:mem:axion" into JDBC URL field
* enter "axion" into User Name field
* enter "secret" into Password field

## Idea configuration

### Java checkstyle

* see overview https://plugins.jetbrains.com/plugin/1065-checkstyle-idea
* see GitHub https://github.com/jshiell/checkstyle-idea/blob/main/README.md
* import google_checkstyle.xml into idea java import schema at java code style
* setup "hard wrap at" value to 120

### JavaScript checkstyle

* setup ident 2 at JavaScript code style

### HTML checkstyle

* setup ident 2 at html code style
* remove all items from "do not indent child of" field

# Conventions

## Git conventions

* the first letter of the commit should be written in upper case
* the simple perfect should be used for commit message
* the title and description should be provided, for example by command: git commit -m "title" -m "description"

# Useful commands:

* run postsgresql by command: docker-compose -f ./postgresql.yml up
* obtain metrics by command: curl http://127.0.0.1:8080/actuator/prometheus
* build package by command: ./mvnw clean package -Dmaven.test.skip
* run application by command: ./mvnw spring-boot:run
* run application with profile by command: ./mvnw spring-boot:run -Pdev
* run application with by command: java -jar axion-x.y.z.jar --application.keys.google_analytics=123
* run application with by command: java -jar axion-x.y.z.jar --spring.liquibase.label-filter=de
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
* setup tag by command: git tag -v0.1.0 && git push origin --tags
* prune tags by command: git fetch --prune --prune-tags
* run psql console by command: su - postgres and run psql

# Inspired by 

* https://github.com/thoughtworks/build-your-own-radar
* https://github.com/zalando/tech-radar
