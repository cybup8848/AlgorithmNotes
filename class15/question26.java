/*************************************************************************
    > File Name: question26.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 13 23:36:38 2024
 ************************************************************************/

import java.util.Stack;


public class question26{

	//预处理数组
	public static int water(int[] arr){
		if(arr==null||arr.length<=2){
			return 0;
		}

		int len=arr.length;
		
		//预处理结构

		//i左边最大值
		int[] left=new int[len];
		left[0]=arr[0];
		for(int i=1;i<len;i++){
			left[i]=Math.max(left[i-1],arr[i]);
		}

		//i右边最大值
		int[] right=new int[len];
		right[len-1]=arr[len-1];
		for(int i=len-2;i>=0;i--){
			right[i]=Math.max(right[i+1],arr[i]);
		}
			
		int res=0;
		for(int i=1;i<len-1;i++){
			res+=Math.min(left[i],right[i])-arr[i];
		}

		return res;
	}

	public static int water1(int[] arr){
		if(arr==null||arr.length<=2){
			return 0;
		}

		int len=arr.length;
		
		//预处理结构

		//i左边最大值
		int[] left=new int[len];
		left[0]=Integer.MIN_VALUE;
		for(int i=1;i<len;i++){
			left[i]=Math.max(left[i-1],arr[i-1]);
		}

		//i右边最大值
		int[] right=new int[len];
		right[len-1]=Integer.MIN_VALUE;
		for(int i=len-2;i>=0;i--){
			right[i]=Math.max(right[i+1],arr[i+1]);
		}
			
		int res=0;
		for(int i=1;i<len-1;i++){
			res+=Math.max(Math.min(left[i],right[i])-arr[i],0);
		}

		return res;
	}

	public static int water2(int[] arr){
		if(arr==null||arr.length<=2){
			return 0;
		}

		int len=arr.length;
		
		//预处理结构

		//i左边最大值
		int[] left=new int[len];
		left[0]=Integer.MIN_VALUE;
		for(int i=1;i<len;i++){
			left[i]=Math.max(left[i-1],arr[i-1]);
		}

		//i右边最大值
		int[] right=new int[len];
		right[len-1]=Integer.MIN_VALUE;
		for(int i=len-2;i>=0;i--){
			right[i]=Math.max(right[i+1],arr[i+1]);
		}
			
		int res=0;
		for(int i=1;i<len-1;i++){
			int min=Math.min(left[i],right[i]);
			if(min>arr[i]){
				res+=min-arr[i];
			}
		}

		return res;
	}

	//空间复杂度O(1)，时间复杂度O(N)
	public static int water3(int[] arr){
		if(arr==null||arr.length<=2){
			return 0;
		}
		
		int len=arr.length;

		int res=0;//存储的水量
		int leftMax=arr[0];
		int rightMax=arr[len-1];


		int L=1;
		int R=len-2;
		while(L<=R){
			if(leftMax<=rightMax){
				if(leftMax>arr[L]){
					res+=leftMax-arr[L];
				}
				leftMax=Math.max(leftMax,arr[L]);
				++L;
			} else {
				if(rightMax>arr[R]){
					res+=rightMax-arr[R];
				}
				rightMax=Math.max(rightMax,arr[R]);
				--R;
			}
		}

		return res;	
	}

	
	public static void main(String[] args){
		int[] arr={
			3,1,2,5,2,4
		};
		System.out.println(water(arr));
		System.out.println(water1(arr));
		System.out.println(water2(arr));
		System.out.println(water3(arr));

		int[] arr1={
			4,5,1,3,2
		};
		System.out.println(water(arr1));
		System.out.println(water1(arr1));
		System.out.println(water2(arr1));
		System.out.println(water3(arr1));



		System.out.println("hello world");
	}

}
