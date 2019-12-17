/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefixsum;

/**
 *
 * @author Rewas
 */
import java.lang.Math; 
import java.util.Arrays;
import java.util.Date;
import java.util.*;


public class SerialPrefixSum {

    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) {
        // TODO code application logic here
//      int n = 16;
//        int n = 16; // arraysize

//        int InArr[] = new int[]{4,9,5,1,0,5,1,6,6,4,6,5,1,6,9,3};
        //int InArr[] = new int[]{6,4,16,10,16,14,2,8};
        
       Scanner sc = new Scanner(System.in); 
       System.out.println("enter size : ");
        int n = sc.nextInt(); // arraysize
       
         //int InArr[] = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
       
        int InArr[] = new int[n];
        System.out.println("enter elements : ");

        for(int i=0;i<n;i++){//for reading array
            InArr[i]=sc.nextInt();

        }

        int OutArr[] = new int[n];
        
         double currentTime = 0d;
        
         Date start, end;
          start = new Date();
       //Serial Version
      OutArr[0]=InArr[0];
       for(int i = 1 ; i<n; i++)
       {
           OutArr[i]+=OutArr[i-1]+InArr[i];
       }
       end = new Date();
       
       currentTime = end.getTime() - start.getTime();
       
      System.out.println(Arrays.toString(InArr));
      System.out.println(Arrays.toString(OutArr));
      System.out.println("Execution Time: " + (currentTime)
                + " seconds.");

    }
    
}
