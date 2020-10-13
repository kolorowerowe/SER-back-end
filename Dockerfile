FROM adoptopenjdk/openjdk14:latest
WORKDIR /
EXPOSE 8080
ARG JAR_FILE=./target/ser-back-end-0.0.2.jar
ADD ${JAR_FILE} ser-back-end.jar
CMD ["java","-Xmx128m", "-jar","ser-back-end.jar"]