/*************************************************************************
    > File Name: question24.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 13 16:37:42 2024
 ************************************************************************/

import java.util.Queue;
import java.util.Stack;
import java.util.Deque;
import java.util.LinkedList;
import java.lang.Comparable;

public class question24{

	//自己实现
	//使用队列结构实现栈结构
	public static class StackByQueue<T extends Comparable<T>>{
		private Queue<T> queue;

		public StackByQueue(){
			queue=new LinkedList<>();
		}

		public T pop(){
			T res=null;
			if(!queue.isEmpty()){

				int len=queue.size();
				Queue<T> tmpQueue=new LinkedList<>();
				while(len>1){
					tmpQueue.add(queue.poll());
					--len;
				}
				res=queue.poll();
				queue=tmpQueue;
			}
			return res;
		}

		public T push(T t){
			queue.add(t);
			return t;
		}

		public boolean isEmpty(){
			return queue.isEmpty();
		}

		public int size(){
			return queue.size();
		}
	}


	//使用栈结构实现队列结构
	public static class QueueByStack<T extends Comparable<T>>{
		private Stack<T> stack;

		public QueueByStack(){
			stack=new Stack<>();
		}

		public T poll(){
			T res=null;
			if(!stack.isEmpty()){
				Stack<T> tmpStack=new Stack<>();
				int len=stack.size();
				while(len>1){
					tmpStack.push(stack.pop());
					--len;
				}
				res=stack.pop();
				while(!tmpStack.isEmpty()){
					stack.push(tmpStack.pop());
				}
			}
			return res;
		}

		public T add(T t){
			stack.push(t);
			return t;
		}

		public boolean isEmpty(){
			return stack.isEmpty();
		}

		public int size(){
			return stack.size();
		}
	}

	//左程云实现
	//使用栈结构实现队列结构
	public static class TwoStackQueue<T extends Comparable<T>>{
		private Stack<T> stackPush;
		private Stack<T> stackPop;

		public TwoStackQueue(){
			stackPush=new Stack<>();
			stackPop=new Stack<>();
		}

		public void add(T pushVal){
			stackPush.push(pushVal);
			dao();
		}

		public T poll(){
			if(stackPop.empty()&&stackPush.empty()){
				throw new RuntimeException("Queue is empty");
			}
			dao();
			return stackPop.pop();
		}

		public T peek(){
			if(stackPop.isEmpty()&&stackPush.isEmpty()){
				throw new RuntimeException("Queue is empty");
			}
			dao();
			return stackPop.peek();
		}

		public boolean isEmpty(){
			return stackPop.isEmpty()&&stackPush.isEmpty();
		}

		private void dao(){
			if(stackPop.isEmpty()){
				while(!stackPush.isEmpty()){
					stackPop.push(stackPush.pop());
				}
			}
		}
	}

	public static class TwoQueueStack<T extends Comparable<T>>{
		private Queue<T> queue;
		private Queue<T> help;
		
		public TwoQueueStack(){
			queue=new LinkedList<>();
			help=new LinkedList<>();
		}


		public void push(T pushVal){
			queue.add(pushVal);
		}

		public T pop(){
			if(queue.isEmpty()){
				throw new RuntimeException("Stack is empty");
			}

			dao();
			T res=queue.poll();
			swap();
			return res;
		}

		public T peek(){
			if(queue.isEmpty()){
				throw new RuntimeException("Stack is empty");
			}
			dao();
			
			T res=queue.peek();
			help.add(queue.poll());
			swap();
			return res;
		}

		public boolean isEmpty(){
			return queue.isEmpty();
		}

		private void dao(){
			int len=queue.size();
			while(len>1){
				help.add(queue.poll());
				--len;
			}
		}

		private void swap(){
			Queue<T> tmp=queue;
			queue=help;
			help=tmp;
		}

	}



	public static void main(String[] args){
		String s="abc";
		System.out.println(s.length());
		System.out.println("\n\n\n");
		
		int[] arr={
			1,2,3,4
		};
		
		StackByQueue<Integer> stack=new StackByQueue<>();
		for(int x:arr){
			stack.push(x);
		}

		while(!stack.isEmpty()){
			System.out.print(stack.pop()+"	");
		}
		System.out.println("\n\n\n");


		QueueByStack<Integer> queue=new QueueByStack<>();
		for(int x:arr){
			queue.add(x);
		}
		while(!queue.isEmpty()){
			System.out.print(queue.poll()+"	");
		}
		System.out.println("\n\n\n");



		TwoQueueStack<Integer> stack1=new TwoQueueStack<>();
		for(int x:arr){
			stack1.push(x);
		}
		while(!stack1.isEmpty()){
			System.out.print(stack1.pop()+"	");
		}
		System.out.println("\n\n\n");

		TwoStackQueue<Integer> queue1=new TwoStackQueue<>();
		for(int x:arr){
			queue1.add(x);
		}
		while(!queue1.isEmpty()){
			System.out.print(queue1.poll()+"	");
		}
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}
