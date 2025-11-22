# 1. Usamos una imagen de Maven para construir el proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Compilamos el .war (saltando tests para ir rápido)
RUN mvn clean package -DskipTests

# 2. Usamos una imagen de Tomcat para ejecutarlo
FROM tomcat:10.1-jdk17
WORKDIR /usr/local/tomcat/webapps/

# Borramos la app por defecto de Tomcat
RUN rm -rf ROOT && rm -rf ROOT.war

# Copiamos tu .war y lo renombramos a ROOT.war para que sea la página principal
COPY --from=build /app/target/fitforlife_pagina-1.0-SNAPSHOT.war ./ROOT.war

# El puerto que usa Render
EXPOSE 8080
CMD ["catalina.sh", "run"]
```

#### 2. Subir todo a GitHub
Render toma el código de GitHub. Tenemos que subir la nueva configuración de `ConexionBD.java` y el `Dockerfile`.

Abre tu **Git Bash** en la carpeta del proyecto y ejecuta:

```bash
git add .
git commit -m "config: Añade Dockerfile y conexión a nube TiDB"
git push origin main