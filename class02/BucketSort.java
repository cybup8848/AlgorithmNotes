/*************************************************************************
    > File Name: BucketSort.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jan 17 20:21:10 2024
 *co***********************************************************************/
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayDeque;
public class BucketSort{
	
	public static int[] getMax(int[] arr){
		if(arr.length<2)
			return new int[0];
		int max=arr[0];
		int min=arr[0];
		int len=arr.length;
		for(int i=1;i<len;i++){
			max=(max<arr[i])?arr[i]:max;
			min=(min>arr[i])?arr[i]:min;
		}
		return new int[]{min,max};
	}

	public static void countingSort(int[] arr){
		int[] base=getMax(arr);
		if(base.length!=2)
			return;
		int min=base[0];
		int max=base[1];
		int len=max-min+1;
		int[] res=new int[len];
		
		int length=arr.length;
		for(int i=0;i<length;i++)
			res[arr[i]-min]++;
		int index=0;
		for(int i=0;i<len;i++){
			while(res[i]>0){
				arr[index++]=i+min;
				res[i]--;
			}
		}
	}

	public static int getCount(int num){
		int count=0;
		while(num>0){
			num/=10;
			count++;
		}
		return count;
	}

	//简化版本队列，平铺直叙队列
	public static class CybQueue{
		private int[] arr;
		private int size;
		private int front;
		private int len;
		private int wasted;

		public CybQueue(){
			size=10;
			arr=new int[size];
			front=0;
			len=0;
			wasted=10;
			//System.out.println("初始化完成");
		}

		public void offer(int num){
			if(front+len>=size){
				size*=2;
				int[] tmp=new int[size];
				for(int i=front;i<front+len;i++)
					tmp[i]=arr[i];
				arr=tmp;
			}
			arr[front+len]=num;
			len++;
			//System.out.println("offer");
		}

		public int poll(){
			if(len==0)//简化处理
				return -100;
			
			int res=arr[front];
			front++;
			len--;
			//最多浪费wasted(次队列中代表10)个位置
			if(front>=wasted){
				for(int i=0;i<len;i++)
					arr[i]=arr[front+i];
				front=0;
			}
			return res;
		}

		public boolean isEmpty(){
			return len==0?true:false;
		}
	}


	public static void radixSort1(int[] arr){
		int[] base=getMax(arr);
		if(base.length!=2)
			return;
		int min=base[0];
		int max=base[1];
		int cycles=getCount(max-min);
		
		/*
		//LinkedBlockingQueue<Integer>[] queues=new LinkedBlockingQueue<Integer>[10];
		//LinkedBlockingQueue tmo=new LinkedBlockingQueue();
		//Queue<Integer>[] queues=new LinkedList<?>[10];
		//for(int i=0;i<10;i++)
			//queues[i]=new LinkedList<Integer>();
		Queue<Integer> queues=new ArrayDeque[10];
		for(int i=0;i<10;i++)
			queues[i]=new ArrayDeque<Integer>();
		*/

		CybQueue[] queues=new CybQueue[10];
		for(int i=0;i<10;i++)
			queues[i]=new CybQueue();

		int len=arr.length;
		int divider=1;
		for(int i=1;i<=cycles;i++){
			for(int j=0;j<len;j++){
				queues[(arr[j]-min)/divider%10].offer(arr[j]);
			}

			int index=0;
			for(int m=0;m<10;m++){
				while(!queues[m].isEmpty()){
					arr[index++]=queues[m].poll();
				}
			}
			divider*=10;
		}
	}

	public static void radixSort(int[] arr){
		int[] base=getMax(arr);
		if(base.length!=2)
			return;
		int min=base[0];
		int max=base[1];
		int cycles=getCount(max-min);
		int divider=1;
		int len=arr.length;
		for(int i=1;i<=cycles;i++){
			int[] bucket=new int[len];
			int[] count=new int[10];
			for(int j=0;j<len;j++){
				count[(arr[j]-min)/divider%10]++;
			}

			for(int m=1;m<10;m++){
				count[m]+=count[m-1];
			}
			
			//装桶
			int tmp=0;
			for(int k=len-1;k>=0;k--){
				tmp=(arr[k]-min)/divider%10;
				bucket[count[tmp]-1]=arr[k];
				count[tmp]-=1;
			}
			
			//收集
			for(int l=0;l<len;l++)
				arr[l]=bucket[l];

			divider*=10;
		}
	}

	public static int[] generateRandomArray(int maxSize,int maxValue){
		int len=(int)((maxSize+1)*Math.random());
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=(int)((maxValue+1)*Math.random())-(int)(maxValue*Math.random());
		}
		return res;
	}

	public static void comparator(int[] arr){
		Arrays.sort(arr);
	}

	public static int[] copyArray(int[] arr){
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++)
			res[i]=arr[i];
		return res;
	}

	public static boolean isEqual(int[] arr1,int[] arr2){
		int len1=arr1.length;
		int len2=arr2.length;
		if(len1!=len2)
			return false;
		for(int i=0;i<len1;i++){
			if(arr1[i]!=arr2[i])
				return false;
		}
		return true;
	}


	public static void main(String[] args){
		int testTimes=500000;
		int maxValue=1000;
		int maxSize=1000;
		boolean flag=true;
		for(int i=0;i<testTimes;i++){
			int[] arr1=generateRandomArray(maxSize,maxValue);
			int[] arr2=copyArray(arr1);
			comparator(arr1);
			//countingSort(arr2);
			//radixSort(arr2);
			radixSort1(arr2);
			if(!isEqual(arr1,arr2)){
				flag=false;
				break;
			}
		}
		System.out.println(flag?"nice":"wrong");

		//LinkedBlockingQueue queue=new LinkedBlockingQueue();


		System.out.println("hello world");
	}
}
