# SUSTech-Football-Backend

## 简介

1. 发布并展示来自体育中心的全局性公告；
2. 允许用户将自己注册为球员、教练、球队管理者、赛事管理者、比赛管理者、裁判等多种身份；
3. 允许用户自行创建球队，并自主进行球队信息、球队成员的管理；
4. 允许用户自行创建赛事（event，赛事示例：“书院杯”），并由我们的系统提供一系列服务；
5. 允许用户自行发起比赛（match，包含基于赛事的比赛，如“书院杯”的一场小组赛；以及独立的一场比赛，如树德和致诚周末约的友谊赛）。

我们希望所有功能基于微信小程序实现，部分管理端功能使用web端实现。

## 需求

见 "需求文档.md" 和 "需求文档-数据库.md"



## Docker

#### 文件结构：

```bash
football
 ├── Dockerfile
 ├── football.jar
 └── haoyu-wang141.top.pfx
```



#### Dockerfile：

pfx文件为https启动所需密钥

```dockerfile
FROM openjdk:17-oracle

WORKDIR /app

COPY football.jar .
COPY haoyu-wang141.top.pfx ../

EXPOSE 8085

CMD ["java", "-jar", "./football.jar"]
```



#### 创建过程

```bash
# 创建image
sudo docker build -t football-springboot .

# 查看docker images
sudo docker images

# 用这个image跑一个container
sudo docker run --name football -d -p 8086:8085 football-springboot

# 查看正在跑的进程
sudo docker ps
```

