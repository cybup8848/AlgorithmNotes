/*************************************************************************
    > File Name: question12.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May  8 23:14:04 2024
 ************************************************************************/

import java.util.Stack;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class question12{

	//对栈里的数据进行排序，最多使用一个额外的栈存放临时数据
	public static void sortAsc(Stack<Integer> stack){
		Stack<Integer> secondaryStack=new Stack<>();
		while(stack.isEmpty()){
			int tmp=stack.pop();
			if(secondaryStack.isEmpty()||secondaryStack.peek()>=tmp){
				secondaryStack.push(tmp);
			} else {
				while(!secondaryStack.isEmpty()&&secondaryStack.peek()<tmp){
					stack.push(secondaryStack.pop());
				}
				secondaryStack.push(tmp);
			}
		}

		while(!secondaryStack.isEmpty()){
			stack.push(secondaryStack.pop());
		}
	}


	public static void main(String[] args){
		int[] arr={
			2,3,4,5,6
		};
		int len=arr.length;
		Stack<Integer> stack=new Stack<>();
		for(int i=0;i<len;i++){
			stack.push(arr[i]);
		}

		sortAsc(stack);
		
		while(!stack.isEmpty()){
			System.out.print(stack.pop()+"	");
		}
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}
