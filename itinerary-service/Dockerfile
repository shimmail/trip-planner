# 使用官方 OpenJDK 镜像作为基础镜像
FROM openjdk:8-jdk-slim

# 设置时区为 Asia/Shanghai
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#   设置工作目录
WORKDIR /app

# 将应用的 jar 包复制到容器内
COPY target/itinerary-service-0.0.1-SNAPSHOT.jar /app/itinerary-service-0.0.1-SNAPSHOT.jar

# 暴露应用运行的端口
EXPOSE 8082

# 启动应用
ENTRYPOINT ["java", "-jar", "itinerary-service-0.0.1-SNAPSHOT.jar"]