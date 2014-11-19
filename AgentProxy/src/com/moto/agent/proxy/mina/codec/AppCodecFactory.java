package com.moto.agent.proxy.mina.codec;

import com.moto.agent.proxy.mina.message.SnmpAlarmRespMessage;
import com.moto.agent.proxy.mina.message.SnmpConnectRespMessage;
import com.moto.agent.proxy.mina.message.SnmpLinkRespMessage;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * Created by Eric on 10/23/14.
 */
public class AppCodecFactory extends DemuxingProtocolCodecFactory {

    //创建化解码过滤器的顺序，先解包，后封包
    public AppCodecFactory() {

        super.addMessageDecoder(SnmpConnectMessageDecoder.class);
        super.addMessageDecoder(SnmpAlarmMessageDecoder.class);
        super.addMessageDecoder(SnmpLinkMessageDecoder.class);

        super.addMessageEncoder(SnmpConnectRespMessage.class, SnmpConnectMessageEncoder.class);
        super.addMessageEncoder(SnmpAlarmRespMessage.class, SnmpAlarmMessageEncoder.class);
        super.addMessageEncoder(SnmpLinkRespMessage.class, SnmpLinkMessageEncoder.class);
    }
}
