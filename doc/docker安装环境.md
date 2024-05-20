# 安装MySQL
sudo docker run -p 3306:3306 --name mysql \
-v /var/lib/docker/volumes/mysql/logs/log:/var/log \
-v /var/lib/docker/volumes/mysql/mysql-files:/var/lib/mysql \
-v /var/lib/docker/volumes/mysql/conf:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:latest

docker exec -it mysql /bin/bash

create user 'learnhub'@'%' identified  by 'learnhub';
grant all privileges on *.* to 'learnhub'@'%' with grant option;
flush privileges;

# 安装redis
cd /var/lib/docker/volumes/
mkdir redis
vi redis.conf

# 这行要注释掉，解除本地连接限制
bind 127.0.0.1 
# 默认yes，如果设置为yes，则只允许在本机的回环连接，其他机器无法连接。
protected-mode no
# 默认no 为不守护进程模式，docker部署不需要改为yes，docker run -d本身就是后台启动，不然会冲突
daemonize no 
# 设置密码
requirepass learnhub
# 持久化
appendonly yes 


docker run --name redis \
-p 6379:6379 \
-v /var/lib/docker/volumes/redis/redis.conf:/etc/redis/redis.conf \
-v /var/lib/docker/volumes/redis:/data \
-d redis:7.0.10 redis-server /etc/redis/redis.conf --appendonly yes


# 安装nacos
docker pull nacos/nacos-server

mkdir -p /var/lib/docker/volumes/nacos/conf
mkdir -p /var/lib/docker/volumes/nacos/data
mkdir -p /var/lib/docker/volumes/nacos/logs
chmod a+w /var/lib/docker/volumes/nacos

docker run -it --name nacos \
-p 8848:8848 \
-e MODE=standalone \
-v /var/lib/docker/volumes/nacos/conf/application.properties:/home/nacos/conf/application.properties \
-v /var/lib/docker/volumes/nacos/data:/home/nacos/data \
-v /var/lib/docker/volumes/nacos/logs:/home/nacos/logs \
-d nacos/nacos-server:v2.2.0

docker run -d \
--name nacos \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9849 \
--privileged=true \
-e JVM_XMS=256m \
-e JVM_XMX=256m \
-e MODE=standalone \
-e NACOS_AUTH_CACHE_ENABLE=enable \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e NACOS_AUTH_IDENTITY_KEY=learnhub \
-e NACOS_AUTH_IDENTITY_VALUE=learnhub \
--restart=always \
nacos/nacos-server:v2.2.1




