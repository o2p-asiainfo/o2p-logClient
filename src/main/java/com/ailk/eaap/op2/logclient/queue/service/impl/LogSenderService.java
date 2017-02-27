/** 
 * Project Name:o2p-logClient 
 * File Name:LogSenderService.java 
 * Package Name:com.ailk.eaap.op2.logclient.queue.service.impl 
 * Date:2015年4月24日上午10:53:27 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.queue.service.impl;  

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ailk.eaap.op2.logclient.log.service.ILogSenderService;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

/** 
 * ClassName:LogSenderService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年4月24日 上午10:53:27 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class LogSenderService implements ILogSenderService{
    private JmsTemplate jmsTemplate;  
    private Destination destination;  
      
    private static final Logger LOG = Logger.getLog(LogSenderService.class);
    
    @Override
    public void send(final Serializable object) {
        try{
            jmsTemplate.send(  
                    destination,  
                    new MessageCreator(){  
                        public Message createMessage(Session session) throws JMSException {  
                            return session.createObjectMessage(object);
                        }  
                    }  
            );
            
        }catch(Exception e){
            LOG.error(LogModel.EVENT_APP_EXCPT, e);
        }
    }
    public JmsTemplate getJmsTemplate() {  
        return jmsTemplate;  
    }  
    public void setJmsTemplate(JmsTemplate jmsTemplate) {  
        this.jmsTemplate = jmsTemplate;  
    }  
    public Destination getDestination() {  
        return destination;  
    }  
    public void setDestination(Destination destination) {  
        this.destination = destination;  
    }  
}
