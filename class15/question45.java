/*************************************************************************
    > File Name: question45.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 21 23:27:20 2024
 ************************************************************************/



public class question45{

	
	public static int dividedBy3Num(int l,int r){
		int cn=0;
		for(int i=l;i<=r;i++){
			if((l+i)*(i-l+1)/2%3==0){
				++cn;
			}
		}

		return cn;
	}


	public static void main(String[] args){

		int l=1;
		int r=100;
		System.out.println(dividedBy3Num(l,r));

		System.out.println("hello world");
	}
}


