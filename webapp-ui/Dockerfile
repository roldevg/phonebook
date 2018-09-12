LABEL maintainer="Evgeniy Roldukhin<evgeniy@roldukhin.ru>"
FROM openjdk:8-jdk-alpine
COPY /target/phonebook.war demo.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.war"]
