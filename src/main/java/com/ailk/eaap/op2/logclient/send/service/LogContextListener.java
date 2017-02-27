package com.ailk.eaap.op2.logclient.send.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ailk.eaap.op2.common.IThreadManagerService;

public class LogContextListener implements ServletContextListener{

	private transient IThreadManagerService threadManagerService;
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(threadManagerService!=null){
			threadManagerService.stop();
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		threadManagerService = (IThreadManagerService)context.getBean("logThreadManagerService");
		threadManagerService.start();
	}

}
