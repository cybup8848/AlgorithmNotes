/*************************************************************************
    > File Name: question14.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May  9 10:24:52 2024
 ************************************************************************/
public class question14{

	public static class Node{
		int val;
		Node left;
		Node right;

		public Node(int value){
			val=value;
			left=right=null;
		}
	}

	//自己实现
	public static int maxWeight(Node root){
		if(root==null){
			return 0;
		}
		
		if(root.left==null&&root.right==null){
			return root.val;
		}

		return root.val+Math.max(maxWeight(root.left),maxWeight(root.right));
	}

	//全局变量，只有到达叶节点的时候，有可能更新
	public static int maxSum=Integer.MIN_VALUE;
	public static int maxPath(Node head){
		process(head,0);
		return maxSum;
	}

	//左程云实现
	//从根节点出发到当前节点上方的节点，获得的路径和
	public static void process(Node x,int pre){//前序遍历
		if(x.left==null&&x.right==null){//当前的x是叶节点
			maxSum=Math.max(maxSum,pre+x.val);
		}

		if(x.left!=null){
			process(x.left,pre+x.val);
		}

		if(x.right!=null){
			process(x.right,pre+x.val);
		}
	}

	public static int maxDis(Node head){
		if(head==null){
			return 0;
		}
		return process2(head);
	}

	//x为头的整棵树上，最大路径和是多少，返回
	//路径要求，一定从x出发，到叶节点，算做一个路径
	public static int process2(Node x){//后序遍历
		if(x.left==null&&x.right==null){
			return x.val;
		}

		int next=Integer.MIN_VALUE;
		if(x.left!=null){
			next=process2(x.left);
		}
		if(x.right!=null){
			next=Math.max(next,process2(x.right));
		}
		return x.val+next;
	}
	

	public static void main(String[] args){

		Node root=new Node(1);

		root.left=new Node(2);
		root.right=new Node(3);

		root.left.left=new Node(9);
		root.left.right=new Node(8);
		root.left.left.left=new Node(1);

		root.right.left=new Node(6);


		System.out.println(maxWeight(root));
		System.out.println(maxPath(root));
		System.out.println(maxDis(root));

		System.out.println("hello world");
	}

}
