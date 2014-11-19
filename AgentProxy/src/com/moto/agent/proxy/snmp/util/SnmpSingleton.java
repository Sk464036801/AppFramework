package com.moto.agent.proxy.snmp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

/**
 * Created by Eric on 10/24/14.
 */
public class SnmpSingleton {

    private static volatile SnmpSingleton instance;
    private static final Log logger = LogFactory.getLog(SnmpSingleton.class);

    private Snmp snmp;

    private SnmpSingleton (){}

    public static SnmpSingleton getInstance() {

        if (null == instance){
            synchronized (SnmpSingleton.class){
                if (null == instance){
                    instance = new SnmpSingleton();
                    instance.createSnmp();
                }
            }
        }

        return instance;
    }

    private void createSnmp(){
        try {
            TransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();
        } catch (IOException e) {
            logger.error("createSnmp exception ->",e);
        }

    }

    public Snmp getSnmp() {
        return snmp;
    }
}
