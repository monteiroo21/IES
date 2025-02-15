# NMEC-114547

# LAB 1

## 1.1
    - install java 21
    - install maven (mvn --version)
    - change JAVA_HOME:
        - add in ~/.bashrc the command: 
            export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"

## 1.2
    - Creating a maven project:
        - groupId (started by 'com.') and artifactId should be specific for each project and are named by me
        - Default version --> '1.0-SNAPSHOT'
    - Possible to add properties (dev team, charachter encoding, or java version) with the properties tag (pom.xml)
    - Installed the 2.11.0 version of retrofit (through the dependencies in pom.xml)
    - mvn package  -->  get dependencies, compiles the project and creates the jar
    - mvn exec:java -Dexec.mainClass="com.myweatherradar.app.WeatherStarter" (allows command line arguments)

## 1.3
    - git clone <SSH_KEY> location2 (to clone the repository into a different location)
    - add log4j to the project dependencies
    - private static Logger logger = LogManager.getLogger(WeatherStarter.class); (to initialize the logger)
        - logger.info (Information messages)
        - logger.debug (Debug messages)
        - logger.error (Error messages)

## 1.4
    - Install Docker Desktop
    - Add user to the docker group (https://docs.docker.com/engine/install/linux-postinstall/) 
    - Install the Portainer app
    - Important commands:
        - docker ps (-a)
        - docker run -d -p 8000:8000 -p 9000:9000 -p 9443:9443 --name portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce:2.21.2 
        - docker stop <reference>
        - docker rm <reference>
    - Docker Compose Quickstart:
        - app.py
        - requirements.txt
        - Dockerfile
        - compose.yaml (had to change the port to 8001, since 8000 was already in use)
        - infra.yaml

## 1.5
    - java.awk.Toolkit, java.util.Timer, java.util.TimerTask to use a timer for periodic requests
    - Dockerizing an aplication:
        - mvn package
        - docker image build -t docker-java-jar:latest .
        - docker run docker-java-jar:latest

