/*************************************************************************
    > File Name: question15.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jun 18 19:34:42 2024
 ************************************************************************/

public class question15{


	//求两个字符串str1和str2的最长公共子序列
	public static int maxCommonSeqLength(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return 0;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		int len1=str1.length;
		int len2=str2.length;
		

		int[][] dp=new int[len1][len2];
		
		//设置初始值
		dp[0][0]=(str1[0]==str2[0]?1:0);
		for(int i=1;i<len1;i++){
			if(str1[i]==str2[0]){
				dp[i][0]=1;
			} else {
				dp[i][0]=dp[i-1][0];
			}
		}

		for(int j=1;j<len2;j++){
			if(str1[0]==str2[j]){
				dp[0][j]=1;
			} else {
				dp[0][j]=dp[0][j-1];
			}
		}

		for(int i=1;i<len1;i++){
			for(int j=1;j<len2;j++){
				dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
				if(str1[i]==str2[j]){
					dp[i][j]=Math.max(dp[i][j],dp[i-1][j-1]+1);
				}
			}
		}

		return dp[len1-1][len2-1];
	}

		
	public static void main(String[] args){
		String s1="a12b3c45def";
		String s2="kts123ZY4YY5";
		System.out.println(maxCommonSeqLength(s1,s2));



		System.out.println("hello world");
	}
}








