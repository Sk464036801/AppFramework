package com.moto.agent.proxy.mina.processor;

import com.moto.agent.proxy.mina.codec.Constants;
import com.moto.agent.proxy.mina.message.SnmpConnectMessage;
import com.moto.agent.proxy.mina.message.SnmpConnectRespMessage;
import com.moto.agent.proxy.model.AppContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpConnectionProcessor extends AbstractProcessor {

    private AppContext appContext;
    private static final Log logger = LogFactory.getLog(SnmpConnectionProcessor.class);

    public SnmpConnectionProcessor(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    void doProcessor(Object message, IoSession session) throws Exception {

        SnmpConnectMessage m = (SnmpConnectMessage)message;
        SnmpConnectRespMessage resp = new SnmpConnectRespMessage();

        if (!m.getPassword().equals(appContext.getPassword()) ||
                !m.getUserName().equals(appContext.getUserName())){
            logger.info(" --connect fail, recv connect info = " + m.toString());
            resp.setResult(Constants.RESPONSE_FAIL);
        } else {
            logger.info(" -- recv message , connect success.");
            resp.setResult(Constants.RESPONSE_SUCCESS);
        }

        session.write(resp);

    }
}
