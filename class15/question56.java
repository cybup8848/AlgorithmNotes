/*************************************************************************
    > File Name: question56.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 24 22:42:41 2024
 ************************************************************************/

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Comparator;
import java.util.Collections;

import java.util.Queue;
//import 

public class question56{



    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(br.readLine());
        String[] info = br.readLine().trim().split(" ");
        List<String> list = new ArrayList<>();
        for(int i=0;i<len;i++){
            list.add(br.readLine().trim());                    
        }
        List<List<String>> res = getRes(list,info[0],info[1]);
        Collections.sort(res,new Comparator<List<String>>() {
			@Override
			public int compare(List<String> o1, List<String> o2) {
				for(int i=0;i<o1.size()&&i<o2.size();i++) {
					if(o1.get(i).compareTo(o2.get(i))<0) {
						return -1;
					}else if(o1.get(i).compareTo(o2.get(i))>0){
						return 1;
					}
				}
				return o1.size()>o2.size()?1:-1;
			}
		});


        if(res.size()>0){
            System.out.println("YES");
            for(List<String> l:res){
                StringBuilder sb = new StringBuilder();
                for(String str:l){
                    sb.append(str+" -> ");    
                }
                String print = sb.toString();
                System.out.println(print.substring(0,print.length()-4));
            }
        }else{
            System.out.println("NO");
        }
    }

    public static List<List<String>> getRes(List<String> list,String start,String to){
        list.add(start);
        HashMap<String,ArrayList<String>> nexts = getNexts(list);
        HashMap<String,Integer> dis = getDis(start,nexts);
        LinkedList<String> sol = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        getShortPath(start,to,nexts,dis,sol,res);
        return res;
    }

    public static void getShortPath(String cur,String to,HashMap<String,ArrayList<String>> nexts,HashMap<String,Integer> dis,
		LinkedList<String> sol,List<List<String>> res){//图的广度优先遍历

        sol.add(cur);
        if(cur.equals(to)){
            res.add(new LinkedList<String>(sol));
        }else{
            for(String str:nexts.get(cur)){
                if(dis.get(cur)==dis.get(str)-1){//途中可能有环
                     getShortPath(str,to,nexts,dis,sol,res);   
                }
				
				getShortPath(str,to,nexts,dis,sol,res);
            }
        }
        sol.pollLast();
    }

    public static HashMap<String,Integer> getDis(String start,HashMap<String,ArrayList<String>> nexts){
        HashMap<String,Integer> map = new HashMap<>();
        map.put(start,0);
        Queue<String> q = new LinkedList<>();
        Set<String> set = new HashSet<>();
        q.add(start);
        set.add(start);
        while(!q.isEmpty()){
            String cur = q.poll();
            for(String str:nexts.get(cur)){
                if(!set.contains(str)){
                    map.put(str,map.get(cur)+1);
                    q.add(str);
                    set.add(str);
                }
            }
        }
        return map;
    }

    public static HashMap<String,ArrayList<String>> getNexts(List<String> list){
        HashSet<String> set = new HashSet<>(list);
        HashMap<String,ArrayList<String>> res = new HashMap<>();
        for(int i=0;i<list.size();i++){
            res.put(list.get(i),new ArrayList<String>());    
        }
        for(int i=0;i<list.size();i++){
            res.put(list.get(i),getNext(list.get(i),set));
        }
        return res;
    }

    public static ArrayList<String> getNext(String str,HashSet<String> set){
        char[] arr = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            for(char c='a';c<='z';c++){
                if(arr[i]!=c){
                    char tmp = arr[i];
                    arr[i] = c;
                    if(set.contains(String.valueOf(arr))){
                        list.add(String.valueOf(arr));        
                    }
                    arr[i] = tmp;
                }
            }
        }
        return list;
    }


}




