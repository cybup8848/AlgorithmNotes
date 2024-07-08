/*************************************************************************
    > File Name: question31.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jul  3 20:26:53 2024
 ************************************************************************/

import java.util.PriorityQueue;
import java.util.Vector;
import java.util.Comparator;

public class question31{

	public static class Project{
		public int projectManager;
		public int timePoint;
		public int priorityLevel;
		public int spendTime;
		public int overTime;

		public Project(int i,int t,int p,int s){
			projectManager=i;
			timePoint=t;
			priorityLevel=p;
			spendTime=s;
			overTime=Integer.MAX_VALUE;
		}
	}

	public static class ComparatorProject implements Comparator<Project>{
		@Override
		public int compare(Project o1,Project o2){
			if(o1.priorityLevel!=o2.priorityLevel){
				return o2.priorityLevel-o1.priorityLevel;
			}
			
			if(o1.spendTime!=o2.spendTime){
				return o1.spendTime-o2.spendTime;
			}

			return o1.timePoint-o2.timePoint;
		}
	}

	public static class ComparatorProgramer implements Comparator<Project>{
		@Override
		public int compare(Project o1,Project o2){
			if(o1.spendTime!=o2.spendTime){
				return o1.spendTime-o2.spendTime;
			}

			return o1.projectManager-o2.projectManager;
		}
	}

	public static Project[] getOverTime(int pms,int sde,int[][] programs){//项目经理数量、程序员数量、项目个数
		if(pms<=0||sde<=0||programs==null||programs.length==0){
			return null;
		}
	
		Vector<PriorityQueue<Project>> managers=new Vector<>(pms);
		for(int i=0;i<pms;i++){
			managers.add(new PriorityQueue<>());
		}

		return null;
	}

	
	//有序数组和绳子，绳子覆盖问题
	public static int maxCoverPoints(int[] arr,int L){
		if(arr==null||arr.length==0||L<=0){
			return 0;
		}

		int len=arr.length;
		int end=arr[len-1];
		
		int max=0;
		int count=0;
		int l=arr[0];
		int r=arr[0];

		int rindex=0;
		int lindex=0; 
		while(r<=end){
			if(r==arr[rindex]){
				count++;
				rindex++;
				max=Math.max(max,count);
			}
			++r;
			
			if(r-l>L){
				if(l==arr[lindex]){
					--count;
					lindex++;
				}
				++l;
			}
		}
		
		return max;
	}




	public static void main(String[] args){
		int[] arr={
			1,2,4,6,7,8,10,13
		};
		int L=4;
		
		System.out.println(maxCoverPoints(arr,L));

		System.out.println("hello world");
	}
}



