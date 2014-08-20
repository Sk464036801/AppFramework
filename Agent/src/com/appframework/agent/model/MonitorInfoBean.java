package com.appframework.agent.model;

import java.util.Map;

public class MonitorInfoBean {

	//总内存
	private long totalMemory;				
	//剩余内存
	private long freeMemory;
	//最大可使用内存
	private long maxMemory;
	//操作系统
	private String osName;
	//总物理内存
	private long totalPhysicalMemorySize;
	//剩余物理内存
	private long freePhysicalMemorySize;
	//已使用物理内存
	private long usedMemory;
	//线程总数 
	private int totalThread;
	//cpu使用率
	private double cpuRatio;
	//内存使用率
	private double memoryRatio;
	
	private double diskRatio;
	//磁盘使用率
	private Map<String,Double> diskRatioMap;
	
	
	public double getDiskRatio() {
		return diskRatio;
	}
	public void setDiskRatio(double diskRatio) {
		this.diskRatio = diskRatio;
	}
	public double getCpuRatio() {
		return cpuRatio;
	}
	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	}
	public double getMemoryRatio() {
		return memoryRatio;
	}
	public void setMemoryRatio(double memoryRatio) {
		this.memoryRatio = memoryRatio;
	}
	
	public long getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}
	public long getFreeMemory() {
		return freeMemory;
	}
	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}
	public long getMaxMemory() {
		return maxMemory;
	}
	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public long getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}
	public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
		this.totalPhysicalMemorySize = totalPhysicalMemorySize;
	}
	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}
	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}
	public long getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}
	public int getTotalThread() {
		return totalThread;
	}
	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}
	
	public Map<String, Double> getDiskRatioMap() {
		return diskRatioMap;
	}
	public void setDiskRatioMap(Map<String, Double> diskRatioMap) {
		this.diskRatioMap = diskRatioMap;
	}
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "MonitorInfoBean ( "
	        + super.toString() + TAB
	        + "totalMemory = " + this.totalMemory + TAB
	        + "freeMemory = " + this.freeMemory + TAB
	        + "maxMemory = " + this.maxMemory + TAB
	        + "osName = " + this.osName + TAB
	        + "totalPhysicalMemorySize = " + this.totalPhysicalMemorySize + TAB
	        + "freePhysicalMemorySize = " + this.freePhysicalMemorySize + TAB
	        + "usedMemory = " + this.usedMemory + TAB
	        + "totalThread = " + this.totalThread + TAB
	        + "cpuRatio = " + this.cpuRatio + TAB
	        + "memoryRatio = " + this.memoryRatio + TAB
	        + "diskRatio = " + this.diskRatio + TAB
	        + " )";
	
	    return retValue;
	}
	
	
}
