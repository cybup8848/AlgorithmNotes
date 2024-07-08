/*************************************************************************
    > File Name: TreeDP.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Mar  1 22:21:09 2024
 ************************************************************************/

import java.util.Stack;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Deque;

public class TreeDP{

	public static class Node{
		public int num;
		public Node left;
		public Node right;

		public Node(int num){
			this.num=num;
			this.left=null;
			this.right=null;
		}
	}

	public static int maxDistance(Node head){
		return process(head).maxDistance;
	}

	//返回以x为头的整棵树，两个信息：高度、最大距离
	public static Info process(Node head){
		if(head==null){
			return new Info(0,0);
		}
		
		Info left=process(head.left);
		Info right=process(head.right);
		int height=1+Math.max(left.height,right.height);
		int maxDis=Math.max(Math.max(left.maxDistance,right.maxDistance),left.height+right.height+1);
		return new Info(maxDis,height);
	}
	
	//左程云实现
	public static Info process1(Node x){
		if(x==null){
			return new Info(0,0);
		}

		Info leftInfo=process(x.left);
		Info rightInfo=process(x.right);

		int p1=leftInfo.maxDistance;
		int p2=rightInfo.maxDistance;
		int p3=leftInfo.height+1+rightInfo.height;
		int maxDistance=Math.max(p3,Math.max(p1,p2));
		int height=Math.max(leftInfo.height,rightInfo.height)+1;
		return new Info(maxDistance,height);
	}

	public static class Info{
		public int maxDistance;
		public int height;

		public Info(int dis,int h){
			this.maxDistance=dis;
			this.height=h;
		}
	}

	//派对的最大快乐值
	public static class Employee{
		public int happy;//这名员工可以带来的快乐值
		public List<Employee> subordinates;//这名员工有哪些直接下级
		public Employee(int happy){
			this.happy=happy;
			this.subordinates=null;
		}
	}

	//自己实现
	public static int maxHappy(Employee boss){
		return Math.max(includeProcess(boss),excludeProcess(boss));
	}
	
	public static int includeProcess(Employee boss){
		if(boss.subordinates==null){
			return boss.happy;
		}

		int sum=boss.happy;
		for(Employee e:boss.subordinates){
			int tmp=excludeProcess(e);
			if(tmp>0){
				sum+=tmp;
			}
		}

		return sum;
	}

	public static int excludeProcess(Employee boss){
		if(boss.subordinates==null){
			return 0;
		}

		int sum=0;
		for(Employee e:boss.subordinates){
			int tmp=maxHappy(e);
			if(tmp>0){
				sum+=tmp;
			}
		}
		return sum;
	}

	//左程云实现
	public static class InfoParty{
		public int includeSelf;
		public int excludeSelf;
		public InfoParty(int include,int exclude){
			this.includeSelf=include;
			this.excludeSelf=exclude;
		}
	}

	public static int maxHappy1(Employee boss){
		InfoParty info=process2(boss);
		return Math.max(info.includeSelf,info.excludeSelf);
	}

	public static InfoParty process2(Employee boss){
		if(boss.subordinates==null){//boss是基层员工的时候
			return new InfoParty(boss.happy,0);
		}
		
		int include=boss.happy;//x来的情况下，整棵树最大收益
		int exclude=0;//x不来的情况下，整棵树最大收益
		for(Employee next:boss.subordinates){
			InfoParty x=process2(next);
			include+=x.excludeSelf;
			exclude+=Math.max(x.includeSelf,x.excludeSelf);
		}
		return new InfoParty(include,exclude);
	}

	public static void main(String[] args){
		//测试最大距离
		Node head=new Node(1);
		Node left1=new Node(2);
		Node right1=new Node(3);
		head.left=left1;
		head.right=right1;

		Node left2=new Node(4);
		Node right2=new Node(5);
		left1.left=left2;
		left1.right=right2;

		Node left3=new Node(6);
		Node right3=new Node(7);
		left2.left=left3;
		left2.right=right3;

		Node left4=new Node(8);
		Node right4=new Node(9);
		right2.left=left4;
		right2.right=right4;
		
		System.out.println(maxDistance(head));
		
		right1.left=new Node(4);
		right1.right=new Node(7);

		right1.right.right=new Node(8);
		right1.right.right.right=new Node(9);
		System.out.println(maxDistance(head));

		//派对的最大快乐值
		Employee boss=new Employee(700);
		
		Employee node3=new Employee(3);
		Employee node4=new Employee(4);
		Employee node5=new Employee(5);
		Employee node100=new Employee(-100);
		Employee node200=new Employee(-200);
		Employee node300=new Employee(300);
		
		List<Employee> list1=new LinkedList<Employee>();
		list1.add(node3);
		list1.add(node4);
		list1.add(node5);
		boss.subordinates=list1;

		List<Employee> list2=new LinkedList<Employee>();
		list2.add(node100);
		node3.subordinates=list2;

		List<Employee> list3=new LinkedList<Employee>();
		list3.add(node200);
		node4.subordinates=list3;

		List<Employee> list4=new LinkedList<Employee>();
		list4.add(node300);
		node5.subordinates=list4;
		System.out.println(maxHappy(boss));
		System.out.println(maxHappy1(boss));

		//测试
		Employee boss_1=new Employee(10);
		Employee node_3=new Employee(3);
		Employee node_20=new Employee(20);
		Employee node_40=new Employee(40);
		Employee node_60=new Employee(60);
		Employee node_3_1=new Employee(3);
		Employee node_5=new Employee(5);
		Employee node_6=new Employee(6);

		List<Employee> l1=new LinkedList<Employee>();
		l1.add(node_3);
		l1.add(node_20);
		l1.add(node_40);
		boss_1.subordinates=l1;

		List<Employee> l2=new LinkedList<Employee>();
		l2.add(node_60);
		node_3.subordinates=l2;

		List<Employee> l3=new LinkedList<Employee>();
		l3.add(node_3_1);
		node_20.subordinates=l3;

		List<Employee> l4=new LinkedList<Employee>();
		l4.add(node_5);
		l4.add(node_6);
		node_40.subordinates=l4;
		System.out.println(maxHappy(boss_1));
		System.out.println(maxHappy1(boss_1));

		System.out.println("hello world");
	}
}
