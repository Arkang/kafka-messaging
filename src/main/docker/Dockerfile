FROM openjdk:8-jdk-alpine
#VOLUME /data
#RUN chmod 777 /data
#RUN mkdir -p /data/incoming
#RUN mkdir -p /data/processing
#RUN mkdir -p /data/archived
VOLUME /tmp
RUN chmod 777 /tmp
RUN mkdir -p /tmp/incoming
RUN mkdir -p /tmp/processing
RUN mkdir -p /tmp/archived
RUN mkdir -p /logs
RUN chmod 777 /logs
ARG env
ARG url
ARG release
ARG artifact
RUN echo "${env}"
RUN echo "${url}${release}${artifact}"
#COPY ${sh_dir}/startup.sh startup.sh
#RUN echo "java -DServerEnvironment=${env} -jar /mdm-messaging.jar >logs/test.log 2>&1" > startup.sh
RUN echo '#!/bin/sh' >> startup.sh
RUN echo '' >> startup.sh
RUN echo 'while(true); do' >> startup.sh
RUN echo 'sleep 10' >> startup.sh
RUN echo "echo 'wake up'" >> startup.sh
RUN echo 'done' >> startup.sh
RUN chmod 755 startup.sh
ADD ${url}${release}${artifact} mdm-messaging.jar
RUN chmod 644 mdm-messaging.jar
ENTRYPOINT ["java", "-DServerEnvironment=test", "-jar", "mdm-messaging.jar"]
#ENTRYPOINT ["./startup.sh"]