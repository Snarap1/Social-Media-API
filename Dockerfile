FROM openjdk:17
EXPOSE 8080
ADD target/smart-cart-repo.jar  smart-cart-repo.jar
ENTRYPOINT ["java","-jar","/smart-cart-repo.jar"]