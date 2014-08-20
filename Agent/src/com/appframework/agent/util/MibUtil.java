package com.appframework.agent.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.browser.MibNode;
import net.percederberg.mibble.browser.MibTreeBuilder;
import net.percederberg.mibble.value.ObjectIdentifierValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MibUtil {
	private final static Logger logger = LoggerFactory.getLogger(MibUtil.class);
	private static MibUtil instance=null;
	private static ArrayList<String> loadedMibs = new ArrayList<String> ();
	private static List<String> mibValues=new ArrayList<String>(); //所有key
	private static Map<String,String> val=new HashMap<String,String>();
	private static Mib mib = null;
	/**
	 * @param args
	 * @throws net.percederberg.mibble.MibLoaderException
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException,
			MibLoaderException {
		new MibUtil("");
	}
	public static MibUtil getInstance(String path) {
		if(instance==null)
			instance=new MibUtil(path);
		return instance;
	}
    private static void addSymbol( MibSymbol symbol) {
        MibValue               value;
        ObjectIdentifierValue  oid;
        if (symbol instanceof MibValueSymbol) {
            value = ((MibValueSymbol) symbol).getValue();
            if (value instanceof ObjectIdentifierValue) {
                oid = (ObjectIdentifierValue) value;
                MibNode mib=   addToTree(oid);
                //System.out.println(mib.getName()+"==="+mib.getOid()+"==");
               /* if(mib.getSnmpObjectType()!=null){
                	System.out.println("name:"+mib.getName()+"==="+mib.getSnmpObjectType());
                }*/
                mibValues.add(mib.getValue().toString());
                val.put(mib.getValue().toString(), mib.getLevel()+"");
                	//System.out.println("name:"+mib.getName().split(" ")[0]+"==="+mib.getLevel()+"=="+mib.getValue());
            }
        }
    }
    /**
     * 获取下一个节点配置
     * @param oid
     * @return
     */
    public static String getNextOid(String oid){
    	boolean args=false;
    	for(String val:mibValues){
    		if(args){
    			return  val;
    		}
    		if(val.equals(oid)){
    			args=true;
    		}
    	}
    	return null;
    }
    /**
     * 根据oid获取value
     * @param oid
     * @return
     */
    public static String getOid(String oid){
    	MibValueSymbol mibValueSymbol=null;
		mibValueSymbol=mib.getSymbolByValue(oid);
		if(mibValueSymbol!=null)
			return mibValueSymbol.getName();
		return null;
    }
    /**
     * 根据oid获取子节点
     * @param oid
     * @return
     */
    public static List<String> getNode(String oid){
    	if(oid==null)
    		return null;
    	List<String> oids=new ArrayList<String>();
    	int j=0;
    	for(String val:mibValues){
    		j=val.lastIndexOf(".");
    		String v=val.substring(0,j);
    		if(oid.equals(v)){
    			oids.add(val);
    		}
    	}
    	if(oids.size()==0){//如果未找到子节点，返回本身
    		oids.add(oid);
    	}
		return oids;
    }
    /**
     * 获取父节点
     * @param oid
     * @return
     */
    public static String getSupserNode(String oid){
    	if(oid==null)
    		return null;
    	int i=oid.lastIndexOf(".");
    	 oid=oid.substring(0,i);
		return oid;
    	
    }
    /**
     * 检查是否存在值
     * @param name
     * @return
     */
    public static boolean checkValue(String name){
    	for(String val:mibValues){
    		if(getOid(val).equals(name)){
    			return true;
    		}
    	}
    	return false;
    }
    
    private static MibNode addToTree(ObjectIdentifierValue oid) {
        MibNode  parent;
        MibNode  node;
        String   name;

        // Add parent node to tree (if needed)
        if (hasParent(oid)) {
            parent = addToTree( oid.getParent());
        } else {
            parent = new MibNode("李水平", null);
        }

        // Check if node already added
 /*       for (int i = 0; i < model.getChildCount(parent); i++) {
            node = (MibNode) model.getChild(parent, i);
            if (node.getValue().equals(oid)) {
                return node;
            }
        }*/

        // Create new node
        name = oid.getName() + " (" + oid.getValue() + ")";
        node = new MibNode(name, oid);
        parent.add(node);
      //  System.out.println(oid.getSymbol()+"====" +node.getSiblingCount());
        return node;
    }
    
    private static  boolean hasParent(ObjectIdentifierValue oid) {
        ObjectIdentifierValue  parent = oid.getParent();
        return oid.getSymbol() != null
            && oid.getSymbol().getMib() != null
            && parent != null
            && parent.getSymbol() != null
            && parent.getSymbol().getMib() != null
            && parent.getSymbol().getMib().equals(oid.getSymbol().getMib());
    }
    
    public MibUtil(String path){
    	try{
    	MibTreeBuilder mb = MibTreeBuilder.getInstance();
		mib = mb.loadMib(new File(path));
		for (int i = 0; i < loadedMibs.size(); i++) {
			if (mib.getName().equals(loadedMibs.get(i))) {
				return;
			}
		}
		 MibSymbol  symbol;
		 Iterator   iter = mib.getAllSymbols().iterator();
	     while (iter.hasNext()) {
	            symbol = (MibSymbol) iter.next();
	            addSymbol(symbol);
	       }
    	}catch (Exception e) {
			logger.error("error:",e);
		}
    	System.out.println(getNode("1.3.6.1.4.1.1.1.1"));
    }
	public static List<String> getMibValues() {
		return mibValues;
	}
	public static void setMibValues(List<String> mibValues) {
		MibUtil.mibValues = mibValues;
	}
	public static Map<String, String> getVal() {
		return val;
	}
	public static void setVal(Map<String, String> val) {
		MibUtil.val = val;
	}
	public static Mib getMib() {
		return mib;
	}
	public static void setMib(Mib mib) {
		MibUtil.mib = mib;
	}
}
