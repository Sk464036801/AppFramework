package com.moto.agent.proxy.mina.processor;

import com.moto.agent.proxy.mina.codec.Constants;
import com.moto.agent.proxy.mina.message.SnmpAlarmMessage;
import com.moto.agent.proxy.mina.message.SnmpConnectMessage;
import com.moto.agent.proxy.mina.message.SnmpLinkMessage;
import com.moto.agent.proxy.mina.message.SnmpLinkRespMessage;
import com.moto.agent.proxy.model.AppContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Hashtable;

/**
 * Created by Eric on 10/23/14.
 */
public class ServerSessionHandler extends IoHandlerAdapter {

    private static final Log logger = LogFactory.getLog(ServerSessionHandler.class);
    private AppContext appContext;


    private Hashtable<Integer, AbstractProcessor> ht;
    private void register(int cmd, AbstractProcessor psr) {
        ht.put(new Integer(cmd), psr);
    }
    private AbstractProcessor findProcesser(int msghashcode) {
        return (AbstractProcessor) (ht.get(new Integer(msghashcode)));
    }

    private void initProcessor(){
        ht = new Hashtable<Integer, AbstractProcessor>();

        register(SnmpConnectMessage.class.hashCode(), new SnmpConnectionProcessor(appContext));
        register(SnmpAlarmMessage.class.hashCode(), new SnmpAlarmProcessor(appContext));
        register(SnmpLinkMessage.class.hashCode(), new SnmpLinkProcessor(appContext));
    }


    public ServerSessionHandler(AppContext appContext) {
        this.appContext = appContext;
        initProcessor();
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE,appContext.getLdleTime());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.debug("sessionIdle -> execute .");
        SnmpLinkRespMessage message = new SnmpLinkRespMessage();
        message.setTotalLength(Constants.HEADER_LEN);
        message.setSequenceId(appContext.getSeqID());
        message.setMessageType(Constants.SNMP_LINK_RESP);

        session.write(message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        session.close(true);
        logger.error("exceptionCaugh -> ",cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.debug(" -- recv message : " + message.getClass().getName());
        logger.debug(" -- findProcessor :" + findProcesser(message.getClass().hashCode()));

        try {
            AbstractProcessor processor = findProcesser(message.getClass().hashCode());
            processor.doProcessor(message, session);
        } catch (Exception e) {
            logger.error("messageReceived error", e);
            session.close(true);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }
}
