FROM gradle:6.2.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon 

FROM openjdk:11-slim
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=build /home/gradle/src/build/libs/*.jar $APP_HOME/postservice.jar
EXPOSE 8080
CMD ["java","-jar","postservice.jar"]
