# 1. Use an official Java Runtime as the base (The "Plate")
FROM eclipse-temurin:17-jdk-alpine

# 2. Set the working folder inside the container
WORKDIR /app

# 3. Copy the built jar file into the container (The "Food")
# Note: Make sure you run 'mvn clean package' first!
COPY target/*.jar app.jar

# 4. Tell Docker the app listens on port 8080
EXPOSE 8080

# 5. The command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]