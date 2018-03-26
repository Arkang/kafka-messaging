FROM openjdk:8-jdk-alpine

VOLUME /tmp
RUN chmod 777 /tmp
RUN mkdir /logs
RUN chmod 777 /logs
ARG url
ARG release
ARG artifact
RUN echo "${url}${release}${artifact}"

RUN echo '#!/bin/sh' > startup.sh && \
echo '' >> startup.sh && \
echo 'mkdir -p /tmp/incoming' >> startup.sh && \
echo 'mkdir -p /tmp/processing' >> startup.sh && \
echo 'mkdir -p /tmp/archived' >> startup.sh && \
echo '' >> startup.sh && \
echo 'java -DServerEnvironment="$ServerEnvironment" -Xms512m -Xmx512m -XX:PermSize=128m -XX:MaxPermSize=256m -XX:NewRatio=1 -Dsun.rmi.dgc.server.gcInterval=3600000 -XX:+UseParallelOldGC -XX:+DisableExplicitGC -verbose:gc -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=logs/OOMheapdump.hprof -jar mdm-messaging.jar' >> startup.sh

RUN chmod 755 startup.sh
ADD ${url}${release}${artifact} mdm-messaging.jar
RUN chmod 644 mdm-messaging.jar
ENTRYPOINT ["./startup.sh"]