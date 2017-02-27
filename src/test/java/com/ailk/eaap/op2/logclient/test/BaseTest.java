package com.ailk.eaap.op2.logclient.test;

import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest extends TestCase{

	ApplicationContext app = null;
	public  BaseTest(){
		app = new ClassPathXmlApplicationContext(new String[]{"eaap-op2-logclient-Spring.xml"});
	}
	public Object getBean(String bean){
		return app.getBean(bean);
	}
}
