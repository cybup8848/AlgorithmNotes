/*************************************************************************
    > File Name: question17.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 19 19:14:34 2024
 ************************************************************************/

public class question17{
	

	public static int maxLengthSubSeq1(String s){
		if(s==null||s.length()==0){
			return 0;
		}

		int len=s.length();
		String newStr="";
		for(int i=0;i<len;i++){
			newStr=s.charAt(i)+newStr;
		}

		return process1(s,newStr);
	}

	public static int process1(String s1,String s2){
		char[] str1=s1.toCharArray();
		int len1=str1.length;
		char[] str2=s2.toCharArray();
		int len2=str2.length;

		int[][] dp=new int[len1][len2];
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

	public static int maxLengthSubSeq2(String s){
		if(s==null||s.length()==0){
			return 0;
		}

		int len=s.length();
		String newStr="";
		for(int i=0;i<len;i++){
			newStr=s.charAt(i)+newStr;
		}

		return process2(s,newStr);
	}

	public static int process2(String s1,String s2){
		char[] str1=s1.toCharArray();
		int len1=str1.length;
		char[] str2=s2.toCharArray();
		int len2=str2.length;

		int[] dp=new int[len2];
		dp[0]=(str1[0]==str2[0]?1:0);
		for(int j=1;j<len2;j++){
			if(str1[0]==str2[j]){
				dp[j]=1;
			} else {
				dp[j]=dp[j-1];
			}
		}
		
		for(int i=1;i<len1;i++){
			int leftDown=dp[0];
			dp[0]=(str1[i]==str2[0]?1:dp[0]);
			for(int j=1;j<len2;j++){
				int tmp=dp[j];
				dp[j]=Math.max(dp[j],dp[j-1]);
				if(str1[i]==str2[j]){
					dp[j]=Math.max(dp[j],leftDown+1);
				}
				leftDown=tmp;
			}
		}

		return dp[len2-1];
	}

	public static int maxLengthSubSeq3(String s){
		if(s==null||s.length()==0){
			return 0;
		}

		return process3(s);
	}

	public static int process3(String s){
		char[] str=s.toCharArray();
		int len=str.length;
		
		
		int[][] dp=new int[len][len];
		for(int i=0;i<len-1;i++){
			dp[i][i]=1;
			dp[i][i+1]=str[i]==str[i+1]?2:1;
		}
		dp[len-1][len-1]=1;

		for(int i=len-3;i>=0;i--){
			for(int j=i+2;j<len;j++){
				dp[i][j]=Math.max(dp[i][j-1],dp[i+1][j]);
				if(str[i]==str[j]){
					dp[i][j]=Math.max(dp[i][j],2+dp[i+1][j-1]);
				}
			}
		}
		
		return dp[0][len-1];
	}

	public static int maxLengthSubSeq4(String s){
		if(s==null||s.length()==0){
			return 0;
		}
		
		return process4(s);
	}

	public static int process4(String s){
		char[] str=s.toCharArray();
		int len=str.length;
		

		int max=1;
		int[] dp=new int[len];
		int pre=0;

		for(int i=len-2;i>=0;i--){
			dp[i]=1;
			dp[i+1]=(str[i]==str[i+1]?2:1);
			
			pre=1;
			for(int j=i+2;j<len;j++){
				int tmp=dp[j];
				dp[j]=Math.max(dp[j-1],dp[j]);
				if(str[i]==str[j]){
					dp[j]=Math.max(dp[j],2+pre);
				}
				pre=tmp;
			}
		}
		
		return dp[len-1];
	}



	
	public static void main(String[] args){
		
		String s="wetewgfyhsgauasfywetgregvdfhrujytqwrwe";
		System.out.println(maxLengthSubSeq1(s));
		System.out.println(maxLengthSubSeq2(s));
		System.out.println(maxLengthSubSeq3(s));
		System.out.println(maxLengthSubSeq4(s));
		
		System.out.println("\n\n\n");

		s="324534tsgdfhjferer";
		System.out.println(maxLengthSubSeq1(s));
		System.out.println(maxLengthSubSeq2(s));
		System.out.println(maxLengthSubSeq3(s));
		System.out.println(maxLengthSubSeq4(s));

		System.out.println("hello world");
	}
		
}





