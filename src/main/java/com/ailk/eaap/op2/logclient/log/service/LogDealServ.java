package com.ailk.eaap.op2.logclient.log.service;



import java.util.List;

import org.springframework.util.CollectionUtils;

import com.ailk.eaap.op2.common.IQueueIHelper;
import com.asiainfo.integration.o2p.log.common.util.Compressor;
import com.asiainfo.foundation.log.Logger;
import com.ailk.eaap.op2.bo.LogMessageObject;
import com.ailk.eaap.op2.bo.OriLogClob;

public class LogDealServ implements ILogDealServ{

	private IQueueIHelper queueIHelper;
	private String deapth;
	private  static final transient Logger logger = Logger.getLog(LogDealServ.class);
	public void logDeal(LogMessageObject log) {
		// TODO Auto-generated method stub
		try{
			int deap = queueIHelper.getDeap();
			if(deap<Integer.valueOf(deapth)){
			    //压缩报文
			    List<OriLogClob> oriLogClobList = log.getOriLogClobList();
			    if(!CollectionUtils.isEmpty(oriLogClobList)){
			        for(OriLogClob ori:oriLogClobList){
			            ori.setMsg(Compressor.gzip(ori.getMsg()));
			        }
			    }
				queueIHelper.writeObj(log);
				if(logger.isDebugEnabled()){
				    logger.debug("=======["+Thread.currentThread().getName()+"]"+" current queue length:{0} =======",queueIHelper.getDeap());
				}
				
			}else{
				logger.error("java queue deapth:"+deap);
			}
		}catch(Exception e){
			logFile(log);
		}
		
	}

	public void logFile(LogMessageObject log){
		
	}

	public IQueueIHelper getQueueIHelper() {
		return queueIHelper;
	}

	public String getDeapth() {
		return deapth;
	}

	public void setDeapth(String deapth) {
		this.deapth = deapth;
	}

	public void setQueueIHelper(IQueueIHelper queueIHelper) {
		this.queueIHelper = queueIHelper;
	}

  

}
