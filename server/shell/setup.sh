#!/bin/sh

# Import the public key used by the package management system
apt-key adv --keyserver hkp://keyserver.ubuntu.com --recv EA312927

# Create a /etc/apt/sources.list.d/mongodb-org-3.2.list file for MongoDB
echo "deb http://repo.mongodb.org/apt/debian wheezy/mongodb-org/3.2 main" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list

# Reload local package database (and update in general)
apt-get update
apt-get upgrade -y
apt-get dist-upgrade -y

# Install MongoDB
apt-get install mongodb-org -y

# Install Java 8 JDK
#apt-get install java


# Cleanup
apt-get autoremove -y
apt-get clean

# Replace IP 127.0.0.1 with 0.0.0.0 by using sed. MongoDB can now listen on all interfaces.
sed -i -e 's/'127.0.0.1'\b/'0.0.0.0'/g' /etc/mongod.conf

# Create directory for MongoDB database
mkdir -p /data/kd

# Stop MongoDB (in case we only provision and don't run the script after booting up) 
service mongod stop

# Start MongoDB
#service mongod start
mongod --dbpath /data/kd

# Extract dump


# Drop database and insert 1 million records


# Finish
echo "DONE"