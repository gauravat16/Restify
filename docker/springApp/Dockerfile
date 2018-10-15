FROM openjdk:8u171-jre

ADD restify-1.2.jar  restify-1.2.jar
CMD mkdir -p /usr/local/var/Restify/Configuration
COPY Restify_Rest_jobs.xml /usr/local/var/Restify/Configuration/
#EXPOSE 9090docker images -q --filter dangling=true | xargs docker rmi
CMD ["java", "-Dserver.port=9090", "-jar", "restify-1.2.jar"]


