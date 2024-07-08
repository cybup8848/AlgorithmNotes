/*************************************************************************
    > File Name: question49.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 22 19:54:55 2024
 ************************************************************************/

import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;


public class question49{

	//这里假设最后一行为最后一个活动
	public static void getMaxReward(int[] spendDays,int[] rewards,int[][] graph,int totalDays){
		if(totalDays<=0){
			return;
		}

		int activities=spendDays.length;

		int lastActivity=activities-1;

		ReturnType res=process(spendDays,rewards,graph,totalDays,0,lastActivity);
		System.out.println(res.reward+"	"+(totalDays-res.rest));
	}

	public static class ReturnType{
		public int reward;
		public int rest;

		public ReturnType(int reward,int rest){
			this.reward=reward;
			this.rest=rest;
		}
	}

	public static ReturnType process(int[] spendDays,int[] rewards,int[][] graph,int restDays,int totalReward,int activity){
		if(restDays<spendDays[activity]){
			return new ReturnType(totalReward,restDays);
		}

		restDays-=spendDays[activity];
		totalReward+=rewards[activity];
		
		ReturnType max=new ReturnType(totalReward,restDays);
		for(int i=0;i<spendDays.length;++i){
			if(graph[i][activity]==1){
				ReturnType res=process(spendDays,rewards,graph,restDays,totalReward,i);
				if(max.reward<res.reward){
					max=res;
				} else if(max.reward==res.reward&&max.rest<res.rest){
					max=res;
				}
			}
		}
		
		return max;
	}	

	
	//图的宽度优先遍历
	public static class Node{
		public int activityLabel;
		public int spendDays;
		public int totalReward;

		public Node(int label,int spend,int reward){
			activityLabel=label;
			spendDays=spend;
			totalReward=reward;
		}
	}

	public static void getMaxReward1(int[] spendDays,int[] rewards,int[][] graph,int totalDays){
		if(graph==null||graph[0]==null){
			return;
		}

		int sink=findSink(graph);

		Queue<Node> queue=new LinkedList<>();
		
		queue.add(new Node(sink,0,0));

		int maxReward=Integer.MIN_VALUE;
		int spends=Integer.MAX_VALUE;

		Node node=null;
		while(!queue.isEmpty()){
			node=queue.poll();
			
			if(node.spendDays+spendDays[node.activityLabel]>totalDays){
				if(maxReward<node.totalReward){
					maxReward=node.totalReward;
					spends=node.spendDays;
				} else if(maxReward==node.totalReward&&spends<node.spendDays){
					spends=node.spendDays;
				}

				continue;
			}
			
			//node.sendDays+=spendDays[node.activityLabel];
			//node.totalReward+=rewards[node.activityLabel];

			boolean isSource=true;
			for(int i=0;i<spendDays.length;i++){
				if(graph[i][node.activityLabel]==1){
					isSource=false;
					queue.add(new Node(i,node.spendDays+spendDays[node.activityLabel],node.totalReward+rewards[node.activityLabel]));
				}
			}

			if(isSource){
				if(maxReward<node.totalReward+rewards[node.activityLabel]){
					maxReward=node.totalReward+rewards[node.activityLabel];
					spends=node.spendDays+spendDays[node.activityLabel];
				} else if(maxReward==node.totalReward+rewards[node.activityLabel]&&spends<node.spendDays+spendDays[node.activityLabel]){
					spends=node.spendDays+spendDays[node.activityLabel];
				}
			}
		}

		System.out.println(maxReward+"	"+spends);
	}
		
	public static int findSink(int[][] graph){
		int row=graph.length;

		int col=graph[0].length;
		
		boolean flag=true;
		for(int i=0;i<row;i++){

			flag=true;
			for(int j=0;j<col;j++){
				if(graph[i][j]==1){
					flag=false;
					break;
				}
			}

			if(flag==true){
				return i;
			}
		}

		return -1;
	}

	

	


	




	public static void main(String[] args){

		int N=8;
		int D=10;

		int[][] graph={
			{
				0,1,1,0,0,0,0,0
			},
			{
				0,0,0,1,1,0,0,0
			},
			{
				0,0,0,1,0,0,0,0
			},
			{
				0,0,0,0,1,1,1,0
			},
			{
				0,0,0,0,0,0,0,1
			},
			{
				0,0,0,0,0,0,0,1
			},
			{
				0,0,0,0,0,0,0,1
			},
			{
				0,0,0,0,0,0,0,0
			}
		};

		int[] spendDays={
			3,3,2,1,4,2,4,3
		};

		int[] rewards={
			2000,4000,2500,1600,3800,2600,4000,3500
		};

	
		getMaxReward(spendDays,rewards,graph,D);
		getMaxReward(spendDays,rewards,graph,D+100);
		getMaxReward1(spendDays,rewards,graph,D+100);

		System.out.println("hello world");
	}
}




