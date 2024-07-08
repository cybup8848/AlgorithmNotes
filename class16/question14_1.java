/*************************************************************************
    > File Name: question14_1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jun 18 20:26:21 2024
 ************************************************************************/



public class question14_1{

	//给定两个字符串，已知可以进行删除、替换和插入任意字符串的任意字符，
    //求最少编辑几步可以将两个字符串变得相同
    public static int minEditDistance(String s1,String s2){
		if(s1==null||s2==null||s2.length()==0||s1.length()==0){
			return 0;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		int len1=str1.length;
		int len2=str2.length;
		
		int[][] dp=new int[len1][len2];
		dp[0][0]=(str1[0]==str2[0]?0:1);
		
		for(int j=1;j<len2;j++){
			dp[0][j]=dp[0][j-1]+1;
		}

		for(int i=1;i<len1;i++){
			dp[i][0]=dp[i-1][0]+1;
		}

		for(int i=1;i<len1;i++){
			for(int j=1;j<len2;j++){
				dp[i][j]=Math.min(dp[i-1][j-1],Math.max(dp[i-1][j],dp[i][j-1]))+1;
				if(str1[i]==str2[j]){
					dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1]);
				}
			}
		}

		return dp[len1-1][len2-1];
	}

    //给你一个二进制字符春s，现需要将其转化为一个交替字符串。请你计算并返回转化所需的
	//最小字符交换次数，如果无法完成转化，返回-1
    //交替字符串：相邻字符之间不存在相等情况的字符串。例如，字符串 "010" 和 "1010"属于交替字符串，但"0100"不是
    //任意两个字符都可以进行交换，不必相邻
	public static int alternateString(String s){
		if(s==null||s.length()==0){
			return -1;
        }

		char[] str=s.toCharArray();
		int len=str.length;
		int cn0=0;
		int cn1=0;
        for(int i=0;i<len;i++){
			if(str[i]=='0'){
                ++cn0;
            } else if(str[i]=='1'){
                ++cn1;
            }
        }

        if(Math.abs(cn0-cn1)>1){
			return -1;
		}

		
		int res=Integer.MAX_VALUE;

		//如果"101010"、“1010101”
		//   cn0<=cn1
		if(cn0==len/2&&cn1==(len+1)/2){
			int diff1=0;
			for(int i=0;i<len;i++){
				if(str[i]-'0'==i%2){
					++diff1;
				}
			}
			res=Math.min(res,diff1/2);
		}
		
		//如果"010101"、"0101010"
		//  cn0>=cn1
		if(cn0==(len+1)/2&&cn1==len/2){
			int diff2=0;
			for(int i=0;i<len;i++){
				if(str[i]-'0'!=i%2){
					++diff2;
				}
			}
			res=Math.min(res,diff2/2);
		}

		return res!=Integer.MAX_VALUE?res:-1;
    }

	public static void main(String[] args){

		String s="111000";
		System.out.println(alternateString(s));

		s="010";
		System.out.println(alternateString(s));

		s="1110";
		System.out.println(alternateString(s));



		System.out.println("hello world");
	}


}


