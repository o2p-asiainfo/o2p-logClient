package com.ailk.eaap.op2.logclient.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * json-lib Date转json格式注册类
 * @author daimq
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	    public static final String Default_DATE_PATTERN ="yyyyMMddHHmmssSSS";  
	    private transient DateFormat dateFormat;  
	    public DateJsonValueProcessor(String datePattern){  
	        try{  
                dateFormat  = new SimpleDateFormat(datePattern);
	        }catch(Exception e){  
	            dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);  
	        }  
	    }  
	    public DateJsonValueProcessor(DateFormat dateFormat){  
           this.dateFormat = dateFormat;
        }  
	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
	        if(null!=value){  
	            return process(value);  
	        }else{  
	            return "";  
	        }  
	    }  
	    public Object processObjectValue(String key, Object value,JsonConfig jsonConfig) {  
	        if(null!=value){  
	            return process(value);  
	        }else{  
	            return "";  
	        }  
	    }  
	    private Object process(Object value){ 
	        return dateFormat.format((Date)value);  
	    }  
}
