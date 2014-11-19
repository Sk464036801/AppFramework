package com.moto.agent.proxy.snmp.util;

import com.moto.agent.proxy.model.AppContext;
import com.moto.agent.proxy.util.FileConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;

import java.io.IOException;

/**
 * Created by Eric on 10/24/14.
 */
public class SnmpUtils {

    private static final Log logger = LogFactory.getLog(SnmpUtils.class);

    public static PDU paddingTrapHeaderInfo(PDU pdu, OID oid){

        TimeTicks sysUpTime = new TimeTicks((System.currentTimeMillis()/1000));
        pdu.add(new VariableBinding(SnmpConstants.sysUpTime,sysUpTime));
        pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID,oid));
        return pdu;
    }

    private static void sendTrapToServer(AppContext appContext, PDU pdu){

        String addressStr = "udp:" + appContext.getTrapIP() + "/" + appContext.getTrapPort();
        logger.debug("sendTrapInfo addressStr = " + addressStr);
        Address targetAddress = GenericAddress.parse(addressStr);

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(appContext.getCommunity()));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(appContext.getCommunityTimeout());
        target.setVersion(SnmpConstants.version2c);

        try {
            if (null == targetAddress){
                logger.warn("sendTrapInfo target address is null.");
            } else {
                SnmpSingleton.getInstance().getSnmp().send(pdu,target);
            }
        } catch (IOException e) {
            logger.error("sendTrapInfo exception ->",e);
        }
    }

    public static void sendTrapInfo(AppContext appContext, int alarmType){

        String name = new Integer(alarmType).toString();
        String trapOidStr = appContext.getFileConfig(name);
        logger.debug("sendTrapInfo name = " +name + ", trapOidStr = " + trapOidStr);
        if (null != trapOidStr) {

            PDU pdu = new PDU() ;
            pdu.setType(PDU.TRAP);
            int length = trapOidStr.length() - 2;
            String parentOidStr = trapOidStr.substring(0,length);

            OID parentOid = new OID(parentOidStr);

            logger.info(" -- recv trapOid = " + trapOidStr + ", parentOid = " + parentOid + ", alarmType = " + alarmType);

            pdu = paddingTrapHeaderInfo(pdu,parentOid);

            //缺少告告警状态的定义
//        pdu.add(new VariableBinding(new OID(trapOidStr),new OctetString("0")));
            pdu.add(new VariableBinding(new OID(trapOidStr)));

            sendTrapToServer(appContext, pdu);

        } else {
            logger.info("alarmType = " + alarmType + ", trapOidStr is null.");
        }


    }


    public static void main(String[] args) {
        int num1 = 5;

        System.out.println(Integer.toHexString(num1));
        System.out.println(Integer.toHexString(num1 & 0xFF).toUpperCase());
    }

}


