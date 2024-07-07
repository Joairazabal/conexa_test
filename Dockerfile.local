# Utilizo la imagen de OpenJDK 8
FROM openjdk:8-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia solo los archivos necesarios para evitar una reconstrucción completa en cada cambio
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Realiza una descarga preliminar de dependencias
RUN ./mvnw dependency:go-offline

# Copia el resto del proyecto
COPY src ./src

# Establece el comando por defecto para ejecutar la aplicación
CMD ["./mvnw", "spring-boot:run"]