package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import com.moto.agent.proxy.mina.message.SnmpAlarmRespMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpAlarmMessageEncoder<T extends AbstractMessage> extends AbstractMessageEncoder<T> {


    public SnmpAlarmMessageEncoder() {
        super(Constants.SNMP_ALARM_RESP);
    }

    @Override
    protected void encodeBody(IoSession session, T message, IoBuffer out) {

        SnmpAlarmRespMessage respMessage = (SnmpAlarmRespMessage)message;

        out.putInt(respMessage.getResult());
    }
}
