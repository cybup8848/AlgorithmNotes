/*************************************************************************
    > File Name: question50.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 23 09:37:12 2024
 ************************************************************************/




public class question50{

	public static int getMaxXORSum(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		int[] dp=new int[len];
		dp[0]=arr[0];
		int max=arr[0];

		for(int i=1;i<len;i++){
			dp[i]=arr[i];
			for(int j=0;j<i;j++){
				dp[i]=Math.max(dp[i],dp[i]^dp[j]);
			}
			max=Math.max(max,dp[i]);
		}

		return max;
	}


	public static void main(String[] args){
		int[] arr={
			1,2,3,4,5,6
		};
		System.out.println(getMaxXORSum(arr));




		System.out.println("hello world");
	}
}




