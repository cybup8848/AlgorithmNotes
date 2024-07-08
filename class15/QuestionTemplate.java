/*************************************************************************
    > File Name: questionTemplate.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 16 09:06:56 2024
 ************************************************************************/



public class QuestionTemplate{
	
	//递归
	public static int fi(int n){
		if(n<1){
			return 0;
		}

		if(n==1||n==2){
			return	1;
		}
			
		return fi(n-1)+fi(n-2);
	}

	//动态规划
	public static int fiDP(int n){
		if(n<1){
			return 0;
		}

		int[] dp=new int[n+1];
		dp[0]=0;
		dp[1]=1;
		for(int i=2;i<=n;i++){
			dp[i]=dp[i-1]+dp[i-2];
		}

		return dp[n];
	}

	//转换为求幂次方
	public static int fiPower(int n){
		if(n<1){
			return 0;
		}

		if(n==1||n==2){
			return 1;
		}

		int[][] base={
			{
				1,1
			},
			{
				1,0
			}
		};

		int[][] res=matrixPower(base,n-2);
		return res[0][0]+res[1][0];
	}

	public static int[][] matrixPower(int[][] m,int p){
		int row=m.length;
		int col=m[0].length;

		int[][] res=new int[row][col];

		for(int i=0;i<row;i++){
			res[i][i]=1;
		}

		int[][] tmp=m;
		for(;p!=0;p>>=1){
			if((p&1)!=0){
				res=multiMatrix(res,tmp);
			}
			tmp=multiMatrix(tmp,tmp);
		}
		return res;
	}

	public static int[][] multiMatrix(int[][] m1,int[][] m2){
		int m1Row=m1.length;
		int m1Col=m1[0].length;

		int m2Row=m2.length;
		int m2Col=m2[0].length;
		
		if(m1Col!=m2Row){
			return null;
		}

		int[][] res=new int[m1Row][m2Col];
		for(int i=0;i<m1Row;i++){
			for(int j=0;j<m2Col;j++){
				int sum=0;
				for(int k=0;k<m1Col;k++){
					sum+=m1[i][k]*m2[k][j];
				}
				res[i][j]=sum;
			}
		}

		return res;
	}

	




	public static void main(String[] args){
		int len=40;
		for(int i=0;i<len;i++){
			System.out.println(fi(i)+"	"+fiDP(i)+"	"+fiPower(i));
		}	
	


		System.out.println("hello world");
	}
}


