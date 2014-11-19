package com.moto.agent.proxy.mina.message;

/**
 * Created by Eric on 10/22/14.
 */
public class SnmpAlarmRespMessage extends AbstractMessage {

    private byte result;

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SnmpAlarmRespMessage{" +
                "result=" + result +
                '}';
    }
}
