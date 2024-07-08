/*************************************************************************
    > File Name: SlideWin.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Feb 29 15:17:13 2024
 ************************************************************************/

import java.util.Queue;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.Comparator;


public class SlideWin{
	
	public static  class IntegerComparator implements Comparator<Integer>{
		public int compare(Integer o1,Integer o2){
			return o2-o1;
		}
	}
	
	//自己实现
	public static int[] slideWin(int[] nums,int winSize){
		if(nums==null||nums.length<winSize){
			return null;
		}
		
		int len=nums.length;
		int size=len-winSize+1;
		int[] res=new int[size];

		//双端队列
		Deque<Integer> deque=new ArrayDeque<Integer>();
		int l=0;
		int r=winSize-1;
		for(int i=0;i<winSize;i++){
			while(!deque.isEmpty()){
				int back=deque.peekLast();
				if(nums[back]>nums[i]){
					break;
				}
				deque.pollLast();
			}
			deque.offerLast(i);
		}
		
		int index=0;
		for(l=0,r=winSize;r<len;l++,r++){
			int getIndex=deque.peekFirst();
			res[index++]=nums[getIndex];
			if(l==getIndex){
				deque.pollFirst();
			}

			//添加r
			while(!deque.isEmpty()){
				int back=deque.peekLast();
				if(nums[back]>nums[r]){
					break;
				}
				deque.pollLast();
			}
			deque.offerLast(r);
		}
		res[index]=nums[deque.peekFirst()];
		return res;	
	}

	//左程云实现
	public static class WindowMax{
		private int L;
		private int R;
		private int[] arr; //  arr[   (L,R)   ]，我认为应该是arr[   (L,R)   ]
		private LinkedList<Integer> qmax;

		public WindowMax(int[] a){
			arr=a;
			L=-1;
			R=0;
			qmax=new LinkedList<Integer>();
		}
		
		public void addNumFromRight(){
			if(R==arr.length){
				return;
			}
	
			while(!qmax.isEmpty()&& arr[qmax.peekLast()]<=arr[R]){
				qmax.pollLast();
			}
			qmax.offerLast(arr[R]);
			R+=1;
		}

		//arr[L,R)，我认为应该是arr(L,R)
		public void removeNumFromLeft(){
			if(L>=R-1){
				return;
			}
			
			L++;
			if(qmax.peekFirst()==L){
				qmax.pollFirst();
			}
		}

		public Integer getMax(){
			if(!qmax.isEmpty()){
				return arr[qmax.peekFirst()];
			}
			return null;
		}
	}

	public static int[] getMaxWindow(int[] arr,int w){
		if(arr==null||w<1||arr.length<w){
			return null;
		}
		
		LinkedList<Integer> qmax=new LinkedList<Integer>();
		int[] res=new int[arr.length-w+1];
		int index=0;
		for(int i=0;i<arr.length;i++){
			while(!qmax.isEmpty()&&arr[qmax.peekLast()]<arr[i]){
				qmax.pollLast();
			}
			qmax.offerLast(i);
			if(qmax.peekFirst()==i-w){//i-w  过期的下标
				qmax.pollFirst();
			}
			if(i>=w-1){// 窗口形成了
				res[index++]=arr[qmax.peekFirst()];
			}
		}
		return res;
	}

	public static void printArray(int[] nums){
		if(nums==null){
			return;
		}
		int len=nums.length;
		for(int i=0;i<len;i++){
			System.out.print(nums[i]+"	");
		}
		System.out.println();
	}

	public static void main(String[] args){
		
		//滑动窗口
		int[] nums={4,3,5,4,3,3,6,7};
		int[] res=slideWin(nums,3);
		printArray(res);

		//左程云实现
		res=getMaxWindow(nums,3);
		printArray(res);


		PriorityQueue<Integer> priorityQueue=new PriorityQueue<Integer>(new IntegerComparator());
		priorityQueue.offer(10);
		priorityQueue.offer(23);
		priorityQueue.offer(12);
		priorityQueue.offer(2);
		priorityQueue.offer(1);
		priorityQueue.offer(12);
		while(!priorityQueue.isEmpty()){
			/*
			int tmp=priorityQueue.peek();
			System.out.print(tmp+"	");
			priorityQueue.remove(tmp);
			*/
			
			System.out.print(priorityQueue.poll()+"	");
		}
		System.out.println("\n\n");

		//栈
		Stack<Integer> stack=new Stack<Integer>();
		stack.push(12);
		stack.push(12);
		stack.push(2);
		stack.push(23);
		while(!stack.isEmpty()){
			System.out.print(stack.pop()+"	");
		}
		System.out.println("\n\n");
		
		//队列：单端队列
		Queue<Integer> queue=new LinkedList<Integer>();
		queue.offer(12);
		queue.offer(23);
		queue.offer(1);
		while(!queue.isEmpty()){
			System.out.print(queue.poll()+"	");
		}
		System.out.println("\n\n");
		
		//双端队列
		Deque<Integer> deque=new LinkedList<Integer>();
		deque.offerFirst(12);
		deque.offerLast(1);
		deque.offerFirst(35);
		deque.offerLast(789);
		while(!deque.isEmpty()){
			System.out.print(deque.pollFirst()+"	"+deque.pollLast()+"	");
		}
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}
