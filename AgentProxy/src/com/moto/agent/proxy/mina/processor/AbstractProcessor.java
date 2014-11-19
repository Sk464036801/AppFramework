package com.moto.agent.proxy.mina.processor;

import com.moto.agent.proxy.model.AppContext;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Eric on 10/22/14.
 */
public abstract class AbstractProcessor {

    private AppContext appContext;

    abstract void doProcessor(Object message, IoSession session) throws Exception;


    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }
}
