/*************************************************************************
    > File Name: question8.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May  8 09:34:44 2024
 ************************************************************************/
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class question8{

	//自己实现
	public static HashMap<Integer,Integer> getDiffKPair(int[] arr,int k){
		if(arr==null){
			return null;
		}
		HashMap<Integer,Integer> hashMap=new HashMap<>();
		HashSet<Integer> hashSet=new HashSet<>();
		for(int x:arr){
			hashSet.add(x);
		}

		for(int x:arr){
			if(hashSet.contains(x+k)){
				hashMap.put(x,x+k);
			}
		}
		return hashMap;
	}

	//左程云实现
	public static List<List<Integer>> allPair(int[] arr,int k){
		HashSet<Integer> set=new HashSet<>();
		int len=arr.length;
		for(int i=0;i<len;i++){
			set.add(arr[i]);
		}

		List<List<Integer>> res=new ArrayList<>();
		for(Integer cur:set){
			if(set.contains(cur+k)){
				res.add(Arrays.asList(cur,cur+k));
			}
		}
		
		return res;
	}


	public static void main(String[] args){
		int[] arr={
			3,2,5,7,0,0
		};
		int k=2;
		HashMap<Integer,Integer> hashMap=getDiffKPair(arr,2);
		Iterator<Map.Entry<Integer,Integer>> iterator=hashMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<Integer,Integer> entry=iterator.next();
			System.out.println(entry.getKey()+"	"+entry.getValue());
		}
		System.out.println("\n\n\n");
		
		List<List<Integer>> list=allPair(arr,k);
		for(List<Integer> tmp:list){
			for(Integer x:tmp){
				System.out.print(x+"	");
			}
			System.out.println();
		}


		System.out.println("abc"=="abc");
		System.out.println("hello world");
	}


}


