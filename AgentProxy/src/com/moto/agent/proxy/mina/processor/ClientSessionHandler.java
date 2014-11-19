package com.moto.agent.proxy.mina.processor;

import com.moto.agent.proxy.mina.manager.SnmpSessionManager;
import com.moto.agent.proxy.model.AppContext;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/22/14.
 */
public class ClientSessionHandler extends IoHandlerAdapter {

    private SnmpSessionManager ssm;
    private AppContext appContext;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    public ClientSessionHandler(SnmpSessionManager ssm) {
        this.ssm = ssm;
        this.appContext = ssm.getAppContext();
    }
}
