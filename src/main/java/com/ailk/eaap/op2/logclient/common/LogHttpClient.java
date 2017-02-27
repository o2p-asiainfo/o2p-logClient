package com.ailk.eaap.op2.logclient.common;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

public final class LogHttpClient {

	private static final Logger LOG = Logger.getLog(LogHttpClient.class);
	public static String sendObjFromServer(String url, int timeout,
			Serializable inputObj) throws IOException {
		//httpClient = new HttpClient();

		PostMethod post = new PostMethod(url);
		HttpClient logHttpClient = new HttpClient();

		post.setRequestHeader("Content-Type", "application/octet-stream");

		java.io.ByteArrayOutputStream bOut = new java.io.ByteArrayOutputStream();
		java.io.ByteArrayInputStream bInput = null;
		java.io.ObjectOutputStream out = null;
		String returnObj = null;
		try {
			logHttpClient.getParams().setConnectionManagerTimeout(timeout * 1000);
			logHttpClient.getParams().setSoTimeout(timeout * 1000);
			logHttpClient.setConnectionTimeout(timeout*1000);
			logHttpClient.setTimeout(timeout*1000);
			logHttpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(0, false));
			
			out = new java.io.ObjectOutputStream(bOut);

			out.writeObject(inputObj);

			out.flush();
			out.close();

			out = null;

			bInput = new java.io.ByteArrayInputStream(bOut.toByteArray());

			RequestEntity re = new InputStreamRequestEntity(bInput);
			post.setRequestEntity(re);

			logHttpClient.executeMethod(post);

			java.io.InputStream in = post.getResponseBodyAsStream();
			java.io.ObjectInputStream oInput = new java.io.ObjectInputStream(in);
			returnObj = (String) oInput.readObject();
			oInput.close();
			oInput = null;
			//returnObj = StringUtil.getString( post.getResponseBodyAsStream(), EOPDomain.CHARSET_UTF8);
		} catch (Exception e) {
			LOG.error(LogModel.EVENT_APP_EXCPT, e);
		}  finally {
			try{
				if (out != null) {
					out.close();
					out = null;

				}

				if (bInput != null) {
					bInput.close();
					bInput = null;

				}
				if(bOut!=null){
					bOut.close();
					bOut = null;
				}
				if(post!=null){
					post.releaseConnection();
				}
				if (logHttpClient != null) {
					if(logHttpClient.getHttpConnectionManager()!=null){
						((SimpleHttpConnectionManager) (logHttpClient.getHttpConnectionManager())).shutdown();
					}
					
					
				}
			}catch(Exception e){
			    LOG.error(LogModel.EVENT_APP_EXCPT, e);
			}
			
			
		}

		return returnObj;

	}
	private LogHttpClient(){
	    
	}
}
