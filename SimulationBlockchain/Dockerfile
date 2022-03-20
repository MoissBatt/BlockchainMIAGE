FROM maven:3-openjdk-17 AS BUILDER
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B -DskipTests clean package

FROM openjdk:17
COPY --from=builder /app/target/SimulationBlockchain-1.0.jar /app/target/SimulationBlockchain-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/target/SimulationBlockchain-1.0.jar"]