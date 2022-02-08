FROM tomcat:8.5.16-jre8-alpine

ADD target/App42PaaS-Java-MySQL-Sample-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
COPY tomcat-users.xml $CATALINA_HOME/conf/tomcat-users.xml
#COPY context.xml $CATALINA_HOME/conf/context.xml

CMD ["catalina.sh", "run"]
