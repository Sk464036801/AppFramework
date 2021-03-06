package com.moto.agent.proxy.mina.message;

/**
 * Created by Eric on 10/22/14.
 * SNMP .消息头定义
 *
 */
public abstract class AbstractMessage {

    private int totalLength;
    private int sequenceId;
    private int messageType;

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "AbstractMessage{" +
                "totalLength=" + totalLength +
                ", sequenceId=" + sequenceId +
                ", messageType=" + messageType +
                '}';
    }
}
