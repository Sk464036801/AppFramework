/**
 * 
 */
package com.appframework.agent.util;

import java.io.IOException;

import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;


/**
 * @author Amon
 * 2013-10-31
 */
public class SNMPSingleInstance {

    private static class SingletonHolder {  
    	private static final SNMPSingleInstance INSTANCE = new SNMPSingleInstance();  
    }  
   private SNMPSingleInstance (){}
   public static final SNMPSingleInstance getInstance() {  
      return SingletonHolder.INSTANCE;  
    }  
   
   public Snmp createSNMP(){
	   TransportMapping transport;
	   Snmp snmp = null;
	try {
		transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return snmp;
		
   }
}