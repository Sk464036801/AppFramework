#!/bin/bash
ps -ef|grep 'com.moto.agent.snmp.Launcher'|grep -v 'grep'|awk '{print $2}'|xargs kill -9
echo 'agpeAgent server stopped'
