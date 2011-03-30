#!/bin/sh

mvn install:install-file -Dfile=./lib/GroupManager.jar -DgroupId=org.anjocaido -DartifactId=GroupManager -Dversion=1.0-alpha-5 -Dpackaging=jar
mvn install:install-file -Dfile=./lib/Permissions.jar -DgroupId=com.nijikokun -DartifactId=Permissions -Dversion=2.5.4 -Dpackaging=jar
