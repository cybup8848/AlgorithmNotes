/*************************************************************************
    > File Name: question31.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 15 14:01:05 2024
 ************************************************************************/



public class question31{


	 public static boolean isDivisibleBy4(int[] arr){
		if(arr==null||arr.length==1){
			return false;
		}

		int a=0;//奇数个数
		int b=0;//仅包含1个2因子的偶数个数
		int c=0;//包含4因子的偶数个数
		
		for(int x:arr){
			if(x%2!=0){
				++a;
			} else if(x%4==0){
				++c;
			} else {
				++b;
			}
		}

		if(b==0&&(a==1&&c>=a||a>1&&c>=a-1)){
			return true;
		}
		if(c>=a){
			return true;
		}
		
		return false;
	 }


	public static void main(String[] args){
		int[] arr={
			2,2,34,45,524,34
		};
		System.out.println(isDivisibleBy4(arr));



		System.out.println("hello world");
	}
}


