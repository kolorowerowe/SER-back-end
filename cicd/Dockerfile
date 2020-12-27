FROM adoptopenjdk/openjdk11:latest
WORKDIR /
EXPOSE 8080
ARG JAR_FILE=./target/ser-back-end-1.0.5.jar
ADD ${JAR_FILE} ser-back-end.jar
CMD ["java","-Xmx128m", "-jar","ser-back-end.jar"]