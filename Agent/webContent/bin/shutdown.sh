#!/bin/bash
ps -ef|grep 'com.appframework.agent.Launcher'|grep -v 'grep'|awk '{print $2}'|xargs kill -9
echo 'Agent server stopped'
