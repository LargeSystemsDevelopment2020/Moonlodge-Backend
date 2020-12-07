#!/bin/bash

# echo 'plz deliver'
# for permissions for github: git update-index --chmod=+x check_services.sh

echo 'The following Maven command installs your Maven-built Java application'
echo 'into the local Maven repository, which will ultimately be stored in'
echo 'Jenkins''s local Maven repository (and the "maven-repository" Docker data'
echo 'volume).'
set -x


mvn jar:jar install:install help:evaluate -Dexpression=project.name
set +x

echo 'The following complex command extracts the value of the <name/> element'
echo 'within <project/> of your Java/Maven project''s "pom.xml" file.'
set -x
NAME=`mvn help:evaluate -Dexpression=project.name | grep "^[^\[]"`
set +x

echo 'The following complex command behaves similarly to the previous one but'
echo 'extracts the value of the <version/> element within <project/> instead.'
set -x
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
set +x

echo 'The following command runs and outputs the execution of your Java'
echo 'application (which Jenkins built using Maven) to the Jenkins UI.'
set -x

# Copying the file from jenkins to droplet
echo 'Jar File Name: ' ${NAME}-${VERSION}
cp target/${NAME}-${VERSION}.jar /lsd/
sudo su

sudo fuser 1099/tcp
sleep 10
sudo fuser -k 1099/tcp
sleep 30
#/usr/lib/jvm/java-11-openjdk-amd64/bin/
nohup java -jar /lsd/${NAME}-${VERSION}.jar &
