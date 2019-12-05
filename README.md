# FCIH_Parallel_Fall2019_PrefixSum_20160162
## Team
```bash
1-رويس رضا نعمان  20160162
```

```bash
2-اسماء احمدعبدالله  20160069
```

```bash
3-رنا اسامة عبدالعال 20160160
```

```bash
4-حسين ايمن حسن 20160134
```


# Prefix sum 


## Serial algorithm 

The serial algorithm is pretty easy we just use this formula to calculate the prefix sum array:
yi = yi − 1 + xi
yi is th element of the prefix sum array ,xi is element of the original array.
So we implemented in one class SerialPrefixSum stores the size of the array in n, input & output array. 
int n = 16; 
int InArr[] = new int[]{4,9,5,1,0,5,1,6,6,4,6,5,1,6,9,3};
int OutArr[] = new int[n];

then we have one loop that calculate this formula among the original array InArr and store the result on the OutArr but we first store the first element in the array out of the loop because it will not change:

OutArr[0]=InArr[0];
for(int i = 1 ; i<n; i++)
       {
           OutArr[i]+=OutArr[i-1]+InArr[i];
       }

At last we calculate the time before and after so we can compare it with the parallel algorithm.

currentTime = end.getTime() - start.getTime();


Parallel algorithm 

We have implemented the parallel algorithm in coarse-grained concurrent version 
Firstly, we’ve implemented it in class ParallelPrefixSum that store size of the array ,InArr ,OutArr ,PrefixArr ,Tnum (the number of the threads, This number will be the number of processors available in the system that we obtain with the availableProcessors() method of the Runtime class)  and then we will split the array elements on this Tnum and store the value on ElementNum.

int n = 16;
int InArr[] = new int[]{4,9,5,1,0,5,1,6,6,4,6,5,1,6,9,3};
int OutArr[] = new int[n];
int TNum = Runtime.getRuntime().availableProcessors();
executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(TNum);
int ElementNum = n/TNum;
int PrefixArr[] = new int[TNum];
int startIndex=0, endIndex=ElementNum;
        
Then we initialize the CountDownLatch class with the number of tasks we are going to execute in the executor. The main thread calls the await() method and every task, when it finishes its calculation, calls the getDown() method:

CountDownLatch endControler = new CountDownLatch(TNum);
CountDownLatch endControler2 = new CountDownLatch(TNum);
for(int i = 0 ; i < TNum ; i++)
        {
            PrefixTask Task = new PrefixTask(startIndex ,endIndex ,n,i,0,InArr,OutArr,PrefixArr,endControler);
            executor.execute(Task);

            startIndex=endIndex;
            
            if (i<TNum-1) {
                    endIndex=endIndex+ElementNum; 
            } else {
                    endIndex=n;
            }

        }
       endControler.await();


Then, we assign to each thread the start and end indexes of the array they have to process. For all the threads except the last one, we add the length value to the start index to calculate the end index. For the last one, the last index is the size of the array.This class use PrefixTask class that has the run() method and store: 

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

The run() method that include the serial algorithm is formula ,in addition to the prefixArr that store the last element for every thread that calculate the prefixsum  :

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
            endControler.countDown();  } }
 
by now every thread have calculated the prefix sum for every part of the array stored in OutArr and the last element of each stored in PrefixArr.
So the next step is to apply the serial function on PrefixArr then add element i to the OutArr in parallel again.

for(int i = 1 ; i<TNum; i++)
        {
            PrefixArr[i]+=PrefixArr[i-1];
        }

startIndex=ElementNum;
endIndex=startIndex+ElementNum;
for(int i = 0 ; i < TNum-1 ; i++)
        {
            PrefixTask Task = new PrefixTask(startIndex ,endIndex ,n,i,1,InArr,OutArr,PrefixArr,endControler2);
            executor.execute(Task);
            startIndex=endIndex;
            if (i<TNum-1) {
                    endIndex=endIndex+ElementNum; 
            } else {
                    endIndex=n;
            }
        }

That why we have sent the mood attribute to PrefixTask class so it can switch between the 2 moods in the run() method.


