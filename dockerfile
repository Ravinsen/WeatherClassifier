FROM openjdk:21-jdk-slim

# Arbeitsverzeichnis im Container
WORKDIR /usr/src/app

# Dateien kopieren
COPY src src
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Zeilenumbrüche und Berechtigungen (falls du auf Windows arbeitest)
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw

# Build ausführen
RUN ./mvnw -Dmaven.test.skip=true package

# Port freigeben und App starten
EXPOSE 8080
CMD ["java", "-jar", "/usr/src/app/target/weatherclassifier-0.0.1-SNAPSHOT.jar"]
