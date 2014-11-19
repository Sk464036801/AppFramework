package com.moto.agent.proxy.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FileConfig {

    private Properties config;
    private String configFile;
    private static final Log logger = LogFactory.getLog(FileConfig.class);

    public String getConfig(String config_name) {
        return (config.getProperty(config_name));
    }

    public FileConfig() throws Exception {
        this.config = new Properties();
    }

    public FileConfig(String configFile){
        this.config = new Properties();
        this.setConfigFile(configFile);
    }
    
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
        try {
            this.config.load(new FileInputStream(configFile));
//            iteratorFileConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        try {
            this.config.load(new FileInputStream(configFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iteratorFileConfig() {
        Set<Object> keySet = this.config.keySet();

        for (Iterator<Object> iterator = keySet.iterator(); iterator.hasNext(); ) {
            Object next = iterator.next();
            logger.info( " >>>><<<< iteratorFileConfig ->" + next.toString());
        }
    }
}
