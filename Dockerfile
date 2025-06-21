# Use the official OpenJDK 21 image
FROM openjdk:21

# Set working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/employee-app-caching.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
