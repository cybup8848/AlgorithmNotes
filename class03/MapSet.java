/*************************************************************************
    > File Name: HashSet.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jan 18 19:29:50 2024
 ************************************************************************/
import java.util.HashSet;
import java.util.HashMap;
public class MapSet{
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		
		//UnorderedMap   UnSortedMao  --> C++
		//UnorderedSet   UnSortedSet  --> C++
		HashSet<Integer> hashSet1=new HashSet<>();
		hashSet1.add(3);
		System.out.println(hashSet1.contains(3));
		hashSet1.remove(3);
		System.out.println(hashSet1.contains(3));

		HashMap<Integer,String> mapTest=new HashMap<>();
		mapTest.put(1,"zuo");
		mapTest.put(1,"cheng");
		mapTest.put(2,"2");
		System.out.println(mapTest.containsKey(1));
		System.out.println(mapTest.get(1));
		System.out.println(mapTest.get(4));
		
		System.out.println("Hello World");
	}
}
