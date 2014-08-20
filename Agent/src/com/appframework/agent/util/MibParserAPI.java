package com.appframework.agent.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.value.ObjectIdentifierValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author lisp
 *
 * 2012-5-16
 */
public class MibParserAPI {
	private final static Logger logger = LoggerFactory.getLogger(MibParserAPI.class);
	private static List<String> mibValues=new ArrayList<String>(); //所有key
	private static Map<String,String> val=new HashMap<String,String>();
	
	
	static MibLoader loader = new MibLoader();;
	@SuppressWarnings("unchecked")
	static Hashtable mibs = new Hashtable();
	//private static Vector<String> mibVector;

	/**
	 * 单个载入MIB库
	 * 
	 * @param path
	 *            - the path of mib file
	 */
	@SuppressWarnings("unchecked")
	public static void load(String path) {
		try {
			File file = new File(path);
			loader.addDir(file.getParentFile());
			Mib m = loader.load(file);
			logger.info("loaded " + m);
			if (m != null)
				mibs.put(m.getName(), m);
			else
				logger.error("can not load " + path);
		} catch (IOException e) {
			logger.error("io error", e);
		} catch (MibLoaderException e) {
			logger.error("load error", e);
		}
		
	}

	/**
	 * 载入多个MIB库
	 * 
	 * @param path
	 *            - the String array to hold mib file paths
	 */
	public static void load(String[] path) {
		for (int i = 0; i < path.length; i++) {
			load(path[i]);
		}
	}

	/**
	 * 根据mib中定义的oid名称获取OID值
	 * 
	 * @param name
	 *            - the name defined in mib
	 * @return the oid correspond to name
	 */
	@SuppressWarnings("unchecked")
	public static String getOIDByName(String name) {
		Enumeration enu = mibs.keys();
		while (enu.hasMoreElements()) {
			String mibName = (String) enu.nextElement();
			String oid = getOIDByName(mibName, name);
			if (oid != null)
				return oid;
		}
		return null;
	}

	/**
	 * 在指定mib库文件时获取与oid名称对应的OID值
	 * 
	 * @param mibName
	 *            - mib file name,exclude path,just a file name
	 * @param name
	 *            - the name defined in mib
	 * @return the oid correspond to name in specified mib file
	 */
	public static String getOIDByName(String mibName, String name) {
		Mib mib = (Mib) mibs.get(mibName);
		MibSymbol symbol = mib.getSymbol(name);
		if (symbol instanceof MibValueSymbol) {
			MibValue value = ((MibValueSymbol) symbol).getValue();
			if (value instanceof ObjectIdentifierValue) {
				ObjectIdentifierValue oid = (ObjectIdentifierValue) value;
				return oid.toString();
			} else
				return null;
		} else
			return null;
	}

	/**
	 * 根据OID值获得相应的oid名称
	 * 
	 * @param OID
	 *            - oid value,
	 * @return oid name correspond to oid value
	 */
	@SuppressWarnings("unchecked")
	public static String getNameByOID(String OID) {
		Enumeration enu = mibs.keys();
		while (enu.hasMoreElements()) {
			String mibName = (String) enu.nextElement();
			String name = getNameByOID(mibName, OID);
			if (name != null)
				return name;
		}
		return null;
	}
	/**
	 * 
	 * @param OID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getSupserOidBYNote(String OID){
		Enumeration enu = mibs.keys();
		while (enu.hasMoreElements()) {
			String mibName = (String) enu.nextElement();
			Map map = getSupserOidBYNote(mibName, OID);
			if (map != null)
				return map;
		}
		return null;
	}
	/**
	 * 根据父节点, 查询子节点
	 * @param OID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getNodeBySupserId(String OID){
		Enumeration enu = mibs.keys();
		while (enu.hasMoreElements()) {
			String mibName = (String) enu.nextElement();
			List<String>list = getNodeBySupserId(mibName, OID);
			if (list != null)
				return list;
		}
		return null;
	}
	

	/**
	 * 在指定的MIB文件中获取OID对应的名称
	 * 
	 * @param mibName
	 *            - mib file name,exclude path,just a file name
	 * @param OID
	 *            - oid vlaue
	 * @return the oid name correspond to oid value in specified mib file
	 */
	public static String getNameByOID(String mibName, String OID) {
		Mib mib = (Mib) mibs.get(mibName);
		MibSymbol symbol = mib.getSymbolByOid(OID);
		if (symbol != null) {
			String name = symbol.getName();
			return name;
		} else
			return null;
	}
	
	/**
	 * 根据父节点获取子节点
	 * @param mibName
	 * @param OID
	 * @return
	 */
	public static List<String> getNodeBySupserId(String mibName, String OID){
		List<String> list=new ArrayList<String>();
		String superName=getNameByOID(OID);
		if(superName!=null){
			//int  i = OID.lastIndexOf(".");
			//OID= OID.substring(0, i);//父节点
			int j=1;
			String val="";
			while(j<100){
				val=getNameByOID(OID+"."+j);
				if(val!=null&&!val.trim().equals(superName.trim())){
					list.add(val);
				}
				j++;
			}
		}
		return list;
	}

	/**
	 * 根据子节点获取父节点
	 * @param mibName
	 * @param OID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getSupserOidBYNote(String mibName, String OID){
		if(OID!=null&&!OID.equals("")){
			Map map=new HashMap<String, String>();
			int  i = OID.lastIndexOf(".");
			OID= OID.substring(0, i); 
			String value=getNameByOID(OID);
			map.put("key", OID);
			map.put("value", value);
			return map;
		}
		return null;
	}
	
	
      
	
	//折半查找
/*	public static  String getNextId(String id) { 
		if (id.startsWith(".")) {
			id=id.substring(1);
		}
		 
		int low = 0;
		int high = mibVector.size() - 1;
		int mid = 0;
		boolean foudFlag=false;
		while (low <= high) {
			
			mid = (low + high) >> 1;
			//logger.debug("low="+low+" high="+high+" mid="+mid);
			String midVal = (String) mibVector.get(mid);

			if (midVal.compareTo(id) < 0)
				low = mid + 1;
			else if (midVal.compareTo(id) > 0)
				high = mid - 1;
			else{
				foudFlag=true;
				break;
			} 
		}  
		
		int nextIdIndex=0; 
		if (foudFlag){
			nextIdIndex=mid+1; 
		}else{  
			nextIdIndex=low; 
		} 
		if (nextIdIndex>mibVector.size() - 1){
			return null;
		}else{
			return((String) mibVector.get(nextIdIndex));
		}
	}*/

	public static void main(String[] args) {
		//MibParserAPI.load("C:/omc-agent.txt");
		String []array=new String[2];
		array[0]="C:/omc-agent.txt";
		array[1]="C:/omc-agent2.txt";	
		MibParserAPI.load(array);
		System.out.println(getNameByOID("1.3.6.1.4.1.1.1"));

		//Map map=getSupserOidBYNote("1.3.6.1.4.1.1.1");
		//System.out.println(map.get("key"));
		//System.out.println(map.get("value"))
		
	/*	List<String> list=getNodeBySupserId("1.3.6.1.4.1.1.1");
		for(String val:list){
			System.out.println(val);
		}*/
		
		//System.out.println(getNextId("1.3.6.1.4.1.1.1"));
	}
}
