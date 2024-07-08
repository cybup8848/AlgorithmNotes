/*************************************************************************
    > File Name: question6.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jun 11 20:54:38 2024
 ************************************************************************/

import java.util.TreeMap;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
//import jva.util.Map.Entry;
import java.util.TreeMap;

import java.util.Iterator;

import java.util.Comparator;
import java.util.Arrays;
//import java.util.Entry;


public class question6{

	//自己实现
	public static class Node1{
		public int start;
		public int end;
		public int height;
		public Node1(int s,int e,int h){
			start=s;
			end=e;
			height=h;
		}
	}
	
	//这种方法不适应线条状大楼，eg：[7,7,9]
	public static LinkedList<Node1> getSkyLine(int[][] matrix){
		if(matrix==null||matrix.length==0){
			return null;
		}
		
		TreeMap<Integer,Integer> map=new TreeMap<>();
		int row=matrix.length;
		for(int i=0;i<row;i++){
			int start=matrix[i][0];
			int end=matrix[i][1];
			int height=matrix[i][2];
			for(;start<end;++start){
				if(!map.containsKey(start)||(map.containsKey(start)&&map.get(start)<=height)){
					map.put(start,height);
				}
			}
			if(!map.containsKey(end)){
				map.put(end,0);
			}
		}
		
		System.out.println(map.size());

		LinkedList<Node1> res=new LinkedList<Node1>();

		Iterator<Integer> ite=map.keySet().iterator();
		int start=ite.next();
		int height=map.get(start);
		Node1 node=new Node1(start,0,height);
		int maxH=height;
		res.add(node);
		while(ite.hasNext()){
			start=ite.next();
			height=map.get(start);
			if(maxH!=height){
				res.getLast().end=start;
				if(maxH==0){
					res.removeLast();
				}
				node=new Node1(start,0,height);
				res.add(node);
				maxH=height;
			}
		}
		res.removeLast();
		return res;
	}


	//左程云实现
	
	//描述高度变化的对象
	public static class Node{
		public int x;//x轴上的值
		public boolean isAdd;//tre为加入，false为删除
		public int h;//高度
		
		public Node(int x,boolean isAdd,int h){
			this.x=x;
			this.isAdd=isAdd;
			this.h=h;
		}
	}

	//排序的比较策略
	//1、第一个维度的x值从小到大
	//2、如果第一个维度的值相等，看第二个维度的值，“加入”排在前，“删除”排在后
	//3、如果两个对象第一维度和第二维度的值都相等，则认为两个对象相等，谁在前都行
	public static class NodeComparator implements Comparator<Node>{
		@Override
		public int compare(Node o1,Node o2){
			if(o1.x!=o2.x){
				return o1.x-o2.x;
			}
			if(o1.isAdd!=o2.isAdd){
				return o1.isAdd?-1:1;
			}
			return 0;
		}
	}

	//全部流程的主方法
	// [s,e,h]
	// [s,e,h]
	public static List<List<Integer>> buildingOutline(int[][] matrix){
		
		Node[] nodes=new Node[matrix.length*2];
		//每一个大楼轮廓数组，产生两个描述高度变化的对象
		for(int i=0;i<matrix.length;i++){
			nodes[i*2]=new Node(matrix[i][0],true,matrix[i][2]);
			nodes[i*2+1]=new Node(matrix[i][1],false,matrix[i][2]);
		}

		//把描述高度变化的对象数组，按照规定的排序策略排序
		Arrays.sort(nodes,new NodeComparator());
		
		// TreeMap就是java中的红黑树结构，直接当作有序表使用
		TreeMap<Integer,Integer> mapHeightTimes=new TreeMap<>();
		TreeMap<Integer,Integer> mapXHeight=new TreeMap<>();

		for(int i=0;i<nodes.length;i++){
			if(nodes[i].isAdd){//如果当前是加入操作
				if(!mapHeightTimes.containsKey(nodes[i].h)){//没有出现的高度直接新加记录
					mapHeightTimes.put(nodes[i].h,1);
				} else {
					mapHeightTimes.put(nodes[i].h,mapHeightTimes.get(nodes[i].h)+1);//之前出现的高度，次数加1即可
				}
			} else {//如果当前是删除操作
				int times=mapHeightTimes.get(nodes[i].h);
				times-=1;
				if(times==0){//次数为0，直接删除
					mapHeightTimes.remove(nodes[i].h);
				} else {
					mapHeightTimes.put(nodes[i].h,times);//次数不为0，次数减1即可
				}
			}
			
			//根据mapHeightTimes中的最大高度，设置mapXvalueHeight表
			if(mapHeightTimes.isEmpty()){//如果mapHeightTimes为空，说明最大高度为0
				mapXHeight.put(nodes[i].x,0);
			} else{//如果mapHeightTimes不为空，通过mapHeightTimes.lastKey()取得最大高度
				mapXHeight.put(nodes[i].x,mapHeightTimes.lastKey());
			}
		}

		//res为结果数组，每一个List<Inetger> 代表一个轮廓线，右开始位置、结束位置、高度，一共三个元素
		List<List<Integer>> res=new ArrayList<>();
		
		//一个新轮廓线的开始位置
		int start=0;

		//之前的最大高度
		int preHeight=0;

		//根据mapXvalueHeight生成res数组
		for(Map.Entry<Integer,Integer> entry:mapXHeight.entrySet()){
			int curX=entry.getKey();//当前位置
			int curMaxHeight=entry.getValue();//当前最大高度
			if(preHeight!=curMaxHeight){//之前最大高度和当前最大高度不一样时
				if(preHeight!=0){
					res.add(new ArrayList<>(Arrays.asList(start,curX,preHeight)));
				}
				
				start=curX;
				preHeight=curMaxHeight;
			}
		}
		
		return res;
	}




	
	public static void main(String[] args){
		int[][] matrix={
			{
				1,14,4
			},
			{
				2,6,8
			},
			{
				3,5,3
			},
			{
				4,8,5
			},
			{
				7,11,9
			}
		};
		LinkedList<Node1> res=getSkyLine(matrix);
		for(Node1 node:res){
			System.out.println(node.start+"	"+node.end+" "+node.height);
		}
		System.out.println();
		
		List<List<Integer>> res1=buildingOutline(matrix);
		for(List<Integer> x:res1){
			for(int y:x){
				System.out.print(y+"	");
			}
			System.out.println();
		}
		System.out.println("\n\n\n");


		int[][] matrix1={
			{
				2,5,6
			},
			{
				1,7,4
			},
			{	
				4,6,7
			},
			{
				3,6,5
			},
			{
				10,13,2
			},
			{
				9,11,3
			},
			{
				12,14,4
			},
			{
				10,12,5
			}
		};
		res=getSkyLine(matrix1);
		for(Node1 x:res){
			System.out.println(x.start+"	"+x.end+"	"+x.height);
		}
		System.out.println();
		
		res1=buildingOutline(matrix1);
		for(List<Integer> x:res1){
			for(int y:x){
				System.out.print(y+"	");
			}
			System.out.println();
		}
		System.out.println("\n\n\n");

		
		int[][] matrix2={
			{
				1,9,5
			},
			{
				2,9,5
			},
			{
				9,10,6
			}
		};
		
		res=getSkyLine(matrix2);
		for(Node1 x:res){
			System.out.println(x.start+"	"+x.end+"	"+x.height);
		}
		System.out.println("\n\n\n");

		res1=buildingOutline(matrix2);
		for(List<Integer> x:res1){
			for(int y:x){
				System.out.print(y+"	");
			}
			System.out.println();
		}
		System.out.println("\n\n\n");

		int[][] matrix3={
			{
				7,7,9
			}
		};
		res=getSkyLine(matrix3);
		for(Node1 x:res){
			System.out.println(x.start+"	"+x.end+"	"+x.height);
		}
		System.out.println();

		res1=buildingOutline(matrix3);
		for(List<Integer> x:res1){
			for(int y:x){
				System.out.print(y+"	");
			}
			System.out.println();
		}
	
		System.out.println("hello world");
	}
	

	
	public static void main1(String[] args){
		
		//有序表，红黑树
		TreeMap<Integer,String> map=new TreeMap<>();
		map.put(6,"我是6");
		map.put(3,"我是3");
		map.put(9,"我是9");
		map.put(1,"我是1");
		map.put(2,"我是2");
		map.put(5,"我是5");
		map.put(1,"我还是1");//既是新增操作，也是更新操作

		System.out.println(map.containsKey(5));
		map.remove(5);
		System.out.println(map.containsKey(5));
		System.out.println(map.get(9));

		System.out.println("\n\n\n");

		System.out.println(map.firstKey());//最小的key
		System.out.println(map.lastKey());//最大的key
		
		//查询 <=num，离这个num最近的key是谁
		System.out.println(map.floorKey(5));

		//查询 >=num，离这个num最近的key是谁
		System.out.println(map.ceilingKey(5));

		System.out.println(map.get(1));

		System.out.println("hello world");
	}


}







