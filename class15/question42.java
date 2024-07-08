/*************************************************************************
    > File Name: question42.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 20 13:59:30 2024
 ************************************************************************/



public class question42{

	public static int maxSum(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
			
		int max=Integer.MIN_VALUE;
		int cur=0;
		int len=arr.length;
		for(int i=0;i<len;i++){
			cur+=arr[i];
			max=Math.max(max,cur);
			cur=cur<0?0:cur;
		}

		return max;
	}

	public static int getMatrixMaxSum(int[][] matrix){
		if(matrix==null||matrix.length==0){
			return 0;
		}
		
		int row=matrix.length;
		int col=matrix[0].length;
		
		int max=Integer.MIN_VALUE;
		
		for(int i=0;i<row;i++){
			int[] accumu=new int[col];
			for(int j=i;j<row;j++){

				for(int k=0;k<col;k++){//包含第0行数据，包含第0、1行数据，包含第0、1、2行数据
					accumu[k]+=matrix[j][k];
				}
				max=Math.max(max,maxSum(accumu));
			}
		}

		return max;
	}

	//左程云实现
	public static int getMatrixMaxSum1(int[][] matrix){
		if(matrix==null||matrix.length==0||matrix[0].length==0){
			return 0;
		}

		int max=Integer.MIN_VALUE;
		int cur=0;
		int[] s=null;
		for(int i=0;i<matrix.length;i++){
			s=new int[matrix[0].length];
			for(int j=0;j<matrix.length;j++){
				cur=0;
				for(int k=0;k<s.length;k++){
					s[k]+=matrix[j][k];
					cur+=s[k];
					max=Math.max(max,cur);
					cur=cur<0?0:cur;
				}
			}
		}

		return max;
	}


	public static void main(String[] args){
		int[][] matrix={
			{	
				-5,3,6,4
			},
			{	
				-7,9,-5,3
			},
			{	
				-10,1,-200,4
			}
		};

		System.out.println(getMatrixMaxSum(matrix));
		System.out.println(getMatrixMaxSum1(matrix));

				
		
		
		System.out.println("hello world");
	}
}



