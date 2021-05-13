FROM tomcat
RUN rm -frd /usr/local/tomcat/webapps/*.war
COPY target/ROOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]