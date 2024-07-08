/*************************************************************************
    > File Name: GreedyAlgorithm.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Jan 29 14:09:39 2024
 ************************************************************************/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Scanner;

public class GreedyAlgorithm{
	//一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
	//给你每一个项目开始的时间和结束的时间（给你一个数组，里面是一个个具体的项目），
	//你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。返回这个最多的宣讲场次。
	public static class Project{
		public String name;
		public int start;
		public int end;
		public Project(String name,int start,int end){
			this.name=name;
			this.start=start;
			this.end=end;
		}
	}
	
	public static class ProjectComparator implements Comparator<Project>{
		@Override
		public int compare(Project o1,Project o2){
			return o1.end-o2.end;
		}
	}

	//自己实现
	public static List<Project> scheduleProjects(Project[] projects,int timePoint){
		int len=projects.length;
		if(len<1){
			return null;
		}
		PriorityQueue<Project> priorityQueue=new PriorityQueue<Project>(new ProjectComparator());
		List<Project> list=new LinkedList<Project>();
		for(int i=0;i<len;i++){
			priorityQueue.offer(projects[i]);
		}

		Project cur=null;
		while(!priorityQueue.isEmpty()){
			cur=priorityQueue.poll();
			if(cur.start<timePoint){
				continue;
			}
			timePoint=cur.end;
			list.add(cur);
		}
		return list;
	}

	//左程云实现
	//贪心策略，代码都非常短。
	public static class Program{
		public int start;
		public int end;
		public Program(int start,int end){
			this.start=start;
			this.end=end;
		}
	}

	public static class ProgramComparator implements Comparator<Program>{
		@Override
		public int compare(Program o1,Program o2){
			return o1.end-o2.end;
		}
	}

	public static int bestArrange(Program[] programs,int timePoint){//timePoint：开始时间点
		Arrays.sort(programs,new ProgramComparator());
		int result=0;
		//从左往右依次遍历所有的会议  
		for(int i=0;i<programs.length;i++){
			if(timePoint<=programs[i].start){
				result++;
				timePoint=programs[i].end;
			}
		}
		return result;
	}
	
	public static class StringComparator implements Comparator<String>{
		@Override
		public int compare(String o1,String o2){
			return (o1+o2).compareTo(o2+o1);
		}
	}
	//自己实现
	public static String bestConcat(String[] strs){
		if(strs==null||strs.length==0){
			return "";
		}
		Arrays.sort(strs,new StringComparator());
		String res=new String();
		int len=strs.length;
		for(int i=0;i<len;i++){
			res+=strs[i];
		}
		return res;
	}

	//左程云实现
	public static class MyComparator implements Comparator<String>{
		@Override
		public int compare(String a,String b){
			return (a+b).compareTo(b+a);
		}
	}
	public static String lowestString(String[] strs){
		if(strs==null||strs.length==1){
			return "";
		}
		Arrays.sort(strs,new MyComparator());
		String res="";
		for(int i=0;i<strs.length;i++){
			res+=strs[i];
		}
		return res;
	}
	
	//自己实现
	public static class MoneyComparator implements Comparator<Integer>{
		@Override
		public int compare(Integer o1,Integer o2){
			return o2-o1;
		}
	}
	public static int leastMoney(Integer[] golds){
		if(golds==null||golds.length<2){
			return 0;
		}
		Arrays.sort(golds,new MoneyComparator());
		int subSum=0;
		int tmp=0;
		int len=golds.length;
		for(int i=0;i<len-2;i++){
			tmp+=golds[i];
			subSum+=tmp;
		}
		tmp+=golds[len-1]+golds[len-2];
		return (len-1)*tmp-subSum;
	}

	//左程云实现
	//对于贪心策略，堆和排序是最常用的两个技巧
	public static int lessMoney(Integer[] arr){
		PriorityQueue<Integer> pQ=new PriorityQueue<Integer>();
		int len=arr.length;
		for(int i=0;i<len;i++){
			pQ.offer(arr[i]);
		}

		int sum=0;
		int cur=0;
		while(pQ.size()>1){
			cur=pQ.poll()+pQ.poll();
			sum+=cur;
			pQ.add(cur);
		}
		return sum;
	}
	
	//对数器
	public static Integer[] generateRandomArray(int maxValue,int maxSize){
		int len=(int)(Math.random()*(maxSize+1));
		Integer[] res=new Integer[len];
		for(int i=0;i<len;i++){
			res[i]=(int)(Math.random()*(maxValue+1));
				//-(int)(Math.random()*maxValue);
		}
		return res;
	}

	public static Integer[] copyArray(Integer[] arr){
		if(arr==null){
			return null;
		}
		int len=arr.length;
		Integer[] res=new Integer[len];
		for(int i=0;i<len;i++){
			res[i]=arr[i];
		}
		return res;
	}

	public static class CostProfit{
		public int cost;
		public int profit;
		public CostProfit(int cost,int profit){
			this.cost=cost;
			this.profit=profit;
		}
	}

	public static class CostComparator implements Comparator<CostProfit>{
		@Override
		public int compare(CostProfit o1,CostProfit o2){
			return o1.cost-o2.cost;
		}
	}
	public static class ProfitComparator implements Comparator<CostProfit>{
		@Override
		public int compare(CostProfit o1,CostProfit o2){
			return o2.profit-o1.profit;
		}
	}
	//自己实现，根据左程云的思想
	public static int mostProfits(int[] costs,int[] profits,int k,int m){
		PriorityQueue<CostProfit> costsPriorityQueue=new PriorityQueue<CostProfit>(new CostComparator());
		PriorityQueue<CostProfit> profitsPriorityQueue=new PriorityQueue<CostProfit>(new ProfitComparator());
		int len=costs.length;
		for(int i=0;i<len;i++){
			costsPriorityQueue.offer(new CostProfit(costs[i],profits[i]));
		}

		CostProfit costProfit=null;
		while(k>0){
			while(!costsPriorityQueue.isEmpty()){
				costProfit=costsPriorityQueue.peek();
				if(costProfit.cost>m){
					break;
				}
				costsPriorityQueue.poll();
				profitsPriorityQueue.offer(costProfit);
			}
			
			if(profitsPriorityQueue.isEmpty()){
				return m;
			}
			costProfit=profitsPriorityQueue.poll();
			m+=costProfit.profit;
			k--;
		}
		return m;
	}

	//左程云实现
	public static class Node{
		public int p;
		public int c;
		public Node(int p,int c){
			this.p=p;
			this.c=c;
		}
	}
	public static class MinCostComparator implements Comparator<Node>{
		@Override
		public int compare(Node o1,Node o2){
			return o1.c-o2.c;
		}
	}
	public static class MaxProfitComparator implements Comparator<Node>{
		@Override
		public int compare(Node o1,Node o2){
			return o2.p-o1.p;
		}
	}
	public static int findMaximizedCapital(int k,int W,int[] Profits,int[] Capital){
		PriorityQueue<Node> minCostQ=new PriorityQueue<Node>(new MinCostComparator());
		PriorityQueue<Node> maxProfitQ=new PriorityQueue<Node>(new MaxProfitComparator());
		for(int i=0;i<Profits.length;i++){
			minCostQ.add(new Node(Profits[i],Capital[i]));
		}
		for(int i=0;i<k;i++){//进行k轮
			//能力所及的项目，全解锁
			while(!minCostQ.isEmpty()&&minCostQ.peek().c<=W){
				maxProfitQ.add(minCostQ.poll());
			}
			if(maxProfitQ.isEmpty()){//如果没有能做的项目了，资金不够
				return W;
			}
			W+=maxProfitQ.poll().p;
		}
		return W;
	}

	//一个数据流中，随时可以取得中位数
	//自己实现
	public static class IntegerComparator implements Comparator<Integer>{
		@Override
		public int compare(Integer o1,Integer o2){
			return o2-o1;
		}
	}
	public static void getMedian(){
		java.util.Scanner scanner=new java.util.Scanner(System.in);
		
		PriorityQueue<Integer> minPriorityQueue=new PriorityQueue<Integer>();
		PriorityQueue<Integer> maxPriorityQueue=new PriorityQueue<Integer>(new IntegerComparator());
		int temp=0;
		int median=0;
		int size1=minPriorityQueue.size();
		int size2=maxPriorityQueue.size();
		while(true){
			temp=scanner.nextInt();
			//压入大根堆
			if(size1>0&&temp>=minPriorityQueue.peek()){
				minPriorityQueue.offer(temp);
				size1++;
			} else {
				maxPriorityQueue.offer(temp);
				size2++;
			}

			//调整大根堆、小根堆的数据个数
			if(size1-size2>1){
				maxPriorityQueue.offer(minPriorityQueue.poll());
				size1--;
				size2++;
			} else if(size2-size1>1){
				minPriorityQueue.offer(maxPriorityQueue.poll());
				size1++;
				size2--;
			}

			//获取中位数
			if(size1>size2){
				System.out.println(minPriorityQueue.peek());
			} else if(size1<size2){
				System.out.println(maxPriorityQueue.size());
			} else{
				System.out.println((minPriorityQueue.peek()+maxPriorityQueue.peek()+0.0)/2);
			}
		}
	}

	//N皇后问题
	/*
	public static boolean isSameRow(int[][] chessBoard,int row){
		int len=chessBoard[0].length;
		for(int i=0;i<len;i++){
			if(chessBoard[row][i]==1){
				return false;
			}
		}
		return true;
	}
	*/
	//相同行不需要检测，因为每一行只能有1个皇后，所以我们可以在参数中进行设置行数

	//检测是否同列
	public static boolean isSameCol(int[][] chessBoard,int col,int N){
		for(int i=0;i<N;i++){
			if(chessBoard[i][col]==1){
				return false;
			}
		}
		return true;
	}

	//检测是否同斜对角线
	public static boolean isSameDiag(int[][] chessBoard,int row,int col,int N){
		//左上方
		for(int r=row-1,c=col-1;r>=0&&c>=0;r--,c--){
			if(chessBoard[r][c]==1){
				return false;
			}
		}

		//右上方
		for(int r=row-1,c=col+1;r>=0&&c<N;r--,c++){
			if(chessBoard[r][c]==1){
				return false;
			}
		}

		return true;
	}
	
	public static int NQueenRecur(int[][] chessBoard,int row,int N){
		if(row==N){
			return 1;
		}
		int sum=0;
		for(int i=0;i<N;i++){
			if(!isSameCol(chessBoard,i,N)||!isSameDiag(chessBoard,row,i,N)){
				continue;
			}
			chessBoard[row][i]=1;
			sum+=NQueenRecur(chessBoard,row+1,N);
			chessBoard[row][i]=0;
		}
		return sum;
	}
	
	//左程云实现
	public static int num1(int n){
		if(n<1){
			return 0;
		}
		int[] record=new int[n];// record[i]  ---> i行的皇后，放在了第几列
		return process1(0,record,n);
	}
	
	//潜台词：record[0...i-1]的皇后，任何两个皇后一定都不共行、不共列、不共斜线
	//目前来到了第i行
	//record[0...i-1]表示之前的行，放了的皇后位置
	//n代表整体一共有多少行
	//返回值是，摆完所有的皇后，合理的摆法有多少种
	public static int process1(int i,int[] record,int n){
		if(i==n){//终止行
			return 1;
		}
		int res=0;
		for(int j=0;j<n;j++){
			//当前i行的皇后，放在j列，会不会和之前(0...i-1)的皇后，共行共列或者共斜线
			//如果是，认为有效
			//如果不是，认为无效
			if(isValid(record,i,j)){
				record[i]=j;
				res+=process1(i+1,record,n);
			}
		}
		return res;
	}
	
	//record[0...i-1]，你需要看，record[i...]不需要看
	//返回i行皇后，放在了j列，是否有效
	public static boolean isValid(int[] record,int i,int j){
		// j==record[k]：检查不同列
		// Math.abs(record[k]-j)==Math.abs(i-k)：列坐标绝对值与行坐标相减绝对值 是否相等，说明共斜线。利用了斜率
		for(int k=0;k<i;k++){// 之前的某个k行的皇后 
			if(j==record[k] || Math.abs(record[k]-j)==Math.abs(i-k)){
				return false;
			}
		}
		return true;
	}

	//请不要超过32皇后问题
	//优化后的方法，优化常数时间
	public static int num2(int n){
		if(n<1 || n>32){
			return 0;
		}
		int limit=n==32?-1:(1<<n)-1;//生成一个二进制数，后n位是1
									//8皇后：0 1111 1111
									//9皇后：0 1111 1111 1
									//16皇后：0 1111 1111 1111 1111
									//不代表任何信息，只使用位欣喜
		return process2(limit,0,0,0);//limit表示可以在哪些位置尝试皇后
	}

	//colLim 列的限制，1的位置不能放皇后，0的位置可以
	//leftDiaLim 左斜线的限制，1的位置不能放皇后，0的位置可以
	//rightDiaLim 右斜线的限制，1的位置不能放皇后，0的位置可以
	public static int process2(int limit,int colLim,int leftDiaLim,int rightDiaLim){
		//来到第几行填皇后的时候，检查皇后合法这件事怎么做的更快一些
		
		if(colLim==limit){//列上的限制等于limit，所有皇后都填上
			return 1;
		}

		int pos=0;
		int mostRightOne=0;
		pos=limit & (~(colLim | leftDiaLim | rightDiaLim));
		//（colLim | leftDaiLim | rightDiaLim）是总的限制，1代表不可以放皇后，0代表可以放皇后
		//取反以后，1表示可以放皇后，0不可以放皇后
		//所有可以填皇后的信息都在pos这个位信息中
		//所有候选皇后的位置，都在pos上
		int res=0;

		//利用位运算的特性代替record检查
		while(pos!=0){
			mostRightOne=pos & (~pos+1);//提取候选皇后最右侧的1
			pos=pos-mostRightOne;//将试的皇后的位置剪掉，再去试下一个
			res+=process2(limit,
					colLim|mostRightOne,//列的限制
					(leftDiaLim | mostRightOne)<<1,//左斜线限制
					(rightDiaLim | mostRightOne)>>1);//右斜线限制
		}
		return res;
	}

	//改进后的dijkstra算法
	//从head出发，所能到达的节点，生成到达每个结点的最小路径记录并返回
	//修改后的dijkstra算法，使用堆NodeHeap
	public static HashMap<Node,Integer> dijkstra2(Node head,int size){
		NodeHeap nodeHeap=new NodeHeap(size);
		nodeHeap.addOrUpdateOrIgnore(head,0);
		HashMap<Node,Integer> result=new HashMap<Node,Integer>();
		while(!nodeHeap.isEmpty()){
			NodeRecord record=nodeHeap.pop();
			Node cur=record.node;
			int distance=record.distance;
			for(Edge edge:cur.edges){
				nodeHeap.addOrUpdateOrIgnore(edge.to,edge.weight+distance);
			}
		}
		return result;
	}

	public static class NodeRecord{
		public Node node;
		public int distance;
		public NodeRecord(Node node,int distance){
			this.node=node;
			this.distance=distance;
		}
	}

	public static class NodeHeap{
		private Node[] nodes;//用数组表示堆
		private HashMap<Node,Integer> heapIndexMap;//查询一个节点在堆的什么位置
		private HashMap<Node,Integer> distanceMap;//节点与距离的映射
		private int size;//堆有多少个节点

		public NodeHeap(int size){
			nodes=new Node[size];
			heapIndexMap=new HashMap<Node,Integer>();
			distanceMap=new HashMap<Node,Integer>();
			this.size=0;
		}

		public boolean isEmpty(){
			return this.size==0;
		}

		public void addOrUpdateOrIgnore(Node node,int distance){
			if(inHeap(node)){
				distanceMap.put(node,Math.min(distanceMap.get(node),distance));
				insertHeapify(node,heapIndexMap.get(node));
			}
			if(!isEntered(node)){
				nodes[size]=node;
				heapIndexMap.put(node,size);
				distanceMap.put(node,distance);
				insertHeapify(node,size++);
			}
		}

		public NodeRecord pop(){
			NodeRecord nodeRecord=new NodeRecord(nodes[0],distanceMap.get(nodes[0]));
			swap(0,size-1);
			heapIndexMap.put(nodes[size-1],-1);
			distanceMap.remove(nodes[size-1]);
			nodes[size-1]=null;
			heapify(0,--size);
			return nodeRecord;
		}
		
		private void insertHeapify(Node node,int index){
			while(distanceMap.get(nodes[index])<distanceMap.get(nodes[(index-1)/2])){
				swap(index,(index-1)/2);
				index=(index-1)/2;
			}
		}

		private void heapify(int index,int size){
			int left=index*2+1;
			while(left<size){
				int smallest=left+1<size&&distanceMap.get(nodes[left+1])<distanceMap.get(nodes[left])?left+1:left;
				smallest=distanceMap.get(nodes[smallest])<distanceMap.get(nodes[index])?smallest:index;
				if(smallest==index){
					break;
				}
				swap(index,smallest);
				index=smallest;
				left=index*2+1;
			}
		}
		
		public boolean isEntered(Node node){//node有没有进来过
			return heapIndexMap.containsKey(node);
		}

		private boolean inHeap(Node node){
			return isEntered(node) && heapIndexMap.get(node)!=-1;//进来过，但是被弹出，标记为-1
		}

		private void swap(int index1,int index2){
			heapIndexMap.put(nodes[index1],index2);
			heapIndexMap.put(nodes[index2],index1);
			Node tmp=nodes[index1];
			nodes[index1]=nodes[index2];
			nodes[index2]=tmp;
		}

	}

	
		
	public static void main(String[] args){
		int maxValue=100;
		int maxSize=100;
		int testTime=500000;
		boolean flag=true;
		for(int i=0;i<testTime;i++){
			Integer[] arr1=generateRandomArray(maxValue,maxSize);
			Integer[] arr2=copyArray(arr1);
			int res1=leastMoney(arr1);
			int res2=lessMoney(arr2);
			System.out.println(res1+"	"+res2);
			if(res1!=res2){
				flag=false;
				break;
			}
		}
		System.out.println(flag?"right":"wrong");
		
		//getMedian();

		//N黄后问题
		int[] Ns={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		for(int i=0;i<Ns.length;i++){
			int N=Ns[i];
			int[][] chessBoard=new int[N][N];
			System.out.println(N+"："+NQueenRecur(chessBoard,0,N));
		}


		
		System.out.println("Hello World");
	}
}
