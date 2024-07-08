/*************************************************************************
    > File Name: question30.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 16 14:18:26 2024
 ************************************************************************/



public class question30{

	public static int minSticks(int n){
		if(n<3){
			return 0;
		}

		int first=1;
		int second=2;
		int res=first+second;
		int cn=2;
		while(res<=n){
			++cn;
			first=second;
			second=res;
			res=first+second;
		}

		return n-cn;
	}


	public static void main(String[] args){
		for(int i=0;i<20;i++){

			System.out.println(minSticks(i));
		}
		
		
		
		System.out.println("hello world");
	}
}


