/*************************************************************************
    > File Name: question48.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 22 14:58:46 2024
 ************************************************************************/



public class question48{
	
	private static int min=Integer.MAX_VALUE;

	public static int minCC(int x,int y,int z,int start,int end){
		if(start>=end){
			return 0;
		}
		
		process(x,y,z,start,end,0);

		return min;
	}

	//这道题主要考察边界问题
	public static void process(int x,int y,int z,int start,int end,int coins){
		if(start>end){
			process(x,y,z,start-2,end,coins+z);
		} else if(start==end){
			min=Math.min(min,coins);
		} else {
			process(x,y,z,start+2,end,coins+x);
			process(x,y,z,start*2,end,coins+y);
		}
	}


	//初始的尝试版本
	//点赞，人气+2
	//送礼，人气*2
	//私聊，人气-2
	public static int coins(int start,int end,int x,int y,int z){
		return process1(start,end,x,y,z);
	}

	//递归跑不完
	public static int process1(int start,int end,int dianC,int songC,int siC){
		if(start==end){
			return 0;
		}

		int p1=process1(start+2,end,dianC,songC,siC)+dianC;
		int p2=process1(start*2,end,dianC,songC,siC)+songC;
		int p3=process1(start-2,end,dianC,songC,siC)+siC;

		return Math.min(p1,Math.min(p2,p3));
	}

	//preMoney之前已经花了多少前，可变
	//end目标，不可变
	//int add,int times,int del     固定
	//start 当前来到的人气
	//limitAim 人气大到什么成都不需要再尝试了，固定
	//limitCoin已经使用的硬币大到什么程度不需要再尝试了，固定
	//返回最小硬币数
	
	//start偶数，end偶数，start<=end
	public static int minCoins1(int add,int times,int del,int start,int end){
		if(start>end){
			return -1;
		}

		return process2(0,end,add,times,del,start,end*2,((end-start)/2)*add);
	}

	public static int process2(int preMoney,int end,int add,int times,int del,int start,int limitAim,int limitCoin){
		if(preMoney>limitCoin){
			return Integer.MAX_VALUE;
		}

		if(start<0){
			return Integer.MAX_VALUE;
		}

		if(start>limitAim){
			return Integer.MAX_VALUE;
		}

		if(end==start){
			return preMoney;
		}

		int min=Integer.MAX_VALUE;

		//让人气+2的方式
		int p1=process2(preMoney+add,end,add,times,del,start+2,limitAim,limitCoin);
		if(p1!=Integer.MAX_VALUE){
			min=p1;
		}
		
		//让人气*2的方式
		int p2=process2(preMoney+times,end,add,times,del,start*2,limitAim,limitCoin);
		if(p2!=Integer.MAX_VALUE){
			min=Math.min(min,p2);
		}

		//让人气-2的方式
		int p3=process2(preMoney+del,end,add,times,del,start-2,limitAim,limitCoin);
		if(p3!=Integer.MAX_VALUE){
			min=Math.min(min,p3);
		}
		
		return min;
	}

	//动态规划版本
	//完全没看懂，按照套路改成了动态规划

	public static int minCoins2(int add,int times,int del,int start,int end){
		if(start>end){
			return -1;
		}
		
		int limitCoin=(end-start)/2*add;
		int limitAim=end*2;
		int[][] dp=new int[limitCoin+1][limitAim+1];
		for(int pre=0;pre<=limitCoin;++pre){
			for(int aim=0;aim<=limitAim;++aim){
				if(aim==end){
					dp[pre][aim]=pre;//所需的最少花费
				} else {
					dp[pre][aim]=Integer.MAX_VALUE;
				}
			}
		}

		//将最少花费一直往上传递
		for(int pre=limitCoin;pre>=0;--pre){
			for(int aim=0;aim<=limitAim;aim++){
				//add
				if(aim+2<=limitAim&&pre+add<=limitCoin){
					dp[pre][aim]=Math.min(dp[pre][aim],dp[pre+add][aim+2]);
				}

				//del
				if(aim-2>=0&&pre+del<=limitCoin){
					dp[pre][aim]=Math.min(dp[pre][aim],dp[pre+del][aim-2]);
				}

				//times
				if(aim*2<=limitAim&&pre+times<=limitCoin){
					dp[pre][aim]=Math.min(dp[pre][aim],dp[pre+times][aim*2]);
				}
			}
		}
		

		return dp[0][start];
	}





	public static void main(String[] args){
		int x=3;
		int y=100;
		int z=1;
		int start=2;
		int end=20;

		System.out.println(minCoins1(x,y,z,start,end));
		System.out.println(minCC(x,y,z,start,end));
		System.out.println(minCoins2(x,y,z,start,end));
		
		//end必须为偶数
		//System.out.println(minCC(x,y,z,start,end));
		
		//System.out.println(coins(start,end,x,y,z));
		

		System.out.println("hello world");
	}
}



