/** 
 * Project Name:o2p-logClient 
 * File Name:ILogSender.java 
 * Package Name:com.ailk.eaap.op2.logclient.log.service 
 * Date:2015年4月24日上午10:50:05 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.log.service;  

import java.io.Serializable;

/** 
 * ClassName:ILogSender <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年4月24日 上午10:50:05 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public interface ILogSenderService {

    void send(final Serializable object);
}
