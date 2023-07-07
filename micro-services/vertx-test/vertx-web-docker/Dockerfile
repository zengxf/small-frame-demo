FROM openjdk:8-jre-alpine

COPY build/libs/vertx-web-docker-0.0.1.jar /app/vertx-web.jar

RUN ls -lrt
RUN ls -lrt /app

ENV RUN_LOG=/app/run.log
ENV SERVER_PORT=8081

ENTRYPOINT java \
    -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+CMSPermGenSweepingEnabled \
    -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 \
    -Dstart.desc=vertx-web \
    -Dserver.port=$SERVER_PORT \
    -jar \
    /app/vertx-web.jar &> $RUN_LOG && sleep 120s

# `&& sleep 120s` 暂停 2 分钟，是为了进入查看 `/app/run.log` 日志