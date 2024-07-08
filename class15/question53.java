/*************************************************************************
    > File Name: question53.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 23 23:00:59 2024
 ************************************************************************/



public class question53{

	//编辑距离
	//和最长公共子串一个模型
	public static int minCost(String s1,String s2,int ic,int dc,int rc){
		if(s1==null||s2==null){
			return 0;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();

		int len1=str1.length;
		int len2=str2.length;

		int[][] dp=new int[len1+1][len2+1];
		
		for(int i=1;i<=len2;i++){
			dp[0][i]=i*ic;
		}

		for(int i=1;i<=len1;i++){
			dp[i][0]=i*dc;
		}

		for(int i=1;i<=len1;i++){
			for(int j=1;j<=len2;j++){
				dp[i][j]=dp[i-1][j]+dc;
				dp[i][j]=Math.min(dp[i][j],dp[i][j-1]+ic);
				dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1]+(str1[i-1]==str2[j-1]?0:rc));
			}
		}

		return dp[len1][len2];
	}



	//左程云实现
	public static int minCost1(String str1,String str2,int ic,int dc,int rc){
		if(str1==null||str2==null){
			return 0;
		}


		char[] chs1=str1.toCharArray();
		char[] chs2=str2.toCharArray();

		int row=chs1.length+1;
		int col=chs2.length+1;

		int[][] dp=new int[row][col];

		for(int i=1;i<row;i++){
			dp[i][0]=dc*i;
		}

		for(int j=1;j<col;j++){
			dp[0][j]=ic*j;
		}

		for(int i=1;i<row;i++){
			for(int j=1;j<col;j++){
				if(chs1[i-1]==chs2[j-1]){
					dp[i][j]=dp[i-1][j-1];
				} else {
					dp[i][j]=dp[i-1][j-1]+rc;
				}
				
				dp[i][j]=Math.min(dp[i][j],dp[i][j-1]+ic);
				dp[i][j]=Math.min(dp[i][j],dp[i-1][j]+dc);
			}
		}
		
		return dp[row-1][col-1];
	}

	public static int minCost2(String str1,String str2,int ic,int dc,int rc){
		if(str1==null||str2==null){
			return 0;
		}

		char[] chs1=str1.toCharArray();
		char[] chs2=str2.toCharArray();
		
		int row=chs1.length+1;
		int col=chs2.length+1;

		int[] arr1=new int[col];
		int[] arr2=new int[col];
		for(int j=1;j<col;j++){
			arr1[j]=j*ic;
		}
		
		for(int i=1;i<row;i++){
			arr2[0]=i*dc;
			for(int j=1;j<col;j++){
				arr2[j]=arr1[j-1]+(chs1[i-1]==chs2[j-1]?0:rc);
				arr2[j]=Math.min(arr2[j],arr2[j-1]+ic);
				arr2[j]=Math.min(arr2[j],arr1[j]+dc);
			}
			int[] tmp=arr1;
			arr1=arr2;
			arr2=tmp;
		}

		return arr1[col-1];
	}




	public static void main(String[] args){
		String s1="abcdef";
		String s2="skbcdf";
		System.out.println(minCost(s1,s2,5,4,3));
		System.out.println(minCost1(s1,s2,5,4,3));	
		System.out.println(minCost2(s1,s2,5,4,3));
		System.out.println("\n\n\n");




		System.out.println("hello world");
	}
}





