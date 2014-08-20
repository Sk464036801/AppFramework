package com.appframework.agent.socket;

import java.net.InetAddress;

import com.appframework.agent.util.AppContext;
import com.appframework.agent.util.StringUtil;
import org.apache.log4j.Logger;

/**
 * 接受组播消息 ，更改配置
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

			}
			
			
		}
	  }catch (Exception e) {
		  logger.error("multicastReceived error:",e);
	 }
	}

	@Override
	public void run() {
		init();
	}
	
	
	
	
}
