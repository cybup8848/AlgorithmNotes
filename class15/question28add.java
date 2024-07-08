/*************************************************************************
    > File Name: question28add.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 14 20:45:19 2024
 ************************************************************************/


import java.util.Comparator;
import java.util.PriorityQueue;

public class question28add{
	
	private static int[] drinks1=null;

	private static int minDrinks1=Integer.MAX_VALUE;
	
	public static int minTime1(int[] arr,int n,int a,int b){
		int len=arr.length;
		drinks1=new int[n];
		int[] tmpDrinks=new int[n];

		int[] timePoints=new int[len];
		computeDrinks(arr,timePoints,n,tmpDrinks,0,a,b);//暴力计算每个客人喝咖啡的时间，并求最小值
		for(int i=0;i<n;i++){
			System.out.print(drinks1[i]+"	");
		}
		System.out.println();

		return minDrinks1;
	}

	private static void computeDrinks(int[] arr,int[] timePoints,int n,int[] tmpDrinks,int index,int a,int b){
		if(index==n){
			int tmp=process1(tmpDrinks,a,b,0,0);
			if(minDrinks1>tmp){
				minDrinks1=tmp;
				int cn=0;
				for(int x:tmpDrinks){
					drinks1[cn++]=x;
				}
			}
			return;
		}

		int len=arr.length;
		for(int i=0;i<len;i++){
			timePoints[i]+=arr[i];
			tmpDrinks[index]=timePoints[i];
			computeDrinks(arr,timePoints,n,tmpDrinks,index+1,a,b);
			timePoints[i]-=arr[i];
		}

	}
	
	private static int maxTime(int[] arr){
		int max=arr[0];
		for(int i=1;i<arr.length;i++){
			max=Math.max(max,arr[i]);
		}
		return max;
	}
	
	public static int process1(int[] drinks,int a,int b,int washStart,int index){
		if(index==drinks.length-1){
			return Math.min(Math.max(washStart,drinks[index])+a,drinks[index]+b);
		}

		//wash是我当前的咖啡杯，洗完的时间
		int wash=Math.max(washStart,drinks[index])+a;
		int next1=process1(drinks,a,b,wash,index+1);
		int p1=Math.max(wash,next1);

		int dry=drinks[index]+b;
		int next2=process1(drinks,a,b,washStart,index+1);
		int p2=Math.max(dry,next2);

		return Math.min(p1,p2);
	}







	public static class Machine{
		public int timePoint;
		public int workTime;

		public Machine(){
			this.timePoint=0;
			this.workTime=0;
		}

		public Machine(int time,int work){
			this.timePoint=time;
			this.workTime=work;
		}
	}

	public static class MachineComparator implements Comparator<Machine>{
		@Override
		public int compare(Machine o1,Machine o2){
			return (o1.timePoint+o1.workTime)-(o2.timePoint+o2.workTime);
		}
	}

	//方法2：每个人暴力尝试用每一个咖啡机给自己做咖啡，优化成贪心
	public static int minTime2(int[] arr,int n,int a,int b){
		PriorityQueue<Machine> heap=new PriorityQueue<>(new MachineComparator());
		int len=arr.length;
		for(int i=0;i<len;i++){
			heap.add(new Machine(0,arr[i]));
		}

		int[] drinks=new int[n];

		Machine cur=null;
		for(int i=0;i<n;i++){
			cur=heap.poll();
			cur.timePoint+=cur.workTime;
			drinks[i]=cur.timePoint;
			System.out.print(cur.timePoint+"	");
			heap.offer(cur);
		}
		System.out.println();

		return process(drinks,a,b,0,0);
	}

	//假设洗咖啡的机器，在washLine的时间才有空。a洗咖啡的机器洗一杯的时间，b咖啡被自然挥发的时间
	//如果要洗碗drinks[index...N-1]，返回最早完成所有事情的时间点
	//方法2：洗咖啡杯的方式与原来一样，只是这个暴力版本减少了一个可变参数
	public static int process(int[] drinks,int a,int b,int index,int washLine){
		if(index==drinks.length-1){
			return Math.min(Math.max(washLine,drinks[index])+a,drinks[index]+b);
		}

		//wash是我当前的咖啡杯，洗完的时间
		int wash=Math.max(washLine,drinks[index])+a;
		int next1=process(drinks,a,b,index+1,wash);//洗完剩下所有的咖啡杯最早的结束时间
		int p1=Math.max(wash,next1);//需要做完所有事情，所以包括我洗完的时间，和剩下咖啡杯洗完的最早时间

		//决定当前杯子决定挥发
		int dry=drinks[index]+b;
		int next2=process(drinks,a,b,index+1,washLine);
		int p2=Math.max(dry,next2);

		return Math.min(p1,p2);
	}







	//方法3：最终版本，把方法二洗咖啡杯的暴力尝试进一步优化成动态规划
	//两个参数：index、washline，所以是一张二维表
	public static int minTime3(int[] arr,int n,int a,int b){
		PriorityQueue<Machine> heap=new PriorityQueue<>(new MachineComparator());
		int len=arr.length;
		for(int i=0;i<len;i++){
			heap.add(new Machine(0,arr[i]));
		}

		int[] drinks=new int[n];

		Machine cur=null;
		for(int i=0;i<n;i++){
			cur=heap.poll();
			cur.timePoint+=cur.workTime;
			drinks[i]=cur.timePoint;
			heap.offer(cur);
		}

		return processDP(drinks,a,b);
	}	
	
	public static int processDP(int[] drinks,int a,int b){
		int len=drinks.length;

		if(a>=b){
			return drinks[len-1]+b;
		}
		
		int[][] dp=new int[len][drinks[len-1]+len*a];
		for(int i=0;i<dp[0].length;i++){
			dp[len-1][i]=Math.min(Math.max(i,drinks[len-1])+a,drinks[len-1]+b);
		}

		for(int row=len-2;row>=0;--row){//row 咖啡杯的编号
			int washLine=drinks[row]+(row+1)*a;
			for(int col=0;col<washLine;col++){
				int wash=Math.max(col,drinks[row])+a;
				dp[row][col]=Math.min(Math.max(wash,dp[row+1][wash]),Math.max(drinks[row]+b,dp[row+1][col]));

			}
		}
		
		return dp[0][0];
	}
	






	public static int minTime4(int[] arr,int n,int a,int b){
		PriorityQueue<Machine> heap=new PriorityQueue<>(new MachineComparator());
		int len=arr.length;
		for(int i=0;i<len;i++){
			heap.add(new Machine(0,arr[i]));
		}

		int[] drinks=new int[n];

		Machine cur=null;
		for(int i=0;i<n;i++){
			cur=heap.poll();
			cur.timePoint+=cur.workTime;
			drinks[i]=cur.timePoint;
			heap.offer(cur);
		}

		return processDP1(drinks,a,b);
	}

	public static int processDP1(int[] drinks,int a,int b){
		if(drinks==null){
			return 0;
		}

		//index变化范围：[0,N-1]
		int len=drinks.length;

		//washLine变化范围：所有咖啡杯都放到机器中去洗
		int washLine=0;
		for(int i=0;i<len;i++){
			washLine=Math.max(washLine,drinks[i])+a;
		}

		int[][] dp=new int[len][washLine];
		for(int j=0;j<washLine;++j){
			dp[len-1][j]=Math.min(Math.max(j,drinks[len-1])+a,drinks[len-1]+b);
		}

		for(int i=len-2;i>=0;--i){
			for(int j=0;j<washLine-a;++j){
				int wash=Math.max(j,drinks[i])+a;
				int next1=dp[i+1][wash];
				int p1=Math.max(wash,next1);

				int dry=drinks[i]+b;
				int next2=dp[i+1][j];
				int p2=Math.max(dry,next2);
				
				dp[i][j]=Math.min(p1,p2);
			}
		}
		
		
		return dp[0][0];
	}
	
	public static void main(String[] args){

		int[] arr={
			3,2,7
		};
		int a=5;
		int b=8;
		int n=10;
		
		System.out.println(minTime1(arr,n,a,b));

		System.out.println(minTime2(arr,n,a,b));
		
		System.out.println(minTime3(arr,n,a,b));
		System.out.println(minTime4(arr,n,a,b));
		System.out.println("\n\n\n");



	
		int[] arr1={
			3,2,5,6,1
		};
		int a1=10;
		int b1=15;
		int n1=15;
		//System.out.println(minTime1(arr1,n1,a1,b1));
		System.out.println(minTime2(arr1,n1,a1,b1));
		System.out.println(minTime3(arr1,n1,a1,b1));
		System.out.println(minTime4(arr1,n1,a1,b1));
		System.out.println("\n\n\n");
		



		int[] arr2={
			3,2,10
		};
		int a2=2;
		int b2=4;
		int n2=5;
		System.out.println(minTime1(arr2,n2,a2,b2));
		System.out.println(minTime2(arr2,n2,a2,b2));
		System.out.println(minTime3(arr2,n2,a2,b2));
		//System.out.println(minTime4(arr2,n2,a2,b2));

		System.out.println("\n\n\n");


		System.out.println("hello world");
	}
}
