# Step 1: Build the JAR using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Create the final lightweight image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the JAR built in the first step
COPY --from=build /app/target/*.jar bankapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bankapp.jar"]