/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefixsum;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author lenovo
 */
public class PrefixTask implements Runnable{
    
    
    int startIndex ,endIndex;
    int n,i;
    int InArr[] ;
    int OutArr[] ;
    int PrefixArr[];
    CountDownLatch endControler;
    int mood;
    
   public PrefixTask(int startIndex,int endIndex, int n,int i ,int mood,int InArr[] ,int OutArr[] ,int PrefixArr[],CountDownLatch endControler ){
      
      
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.n = n;
        this.i = i;
        this.mood = mood;
        this.InArr = InArr;
        this.OutArr = OutArr;
        this.PrefixArr = PrefixArr;
        this.endControler = endControler;
       
       
   }
    
    
    
     @Override
   
    public void run() {
        if (mood == 0){
            if(startIndex < n)
            {
                OutArr[startIndex]=InArr[startIndex];
            }
            for(int j = startIndex+1 ; j < endIndex ; j++)
            {
                OutArr[j]=OutArr[j-1]+InArr[j];
                
            }

             PrefixArr[i]+=OutArr[endIndex-1];
             endControler.countDown();
        }else if (mood == 1){
            for(int j = startIndex ; j < endIndex ; j++)
            {
                OutArr[j]+=PrefixArr[i];   
            }
            endControler.countDown();
        }
         
    }
    
    
    
    
   
    
}
