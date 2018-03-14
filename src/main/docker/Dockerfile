FROM openjdk:8-jdk-alpine
VOLUME /data
RUN chmod 777 /data
RUN mkdir -p /data/incoming
RUN mkdir -p /data/processing
RUN mkdir -p /data/archived
RUN mkdir -p /logs
RUN chmod 777 /logs
ARG env
ARG url
ARG release
ARG artifact
RUN echo "${env}"
RUN echo "${url}${release}${artifact}"
ADD ${url}${release}${artifact} mdm-messaging.jar
RUN chmod 644 mdm-messaging.jar
ENTRYPOINT java -DServerEnvironment="${env}" -jar mdm-messaging.jar