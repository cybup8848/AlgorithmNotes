/*************************************************************************
    > File Name: question14.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jun 18 14:05:25 2024
 ************************************************************************/


public class question14{

	//自己实现：暴力尝试
	public static int maxCommonLength(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return 0;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		int len1=str1.length;
		int len2=str2.length;
		
		int res=0;
		for(int i=0;i<len1;i++){
			for(int j=0;j<len2;j++){
				int len=0;
				int m=i;
				int n=j;
				while(m<len1&&n<len2&&str1[m]==str2[n]){
					++len;
					++m;
					++n;
				}
				
				res=Math.max(res,len);
			}
		}

		return res;
	}

	//动态规划
	public static int maxCommonLengthDP(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return 0;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();

		int len1=str1.length;
		int len2=str2.length;
		int[][] dp=new int[len1][len2];
		
		int res=0;

		//初始状态
		for(int i=0;i<len1;i++){
			if(str1[i]==str2[0]){
				dp[i][0]=1;
				res=1;
			}
		}

		for(int j=0;j<len2;++j){
			if(str1[0]==str2[j]){
				dp[0][j]=1;
				res=1;
			}
		}
		
		for(int i=1;i<len1;i++){
			for(int j=1;j<len2;j++){
				if(str1[i]==str2[j]){
					dp[i][j]=dp[i-1][j-1]+1;
					res=Math.max(res,dp[i][j]);	
				}
			}
		}

		return res;
	}

	//压缩成一维表格
	public static int maxCommonLengthDP1(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return 0;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		int len1=str1.length;
		int len2=str2.length;
		
		int res=0;
		int[] dp=new int[len2];
		int preLeftUp=0;
		int tmp=0;

		for(int i=0;i<len1;i++){
			preLeftUp=dp[0];
			if(str1[i]==str2[0]){
				dp[0]=1;
				res=Math.max(res,dp[0]);
			} else {
				dp[0]=0;
			}

			for(int j=1;j<len2;j++){
				tmp=dp[j];
				if(str1[i]==str2[j]){
					dp[j]=preLeftUp+1;
					res=Math.max(res,dp[j]);
				} else {
					dp[j]=0;
				}
				preLeftUp=tmp;
			}
		}
		
		return res;
	}
	
	//压缩成几个变量
	public static int maxCommonLengthDP2(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return 0;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		int len1=str1.length;
		int len2=str2.length;
		
		int res=0;
		int pre=0;
		
		//右上角
		for(int j=0;j<len2;j++){
			pre=0;	
			int row=0;
			int col=j;
			while(row<len1&&col<len2){
				if(str1[row]==str2[col]){
					pre=pre+1;
					res=Math.max(res,pre);
				} else {
					pre=0;
				}

				++row;
				++col;
			}
		}
		
		//左下角
		for(int i=1;i<len1;i++){
			pre=0;
			int row=i;
			int col=0;
			while(row<len1&&col<len2){
				if(str1[row]==str2[col]){
					pre+=1;
					res=Math.max(res,pre);
				} else {
					pre=0;
				}
				++row;
				++col
			}
		}
		
		return res;
	}


	//左程云实现
	public static String lcst2(String s1,String s2){
		if(s1==null||s2==null||s1.equals("")||s2.equals("")){
			return "";
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		int row=0;//斜线开始位置的行
		int col=str2.length-1;//斜线开始位置的列
		int max=0;// t 达到的全局最大值
		int end=0;//忽略
		
		while(row<str1.length){
			int i=row;
			int j=col;
			int len=0;
			
			while(i<str1.length&&j<str2.length){
				if(str1[i]!=str2[j]){
					len=0;
				} else {
					len++;
				}

				if(len>max){
					end=i;
					max=len;
				}
				
				i++;
				j++;
			}

			if(col>0){
				--col;
			} else {
				++row;
			}
		}

		return s1.substring(end-max+1,end+1);
	}
	
	


	//给定两个字符串，已知可以进行删除、替换和插入任意字符串的任意字符，
	//求最少编辑几步可以将两个字符串变得相同
	public static int minEditDistance(String s1,String s2){
		



		return 0;
	}

	//给你一个二进制字符春s，现需要将其转化为一个交替字符串。请你计算并返回转化所需的
	//最小字符交换次数，如果无法完成转化，返回-1
	//交替字符串：相邻字符之间不存在相等情况的字符串。例如，字符串 "010" 和 "1010"属于交替字符串，但"0100"不是
	//任意两个字符都可以进行交换，不必相邻
	public static int alternateString(String s){
		if(s==null||s.length()==0){
			return -1;
		}
		int cn=0;
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='0'){
				--cn;
			} else if(s.charAt(i)=='1'){
				++cn;
			}
		}

		if(cn!=0){
			return -1;
		}


		


		return 0;
	}
	

	public static void main(String[] args){
		String s1="abcdfegergr";
		String s2="sdgsdgdcdfe";
		System.out.println(maxCommonLength(s1,s2));
		System.out.println(maxCommonLengthDP(s1,s2));
		System.out.println(maxCommonLengthDP1(s1,s2));
		System.out.println(maxCommonLengthDP2(s1,s2));
		System.out.println(lcst2(s1,s2));



		s1="aabcaaeryrec";
		s2="adfhdaadfhfgbccab";
		System.out.println(maxCommonLength(s1,s2));
		System.out.println(maxCommonLengthDP(s1,s2));
		System.out.println(maxCommonLengthDP1(s1,s2));
		System.out.println(maxCommonLengthDP2(s1,s2));
		System.out.println(lcst2(s1,s2));

		System.out.println("hello world");
	}
}













