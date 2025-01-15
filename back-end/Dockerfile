# Étape de build
FROM maven:3.8.4-openjdk-8 as builder
WORKDIR /app
# Ajout du volume Maven
VOLUME /root/.m2
COPY . .
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"
# Ajout des options pour le repository Maven
RUN mvn clean package -DskipTests --no-transfer-progress -Dmaven.repo.local=/root/.m2/repository

# Étape de runtime
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
