/************************************************************************
    > File Name: DP.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Mar  8 09:15:54 2024
 ************************************************************************/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DP{
	//机器人问题
	//自己实现
	//索引从0开始
	public static int robort(int s,int e,int k,int N){
		if((k==0)&&(s==e)){
			return 1;
		}
		if((k==0)&&(s!=e)){
			return 0;
		}

		int left=0;
		if(s!=0){
			left=robort(s-1,e,k-1,N);
		}
		int right=0;
		if(s!=N-1){
			right=robort(s+1,e,k-1,N);
		}
		return left+right;
	}

	//左程云实现
	public static int walkWays1(int N,int E,int S,int K){
		return f1(N,E,K,S);
	}

	//一共是1~N这么多位置
	//最终的目标是P
	//当前再cur位置
	//还剩rest步
	//返回方法数
	//索引从1开始
	public static int f1(int N,int E,int rest,int cur){
		if(rest==0){
			return cur==E?1:0;
		}
		if(cur==1){
			return f1(N,E,rest-1,2);
		}
		if(cur==N){
			return f1(N,E,rest-1,N-1);
		}
		return f1(N,E,rest-1,cur-1)+f1(N,E,rest-1,cur+1);
	}
	

	//记忆搜索
	public static int walkWays2(int N,int E,int S,int K){
		int[][] dp=new int[K+1][N+1];//   第一个可变参数的范围[0,K]
									 //   第二个可变参数的范围[1,N]
		for(int i=0;i<=K;i++){
			for(int j=1;j<=N;j++){
				dp[i][j]=-1;
			}
		}
		return f2(N,E,K,S,dp);
	}
	public static int f2(int N,int E,int rest,int cur,int[][] dp){
		if(dp[rest][cur]!=-1){//如果之前的状态计算算过，就已记录了，直接返回
			return dp[rest][cur];
		}

		//缓存没命中
		if(rest==0){
			dp[rest][cur]=(cur==E?1:0);
			return dp[rest][cur];
		}
		
		int sum=0;
		if(cur==1){
			sum=f2(N,E,rest-1,2,dp);
		} else if(cur==N){
			sum=f2(N,E,rest-1,N-1,dp);
		} else {
			sum=f2(N,E,rest-1,cur-1,dp)+f2(N,E,rest-1,cur+1,dp);
		}
		dp[rest][cur]=sum;
		return sum;
	}


	//严格表结构
	//自己实现
	public static int walkWays3(int N,int E,int S,int K){
		int[][] dp=new int[K+1][N+1];//   [0,K]   [1,N]
		dp[0][S]=1;
		for(int i=1;i<=K;i++){
			dp[i][1]=dp[i-1][2];
			dp[i][N]=dp[i-1][N-1];
			for(int j=2;j<N;j++){
				dp[i][j]=dp[i-1][j-1]+dp[i-1][j+1];
			}
		}
		return dp[K][E];
	}

	//左程云实现有问题
	public static int walkWays4(int N,int E,int S,int K){
		int[][] dp=new int[K+1][N+1];
		dp[0][E]=1;
		for(int i=1;i<=K;i++){
			dp[i][1]=dp[i-1][2];
			dp[i][N]=dp[i-1][N-1];
			for(int j=2;j<N;j++){
				dp[i][j]=dp[i-1][j-1]+dp[i-1][j+1];
			}
		}
		return dp[K][E];
	}

	
	//最少硬币问题
	public static int coin1(int[] coins,int sum){
		if(coins==null||coins.length<1||sum<0){
			return -1;
		}
		boolean[] flags=new boolean[coins.length];
		
		int count=exchangeCoins1(coins,sum,flags);
		return count>coins.length?-1:count;
	}
	public static int exchangeCoins1(int[] coins,int sum,boolean[] flags){
		if(sum<0){
			return coins.length+2;
		} else if(sum==0){
			return 0;
		}
		int count=0;
		int min=coins.length+2;
		for(int i=0;i<coins.length;i++){
			if(coins[i]>sum||flags[i]==true){
				continue;
			}
			flags[i]=true;
			count=1+exchangeCoins1(coins,sum-coins[i],flags);
			min=Math.min(min,count);
			flags[i]=false;
		}
		return min;
	}

	public static boolean isExist(int[] arr,int j){
		if(arr==null||arr.length<1){
			return false;
		}
		return arr[j]==1?true:false;
	}

	public static void copyArr(int[] dest,int[] src){
		if(dest==null||src==null){
			return;
		}
		int len1=dest.length;
		int len2=src.length;
		if(len1!=len2){
			return;
		}

		for(int i=0;i<len1;i++){
			dest[i]=src[i];
		}
	}
	//这个问题有两个可变参数：aim、零钱数组中剩余的零钱
	public static int exchangeCoins2(int[] coins,int sum){
		if(coins==null||coins.length<1||sum<0){
			return -1;
		}

		int[] dp=new int[sum+1];//  0~sum

		int len=coins.length;
		int[][] flags=new int[sum+1][len];//0~sum：剩余的钱币 
		
		for(int i=0;i<=sum;i++){
			int minPreIndex=-1;//i=coins[addIndex]+dp[minPreIndex]
			int addIndex=-1;//准备加入的索引
			int count=0;//硬币个数
			int preCount=Integer.MAX_VALUE;
			for(int j=0;j<len;j++){
				int preIndex=i-coins[j];

				//不存在
				if(preIndex<0){
					continue;
				}

				//表示preIndex钱币，不能替换成功
				if((preIndex>0)&&(dp[preIndex]==0)){
					continue;
				}

				//如果j已经被占用
				if(isExist(flags[preIndex],j)){
					continue;
				}
				
				count=1+dp[preIndex];
				if(count<preCount){
					minPreIndex=preIndex;
					preCount=count;
					addIndex=j;
				}
			}
			if(preCount!=Integer.MAX_VALUE){
				copyArr(flags[i],flags[minPreIndex]);
				dp[i]=preCount;
				flags[i][addIndex]=1;
			}
		}
		return dp[sum];
	}
	
	//左程云实现
	public static int minCoins1(int[] arr,int aim){
		return process1(arr,0,aim);
	}
	//arr[index...] 组成处rest这么多钱，最少的硬币数量返回
	//感觉不太对，返回-1可能会影响硬币个数
	public static int process1(int[] arr,int index,int rest){
		if(rest<0){
			return -1;
		}
		if(rest==0){
			return 0;
		}

		//rest>0
		if(index==arr.length){
			return -1;
		}
		
		//rest>0，并且 也有硬币
		int p1=process1(arr,index+1,rest);
		int p2Next=process1(arr,index+1,rest-arr[index]);
		if(p1==-1 && p2Next==-1){
			return -1;//无效解
		} else {
			//只返回有效解
			if(p1==-1){
				return p2Next+1;
			} 
			if(p2Next==-1){
				return p1;
			}

			//都是有效解
			return Math.min(p1,p2Next+1);
		}
	}

	
	//记忆搜索
	public static int minCoins2(int[] arr,int aim){
		int[][] dp=new int[arr.length][aim+1];
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<=aim;j++){
				dp[i][j]=-2;
			}
		}

		return process2(arr,0,aim,dp);
	}
	public static int process2(int[] arr,int index,int rest,int[][] dp){
		if(rest<0){
			return -1;
		}
		if(rest==0){
			return 0;
		}
		if(index==arr.length){
			return -1;
		}
		if(dp[index][rest]!=-2){
			return dp[index][rest];
		}

		int p1=process2(arr,index+1,rest,dp);
		int p2=process2(arr,index+1,rest-arr[index],dp);
		if(p1==-1&&p2==-1){
			dp[index][rest]=-1;
		} else {
			if(p1==-1){
				dp[index][rest]=p2+1;
			} else if(p2==-1){
				dp[index][rest]=p1;
			} else {
				dp[index][rest]=Math.min(p1,p2+1);
			}
		}
		return dp[index][rest];
	}

	//左程云实现
	public static int minCoins3(int[] arr,int aim){
		int len=arr.length;
		int[][] dp=new int[len+1][aim+1];//index可以取到arr.length(根据递归程序)
		for(int i=0;i<=len;i++){
			for(int j=0;j<=aim;j++){
				dp[i][j]=-2;
			}
		}
		return process3(arr,0,aim,dp);
	}	
	public static int process3(int[] arr,int index,int rest,int[][] dp){
		if(rest<0){
			return -1;
		}
		if(dp[index][rest]!=-2){
			return dp[index][rest];
		}
		if(rest==0){
			dp[index][rest]=0;
			return 0;
		} else if(index==arr.length){
			dp[index][rest]=-1;
			return -1;
		}

		//rest>0，并且也有硬币
		int p1=process3(arr,index+1,rest,dp);
		int p2=process3(arr,index+1,rest-arr[index],dp);
		int res=0;
		if(p1==-1&&p2==-1){
			res=-1;
		} else if(p1==-1){
			res=p2+1;
		} else if(p2==-1){
			res=p1;
		} else {
			res=Math.min(p1,p2+1);
		}
		dp[index][rest]=res;
		return res;
	}

	//严格表结构的动态规划
	//自底向上，将递归结构转换为动态规划结构
	public static int dpMinCoins4(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<0){
			return -1;
		}

		int len=arr.length;
		//自动初始化为0
		int[][] dp=new int[len+1][aim+1];//有两个可变参数，索引index，表示来到第几块硬币；rest：剩余的需要兑换的钱币
		for(int i=1;i<=aim;i++){
			dp[len][i]=-1;
		}

		for(int i=len-1;i>=0;i--){
			int has=-1;
			for(int rest=1;rest<=aim;rest++){
				has=rest-arr[i];
				if(has<0){
					dp[i][rest]=dp[i+1][rest];
					continue;
				}
				if(dp[i+1][has]==-1&&dp[i+1][rest]==-1){
					dp[i][rest]=-1;
				} else if(dp[i+1][has]==-1){
					dp[i][rest]=dp[i+1][rest];
				} else if(dp[i+1][rest]==-1){
					dp[i][rest]=1+dp[i+1][has];
				} else {
					dp[i][rest]=Math.min(dp[i+1][rest],1+dp[i+1][has]);
				}
			}
		}
		return dp[0][aim];
	}
	
	//左程云实现
	public static int dpMinCoins5(int[] arr,int aim){
		int N=arr.length;
		int[][] dp=new int[N+1][aim+1];

		for(int row=0;row<=N;row++){
			dp[row][0]=0;
		}

		for(int col=1;col<=aim;col++){
			dp[N][col]=-1;
		}

		for(int index=N-1;index>=0;index--){
			for(int rest=1;rest<=aim;rest++){
				int p1=dp[index+1][rest];
				int p2Next=-1;
				if(rest-arr[index]>=0){
					p2Next=dp[index+1][rest-arr[index]];
				}
				if(p1==-1&&p2Next==-1){
					dp[index][rest]=-1;
				} else {
					if(p1==-1){
						dp[index][rest]=p2Next+1;
					} else if(p2Next==-1){
						dp[index][rest]=p1;
					} else {
						dp[index][rest]=Math.min(p1,p2Next+1);
					}
				}
			}
		}
		return dp[0][aim];
	}

	//绝顶聪明
	//
	//暴力递归
	public static int getWinScore1(int[] arr){
		return Math.max(first(arr,0,arr.length-1),second(arr,0,arr.length-1));
	}

	//先手函数
	//当前该你拿，arr[start...end]
	//返回你的最好分数
	public static int first(int[] arr,int start,int end){
		if(start==end){
			return arr[start];
		}
		return Math.max(arr[start]+second(arr,start+1,end),arr[end]+second(arr,start,end-1));
	}
	
	//后手函数
	//当前不该你拿，是对方在arr[start...end]范围上拿
	//返回你的最好分数
	//这是对手选完之后，你被迫接受的
	public static int second(int[] arr,int start,int end){
		if(start==end){
			return 0;
		}
		return Math.min(first(arr,start+1,end),first(arr,start,end-1));
		//对手会给你最小的。数组中元素都是正数，零和博弈，对手拿的多，你就拿的少；对手拿的少，你就拿的多
		//对手一定会给你最差的
	}
	
	//严格表结构，只有两个可变参数，start、end，arr是固定参数
	//f参数可变参数
	public static int getWinScore2(int[] arr){
		if(arr==null||arr.length<1){
			return -1;
		}
		int len=arr.length;//
						   //
		//第一步：分析参数变化范围
		int[][] first=new int[len][len];//能够装得下first函数，first也是两个可变参数，变化范围[0,len-1]
		int[][] second=new int[len][len];//能够装得下second函数，second函数也是两个可变参数，变化范围[0,len-1]

		//第二步：标记需要计算的终止状态，确定返回值，都是first[0][len-1]    second[0][len-1]
		
		//第三步：标出不用计算，直接出答案，base case，if(start==end) return arr[start]
		for(int i=0;i<len;i++){
			first[i][i]=arr[i];
			second[i][i]=0;
		}

		//第四步：分析普遍位置如何依赖，推出依赖关系
		
		//第五步：根据依赖关系和最终状态，确定计算顺序
		
		// i 永远不可能大于 j，这是一个范围尝试，左下半区无效
		for(int i=1;i<len;i++){
			for(int j=0;j<len;j++){
				if(j+i>=len){
					continue;
				}
				first[j][j+i]=Math.max(arr[j]+second[j+1][j+i],arr[j+i]+second[j][j+i-1]);
				second[j][j+i]=Math.min(first[j+1][j+i],first[j][j+i-1]);
			}
		}
		return Math.max(first[0][len-1],second[0][len-1]);
	}

	//左程云实现
	public static int getWinScore3(int[] arr){
		if(arr==null||arr.length<1){
			return -1;
		}

		int len=arr.length;
		int[][] f=new int[len][len];
		int[][] s=new int[len][len];
		
		for(int i=0;i<len;i++){
			f[i][i]=arr[i];
			s[i][i]=0;
		}

		int row=0;
		int col=1;
		//对角线开始位置row行 col列
		while(col<len){
			int i=row;
			int j=col;
			while(i<len&&j<len){
				f[i][j]=Math.max(arr[i]+s[i+1][j],arr[j]+s[i][j-1]);
				s[i][j]=Math.min(f[i+1][j],f[i][j-1]);
				i++;
				j++;
			}
			col++;
		}
		return Math.max(f[0][len-1],s[0][len-1]);
	}

	//记忆搜索
	public static int getWinScore4(int[] arr){
		if(arr==null||arr.length<1){
			return 0;
		}

		int len=arr.length;
		int[][] first=new int[len][len];
		int[][] second=new int[len][len];
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				first[i][j]=-1;
				second[i][j]=-1;
			}
		}
		return Math.max(first3(arr,0,len-1,first,second),second3(arr,0,len-1,first,second));
	}

	public static int first3(int[] arr,int start,int end,int[][] first,int[][] second){
		if(first[start][end]!=-1){
			return first[start][end];
		}

		if(start==end){
			first[start][end]=arr[start];
			return arr[start];
		}
		first[start][end]=Math.max(arr[start]+second3(arr,start+1,end,first,second),arr[end]+second3(arr,start,end-1,first,second));
		return first[start][end];
	}

	public static int second3(int[] arr,int start,int end,int[][] first,int[][] second){
		if(second[start][end]!=-1){
			return second[start][end];
		}

		if(start==end){
			second[start][end]=0;
			return 0;
		}
		second[start][end]=Math.min(first3(arr,start+1,end,first,second),first3(arr,start,end-1,first,second));
		return second[start][end];
	}	
	

	public static void main(String[] args){
		//绝顶聪明
		int[] arr={3,100,2,50};
		System.out.println(getWinScore1(arr));
		System.out.println(getWinScore2(arr));
		System.out.println(getWinScore3(arr));
		System.out.println(getWinScore4(arr));
		System.out.println("\n\n\n");


		int s=1;
		int e=5;
		int k=10;
		int N=20;
		System.out.println(robort(s,e,k,N));	
		System.out.println(walkWays1(N,e+1,s+1,k));
		System.out.println(walkWays2(N,e+1,s+1,k));
		System.out.println(walkWays3(N,e+1,s+1,k));
		System.out.println(walkWays4(N,e+1,s+1,k));
		//System.out.println(dpWay(s,e,k,N));

		s=1;
		e=3;
		k=4;
		N=5;
		System.out.println(robort(s,e,k,N));
		System.out.println(walkWays1(N,e+1,s+1,k));
		System.out.println(walkWays2(N,e+1,s+1,k));
		System.out.println(walkWays3(N,e+1,s+1,k));
		System.out.println(walkWays4(N,e+1,s+1,k));

		//零钱兑换问题
		System.out.println("\n\n\n零钱兑换问题");
		int[] coins={2,7,3,5,3};
		int aim=10;
		System.out.println(coin1(coins,aim));
		System.out.println(exchangeCoins2(coins,aim));
		System.out.println(minCoins1(coins,aim));
		System.out.println(minCoins2(coins,aim));
		System.out.println(minCoins3(coins,aim));
		System.out.println(dpMinCoins4(coins,aim));
		System.out.println(dpMinCoins5(coins,aim));

		int[] coins1={2,3,100,200};
		int aim1=5;
		System.out.println(minCoins1(coins1,aim1));
		System.out.println(minCoins2(coins1,aim1));
		System.out.println(minCoins3(coins1,aim1));
		System.out.println(dpMinCoins4(coins1,aim1));
		System.out.println(dpMinCoins5(coins1,aim1));
	}
}


