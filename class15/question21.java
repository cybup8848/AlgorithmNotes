/*************************************************************************
    > File Name: question21.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun May 12 15:25:20 2024
 ************************************************************************/

import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class question21{

	public static class WordCount{
		public char word;
		public int count;
		
		public WordCount(char w,int cn){
			word=w;
			count=cn;
		}
	}

	public static class WordCountComparator implements Comparator<WordCount>{
		@Override
		public int compare(WordCount o1,WordCount o2){
			return o2.count-o1.count;
		}
	}


	public static List<Character> getTopK(String s,int K){
		char[] chs=s.toCharArray();
		if(chs==null){
			return null;
		}

		int len=chs.length;
		int[] wordCounts=new int[26];
		for(int i=0;i<len;i++){
			++wordCounts[chs[i]-'a'];
		}

		PriorityQueue<WordCount> priorityQueue=new PriorityQueue<WordCount>(new WordCountComparator());
		for(int i=0;i<26;i++){
			priorityQueue.offer(new WordCount((char)(i+(int)'a'),wordCounts[i]));
		}

		int cn=0;
		List<Character> res=new ArrayList<>();
		while(cn<K&&!priorityQueue.isEmpty()){
			res.add(priorityQueue.poll().word);
			cn++;
		}
		
		return res;
	}

	//左程云实现
	//建立小根堆
	
	public static class StringCount{
		public String str;
		public int count;
		public StringCount(String s,int cn){
			str=s;
			count=cn;
		}
	}
	
	public static class StringCountComparator implements Comparator<StringCount>{
		@Override
		public int compare(StringCount o1,StringCount o2){
			return o1.count-o2.count;
		}
	}

	public static PriorityQueue<StringCount> getTopK1(String[] ss,int K){
		if(ss==null){
			return null;
		}
		
		HashMap<String,Integer> hashMap=new HashMap<>();
		for(String s:ss){
			if(hashMap.containsKey(s)){
				int v=hashMap.get(s);
				hashMap.put(s,v+1);
			} else{
				hashMap.put(s,1);
			}
		}

		PriorityQueue<StringCount> priorityQueue=new PriorityQueue<>(new StringCountComparator());
		int cn=0;

		Iterator<Map.Entry<String,Integer>> iterator=hashMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String,Integer> entry=iterator.next();
			if(cn<K){
				priorityQueue.offer(new StringCount(entry.getKey(),entry.getValue()));
				cn++;
			} else {
				if(entry.getValue()>priorityQueue.peek().count){
					priorityQueue.poll();
					priorityQueue.offer(new StringCount(entry.getKey(),entry.getValue()));
				}
			}
		}
		
		return priorityQueue;
	}
	



	public static void main(String[] args){
		String s="afhiwstguiwefgvwdsighviwerhgorhgdshbfgjytgwhgeruyhrt";
		int K=5;
		List<Character> res=getTopK(s,K);
		for(char ch:res){
			System.out.print(ch+"	");
		}
		System.out.println("\n\n\n");

		String[] ss={
			"wetgewrhgdr","stgerhtr","ste","we","wete","wetre","wete","wete","wetegbdfhfgd","wetewrter","asdgfsd"
		};
		PriorityQueue<StringCount> result=getTopK1(ss,K);
		while(!result.isEmpty()){
			StringCount s1=result.poll();
			System.out.println(s1.str+"	"+s1.count);
		}



		System.out.println("hello world");
	}
}


