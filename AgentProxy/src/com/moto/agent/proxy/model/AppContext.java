package com.moto.agent.proxy.model;

import com.moto.agent.proxy.mina.manager.SnmpSessionManager;
import com.moto.agent.proxy.util.FileConfig;

import java.io.File;

/**
 * Created by Eric on 10/22/14.
 */
public class AppContext {

    private SnmpSessionManager ssm;
    //创建自动增长序列ID
    private int seqID;

    //创建连接用户校验
    private String userName;
    private String password;

    private int serverPort;
    private int ldleTime;

    private String trapIP;
    private String trapPort;
    private String community;
    private int communityTimeout;

    private FileConfig fileConfig;

    //系统告警，(CPU,MEMORY,STORAGE_DISK)
    private double cpuAlarmRate;
    private double memoryAlarmRate;
    private double storageDiskAlarmRate;
    private String storageDiskMounted;
    private File[] roots;
    private int alarmThreadInterval;


    public synchronized int getSeqID(){
        return seqID++;
    }

    public SnmpSessionManager getSsm() {
        return ssm;
    }

    public void setSsm(SnmpSessionManager ssm) {
        this.ssm = ssm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getLdleTime() {
        return ldleTime;
    }

    public void setLdleTime(int ldleTime) {
        this.ldleTime = ldleTime;
    }

    public String getTrapIP() {
        return trapIP;
    }

    public void setTrapIP(String trapIP) {
        this.trapIP = trapIP;
    }

    public String getTrapPort() {
        return trapPort;
    }

    public void setTrapPort(String trapPort) {
        this.trapPort = trapPort;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public int getCommunityTimeout() {
        return communityTimeout;
    }

    public void setCommunityTimeout(int communityTimeout) {
        this.communityTimeout = communityTimeout;
    }

    public String getFileConfig(String name) {
        return fileConfig.getConfig(name);
    }

    public void setFileConfig(String filePath) {
        this.fileConfig = new FileConfig(filePath);
    }

    public double getCpuAlarmRate() {
        return cpuAlarmRate;
    }

    public void setCpuAlarmRate(double cpuAlarmRate) {
        this.cpuAlarmRate = cpuAlarmRate;
    }

    public double getMemoryAlarmRate() {
        return memoryAlarmRate;
    }

    public void setMemoryAlarmRate(double memoryAlarmRate) {
        this.memoryAlarmRate = memoryAlarmRate;
    }

    public double getStorageDiskAlarmRate() {
        return storageDiskAlarmRate;
    }

    public void setStorageDiskAlarmRate(double storageDiskAlarmRate) {
        this.storageDiskAlarmRate = storageDiskAlarmRate;
    }

    public void setStorageDiskMounted(String storageDiskMounted) {
        this.storageDiskMounted = storageDiskMounted;

        String[] filePath = storageDiskMounted.split(",");
        this.roots = new File[filePath.length];
        int i = 0;
        for(String path : filePath){
            this.roots[i] = new File(path);
            i++;
        }
    }

    public File[] getRoots() {
        return roots;
    }

    public int getAlarmThreadInterval() {
        return alarmThreadInterval;
    }

    public void setAlarmThreadInterval(int alarmThreadInterval) {
        this.alarmThreadInterval = alarmThreadInterval;
    }
}
