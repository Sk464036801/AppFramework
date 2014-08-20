package com.appframework.agent.socket;

import java.net.InetAddress;
import org.apache.log4j.Logger;

import com.moto.agent.model.GlcModuleAlarm;
import com.moto.agent.snmp.util.AppContext;
import com.moto.agent.snmp.util.StringUtil;
/**
 * 接受组播消息 ，更改配置
 * @author lisp
 *
 * 2012-5-18
 */
public class MulticastReceived extends Thread {
	private final Logger logger = Logger.getLogger(MulticastReceived.class); 
//	private static int PORT = 3375;// 接收组播信息的端口
//	private static String IP = "224.0.0.1";// 用于组播的IP
	private AppContext appContext=null;
	public MulticastReceived(AppContext appContext){
		this.appContext=appContext;
	}
	public  void init() {
		try{
		logger.info(appContext.getMulticastIp()+"========"+appContext.getMulticastPort());
		// 创建组播地址对象
		InetAddress address = InetAddress.getByName(appContext.getMulticastIp());
		// 创建组播Socket对象,在特定的端口上接收数据
		java.net.MulticastSocket socket = new java.net.MulticastSocket(appContext.getMulticastPort());
		// 加入到组中，才可以接收到消息
		socket.joinGroup(address);
		
		
		while (true) {
			byte[] date = new byte[100];
			// 接收信息的数据包对象
			java.net.DatagramPacket packet = new java.net.DatagramPacket(date,
					date.length);
			socket.receive(packet);
			logger.debug("date length:"+date.length);
			//String s = (new String(date).trim() + "from" + packet.getAddress()+ ":" + packet.getPort());
			//System.out.println("收到：" + s);
			String cmd=(new String(date).trim());//updateconfig:key=value;key=value;
			logger.debug("recv cmd :"+cmd);
			logger.info(packet.getAddress()+"  multicastReceived  cmd:"+cmd);
			if(!StringUtil.isEmpty(cmd)){
				String []head=cmd.split(":");
				if(!StringUtil.isEmpty(head)){
					//修改配置
					updateConfig(head);
					//omc server入库，通知agent
					alarmFromDB(head);
					//更改是否自动上报的配置,true为自动上报，false为不自动上报
					autoReport(head);
					
				}
			}
			logger.info("getStatistics_granularity:"+appContext.getStatistics_granularity());
			logger.info("getStatistics_interval:"+appContext.getStatistics_interval());
			
			
		}
	  }catch (Exception e) {
		  logger.error("multicastReceived error:",e);
	 }
	}
	private void autoReport(String[] head) {
		try {
			//更改是否自动上报的配置,true为自动上报，false为不自动上报
			if ("setautoreport".equals(head[0])) {
				if (!StringUtil.isEmpty(head[1])) {
					if ("true".equals(head[1])) {
						SnmpMain sm = new SnmpMain();
						sm.runThread();
					}

				}
			}
		} catch (Exception e) {
			logger.error("autoReport error", e);
		}
	}
	private void alarmFromDB(String[] head) {
		//omc server入库，通知agent
		if(head[0].equals("getalarmfromdb")){
			logger.debug("recv length = " + head.length);
			try {
				if(head.length < 3) return;
				String appIndex = head[2].split("_")[1];
				logger.debug("appIndex = "+appIndex);
				
				if (!StringUtil.isEmpty(head[1]) 
						&& Integer.parseInt(appIndex) == appContext.getAppIndex()) {
					GlcModuleAlarm gma = appContext.getOmcService()
							.getModuleAlarmById(
									Integer.parseInt(head[1]),head[2]);
					logger.debug("enable report:"
							+ gma.getIfReport());
					
					if (gma != null && gma.getIfReport() == 1) {
						new CallableSendTrap(appContext)
								.sendGlcModuleAlarm(
										appContext.getTrapIp(),
										appContext.getTrapPort(),
										gma);
					}
				}
			} catch (Exception e) {
				logger.error("alarmFromDB error",e);
			}
		}
	}
	
	
	private void updateConfig(String[] head) {
				
		try {
			//修改配置
			if (head[0].equals("updateconfig")) {
				String[] content = head[1].split(";");
				if (!StringUtil.isEmpty(content)) {
					for (int i = 0; i < content.length; i++) {
						String value = content[i];
						String[] keyValue = value.split("=");
						if (!StringUtil.isEmpty(keyValue)) {
							if (keyValue.length == 2) {
								if (keyValue[0]
										.equals(appContext.getInterval())) {
									appContext.setStatistics_interval(Integer
											.parseInt(keyValue[1]) * 60 * 1000);
								} else if (keyValue[0].equals(appContext
										.getGrading())) {
									appContext
											.setStatistics_granularity(Integer
													.parseInt(keyValue[1]));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("updateConfig error", e);
		}
		
	}
	@Override
	public void run() {
		init();
	}
	
	
	
	
}
