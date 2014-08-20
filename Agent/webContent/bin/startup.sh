#!/bin/bash
# startup Agent server shell.

if [ -z "$JAVA_HOME" ];then
	echo "Usage: enviromental JAVA_HOME is not exists,please set it !"
	exit
fi

export AGENT_HOME=`cd .. && pwd`

export PATH=$AGENT_HOME/bin:$PATH

export CLASSPATH=$AGENT_HOME/lib:$CLASSPATH

export LIBPATH=$AGENT_HOME/lib

for file in ${LIBPATH}/*.jar;
do
CLASSPATH=$CLASSPATH:$file
done

cd $AGENT_HOME
nohup ${JAVA_HOME}/bin/java -Xmx400m com.appframework.agent.Launcher > /dev/null 2>&1 &
#${JAVA_HOME}/bin/java -Xmx400m com.moto.agent.snmp.Launcher
echo 'Agent server started'