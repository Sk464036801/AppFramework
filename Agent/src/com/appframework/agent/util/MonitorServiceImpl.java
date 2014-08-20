package com.appframework.agent.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.management.ManagementFactory;

import com.moto.agent.model.MonitorInfoBean;
import com.sun.management.OperatingSystemMXBean;

/**
 * for windows
 * @author xum
 *
 */
public class MonitorServiceImpl  {

	private static final int CPUTIME = 5000;
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;
	private AppContext appContext;
	private static final Log logger = LogFactory.getLog(MonitorServiceImpl.class);
	
	public MonitorInfoBean getMonitorBean() throws Exception {
		int kb = 1024;
		
		long totalMemory = Runtime.getRuntime().totalMemory() / kb;
		long freeMemory = Runtime.getRuntime().freeMemory() / kb;
		long maxMemory = Runtime.getRuntime().maxMemory() / kb;
		
		//OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		
		OperatingSystemMXBean osmxb = null;
		if(System.getProperty("java.version").contains("1.6")){
			osmxb =  (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		}else if(System.getProperty("java.version").contains("1.7")){
			osmxb =  (OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
		}
		
		String osName = System.getProperty("os.name");
		long totalPhysicalMemory = osmxb.getTotalPhysicalMemorySize() / kb;
		long freePhysicalMemory = osmxb.getFreePhysicalMemorySize() / kb;
		long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
		
		ThreadGroup parentThread;
		for(parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent());
		
		int totalThread  = parentThread.activeCount();
		
		double cpuRatio = 0;
		if(osName.toLowerCase().startsWith("windows")){
			cpuRatio = this.getCpuRatioForWindows();
		}else{
			cpuRatio = this.getCpuRatioForLinux();
		}
		
		double diskRatio = 0;
		diskRatio = getDiskInfo();
		Map<String,Double> diskMap = getEachDiskInfo();
		
		MonitorInfoBean infoBean = new MonitorInfoBean();
		infoBean.setFreeMemory(freeMemory);
		infoBean.setFreePhysicalMemorySize(freePhysicalMemory);
		infoBean.setMaxMemory(maxMemory);
		infoBean.setOsName(osName);
		infoBean.setTotalMemory(totalMemory);
		infoBean.setTotalPhysicalMemorySize(totalPhysicalMemory);
		infoBean.setTotalThread(totalThread);
		infoBean.setUsedMemory(usedMemory);
		infoBean.setCpuRatio(cpuRatio);
		infoBean.setMemoryRatio(Double.valueOf(100 * (usedMemory) / (totalPhysicalMemory)).doubleValue() );
		infoBean.setDiskRatio(diskRatio);
		infoBean.setDiskRatioMap(diskMap);
		return infoBean;
	}
	public static void main(String[] args) {
		MonitorServiceImpl ms = new MonitorServiceImpl(null);
		try {
//			MonitorInfoBean bean = ms.getMonitorBean();
//			System.out.println(bean);
			String str = "root:/,home:/home";
			String[] str1 = str.split(",");
			String[] str2 = str1[0].split(":");
			System.out.println(str2[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	//获取硬盘使用情况，适用于linux，windows，mac os
	 public double  getDiskInfo() {
//        File[] roots = File.listRoots();// 获取磁盘分区列表
		 File[] roots = appContext.getRoots();
		 long free = 0;
		 long total = 0;
		 long use = 0;
        for (File file : roots) {
            logger.debug(file.getPath() + "信息如下:");
            free += file.getFreeSpace();
            total += file.getTotalSpace();
//            use += total - free;
            logger.debug("distStat_空闲未使用 = " + change(file.getFreeSpace()) + "G");// 空闲空间
            logger.debug("distStat_已经使用 = " + change(file.getTotalSpace() - file.getFreeSpace()) + "G");// 可用空间
            logger.debug("distStat_总容量 = " + change(file.getTotalSpace()) + "G");// 总空间
            logger.debug("使用百分比 = " + bfb(file.getTotalSpace() - file.getFreeSpace(), file.getTotalSpace()) + "%");

        }
        use = total - free;
        return Double.valueOf(bfb(use,total));
    }
	
	public Map<String, Double> getEachDiskInfo(){
		Map<String,Double> diskMap = new HashMap<String, Double>();
		Map<String,File> pathMap = appContext.getDiskMap();
		long free = 0;
		long total = 0;
		long use = 0;
		Set<String> ketSet = pathMap.keySet();
		File file = null;
		for(String key : ketSet){
			file = pathMap.get(key);
            free = file.getFreeSpace();
            total = file.getTotalSpace();
            use = total - free;
            diskMap.put(key, Double.valueOf(bfb(use,total)));
		}
		
		return diskMap;
	}
	 
	 
    public static long change(long num) {
        // return num;
        return num / 1024 / 1024 / 1024;
    }
    public static String bfb(Object num1, Object num2) {
        double val1 = Double.valueOf(num1.toString());
        double val2 = Double.valueOf(num2.toString());
        if (val2 == 0) {
            return "0.0";
        } else {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(val1 / val2 * 100);
        }
    }
	
	//获取linux的cpu使用情况
	public  float getCpuRatioForLinux() 
	{
		try {
			File file = new File("/proc/stat");
			BufferedReader br = new BufferedReader(new InputStreamReader(
			new FileInputStream(file)));
			StringTokenizer token = new StringTokenizer(br.readLine());
			token.nextToken();
			int user1 = Integer.parseInt(token.nextToken());
			int nice1 = Integer.parseInt(token.nextToken());
			int sys1 = Integer.parseInt(token.nextToken());
			int idle1 = Integer.parseInt(token.nextToken());
		
			Thread.sleep(1000);
		
			br = new BufferedReader(
			new InputStreamReader(new FileInputStream(file)));
			token = new StringTokenizer(br.readLine());
			token.nextToken();
			int user2 = Integer.parseInt(token.nextToken());
			int nice2 = Integer.parseInt(token.nextToken());
			int sys2 = Integer.parseInt(token.nextToken());
			int idle2 = Integer.parseInt(token.nextToken());
		
			return (float)((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) / (float)((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1)) * 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private double getCpuRatioForWindows(){
		try {
			String procCmd = System.getenv("windir")+"\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
							+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			
			if(c0 != null && c1 != null){
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(  
                        PERCENT * (busytime) / (busytime + idletime))  
                        .doubleValue();  
				
			}else{
				return 0.0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	private long[] readCpu(final Process proc){
		long[] result = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader lr = new LineNumberReader(ir);
			String line = lr.readLine();
			if(line == null || line.length() < FAULTLENGTH){
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			
			while((line = lr.readLine()) != null){
				if(line.length() < wocidx){
					continue;
				}
				String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();
				String cmd = Bytes.substring(line, cmdidx, kmtidx-1).trim();
				if(cmd.indexOf("wmic.exe") >= 0){
					continue;
				}
				
				if(caption.equals("System Idle Process") || caption.equals("System")){
					idletime += Long.valueOf(Bytes.substring(line, kmtidx, rocidx -1).trim()).longValue();
					idletime += Long.valueOf(Bytes.substring(line, umtidx, wocidx - 1).trim()).longValue();
					continue;
				}
				
				kneltime += Long.valueOf(Bytes.substring(line, kmtidx, rocidx -1).trim()).longValue();
				usertime += Long.valueOf(Bytes.substring(line, umtidx, wocidx -1).trim()).longValue();
			}
			result[0] = idletime;
			result[1] = kneltime + usertime;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				proc.getInputStream().close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	public MonitorServiceImpl(AppContext appContext) {
		this.appContext = appContext;
	}

	
	

}
