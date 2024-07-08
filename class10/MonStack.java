/*************************************************************************
    > File Name: MonStack.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Feb 29 21:32:57 2024
 ************************************************************************/

import java.util.Stack;
import java.util.LinkedList;
import java.util.Vector;

public class MonStack{ 

	//找两边比他大的数
	public static class MonotonicStack{
		private int[] nums;
		private Stack<LinkedList<Integer>> stack;
		
		public MonotonicStack(int[] nums){
			this.nums=nums;
			this.stack=new Stack<LinkedList<Integer>>();
		}

		//如果左边 or 右边 没有，则使用 Math.MIN_VALUE 代替
		//找出两边比他大的
		//单调栈，从栈底到栈顶，由大到小
		public int[][] big(){
			int len=nums.length;
			int[][] res=new int[len][3];// 本身 左大 右大
			int index=0;
			for(int i=0;i<len;i++){

				int topIndex=0;
				while(!stack.isEmpty()){
					topIndex=stack.peek().peekFirst();
					if(nums[i]<=nums[topIndex]){
						break;
					}
					
					LinkedList<Integer> list=stack.pop();
					int leftValue=Integer.MAX_VALUE;
					if(!stack.isEmpty()){
						leftValue=nums[stack.peek().peekFirst()];
					}

					while(!list.isEmpty()){
						int tmpIndex=list.pollFirst();
						res[index][0]=nums[tmpIndex];
						res[index][1]=leftValue;
						res[index][2]=nums[i];
						index++;
					}
				}

				if(stack.isEmpty()||nums[i]<nums[stack.peek().peekFirst()]){
					LinkedList<Integer> list=new LinkedList<Integer>();
					list.offerLast(i);
					stack.push(list);
				} else {
					stack.peek().offerLast(i);
				}
			}

			//进入清算阶段
			while(!stack.isEmpty()){
				
				LinkedList<Integer> list=stack.pop();
				int leftValue=Integer.MAX_VALUE;
				if(!stack.isEmpty()){
					leftValue=nums[stack.peek().peekFirst()];
				}

				while(!list.isEmpty()){
					int tmpIndex=list.pollFirst();
					res[index][0]=nums[tmpIndex];
					res[index][1]=leftValue;
					res[index][2]=Integer.MAX_VALUE;
					index++;
				}
			}
			
			return res;
		}
		
		//找出两边比他小的
		//单调栈，从栈底到栈顶，由小到大
		public int[][] small(){
			int len=nums.length;
			int[][] res=new int[len][3];
			int index=0;

			for(int i=0;i<len;i++){
				
				while(!stack.isEmpty()&&nums[stack.peek().peekFirst()]>nums[i]){
					LinkedList<Integer> list=stack.pop();
					
					int leftValue=Integer.MIN_VALUE;
					if(!stack.isEmpty()){
						leftValue=nums[stack.peek().peekFirst()];
					}
					while(!list.isEmpty()){
						int tmpIndex=list.pollFirst();
						res[index][0]=nums[tmpIndex];
						res[index][1]=leftValue;
						res[index][2]=nums[i];
						index++;
					}
				}

				if(stack.isEmpty()||nums[stack.peek().peekFirst()]<nums[i]){
					LinkedList<Integer> list=new LinkedList<Integer>();
					list.offerLast(i);
					stack.push(list);
				} else {
					stack.peek().offerLast(i);
				}
			}

			//进入清算阶段
			while(!stack.isEmpty()){
				LinkedList<Integer> list=stack.pop();

				int leftValue=Integer.MIN_VALUE;
				if(!stack.isEmpty()){
					leftValue=nums[stack.peek().peekFirst()];
				}
				
				while(!list.isEmpty()){
					int tmpIndex=list.pollFirst();
					res[index][0]=nums[tmpIndex];
					res[index][1]=leftValue;
					res[index][2]=Integer.MIN_VALUE;
					index++;
				}
			}
			
			return res;
		}
	}

	public static void printArray(int[][] nums){
		if(nums==null||nums.length<1||nums[0].length<1){
			return;
		}
		int row=nums.length;
		int col=nums[0].length;
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				System.out.print(nums[i][j]+"	");
			}
			System.out.println();
		}
		System.out.println("\n\n\n");
	}
	
	//定义：数组中累积和与最小值的乘积，假设叫做指标A
	//给定一个正数数组，请返回子数组中，指标A最大的值，子数组要连续
	public static int getMaxA(int[] nums){
		if(nums==null){
			return Integer.MIN_VALUE;
		}
		int len=nums.length;
		int res=Integer.MIN_VALUE;
		for(int i=0;i<len;i++){
			int A=nums[i];
			int sum=nums[i];
			
			//计算左边
			int left=i-1;
			while(left>=0){
				if(nums[left]<nums[i]){
					break;
				}
				sum+=nums[left];
				--left;
			}

			//计算右边
			int right=i+1;
			while(right<len){
				if(nums[right]<nums[i]){
					break;
				}
				sum+=nums[right];
				++right;
			}
			
			A*=sum;
			res=Math.max(res,A);
		}
		return res;
	}
	
	//找到两边离他最小的数，那么中间就是以他最小
	public static int getMaxA1(int[] nums){
		if(nums==null||nums.length<1){
			return Integer.MIN_VALUE;
		}

		int len=nums.length;
		int[] sum=new int[len];
		sum[0]=nums[0];
		for(int i=1;i<len;i++){
			sum[i]=sum[i-1]+nums[i];
		}

		int max=Integer.MIN_VALUE;
		Stack<Integer> stack=new Stack<Integer>();
		for(int i=0;i<len;i++){
			while(!stack.isEmpty()&&nums[stack.peek()]>=nums[i]){
				int j=stack.pop();
				max=Math.max(max,(stack.isEmpty()?sum[i-1]:(sum[i-1]-sum[stack.peek()]))*nums[j]);
			}
			stack.push(i);
		}

		while(!stack.isEmpty()){
			int j=stack.pop();
			max=Math.max(max,(stack.isEmpty()?sum[len-1]:(sum[len-1]-sum[stack.peek()]))*nums[j]);
		}
		
		return max;
	}
	
	
	public static void main(String[] args){
		
		int[] nums={1,2,3,4,45,723,23,8,90,23,2354,823,46};
		
		MonotonicStack stack1=new MonotonicStack(nums);
		int[][] res1=stack1.big();
		printArray(res1);
		
		MonotonicStack stack2=new MonotonicStack(nums);
		int[][] res2=stack2.small();
		printArray(res2);
		

		System.out.println(getMaxA(nums));
		System.out.println(getMaxA1(nums));

		System.out.println("hello world");
	}
}
