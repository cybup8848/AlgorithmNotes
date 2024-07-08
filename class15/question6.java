/*************************************************************************
    > File Name: question6.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May  7 19:56:50 2024
 ************************************************************************/
public class question6{
	
	//自己实现
	//n个节点，能形成多少种二叉树
	public static int binaryTreeCount(int n){
		if(n==0){
			return 1;
		}
		int[] dp=new int[n+1];
		dp[0]=1;
		dp[1]=1;
		for(int i=2;i<=n;i++){
			for(int j=0;j<=i-1;j++){
				dp[i]+=dp[j]*dp[i-1-j];
			}
		}
		return dp[n];
	}

	//左程云实现
	public static int numTrees(int n){
		if(n<2){
			return 1;
		}
		return process(n);
	}

	public static int process(int n){
		if(n<0){
			return 0;
		}
		
		if(n==0||n==1){
			return 1;
		}

		if(n==2){
			return 2;
		}

		int res=0;
		for(int i=0;i<=n-1;i++){
			int leftWays=process(i);
			int rightWays=process(n-1-i);
			res+=leftWays*rightWays;
		}
		return res;
	}

	public static int numTreesDP(int n){
		if(n<2){
			return 1;
		}

		int[] dp=new int[n+1];
		dp[0]=1;
		for(int i=1;i<n+1;i++){
			for(int j=0;j<=i-1;j++){
				dp[i]+=dp[j]*dp[i-j-1];
			}
		}
		return dp[n];
	}

	public static void main(String[] args){
		for(int i=0;i<30;i++){
			System.out.println(i+" "+binaryTreeCount(i)+"  "+numTrees(i));
		}


		System.out.println("hello world");
	}
}
