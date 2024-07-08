/*************************************************************************
    > File Name: question2.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 29 09:20:20 2024
 ************************************************************************/

import java.util.HashMap;
import java.util.Map;

public class question2{

	public static int getMaxXorSum(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
			
		int len=arr.length;

		return process(arr,0,len-1);
	}

	public static int process(int[] arr,int start,int end){		
		int res=0;
		for(int i=start;i<=end;++i){
			res^=arr[i];
		}
		res=res==0?1:0;

		for(int i=start;i<end;i++){
			int left=process(arr,start,i);
			int right=process(arr,i+1,end);
			res=Math.max(left+right,res);
		}

		return res;
	}
	

	public static int getMaxXorSumDP(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		int[][] dp=new int[len][len];
		for(int i=0;i<len;i++){
			dp[i][i]=arr[i]==0?1:0;
		}
		
		for(int i=len-2;i>=0;--i){
			for(int j=i;j<len;++j){
				int res=0;
				for(int k=i;k<=j;++k){
					res^=arr[k];
				}
				dp[i][j]=res==0?1:0;
				
				for(int k=i;k<j;++k){
					dp[i][j]=Math.max(dp[i][j],dp[i][k]+dp[k+1][j]);
				}
			}
		}
		
		return dp[0][len-1];
	}

	
	public static int getMaxXorSumDP1(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
		
		int len=arr.length;
		int[] dp=new int[len];
		dp[0]=arr[0]==0?1:0;
		
		for(int i=1;i<len;i++){
			int xorsum=0;
			int k=i;
			do{
				xorsum^=arr[k];
				--k;
			}while(k>=0&&xorsum!=0);
			
			dp[i]=xorsum==0?1:0;
			if(k>=0){
				dp[i]+=dp[k];
			}

			dp[i]=Math.max(dp[i],dp[i-1]);
		}
		
		return dp[len-1];
	}
	
	//左程云实现
	public static int mostEOR1(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		int[] dp=new int[len];
		HashMap<Integer,Integer>map=new HashMap<>();
		map.put(0,-1);
		
		int xorsum=0;
		for(int i=0;i<len;++i){
			xorsum^=arr[i];
			
			if(map.containsKey(xorsum)){
				int pre=map.get(xorsum);
				dp[i]=1+(pre==-1?0:dp[pre]);
			}
			dp[i]=Math.max(dp[i],i-1>=0?dp[i-1]:0);
			map.put(xorsum,i);
		}

		return dp[len-1];
	}
	
	public static int mostEOR(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
		int len=arr.length;
		
		//dp[i] -> arr[0...i] 在最优划分的情况下，异或和为0最多的部分是多少个
		int[] dp=new int[len];

		//key：从0出发的某个前缀异或和
		//value：这个前缀异或和出现的最晚位置（index）
		HashMap<Integer,Integer> map=new HashMap<>();
		map.put(0,-1);
		
		int xor=0;
		for(int i=0;i<len;i++){//i所在的最后一块
			xor^=arr[i];// xor -> 0...i所有数的异或和
			if(map.containsKey(xor)){//上一次，这个异或和出现的位置
				//pre -> pre+1 -> i，最优划分，最后一个部分的开始位置
				// (pre+1,i)最后一个部分
				int pre=map.get(xor);
				dp[i]=pre==-1?1:(dp[pre]+1);
			}

			//dp[i]=Max{dp[i-1],dp[k-1]+1}
			if(i>0){
				dp[i]=Math.max(dp[i-1],dp[i]);
			}
			
			map.put(xor,i);
		}

		return dp[len-1];
	}




	public static void main(String[] args){
		int[] arr={
			3,2,1,0,1,0,2,0,3,2,1,0,4,0
		};

		System.out.println(getMaxXorSum(arr));
		System.out.println(getMaxXorSumDP(arr));
		System.out.println(getMaxXorSumDP1(arr));
		System.out.println(mostEOR1(arr));
		System.out.println(mostEOR(arr));

		System.out.println("\n\n\n");
	}
}




