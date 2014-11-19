package com.moto.agent.proxy.mina.processor;

import com.moto.agent.proxy.mina.message.SnmpAlarmMessage;
import com.moto.agent.proxy.model.AppContext;
import com.moto.agent.proxy.snmp.util.SnmpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/23/14.
 */
public class SnmpAlarmProcessor extends AbstractProcessor {

    private AppContext appContext;
    private static final Log logger = LogFactory.getLog(SnmpAlarmProcessor.class);

    public SnmpAlarmProcessor(AppContext appContext) {

        this.appContext = appContext;
    }

    @Override
    void doProcessor(Object message, IoSession session) throws Exception {

        SnmpAlarmMessage m = (SnmpAlarmMessage)message;

        if (0 != m.getAlarmType()) {
            SnmpUtils.sendTrapInfo(appContext, m.getAlarmType());
        } else {
            logger.info(" -- recv alarmType = " + m.getAlarmType());
        }
    }
}
