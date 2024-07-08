/*************************************************************************
    > File Name: question11.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jun 14 22:20:20 2024
 ************************************************************************/

import java.util.Random;
import java.util.Queue;
import java.util.Deque;
import java.util.LinkedList;

public class question11{

	public static class Node{
		public int val;
		public Node left;
		public Node right;

		public Node(int v){
			this.val=v;
			this.left=this.right=null;
		}

	}

	//自己实现
	public static int maxSum(Node root){
		return process(root);
	}
	
	public static int process(Node root){
		if(root==null){
			return 0;
		}

		int left=process(root.left);
		int right=process(root.right);
		
		int max=Math.max(left,right);

		return Math.max(root.val+max,Math.max(max,root.val));
	}

	public static Node generateNode(int max){
		Random rand=new Random(6);
		int rootValue=rand.nextInt();
		Node root=new Node(rootValue);
		Queue<Node> queue=new LinkedList<>();
		queue.add(root);
		for(int i=0;i<20;i++){
			if(!queue.isEmpty()){
				Node tmp=queue.poll();
				if(Math.random()*10>5){
					tmp.left=new Node(rand.nextInt(max)-rand.nextInt(max*2));
					queue.add(tmp.left);
				}

				if(Math.random()*10>5){
					tmp.right=new Node(rand.nextInt(max)-rand.nextInt(max*2));
					queue.add(tmp.right);
				}
			}
		}
	
		return root;
	}


	public static void main(String[] args){

		Node root=generateNode(100);
		System.out.println(maxSum(root));


		System.out.println("hello world");
	}
}






