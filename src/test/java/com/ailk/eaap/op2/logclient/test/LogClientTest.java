package com.ailk.eaap.op2.logclient.test;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.eaap.op2.common.IThreadManagerService;
import com.ailk.eaap.op2.logclient.log.service.ILogDealServ;

public class LogClientTest{

	private static Logger log = Logger.getLogger(LogClientTest.class);
	private ApplicationContext ctx ;
	private String[] locations = {"eaap-op2-logclient-spring.xml"};
	private ILogDealServ logDeal;

	private IThreadManagerService logThreadManagerService;
	
	@Before
	public void init(){
	    ctx = new ClassPathXmlApplicationContext(locations);
	    logDeal = (ILogDealServ) ctx.getBean("logDealServ");
	    logThreadManagerService = (IThreadManagerService) ctx.getBean("logThreadManagerService");
	}
	
	
	
	@Test
	public  void testLogSendSwitch(){
	    logThreadManagerService.start();
	}
}
