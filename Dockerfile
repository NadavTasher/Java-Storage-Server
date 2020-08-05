# Select the base image
FROM openjdk:16-slim

# Expose port
EXPOSE 3335

# Copy program
COPY out/artifacts/Storage.jar /home/application.jar

# Run program
CMD ["/usr/local/openjdk-16/bin/java", "-jar", "/home/application.jar"]