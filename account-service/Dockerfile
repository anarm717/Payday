FROM ubuntu:latest
RUN apt-get -y update
RUN apt-get install -y tzdata
RUN apt-get -y install apache2
RUN apt-get install -y maven
RUN apt-get install -y vim
ADD . /var/account_service
RUN cd /var/account_service && mvn clean install
EXPOSE 80
EXPOSE 8080
CMD apachectl -D FOREGROUND
