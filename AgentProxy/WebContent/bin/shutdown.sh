#!/bin/bash
ps -ef|grep 'com.moto.agent.proxy.Launcher'|grep -v 'grep'|awk '{print $2}'|xargs kill -9
echo 'Agent Proxy Server Stopped'
