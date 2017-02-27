/** 
 * Project Name:o2p-logClient 
 * File Name:SummaryLogsThread.java 
 * Package Name:com.ailk.eaap.op2.logclient.send.service 
 * Date:2015年4月29日下午4:12:32 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.send.service;  


import com.ailk.eaap.op2.logclient.common.Constants;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

/** 
 * ClassName:SummaryLogsThread <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年4月29日 下午4:12:32 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class SummaryLogsThread implements Runnable{
    
    private final static transient Logger logger = Logger.getLog(SummaryLogsThread.class);

    private long[] totalSend;
    private boolean flag;
    private transient long regularTime = Constants.SUMMARY_THREAD_REGULAR;
    @Override
    public void run() {
        while(flag){
            long sum = 0l;
            try{
                if(logger.isDebugEnabled()){
                    for(long s:LogThreadManagerServiceImpl.totalArray){
                        sum += s;
                    }
                    logger.debug("=================total send logs:{0}=============",sum);
                }
                Thread.sleep(regularTime);
            }catch(Exception e){
                logger.error(LogModel.EVENT_APP_EXCPT, e);
            }
        }
        
    }
    
   
    public long[] getTotalSend() {
        if(this.totalSend != null){
            return totalSend.clone();
        }else{
            return null;
        }
    }
    public void setTotalSend(long[] totalSend) {
        if(totalSend != null){
            this.totalSend = totalSend.clone();
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    
}
