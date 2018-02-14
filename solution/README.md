README
====
This module uses Spring Boot. To run the Spring Boot application you need to have Java and Maven installed. The application also uses MySQL database.

Database configuration
--------------

You will need to create a database schema on your MySQL server. The database schema can be empty. The application will create all needed tables automatically.

Before running the application, database configuration must be prepared. To do so, create a text file (any file name) with the following content (or use a template file in `solution/database.propertiesTemplate`):

spring.datasource.url=jdbc:mysql://`<your_db_server>`:`<your_db_port>`/`<your_schema>`  
spring.datasource.username=`<your_schema_username>`  
spring.datasource.password=`<your_schema_password>`

where:  
`<your_db_server>` is an IP of your MySQL server, might be localhost  
`<your_db_port>` is a port on which your MySQL server is running  
`<your_schema>` is your schema name on MySQL server  
`<your_schema_username>` is a username used to connect to your schema  
`<your_schema_password>` is a password used to connect to your schema

Running the application
--------------
First of all, you need to use Gulp to prepare static resources for the application. From terminal run the _gulp dev_ command from `backend-coding-challenge` directory.
> Hint: to install Gulp execute _npm install -g gulp_

To run the application execute the following Maven command from `backend-coding-challenge/solution` directory:

_mvn spring-boot:run -Dspring.config.location=file:`<path_to_db_configuration>`_

where `<path_to_db_configuration>` is a path to file with database configuration you have created before.

Your application will then be running at `localhost:8080`

> Note: you can also run the application this way:

_mvn clean install_  
_java -jar target/codingtest-1.0.0-SNAPSHOT.jar --spring.config.location=file:`<path_to_db_configuration>`_

This way you can take the jar file to any server and run the application there.

Running in IntelliJ IDEA
--------------
To run the application from IntelliJ IDEA create new run configuration. Choose configuration of type `Spring Boot` with the following properties:

**Main class**: com.engage.codingtest.CodingTestApplication  
**Program arguments**: --spring.config.location=file:`<path_to_db_configuration>`  
**Use classpath of module**: coding-test
