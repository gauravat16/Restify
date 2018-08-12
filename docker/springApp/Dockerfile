FROM openjdk:8u171-jre

ADD restify-1.0.jar  restify-1.0.jar
CMD mkdir -p /usr/local/var/Restify/Configuration
COPY Restify_Rest_jobs.xml /usr/local/var/Restify/Configuration/
#EXPOSE 9090
CMD ["java", "-Dserver.port=9090", "-jar", "restify-1.0.jar"]


