/*************************************************************************
    > File Name: question55.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 24 18:57:00 2024
 ************************************************************************/



public class question55{

	//第i个字符开头、长度为len的字符串有多少个
	//Z开头，长度为6，0
	// i可以认为是ASCII码，0--->a    1--->b  ......  26--->z
	public static int g(int i,int len){
		int sum=0;
		if(len==1){
			return 1;
		}
		for(int j=i+1;j<=26;j++){
			sum+=g(j,len-1);
		}
		
		return sum;
	}

	//长度为len的字符串有多少个
	public static int f(int len){
		int sum=0;
		for(int i=1;i<=26;i++){
			sum+=g(i,len);
		}
		
		return sum;
	}

	public static int kth(String s){
		char[] str=s.toCharArray();
		
		int sum=0;

		int len=str.length;

		for(int i=1;i<len;i++){
			sum+=f(i);//长度为1.....len-1的子序列个数
		}

		int first=str[0]-'a'+1;//第一个字符
		
		for(int i=1;i<first;i++){
			sum+=g(i,len);//以它前面字符开头、长度为len的个数
		}

		int pre=first;
		for(int i=1;i<len;i++){
			int cur=str[i]-'a'+1;
			for(int j=pre+1;j<cur;j++){
				sum+=g(j,len-i);
			}
			pre=cur;
		}
		 
		return sum+1;
	}



	public static void main(String[] args){
		String test="bc";
		System.out.println(kth(test));


		System.out.println("hello world");
	}
}



