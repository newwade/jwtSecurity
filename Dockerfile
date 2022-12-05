FROM openjdk:11
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp/target
ENTRYPOINT ["java", "-jar","antMatchers-0.0.1-SNAPSHOT.jar"]
