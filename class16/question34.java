/*************************************************************************
    > File Name: question34.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jul  5 09:40:14 2024
 ************************************************************************/


import java.util.HashMap;
import java.util.Date;
import java.util.Comparator;

public class question34{
	
	public static class Node{
		public int key;
		public int count;
		public Date time;

		public Node(){
			key=0;
			count=0;
			time=new Date();
		}
	}


	public static class ComparatorNode{
		public long compare(Node o1,Node o2){
			if(o1.count!=o2.count){
				return o1.count-o2.count;
			}

			return o1.time.getTime()-o2.time.getTime();
		}
	}

	//自己实现，LFU
	public static class PriorityQueue{
		private Node[] arrays;//队列
		private int capacity=0;//最大容量
		private int size=0;//现在队列里的个数
		
		private HashMap<Integer,Integer> keyIndexMap;//键、索引映射

		private HashMap<Integer,Integer> keyValueMap;//键值映射

		private ComparatorNode comparatorNode;
		

		public PriorityQueue(int K){
			arrays=new Node[K];
			capacity=K;
			size=0;
			comparatorNode=new ComparatorNode();

			keyIndexMap=new HashMap<>();
			keyValueMap=new HashMap<>();
		}

		public int getCapacity(){
			return capacity;
		}

		public int getSize(){
			return size;
		}

		public void set(int key,int value){
			if(!keyValueMap.containsKey(key)){
				if(size==capacity){
					pop();
				}
				push(key,value);
				return;
			}

			//如果key已经在里面存在
			keyValueMap.put(key,value);
			int index=keyIndexMap.get(key);
			Node node=arrays[index];
			node.count+=1;
			node.time=new Date();
			heapSort(node,index);
		}
		
		public int get(int key){
			return keyValueMap.containsKey(key)?keyValueMap.get(key):Integer.MIN_VALUE;
		}
		
		//插入节点，前提：这个节点的key不存在
		private void push(int key,int value){
			Node node=new Node();
			node.key=key;
			node.count=1;
			node.time=new Date();
			
			arrays[size]=node;
			keyIndexMap.put(node.key,size);
			keyValueMap.put(key,value);
			heapify(node,size);
			++size;
		}

		//弹出栈顶元素
		private void pop(){
			Node top=arrays[0];
			keyIndexMap.remove(top.key);
			keyValueMap.remove(top.key);
			--size;
			arrays[0]=arrays[size];
			
			top=arrays[0];
			heapSort(top,0);
		}


		//向上调整
		private void heapify(Node node,int index){
			int child=index;
			int parent=(index-1)/2;
			while(comparatorNode.compare(arrays[child],arrays[parent])<0){
				
				keyIndexMap.put(arrays[parent].key,child);
				keyIndexMap.put(arrays[child].key,parent);

				swapNode(child,parent);
				
				child=parent;
				parent=(child-1)/2;
			}
		}

		private void swapNode(int i,int j){
			Node tmp=arrays[i];
			arrays[i]=arrays[j];
			arrays[j]=tmp;
		}

		//向下调整
		private void heapSort(Node node,int index){
			int parent=index;
			int left=parent*2+1;
			while(left<size){
				int right=left+1<size?left+1:left;
				
				int smallest=comparatorNode.compare(arrays[left],arrays[right])<0?left:right;
				
				smallest=comparatorNode.compare(arrays[parent],arrays[smallest])<0?parent:smallest;
				if(smallest==parent){
					break;
				}

				keyIndexMap.put(arrays[parent].key,smallest);
				keyIndexMap.put(arrays[smallest].key,parent);

				swapNode(parent,smallest);
				parent=smallest;
				left=parent*2+1;
			}
		}	
	}

	//上面也可以不用Date类，类内部用一个计数器timer，调用get、set时，timer++

	//左程云实现
	//要求set、get方法的时间复杂度O(1)
	public static class LFUNode{
		int key;
		int value;
		public LFUNode pre;
		public LFUNode next;
		public LFUNode(int k,int v){
			key=k;
			value=v;
			pre=next=null;
		}
	}

	public static class LFUNodeBidList{
		private LFUNode front;
		private LFUNode rear;

		public LFUNodeBidList(){
			front=new LFUNode(0,0);//构造一个虚拟头结点
			rear=new LFUNode(0,0);//构造一个虚拟尾节点
			front.next=rear;
			rear.pre=front;
		}

		public void insertRearNode(LFUNode node){//尾部插入

			LFUNode rearPre=rear.pre;
			rearPre.next=node;
			node.pre=rearPre;

			node.next=rear;
			rear.pre=node;
		}

		public void deleteFrontNode(){
			if(isEmpty()){
				return;
			}
			
			LFUNode head=front.next;
			front.next=head.next;
			head.next.pre=front;
		}

		public void deleteRandNode(LFUNode node){
			node.pre.next=node.next;
			node.next.pre=node.pre;
		}

		public LFUNode getFront(){
			return isEmpty()?null:front.next;
		}

		public boolean isEmpty(){
			return front.next==rear;
		}
	}
		

	public static class LFUBucket{
		public int count;//调用次数

		public LFUNodeBidList list;
		
		public LFUBucket pre;
		public LFUBucket next;

		public LFUBucket(int cn){
			count=cn;
			list=new LFUNodeBidList();
			pre=next=null;
		}
	}

	public static class LFUBucketBidList{
		private LFUBucket front;
		private LFUBucket rear;
		
		private HashMap<Integer,LFUNode> keyNodeMap;//键、LFUNode映射

		private HashMap<Integer,LFUBucket> keyBucketMap;//键、LFU桶映射
		
		private int capacity;
		private int size;


		//不使用虚拟节点
		public LFUBucketBidList(int K){
			front=rear=new LFUBucket(1);
			
			front.next=rear;
			rear.pre=front;
			
			keyNodeMap=new HashMap<>();

			keyBucketMap=new HashMap<>();

			capacity=K;
			size=0;
		}

		public void set(int key,int value){
			if(!keyNodeMap.containsKey(key)){
				if(size==capacity){
					LFUNode delNode=front.list.getFront();
					keyNodeMap.remove(delNode.key);
					keyBucketMap.remove(delNode.key);
					front.list.deleteFrontNode();
					if(front.list.isEmpty()){
						front=front.next;
					}
					--size;
				}

				LFUNode node=new LFUNode(key,value);
				if(front.count!=1){
					LFUBucket tmp=new LFUBucket(1);
					tmp.next=front;
					front.pre=tmp;
					front=tmp;
				}
				front.list.insertRearNode(node);
				keyNodeMap.put(key,node);
				keyBucketMap.put(key,front);
				++size;

				return;
			}

			//如果已经存在
			LFUNode lfuNode=keyNodeMap.get(key);
			LFUBucket lfuBucket=keyBucketMap.get(key);
			lfuBucket.list.deleteRandNode(lfuNode);
			
			LFUBucket shouldBucket=null;
			if(lfuBucket.next==null||lfuBucket.next.count!=lfuBucket.count+1){
				shouldBucket=new LFUBucket(lfuBucket.count+1);
				shouldBucket.next=lfuBucket.next;
				shouldBucket.pre=lfuBucket;

				LFUBucket lfuBucketNext=lfuBucket.next;
				lfuBucket.next=shouldBucket;
				if(lfuBucketNext!=null){
					lfuBucketNext.pre=shouldBucket;
				}
			} else {
				shouldBucket=lfuBucket.next;
			}

			shouldBucket.list.insertRearNode(lfuNode);
			keyBucketMap.put(key,shouldBucket);

			if(lfuBucket.list.isEmpty()){
				if(front==lfuBucket){
					front=lfuBucket.next;
					front.pre=null;
				} else {
					lfuBucket.pre.next=lfuBucket.next;
					if(lfuBucket.next!=null){
						lfuBucket.next.pre=lfuBucket.pre;
					}
				}
			}
		}

		public int get(int key){
			LFUNode node=keyNodeMap.get(key);
			if(node!=null){
				return node.value;
			}
			return Integer.MIN_VALUE;
		}


		public boolean isEmpty(){
			return front==null;
		}

	}


	
	public static void main(String[] args){
		int K=10;
		PriorityQueue priorityQueue=new PriorityQueue(K);

		int N=100;
		for(int i=0;i<N;i++){
			int key=(int)(Math.random()*10);
			int value=(int)(Math.random()*20);
			priorityQueue.set(key,value);

			key=(int)(Math.random()*10)+2;
			priorityQueue.get(key);
		}



		LFUBucketBidList bidList=new LFUBucketBidList(K);
		for(int i=0;i<N;i++){
			int key=(int)(Math.random()*10);
			int value=(int)(Math.random()*20);
			bidList.set(key,value);

			key=(int)(Math.random()*10)+2;
			bidList.get(key);
		}


		System.out.println("hello world");
	}
}










