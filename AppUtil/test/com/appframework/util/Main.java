package com.appframework.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
//		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(inputFormat.format(cal.getTime()));
		cal.add(Calendar.DAY_OF_MONTH, -0);
		System.out.println(inputFormat.format(cal.getTime()));
		
		System.out.println(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH));
		
		int lacId = 456;
		int value = Integer.valueOf(Integer.toHexString(lacId).substring(0, 2),16).intValue();
		System.out.println("value = " + value);
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("name1"));
		list.add(new User("name2"));
		list.add(new User("name3"));
		list.add(new User("name4"));
		list.add(new User("name5"));
		list.add(new User("name5"));
		
		ArrayList<User> list2 = (ArrayList<User>)list.clone();
		list = null;
		System.out.println(list2.toString());
		
		List<String> userPowerList = selectUserPowerId(576);
		System.out.println(userPowerList.toString());
	}

	
	private static List<String> selectUserPowerId(int powerId){
		
		final int USER_POWER_MANAGER = 0x0040; //64
		final int USER_POWER_CITY = 0x0080; //128
		final int USER_POWER_PROVINCE = 0x0100;//256
		final int USER_POWER_SYSTEM = 0x0200;//512
		
		List<String>  selectList = new  ArrayList<String>();
		System.out.println(powerId&USER_POWER_MANAGER);
		System.out.println(powerId|USER_POWER_MANAGER);
		if ( (powerId&USER_POWER_MANAGER) == USER_POWER_MANAGER){
			selectList.add("manager");
		}
		
		if ( (powerId&USER_POWER_CITY) == USER_POWER_CITY){
			selectList.add("city");
		}
		
		if ( (powerId&USER_POWER_PROVINCE) == USER_POWER_PROVINCE){
			selectList.add("province");
		}
		System.out.println(powerId&USER_POWER_SYSTEM);
		if ( (powerId&USER_POWER_SYSTEM) == USER_POWER_SYSTEM){
			selectList.add("system");
		}
		
		return selectList;
	}
}
