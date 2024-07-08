/*************************************************************************
    > File Name: HeapSort.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Jan 15 12:52:16 2024
 ************************************************************************/
import java.util.Arrays;
import java.util.PriorityQueue;
public class HeapSort{


	public static void swap(int[] arr,int i,int j){
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;
	}
	
	//build small heap
	public static void buildHeap(int[] arr){
		int len=arr.length;
		if(len<2)
			return;
		int p=0;
		for(int i=1;i<len;i++){
			for(int j=i;j>0;){
				p=(j-1)/2;
				if(arr[j]>=arr[p])
					break;
				swap(arr,j,p);
				j=p;
			}
		}
	}
	
	//相当于冒泡排序
	public static void heapSort1(int[] arr){
		if((arr==null)||(arr.length<2))
			return;
		int len=arr.length;
		//这个没有利用堆的性质，每次都是重新建堆，而不是在已建堆的基础上调整堆。
		for(int i=len-1;i>0;i--){
			for(int j=i;j>0;j--){
				int p=(j-1)/2;
				if(arr[j]>arr[p])
					swap(arr,j,p);
			}
			swap(arr,0,i);
		}
	}

	public static void heapSort2(int[] arr){
		//先对数组进行堆化
		int len=arr.length;
		if(len<2)
			return;
		for(int i=0;i<len;i++){
			int p=(i-1)/2;
			while(arr[i]>arr[p]){
				swap(arr,i,p);
				i=p;
				p=(i-1)/2;
			}
		}
		swap(arr,0,--len);

		while(len>1){
			int p=0;
			int left=1;
			
			//堆化
			while(left<len){
				int largest=((left+1<len)&&(arr[left+1]>arr[left]))?left+1:left;
				if(arr[largest]<=arr[p]){
					break;
				}
				swap(arr,p,largest);
				p=largest;
				left=p*2+1;
			}
			swap(arr,0,--len);
		}
		
	}


	public static void comparator(int[] arr){
		Arrays.sort(arr);
	}

	public static int[] copyArray(int[] arr){
		int len=arr.length;
		/*
		 if(len<1)
			return null;//不能这样使用，因为null(空指针)不能使用length，new int[0](空数组)可以
		 */

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

	public static int[] generateRandomArray(int maxValue,int maxSize){
		int len=(int)((maxSize+1)*Math.random());
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=(int)((maxValue+1)*Math.random())-(int)(maxValue*Math.random());
		}
		return res;
	}
	

	public static void printArray(int[] arr){
		int len=arr.length;
		for(int i=0;i<len;i++){
			System.out.println(arr[i]);
		}
	}

	public static void main(String[] args){
		int[] arr={1,2,4,6,2,45,90,24};
		buildHeap(arr);
		printArray(arr);

		boolean flag=true;
		int testTimes=500000;
		int maxValue=1000;
		int maxSize=200;
		for(int i=0;i<testTimes;i++){
			int[] arr1=generateRandomArray(maxValue,maxSize);
			int[] arr2=copyArray(arr1);
			comparator(arr1);
			//heapSort1(arr2);
			//heapSort2(arr2);
			heapSort(arr2);
			if(!isEqual(arr1,arr2)){
				flag=false;
				break;
			}
		}
		System.out.println(flag?"nice":"wrong");
		System.out.println((0-1)/2);
		System.out.println((0-2)/2);

		System.out.println("Hello World");
	}

	//左程云版本
	//某个数现在处在index位置，往上继续移动
	public static void heapInsert(int[] arr,int index){
		while(arr[index]>arr[(index-1)/2]){
			swap(arr,index,(index-1)/2);
			index=(index-1)/2;
		}
	}
	
	//从index堆化，O(n)，堆剪枝
	//某个数在index位置，能否往下移动
	public static void heapify(int[] arr,int index,int heapSize){
		int left=index*2+1;//左孩子的下标
		while(left<heapSize){//下方还有孩子的时候
			//两个孩子中，谁的值大，把下标给largest
			int largest=(left+1<heapSize)&&(arr[left+1]>arr[left])?left+1:left;
			//夫和较大  孩子之间，谁的值大，把下标给largest
			largest=arr[largest]>arr[index]?largest:index;
			if(largest==index)
				break;
			swap(arr,largest,index);
			index=largest;
			left=index*2+1;
		}
	}
	
	//堆化整个数组，大顶堆
	public static void heapifyArray(int[] arr){
		int len=arr.length;
		if(len<2)
			return;
		for(int i=len-1;i>=0;i--){
			int parent=i;
			int left=parent*2+1;
			while(left<len){
				int largest=((left+1<len)&&(arr[left+1]>arr[left]))?left+1:left;
				largest=(arr[largest]>arr[parent])?largest:parent;
				if(largest==parent)
					break;
				swap(arr,parent,largest);
				parent=largest;
				left=parent*2+1;
			}
		}
	}


	public static void heapSort(int[] arr){
		if(arr==null||arr.length<2)
			return;
		int len=arr.length;

		//数组堆化，数组整体范围都变成大根堆
		/*
		for(int i=0;i<len;i++){//O(N)
			heapInsert(arr,i);//O(logN)
		}
		*/

		heapifyArray(arr);
		swap(arr,0,len-1);

		//堆排序，调整堆
		for(int i=len-1;i>1;i--){//O(N)
			heapify(arr,0,i);//O(logN)
			swap(arr,0,i-1);//O(1)
		}

		//建成大根堆，O(NlogN)
		//堆调整，N(NlogN)
		//所以整个算法的时间复杂度：O(NlogN)
	}

	public static void sortedArrDistanceLessK(int[] arr,int k){
		//默认小根堆
		PriorityQueue<Integer> heap=new PriorityQueue<>();
		int index=0;
		int len=arr.length;
		for(;index<Math.min(len,k);index++)
			heap.add(arr[index]);
		int i=0;
		for(;index<len;index++){
			heap.add(arr[index]);
			arr[i++]=heap.poll();
		}
		while(!heap.isEmpty()){
			arr[i++]=heap.poll();
		}
	}

	public static void cyb(int[] arr,int k){
		int[] store=new int[k+1];
		int len=arr.length;
		int index=0;
		for(;index<Math.min(len,k);index++)
			store[index]=arr[index];
		if(index<len)
			store[index]=arr[index];
		heapArray(store);
		int i=0;
		arr[i++]=store[0];
		for(;index<len;index++){
			store[0]=arr[index];
			heapify(store,0,k+1);
			arr[i++]=store[0];
		}
		int heapSize=store.length;
		while(heapSize>0){
			store[0]=store[--heapSize];
			heapify(store,0,heapSize);
			arr[i++]=store[0];
		}
	}



}











