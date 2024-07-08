/*************************************************************************
    > File Name: question34.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 16 15:34:22 2024
 ************************************************************************/



public class question34{

	//递归
	public static int waysRecur(int w,int[] v){
		return process(w,v,0);
	}

	public static int process(int w,int[] v,int index){
		if(index==v.length){
			return w>=0?1:0;
		}

		int have=process(w-v[index],v,index+1);
		int no=process(w,v,index+1);

		return have+no;
	}

	//递归进行剪枝，如果发现背包满了，直接返回
	public static int waysRecur1(int w,int[] v){
		return process1(w,v,0);
	}

	public static int process1(int w,int[] v,int index){
		if(w<0){
			return 0;
		}
		
		//可有可无，如果有，则少一次递归
		/*
		if(w==0){
			return 1;
		}
		*/

		if(index==v.length){
			return 1;
		}
	
		int have=process1(w-v[index],v,index+1);
		int no=process1(w,v,index+1);

		return have+no;
	}

	public static int waysRecur2(int w,int[] v){
		return process2(w,v,v.length-1);
	}

	public static int process2(int w,int[] v,int index){
		if(w<0){
			return 0;
		}
		if(w==0){
			return 1;
		}
		
		if(index==-1){
			return 1;
		}

		return process2(w-v[index],v,index-1)+process2(w,v,index-1);
	}



	//动态规划
	public static int waysDP(int w,int[] v){
		int len=v.length;

		int[][] dp=new int[len+1][w+1];
		for(int i=0;i<=w;i++){
			dp[len][i]=1;
		}

		for(int i=len-1;i>=0;--i){
			for(int j=0;j<=w;++j){
				int ways=dp[i+1][j];
				if(j-v[i]>=0){
					ways+=dp[i+1][j-v[i]];
				}
				dp[i][j]=ways;
			}
		}

		return dp[0][w];
	}

	
	//有错误
	public static int waysDP1(int w,int[] v){
		if(v==null){
			return 0;
		}
		
		int len=v.length;
		int[][] dp=new int[len+1][w+1];
		for(int i=0;i<=w;++i){
			dp[0][i]=1;
		}

		for(int i=1;i<=len;i++){
			for(int j=0;j<=w;++j){
				int ways=dp[i-1][j];
				if(j-v[i-1]>=0){
					ways+=dp[i-1][j-v[i-1]];
				}
				dp[i][j]=ways;
			}	
		}

		return dp[len][w];
	}


	public static void main(String[] args){
		int[] v={
			1,2,3,4,5
		};
		int w=20;
		System.out.println(waysRecur(w,v));
		System.out.println(waysRecur1(w,v));
		System.out.println(waysRecur2(w,v));
		System.out.println(waysDP(w,v));
		System.out.println(waysDP1(w,v));
		System.out.println("\n\n\n");


		
		v=new int[]{
			1,2,4
		};
		w=10;
		System.out.println(waysRecur(w,v));
		System.out.println(waysRecur1(w,v));
		System.out.println(waysRecur2(w,v));
		System.out.println(waysDP(w,v));
		System.out.println(waysDP1(w,v));



		System.out.println("hello world");
	}
}


