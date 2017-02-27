/** 
 * Project Name:o2p-logClient 
 * File Name:Test2.java 
 * Package Name:com.ailk.eaap.op2.logclient.test 
 * Date:2015年12月2日上午11:49:20 
 * Copyright (c) 2015, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.eaap.op2.logclient.test;  

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * ClassName:Test2 <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2015年12月2日 上午11:49:20 <br/> 
 * @author   daimq 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class Test2 {

    
   public static void main(String[] args) {
       int threadNum = 2;
       long[] arr = new long[threadNum];
       ExecutorService es= Executors.newFixedThreadPool(threadNum);
       ExecutorService es2 = Executors.newFixedThreadPool(threadNum);
       ThreadPut[] tps = new ThreadPut[threadNum];
       ThreadGet[] gets = new ThreadGet[threadNum];
       for(int i=0;i<threadNum;i++){
           tps[i] = new ThreadPut();
           tps[i].setSum(arr);
           es.submit(tps[i]);
           Test2 t = new Test2();
           gets[i] =  t.new ThreadGet();
           gets[i].setArr(arr);
           es2.submit(gets[i]);
       }
       
}
    
    static class ThreadPut implements Runnable{
        private long[] sum;
        @Override
        public void run() {
            for(int i=0;i<sum.length;i++){
                sum[i]=5l;
            }
        }
        public long[] getSum() {
            return sum;
        }
        public void setSum(long[] sum) {
            this.sum = sum;
        }
        
        
        
    }
    
     class ThreadGet implements Runnable{

        private long[] arr;
        long sum = 0;
        @Override
        public void run() {
            for(int i=0;i<arr.length;i++){
                sum += arr[i];
            }
            System.out.println("sum is :"+sum);
        }
        public long[] getArr() {
            return arr;
        }
        public void setArr(long[] arr) {
            this.arr = arr;
        }
        
        
        
    }
}
