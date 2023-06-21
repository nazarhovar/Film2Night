FROM tomcat:8.5.88-jdk11
ADD target/Film2Night.war /usr/local/tomcat/webapps/Film2Night.war
EXPOSE 8181
CMD ["catalina.sh","run"]