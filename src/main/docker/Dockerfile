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
echo 'mkdir /tmp/incoming' >> startup.sh && \
echo 'mkdir /tmp/processing' >> startup.sh && \
echo 'mkdir /tmp/archived' >> startup.sh && \
echo '' >> startup.sh && \
echo 'java -DServerEnvironment="$ServerEnvironment" -jar mdm-messaging.jar' >> startup.sh

RUN chmod 755 startup.sh
ADD ${url}${release}${artifact} mdm-messaging.jar
RUN chmod 644 mdm-messaging.jar
ENTRYPOINT ["./startup.sh"]