package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import com.moto.agent.proxy.mina.message.SnmpConnectMessage;
import com.moto.agent.proxy.mina.util.CodecUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.CharacterCodingException;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpConnectMessageDecoder extends AbstractMessageDecoder {

    private static final Log logger = LogFactory.getLog(SnmpConnectMessageDecoder.class);

    public SnmpConnectMessageDecoder() {
        super(Constants.SNMP_CONNECTION_REQ);
    }

    @Override
    protected AbstractMessage decodeBody(IoSession session, IoBuffer in, int bodyLen) {
        logger.info("renaming = " + in.remaining() + ", bodyLength = " + bodyLen);

        if (in.remaining() < bodyLen){
            return null;
        }
        SnmpConnectMessage message = new SnmpConnectMessage();
        try {
            message.setUserName(in.getString(32, CodecUtils.getGBKDecoder()));
            message.setPassword(in.getString(32, CodecUtils.getGBKDecoder()));
            logger.debug(" --- renaming = " + in.remaining());
        } catch (CharacterCodingException e) {
            logger.error("codec exception",e);
        }
        return message;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }
}
