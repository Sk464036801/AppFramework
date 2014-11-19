package com.moto.agent.proxy.mina.manager;

import com.moto.agent.proxy.mina.processor.ClientSessionHandler;

/**
 * Created by Eric on 10/22/14.
 */
public class SnmpConnectManager extends SnmpSessionManager {
    @Override
    protected void connect() {

    }

    @Override
    protected void initSnmpHandler() {
        this.handlerAdapter = new ClientSessionHandler(this);
    }

}
