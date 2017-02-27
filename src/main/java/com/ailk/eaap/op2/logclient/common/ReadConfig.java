/** 
 * Project Name:o2p-logClient 
 * File Name:ReadConfig.java 
 * Package Name:com.ailk.eaap.op2.logclient.common 
 * Date:2015年3月20日下午3:35:59 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.common;  

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.ailk.eaap.o2p.common.spring.config.ZKCfgCacheHolder;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

/** 
 * ClassName:ReadConfig <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年3月20日 下午3:35:59 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @modify   wuzhy 
 * @see       
 */
public final class ReadConfig {
    
    
    public static String getConfig(String key,String defaultValue){
        String value =(String) ZKCfgCacheHolder.PROP_ITEMS.get(key);
        
        if(value==null||"".equals(value))
        {
            return defaultValue.trim();
        }else{
                    
            return value.trim();
        }
                                        
    }
    private ReadConfig(){
        
    }
}
