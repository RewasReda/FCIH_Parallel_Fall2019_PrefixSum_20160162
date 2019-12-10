/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefixsum;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author lenovo
 */
public class ParallelPrefixSum {
    
    static ThreadPoolExecutor executor;
 

    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
//        int n = 8;
//        int n = 10;
        int n = 16; // arraysize

//        int InArr[] = new int[]{6,4,16,10,16,14,2,8};
//        int InArr[] = new int[]{4,9,5,1,0,5,1,6,6,4};
       int InArr[] = new int[]{4,9,5,1,0,5,1,6,6,4,6,5,1,6,9,3};

       int OutArr[] = new int[n];
        
        
        int TNum = Runtime.getRuntime().availableProcessors();
         executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(TNum);
        
        int ElementNum = n/TNum;
        int PrefixArr[] = new int[TNum];
        
        int startIndex=0, endIndex=ElementNum;
        
        CountDownLatch endControler = new CountDownLatch(TNum);
        CountDownLatch endControler2 = new CountDownLatch(TNum-1);
         
         double currentTime = 0d;
        
         Date start, end;
         start = new Date();
         
    //1st Parallel Version

      System.out.println("HI1");
    for(int i = 0 ; i < TNum ; i++)
        {
            PrefixTask Task = new PrefixTask(startIndex ,endIndex ,n,i,0,InArr,OutArr,PrefixArr,endControler);
            executor.execute(Task);
            startIndex=endIndex;
  
//            Correct Result From Second Run 
//            if (i<TNum-2) {
//                    endIndex=endIndex+ElementNum; 
//            } else {
//
//                    endIndex=n;
//            }
            
            if (i<TNum-1) {
                    endIndex=endIndex+ElementNum; 
            } else {

                    endIndex=n;
            }

        }
       endControler.await();
        System.out.println("HI122"); 
        
       //serial prefixsum lel prefixarr
        for(int i = 1 ; i<TNum; i++)
        {
            PrefixArr[i]+=PrefixArr[i-1];
        }

        System.out.println("HI3");
        
        //2nd paralell prefixsum
        
        startIndex=ElementNum;
        endIndex=startIndex+ElementNum;
        for(int i = 0 ; i < TNum-1 ; i++)
        {
            PrefixTask Task = new PrefixTask(startIndex ,endIndex ,n,i,1,InArr,OutArr,PrefixArr,endControler2);
            executor.execute(Task);
//            System.out.println(i + " " + startIndex + " " + endIndex);
//            System.out.println(startIndex);
//            System.out.println(endIndex);
            startIndex=endIndex;
            
//            Correct Result From Second Run 
//            if (i+1<TNum-2) {
//                    endIndex=endIndex+ElementNum; 
//            } else {
//                    endIndex=n;
////                                                System.out.println("kkkkkkkkkkkkkkkkkkk");
//
//            }
            if (i<TNum-1) {
                    endIndex=endIndex+ElementNum; 
            } else {
                    endIndex=n;
            }
            
        }
        System.out.println("HI144");
        endControler2.await();
                System.out.println(TNum);
        System.out.println(ElementNum);

        for(int i = TNum*ElementNum ; i < n ; i++)
        {
                    System.out.println(i);

            OutArr[i]=OutArr[i-1]+InArr[i];
        }
        PrefixArr[TNum-1]=OutArr[n-1];
        
        
       end = new Date();
       
       currentTime = end.getTime() - start.getTime();
      System.out.println(Arrays.toString(InArr));
      System.out.println(Arrays.toString(OutArr));
      System.out.println(Arrays.toString(PrefixArr));
      System.out.println("Execution Time: " + (currentTime)
                + " seconds.");
      executor.shutdown();

    }
    
    
}
