package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpLinkMessageEncoder<T extends AbstractMessage> extends AbstractMessageEncoder<T> {

    public SnmpLinkMessageEncoder() {
        super(Constants.SNMP_LINK_RESP);
    }

    @Override
    protected void encodeBody(IoSession session, T message, IoBuffer out) {

    }
}
