#!/bin/bash

# 拉取最新代码
git pull
if [ $? -ne 0 ]; then
    echo "git pull 失败，脚本退出"
    exit 1
fi
echo "git pull 拉取仓库成功"

# 进入项目目录
cd springboot/ || exit

# 检查 application.yml 中的配置
if grep -q 'active: server' src/main/resources/application.yml; then
    echo "配置文件设置正确"
else
    echo "配置文件中的 active: 不是 server，正在更新配置文件"
    # 使用 sed 命令更新配置文件
    sed -i 's/active: .*/active: server/' src/main/resources/application.yml
    if [ $? -ne 0 ]; then
        echo "更新配置文件失败，脚本退出"
        exit 1
    else
        echo "更新配置文件成功"
    fi
fi

# 使用 Maven Wrapper 构建项目
./mvnw clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "构建失败，脚本退出"
    exit 1
fi

# 进入目标目录
cd target/ || exit

# 优雅地停止旧进程
OLD_PID=$(lsof -t -i:8085)
if [ -n "$OLD_PID" ]; then
    kill "$OLD_PID"
    sleep 10
fi

# 启动新进程
nohup java -jar football.jar > ./nohup.out 2>&1 &
JAVA_PID=$!
echo "正在检查应用进程..."

# 短暂等待，以确保操作系统有时间将进程置于运行状态
sleep 5

# 检查进程是否存在
if ps -p $JAVA_PID > /dev/null; then
   echo "应用启动中，请稍候...，PID: $JAVA_PID"
else
   echo "应用进程启动失败。"
   cat ./nohup.out
   exit 1
fi

# 短暂等待，以确保操作系统有时间将进程置于运行状态
sleep 5

# 检查进程是否存在
if ps -p $JAVA_PID > /dev/null; then
   echo "应用启动中，请稍候...，PID: $JAVA_PID"
else
   echo "应用进程启动失败。"
   cat ./nohup.out
   exit 1
fi

# 初始化计数器
count=0

# 最多检查10次
while [ $count -lt 10 ]; do
    # 检查日志以确认应用启动成功
    if grep -q 'Started SpringbootApplication in' ./nohup.out; then
        echo "应用成功启动。"
        break
    else
        if grep -q "org.postgresql.util.PSQLException" ./nohup.out; then
            echo "数据库连接发生错误! 程序终止"
            exit 1
        fi
        echo "应用仍在启动中..."
    fi

    # 每次循环等待10秒
    sleep 10
    count=$((count+1))
done

# 如果10次尝试后应用未能启动
if [ $count -eq 10 ]; then
    echo "应用启动失败，请检查日志以获取更多信息。"
    # 杀掉启动失败的 Java 进程
    kill $JAVA_PID
    exit 1
fi

# 输出日志
cat ./nohup.out
