FROM openjdk:8-jdk-alpine
MAINTAINER Elastisys <techteam@elastisys.com>

# install bash shell in alpine linux
RUN apk add --no-cache bash

# install azurepool server
COPY ${project.build.finalName}.jar /opt/elastisys/azurepool/azurepool.jar
COPY start.sh /opt/elastisys/azurepool/start.sh

# create configuration directory
COPY etc/elastisys/ /etc/elastisys/

# create directories
RUN mkdir -p /var/log/elastisys && \
    mkdir -p /var/run/elastisys/ && \
    mkdir -p /var/lib/elastisys/ && \    
    chmod +x /opt/elastisys/azurepool/start.sh

CMD ["/opt/elastisys/azurepool/start.sh"]