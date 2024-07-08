/*************************************************************************
    > File Name: question21add.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun May 12 16:51:19 2024
 ************************************************************************/

import java.util.HashMap;

public class question21add{
	
	//自己实现
	public static class TopK{
		private int _K=0;
		private String[] _smallHeap;
		private int _heapSize;
		
		//词频表
		private HashMap<String,Integer> _stringCount;

		//堆位置表
		private HashMap<String,Integer> _heapIndex;

		public TopK(int k){
			_K=k;

			_smallHeap=new String[k];
			
			_heapSize=0;

			_stringCount=new HashMap<>();

			_heapIndex=new HashMap<>();
		}

		public boolean add(String s){
			if(s==null){
				return false;
			}

			int cn=1;
			int index=-2;
			if(_stringCount.containsKey(s)){
				cn+=_stringCount.get(s);
				index=_heapIndex.get(s);
			}
			_stringCount.put(s,cn);
				
			
			switch(index){
			case -2://不存在
				{
					if(_heapSize<_K){
						_smallHeap[_heapSize]=s;
						_heapIndex.put(s,_heapSize);
						++_heapSize;
						adjustUp(_heapSize-1);
					} else {
						//因为cn=1，而且小根堆里的次数至少为1，所以不需要
						_heapIndex.put(s,-1);
					}
					break;
				}
			case -1://存在，但是未进入小根堆
				{
					int top=_stringCount.get(_smallHeap[0]);//顶端次数
					if(cn>top){//cn大于顶端次数
						_heapIndex.put(_smallHeap[0],-1);
						_smallHeap[0]=s;
						_heapIndex.put(s,0);
						adjustDown(0);
					}
					break;
				}
			default://存在。且进入小根堆
				{
					adjustDown(index);
					break;
				}
			}
			return true;
		}

		public void print(){
			for(int i=0;i<_heapSize;++i){
				String s=_smallHeap[i];
				System.out.println(s+"	"+_stringCount.get(s));
			}
		}

		private void adjustDown(int index){
			
			int parent=index;
			int left=index*2+1;
			int right=0;
			while(left<_heapSize){
				right=left+1<_heapSize?left+1:-1;
				
				int leftNum=_stringCount.get(_smallHeap[left]);
				int rightNum=0;
				if(right!=-1){
					rightNum=_stringCount.get(_smallHeap[right]);
				}
				
				
				int minNum=leftNum<=rightNum?leftNum:rightNum;
				int minIndex=leftNum<=rightNum?left:right;
				if(_stringCount.get(_smallHeap[parent])<=minIndex){
					break;
				}

				swap(parent,minIndex);
				parent=minIndex;
				left=parent*2+1;
			}
		}

		public void adjustUp(int index){
			int parent=(index-1)/2;
			while(_stringCount.get(_smallHeap[index])>_stringCount.get(_smallHeap[parent])){
				swap(index,parent);
				index=parent;
				parent=(index-1)/2;
			}
		}

		private void swap(int index1,int index2){
			_heapIndex.put(_smallHeap[index1],index2);
			_heapIndex.put(_smallHeap[index2],index1);

			String s=_smallHeap[index1];
			_smallHeap[index1]=_smallHeap[index2];
			_smallHeap[index2]=s;
		}
	}

	//左程云实现
	public static class Node{//堆上放的东西是node类型的实例
		public String str;
		public int times;
		public Node(String s,int t){
			str=s;
			times=t;
		}
	}
	
	public static class TopKRecord{
		private Node[] heap;
		private int index;
		private HashMap<String,Node> strNodeMap;
		private HashMap<Node,Integer> nodeIndexMap;
		
		public TopKRecord(int size){
			heap=new Node[size];
			index=0;
			strNodeMap=new HashMap<>();
			nodeIndexMap=new HashMap<>();
		}
		
		public void add(String str){
			Node curNode=null;
			int preIndex=-1;
			
			if(!strNodeMap.containsKey(str)){
				curNode=new Node(str,1);	
				strNodeMap.put(str,curNode);
				nodeIndexMap.put(curNode,-1);
			} else {
				curNode=strNodeMap.get(str);
				++curNode.times;
				preIndex=nodeIndexMap.get(curNode);
			}
			
			if(preIndex==-1){
				if(index==heap.length){
					if(heap[0].times<curNode.times){
						nodeIndexMap.put(heap[0],-1);
						nodeIndexMap.put(curNode,0);
						heap[0]=curNode;
						heapify(0,index);
					}
				} else {
					nodeIndexMap.put(curNode,index);
					heap[index]=curNode;
					heapInsert(index++);
				}
			} else {
				heapify(preIndex,index);
			}
		}

		public void printTopK(){
			System.out.println("Top：");
			for(int i=0;i<heap.length;i++){
				if(heap[i]==null){
					break;
				}
				System.out.print("Str："+heap[i].str);
				System.out.println("Times："+heap[i].times);
			}
		}

		private void heapInsert(int index){
			while(index!=0){
				int parent=(index-1)/2;
				if(heap[index].times<heap[parent].times){
					swap(parent,index);
					index=parent;
				} else{
					break;
				}
			}
		}

		private void heapify(int index,int heapSize){
			int l=index*2+1;
			int r=index*2+2;
			int smallest=index;
			while(l<heapSize){
				if(heap[l].times<heap[index].times){
					smallest=l;
				}
				
				if(r<heapSize&&heap[r].times<heap[smallest].times){
					smallest=r;
				}

				if(smallest!=index){
					swap(smallest,index);
				}else{
					break;
				}
				index=smallest;
				l=index*2+1;
				r=index*2+2;
			}
		}

		private void swap(int index1,int index2){
			nodeIndexMap.put(heap[index1],index2);
			nodeIndexMap.put(heap[index2],index1);
			
			Node tmp=heap[index1];
			heap[index1]=heap[index2];
			heap[index2]=tmp;
		}

	}


	public static void main(String[] args){
		String[] s={
			"a",
			"b",
			"abc",
			"b",
			"a",
			"abc",
			"d",
			"e",
			"abc",
			"a",
			"a",
			"e",
			"e",
			"e"
		};

		int k=3;
		TopK topK=new TopK(k);
		for(String t:s){
			topK.add(t);
			topK.print();
			System.out.println("\n\n");
		}
		System.out.println("\n\n\n");

		TopKRecord record=new TopKRecord(k);
		for(String t:s){
			record.add(t);
			record.printTopK();
			System.out.println();
		}

		System.out.println("hello world");
	}
}

