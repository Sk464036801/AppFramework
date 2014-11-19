package com.template.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by Eric on 10/22/14.
 */
public class Launcher {

    private static final Log logger = LogFactory.getLog(Launcher.class);

    public static void main(String[] args) {

        try {
            PropertyConfigurator.configure("conf/log4j.properties");
            new FileSystemXmlApplicationContext(new String[] {
                    "conf/applicationContext.xml", "conf/appModuleContent.xml" });
        } catch (Exception e) {
            logger.error("Server Launcher error", e);
        }

    }
}
