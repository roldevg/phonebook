FROM openjdk:8-jdk-alpine
LABEL maintainer="Evgeniy Roldukhin<evgeniy@roldukhin.ru>"
COPY /target/phonebook.war demo.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.war"]
