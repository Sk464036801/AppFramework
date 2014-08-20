#!/bin/bash
# startup agpsAgent server shell.

if [ -z "$JAVA_HOME" ];then
	echo "Usage: enviromental JAVA_HOME is not exists,please set it !"
	exit
fi

#export JAVA_HOME="/usr/lib/jvm/java-1.6.0-jdk"
SERVER_HOME=`cd .. && pwd`
export SERVER_HOME

PATH=$SERVER_HOME/bin:$PATH
export PATH

CLASSPATH=$SERVER_HOME/lib:$CLASSPATH
export CLASSPATH

LIBPATH=$SERVER_HOME/lib
for file in ${LIBPATH}/*.jar;
do
CLASSPATH=$CLASSPATH:$file
done
export CLASSPATH

cd $SERVER_HOME
nohup ${JAVA_HOME}/bin/java -Xmx400m com.moto.agent.snmp.Launcher > /dev/null 2>&1 &
#${JAVA_HOME}/bin/java -Xmx400m com.moto.agent.snmp.Launcher
echo 'agpsAgent server started'