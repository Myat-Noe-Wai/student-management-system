# Use OpenJDK 17
FROM openjdk:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy JAR into container
COPY target/student-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]
