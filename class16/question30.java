/*************************************************************************
    > File Name: question30.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jul  3 20:20:56 2024
 ************************************************************************/

import java.util.Stack;
import java.util.Comparator;

public class question30{
	//单调栈的应用
	
	//单调栈，找一个数两边比他小的最近的数
	public static class Node{
		public int left;
		public int right;
		public Node(int l,int r){
			left=l;
			right=r;
		}
	}
	
	public static Node[] getTwoSmall(int[] arr){
		if(arr==null||arr.length<2){
			return null;
		}

		int len=arr.length;

		Node[] res=new Node[len];

		Stack<Integer> stack=new Stack<>();
		
		for(int i=0;i<len;i++){
			while(!stack.isEmpty()&&arr[stack.peek()]>=arr[i]){
				int index=stack.pop();
				int l=stack.isEmpty()?-1:stack.peek();
				res[index]=new Node(l,i);
			}
			stack.push(i);
		}
		
		int index=stack.pop();
		int l=-1;
		while(!stack.isEmpty()){
			l=stack.pop();
			res[index]=new Node(l,-1);
			index=l;
			l=-1;
		}
		res[index]=new Node(l,-1);
		
		return res;
	}

	
	//面积最大的子矩阵
	//给定一个矩阵由0、1构成，求矩阵中由1构成的子矩阵面积的最大值
	public static int maxAreaSubMatrix(int[][] matrix){
		if(matrix==null||matrix.length==0||matrix[0].length==0){
			return 0;
		}

		int row=matrix.length;
		int col=matrix[0].length;
		
		int maxArea=0;
		int[] height=new int[col];
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(matrix[i][j]==1)	{
					height[j]+=1;
				} else {
					height[j]=0;
				}
			}

			maxArea=Math.max(maxArea,maxRecFromBottom(height));
		}

		return maxArea;
	}

	public static int maxRecFromBottom(int[] height){
		Stack<Integer> stack=new Stack<>();
		int maxArea=0;

		int len=height.length;
		for(int i=0;i<len;i++){
			while(!stack.isEmpty()&&height[stack.peek()]>=height[i]){
				int h=stack.pop();
				int l=stack.isEmpty()?-1:stack.peek();
				maxArea=Math.max(maxArea,(i-l-1)*height[h]);
			}
			stack.push(i);
		}
		
		while(!stack.isEmpty()){
			int h=stack.pop();
			int l=stack.isEmpty()?-1:stack.peek();
			maxArea=Math.max(maxArea,(len-l-1)*height[h]);
		}

		return maxArea;
	}


	//环形山相互看见问题
	public static class HC{
		public int height;
		public int count;
		public HC(int h,int c){
			height=h;
			count=c;
		}
	}

	public static class ComparatorHC implements Comparator<HC>{
		@Override
		public int compare(HC o1,HC o2){
			return o1.height-o2.height;
		}
	}

	public static int mountainPairs(int[] arr){
		if(arr==null||arr.length<2){
			return 0;
		}

		int res=0;
		int maxLoc=0;
		int len=arr.length;
		for(int i=1;i<len;i++){
			maxLoc=arr[i]>arr[maxLoc]?i:maxLoc;
		}

		Stack<HC> stack=new Stack<>();
		stack.push(new HC(arr[maxLoc],1));

		int cur=(maxLoc+1)%len;
		while(cur!=maxLoc){
			while(!stack.isEmpty()&&stack.peek().height<arr[cur]){
				int times=stack.pop().count;
				res+=times*(times-1)/2+2*times;
			}
			
			if(!stack.isEmpty()&&stack.peek().height==arr[cur]){
				stack.peek().count+=1;
			} else {
				stack.push(new HC(arr[cur],1));
			}

			cur=(cur+1)%len;
		}

		while(!stack.isEmpty()){
			int times=stack.pop().count;
			res+=times*(times-1)/2;

			if(!stack.isEmpty()){
				res+=times;
				if(stack.size()>1){
					res+=times;
				} else {
					res+=stack.peek().count>1?times:0;
				}
			}

		}
		
		return res;
	}

	//结论：环形结构中有 i 座山峰时(i>2)。可见山峰对的数量为 2*i-3

	public static void main(String[] args){
		int[][] matrix={
			{
				1,1,1,1,0,0,0
			},
			{
				1,1,1,1,1,1,0
			},
			{
				0,0,0,0,1,1,1
			},
			{
				0,1,0,1,0,1,1
			}
		};

		System.out.println(maxAreaSubMatrix(matrix));

		int[] arr={
			3,1,2,4,5
		};
		System.out.println(mountainPairs(arr));

		System.out.println("hello world");
	}
}



