/*************************************************************************
    > File Name: question46.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 21 16:17:04 2024
 ************************************************************************/

import java.util.Queue;
import java.util.LinkedList;

public class question46{
	

	public static class Node{
		public int val;
		public Node left;
		public Node right;

		public Node(int v){
			val=v;
			left=right=null;
		}
	}

	
	//自己实现，遍历二叉树
	public static int getNodeCount(Node root){
		if(root==null){
			return 0;
		}

		return 1+getNodeCount(root.left)+getNodeCount(root.right);
	}

	//BFS
	public static int getNodeCount1(Node root){
		if(root==null){
			return 0;
		}

		Queue<Node> queue=new LinkedList<>();
		queue.add(root);

		int cn=0;
		while(!queue.isEmpty()){
			root=queue.poll();
			++cn;
			if(root.left!=null){
				queue.add(root.left);
			}

			if(root.right!=null){
				queue.add(root.right);
			}
		}

		return cn;
	}

	public static int getDepth(Node root){
		if(root==null){
			return 0;
		}

		return 1+Math.max(getDepth(root.left),getDepth(root.right));
	}

	//时间复杂度：O(logN) 
	public static int getNodeCount2(Node root){
		if(root==null){
			return 0;
		}

		int leftHeight=getDepth(root.left);
		int rightHeight=getDepth(root.right);

		if(leftHeight==rightHeight){//左树满二叉树
			return (1<<leftHeight)+getNodeCount2(root.right);
		} else {//右树满二叉树
			return getNodeCount2(root.left)+(1<<rightHeight);
		}
	}

	//左程云实现
	//请保证head为头的数，是完全二叉树
	public static int nodeNum(Node head){
		if(head==null){
			return 0;
		}
		
		return bs(head,1,mostLeftLevel(head,1));
	}
	
	//node在第level层，h是总的深度（h永远不变，全局变量）
	//以node为头的完全二叉树，节点个数是多少
	public static int bs(Node node,int level,int h){
		if(level==h){
			return 1;
		}


		if(mostLeftLevel(node.right,level+1)==h){
			return (1<<(h-level))+bs(node.right,level+1,h);
		} else {
			return (1<<(h-level-1))+bs(node.left,level+1,h);
		}
	}

	//如果node在第level层
	//求以node为头的子树，最大深度是多少
	//node为头的子树，一定是完全二叉树
	
	public static int mostLeftLevel(Node node,int level){
		while(node!=null){
			++level;
			node=node.left;
		}

		return level-1;
	}




	public static void main(String[] args){
		Node root=new Node(1);
		
		root.left=new Node(2);
		root.right=new Node(3);

		root.left.left=new Node(4);
		root.left.right=new Node(5);

		root.right.left=new Node(6);

		System.out.println(mostLeftLevel(root,1));

		System.out.println(getNodeCount(root));
		System.out.println(getNodeCount1(root));
		System.out.println(getNodeCount2(root));
		System.out.println(nodeNum(root));


		root.right.right=new Node(7);

		root.left.left.left=new Node(8);

		System.out.println(getNodeCount(root));
		System.out.println(getNodeCount1(root));
		System.out.println(getNodeCount2(root));
		System.out.println(nodeNum(root));

		Node root1=new Node(12);
		System.out.println(getNodeCount(root1));
		System.out.println(getNodeCount1(root1));
		System.out.println(getNodeCount2(root1));
		System.out.println(nodeNum(root1));

		System.out.println("hello world");
	}
}





