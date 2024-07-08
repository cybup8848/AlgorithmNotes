/*************************************************************************
    > File Name: question19.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jun 20 15:15:05 2024
 ************************************************************************/



public class question19{

	public static int minSplitNum1(String s){
		if(s==null||s.length()<2){
			return 0;
		}

		return process1(s);
	}
	
	public static int process1(String s){
		char[] str=s.toCharArray();
		int len=str.length;
		
		int[][] dp=new int[len][len];

		dp[len-1][len-1]=0;
		for(int i=len-2;i>=0;--i){
			dp[i][i]=0;
			dp[i][i+1]=(str[i]==str[i+1]?0:1);
			for(int j=i+2;j<len;j++){
				dp[i][j]=Math.min(dp[i+1][j],dp[i][j-1])+1;
				if(str[i]==str[j]){
					dp[i][j]=Math.min(dp[i][j],dp[i+1][j-1]);
				}
			}
		}
		
		return dp[0][len-1];
	}

	
	public static int minSplitNum2(String s){
		if(s==null||s.length()<2){
			return 0;
		}

		return process2(s);
	}

	public static int process2(String s){
		char[] str=s.toCharArray();
		int len=str.length;

		int[] dp=new int[len];
		dp[len-1]=0;
		
		int pre=0;
		for(int i=len-2;i>=0;--i){
			dp[i]=0;
			dp[i+1]=(str[i]==str[i+1]?0:1);
			pre=0;
			for(int j=i+2;j<len;j++){
				int tmp=dp[j];
				dp[j]=Math.min(dp[j-1],dp[j])+1;
				if(str[i]==str[j]){
					dp[j]=Math.min(dp[j],pre);
				}
				pre=tmp;
			}
		}

		return dp[len-1];
	}
	
	
	public static int minSplitNum3(String s){
		if(s==null||s.length()<2){
			return 0;
		}

		char[] str=s.toCharArray();
		int len=str.length;
		
		return process3(str,0);
	}

	public static int process3(char[] str,int s){
		int len=str.length;
		
		if(s==len){
			return 0;
		}

		if(isPaString(str,s,len-1)){
			return 0;
		}

		int min=Integer.MAX_VALUE;
		for(int j=s;j<len-1;j++){
			if(isPaString(str,s,j)){
				int res=process3(str,j+1);
				if(res!=Integer.MAX_VALUE){
					min=Math.min(min,res+1);
				}
			}
		}
		
		return min;
	}
	
	public static boolean isPaString(char[] str,int i,int j){
		while(i<j){
			if(str[i]!=str[j]){
				return false;
			}
			++i;
			--j;
		}

		return true;
	}




	public static void main(String[] args){
		String s="ABA";
		System.out.println(minSplitNum1(s));
		System.out.println(minSplitNum2(s));
		System.out.println(minSplitNum3(s));

		System.out.println("\n\n\n");

		s="ACDCDCDAD";
		System.out.println(minSplitNum1(s));
		System.out.println(minSplitNum2(s));
		System.out.println(minSplitNum3(s));

		System.out.println("\n\n\n");

		s="aabaakck";
		System.out.println(minSplitNum1(s));
		System.out.println(minSplitNum2(s));
		System.out.println(minSplitNum3(s));
		

		
		System.out.println("hello world");
	}
}





