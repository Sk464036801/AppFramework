package com.moto.agent.proxy.mina.manager;

import com.moto.agent.proxy.model.AppContext;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/22/14.
 */
public abstract class SnmpSessionManager {

    protected IoSession session;
    protected IoHandlerAdapter handlerAdapter;
    protected AppContext appContext;

    protected abstract void connect();
    protected abstract void initSnmpHandler();
//    protected abstract void initSnmpHandlerClient();

    protected void start(){
        initSnmpHandler();
        this.appContext.setSsm(this);
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public IoHandlerAdapter getHandlerAdapter() {
        return handlerAdapter;
    }

    public void setHandlerAdapter(IoHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }
}
