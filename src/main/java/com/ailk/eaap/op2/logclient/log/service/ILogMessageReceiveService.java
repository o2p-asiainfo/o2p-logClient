package com.ailk.eaap.op2.logclient.log.service;

import java.util.List;

import com.ailk.eaap.op2.bo.LogMessageObject;

public interface ILogMessageReceiveService {
	
	public boolean receiveLog(LogMessageObject logMessage);
	public boolean receiveLog(String jsonLogMessage);
	public boolean receiveLog(List<LogMessageObject> logMessage);

}
