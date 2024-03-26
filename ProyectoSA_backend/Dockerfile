# Usa una imagen de Java como base
FROM maven:3.9.0-amazoncorretto-17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR generado por Maven
COPY src /app/src/
COPY pom.xml /app/pom.xml

RUN mvn -f /app/pom.xml clean package
#
# Package stage
#
FROM amazoncorretto:17

COPY --from=0 /app/target/ticketSys-1.1.jar /usr/local/lib/ticketSys.jar
EXPOSE 33065
ENTRYPOINT ["java", "-jar", "/usr/local/lib/ticketSys.jar"]
