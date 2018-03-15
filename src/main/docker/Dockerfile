FROM openjdk:8-jdk-alpine
#VOLUME /data
#RUN chmod 777 /data
#RUN mkdir -p /data/incoming
#RUN mkdir -p /data/processing
#RUN mkdir -p /data/archived
VOLUME /tmp
RUN chmod 777 /tmp
#RUN mkdir /tmp/incoming
#RUN mkdir /tmp/processing
#RUN mkdir /tmp/archived
#RUN chmod 777 /tmp/incoming
#RUN chmod 777 /tmp/processing
#RUN chmod 777 /tmp/archived
RUN mkdir /logs
RUN chmod 777 /logs
ARG env
ARG url
ARG release
ARG artifact
RUN echo "${env}"
RUN echo "${url}${release}${artifact}"
#COPY ${sh_dir}/startup.sh startup.sh
#RUN echo "java -DServerEnvironment=${env} -jar /mdm-messaging.jar >logs/test.log 2>&1" > startup.sh

RUN echo "$ServerEnvironment" >
RUN echo '#!/bin/sh' >> startup.sh
RUN echo '' >> startup.sh
# checking environment variables
RUN echo 'echo $ServerEnvironment >> /tmp/env.txt' >> startup.sh
RUN echo 'echo $log4j_configurationFile >> /tmp/env.txt' >> startup.sh
RUN echo '' >> startup.sh
RUN echo 'mkdir /tmp/incoming' >> startup.sh
RUN echo 'mkdir /tmp/processing' >> startup.sh
RUN echo 'mkdir /tmp/archived' >> startup.sh
RUN echo '' >> startup.sh
RUN echo 'java -DServerEnvironment=test -jar mdm-messaging.jar' >> startup.sh
RUN chmod 755 startup.sh
ADD ${url}${release}${artifact} mdm-messaging.jar
RUN chmod 644 mdm-messaging.jar
#ENTRYPOINT ["java", "-DServerEnvironment=test", "-jar", "mdm-messaging.jar"]
ENTRYPOINT ["./startup.sh"]