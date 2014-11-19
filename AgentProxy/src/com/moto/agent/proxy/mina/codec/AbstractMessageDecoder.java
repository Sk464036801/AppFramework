package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.AbstractMessage;
import com.moto.agent.proxy.mina.message.HeaderMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public abstract class AbstractMessageDecoder implements MessageDecoder {
	
	private final int messageType;
	private boolean readHeader;
	private HeaderMessage headerMessage;
    private static final Log logger = LogFactory.getLog(AbstractMessageDecoder.class);

	protected AbstractMessageDecoder(int messageType) {
		this.messageType = messageType;
	}

	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		
		//当信息头（header）中没有读取时返回需要读取 NEED_DATA 
		if (in.remaining() < Constants.HEADER_LEN) {
			return MessageDecoderResult.NEED_DATA;
		}
		//如果类型和长度匹配返回 OK
		int length = in.getInt(); //消息总长度
		int seqId = in.getInt(); //消息自动增长序列
		int cmd = in.getInt(); //消息类型
        logger.debug("length = " + length + ", seqId = " + seqId + ",cmd = " + cmd);
		if (messageType == cmd) {
			return MessageDecoderResult.OK;
		}
		//如果不匹配返回NOT OK
		return MessageDecoderResult.NOT_OK;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		// Try to skip header if not read.
		if (!readHeader) {
			headerMessage = new HeaderMessage();
			headerMessage.setTotalLength(in.getInt());
            headerMessage.setSequenceId(in.getInt());
			headerMessage.setMessageType(in.getInt());
			readHeader = false;
		}
		
        // Try to decode body
        AbstractMessage m = decodeBody(session, in, headerMessage.getTotalLength() - Constants.HEADER_LEN);
        if (m == null){
        	return MessageDecoderResult.NEED_DATA;
        }else{
        	readHeader = false;
        }
		
        m.setMessageType(headerMessage.getMessageType());
        m.setSequenceId(headerMessage.getSequenceId());
        m.setTotalLength(headerMessage.getTotalLength());
        
        out.write(m);
		
		return MessageDecoderResult.OK;
	}
	
    /** 
     * 解析消息的body体的信息 
     * @param session 
     * @param in 
     * @return 
     */  
    protected abstract AbstractMessage decodeBody(IoSession session ,IoBuffer in ,int bodyLen);

}
