package com.appframework.agent;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by Eric on 8/20/14.
 * .通过Spring来加载相关的资源文件
 */
public class Launcher {

    public static Logger logger = LoggerFactory.getLogger(Launcher.class);

    private static ApplicationContext context = null;

    public static void main(String[] args) {

        try {
            PropertyConfigurator.configure("./conf/log4j.properties");
            context = new FileSystemXmlApplicationContext(
                    new String[] {"/conf/applicationContext.xml" }
            );
        } catch (BeansException e) {
            logger.error("Agent Launcher Error :",e);
        }

    }
}
