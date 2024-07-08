/*************************************************************************
    > File Name: question23.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 13 14:51:30 2024
 ************************************************************************/

import java.util.Stack;
import java.util.Queue;
import java.lang.Comparable;

public class question23{
	
	public static class SpecialStack<T extends Comparable<T>>{
		private Stack<T> dataStack;
		private Stack<T> minStack;

		public SpecialStack(){
			dataStack=new Stack<>();
			minStack=new Stack<>();
		}

		public T pop(){
			T res=null;
			if(!dataStack.isEmpty()){
				res=dataStack.pop();
				minStack.pop();
			}
			return res;
		}

		public void push(T t){
			dataStack.push(t);
			T peek=minStack.isEmpty()?null:minStack.peek();
			if(peek!=null&&peek.compareTo(t)<0){
				t=peek;
			}
			minStack.push(t);
		}

		public T getMin(){
			return minStack.isEmpty()?null:minStack.peek();
		}

		public boolean isEmpty(){
			return dataStack.isEmpty();
		}
	}


	public static void main(String[] args){
		int[] arr={
			3,4,2,6
		};

		SpecialStack<Integer> specialStack=new SpecialStack<>();
		for(int x:arr){
			specialStack.push(x);
			System.out.println(specialStack.getMin()+"	"+specialStack.isEmpty());
		}
		System.out.println("\n\n\n");
		
		
		System.out.println("hello world");
	}
}
