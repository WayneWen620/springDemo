# 使用官方 OpenJDK 17 slim 作為基底
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 複製 Maven build 出的 jar 到容器
COPY target/springDemo-0.0.1-SNAPSHOT.jar app.jar

# 暴露容器內部端口（Spring Boot 默認 8080）
EXPOSE 8080

# 啟動應用
ENTRYPOINT ["java","-jar","app.jar"]