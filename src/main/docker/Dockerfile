FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
RUN echo "$JAR_FILE"
RUN mkdir -p /logs
RUN chmod 777 /logs
#COPY $JAR_FILE mdm-messaging.jar
COPY com.optumrx.ms.mdm/mdm-messaging/0.0.1-SNAPSHOT/mdm-messaging-0.0.1-SNAPSHOT.jar mdm-messaging.jar
ENTRYPOINT [ "java", "-jar", "mdm-messaging.jar" ]