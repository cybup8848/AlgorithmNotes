/*************************************************************************
    > File Name: question28.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jul  2 19:36:47 2024
 ************************************************************************/

import java.util.TreeMap;

public class question28{

	//自己实现
	//暴力递归
	public static int maxScore1(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}
		
		int len=arr.length;
		boolean[] flags=new boolean[len];//false表示没有被打爆，true表示被打爆

		return process1(arr,flags);
	}

	public static int process1(int[] arr,boolean[] flags){
		
		int max=0;
		int res=0;
		int len=arr.length;
		for(int i=0;i<len;i++){
			if(flags[i]){
				continue;
			}

			flags[i]=true;
			int left=findLeft(arr,i,flags);
			int right=findRight(arr,i,flags);
			res=left*arr[i]*right+process1(arr,flags);
			max=Math.max(res,max);
			flags[i]=false;
		}

		return max;
	}
	
	public static int findLeft(int[] arr,int index,boolean[] flags){
		int res=1;
		for(int i=index-1;i>=0;i--){
			if(flags[i]==false){
				res=arr[i];
				break;
			}
		}
		return res;
	}

	public static int findRight(int[] arr,int index,boolean[] flags){
		int res=1;
		int len=arr.length;
		for(int i=index+1;i<len;i++){
			if(flags[i]==false){
				res=arr[i];
				break;
			}
		}
		return res;
	}

	//自己实现
	//暴力尝试，利用有序表进行加速
	public static int maxScore2(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}
		
		int len=arr.length;

		TreeMap<Integer,Integer> treeMap=new TreeMap<>();
		for(int i=0;i<len;i++){
			treeMap.put(i,arr[i]);
		}

		return process2(arr,treeMap);
	}

	public static int process2(int[] arr,TreeMap<Integer,Integer> treeMap){
		
		int max=0;
		int res=0;
		int len=arr.length;
		for(int i=0;i<len;i++){
			if(!treeMap.containsKey(i)){
				continue;
			}

			treeMap.remove(i);

			Integer left=treeMap.lowerKey(i);
			left=left==null?1:arr[left];
			
			Integer right=treeMap.higherKey(i);
			right=right==null?1:arr[right];

			res=left*arr[i]*right+process2(arr,treeMap);
			
			max=Math.max(res,max);

			treeMap.put(i,arr[i]);
		}

		return max;
	}

	//左程云实现
	public static int maxScore3(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}

		int len=arr.length;
		int[] help=new int[len+2];//构建一个辅助数组，减少边界条件
		for(int i=1;i<=len;i++){
			help[i]=arr[i-1];
		}
		help[0]=help[len+1]=1;

		return process3(help,1,len);
	}

	//打爆arr[L...R]范围上的所有气球，返回最大的分数
	//假设 arr[L-1] 和 arr[R+1] 一定没有被打爆
	//尝试的方式：每一个位置的气球最后被打爆
	public static int process3(int[] arr,int L,int R){
		if(L==R){//如果arr[L...R] 范围上只有一个踢球，直接打爆即可
			return arr[L-1]*arr[L]*arr[R+1];
		}
		
		//最后打爆arr[L]的方案，和最后打爆arr[R] 的方案，比较一下
		int max=Math.max(arr[L-1]*arr[L]*arr[R+1]+process3(arr,L+1,R),
				arr[L-1]*arr[R]*arr[R+1]+process3(arr,L,R-1));

		//尝试中间位置的气球最后被打爆的每一种方案
		for(int i=L+1;i<R;i++){
			max=Math.max(max,arr[L-1]*arr[i]*arr[R+1]+process3(arr,L,i-1)+process3(arr,i+1,R));
			//有枚举行为，但是经过分析，没有办法进行优化
		}
		
		return max;
	}

	//动态规划
	public static int maxScore4(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
		
		int len=arr.length;
		int[] help=new int[len+2];
		for(int i=1;i<=len;i++){
			help[i]=arr[i-1];
		}
		help[0]=help[len+1]=1;

		return process4(help,1,len);
	}

	public static int process4(int[] arr,int s,int e){
		int len=e-s+1;
		int[][] dp=new int[len+2][len+2];
		for(int i=1;i<len+1;i++){
			dp[i][i]=arr[i-1]*arr[i]*arr[i+1];
		}

		for(int i=len-1;i>0;i--){
			
			for(int j=i+1;j<len+1;j++){
				int max=Math.max(arr[i-1]*arr[i]*arr[j+1]+dp[i+1][j],arr[i-1]*arr[j]*arr[j+1]+dp[i][j-1]);
				
				for(int k=i+1;k<j;k++){
					max=Math.max(max,arr[i-1]*arr[i]*arr[j+1]+dp[i][k-1]+dp[k+1][j]);
				}
				
				dp[i][j]=max;
			}
		}

		return dp[1][len];
	}




	public static void main(String[] args){
		int[] arr={
			3,2,5
		};

		System.out.println(maxScore1(arr));
		System.out.println(maxScore2(arr));
		System.out.println(maxScore3(arr));	
		System.out.println(maxScore4(arr));

		System.out.println("\n\n\n测试TreeMap：");
		TreeMap<Integer,Integer> treeMap=new TreeMap<>();
		for(int i=0;i<arr.length;i++){
			treeMap.put(i,arr[i]);
		}

		Integer res=treeMap.higherKey(1);
		System.out.println(res*10+"	"+treeMap.lowerKey(1)+"	"+treeMap.higherKey(2));
		System.out.println(treeMap.lowerKey(0));
		if(res==null){
			System.out.println("null");
		}
		
		res=treeMap.ceilingKey(1);
		System.out.println(res+"	"+treeMap.floorKey(1));



		System.out.println("hello world");
	}
}





