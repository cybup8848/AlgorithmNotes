/*************************************************************************
    > File Name: question20.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jun 21 10:56:13 2024
 ************************************************************************/





public class question20{

	//尝试写法
	public static int removeScheme1(String s){
		if(s==null||s.length()<2){
			return 0;
		}

		char[] str=s.toCharArray();
		return process1(str);
	}

	public static int process1(char[] str){
		int len=str.length;
		int[][] dp=new int[len][len];

		dp[len-1][len-1]=1;
		for(int i=len-2;i>=0;i--){
			dp[i][i]=1;
			dp[i][i+1]=(str[i]==str[i+1]?3:2);
			for(int j=i+2;j<len;j++){
				if(str[i]==str[j]){
					dp[i][j]=dp[i+1][j-1]+1;
				}
				dp[i][j]=dp[i][j]+dp[i+1][j]+dp[i][j-1]-dp[i+1][j-1];
			}
		}

		return dp[0][len-1];
	}

	public static boolean[][] isPaString(char[] str){
		int len=str.length;
		boolean[][] dp=new boolean[len][len];
		dp[len-1][len-1]=true;
		for(int i=len-2;i>=0;i--){
			dp[i][i]=true;
			dp[i][i+1]=(str[i]==str[i+1]?true:false);
			for(int j=i+2;j<len;j++){
				dp[i][j]=false;
				if(str[i]==str[j]){
					dp[i][j]=dp[i+1][j-1];
				}
			}
			
		}

		return dp;
	}





	public static void main(String[] args){
		String s="XXY";
		System.out.println(removeScheme1(s));

		s="ABA";
		System.out.println(removeScheme1(s));
		

		System.out.println("hello world");
	}

}






