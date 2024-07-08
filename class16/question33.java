/*************************************************************************
    > File Name: question33.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jul  4 17:47:10 2024
 ************************************************************************/

public class question33{

	public static int minSubStrLen1(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return -1;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		return process1(str1,str2);
	}
	
	//滑动窗口
	public static int process1(char[] str1,char[] str2){
		int len1=str1.length;
		int len2=str2.length;

		int[] count=new int[256];
		int[] map=new int[256];
		for(int i=0;i<len2;i++){
			map[str2[i]]=1;
			++count[str2[i]];
		}
		int all=len2;
		
		int l=-1;
		int r=0;
		int min=Integer.MAX_VALUE;
		while(r<len1){
			if(all>0){
				if(map[str1[r]]==1){
					if(count[str1[r]]>0){//如果还需要str1[r]，那么all就继续减，说明还欠str1[r]
						--all;
					}
					--count[str1[r]];
				}
				if(all==0){
					min=Math.min(min,r-l);
				} else {
					++r;
				}
			} else {
				++l;
				//System.out.println(l);
				if(map[str1[l]]==1)	{
					++count[str1[l]];
					if(count[str1[l]]>0){
						++all;
					} else {
						min=Math.min(min,r-l);
					}
				}
			}
		}
		
		return min;
	}

	public static int minSubStrLen2(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return -1;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		
		return process2(str1,str2);
	}
	
	//滑动窗口
	public static int process2(char[] str1,char[] str2){
		int len1=str1.length;
		int len2=str2.length;

		int[] count=new int[256];
		int[] map=new int[256];
		for(int i=0;i<len2;i++){
			map[str2[i]]=1;
			++count[str2[i]];
		}
		int all=len2;
		
		int l=-1;
		int r=0;
		int min=Integer.MAX_VALUE;
		while(r<len1){
			if(all>0){
				if(map[str1[r]]==1){
					if(count[str1[r]]>0){//如果还需要str1[r]，那么all就继续减，说明还欠str1[r]
						--all;
					}
					--count[str1[r]];
				}
			} else {
				//System.out.println(l);
				if(map[str1[l]]==1)	{
					++count[str1[l]];
					if(count[str1[l]]>0){
						++all;
					}
				}
			}

			if(all==0){
				min=Math.min(min,r-l);
				++l;
			} else {
				++r;
			}
		}
		
		return min;
	}



	public static void main(String[] args){
		String s1="abcde";
		String s2="ac";

		System.out.println(minSubStrLen1(s1,s2));
		System.out.println(minSubStrLen2(s1,s2));

		System.out.println("hello world");
	}
}



