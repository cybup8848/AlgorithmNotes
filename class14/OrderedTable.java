/*************************************************************************
    > File Name: OrderedTable.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Mar 18 11:12:51 2024
 ************************************************************************/

import java.util.Stack;
public class OrderedTable{
	//定义：数组中累积和与最小值的乘积，假设叫做指标A
	//给定一个数组，请返回子数组中，指标A最大的值
	//
	//流程：每一个数都必须是自己所在子数组中的最小值，它可以包含哪些数据
	//单调栈：找到两边离他最近的最小值
	public static int getMin(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		int[] sum=new int[len];
		sum[0]=arr[0];
		for(int i=1;i<len;i++){
			sum[i]=arr[i]+sum[i-1];
		}

		int max=0;
		Stack<Integer> stack=new Stack<Integer>();
		stack.push(0);
		int i=1;
		while(i<len){
			int nowIndex=stack.peek();
			if(arr[nowIndex]<arr[i]){
				stack.push(i);
			} else {//stack,peek()>=arr[y]
				while(!stack.isEmpty()&&arr[nowIndex]>=arr[i]){
					nowIndex=stack.pop();
					if(!stack.isEmpty()){
						int preIndex=stack.peek();
						max=Math.max(max,arr[nowIndex]*(sum[i-1]-sum[preIndex]));
						nowIndex=stack.peek();
					} else {
						max=Math.max(max,arr[nowIndex]*sum[i-1]);
					}
					
				}
				stack.push(i);
			}	
			i++;
		}
	
		while(!stack.isEmpty()){
			int nowIndex=stack.pop();
			if(!stack.isEmpty()){
				int preIndex=stack.peek();
				max=Math.max(max,arr[nowIndex]*(sum[nowIndex]-sum[preIndex]));
			} else {
				max=Math.max(max,arr[nowIndex]*sum[nowIndex]);
			}
		}
		return max;
	}


	//



	public static void main(String[] args){
			
		int[] arr1={5,3,2,1,6,7,8,4};
		System.out.println(getMin(arr1));

		int[] arr2={3,2,6,4,1};
		System.out.println(getMin(arr2));

		System.out.println("hello world");
	}
}


