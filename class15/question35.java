/*************************************************************************
    > File Name: question35.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 16 17:58:15 2024
 ************************************************************************/

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Random;

public class question35{

	public static class Job{
		public int money;//该工作的报酬
		public int hard;//该工作的难度
		
		public Job(int m,int h){
			this.money=m;
			this.hard=h;
		}
	}
	
	//jobarr表示工作，arr表示所有小伙伴的能力
	//暴力递归写法
	public static int[] getMaxMoney1(Job[] jobarr,int[] arr){
		if(arr==null){
			return null;
		}
		
		int len=arr.length;
		int[] res=new int[len];

		int jobsLen=jobarr.length;
		for(int i=0;i<len;i++){
			int max=0;
			for(int j=0;j<jobsLen;j++){
				if(arr[i]>=jobarr[j].hard){
					max=Math.max(max,jobarr[j].money);
				}
			}
			res[i]=max;
		}
		
		return res;
	}
	



	public static class JobComparator implements Comparator<Job>{
		@Override
		public int compare(Job o1,Job o2){
			return o1.hard!=o2.hard?o1.hard-o2.hard:o2.money-o1.money;
		}
	}

	public static int[] getMaxMoney2(Job[] jobarr,int[] ability){
		if(ability==null){
			return null;
		}	

		Arrays.sort(jobarr,new JobComparator());

		TreeMap<Integer,Integer> map=new TreeMap<>();
		map.put(jobarr[0].hard,jobarr[0].money);
		Job pre=jobarr[0];// pre之前组的组长
		int len=jobarr.length;
		for(int i=1;i<len;i++){
			if(jobarr[i].hard!=pre.hard&&jobarr[i].money>pre.money){
				map.put(jobarr[i].hard,jobarr[i].money);
				pre=jobarr[i];
			}
		}
		
		int count=ability.length;
		int[] ans=new int[count];
		for(int i=0;i<count;i++){
			Integer key=map.floorKey(ability[i]);
			ans[i]=key!=null?map.get(key):-1;
		}
		
		return ans;
	}
	
	
	
	
	public static void main(String[] args){
		Job[] jobs=new Job[10];
		
		Random random=new Random();
		for(int i=0;i<10;i++){
			jobs[i]=new Job(random.nextInt(100),random.nextInt(10));
		}

		int[] patterns=new int[20];
		for(int i=0;i<20;i++){
			patterns[i]=random.nextInt(15);
		}


		int[] ans=getMaxMoney1(jobs,patterns);
		for(int i=0;i<ans.length;i++){
			System.out.print(ans[i]+"	");
		}
		System.out.println();

		ans=getMaxMoney2(jobs,patterns);
		for(int i=0;i<ans.length;i++){
			System.out.print(ans[i]+"	");
		}
		
		
	
		
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}





