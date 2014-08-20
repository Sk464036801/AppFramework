package com.appframework.agent.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class PropertyPlaceholderConfigurerEX extends PropertyPlaceholderConfigurer {
     private boolean secutiry = false;
     private DESEncrypt desEncrypt = null;
     private static Log logger = LogFactory.getLog(PropertyPlaceholderConfigurerEX.class);
     @Override
     protected String resolvePlaceholder(String placeholder, Properties props) {
         String placeholderValue = props.getProperty(placeholder);
         if(this.secutiry){
              placeholderValue = deEncrypt(placeholderValue);
         }
         logger.info("DeEncrypt placeholder = " + placeholder+ ", Data = " + placeholderValue);
         return placeholderValue;
     }
     
    
	public void setSecutiry(boolean secutiry) {
		this.secutiry = secutiry;
	}


	public void setDesEncrypt(DESEncrypt desEncrypt) {
		this.desEncrypt = desEncrypt;
	}


	private String deEncrypt(String data) {
		try {
			if(data == null || "".equals(data)) return data;
			else return desEncrypt.Decryptor(data);
		} catch (Exception e) {
			logger.error("deEncrypt Exception", e);
			return "";
		}
	}
}