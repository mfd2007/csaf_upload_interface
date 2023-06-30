FROM amazoncorretto:18-alpine3.16 AS appbuild
WORKDIR /opt/app
COPY ./ .
RUN ./gradlew clean generateKey bootJar

FROM amazoncorretto:18-alpine3.16
WORKDIR /opt/app
COPY --from=appbuild /opt/app/build/libs/*.jar /opt/app/*.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx2048m", "-jar", "*.jar"]