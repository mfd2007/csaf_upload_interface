FROM amazoncorretto:20-alpine3.16-jdk AS appbuild
WORKDIR /opt/app
COPY ./ .
RUN ./gradlew clean generateKey bootJar

FROM amazoncorretto:20-alpine3.16-jdk
WORKDIR /opt/app
COPY --from=appbuild /opt/app/build/libs/*.jar /opt/app/*.jar

EXPOSE 8080 8443
ENTRYPOINT ["java", "-Xmx2048m", "-jar", "*.jar"]