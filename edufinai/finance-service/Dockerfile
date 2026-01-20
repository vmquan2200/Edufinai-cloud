# Dockerfile cho Finance Service - Railway Optimized
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copy pom.xml trước để cache dependencies
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Copy source code và build
COPY src src
RUN mvn -q -B clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Railway tự động inject PORT
ENV PORT=8203
ENV SPRING_PROFILES_ACTIVE=railway

# Copy JAR file từ builder stage
COPY --from=builder /workspace/target/*.jar /app/app.jar

# Expose port (Railway sẽ override PORT env var)
EXPOSE $PORT

# Run application với PORT từ environment variable
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=$PORT /app/app.jar"]
