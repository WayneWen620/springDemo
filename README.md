# CRC BACKEND 專案說明
### 現況說明
#### 一、開發版本
- JAVA 17 (請使用OPENJDK)
- SPRING BOOT 3.X

#### 二、開發環境

### 專案說明
#### 一、docker
1. 啟動 MySQL + Redis docker-compose up -d
2. 查看 log docker-compose logs -f mysql 
3. 查看 log docker-compose logs -f redis 
4. 停止 docker-compose down
5. 啟動後本地會產生/mysql_data/  /redis_data/ 放 MySQL 與 Redis 的資料檔（db、log 等）
