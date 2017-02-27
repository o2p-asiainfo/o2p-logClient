package com.ailk.eaap.op2.logclient.send.service;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import com.ailk.eaap.op2.common.IQueueIHelper;
import com.ailk.eaap.op2.common.IThreadManagerService;
import com.ailk.eaap.op2.logclient.common.Constants;
import com.ailk.eaap.op2.logclient.log.service.ILogMessageReceiveService;
import com.ailk.eaap.op2.logclient.log.service.ILogSenderService;

public class LogThreadManagerServiceImpl implements IThreadManagerService{

    private IQueueIHelper queueIHelper;
	//asyncbatchlog httpInvoker方式发送线程
	private AsyncBatchLog[] asyncBatchLogs;
	private Future[] futureHandles ;
	private ExecutorService sendMsgService ;
	private String timeout;
	
	//asynbatchlogProducer activemq方式发送线程
	private ConnectionFactory connectionFactory;
    private Destination destination; 
    private ILogSenderService logSenderService;
    private transient AsyncBatchLogProducer[] producers;
    private transient ExecutorService producerExecutorService;
	
    //汇总线程
    private transient SummaryLogsThread[] summaryLogsThreads;
    private transient ExecutorService summaryExecutorService ;
    
    protected static long[] totalArray;
    
    private transient static int threadNumber = Constants.THREAD_NUM;
    static{
        totalArray = new long[threadNumber];
        for(int i=0;i<totalArray.length;i++){
            totalArray[i] = 0l;
        }
    }
    
	public void start() {
		    //activeMQ
		    producers = new AsyncBatchLogProducer[threadNumber];
		    producerExecutorService = Executors.newFixedThreadPool(threadNumber);
		    for(int i=0;i<threadNumber;i++){
		        producers[i] = new AsyncBatchLogProducer();
		        producers[i].setConnectionFactory(connectionFactory);
		        producers[i].setDestination(destination);
		        producers[i].setRunflag(true);
		        producers[i].setSum(totalArray);
		        producers[i].setQueueIHelper(queueIHelper);
		        producers[i].setIndex(i);
		        producerExecutorService.submit(producers[i]);
		        
		    }
		
		
		if(Constants.IS_START_SUMMARY_THREAD){
		    summaryExecutorService = Executors.newFixedThreadPool(1);
		    summaryLogsThreads = new SummaryLogsThread[1];
		    for(int i=0;i<summaryLogsThreads.length;i++){
		        summaryLogsThreads[i] = new SummaryLogsThread();
		        summaryLogsThreads[i].setFlag(true); 
		        //summaryLogsThreads[i].setTotalSend(totalArray);
		        summaryExecutorService.submit(summaryLogsThreads[i]);
		    }
		}
		
	}

	public void stop() {
	    if(asyncBatchLogs != null){
	        //关闭http发送线程
	        if(asyncBatchLogs.length>0){
	            for (int i = 0; i < asyncBatchLogs.length; i++) {
	                asyncBatchLogs[i].setRunflag(false);
	            }
	        }
	        sendMsgService.shutdown();
	    }
	    if(summaryLogsThreads != null){
	        //关闭汇总线程
	        if(summaryLogsThreads.length>0){
	            for (int i = 0; i < summaryLogsThreads.length; i++) {
	                summaryLogsThreads[i].setFlag(false);
	            }
	        }
	        summaryExecutorService.shutdown();
	    }
	    if(producers != null){
            //关闭汇总线程
            if(producers.length>0){
                for (int i = 0; i < producers.length; i++) {
                    producers[i].closeAll();
                    producers[i].setRunflag(false);
                }
            }
            producerExecutorService.shutdown();
        }
	}

	public AsyncBatchLog[] getAsyncBatchLogs() {
	    if(this.asyncBatchLogs != null){
	        return asyncBatchLogs.clone();
	    }else{
	        return null;
	    }
	}

	public void setAsyncBatchLogs(final AsyncBatchLog[] asyncBatchLogs) {
	    if(asyncBatchLogs != null){
	        this.asyncBatchLogs = asyncBatchLogs.clone();
	    }
	}

	public Future[] getFutureHandles() {
	    if(this.futureHandles != null){
	        return futureHandles.clone();
	    }else{
	        return null;
	    }
	}

	public void setFutureHandles(final Future[] futureHandles) {
	    if(futureHandles != null){
	        this.futureHandles = futureHandles.clone();
	    }
	}

	public ExecutorService getSendMsgService() {
		return sendMsgService;
	}

	public void setSendMsgService(ExecutorService sendMsgService) {
		this.sendMsgService = sendMsgService;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
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

    public IQueueIHelper getQueueIHelper() {
		return queueIHelper;
	}

	public void setQueueIHelper(IQueueIHelper queueIHelper) {
		this.queueIHelper = queueIHelper;
	}

    public ILogSenderService getLogSenderService() {
        return logSenderService;
    }

    public void setLogSenderService(ILogSenderService logSenderService) {
        this.logSenderService = logSenderService;
    }



}
