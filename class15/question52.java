/*************************************************************************
    > File Name: question52.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 23 21:24:27 2024
 ************************************************************************/

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Stack;

public class question52{

	public static int maxLength(String s){
		if(s==null||s.equals("")){
			return 0;
		}

		char[] str=s.toCharArray();
		int len=str.length;
		
		int max=1;
		HashSet<Character> hashSet=new HashSet<>();
		hashSet.add(str[0]);
		int left=0;
		int right=1;
		
		while(right<len){
			if(!hashSet.contains(str[right])){
				hashSet.add(str[right]);
				max=Math.max(max,right-left+1);
				++right;
			} else {
				while(left<right&&str[left]!=str[right]){
					hashSet.remove(str[left]);
					++left;
				}
				hashSet.remove(str[left]);
				++left;
			}
		}

		return max;
	}

	//动态规划
	public static int maxLengthDP(String s){
		if(s==null||s.equals("")){
			return 0;
		}

		char[] str=s.toCharArray();
		int len=str.length;

		//妙
		int[] map=new int[256];//map代替了哈希表，假设字符的码是0~255
		for(int i=0;i<256;i++){
			map[i]=-1;
		}

		int max=0;
		int pre=-1;
		int cur=0;

		for(int i=0;i<len;i++){
			//pre：i-1位置，向左推到哪个位置停的
			//map[str[i]]：得到与str[i]字符，第一个相等的位置
			pre=Math.max(pre,map[str[i]]);
			cur=i-pre;
			max=Math.max(max,cur);
			map[str[i]]=i;
		}

		return max;
	}




	public static void main(String[] args){
		String s="abcabcbb";
		System.out.println(maxLength(s));
		System.out.println(maxLengthDP(s));
		System.out.println("\n\n\n");

		s="bbbbb";
		System.out.println(maxLength(s));
		System.out.println(maxLengthDP(s));
		System.out.println("\n\n\n");
		
		s="pwwkew";
		System.out.println(maxLength(s));
		System.out.println(maxLengthDP(s));
		System.out.println("\n\n\n");



		System.out.println("hello world");
	}
}






