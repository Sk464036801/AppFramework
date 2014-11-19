package com.moto.agent.proxy.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(StringUtil.class);
	public static SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * this method call String.split and remove the empty string in result array
	 * and trim the real string so that we can deal with the more than one
	 * whitespace betwwen two token
	 */

	public static String[] trimSplit(String s, String delimiter) {

		String words[] = s.split(delimiter);
		int count = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i] == null || words[i].trim().length() == 0)
				continue;
			else {
				words[count] = words[i].trim();
				count++;
			}
		}

		String result[] = new String[count];
		System.arraycopy(words, 0, result, 0, count);
		return (result);
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String bytesToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(byteToHexString(b[i]));
		}
		return sb.toString();
	}

	public static final byte[] hexStringToByte(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	public static final String DEFAULT_OPEN_TOKEN = "${";
	public static final String DEFAULT_CLOSE_TOKEN = "}";

	public static String replaceVariable(String msg, String varName, int value) {
		return (replaceVariable(msg, varName, String.valueOf(value), false,
				DEFAULT_OPEN_TOKEN, DEFAULT_CLOSE_TOKEN));
	}

	public static String replaceVariable(String msg, String varName, int value,
			boolean appendWhenNoVar) {
		return (replaceVariable(msg, varName, String.valueOf(value),
				appendWhenNoVar, DEFAULT_OPEN_TOKEN, DEFAULT_CLOSE_TOKEN));
	}

	public static String replaceVariable(String msg, String varName,
			String value) {
		return (replaceVariable(msg, varName, value, false, DEFAULT_OPEN_TOKEN,
				DEFAULT_CLOSE_TOKEN));
	}

	public static String replaceVariable(String msg, String varName,
			String value, boolean appendWhenNoVar) {
		return (replaceVariable(msg, varName, value, appendWhenNoVar,
				DEFAULT_OPEN_TOKEN, DEFAULT_CLOSE_TOKEN));
	}

	public static String replaceVariable(String msg, String varName,
			String value, boolean appendWhenNoVar, String openToken,
			String closeToken) {
		if (msg == null || msg.length() == 0) {
			return returnNoVar(msg, value, appendWhenNoVar);
		}
		if (openToken == null || openToken.length() == 0 || closeToken == null
				|| closeToken.length() == 0)
			return returnNoVar(msg, value, appendWhenNoVar);

		int openTokenLen = openToken.length();
		int closeTokenLen = closeToken.length();

		int offset = 0;
		int pos = -1;
		StringBuilder sb = null;
		int diffLen = 0;

		while ((pos = msg.indexOf(openToken, offset)) >= 0) {

			int varLen = varName.length();
			if (msg.regionMatches(true, pos + openTokenLen, varName, 0, varLen)
					&& msg.startsWith(closeToken, pos + openTokenLen + varLen)) {
				int skipLen = openTokenLen + varLen + closeTokenLen;
				if (sb == null) {
					sb = new StringBuilder();
					sb.append(msg, 0, pos);
					sb.append(value);
					sb.append(msg, pos + skipLen, msg.length());
				} else {
					sb.replace(pos + diffLen, pos + skipLen + diffLen, value);
				}
				diffLen = diffLen + value.length() - skipLen;
				offset = pos + skipLen;
			} else {
				offset = pos + 1;
			}
		}
		if (sb != null)
			return (sb.toString());

		return returnNoVar(msg, value, appendWhenNoVar);
	}

	public static boolean isStringDigital(String str) {
		String strt = str.trim();
		if (strt.length() == 0)
			return false;

		for (int i = 0; i < strt.length(); i++) {
			if (Character.isDigit(strt.charAt(i))) {
				continue;
			}
			return false;
		}
		return true;
	}

	private static String returnNoVar(String msg, String value,
			boolean appendWhenNoVar) {

		if (msg == null)
			msg = "";

		if (appendWhenNoVar)
			return (msg + " " + value);
		else
			return msg;
	}

	public static String makeDownloadFileName(String fileName) {
		return (makeDownloadFileName(fileName, "GBK"));
	}

	public static String makeDownloadFileName(String fileName, String charset) {

		if (fileName == null)
			return (null);

		if (charset == null)
			return fileName;

		String newName;
		try {
			newName = new String(fileName.getBytes(charset), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			newName = fileName;
		}

		return (newName);
	}

	public static byte[] getBytes(String s, String charset) {

		byte[] buf = null;

		if (s == null)
			return (null);

		if (charset == null || charset.length() == 0)
			buf = s.getBytes();
		else {
			try {
				buf = s.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("get bytes error:charset=" + charset, e);
			}
		}

		return (buf);
	}

	public static String byteToString(byte buf[], String charset) {

		if (buf == null)
			return ("");
		if (charset == null || charset.length() == 0)
			return new String(buf);
		else {
			try {
				return new String(buf, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("byte2string unsupport charset:" + charset, e);
				return ("");
			}
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static final Date getDayTime() {
		try {
			return new Date(System.currentTimeMillis());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 得到当前时间减去分钟后的时间
	 * 
	 * @param minute_bad
	 * @return
	 */
	public static final String getMinute_Bad(int minute_bad) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MINUTE, -minute_bad);
		return sf.format(rightNow.getTime());
	}

	public static String getStackTrace(Throwable e) {
		StringBuffer exBuf = new StringBuffer(e.toString());
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++)
			exBuf.append("\nat ").append(trace[i]);
		return exBuf.toString();
	}
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}
}
