/** 
 * Project Name:o2p-logClient 
 * File Name:Constants.java 
 * Package Name:com.ailk.eaap.op2.logclient.common 
 * Date:2015年3月20日下午3:56:57 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.common;  
/** 
 * ClassName:Constants <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年3月20日 下午3:56:57 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public final class Constants {

    
    public static final int BATCH_SEND_LOGS_NUMBER =
            Integer.parseInt(ReadConfig.getConfig("batch_send_logs_number", "20"));
    public static final String IS_SEND_LOGS =
            ReadConfig.getConfig("is_send_logs", "true");
    public static final long SEND_THREAD_SLEEP_TIME =
            Long.parseLong(ReadConfig.getConfig("send_thread_sleep_time", "300"));
    public static final long LIST_INTERVAL_TIME =
            Long.parseLong(ReadConfig.getConfig("list_interval_time", "300"));
    
    public static final int LOG_SEND_TYPE = Integer.parseInt(
            ReadConfig.getConfig("logClient_send_type","1"));
    
    public static final String ACTIVEMQ_BROKERURL = 
            ReadConfig.getConfig("logClient.activemq.brokerURL","");
    public static final int SEND_COMMIT_NUM = 
            Integer.parseInt(ReadConfig.getConfig("logClient.activemq.send.commit.num","500"));
    public static final int DELIVERYMODE =
            Integer.parseInt(ReadConfig.getConfig("logClient.activemq.deliveryMode","2"));
    
    public static final boolean IS_START_SUMMARY_THREAD=
            Boolean.valueOf(ReadConfig.getConfig("logclient.is_start_summary_thread", "true"));
    public static final int SUMMARY_THREAD_REGULAR=
            Integer.parseInt(ReadConfig.getConfig("logclient.summary_thread_regular_time", "10000"));
    public static final int THREAD_NUM = Integer.parseInt(
                ReadConfig.getConfig("threadNum","30"));
    private Constants(){
        
    }
    
}
