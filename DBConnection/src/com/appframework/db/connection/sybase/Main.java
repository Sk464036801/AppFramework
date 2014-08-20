package com.appframework.db.connection.sybase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sybase.jdbc4.jdbc.SybDriver;

public class Main {
	
	public static void main(String[] args) {
		connect();
	}
	
	
	public static void connect()
	{
	   String host = "192.168.1.113";
	   String url = "jdbc:sybase:Tds:"+host+":4100";
	   String username = "sa";
	   String password ="sybase";
	   SybDriver sybDriver = null;
	   Connection conn;

	   try 
	   {
	      sybDriver=(SybDriver)Class.forName("com.sybase.jdbc4.jdbc.SybDriver").newInstance();
	      System.out.println("Driver Loaded");
	      conn = DriverManager.getConnection(url,username,password);
	      Statement stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery("select name, type from sysobjects");
	      while(rs.next()){
		      System.out.println(rs.getString(1) + "->" +rs.getString(2));  
	      }


	   } 
	   catch (InstantiationException ex) 
	   {
	      ex.printStackTrace();
	   } 
	   catch (IllegalAccessException ex) 
	   {
	      ex.printStackTrace();
	   } 
	   catch (ClassNotFoundException ex) 
	   {
	      ex.printStackTrace();
	   } 
	   catch (SQLException ex) 
	   {
	      ex.printStackTrace();
	   }
	}

}
