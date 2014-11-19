package com.moto.agent.proxy.mina.message;

/**
 * Created by Eric on 10/22/14.
 */
public class SnmpConnectRespMessage extends AbstractMessage {

    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SnmpConnectRespMessage{" +
                "result=" + result +
                '}';
    }
}
