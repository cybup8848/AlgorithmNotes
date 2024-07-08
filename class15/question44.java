/*************************************************************************
    > File Name: question44.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 22 10:25:10 2024
 ************************************************************************/

import java.util.Set;
import java.util.HashSet;


public class question44{

	//不考虑空间复杂度:O(N)
	public static void findNotFound(int[] arr){
		if(arr==null){
			return;
		}
		
		int len=arr.length;

		HashSet<Integer> hashSet=new HashSet<>();
		for(int x:arr){
			hashSet.add(x);
		}

		for(int i=1;i<=len;i++){
			if(!hashSet.contains(i)){
				System.out.print(i+"	");
			}
		}	
		
	}

	
	//考虑空间复杂度：O(1)
	//请保证 arr[0...N-1]上的数字都在[1~n]之间
	public static void printNumberNoInArray(int[] arr){
		if(arr==null||arr.length==0){
			return;
		}

		for(int value:arr){//争取做到，i位置上，放的数是i+1
			modify(value,arr);
		}
		
		int len=arr.length;
		for(int i=0;i<len;i++){
			if(arr[i]!=i+1){
				System.out.print((i+1)+"	");
			}
		}
	}

	public static void modify(int value,int[] arr){
		while(arr[value-1]!=value){
			int tmp=arr[value-1];

			arr[value-1]=value;

			value=tmp;
		}
	}
	




	public static void main(String[] args){

		int[] arr={
			1,3,4,3
		};

		findNotFound(arr);
		System.out.println("\n\n");

		printNumberNoInArray(arr);
		System.out.println("\n\n");



		System.out.println("hello world");
	}
}




