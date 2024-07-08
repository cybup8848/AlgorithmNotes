/*************************************************************************
    > File Name: question8.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 12 20:19:22 2024
 ************************************************************************/

import java.util.HashMap;
import java.util.Map;



public class question8{
	
	public static int getMaxLength1(int[] arr,int k){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		int max=0;
		int sum=0;

		HashMap<Integer,Integer> map=new HashMap<>();//key为前缀和，value为前缀和对应的下标
		for(int i=0;i<len;i++){
			sum+=arr[i];
			if(map.containsKey(sum-k)){
				max=Math.max(max,i-map.get(sum-k));
			}
			if(!map.containsKey(sum)){
				map.put(sum,i);
			}
		}

		return max;
	}
	
	//自己实现
	public static int getMaxLength(int[] arr,int k){
		if(arr==null||arr.length==0){
			return 0;
		}
		
		int len=arr.length;
		int[] minSum=new int[len];
		int[] minSumEnd=new int[len];
		
		//计算两个辅助数组：minSum、minSumEnd
		minSum[len-1]=arr[len-1];
		minSumEnd[len-1]=len-1;
		for(int i=len-2;i>=0;--i){
			minSum[i]=arr[i];
			minSumEnd[i]=i;
			if(minSum[i+1]<=0){
				minSum[i]+=minSum[i+1];
				minSumEnd[i]=minSumEnd[i+1];
			}
		}
		
		//下面往右扩
		int l=0;
		int r=0;
		int sum=0;
		int res=0;
		while(r<len){
			while(sum+minSum[r]<=k){
				sum+=minSum[r];
				r=minSumEnd[r]+1;
				res=Math.max(res,r-l);
				if(r==len){
					break;
				}
			}

			if(l==r){
				l=r=r+1;
			} else {
				sum-=arr[l++];
			}
		}

		return res;
	}

	//左程云实现
	public static int maxLengthAwesome(int[] arr,int k){
		if(arr==null||arr.length==0){
			return 0;
		}

		int len=arr.length;
		int[] minSums=new int[len];
		int[] minSumEnds=new int[len];
		minSums[len-1]=arr[len-1];
		minSumEnds[len-1]=len-1;

		for(int i=len-2;i>=0;--i){
			if(minSums[i+1]<=0){
				minSums[i]=arr[i]+minSums[i+1];
				minSumEnds[i]=minSumEnds[i+1];
			} else {
				minSums[i]=arr[i];
				minSumEnds[i]=i;
			}
		}

		int end=0;
		int sum=0;
		int res=0;
		// i是窗口的最左的位置，end是窗口最右位置的下一个位置（种植位置）
		for(int i=0;i<len;i++){
			//while循环结束之后：
			//（1）如果以i开头的情况下，
			//		累加和<=k的最长子数组是arr[i..end-1]看看这个子数组长度能不能更新res
			//（2）如果以i开头的情况下，
			//		累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果
			while(end<len&&sum+minSums[end]<=k){
				sum+=minSums[end];
				end=minSumEnds[end]+1;
			}

			res=Math.max(res,end-i);
			if(end>i){//窗口内还有数
				sum-=arr[i];
			} else {//窗口内已经没有数了，说明i开头的所有子数组累加和都不可能<=k
				end=i+1;
			}
		}
		
		return res;
	}


	



	
	public static void main(String[] args){
		int[] arr={
			-1,2,4,45,23,2,3,354,0,24,2,3,34,5,6,0,-2,-3,-5,6,7,3
		};
		int k=35;
		System.out.println(getMaxLength1(arr,k));
		System.out.println(getMaxLength(arr,k));
		System.out.println(maxLengthAwesome(arr,k));

		System.out.println("hello world");
	}

}



