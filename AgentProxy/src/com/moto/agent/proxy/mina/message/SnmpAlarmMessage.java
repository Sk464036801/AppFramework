package com.moto.agent.proxy.mina.message;

/**
 * Created by Eric on 10/22/14.
 */
public class SnmpAlarmMessage extends AbstractMessage {

    private int timeSec;
    private int timeUsec;
    private int localIp;
    private int alarmType;

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public int getTimeUsec() {
        return timeUsec;
    }

    public void setTimeUsec(int timeUsec) {
        this.timeUsec = timeUsec;
    }

    public int getLocalIp() {
        return localIp;
    }

    public void setLocalIp(int localIp) {
        this.localIp = localIp;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    @Override
    public String toString() {
        return "SnmpAlarmRespMessage{" +
                "timeSec=" + timeSec +
                ", timeUsec=" + timeUsec +
                ", localIp=" + localIp +
                ", alarmType=" + alarmType +
                '}';
    }
}
