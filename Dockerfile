# Start with a base image containing Java runtime
FROM openjdk:17

# The application's jar file
ARG JAR_FILE=target/task_standalone-1.0-SNAPSHOT.jar

# Copy the JAR file into the container
COPY target/task_standalone-1.0-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]