package com.web.spider.thread;

import com.web.spider.model.AppContext;
import com.web.spider.util.AppUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Eric on 10/22/14.
 */
public class MainThread extends Thread {

    private static final Log logger = LogFactory.getLog(MainThread.class);
    private AppContext appContext;

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    private void initConfig(){

        logger.info(" -- initConfig execute . -- " + AppUtils.getDateTimeNow());

    }

    @Override
    public void run() {
        initConfig();
    }
}
