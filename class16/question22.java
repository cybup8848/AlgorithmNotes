/*************************************************************************
    > File Name: question22.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 26 21:27:30 2024
 ************************************************************************/



public class question22{

	
	public static int methods1(int[] arr,int aim){
		if(arr==null||aim<=0||arr.length==0){
			return 0;
		}

		return process1(arr,0,aim);
	}

	public static int process1(int[] arr,int index,int rest){

		if(rest<=0){
			return rest==0?1:0;
		}

		int len=arr.length;
		if(index==len){
			return rest==0?1:0;
		}
		
		int res=0;
		for(int i=0;i*arr[index]<=rest;i++){
			res+=process1(arr,index+1,rest-i*arr[index]);
		}

		return res;
	}
	
	
	public static int methods2(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<=0){
			return 0;
		}

		return process2(arr,aim);
	}

	public static int process2(int[] arr,int aim){
		int len=arr.length;
		
		int[][] dp=new int[len+1][aim+1];
		for(int i=0;i<=len;i++){
			dp[i][0]=1;
		}
		
		for(int i=len-1;i>=0;i--){
			for(int j=1;j<=aim;j++){
				for(int k=0;k*arr[i]<=j;k++){
					if(j-k*arr[i]>=0){
						dp[i][j]+=dp[i+1][j-k*arr[i]];
					}
				}
			}
		}
		
		return dp[0][aim];
	}

	public static int methods3(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<=0){
			return 0;
		}

		return process3(arr,aim);
	}

	public static int process3(int[] arr,int aim){
		int len=arr.length;
		int[][] dp=new int[len+1][aim+1];
		
		for(int i=0;i<=len;i++){
			dp[i][0]=1;
		}

		for(int i=len-1;i>=0;i--){
			for(int j=1;j<=aim;j++){
				dp[i][j]+=dp[i+1][j];
				if(j-arr[i]>=0){
					dp[i][j]+=dp[i][j-arr[i]];
				}
			}
		}
		
		return dp[0][aim];
	}
	
	public static int methods4(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<=0){
			return 0;
		}
		
		return process4(arr,aim);
	}

	public static int process4(int[] arr,int aim){
		int len=arr.length;
		int[] dp=new int[aim+1];
		dp[0]=1;
		for(int i=len-1;i>=0;--i){
			for(int j=1;j<=aim;j++){
				if(j-arr[i]>=0){
					dp[j]+=dp[j-arr[i]];
				}
			}
		}

		return dp[aim];
	}





	public static void main(String[] args){
		int[] arr={
			1,2,3,4,5,6,7
		};
		int aim=10;
		System.out.println(methods1(arr,aim));
		System.out.println(methods2(arr,aim));
		System.out.println(methods3(arr,aim));
		System.out.println(methods4(arr,aim));

		aim=20;
		System.out.println(methods1(arr,aim));
		System.out.println(methods2(arr,aim));
		System.out.println(methods3(arr,aim));
		System.out.println(methods4(arr,aim));

		System.out.println("hello world");
	}
}



