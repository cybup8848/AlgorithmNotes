/*************************************************************************
    > File Name: question19_1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jun 21 09:35:15 2024
 ************************************************************************/




public class question19_1{
	
	public static int minSplitNum(String s){
		if(s==null||s.length()<2){
			return 0;
		}

		char[] str=s.toCharArray();
		boolean[][] dp=isPaSubString(str);
		return process(str,0,dp);
	}

	public static int process(char[] str,int s,boolean[][] dp){
		int len=str.length;
		if(s==len){
			return 0;
		}

		if(dp[s][len-1]){
			return 0;
		}
		
		int min=Integer.MAX_VALUE;
		for(int i=s;i<len-1;i++){
			if(dp[s][i]){
				int res=process(str,i+1,dp);
				min=Math.min(min,1+res);
			}
		}

		return min;
	}


	public static boolean[][] isPaSubString(char[] str){
		int len=str.length;
		boolean[][] dp=new boolean[len][len];
		dp[len-1][len-1]=true;
		for(int i=len-2;i>=0;--i){
			dp[i][i]=true;
			dp[i][i+1]=(str[i]==str[i+1]?true:false);
			for(int j=i+2;j<len;++j){
				dp[i][j]=false;
				if(str[i]==str[j]){
					dp[i][j]=dp[i+1][j-1];
				}
			}
		}

		return dp;
	}

	public static int minSplitNumDP(String s){
		if(s==null||s.length()<2){
			return 0;
		}

		char[] str=s.toCharArray();
		return processDP(str);
	}

	public static int processDP(char[] str){
		int len=str.length;

		boolean[][] dp=isPaSubString(str);

		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=Integer.MAX_VALUE;
		}

		res[len-1]=0;
		for(int i=len-2;i>=0;--i){
			if(dp[i][len-1]){
				res[i]=0;
				continue;
			}

			for(int j=i;j<len-1;j++){
				if(dp[i][j]){
					res[i]=Math.min(res[i],1+res[j+1]);
				}
			}
		}

		return res[0];
	}
	





	public static void main(String[] args){
		String s="avsdghdfjhf";
		System.out.println(minSplitNum(s));
		System.out.println(minSplitNumDP(s));

		System.out.println("\n\n\n");

		s="abakck";
		System.out.println(minSplitNum(s));
		System.out.println(minSplitNumDP(s));
	
		System.out.println("\n\n\n");

		s="abbakck";
		System.out.println(minSplitNum(s));
		System.out.println(minSplitNumDP(s));

		System.out.println("\n\n\n");

		s="ABA";
		System.out.println(minSplitNum(s));
		System.out.println(minSplitNumDP(s));

		System.out.println("\n\n\n");

		s="ABACBCETE";
		System.out.println(minSplitNum(s));
		System.out.println(minSplitNumDP(s));

		System.out.println("\n\n\n");



		System.out.println("hello world");
	}
}









