#!/bin/bash
ps -ef|grep 'com.web.spider.Launcher'|grep -v 'grep'|awk '{print $2}'|xargs kill -9
echo 'App Module Server Stopped'
