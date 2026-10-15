# ---- Stage 1: build (compiles your code into a .jar) ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# pom.xml first: the dependency download stays cached until pom.xml changes
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ---- Stage 2: run (JRE only, no Maven, no source code) ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build /app/target/*.jar app.jar

USER spring:spring
EXPOSE 8080

# Makes the JVM size its memory from the container's limit, not the whole host's
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

# Needs spring-boot-starter-actuator in your pom.xml (see notes)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget -qO- http://127.0.0.1:8080/actuator/health || exit 1

# "exec" lets Java receive Docker's stop signal directly, so it shuts down gracefully.
# SPRING_PROFILES_ACTIVE is read by Spring straight from the environment.
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
