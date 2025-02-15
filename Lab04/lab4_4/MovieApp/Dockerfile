FROM maven:3.8.5-openjdk-17

WORKDIR /MovieApp
COPY . .
RUN mvn clean 

CMD mvn spring-boot:run