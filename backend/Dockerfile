FROM openjdk:15-jdk
WORKDIR /app
COPY . ./
RUN ./gradlew build
ARG EX_PORT=8080
EXPOSE ${EX_PORT}
ENTRYPOINT ["java","-jar","./build/libs/backend-0.0.1-SNAPSHOT.jar"] 