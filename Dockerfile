FROM openjdk:17
EXPOSE 8080
ADD target/social-media-api.jar  social-media-api.jar
ENTRYPOINT ["java","-jar","/social-media-api.jar"]