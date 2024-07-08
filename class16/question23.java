/*************************************************************************
    > File Name: question23.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 26 22:07:59 2024
 ************************************************************************/


public class question23{

	
	public static int method1(int n){
		if(n<=0){
			return 0;
		}

		return process1(1,n,n);
	}

	public static int process1(int index,int rest,int n){

		if(rest<=0){
			return rest==0?1:0;
		}

		if(index==n+1){
			return rest==0?1:0;
		}

		int res=0;
		for(int i=0;i*index<=rest;i++){
			res+=process1(index+1,rest-i*index,n);
		}

		return res;
	}
	
	public static int method2(int n){
		if(n<=0){
			return 0;
		}

		return process2(n);
	}

	public static int process2(int n){
		int[][] dp=new int[n+2][n+1];
		
		for(int i=1;i<=n+1;i++){
			dp[i][0]=1;
		}
		for(int i=n;i>0;i--){
			for(int j=1;j<=n;j++){
				for(int k=0;k*i<=j;k++){
					if(j-k*i>=0){
						dp[i][j]+=dp[i+1][j-k*i];
					}
				}
			}
		}
		
		return dp[1][n];
	}

	public static int method3(int n){
		if(n<=0){
			return 0;
		}

		return process3(n);
	}

	public static int process3(int n){
		int[][] dp=new int[n+2][n+2];
		for(int i=0;i<=n;i++){
			dp[i][0]=1;
		}

		for(int i=n;i>0;i--){
			for(int j=1;j<=n;j++){
				dp[i][j]=dp[i+1][j];
				if(j-i>=0){
					dp[i][j]+=dp[i][j-i];
				}
			}
		}

		return dp[1][n];
	}


	public static int method4(int n){
		if(n<=0){
			return 0;
		}

		return process4(n);
	}

	public static int process4(int n){
		int[] dp=new int[n+1];
		dp[0]=1;
		for(int i=n;i>0;i--){
			for(int j=0;j<=n;j++){
				if(j-i>=0){
					dp[j]+=dp[j-i];
				}
			}
		}

		return dp[n];
	}

	
	//左程云实现
	public static int ways1(int n){
		if(n<1){
			return 0;
		}

		return process1(1,n);
	}
	
	//pre裂开的前一个部分
	//rest还剩多少值，需要去裂开，要求裂出来的第一部分，不要比pre小
	//返回裂开的方法数
	public static int process1(int pre,int rest){
		if(rest==0){
			return 1;//之前裂开的方案，构成了1种有效方法
		}
		
		if(pre>rest){
			return 0;
		}

		int ways=0;
		for(int i=pre;i<=rest;i++){
			ways+=process1(i,rest-i);
		}
		
		return ways;
	}

	public static int ways2(int n){
		if(n<1){
			return 0;
		}

		int[][] dp=new int[n+1][n+1];
		for(int i=0;i<=n;i++){
			dp[i][0]=1;
		}

		for(int pre=n;pre>0;pre--){
			for(int rest=pre;rest<=n;rest++){
				for(int k=pre;k<=rest;k++){
					dp[pre][rest]+=dp[k][rest-k];
				}
			}
		}
		
		return dp[1][n];
	}

	public static int ways3(int n){
		if(n<=0){
			return 0;
		}
		
		int[][] dp=new int[n+1][n+1];
		for(int i=1;i<=n;i++){
			dp[i][0]=1;
			dp[i][i]=1;
		}
		
		for(int pre=n-1;pre>0;pre--){
			for(int rest=pre+1;rest<=n;rest++){
				dp[pre][rest]=dp[pre][rest-pre]+dp[pre+1][rest];
			}
		}

		return dp[1][n];
	}



	
	public static void main(String[] args){
		for(int i=0;i<20;i++){
			System.out.println(i+" ---> "+method1(i)+"	"+method2(i)+"	"+method3(i)+"	"+method4(i)+"	"+
					ways1(i)+"	"+ways2(i)+"	"+ways3(i));
		}	


		System.out.println("hello world");
	}
}





