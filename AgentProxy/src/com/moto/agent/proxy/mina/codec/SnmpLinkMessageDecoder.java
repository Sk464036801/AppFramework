package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import com.moto.agent.proxy.mina.message.SnmpLinkMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpLinkMessageDecoder extends AbstractMessageDecoder {

    public SnmpLinkMessageDecoder() {
        super(Constants.SNMP_LINK_REQ);
    }

    @Override
    protected AbstractMessage decodeBody(IoSession session, IoBuffer in, int bodyLen) {

        if (in.remaining() < bodyLen){
            return null;
        }
        SnmpLinkMessage message = new SnmpLinkMessage();
        return message;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }
}
