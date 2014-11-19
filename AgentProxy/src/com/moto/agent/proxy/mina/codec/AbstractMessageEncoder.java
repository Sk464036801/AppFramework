package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;

public abstract class AbstractMessageEncoder<T extends AbstractMessage> implements MessageEncoder<T> {
	
	private final int messageType;

	protected AbstractMessageEncoder(int messageType) {
		this.messageType = messageType;
	}

	@Override
	public void encode(IoSession session, T message, ProtocolEncoderOutput out)
			throws Exception {
		
		IoBuffer buf = IoBuffer.allocate(128);
		//Enable auto-expand for easier encoding
		buf.setAutoExpand(true);
		
		//Message Header
		AbstractMessage m = (AbstractMessage)message;
		int pkgStartPos = buf.position();
		buf.putInt(m.getTotalLength());
		buf.putInt(m.getSequenceId());
        buf.putInt(this.messageType);
		
		//Message Body
		int bodyStartPos = buf.position();
		encodeBody(session, message, buf);
		int bodyLen = buf.position() - bodyStartPos;
		int totalLen = Constants.HEADER_LEN +bodyLen;
		int pkgEndPos = buf.position();
		buf.position(pkgStartPos);
		buf.putInt(totalLen);
		buf.position(pkgEndPos);
		
		buf.flip();
		out.write(buf);
		
		
	}
	
    /** 
     * 解析消息的体的方法的 
     * @param session 
     * @param message 
     * @param out 
     */  
    protected abstract void encodeBody(IoSession session ,T message ,IoBuffer out);

    protected void putString(IoBuffer out, String s, int maxLen,
                             CharsetEncoder encoder) {

        if (s == null || s.length() == 0) {
            out.fill(maxLen);
            return;
        }

        int pos = out.position();
        int end = pos + maxLen;

        try {
            if (maxLen < 0)
                out.putString(s, encoder);
            else
                out.putString(s, maxLen, encoder);
        } catch (CharacterCodingException e) {
            int left = end - out.position();
            if (left > 0)
                out.fill(left);
        }

    }

}
