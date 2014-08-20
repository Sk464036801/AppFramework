package com.appframework.agent.snmp;

import com.appframework.agent.util.AppContext;
import com.appframework.agent.util.MibUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageException;
import org.snmp4j.PDU;
import org.snmp4j.mp.StateReference;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;

/**
 * Created by Eric on 8/20/14.
 */
public class SnmpAgent implements CommandResponder {

    private static final Log logger = LogFactory.getLog(SnmpAgent.class);
    private AppContext appContext;

    public SnmpAgent(AppContext appContext){
        this.appContext = appContext;
    }

    @Override
    public synchronized void processPdu(CommandResponderEvent event) {

        String fromIP = event.getPeerAddress().toString();
        logger.debug(" -- fromIP = " + fromIP);
        fromIP = fromIP.substring(0,fromIP.indexOf("/"));

        String communityName = new String(event.getSecurityName());
        PDU pdu = event.getPDU();

        logger.debug(" -- Receive commuinityName = " + communityName + ", pdu = " + pdu.toString());

        if (null == communityName) {
            logger.info("Receive CommunityName is Null.");
        } else if (communityName.equals(appContext.getCommunity())) {

            if (null == pdu) {
                logger.info("Receive PDU is Null.");
            } else {
                int pduSize = pdu.size();

                for (int i =0; i< pduSize; i++){

                    String oid = pdu.get(i).getOid().toString();

                    switch (pdu.getType()){
                        case PDU.GET:
                            pduGet(oid, pdu, event, i);
                            break;
                        case PDU.GETNEXT:
                            pduGetNext(oid, pdu, event, i);
                            break;
                        case PDU.SET:
                            pduSet(oid, pdu, event, i);
                            break;
                        default:
                            pduDefault(oid, pdu, event);
                            break;
                    }
                }
            }

        } else {
            logger.info("Receive CommunityName is Wrong.");
        }

    }

    private void pduDefault(String oid, PDU pdu, CommandResponderEvent event) {
        logger.info("pduDefault OID = " + oid);
    }

    private void pduGet(String oid, PDU pdu, CommandResponderEvent event, int i){
        logger.info("pduGet OID = " + oid);

        String iodVelue =MibUtil.getOid(oid);

        if (iodVelue == null) {
            pdu.setErrorIndex(1);
            pdu.setErrorStatus(2);
        } else {
            pdu.set(i,new VariableBinding(new OID(oid),new OctetString(iodVelue)));
        }

        pduResponse(pdu, event);
    }

    private void pduGetNext(String oid, PDU pdu, CommandResponderEvent event, int i) {
        logger.info("pduGetNext OID = " + oid);

        String nextOid = MibUtil.getNextOid(oid);

        if (null == nextOid) {
            pdu.setErrorIndex(1);
            pdu.setErrorStatus(2);
        } else {
            String oidValue = MibUtil.getOid(nextOid);

            if (null == oidValue) {
               pdu.set(i, new VariableBinding(new OID(nextOid)));
            } else {
               pdu.set(i, new VariableBinding(new OID(nextOid), new OctetString(oidValue)));
            }

            pduResponse(pdu, event);

        }


    }

    private void pduSet(String oid, PDU pdu, CommandResponderEvent event, int i) {
        logger.info("pduSet OID = " + oid);

        String receive = pdu.get(i).getVariable().toString();;
        String setOid = pdu.get(i).getOid().toString();

        logger.info("receive = " + receive + ", setOID = " + setOid);
    }

    private void pduResponse(PDU pdu, CommandResponderEvent event){
        pdu.setType(PDU.RESPONSE);
        logger.info("send pud:" + pdu.toString());
        StatusInformation statusInformation = new StatusInformation();
        StateReference ref = event.getStateReference();
        try {
            event.getMessageDispatcher().returnResponsePdu(
                    event.getMessageProcessingModel(),
                    event.getSecurityModel(), event.getSecurityName(),
                    event.getSecurityLevel(), pdu,
                    event.getMaxSizeResponsePDU(), ref, statusInformation);
        } catch (MessageException e) {
            logger.error("pduResponse error",e);
        }
    }



}
