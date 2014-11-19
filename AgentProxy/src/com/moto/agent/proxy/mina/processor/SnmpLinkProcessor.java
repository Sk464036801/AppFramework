package com.moto.agent.proxy.mina.processor;

import com.moto.agent.proxy.mina.codec.Constants;
import com.moto.agent.proxy.mina.message.SnmpLinkRespMessage;
import com.moto.agent.proxy.model.AppContext;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpLinkProcessor extends AbstractProcessor {

    private AppContext appContext;

    public SnmpLinkProcessor(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    void doProcessor(Object message, IoSession session) throws Exception {

        SnmpLinkRespMessage resp = new SnmpLinkRespMessage();

        resp.setTotalLength(Constants.HEADER_LEN);
        resp.setSequenceId(appContext.getSeqID());
        resp.setMessageType(Constants.SNMP_LINK_RESP);

        session.write(resp);
    }
}
