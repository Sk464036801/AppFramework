package com.moto.agent.proxy.monitor;

import com.moto.agent.proxy.model.AppContext;
import com.moto.agent.proxy.snmp.util.SnmpUtils;
import com.moto.agent.proxy.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Eric on 10/27/14.
 */
public class SystemMonitorThread extends Thread {

    private static final Log logger = LogFactory.getLog(SystemMonitorThread.class);
    private AppContext appContext;

    private int cpuAlarm;
    private int cancelCpuAlarm;
    private int memoryAlarm;
    private int cancelMemoryAlarm;
    private int storageDiskAlarm;
    private int cancelStorageDiskAlarm;

    private static final int TRAP_STATE_SEND = 1;
    private static final int TRAP_STATE_CANCEL = 0;

    private int cpuTrapState;
    private int memoryTrapState;
    private int diskTrapState;

    @Override
    public void run() {
        logger.info(" -- System Monitor Thread Startup. --");
        while (true){
            SnmpTrapSystemAlarm();

            //线程休眠
            try {
                Thread.sleep(appContext.getAlarmThreadInterval()*1000);
            } catch (InterruptedException e) {
               logger.error("thread sleep exception ->",e);
            }
        }

    }

    private void initTrapState(){
        cpuTrapState = TRAP_STATE_CANCEL;
        memoryTrapState = TRAP_STATE_CANCEL;
        diskTrapState = TRAP_STATE_CANCEL;
    }

    private void SnmpTrapSystemAlarm(){

        MonitorServiceImpl ms = new MonitorServiceImpl(appContext);
        MonitorInfoBean mb = null;
        try {
            mb = ms.getMonitorBean();

            if(!StringUtil.isEmpty(mb)){

                logger.info("SnmpTrapSystemAlarm cpu alarm rate = " + appContext.getCpuAlarmRate() +", use rate = " + mb.getCpuRatio());
                if (appContext.getCpuAlarmRate() <= mb.getCpuRatio() && cpuTrapState == TRAP_STATE_CANCEL) {
                    cpuTrapState = TRAP_STATE_SEND;
                    SnmpUtils.sendTrapInfo(appContext,cpuAlarm);
                } else if (appContext.getCpuAlarmRate() > mb.getCpuRatio() && cpuTrapState == TRAP_STATE_SEND) {
                    cpuTrapState = TRAP_STATE_CANCEL;
                    SnmpUtils.sendTrapInfo(appContext,cancelCpuAlarm);
                }
                logger.info("SnmpTrapSystemAlarm memory alarm rate = " + appContext.getMemoryAlarmRate() + ", user rate = " + mb.getMemoryRatio());
                logger.debug("memoryTrapState = " + memoryTrapState);
                if (appContext.getMemoryAlarmRate() <= mb.getMemoryRatio() && memoryTrapState == TRAP_STATE_CANCEL) {
                    logger.debug("send trap info");
                    memoryTrapState = TRAP_STATE_SEND;
                    SnmpUtils.sendTrapInfo(appContext,memoryAlarm);
                } else if (appContext.getMemoryAlarmRate() > mb.getMemoryRatio() && memoryTrapState == TRAP_STATE_SEND) {
                    logger.debug("cancel trap info");
                    memoryTrapState = TRAP_STATE_CANCEL;
                    SnmpUtils.sendTrapInfo(appContext, cancelMemoryAlarm);
                }
                logger.info("SnmpTrapSystemAlarm disk alarm rate = " + appContext.getStorageDiskAlarmRate() + ", use rate = " + mb.getDiskRatio());
                if (appContext.getStorageDiskAlarmRate() <= mb.getDiskRatio() && diskTrapState == TRAP_STATE_CANCEL) {
                    diskTrapState = TRAP_STATE_SEND;
                    SnmpUtils.sendTrapInfo(appContext,storageDiskAlarm);
                } else if (appContext.getStorageDiskAlarmRate() > mb.getDiskRatio() && diskTrapState == TRAP_STATE_SEND) {
                    diskTrapState = TRAP_STATE_CANCEL;
                    SnmpUtils.sendTrapInfo(appContext,cancelStorageDiskAlarm);
                }
            } else {
                logger.info("SnmpTrapSystemAlarm get system info fail.");
            }


        } catch (Exception e) {
            logger.error("SnmpTrapSystemAlarm exception ->",e);
        }


    }

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    public int getCpuAlarm() {
        return cpuAlarm;
    }

    public void setCpuAlarm(int cpuAlarm) {
        this.cpuAlarm = cpuAlarm;
    }

    public int getCancelCpuAlarm() {
        return cancelCpuAlarm;
    }

    public void setCancelCpuAlarm(int cancelCpuAlarm) {
        this.cancelCpuAlarm = cancelCpuAlarm;
    }

    public int getMemoryAlarm() {
        return memoryAlarm;
    }

    public void setMemoryAlarm(int memoryAlarm) {
        this.memoryAlarm = memoryAlarm;
    }

    public int getCancelMemoryAlarm() {
        return cancelMemoryAlarm;
    }

    public void setCancelMemoryAlarm(int cancelMemoryAlarm) {
        this.cancelMemoryAlarm = cancelMemoryAlarm;
    }

    public int getStorageDiskAlarm() {
        return storageDiskAlarm;
    }

    public void setStorageDiskAlarm(int storageDiskAlarm) {
        this.storageDiskAlarm = storageDiskAlarm;
    }

    public int getCancelStorageDiskAlarm() {
        return cancelStorageDiskAlarm;
    }

    public void setCancelStorageDiskAlarm(int cancelStorageDiskAlarm) {
        this.cancelStorageDiskAlarm = cancelStorageDiskAlarm;
    }
}
