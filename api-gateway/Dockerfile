FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar

# Expose the port that your Spring Boot application listens on (if needed)
EXPOSE 8080

# Command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
