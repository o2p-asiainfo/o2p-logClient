package com.ailk.eaap.op2.logclient.send.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.ailk.eaap.op2.common.IQueueIHelper;
import com.ailk.eaap.op2.logclient.common.Constants;
import com.ailk.eaap.op2.logclient.log.service.ILogMessageReceiveService;
import com.ailk.eaap.op2.logclient.log.service.ILogSenderService;
import com.asiainfo.foundation.log.LogModel;
import com.ailk.eaap.op2.bo.LogMessageObject;

public class AsyncBatchLog implements Runnable{

    private static final Logger log = Logger.getLogger(AsyncBatchLog.class);
    
    private ILogMessageReceiveService logMessageSendService;
    private ILogSenderService send;
    private boolean runflag = true;
    private String address;
    private String timeout;
    private IQueueIHelper queueIHelper;
    private long[] sum;
    private int index;
  //通过对象流传输
    
  public void run()  {
        
        int currListSize = 0;
        LogMessageObject lob = null;
        List<LogMessageObject> list= new ArrayList<LogMessageObject>();
        final int limitNum = Constants.BATCH_SEND_LOGS_NUMBER;
        final long sleepTime = Constants.SEND_THREAD_SLEEP_TIME;
        final long intervalTime = Constants.LIST_INTERVAL_TIME;
        while(runflag){
            
           try{
                  if(queueIHelper.getDeap()>0){
                      lob = (LogMessageObject)queueIHelper.readObj();
                      if(lob != null){
                          //改成批量传输
                          list.add(lob);
                          if(list.size() == limitNum){
                              long t1 = 0;
                              if(log.isDebugEnabled()){
                                  t1= System.currentTimeMillis();
                              }
                              boolean flag = logMessageSendService.receiveLog(list);
                              if(log.isDebugEnabled()){
                                  if(flag){
                                      sum[index] += (long)list.size();
                                  }
                              }
                              if(log.isDebugEnabled()){
                                  long t2 = System.currentTimeMillis();
                                  log.debug("call http service using time:"+(t2-t1));
                                  log.debug("batch send number is "+ list.size());
                                  log.debug("logMessage is sended to log server,flag : "+flag);
                              }
                              list.clear();
                              currListSize = list.size();
                          }
                      }
                  }else{
                      if(currListSize == list.size() && list.size()>0){
                          long t1 = 0;
                          if(log.isDebugEnabled()){
                              t1= System.currentTimeMillis();
                          }
                          boolean flag = logMessageSendService.receiveLog(list);
                          if(log.isDebugEnabled()){
                              if(flag){
                                  sum[index] += Long.valueOf(list.size());
                              }
                          }
                          if(log.isDebugEnabled()){
                              long t2 = System.currentTimeMillis();
                              log.debug("call http service using time:"+(t2-t1));
                              log.debug("batch send number is "+ list.size());
                              log.debug("logMessage is sended to log server,flag : "+flag);
                          }
                          list.clear();
                          currListSize = list.size();
                      }
                      if(list.size()>0){
                          Thread.sleep(intervalTime);
                          currListSize = list.size();
                          continue;
                      }else{
                          Thread.sleep(sleepTime);
                      }
                  }
             
            }catch(Exception e){
                log.error("send log error",e);
                try {
                    queueIHelper.writeObj(lob);
                    list.clear();
                    Thread.sleep(60000);
                } catch (InterruptedException e1) {
                    log.error(LogModel.EVENT_APP_EXCPT, e);
                }
            }
        }
            
    }
 
    public AsyncBatchLog(String timeout,IQueueIHelper queueIHelper,String address){
        this.timeout = timeout;
        this.queueIHelper=queueIHelper;
        this.address = address;
    }
    public boolean isRunflag() {
        return runflag;
    }
    public void setRunflag(boolean runflag) {
        this.runflag = runflag;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTimeout() {
        return timeout;
    }
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
    public IQueueIHelper getQueueIHelper() {
        return queueIHelper;
    }
    public void setQueueIHelper(IQueueIHelper queueIHelper) {
        this.queueIHelper = queueIHelper;
    }
    public ILogMessageReceiveService getLogMessageSendService() {
        return logMessageSendService;
    }
    public void setLogMessageSendService(
            ILogMessageReceiveService logMessageSendService) {
        this.logMessageSendService = logMessageSendService;
    }

    public ILogSenderService getSend() {
        return send;
    }

    public void setSend(ILogSenderService send) {
        this.send = send;
    }


    public long[] getSum() {
        if(sum != null){
            return Arrays.copyOf(sum, sum.length);
        }else{
            return null;
        }
    }


    public void setSum(final long[] sum) {
        if(sum != null){
            this.sum = sum.clone();
        }
        
    }


    public int getIndex() {
        return index;
    }


    public void setIndex(int index) {
        this.index = index;
    }
    
    
}
