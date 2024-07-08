/*************************************************************************
    > File Name: question10.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jun 13 14:56:40 2024
 ************************************************************************/

import java.util.LinkedList;


public class question10{
	
	public static int powXK1(int x,int k){
		int res=1;
		for(int i=0;i<k;i++){
			res*=x;
		}
		
		return res;
	}

	public static int powXK2(int x,int k){
		int res=1;
		int base=x;
		while(k!=0){
			if((k&1)!=0){
				res*=base;
			}
			k>>=1;
			base*=base;
		}

		return res;
	}



	public static int getNum(String s){
		if(s==null||s.length()==0){
			return -1;
		}
		
		int res=0;
		int base=26;
		int pow=1;
		int len=s.length();
		for(int i=len-1;i>=0;--i){
			res+=(s.charAt(i)-'A'+1)*pow;
			pow*=base;
		}
		return res;
	}

	public static String getString(int n){
		if(n<=0){
			return null;
		}
		
		LinkedList<Integer> list=new LinkedList<>();
		
		int pow=1;
		int base=26;
		while(n>pow){
			list.addFirst(1);
			n-=pow;
			pow*=base;

		}

		pow/=base;
		
		int cn=0;
		while(n!=0){
			int num=n/pow;
			n%=pow;
			pow/=base;
			list.set(cn,1+num);
			++cn;
		}

		String s="";
		for(int x:list){
			s=s+String.valueOf((char)(x-1+'A'));
		}
		
		return s;
	}
	
	
	
	public static void main(String[] args){
		int x=3;
		int k=12;
		System.out.println(powXK1(x,k));
		System.out.println(powXK2(x,k));

		
		int testNum=2321;
		System.out.println(getString(2321));
		
		String testStr="BAEC";
		System.out.println(getNum(testStr));
		
		System.out.println(getString(getNum(testStr)));
		System.out.println("hello world");
	}
}





