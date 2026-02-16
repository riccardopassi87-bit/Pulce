# Use a Java JDK image
FROM eclipse-temurin:21-jdk

# Create the folder inside the container
WORKDIR /app

# Copy the JAR file from your target folder
# Note: Ensure you have run 'mvn clean package' so the jar exists!
COPY pulce-backend/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]