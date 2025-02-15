# NMEC-114547

# LAB 2

## 2.1
### Implementing a Servlet example using Jetty:
- Creating a maven project (as learned in Lab 1)
- Add jetty-server and jetty.servlet dependencies (in pom.xml)
- Start a Jetty Server in a java file with a ServletHandler:

```
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
 
public class EmbeddedJettyExample {
 
    public static void main(String[] args) throws Exception {
         
        Server server = new Server(8680);       
         
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
                 
        servletHandler.addServletWithMapping(HelloServlet.class, "/");
         
        server.start();
        server.join();
 
    }
     
    public static class HelloServlet extends HttpServlet 
    {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>New Hello Simple Servlet</h1>"); 
               } 
        }
 }
```

## 2.2
### Introduction to Apache Tomcat
- High-performance, dedicated application servers to run web artifacts, instead of using embedded servers
- We ran Tomcat in a Docker container
- docker-compose.yml (to prepare a Docker based deployment):
```
version: '3.8'
services:
  tomcat-10-0-11-jdk17:
    image: tomcat:10.0-jdk17
    ports:
#     expose tomcat port 8080(container) on host as port 8888(host)
      - "8888:8080"
#     expose java debugging port 5005 on host as port 5005  (HOST:CONTAINER)
      - "5005:5005"
    command: "catalina.sh run"
    volumes:
#     "host path to the directory with .war file" / "container tomcat directory with webapps"
      - "./target:/usr/local/tomcat/webapps"
    environment:
      JAVA_OPTS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
#      JAVA_OPTS: "JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```
- docker-compose up (to be able to run the application)

## 2.3
### Introduction to Spring Boot
- ./mvnw spring-boot:run (to run the Spring Boot web application)
- to change the port where we run, we should go to the application.properties file and define manually the server.port
- mvn -N wrapper:wrapper: command that creates a maven wrapper script
- Thymeleaf: a view technology to perform server-side rendering of the HTML

## 2.4
### Implementing an application
- Creation of an web service to offer random quotes from movies.
- 3 GET methods:
    - /quote -> returns a random quote
    - /shows -> list of all movies
    - /quotes?show=<show_id> -> returns a quote from a specified movie
  