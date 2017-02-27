package com.ailk.eaap.op2.logclient.send.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.ailk.eaap.op2.common.IQueueIHelper;
import com.ailk.eaap.op2.logclient.common.Constants;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;
import com.ailk.eaap.op2.bo.LogMessageObject;

public class AsyncBatchLogProducer implements Runnable{

    private static final Logger log = Logger.getLog(AsyncBatchLogProducer.class);
    
    private boolean runflag = true;
    private IQueueIHelper queueIHelper;
    
    
    private ConnectionFactory connectionFactory;
    // Connection ：JMS 客户端到JMS Provider 的连接
    private transient Connection connection = null;
    // Session： 一个发送或接收消息的线程
    private transient Session session = null;
    private Destination destination;
    private transient MessageProducer producer = null;
    private long[] sum;
    private int index;
    private transient List<LogMessageObject> tempList = new ArrayList<LogMessageObject>();
    public void run()  {
        int commitNum = Constants.SEND_COMMIT_NUM;
        int count = 0;
        final long sleepTime = Constants.SEND_THREAD_SLEEP_TIME;
        init();
       LogMessageObject logObj = null;
       while(runflag){
           if(!this.init()){
               log.error("activemq init connection err..");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   log.error(LogModel.EVENT_APP_EXCPT,e);
            }
           }
           try {
               if(queueIHelper.getDeap()>0){
                   if(log.isDebugEnabled()){
                       log.debug("consume logs...:"+queueIHelper.getDeap());
                   }
                   logObj = (LogMessageObject) queueIHelper.readObj();
                   tempList.add(logObj);
                   ObjectMessage objMsg = session.createObjectMessage(logObj);
                   producer.send(destination, objMsg);
                   count ++;
                   if(count == commitNum){
                       session.commit();
                       if(log.isDebugEnabled()){
                           sum[index] += count;
                       }
                       tempList.clear();
                       count = 0 ;
                       if(log.isDebugEnabled()){
                           log.debug("send logs to ActiveMQ true");
                       }
                   }
               }else{
                   if(count>0){
                       session.commit();
                       if(log.isDebugEnabled()){
                           sum[index] += count;
                       }
                       count = 0;
                       tempList.clear();
                       if(log.isDebugEnabled()){
                           log.debug("send logs to ActiveMQ true");
                       }
                   }
                   Thread.sleep(sleepTime);
               }    
               
            } catch (JMSException e) {
                rollBack();
                closeAll();
                log.error("send logs to ActiveMQ false:"+e);
            } catch (InterruptedException e) {
                rollBack();
                //closeAll();
                log.error("send logs to ActiveMQ false:"+e);
            }catch(Exception e){
                rollBack();
                //closeAll();
                log.error("send logs to ActiveMQ false:"+e);
            }
       }
      }
    /**
     * 初始化连接
     * @author daimq  
     * @since JDK 1.6
     */
    public boolean init(){
        try {
            if (connection == null) { 
                connection = connectionFactory.createConnection();
                connection.start();
            } 
            if(session == null){
                session = connection.createSession(Boolean.TRUE,
                        Session.AUTO_ACKNOWLEDGE);
            }
            if(producer == null){
                 producer = session.createProducer(destination);
                 //设置是否持久化
                 producer.setDeliveryMode(Constants.DELIVERYMODE);
            }
            return true;
       }catch (JMSException e) {
           log.error(LogModel.EVENT_APP_EXCPT,e);
           closeAll();
           return false;
       }
    }
    /**
     * 资源回收
     * closeAll:(这里用一句话描述这个方法的作用). <br/> 
     * @author daimq  
     * @since JDK 1.6
     */
    public void closeAll(){
        if(producer != null){
            try {
                producer.close();
            } catch (JMSException e1) {
                log.error(LogModel.EVENT_APP_EXCPT,e1);
            }catch (Exception ex) {
            	log.error(LogModel.EVENT_APP_EXCPT,ex);
            }finally{
                producer = null;
            }
        }
        
        
        if (session != null) {
            try {
                session.close();
            }catch (JMSException e1) {
                log.error(LogModel.EVENT_APP_EXCPT, e1);
            }catch (Exception ex) {
            	log.error(LogModel.EVENT_APP_EXCPT,ex);
            }finally {
                session = null;
            }
        }
        if (connection != null){
            try {
                connection.close();
            }catch (JMSException e1) {
                log.error(LogModel.EVENT_APP_EXCPT, e1);
            }catch (Exception ex) {
            	log.error(LogModel.EVENT_APP_EXCPT,ex);
            } finally {
                connection = null;
            }
        }   
    }
    public void rollBack(){
        try {
            for(LogMessageObject reWriteLog:tempList){
                queueIHelper.writeObj(reWriteLog);
            }
            tempList.clear();
            session.rollback();
        } catch (JMSException e) {
            log.error("#logClient# rollBack JMSException:"+e);
        }catch (Exception ex){
        	 log.error("#logClient# rollBack Exception:"+ex);
        }
    }
    
	public boolean isRunflag() {
		return runflag;
	}
	public void setRunflag(boolean runflag) {
		this.runflag = runflag;
	}

	public IQueueIHelper getQueueIHelper() {
		return queueIHelper;
	}
	public void setQueueIHelper(IQueueIHelper queueIHelper) {
		this.queueIHelper = queueIHelper;
	}

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }


    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public Destination getDestination() {
        return destination;
    }


    public void setDestination(Destination destination) {
        this.destination = destination;
    }

   

    public long[] getSum() {
        if(this.sum != null){
            return sum.clone();
        }else{
            return null;
        }
    }

    public void setSum(final long[] sum) {
        if(sum != null){
            this.sum = Arrays.copyOf(sum, sum.length);
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
	
}
