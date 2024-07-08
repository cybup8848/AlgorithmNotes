/*************************************************************************
    > File Name: Comparator.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jan 17 18:47:47 2024
 ************************************************************************/
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
public class comparator{
	
	
	//对于所有的比较器：
	//返回负数的时候，第一个参数排在前面
	//返回正数的时候，第二个参数排在前面
	//返回0的时候，谁在前面无所谓
	
	public static int com(int a,int b){
		return a>b?-1:1;
	}

	public static void print(int[] arr){
		for(int x:arr)
			System.out.println(x);
	}

	public static class AComp implements Comparator<Integer>{
		public int compare(Integer arg0,Integer arg1){
			return arg1-arg0;
		}
	}

	public static void main(String[] args){
		int[] arr={5,4,3,7,9,1,0};
		Arrays.sort(arr);
		print(arr);
		System.out.println("Hello World");
		
		//Arrays.sort(arr,new com());
		//print(arr);
		
		//实现大根堆
		PriorityQueue<Integer> heap=new PriorityQueue<>(new AComp());
		heap.add(6);
		heap.add(9);
		heap.add(3);
		heap.add(2);
		heap.add(10);
		while(!heap.isEmpty()){
			System.out.println(heap.poll());
		}



	}
}
