#!/bin/bash
ps -ef|grep 'com.example.data.acquisition.Launcher'|grep -v 'grep'|awk '{print $2}'|xargs kill -9
echo 'Data Acquisition Server Stopped'
