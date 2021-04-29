FROM maven:3.6.3-openjdk-8-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:8-jre-slim
LABEL maintainer="https://github.com/geekyouth"
ENV LANG=zh_CN.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY --from=build /app/target/*.jar /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=GMT+08", "-Dfile.encoding=UTF-8", "-Dsun.jnu.encoding=UTF-8", "-jar", "/app.jar"]


# mvn clean package -DskipTests=true -f pom.xml
# APP_VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)
# docker build . -t geekyouth/crack-dbeaver-password:latest -f Dockerfile
# docker login -u geekyouth
# docker push geekyouth/crack-dbeaver-password:latest
# docker run -d -p 18080:8080 --name crack-dbeaver-password-18080 geekyouth/crack-dbeaver-password:latest
# docker rm crack-dbeaver-password-18080
# docker rmi geekyouth/crack-dbeaver-password:0.0.1
# Spring Boot 多样化构建 Docker 镜像 - 哈喽沃德先生 https://mrhelloworld.com/spring-boot-docker

# 自建 docker registry
# http://192.168.8.13:5000/v2/_catalog
# docker tag geekyouth/crack-dbeaver-password:latest 192.168.8.13:5000/geekyouth/crack-dbeaver-password:latest
# docker push 192.168.8.13:5000/geekyouth/crack-dbeaver-password:latest
