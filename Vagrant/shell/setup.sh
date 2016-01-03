#!/bin/sh

echo ""
echo "############################################################"
echo "# Importing public keys of MongoDB und Java 8 repositories #"
echo "############################################################"

# MongoDB
apt-key adv --keyserver hkp://keyserver.ubuntu.com --recv EA312927

# Java 8
apt-key adv --keyserver hkp://keyserver.ubuntu.com --recv EEA14886


echo ""
echo "############################################################"
echo "#     Creating repository files for MongoDB and Java 8     #"
echo "############################################################"

# MongoDB
echo "deb http://repo.mongodb.org/apt/debian wheezy/mongodb-org/3.2 main" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list

# Java 8
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list

# Accept Oracle license
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections


echo ""
echo "############################################################"
echo "#         Installing MongoDB, Java 8 and Tomcat 8          #"
echo "############################################################"

# Reload local package database
apt-get update

# Install MongoDB
apt-get install mongodb-org -y

# Install Java 8 JDK and set path variable
apt-get install oracle-java8-installer -y

# Install Tomcat
apt-get install tomcat8 -y
apt-get install tomcat8-admin -y

# Cleanup
apt-get autoremove -y
apt-get clean


echo ""
echo "############################################################"
echo "#                    Setting up MongoDB                    #"
echo "############################################################"

# Replace IP 127.0.0.1 with 0.0.0.0 by using sed. MongoDB can now listen on all interfaces.
sed -i -e 's/'127.0.0.1'\b/'0.0.0.0'/g' /etc/mongod.conf

# Stop MongoDB (in case we only provision and don't run the script after booting up) 
service mongod stop

# Start MongoDB
service mongod start


echo ""
echo "############################################################"
echo "#                    Importing Records                     #"
echo "############################################################"

# Extract dump
cd /home/vagrant/dump/
tar -xvjf records.tar.bz2

# Drop database
#mongo kd --eval "db.dataRecord.drop()"
mongo kd --eval "db.dropDatabase()"

# Insert 1 million records
mongoimport --db kd --collection dataRecord --type json --file /home/vagrant/dump/records.json --jsonArray

echo ""
echo "############################################################"
echo "#                    Setting up Tomcat                     #"
echo "############################################################"

# Stop Tomcat (in case we provision and don't just boot up)
service tomcat8 stop

# Setting JAVA_HOME in Tomcat config file (Pfuschloesung)
echo  "JAVA_HOME=/usr/lib/jvm/java-8-oracle/" | tee -a /etc/default/tomcat8

# Enable admin access
echo "<tomcat-users>
	<role rolename=\"admin\"/>
	<role rolename=\"admin-gui\"/>
	<role rolename=\"manager-script\"/>
	<role rolename=\"manager-gui\"/>
	<role rolename=\"manager-jmx\"/>
	<user username=\"admin\" password=\"admin\" roles=\"admin-gui, manager-gui, admin, manager-jmx, manager-script,\" />
 </tomcat-users>
" > /etc/tomcat8/tomcat-users.xml

# Start Tomcat
service tomcat8 start

# Finish
echo ""
echo "############################################################"
echo "#                           DONE                           #"
echo "############################################################"