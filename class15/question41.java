/*************************************************************************
    > File Name: question41.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 20 11:24:20 2024
 ************************************************************************/




public class question41{

	//左程云实现
	public static int getHighestScore(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int highest=arr[0];
		int pre=arr[0];
		int len=arr.length;
		for(int i=1;i<len;i++){
			if(pre>=0){
				pre+=arr[i];
			} else {
				pre=arr[i];
			}

			highest=Math.max(pre,highest);
		}
		
		return highest;
	}

	
	//左程云实现
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
	
	
	//所包含的动态规划思想
	public static int maxSumDP(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		
		int[] dp=new int[len];
		dp[0]=arr[0];
		
		int max=arr[0];
		
		for(int i=1;i<len;i++){
			dp[i]=arr[i];
			if(dp[i-1]>0){
				dp[i]+=dp[i-1];
			}
			max=Math.max(max,dp[i]);
		}

		return max;
	}


	public static void main(String[] args){
		int[] arr={
			1,1,-1,-10,11,4,-6,9,20,-10,-2
		};

		System.out.println(getHighestScore(arr));
		System.out.println(maxSum(arr));
		System.out.println(maxSumDP(arr));

		System.out.println("hello world");
	}
}
