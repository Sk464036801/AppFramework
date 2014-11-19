package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import com.moto.agent.proxy.mina.message.SnmpConnectRespMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpConnectMessageEncoder<T extends AbstractMessage> extends AbstractMessageEncoder<T> {

    public SnmpConnectMessageEncoder() {
        super(Constants.SNMP_CONNECTION_RESP);
    }

    @Override
    protected void encodeBody(IoSession session, T message, IoBuffer out) {
        SnmpConnectRespMessage respMessage = (SnmpConnectRespMessage)message;

        out.putInt(respMessage.getResult());
    }
}
