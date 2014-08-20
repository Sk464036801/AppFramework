package com.appframework.agent.snmp;

import com.appframework.agent.util.AppContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

/**
 * Created by Eric on 8/20/14.
 */
public class SnmpMain {

    private static final Log logger = LogFactory.getLog(SnmpMain.class);
    private AppContext appContext;

    public void run(){

        try {

            TransportMapping serverSocket  = new DefaultUdpTransportMapping(
                    new UdpAddress(appContext.getSnmpListenPort()));

            SnmpAgent snmpAgent = new SnmpAgent(appContext);

            Snmp snmp = new Snmp(serverSocket);
            snmp.addCommandResponder(snmpAgent);

            serverSocket.listen();

        } catch (IOException e) {
            logger.error("SnmpMain Error",e);
        }

    }


    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContextt) {
        this.appContext = appContextt;
    }
}
