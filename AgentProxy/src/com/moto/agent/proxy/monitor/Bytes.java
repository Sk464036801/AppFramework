package com.moto.agent.proxy.monitor;

/**
 * 针对监控cpu，内存等信息，处理字符串的类
 * @author xum
 *
 */
public class Bytes {

	public static String substring(String src, int start_idx, int end_idx){
		byte[] b = src.getBytes();
		String tgt = "";
		for(int i = start_idx; i <= end_idx; i ++){
			tgt += (char)b[i];
		}
		return tgt;
	}
}
