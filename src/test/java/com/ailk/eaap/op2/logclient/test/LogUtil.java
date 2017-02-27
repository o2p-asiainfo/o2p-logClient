/** 
 * Project Name:o2p-logServer 
 * File Name:LogUtil.java 
 * Package Name:test.com.ailk.eaap.op2.hbase.client.put 
 * Date:2015年4月2日上午9:48:41 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.test;  

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.ailk.eaap.op2.bo.ContractInteraction;
import com.ailk.eaap.op2.bo.CtgLogs;
import com.ailk.eaap.op2.bo.EndpointInteraction;
import com.ailk.eaap.op2.bo.ExceptionLogs;
import com.ailk.eaap.op2.bo.OriLogClob;

/** 
 * ClassName:LogUtil <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年4月2日 上午9:48:41 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class LogUtil {
    private static Logger log = Logger.getLogger("logserver");
    private static String transIdDate="";
    private static String xmlClob="<ContractRoot><TcpCont><ActionCode>0</ActionCode></TcpCont></ContractRoot>";
    
    public static ContractInteraction generateContractInteraction(){
        ContractInteraction contractInteraction =new ContractInteraction();
        Timestamp tms1 =new Timestamp(System.currentTimeMillis());
        Timestamp tms2 =new Timestamp(System.currentTimeMillis());
        Timestamp tms3 =new Timestamp(System.currentTimeMillis());
        Timestamp tms4 =new Timestamp(System.currentTimeMillis());
        Timestamp tms5 =new Timestamp(System.currentTimeMillis());
        contractInteraction.setBizFuncCode("BUS90006");
        contractInteraction.setBizIntfCode("recharge.debtQuery");
        contractInteraction.setContractVersion("SVC90007"); 
        contractInteraction.setRegSerCode("recharge.debtQuery");
        contractInteraction.setRegSerVersion("recharge.debtQuery1.0");
        contractInteraction.setSrcTranId(getTransID("1000000008", "111111"));
        contractInteraction.setServiceLevel("1");
        contractInteraction.setSrcOrgCode("600101");
        contractInteraction.setDstOrgCode("609904");
        contractInteraction.setSrcSysCode("8000000004");
        contractInteraction.setDstSysCode("8000000004");
        contractInteraction.setSrcReqTime(new Timestamp(System.currentTimeMillis()));
        contractInteraction.setCenterRecReqTime(tms1);
        contractInteraction.setCenterFwd2DstTime(tms2);
        contractInteraction.setCenterRecDstTime(tms3);
        contractInteraction.setCenterFwd2SrcTime(tms4);
        contractInteraction.setSrcResponseTime(new Timestamp(System.currentTimeMillis()));
        contractInteraction.setSrcResponseTime(tms5);
        contractInteraction.setResponseType("1");   
        contractInteraction.setCreateTime(new Timestamp(System.currentTimeMillis()));
        contractInteraction.setSrcSysSign("OP2");
        contractInteraction.setCreateTime(tms5);
        return contractInteraction;
    }
    public static String getTransID(String suffix,String prefix){
        String transId = suffix+transIdDate+prefix;
        return transId;
    }
    
    public static EndpointInteraction generateEndpointInteraction(String endpointId){
             
        EndpointInteraction endpointInteraction =new EndpointInteraction();
        endpointInteraction.setServiceId(Long.valueOf("1"));
        endpointInteraction.setEndpointId(Long.valueOf(endpointId));
        endpointInteraction.setCreateDate(new Timestamp(System.currentTimeMillis()));
        endpointInteraction.setSrcTranId(getTransID("1000000008", "111111"));
        endpointInteraction.setReqOrRes("0");
        endpointInteraction.setQueueMsgId("100");
        endpointInteraction.setResQueue(Long.valueOf("10"));
        endpointInteraction.setReqQueue(Long.valueOf("20"));
        endpointInteraction.setDstTranId(getTransID("1000000012", "111111"));
        endpointInteraction.setDstOrgCode("600102");
        endpointInteraction.setDstSysCode("1000000012");
        endpointInteraction.setCenterFwd2DstTime(new Timestamp(System.currentTimeMillis()));
        endpointInteraction.setDstReplyTime(new Timestamp(System.currentTimeMillis()));
        endpointInteraction.setDescriptor("test");
        endpointInteraction.setSrcSysSign("OP2");
        return endpointInteraction;
    }
    
    public static OriLogClob generateOriLogClob(String endpointId,String reqOrRes){
        OriLogClob oriLogClob = new OriLogClob();
        oriLogClob.setEndpointInteractionId("11111");
        oriLogClob.setContractInteractionId("");
        oriLogClob.setEndPointId(Long.valueOf(endpointId));
        oriLogClob.setSrcTranId(getTransID("1000000008", "111111"));
        oriLogClob.setSrcOrgCode("600101");
        oriLogClob.setSrcSysCode("1000000008");
        oriLogClob.setDstTranId(getTransID("1000000012", "111111"));
        oriLogClob.setDstOrgCode("600102");
        oriLogClob.setDstSysCode("1000000012");
        oriLogClob.setSrcIp("123");
        oriLogClob.setReqOrRes(reqOrRes);
        oriLogClob.setMsg(xmlClob);
        oriLogClob.setCreateTime(new Timestamp(System.currentTimeMillis()));
        oriLogClob.setSrcSysSign("OP2");
        return oriLogClob;
    }
    
    public static ExceptionLogs generateExceptionLogs(){
        Timestamp tms =null;
        tms = new Timestamp(System.currentTimeMillis());    
        ExceptionLogs exceptionLogs = new ExceptionLogs();
        exceptionLogs.setExceptionSpecId("123");
        exceptionLogs.setContractInteractionId("");
        exceptionLogs.setSrcTranId(getTransID("1000000008", "111111"));
        exceptionLogs.setCreateTime(new Timestamp(System.currentTimeMillis()));
        exceptionLogs.setCreateTime(tms);
        exceptionLogs.setExceptionMessage("test exception");
        exceptionLogs.setContent("");
        exceptionLogs.setStaffNbr("123");
        exceptionLogs.setSrcSysSign("OP2");
        return exceptionLogs;
    }
    
    public static CtgLogs generateCtgLogs(){
        CtgLogs ctgLogs = new CtgLogs();
        ctgLogs.setContractInteractionId("");
        ctgLogs.setErrCode("001");
        ctgLogs.setFunName("test Function");
        ctgLogs.setCreateDate(new Timestamp(System.currentTimeMillis()));
        ctgLogs.setErrorMsg("program bug");
        ctgLogs.setStatus("A");
        ctgLogs.setSrcSysSign("OP2");
        return ctgLogs;
    }
    
}
