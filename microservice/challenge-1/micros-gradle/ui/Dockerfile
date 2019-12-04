FROM tomcat:9.0-jre11

ADD tomcat-users.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
ADD build/libs/ui.war /usr/local/tomcat/webapps/ui.war

CMD ["catalina.sh", "run"]

EXPOSE 3000