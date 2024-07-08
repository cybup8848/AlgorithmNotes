/*************************************************************************
    > File Name: Graph.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jan 26 09:13:59 2024
 ************************************************************************/
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import java.util.Vector;
import java.util.PriorityQueue;
import java.util.Comparator;

//点结构
class Node{
	public int value;//编号
	public int in;//此点入度
	public int out;//此点出度 
	public ArrayList<Node> nexts;//此点发散出去的边，直接相邻的点
	public ArrayList<Edge> edges;//此点发散出去的边
	public Node(int value){
		this.value=value;
		this.in=0;
		this.out=0;
		this.nexts=new ArrayList<Node>();
		this.edges=new ArrayList<Edge>();
	}
}

//边结构
class Edge{
	public int weight;
	public Node from;
	public Node to;

	public Edge(int weight,Node from,Node to){
		this.weight=weight;
		this.from=from;
		this.to=to;
	}


}
	
//让结构支持所有的算法
public class Graph{
	public HashMap<Integer,Node> nodes;//key：点的编号    value：实际的点      编号和实际结构组合成一个哈希表
	public HashSet<Edge> edges;
	
	public Graph(){
		this.nodes=new HashMap<Integer,Node>();
		this.edges=new HashSet<Edge>();
	}

	//遍历，可以应用不同的遍历方式
	public static void travel(Graph graph){
		Node source=findSource(graph);
		HashSet<Node> visited=new HashSet<Node>();
		DFSRecur(visited,source);
		System.out.println("\n\n");


		DFSUnRecur(source);
		System.out.println("\n\n");

		visited.clear();
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(source);
		visited.add(source);
		BFSRecur(visited,queue);
		System.out.println("\n\n");
	
		BFSUnRecur(source);
		System.out.println("\n\n");

	}

	//找到源节点
	public static Node findSource(Graph graph){
		Node source=null;
		for(Node node:graph.nodes.values()){
			if(node.in==0){
				source=node;
				break;
			}
		}
		return source;
	}
	
	//自己实现
	//深度优先搜索，递归版本
	public static void DFSRecur(HashSet<Node> visited,Node source){
		if(!visited.contains(source)){
			System.out.print(source.value+"	");
			visited.add(source);
			for(Node node:source.nexts){
				DFSRecur(visited,node);
			}
		}
	}

	//深度优先搜索，非递归版本
	public static void DFSUnRecur(Node source){
		if(source==null){
			return;
		}
		Stack<Node> stack=new Stack<Node>();
		HashSet<Node> visited=new HashSet<Node>();
		stack.push(source);
		Node cur=null;
		while(!stack.isEmpty()){
			cur=stack.pop();
			if(visited.contains(cur)){
				continue;
			}
			visited.add(cur);
			System.out.print(cur.value+"	");
			for(Node node:cur.nexts){
				stack.push(node);//会导致栈可能非常大，因为会有很多重复的node进栈
			}
		}
	}
	
	//广度优先搜索，递归版本
	public static void BFSRecur(HashSet<Node> visited,Queue<Node> queue){
		if(queue.isEmpty()){
			return;
		}
		Node cur=queue.poll();
		System.out.print(cur.value+"	");
		for(Node node:cur.nexts){
			if(!visited.contains(node)){
				queue.offer(node);
				visited.add(node);
			}
		}
		BFSRecur(visited,queue);
	}
	
	//广度优先搜索，非递归版本
	public static void BFSUnRecur(Node source){
		if(source==null){
			return;
		}
		HashSet<Node> visited=new HashSet<Node>();
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(source);
		Node cur=null;
		while(!queue.isEmpty()){
			cur=queue.poll();
			System.out.print(cur.value+"	");
			for(Node node:cur.nexts){
				if(!visited.contains(node)){
					queue.offer(node);
					visited.add(node);
				}
			}
		}
	}
	
	//左程云版本，广度优先搜索
	public static void bfs(Node node){
		if(node==null){
			return;
		}
		Queue<Node> queue=new LinkedList<Node>();
		HashSet<Node> set=new HashSet<Node>();
		queue.offer(node);
		set.add(node);
		while(!queue.isEmpty()){
			Node cur=queue.poll();
			System.out.print(cur.value+"	");
			for(Node next:cur.nexts){
				if(!set.contains(next)){
					set.add(next);
					queue.offer(next);
				}
			}
		}
	}

	//左程云版本，深度优先搜索
	public static void dfs(Node node){
		if(node==null){
			return;
		}
		Stack<Node> stack=new Stack<Node>();
		HashSet<Node> set=new HashSet<Node>();
		stack.push(node);
		set.add(node);
		System.out.print(node.value+"	");
		while(!stack.isEmpty()){
			Node cur=stack.pop();
			for(Node next:cur.nexts){
				//如果cur的孩子全部打印了，就返回cur的父节点这一层。回溯到上一层
				if(!set.contains(next)){//如果邻接点(相当于他的孩子还有没有打印的
										//，就继续往下打印，一条路走到黑，同时将父节点
										//再次压入栈中，因为父节点还可能有没有打印的孩子)
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.print(next.value+"	");//处理邻居，相当于cur的一个孩子
					break;//沿着刚进去的节点继续往下走
						  //所走的路径就保存在栈里面
				}
			}
		}
	}

	//拓扑排序，自己实现
	public static void topologicalSort(Graph graph){
		if(graph==null){
			return;
		}
		Queue<Node> queue=new LinkedList<Node>();//存储入度为0的节点
		HashMap<Node,Integer> inMap=new HashMap<Node,Integer>();
		//找到所有入度为0的节点
		for(Node node:graph.nodes.values()){
			inMap.put(node,node.in);
			if(node.in==0){
				queue.offer(node);
			}
		}
		
		//进行拓扑排序
		Node cur=null;
		int count=0;//查看打印了几个节点，最后检查是否有环
		while(!queue.isEmpty()){
			cur=queue.poll();
			count++;
			System.out.print(cur.value+"	");
			
			for(Node node:cur.nexts){
				int in=inMap.get(node);
				in-=1;
				inMap.put(node,in);
				if(in==0){
					queue.offer(node);
				}
			}
		}
		if(count!=graph.nodes.size()){
			System.out.println("\nthe graph has cycle");
		} else {
			System.out.println("\n topological sort is complished");
		}
	}

	//拓扑排序。左程云实现
	public static List<Node> sortedTopology(Graph graph){
		//key：某一个node
		//value：剩余的入度
		HashMap<Node,Integer> inMap=new HashMap<Node,Integer>();

		//入度为0的点，才能进这个队列
		Queue<Node> zeroInQueue=new LinkedList<Node>();
		for(Node node:graph.nodes.values()){
			inMap.put(node,node.in);//这是为了不改变的原来的结构
			if(node.in==0){
				zeroInQueue.offer(node);
			}
		}

		//拓扑排序的结果，以此加入result
		List<Node> result=new ArrayList<Node>();
		while(!zeroInQueue.isEmpty()){
			Node cur=zeroInQueue.poll();
			result.add(cur);
			for(Node next:cur.nexts){
				inMap.put(next,inMap.get(next)-1);
				if(inMap.get(next)==0){
					zeroInQueue.offer(next);
				}
			}
		}
		return result;
	}
	
	public static void swap1(int[] arr,int i,int j){
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;
	}
	
	//保证i和j不相等，根据异或进行交换
	public static void swap2(int[] arr,int i,int j){
		arr[i]=arr[i]^arr[j];
		arr[j]=arr[i]^arr[j];
		arr[i]=arr[i]^arr[j];
	}

	//练习堆的插入、堆化、堆排序
	//以大根堆为例
	public static void heapInsert(int[] arr,int index){//索引index向上插入，索引从0开始
		int parent=(index-1)/2;
		while(arr[index]>arr[parent]){
			swap1(arr,index,parent);
			index=parent;
			parent=(index-1)/2;
		}
	}

	public static void heapify(int[] arr,int index,int heapSize){
		int left=index*2+1;
		int right=0;
		int largest=0;
		while(left<heapSize){
			right=left+1;
			largest=right<heapSize&&arr[right]<arr[left]?right:left;
			largest=arr[index]>arr[largest]?largest:index;
			if(largest==index){
				break;
			}
			swap1(arr,largest,index);
			index=largest;
			left=2*index+1;
		}
	}

	//大根堆
	//依次插入，自顶向下
	public static void heapArray1(int[] arr){
		int len=arr.length;
		if(len<2){
			return;
		}
		for(int i=1;i<len;i++){
			heapInsert(arr,i);
		}
	}

	//小根堆
	//整个数组堆化，自底向上
	public static void heapArray2(int[] arr){
		int len=arr.length;
		if(len<2){
			return;
		}

		//自底向上堆化整个数组，调用heapify函数
		/*
		for(int i=len-1;i>=0;i--){
			heapify(arr,i,len);
		}
		*/
		
		int left=0;
		int right=0;
		int least=0;
		int parent=0;
		for(int i=len-1;i>=0;i--){
			parent=i;
			left=2*parent+1;
			while(left<len){
				right=left+1;
				least=left;
				if(right<len&&arr[right]<arr[left]){
					least=right;
				}
				if(arr[least]>arr[parent]){
					least=parent;
				}
				if(parent==least){
					break;
				}
				swap1(arr,parent,least);
				parent=least;
				left=2*parent+1;
			}
		}
	}

	//堆排序，从大到小
	public static void heapSort1(int[] arr){
		int len=arr.length;
		if(len<2){
			return;
		}
		heapArray2(arr);
		for(int i:arr){
			System.out.print(i+"	");
		}
		System.out.println();
		swap2(arr,0,len-1);

		for(int i=len-1;i>1;i--){
			heapify(arr,0,i);
			swap2(arr,0,i-1);
		}
	}
	
	public static void heapSort2(int[] arr){
		int len=arr.length;
		if(len<2){
			return;
		}
		heapArray2(arr);
		swap2(arr,0,len-1);
		for(int i=len-1;i>1;i--){
			heapify(arr,0,i);
			swap1(arr,0,i-1);
		}
	}

	//两个Edge 交换
	public static void swap3(Vector<Edge> edges,int i,int j){
		Edge edge=edges.get(i);
		edges.set(i,edges.get(j));
		edges.set(j,edge);
	}
	
	//构建小根堆
	public static void heapVectorEdges(Vector<Edge> vec){
		int size=vec.size();
		for(int i=size-1;i>=0;i--){
			int parent=i;
			int left=parent*2+1;
			int right=0;
			int least=0;
			while(left<size){
				right=left+1;
				least=right<size&&vec.get(right).weight<vec.get(left).weight?right:left;
				least=vec.get(least).weight>vec.get(parent).weight?parent:least;
				if(least==parent){
					break;
				}
				swap3(vec,least,parent);
				parent=least;
				left=parent*2+1;
			}
		}
	}

	//堆化
	public static void heapifyVectorEdges(Vector<Edge> vec,int index){
		int size=vec.size();
		int parent=index;
		int left=2*parent+1;
		int right=0;
		int least=0;
		while(left<size){
			right=left+1;
			least=left;
			if(right<size&&vec.get(least).weight>vec.get(right).weight){
				least=right;
			}
			if(vec.get(least).weight>vec.get(parent).weight){
				least=parent;
			}
			if(least==parent){
				break;
			}
			swap3(vec,least,parent);
			parent=least;
			left=2*parent+1;
		}
	}

	public static boolean isSameSet(HashMap<Node,Integer> nodeSetMap,Edge edge){
		return nodeSetMap.get(edge.from)==nodeSetMap.get(edge.to);
	}

	public static Edge pop(Vector<Edge> vec){
		Edge res=vec.get(0);
		int size=vec.size();
		swap3(vec,0,size-1);
		vec.remove(size-1);
		heapifyVectorEdges(vec,0);
		return res;
	}

	public static void processDiffSet(HashMap<Node,Integer> nodeSetMap,Edge edge){
		Node from=edge.from;
		int fromID=nodeSetMap.get(from);

		Node to=edge.to;
		int toID=nodeSetMap.get(to);

		for(Node node:nodeSetMap.keySet()){
			if(nodeSetMap.get(node)==toID){
				nodeSetMap.put(node,fromID);
			}
		}
	}

	//最小生成树算法：Kruskal算法
	public static List<Edge> minimumSpanningTreeKruskal(Graph graph){
		Vector<Edge> vec=new Vector<Edge>();
		for(Edge edge:graph.edges){
			vec.add(edge);
		}
		heapVectorEdges(vec);

		HashMap<Node,Integer> nodeSetMap=new HashMap<Node,Integer>();
		int count=0;
		for(Node node:graph.nodes.values()){
			nodeSetMap.put(node,count++);
		}

		List<Edge> result=new ArrayList<Edge>();

		Edge cur=null;
		while(!vec.isEmpty()){
			cur=pop(vec);
			if(!isSameSet(nodeSetMap,cur)){
				result.add(cur);
				processDiffSet(nodeSetMap,cur);
			}
		}
		return result;
	}
	

	//Prim 算法
	public static void heapifyVectorEdgesToUp(Vector<Edge> vec,int index){
		int parent=(index-1)/2;
		while(vec.get(index).weight<vec.get(parent).weight){
			swap3(vec,index,parent);
			index=parent;
			parent=(index-1)/2;
		}

	}

	public static void push(Vector<Edge> vec,Edge edge){
		if(vec.contains(edge)){
			return;
		}

		int size=vec.size();
		vec.add(edge);
		heapifyVectorEdgesToUp(vec,size);
	}

	//最小生成树算法：Prim算法
	public static List<Edge> minimumSpanningTreePrim(Graph graph){
		Set<Node> set=new HashSet<Node>();//保存已经尝试过的节点
		List<Edge> result=new ArrayList<Edge>();
		Vector<Edge> priorityVector=new Vector<Edge>();
		Edge cur=null;
		for(Node node:graph.nodes.values()){
			
			if(!set.contains(node)){
				set.add(node);
				for(Edge edge:node.edges){
					push(priorityVector,edge);
				}

				while(!priorityVector.isEmpty()){
					cur=pop(priorityVector);
					if(!set.contains(cur.to)){
						set.add(cur.to);
						result.add(cur);
						for(Edge edge:cur.to.edges){
							push(priorityVector,edge);
						}
					}
				}
			}
		}
		return result;
	}
	
	/*
	//只使用set的内存地址，其他什么都不用
	public static class Set{
		
	}

	//这个节约内存空间
	public static class MySets{
		public HashMap<Node,List<Node>> setMap;//顶点属于哪个集合
		public MySets(List<Node> nodes){
			for(Node node:nodes){
				setMap.put(node,new Set());
			}
		}
		
		//判断from和to是否在同一个集合
		public boolean isSameSet(Node from,Node to){
			return inMap.get(from)==inMap.get(to);
		}

		//from集合和to集合合并
		public void union(Node from,Node to){
			if(!isSameSet(from,to)){
				Set set=setMap.get(from);
				setMap.put(to,set);
			}  
		}

	}
	*/

	//这个类实现的功能没有并查集快
	public static class MySets{
		public HashMap<Node,List<Node>> setMap;//顶点属于哪个集合
		
		public MySets(List<Node> nodes){
			setMap=new HashMap<Node,List<Node>>();
			for(Node cur:nodes){
				List<Node> list=new ArrayList<Node>();
				list.add(cur);
				setMap.put(cur,list);
			}
		}
		
		//判断from和to是否在同一个集合中
		public boolean isSameSet(Node from,Node to){
			return setMap.get(from)==setMap.get(to);
		}

		public void union(Node from,Node to){
			List<Node> fromSet=setMap.get(from);
			List<Node> toSet=setMap.get(to);
			for(Node toNode:toSet){
				fromSet.add(toNode);
				setMap.put(toNode,fromSet);
			}
		}
	}

	
	public static Set<Edge> kruskalMST1(Graph graph){
		PriorityQueue<Edge> priorityQueue=new PriorityQueue<Edge>(new EdgeComparator());
		Set<Edge> result=new HashSet<Edge>();
		for(Edge edge:graph.edges){
			priorityQueue.offer(edge);
		}
		
		List<Node> list=new ArrayList<Node>();
		for(Node node:graph.nodes.values()){
			list.add(node);
		}
		MySets mySet=new MySets(list);


		int N=graph.nodes.size();
		int count=0;//边的个数
		Edge cur=null;
		while(!priorityQueue.isEmpty()){
			cur=priorityQueue.poll();
			
			//判断是否有环
			if(mySet.isSameSet(cur.from,cur.to)){
				continue;
			}
			
			mySet.union(cur.from,cur.to);
			result.add(cur);
			//判断是否结束，最小生成树有size个顶点，(size-1)条边
			count++;
			if(count==N-1){
				break;
			}
		}
		return result;
	}

	//左程云实现，Kruskal最小生成树算法
	public static Set<Edge> kruskalMST2(Graph graph){
		List<Node> list=new ArrayList<Node>();
		for(Node node:graph.nodes.values()){
			list.add(node);
		}
		MySets mySet=new MySets(list);
		PriorityQueue<Edge> priorityQueue=new PriorityQueue<Edge>(new EdgeComparator());
		for(Edge edge:graph.edges){
			priorityQueue.offer(edge);
		}	
		Set<Edge> result=new HashSet<Edge>();
		while(!priorityQueue.isEmpty()){
			Edge edge=priorityQueue.poll();
			if(!mySet.isSameSet(edge.from,edge.to)){
				result.add(edge);
				mySet.union(edge.from,edge.to);
			}
		}
		return result;
	}

	//左程云实现，primMST
	public static class EdgeComparator implements Comparator<Edge>{
		public int compare(Edge o1,Edge o2){
			return  o1.weight-o2.weight;
		}
	}


	public static Set<Edge> primMST1(Graph graph){
		//解锁的边进入小根堆
		PriorityQueue<Edge> priorityQueue=new PriorityQueue<Edge>(new EdgeComparator());

		HashSet<Node> set=new HashSet<Node>();
		Set<Edge> result=new HashSet<>();//依次挑选的边在result里
		for(Node node:graph.nodes.values()){
			//node是开始点
			set.add(node);
			for(Edge edge:node.edges){
				priorityQueue.offer(edge);
			}
			break;
		}
		int count=1;//统计点的个数
		int size=graph.nodes.size();
		Edge cur=null;
		while(count<size){
			cur=priorityQueue.poll();
			if(!set.contains(cur.to)){
				set.add(cur.to);
				result.add(cur);
				count++;
				for(Edge edge:cur.to.edges){
					priorityQueue.offer(edge);
				}
			}
		}
		return result;
	}

	//左程云实现，Prim最小生成树算法
	public static Set<Edge> primMST2(Graph graph){
		//节锁的边进入小根堆
		PriorityQueue<Edge> priorityQueue=new PriorityQueue<Edge>(new EdgeComparator());
		HashSet<Node> set=new HashSet<Node>();//考察过的点放在set中
		Set<Edge> result=new HashSet<Edge>();//依次挑选的边在result
		for(Node node:graph.nodes.values()){//这个for循环，处理森林，有很多棵树，各个生成最小生成树

			//node是开始点
			if(!set.contains(node)){//从任何一个点开始
				set.add(node);
				for(Edge edge:node.edges){//由一个点，解锁所有相连的边
					priorityQueue.offer(edge);
				}

				while(!priorityQueue.isEmpty()){
					Edge edge=priorityQueue.poll();//弹出解锁的边中，最小的边
					Node toNode=edge.to;//可能的一个新的点
					if(!set.contains(toNode)){//不含有的时候，就是新的点
						set.add(toNode);
						result.add(edge);
						for(Edge nextEdge:toNode.edges){//
							priorityQueue.offer(nextEdge);
						}
					}
				}
				//这个代码可能会把重复的边，重复放在解锁队列中，但是不影响结果，因为有一个!set.contains(toNode)判断
			}
		}
		return result;
	}

	//打印List<Edge>
	public static void printListEdge(List<Edge> edges){
		for(Edge edge:edges){
			System.out.println(edge.from.value+" ------> "+edge.to.value+" : "+edge.weight);
		}
		System.out.println("\n\n\n");
	}

	public static void printSetEdge(Set<Edge> edges){
		for(Edge edge:edges){
			System.out.println(edge.from.value+" ------> "+edge.to.value+" : "+edge.weight);
		}
		System.out.println("\n\n\n");
	}

	//自己实现，Dijkstra算法
	public static class NodeDistance{
		public Node node;
		public int dis;
		public NodeDistance(Node node,int d){
			this.node=node;
			this.dis=d;
		}
	}

	public static class NodeDistanceComparator implements Comparator<NodeDistance>{
		public int compare(NodeDistance o1,NodeDistance o2){
			return o1.dis-o2.dis;
		}
	}

	public static int findWeight(ArrayList<Edge> edges,Node to){
		int dis=Integer.MAX_VALUE;
		for(Edge edge:edges){
			if(edge.to==to){
				dis=edge.weight;
				break;
			}
		}
		return dis;
	}

	public static NodeDistance findMinDis(List<NodeDistance> list){
		int len=list.size();
		NodeDistance res=list.get(0);
		NodeDistance cur=null;
		for(int i=1;i<len;i++){
			cur=list.get(i);
			if(cur.dis<res.dis){
				res=cur;
			}
		}	
		return res;
	}

	//不使用堆
	public static Set<NodeDistance> dijkstral1(Graph graph,Node source){
			
		PriorityQueue<NodeDistance> priorityQueue=new PriorityQueue<NodeDistance>();
		Set<NodeDistance> result=new HashSet<NodeDistance>();
		List<NodeDistance> unused=new ArrayList<NodeDistance>();
			
		//result.add(new NodeDistance(source,0));
		int dis=0;
		for(Node node:graph.nodes.values()){
			dis=Integer.MAX_VALUE;
			if(source==node){
				continue;
			}
			if(source.nexts.contains(node)){
				dis=findWeight(source.edges,node);
			}
			unused.add(new NodeDistance(node,dis));
		}	
		
		NodeDistance disMin=null;
		while(!unused.isEmpty()){
			disMin=findMinDis(unused);
			result.add(disMin);
			unused.remove(unused.indexOf(disMin));

			//修改值
			for(NodeDistance nodeDis:unused){
				if(disMin.node.nexts.contains(nodeDis.node)){
					int weight=findWeight(disMin.node.edges,nodeDis.node);
					if(disMin.dis+weight<nodeDis.dis){
						nodeDis.dis=disMin.dis+weight;
					}
				}
			}
		}	
		return result;	
	}

	//使用堆
	public static void swap4(Vector<NodeDistance> vec,int i,int j){
		NodeDistance nodeDis=vec.get(i);
		vec.set(i,vec.get(j));
		vec.set(j,nodeDis);
	}
	//向上
	public static void heapInsertNodeDistance(Vector<NodeDistance> vec,int index){
		int parent=(index-1)/2;
		while(vec.get(index).dis<vec.get(parent).dis){
			swap4(vec,index,parent);
			index=parent;
			parent=(index-1)/2;
		}
	}
	//向下
	public static void heapifyNodeDistance(Vector<NodeDistance> vec,int index){
		int size=vec.size();
		int parent=index;
		int left=2*parent+1;
		int right=0;
		int least=0;
		while(left<size){
			right=left+1;
			least=right<size&&vec.get(right).dis<vec.get(left).dis?right:left;
			//错误写法：least=right<size&&vec.get(left).dis<vec.get(right).dis?left:right;
			least=vec.get(parent).dis<vec.get(least).dis?parent:least;
			if(least==parent){
				break;
			}
			swap4(vec,least,parent);
			parent=least;
			left=2*parent+1;
		}
	}
	public static void heapifyNodeDistanceVector(Vector<NodeDistance> vec){
		int size=vec.size();
		int parent=0;
		int left=0;
		int right=0;
		int least=0;
		for(int i=size-1;i>=0;i--){
			parent=i;
			left=2*parent+1;
			right=left+1;
			while(left<size){
				least=right+1<size&&vec.get(left).dis<vec.get(right).dis?left:right;
				least=vec.get(parent).dis<vec.get(least).dis?parent:least;
				if(least==parent){
					break;
				}
				swap4(vec,least,parent);
				parent=least;
				left=2*parent+1;
				right=left+1;
			}
		}
	}
	public static NodeDistance popNodeDistance(Vector<NodeDistance> vec){
		NodeDistance res=vec.get(0);
		int size=vec.size();
		swap4(vec,0,size-1);
		vec.remove(size-1);
		heapifyNodeDistance(vec,0);
		return res;
	}
	public static void changePriorityVectorNode(Vector<NodeDistance> priorityVector,NodeDistance base){
		int size=priorityVector.size();
		int weight=0;
		NodeDistance cur=null;
		for(int i=0;i<size;i++){
			cur=priorityVector.get(i);
			if(base.node.nexts.contains(cur.node)){
				weight=findWeight(base.node.edges,cur.node);
				if(base.dis+weight<cur.dis){
					cur.dis=base.dis+weight;
					heapInsertNodeDistance(priorityVector,i);
				}
			}
		}
	}

	public static Set<NodeDistance> dijkstral2(Graph graph,Node source){
		Vector<NodeDistance> priorityVector=new Vector<NodeDistance>();
		Set<NodeDistance> result=new HashSet<NodeDistance>();
		int dis=Integer.MAX_VALUE;
		for(Node node:graph.nodes.values()){
			dis=Integer.MAX_VALUE;
			if(source==node){
				continue;
			}
			if(source.nexts.contains(node)){
				dis=findWeight(source.edges,node);
			}
			priorityVector.add(new NodeDistance(node,dis));
		}
		heapifyNodeDistanceVector(priorityVector);

		NodeDistance minDis=null;
		while(!priorityVector.isEmpty()){
			minDis=popNodeDistance(priorityVector);	
			result.add(minDis);
			changePriorityVectorNode(priorityVector,minDis);
		}
		return result;
	}

	//左程云实现，Dijkstra算法
	public static HashMap<Node,Integer> dijkstra3(Node head){
		//从head触发到所有点的最小距离
		//key：从head出发到达key
		//value：从head出发到达key的最小距离
		//如果在表中，没有T的记录，含义是从head出发到TT这个点的距离为正无穷

		HashMap<Node,Integer> distanceMap=new HashMap<Node,Integer>();
		distanceMap.put(head,0);
		
		//已经求过距离的节点，存在selectNodes中，以后再也不碰
		HashSet<Node> selectedNodes=new HashSet<Node>();
		Node minNode=getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
		while(minNode!=null){
			int distance=distanceMap.get(minNode);
			for(Edge edge:minNode.edges){
				Node toNode=edge.to;
				if(!distanceMap.containsKey(toNode)){
					distanceMap.put(toNode,distance+edge.weight);
				}
				distanceMap.put(toNode,Math.min(distance+edge.weight,distanceMap.get(toNode)));
			}
			selectedNodes.add(minNode);
			minNode=getMinDistanceAndUnselectedNode(distanceMap,selectedNodes);
		}
		return distanceMap;
	}

	public static Node getMinDistanceAndUnselectedNode(HashMap<Node,Integer> distanceMap,HashSet<Node> touchedNodes){
		Node minNode=null;
		int minDistance=Integer.MAX_VALUE;
		for(Entry<Node,Integer> entry:distanceMap.entrySet()){
			Node node=entry.getKey();
			int distance=entry.getValue();
			if(!touchedNodes.contains(node)&&distance<minDistance){
				minNode=node;
				minDistance=distance;
			}
		}
		return minNode;
	}

	//自己发挥
	public static Node getMinDistanceAndUnselectedNode1(HashMap<Node,Integer> distanceMap,HashSet<Node> selectedNodes){
		int distance=Integer.MAX_VALUE;
		int tmp=0;
		Node res=null;
		for(Node node:distanceMap.keySet()){
			if(!selectedNodes.contains(node)){
				tmp=distanceMap.get(node);
				if(tmp<distance){
					res=node;
					distance=tmp;
				}
			}
		}
		return res;
	}



	public static void printNodeDistance(Set<NodeDistance> set){
		for(NodeDistance node:set){
			System.out.println(node.node.value+" : "+node.dis);
		}
		System.out.println("\n\n\n");
	}

	public static void main(String[] args){
		Integer[][] matrix={
			{1,2,1},
			{1,3,1},
			{2,6,1},
			{3,6,1},
			{6,7,1},
			{6,8,1},
			{3,4,1},
			{4,5,1},
			{3,2,1}
		};
		Graph graph=GraphGenerator.createGraph(matrix);
		Node source=graph.nodes.get(1);
		bfs(source);
		System.out.println("\n\n");
		dfs(source);
		System.out.println("\n\n");
		
		System.out.println("自己实现");
		travel(graph);
		
		System.out.println("\n\n 下面时拓扑排序");
		topologicalSort(graph);

		int[] arr={1,34,4,67,23,46};
		heapSort1(arr);
		for(int i:arr){
			System.out.print(i+"	");
		}
		System.out.println();
		System.out.println("\n\n");

		List<Edge> list=minimumSpanningTreeKruskal(graph);
		printListEdge(list);

		list=minimumSpanningTreePrim(graph);
		printListEdge(list);
		System.out.println("\n\n\n");

		Set<Edge> set=kruskalMST1(graph);
		printSetEdge(set);

		set=kruskalMST2(graph);
		printSetEdge(set);

		set=primMST1(graph);
		printSetEdge(set);

		set=primMST2(graph);
		printSetEdge(set);

		Set<NodeDistance> nodeDistanceSet=dijkstral1(graph,source);
		printNodeDistance(nodeDistanceSet);
		
		nodeDistanceSet=dijkstral2(graph,source);
		printNodeDistance(nodeDistanceSet);


		System.out.println("Hello World");
	}

		

}	

//将上述结构转换为邻接矩阵
class GraphGenerator{
	//matrix所有的边
	//N*3的矩阵
	//[from节点上的值，to节点上的值，weight]
	public static Graph createGraph(Integer[][] matrix){
		Graph graph=new Graph();
		int len=matrix.length;
		for(int i=0;i<len;i++){
			Integer from=matrix[i][0];
			Integer to=matrix[i][1];
			Integer weight=matrix[i][2];

			if(!graph.nodes.containsKey(from)){
				graph.nodes.put(from,new Node(from));
			}
			if(!graph.nodes.containsKey(to)){
				graph.nodes.put(to,new Node(to));
			}

			Node fromNode=graph.nodes.get(from);
			Node toNode=graph.nodes.get(to);
			Edge newEdge=new Edge(weight,fromNode,toNode);
			fromNode.nexts.add(toNode);
			fromNode.edges.add(newEdge);
			fromNode.out++;
			toNode.in++;
			graph.edges.add(newEdge);
		}
		return graph;
	}


}





























