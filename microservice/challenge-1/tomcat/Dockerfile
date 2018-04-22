FROM tomcat:9.0.7-jre8

LABEL maintainer="markallerton"

COPY conf/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY conf/app_context.xml /usr/local/tomcat/conf/Catalina/localhost/JavaMySql-1.0.xml
COPY conf/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

COPY webapps/JavaMySql/target/JavaMySql-1.0.war /usr/local/tomcat/webapps/



EXPOSE 5000
EXPOSE 8080
EXPOSE 8000

WORKDIR /usr/local/tomcat
