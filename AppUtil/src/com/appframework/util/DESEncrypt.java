package com.appframework.util;

import java.security.InvalidKeyException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESEncrypt {

	// SecretKey 负责保存对称密钥
	private SecretKey secretKey = null;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;
	private IvParameterSpec ivp = null;
	
	private static int blockSize = 8;

	private static byte[] iv = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00 };

	private static final String PASSWORD_CRYPT_KEY = "a1b2c3d4";
	

	public DESEncrypt() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		c = Cipher.getInstance("DES/CBC/NoPadding");
		secretKey = new SecretKeySpec(PASSWORD_CRYPT_KEY.getBytes("utf-8"), "DES");
		ivp = new IvParameterSpec(iv);
	}
	
	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws java.security.InvalidKeyException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */
	public String Encrytor(String str) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, secretKey, ivp);
		byte[] src = DESEncrypt.padding(str).getBytes("utf-8");
		cipherByte = c.doFinal(src);
		return new BASE64Encoder().encode(cipherByte);
	}
	public String Encrytor(byte[] src) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, secretKey, ivp);
		cipherByte = c.doFinal(src);
		return new BASE64Encoder().encode(cipherByte);
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws java.security.InvalidKeyException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */
	public String Decryptor(String str) throws Exception {
		byte[] buff = new BASE64Decoder().decodeBuffer(str);
		c.init(Cipher.DECRYPT_MODE, secretKey, ivp);
		cipherByte = c.doFinal(buff);
		return new String(cipherByte, "UTF-8").trim();
	}

	public String Decryptor(byte[] buff) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, secretKey, ivp);
		cipherByte = c.doFinal(buff);
		return new String(cipherByte, "UTF-8");
	}

	/**
	 * byte[] 转16进制字符
	 * 
	 * @param bytea
	 * @return
	 * @throws Exception
	 */
	public static String byteArr2HexStr(byte[] bytea) throws Exception {
		String sHex = "";
		int iUnsigned = 0;
		StringBuffer sbHex = new StringBuffer();
		for (int i = 0; i < bytea.length; i++) {
			iUnsigned = bytea[i];
			if (iUnsigned < 0) {
				iUnsigned += 256;
			}
			if (iUnsigned < 16) {
				sbHex.append("0");
			}
			sbHex.append(Integer.toString(iUnsigned, 16));
		}
		sHex = sbHex.toString();
		return sHex;
	}

	/**
	 * 16进制字符 转byte[]
	 * 
	 * @param sHex
	 * @return
	 * @throws Exception
	 */
	public static byte[] hexStr2ByteArr(String sHex) throws Exception {

		if (sHex.length() % 2 != 0) {
			sHex = "0" + sHex;
		}
		byte[] bytea = new byte[sHex.length() / 2];

		String sHexSingle = "";
		for (int i = 0; i < bytea.length; i++) {
			sHexSingle = sHex.substring(i * 2, i * 2 + 2);
			bytea[i] = (byte) Integer.parseInt(sHexSingle, 16);
		}
		return bytea;
	}
	
	public static String padding(String txt){
//		int c = blockSize-txt.length()%blockSize;
		int c = blockSize-(txt.length()%blockSize == 0 ? 8:txt.length()%blockSize);
		String paddingObject = " ";
		for (int i=0;i<c;i++){
			txt = txt.concat(paddingObject);
		}
		return txt;
	}
	
    public static void iteratorByteArray(byte[] array) {
    	for(int i=0; i<array.length; i++){
    		//System.out.println("Number i = "+i+" , byte = "+array[i]);
    	}
    }
    
    public static byte signByteToUsignInt(byte intValue){
//    	byte byteValue=0;   
    	int temp = intValue % 256;   
    	if (intValue < 0) {   
    		return (byte)(temp < -128 ? 256 + temp : temp);   
    	}else {   
    	  return (byte)(temp > 127 ? temp - 256 : temp);   
    	}  
    }

	public static void main(String[] args) throws Exception {
		DESEncrypt de1 = new DESEncrypt();
		String msg = "1";
		String encontent = de1.Encrytor(msg);
		String decontent = de1.Decryptor(encontent);
		System.out.println("明文是:" + msg);
		System.out.println("java 加密后:" + encontent);
		System.out.println("java 解密后:" + decontent);
		
	}
}
