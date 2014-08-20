package com.appframework.agent.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.snmp4j.Snmp;

import com.moto.agent.snmp.SNMPSingleInstance;
import com.moto.agent.snmp.service.OmcService;

/**
 * 
 * @author lisp
 * 
 *         2012-5-15
 */
public class AppContext {
	private OmcService omcService;
	public  String mibPath;
	private String trapIp;
	private int trapPort;
	private int snmpListonPort;
	private int updateInterval;
	private int statistics_interval;
	private int statistics_granularity;
	private String grading;
	private String interval;
	private int multicastPort;
	private String multicastIp;
	private double alarmUseRate;
	private int clearDays;
	
	private boolean autoReport;	//是否自动上报，true代表自动上报，false代表只有当网管发请求过来时才上报
	private int appIndex;
	
	private Snmp snmp;
	private String community;
	private String cpuRecordPath;
	private int getCpuInterval;
	
	private double memoryAlarmUseRate;
	private double diskAlarmUseRate;
	private int statSysInterval;
	
	private DataSource dataSource;
	private String diskMounted;
	private File[] roots;
	
	private Map<String, File> diskMap;
	
	private String switchKPIThread;
	
	
	//根据key获取val
	public  String getNameByOID(String oid){
//		MibParserAPI.load(mibPath);
		return MibParserAPI.getNameByOID(oid);
	}
	public String getOIDByName(String name){
//		MibParserAPI.load(mibPath);
		return MibParserAPI.getOIDByName(name);
	}
	//根据子节点获取父节点
	@SuppressWarnings("unchecked")
	public  Map<String,String> getSupserOidBYNote(String oid){
//		MibParserAPI.load(mibPath);
		return MibParserAPI.getSupserOidBYNote(oid);
	}
	 //根据父节点, 查询子节点
	public  List<String> getNodeBySupserId(String oid){
//		 MibParserAPI.load(mibPath);
		return MibParserAPI.getNodeBySupserId(oid);
	}
	public void init(){
//		MibUtil.getInstance(mibPath);
		SNMPSingleInstance singleton = SNMPSingleInstance.getInstance();
		snmp = singleton.createSNMP();
//		setSnmp(snmp);
	}
	

	public int getSnmpListonPort() {
		return snmpListonPort;
	}

	public void setSnmpListonPort(int snmpListonPort) {
		this.snmpListonPort = snmpListonPort;
	}

	public int getUpdateInterval() {
		return updateInterval;
	}

	public void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}


	public OmcService getOmcService() {
		return omcService;
	}

	public void setOmcService(OmcService omcService) {
		this.omcService = omcService;
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
	public String getGrading() {
		return grading;
	}
	public void setGrading(String grading) {
		this.grading = grading;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public int getStatistics_interval() {
		return statistics_interval;
	}
	public void setStatistics_interval(int statisticsInterval) {
		statistics_interval = statisticsInterval;
	}
	public int getStatistics_granularity() {
		return statistics_granularity;
	}
	public void setStatistics_granularity(int statisticsGranularity) {
		statistics_granularity = statisticsGranularity;
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
	public double getAlarmUseRate() {
		return alarmUseRate;
	}
	public void setAlarmUseRate(double alarmUseRate) {
		this.alarmUseRate = alarmUseRate;
	}
	public int getClearDays() {
		return clearDays;
	}
	public void setClearDays(int clearDays) {
		this.clearDays = clearDays;
	}
	public boolean isAutoReport() {
		return autoReport;
	}
	public void setAutoReport(boolean autoReport) {
		this.autoReport = autoReport;
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
	public String getCpuRecordPath() {
		return cpuRecordPath;
	}
	public void setCpuRecordPath(String cpuRecordPath) {
		this.cpuRecordPath = cpuRecordPath;
	}
	public int getGetCpuInterval() {
		return getCpuInterval;
	}
	public void setGetCpuInterval(int getCpuInterval) {
		this.getCpuInterval = getCpuInterval;
	}
	public double getMemoryAlarmUseRate() {
		return memoryAlarmUseRate;
	}
	public void setMemoryAlarmUseRate(double memoryAlarmUseRate) {
		this.memoryAlarmUseRate = memoryAlarmUseRate;
	}
	public double getDiskAlarmUseRate() {
		return diskAlarmUseRate;
	}
	public void setDiskAlarmUseRate(double diskAlarmUseRate) {
		this.diskAlarmUseRate = diskAlarmUseRate;
	}
	public int getStatSysInterval() {
		return statSysInterval;
	}
	public void setStatSysInterval(int statSysInterval) {
		this.statSysInterval = statSysInterval;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public File[] getRoots() {
		return roots;
	}
	
	public Map<String, File> getDiskMap() {
		return diskMap;
	}
	public void setDiskMounted(String diskMounted) {
		this.diskMounted = diskMounted;
		
		String[] filePath = diskMounted.split(",");
		this.roots = new File[filePath.length];
		this.diskMap = new HashMap<String, File>();
		int i = 0;
		for(String path : filePath){
			String[] data = path.split(":");
			File file = null;
			if(data != null && data.length == 2){
				file = new File(data[1]);
				this.roots[i] = file;
				this.diskMap.put(data[0], file);
			}else{
				this.roots[i] = new File(path);
			}
			
			i++;
		}
		
	}
	public int getAppIndex() {
		return appIndex;
	}
	public void setAppIndex(int appIndex) {
		this.appIndex = appIndex;
	}
	public String getSwitchKPIThread() {
		return switchKPIThread;
	}
	public void setSwitchKPIThread(String switchKPIThread) {
		this.switchKPIThread = switchKPIThread;
	}
	
	
	
	
}
