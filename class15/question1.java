/*************************************************************************
    > File Name: question1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May  6 10:48:44 2024
 ************************************************************************/

import java.util.Queue;
import java.util.LinkedList;

import java.util.Deque;

public class question1{


	//题目1：滑动窗口
	//给定一个有序数组arr，代表数轴上从左到右右n个节点arr[0]、arr[1]、...、arr[n-1]，给定一个正数L，
	//代表一根长度为L的绳子，求绳子最多能覆盖其中的几个点，
	
	//自己实现
	public static int getMaxCoverPoints1(int[] arr,int L){
		int start=arr[0];
		int idx=1;
		int len=arr.length;

		Queue<Integer> queue=new LinkedList<>();
		queue.offer(start);
		while(idx<len&&arr[idx]<start+L){
			queue.offer(arr[idx]);
			idx++;
		}

		int max=queue.size();
		for(int i=start;i+L<=arr[len-1];i++){
			if(queue.peek()==i){
				queue.poll();
			}
			if(i+L-1==arr[idx]){
				queue.offer(arr[idx]);
				idx++;
			}
			max=Math.max(max,queue.size());
		}
		
		return max;
	}
	
	//华东窗口
	public static int getMaxCoverPoints1_1(int[] arr,int L){
		int max=0;
		int cn=0;
		int l=arr[0];
		int r=arr[0];

		int len=arr.length;
		int s=0;
		for(int i=0;i<len;){
			if(r-l+1>L){
				if(l==arr[s]){
					--cn;
					++s;
				}
				++l;
			}
			if(arr[i]==r){
				++cn;
				++i;
				max=Math.max(max,cn);
			}
			++r;
		}
		return max;
	}

	//左程云实现
	public static int getMaxCoverPoints2(int[] arr,int L){
		int max=0;
		
		
		return max;
	}


	
	//使用二分查找
	public static int getMaxCoverPoints3(int[] arr,int L){
		if(arr==null){
			return 0;
		}
		
		int res=0;
		int len=arr.length;
		for(int i=0;i<len;i++){
			int l=getMostLeftGreaterEqual(arr,i,arr[i]-L+1);
			res=Math.max(res,i-l+1);
		}
		return res;
	}

	public static int getMostLeftGreaterEqual(int[] arr,int end,int find){
		int l=0;
		int r=end;
		int mid=-1;
		int res=-1;
		
		while(l<=r){
			mid=(l+r)/2;
			if(find<=arr[mid]){
				res=mid;
				r=mid-1;
			} else{
				l=mid+1;
			}
		}
		return res;
	}

	public static void main(String[] args){
		int[] arr={
			-4,-1,0,4,5,9
		};
		int L=5;
		System.out.println(getMaxCoverPoints1(arr,L));
		System.out.println(getMaxCoverPoints1_1(arr,L));

		System.out.println(getMaxCoverPoints2(arr,L));

		System.out.println(getMaxCoverPoints3(arr,L));


		System.out.println("hello world");
	}
}


