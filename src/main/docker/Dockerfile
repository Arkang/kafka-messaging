FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir -p /logs
RUN chmod 777 /logs
#COPY $JAR_FILE mdm-messaging.jar
ARG url
ARG release
ARG artifact
#ADD mdm-messaging-*.jar mdm-messaging.jar
RUN echo "${url}${release}${artifact}"
ADD ${url}${release}${artifact} mdm-messaging.jar
RUN chmod 644 mdm-messaging.jar
ENTRYPOINT [ "java", "-jar", "mdm-messaging.jar" ]