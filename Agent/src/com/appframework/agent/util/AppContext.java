package com.appframework.agent.util;

import java.util.List;
import java.util.Map;

import org.snmp4j.Snmp;


/**
 * 
 * @author lisp
 * 
 *         2012-5-15
 */
public class AppContext {

	public  String mibPath;
	private String trapIp;
	private int trapPort;
	private int snmpListenPort;
	
	private Snmp snmp;
	private String community;

    private int multicastPort;
    private String multicastIp;
	
	
	//根据key获取val
	public  String getNameByOID(String oid){
		return MibParserAPI.getNameByOID(oid);
	}
	public String getOIDByName(String name){
		return MibParserAPI.getOIDByName(name);
	}
	//根据子节点获取父节点
	public  Map<String,String> getSupserOidByNote(String oid){
		return MibParserAPI.getSupserOidBYNote(oid);
	}
	 //根据父节点, 查询子节点
	public  List<String> getNodeBySupserId(String oid){
		return MibParserAPI.getNodeBySupserId(oid);
	}
	public void init(){
		SNMPSingleInstance singleton = SNMPSingleInstance.getInstance();
		snmp = singleton.createSNMP();
	}


    public String getMibPath() {
        return mibPath;
    }

    public void setMibPath(String mibPath) {
        this.mibPath = mibPath;
    }

    public String getTrapIp() {
        return trapIp;
    }

    public void setTrapIp(String trapIp) {
        this.trapIp = trapIp;
    }

    public int getTrapPort() {
        return trapPort;
    }

    public void setTrapPort(int trapPort) {
        this.trapPort = trapPort;
    }

    public int getSnmpListenPort() {
        return snmpListenPort;
    }

    public void setSnmpListenPort(int snmpListenPort) {
        this.snmpListenPort = snmpListenPort;
    }

    public Snmp getSnmp() {
        return snmp;
    }

    public void setSnmp(Snmp snmp) {
        this.snmp = snmp;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public int getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }

    public String getMulticastIp() {
        return multicastIp;
    }

    public void setMulticastIp(String multicastIp) {
        this.multicastIp = multicastIp;
    }
}
