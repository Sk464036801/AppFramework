package com.moto.agent.proxy.thread;

import com.moto.agent.proxy.mina.codec.AppCodecFactory;
import com.moto.agent.proxy.mina.processor.ServerSessionHandler;
import com.moto.agent.proxy.model.AppContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Eric on 10/22/14.
 */
public class MainThread extends Thread {

    private static final Log logger = LogFactory.getLog(MainThread.class);
    private AppContext appContext;

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }



    private void initConfig(){

        logger.info(" -- initConfig execute . --");

    }


    @Override
    public void run() {

        //初始化相关配置信息
        initConfig();

        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new AppCodecFactory()));
        acceptor.getFilterChain().addLast("logger",new LoggingFilter());
        acceptor.setReuseAddress(true);
        try {
            acceptor.setHandler(new ServerSessionHandler(appContext));
            acceptor.bind(new InetSocketAddress(appContext.getServerPort()));
        } catch (IOException e) {
            logger.error("App Server Startup Fail -->",e);
        }
        logger.info("App Server Startup Success");
    }
}
