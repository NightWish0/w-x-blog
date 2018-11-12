FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} wxblog.jar
RUN bash -c 'touch /wxblog.jar'
EXPOSE 8000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/wxblog.jar"]
