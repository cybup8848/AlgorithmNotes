/*************************************************************************
    > File Name: OrderedTable.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Feb 27 14:39:53 2024
 ************************************************************************/

import java.util.List;
import java.util.HashMap;
import java.util.Stack;
import java.util.Arrays;

public class OrderedTableAndUnionFindSet{

	
	public static void setTrue(int[][] islands,boolean[][] flags,int i,int j){
		
		//往上
		if((i-1>=0)&&(islands[i-1][j]==1)&&(flags[i-1][j]==false)){
			flags[i-1][j]=true;
			setTrue(islands,flags,i-1,j);
		}

		//往下
		if((i+1<islands.length)&&(islands[i+1][j]==1)&&(flags[i+1][j]==false)){
			flags[i+1][j]=true;
			setTrue(islands,flags,i+1,j);
		}

		//往左
		if((j-1>=0)&&(islands[i][j-1]==1)&&(flags[i][j-1]==false)){
			flags[i][j-1]=true;
			setTrue(islands,flags,i,j-1);
		}

		//往右
		if((j+1<islands[0].length)&&(islands[i][j+1]==1)&&(flags[i][j+1]==false)){
			flags[i][j+1]=true;
			setTrue(islands,flags,i,j+1);
		}
	}

	//自己实现
	public static int islandQues(int[][] islands){
		int num=0;
		if(islands==null){
			return num;
		}
		int row=islands.length;
		int col=islands[0].length;

		boolean[][] flags=new boolean[row][col];

		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if((flags[i][j]==false)&&(islands[i][j]==1)){
					num++;
					setTrue(islands,flags,i,j);
				}
			}
		}
		return num;
	}

	//左程云实现
	//infect：感染过程
	public static int countIslands(int[][] m){
		if(m==null||m[0]==null){
			return 0;
		}
		
		int N=m.length;
		int M=m[0].length;
		int res=0;
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				if(m[i][j]==1){
					res++;
					infect(m,i,j,N,M);
				}
			}
		}
		return res;
	}

	public static void infect(int[][] m,int i,int j,int N,int M){
		if(i<0||i>=N||j<0||j>=M||m[i][j]!=1){
			return;
		}

		//i、j没有越界，并且当前位置值是1
		m[i][j]=2;
		infect(m,i+1,j,N,M);
		infect(m,i-1,j,N,M);
		infect(m,i,j-1,N,M);
		infect(m,i,j+1,N,M);
	}
	
	//进阶：并行算法
	// 


	
	//自己实现
	public class Node{
		public int num;
		public int count;
		public Node parent;
		public Node(int num){
			this.num=num;
			parent=null;
		}
	}

	public class UnionFind{
		Node[] parent;
		int length;

		public UnionFind(int[] nums){
			this.length=nums.length;
			for(int i=0;i<this.length;i++){
				Node tmp=new Node(nums[i]);
				tmp.count=1;
				tmp.parent=tmp;
				parent[i]=tmp;
			}
		}

		public boolean isSameSet(Node a,Node b){
			while(a!=a.parent){
				a=a.parent;
			}

			while(b!=b.parent){
				b=b.parent;
			}
			
			return a==b;
		}

		public void union(Node a,Node b){
			while(a!=a.parent){
				a=a.parent;
			}

			while(b!=b.parent){
				b=b.parent;
			}

			if(a.count>b.count){
				a.count+=b.count;
				b.parent=a;
			} else {
				b.count+=a.count;
				a.parent=b;
			}
		}
	}


	//左程云实现
	public static class Element<V>{
		public V value;
		public Element(V value){
			this.value=value;
		}
	}

	public static class UnionFindSet<V>{
		public HashMap<V,Element<V>> elementMap;
		
		//key  某个元素
		//value：该元素的父节点
		public HashMap<Element<V>,Element<V>> fatherMap;
		
		//key  某个集合的代表元素
		//value  该集合的大小
		public HashMap<Element<V>,Integer> sizeMap;
			
		public UnionFindSet(List<V> list){
			elementMap=new HashMap<V,Element<V>>();
			fatherMap=new HashMap<Element<V>,Element<V>>();
			sizeMap=new HashMap<Element<V>,Integer>();

			for(V value:list){
				Element<V> element=new Element<V>(value);
				elementMap.put(value,element);
				fatherMap.put(element,element);
				sizeMap.put(element,1);
			}
		}

		//自己实现
		public boolean isSameSet1(V val1,V val2){
			if(elementMap.containsKey(val1)==false||elementMap.containsKey(val2)==false){
				return true;
			}

			Element<V> element1=elementMap.get(val1);
			Element<V> element2=elementMap.get(val2);

			while(element1!=fatherMap.get(element1)){
				element1=fatherMap.get(element1);
			}
			
			while(element2!=fatherMap.get(element2)){
				element2=fatherMap.get(element2);
			}
			
			return element1==element2;
		}

		public void unionSet1(V val1,V val2){
			if(isSameSet1(val1,val2)){
				return;
			}

			Element<V> element1=elementMap.get(val1);
			Element<V> element2=elementMap.get(val2);
			
			while(element1!=fatherMap.get(element1)){
				element1=fatherMap.get(element1);
			}

			while(element2!=fatherMap.get(element2)){
				element2=fatherMap.get(element2);
			}

			int size1=sizeMap.get(element1);
			int size2=sizeMap.get(element2);
			if(size1<size2){
				Element<V> tmp=element1;
				element1=element2;
				element2=tmp;
			}

			//fatherMap.remove(element2);
			fatherMap.put(element2,element1);

			sizeMap.put(element1,size1+size2);
			sizeMap.remove(element2);
		}

		//左程云实现
		//给定一个element，往上一直找，把代表元素返回
		private Element<V> findHead(Element<V> element){
			Stack<Element<V>> path=new Stack<Element<V>>();
			while(element!=fatherMap.get(element)){
				path.push(element);
				element=fatherMap.get(element);
			}

			while(!path.isEmpty()){
				fatherMap.put(path.pop(),element);
			}

			return element;
		}
		
		public boolean isSameSet(V a,V b){
			if(elementMap.containsKey(a)&&elementMap.containsKey(b)){
				return findHead(elementMap.get(a))==findHead(elementMap.get(b));
			}

			return false;
		}

		public void union(V a,V b){
			if(elementMap.containsKey(a)&&elementMap.containsKey(b)){
				Element<V> aF=findHead(elementMap.get(a));
				Element<V> bF=findHead(elementMap.get(b));
				
				if(aF!=bF){
					Element<V> big=sizeMap.get(aF)>=sizeMap.get(bF)?aF:bF;
					Element<V> small=big==aF?bF:aF;

					fatherMap.put(small,big);
					sizeMap.put(big,sizeMap.get(big)+sizeMap.get(small));
					sizeMap.remove(small);
				}
			}
		}
	}

	//自己实现KMP算法
	public static int[] getNext(String str){
		if(str==null){
			return null;
		}

		int len=str.length();
		int[] next=new int[len];
		if(len==0){
			return next;
		}

		next[0]=-1;
		if(len==1){
			return next;
		}

		next[1]=0;
		if(len==2){
			return next;
		}

		int index=0;
		int j=0;
		for(int i=2;i<len;){
			if(str.charAt(j)==str.charAt(i-1)){
				next[i++]=++j;
			} else if(j>0){
				j=next[j];
			} else {
				next[i++]=0;
			}
		}
	
		return next;
	}

	public static int KMP(String str1,String str2){
		if(str1==null||str2==null){
			return -1;
		}

		int len1=str1.length();
		int len2=str2.length();
		if(len1<len2){
			return -1;
		}

		int[] next=getNext(str2);
		int i=0;
		int j=0;
		while(i<len1&&j<len2){
			while(i<len1&&j<len2&&str1.charAt(i)==str2.charAt(j)){
				i++;
				j++;
			}
			
			//匹配成功
			if(j==len2){
				return i-j;
			}

			j=next[j];
			if(j==-1){
				i++;
				j=0;
			}
		}

		return -1;
	}
	
	//左程云实现
	public static int getIndexOf(String s,String m){
		if(s==null||m==null||s.length()<1||s.length()<m.length()){
			return -1;
		}

		char[] str1=s.toCharArray();
		char[] str2=m.toCharArray();
		int i1=0;
		int i2=0;
		int[] next=getNextArray(str2);//O(M)
		
		//O(N)
		while(i1<str1.length&&i2.str2.length){
			if(str1[i1]==str2[i2]){
				i1++;
				i2++;
			} else if(next[i2]==-1){//等价于  else if(i2==0)      str2中比对的位置已经无法往前跳了 
				i1++;
			} else {
				i2=next[i2];
			}
		}
		
		//i1越界了 或 i2越界了
		return i2==str2.length?i1-i2:-1;
	}

	public static int[] getNextArray(char[] ms){
		if(ms.length==1){
			return new int[]{-1};
		}
		
		int[] next=new int[ms.length];
		next[0]=-1;
		next[1]=0;
		int i=2;//next数组的位置
		int cn=0;
		while(i<ms.length){
			if(ms[i-1]==ms[cn]){
				next[i++]=++cn;
			} else if(cn>0){
				cn=next[cn];
			} else {
				next[i++]=0;
			}
		}
		return cn;
	}

	public static void quickSort1(int[] nums,int start,int end){// [start,end]
		if(start>=end){
			return;
		}
	
		int base=nums[end];
		int index=start;
		for(int i=start;i<end;i++){
			if(nums[i]<=base){
				int tmp=nums[index];
				nums[index]=nums[i];
				nums[i]=tmp;
				index++;
			}
		}
		nums[end]=nums[index];
		nums[index]=base;
		quickSort1(nums,start,index-1);
		quickSort1(nums,index+1,end);
	}

	public static void quickSort2(int[] nums,int start,int end){// [start,end]
		if(start>=end){
			return;
		}

		int small=start;
		int big=end;
		int base=nums[big];
		for(int i=start;i<big;){
			if(nums[i]>base){
				big--;
				int tmp=nums[i];
				nums[i]=nums[big];
				nums[big]=tmp;
			} else {
				i++;
			}
		}
		int tmp=nums[big];
		nums[big]=nums[end];
		nums[end]=tmp;
		quickSort2(nums,start,big-1);
		quickSort2(nums,big+1,end);
	}
	
	public static int[] generateRandomArray(int maxValue,int maxSize){
		int size=(int)(Math.random()*(1+maxSize));
		int[] res=new int[size];
		for(int i=0;i<size;i++){
			res[i]=(int)(Math.random()*(1+maxValue))-(int)(Math.random()*maxValue);
		}
		return res;
	}

	public static int[] copyArray(int[] nums){
		if(nums==null){
			return null;
		}
		int len=nums.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=nums[i];
		}
		return res;
	}
	
	public static boolean isEqual(int[] arr1,int[] arr2){
		if(arr1==null||arr2==null){
			return false;
		}
		int len1=arr1.length;
		int len2=arr2.length;
		if(len1!=len2){
			return false;
		}
		for(int i=0;i<len1;i++){
			if(arr1[i]!=arr2[i]){
				return false;
			}
		}
		return true;
	}

	public static void systemSort(int[] res){
		Arrays.sort(res);
	}
	
	//Manacher 算法，最长回文子串长度
	
	public static int manacher(String str){
				



		return 0;
	}


	public static void main(String[] args){
		int[][] islands={
			{0,0,1,0,1,0},
			{1,1,1,0,1,0},
			{1,0,0,1,0,0},
			{0,0,0,0,0,0}
		};
		
		System.out.println(islands.length);
		System.out.println(islands[0].length);


		int num=islandQues(islands);
		System.out.println(num);
		
		System.out.println(countIslands(islands));

		//测试KMP算法
		System.out.println(KMP("agdsgdfghfdhfg","hfd"));
		System.out.println(KMP("sdgdfhfgwrewtrecx","asfadsfe"));


		//测试快速排序
		int testTime=500000;
		int maxValue=1000;
		int maxSize=100;
		boolean flag=true;
		for(int i=0;i<testTime;i++){
			int[] arr=generateRandomArray(maxValue,maxSize);
			systemSort(arr);

			int[] arr1=copyArray(arr);
			//quickSort1(arr1,0,arr1.length-1);
			quickSort2(arr1,0,arr1.length-1);
			if(isEqual(arr,arr1)==false){
				flag=false;
				break;
			}
		}
		
		System.out.println(flag==true?"right":"wrong");
	}
}
