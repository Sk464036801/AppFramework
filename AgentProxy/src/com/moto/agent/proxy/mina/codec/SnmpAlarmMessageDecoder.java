package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import com.moto.agent.proxy.mina.message.SnmpAlarmMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpAlarmMessageDecoder extends AbstractMessageDecoder {

    public SnmpAlarmMessageDecoder() {
        super(Constants.SNMP_ALARM_REQ);
    }

    @Override
    protected AbstractMessage decodeBody(IoSession session, IoBuffer in, int bodyLen) {

        if (in.remaining() < bodyLen){
            return null;
        }

        SnmpAlarmMessage message = new SnmpAlarmMessage();
        message.setTimeSec(in.getInt());
        message.setTimeUsec(in.getInt());
        message.setLocalIp(in.getInt());
        message.setAlarmType(in.getInt());
        return message;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }
}
