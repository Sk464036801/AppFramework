package com.moto.agent.proxy.mina.codec;

/**
 * Created by Eric on 10/23/14.
 */
public interface Constants {

    //消息头长度定义
    int HEADER_LEN = 12;

    //响应请求消息成功，失败定义
    int RESPONSE_SUCCESS        = 0x0000;
    int RESPONSE_FAIL           = 0x0001;

    //消息类型定义
    int SNMP_CONNECTION_REQ    = 0x0001;
    int SNMP_CONNECTION_RESP   = 0x0002;
    int SNMP_ALARM_REQ         = 0x0003;
    int SNMP_ALARM_RESP        = 0x0004;
    int SNMP_LINK_REQ          = 0x0005;
    int SNMP_LINK_RESP         = 0x0005;

    //告警类型定义
    int DRA_1_CONNECTION_FAIL   =0x0001;
    int DRA_2_CONNECTION_FAIL   =0x0002;
    int SH_1_CONNECTION_FAIL    =0x0003;
    int SH_2_CONNECTION_FAIL    =0x0004;
    int LOW_SUCC_RATE_RAISE     =0x0005;
    int OVER_TPS_RAISE      	=0x0006;
    int OVER_CON_CURRENT_RAISE  =0x0007;
    int OVER_CPU_RAISE      	=0x0008;
    int OVER_MEM_RAISE      	=0x0009;
    int OVER_STORAGE_RAISE      =0x0010;
    int DRA_1_CONNECTION_SUCC   =0x1001;
    int DRA_2_CONNECTION_SUCC   =0x1002;
    int SH_1_CONNECTION_SUCC    =0x1003;
    int SH_2_CONNECTION_SUCC    =0x1004;
    int LOW_SUCC_RATE_CANCEL    =0x1005;
    int OVER_TPS_CANCEL      	=0x1006;
    int OVER_CON_CURRENT_CANCEL =0x1007;
    int OVER_CPU_CANCEL       	=0x1008;
    int OVER_MEM_CANCEL       	=0x1009;
    int OVER_STORAGE_CANCEL     =0x1010;
}
