# Etapa 1: Compilación del WAR con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Crear directorio de trabajo
WORKDIR /app

# Copiar los archivos necesarios para descargar dependencias primero
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente
COPY src ./src

# Construir el WAR sin ejecutar los tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Jetty
FROM jetty:11-jdk17

# Copiar el WAR generado en la etapa anterior a la carpeta de despliegue de Jetty
COPY --from=builder /app/target/*.war /var/lib/jetty/webapps/libreria-digital.war

# Exponer el puerto por defecto de Jetty
EXPOSE 8080

# Jetty arrancará automáticamente al iniciar el contenedor
