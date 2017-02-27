/** 
 * Project Name:o2p-logClient 
 * File Name:QueueTest.java 
 * Package Name:com.ailk.eaap.op2.logclient.test 
 * Date:2015年4月24日上午11:00:33 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.test;  

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import com.ailk.eaap.op2.logclient.log.service.ILogSenderService;
import com.ailk.eaap.op2.bo.LogMessageObject;


/** 
 * ClassName:QueueTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年4月24日 上午11:00:33 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class QueueTest {

    private ILogSenderService send;
    private String[] locations = new String[]{"eaap-op2-logclient-spring.xml"};
    private ApplicationContext context;
    private ConnectionFactory connectionFactory;
    // Connection ：JMS 客户端到JMS Provider 的连接
    private Connection connection = null; 
    private Destination destination;
    private MessageProducer producer = null;
    private Session session = null;
    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(locations);
        connectionFactory = (ConnectionFactory) this.context.getBean("ActiveMQconnection");
        destination = (Destination) this.context.getBean("logDestination");
    }
//    @Test
//    public  void sendTest(){
//        long s = System.currentTimeMillis();
//        LogMessageObject log = new LogMessageObject();
//        log.getContractInteractionList().add(LogUtil.generateContractInteraction());
//        log.getEndpointInteractionList().add(LogUtil.generateEndpointInteraction("1"));
//        log.getCtgLogsList().add(LogUtil.generateCtgLogs());
//        log.getOriLogClobList().add(LogUtil.generateOriLogClob("1", "1"));
//        log.getOriLogClobList().add(LogUtil.generateExceptionLogs());
//        ArrayList<LogMessageObject> list = new ArrayList<LogMessageObject>();
//        for(int i=0;i<10000;i++){
//            list.add(log);
//            if(list.size() == 1000){
//                send.send(list);
//                list.clear();
//            }
//        }
//        System.out.println("url:"+Constants.ACTIVEMQ_BROKERURL);
//        System.out.println("=============="+(System.currentTimeMillis()-s)+"=================");
//    }
    
    @Test
    public void  sendTest2(){
        
        try {
            if (connection == null) { 
                connection = connectionFactory.createConnection();
                connection.start();
            } 
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
       }catch (JMSException e) {
           try {
               if (session != null) {
                   session.close();
               }
           } catch (JMSException e1) {
               e1.printStackTrace();
           } finally {
               session = null;
           }
           try {
               if (connection != null){
                   connection.close();
               }
           } catch (JMSException e1) {
               e1.printStackTrace();
           } finally {
               connection = null;
           }
       }
       if(producer == null){
           try {
            producer = session.createProducer(destination);
            //设置是否持久化
            producer.setDeliveryMode(1);
        } catch (JMSException e) {
            if(producer != null){
                try {
                    producer.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }finally{
                    producer = null;
                }
            }
        }
           
       }
        try {
            LogMessageObject obj = new LogMessageObject();
            obj.setSrcSysSign("O2P");
            obj.getContractInteractionList().add(LogUtil.generateContractInteraction());
            obj.getEndpointInteractionList().add(LogUtil.generateEndpointInteraction("1"));
            obj.getEndpointInteractionList().add(LogUtil.generateEndpointInteraction("2"));
            obj.getOriLogClobList().add(LogUtil.generateOriLogClob("1", "1"));
            obj.getOriLogClobList().add(LogUtil.generateOriLogClob("1", "0"));
            obj.getOriLogClobList().add(LogUtil.generateOriLogClob("2", "1"));
            obj.getOriLogClobList().add(LogUtil.generateOriLogClob("2", "0"));
            Map map = new HashMap();
            obj.setJavaFiledMap(map);
            ObjectMessage objMsg = session.createObjectMessage(obj);
//            long s = System.currentTimeMillis();
//            producer.send(destination, objMsg);
//            session.commit();
            
//            while(true){
//                i++;
//                if(i%1000 == 0){
//                }
 //               long t = System.currentTimeMillis();
//                if(t-s<500){
//                    continue;
//                }else{
//                    break;
//                }
 //           }
            
            
            for(int i=1;i<=1000;i++){
                producer.send(destination, objMsg);
                if(i%1000 == 0){
                      session.commit();
                 }
            }
        } catch (JMSException e) {
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
       }
        
    
    
    
}
