/*************************************************************************
    > File Name: question36.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun Jul  7 16:28:21 2024
 ************************************************************************/

import java.util.Stack;
import java.util.LinkedList;

public class question36{
	
	public static class Node{
		public int val;
		public Node left;
		public Node right;

		public Node(int v){
			val=v;
			left=right=null;
		}
	}
	
	public static Node[] getWrongNode1(Node root){
		if(root==null){
			return null;
		}

		return process1(root);
	}

	public static Node[] process1(Node root){
		Stack<Node> stack=new Stack<>();
		
		LinkedList<Node> list=new LinkedList<>();
		
		while(!stack.isEmpty()||root!=null){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				list.add(root);
				root=root.right;
			}
		}
		System.out.println("\n\n\n");

		Node[] res=new Node[2];
		int i=0;
		Node pre=new Node(Integer.MIN_VALUE);
		for(Node x:list){
			if(pre.val>x.val){
				if(i==0){
					res[i++]=pre;
				}
				res[1]=x;
			}
			pre=x;
			System.out.print(x.val+"	");
		}
		
		System.out.println("\n\n\n");
		return res;
	}

	public static Node[] getWrongNode2(Node root){
		if(root==null){
			return null;
		}

		return process2(root);
	}

	public static Node[] process2(Node root){
		Stack<Node> stack=new Stack<>();
		Node[] res=new Node[2];
		int i=0;
		Node pre=new Node(Integer.MIN_VALUE);
	
		
		while(!stack.isEmpty()||root!=null){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				if(pre.val>root.val){
					if(i==0){
						res[i++]=pre;
					}
					res[1]=root;
				}
				pre=root;
				System.out.print(root.val+"	");
				root=root.right;
			}
		}
		System.out.println("\n\n\n");

		return res;
	}

	
	public static Node[] getWrongNode3(Node root){
		if(root==null){
			return null;
		}
		
		return process3(root);
	}

	//左程云实现
	public static Node[] process3(Node root){
		Stack<Node> stack=new Stack<>();
		Node[] res=new Node[2];
		int i=0;
		Node pre=new Node(Integer.MIN_VALUE);
	
		while(!stack.isEmpty()||root!=null){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				if(pre.val>root.val){
					res[0]=res[0]==null?pre:res[0];
					res[1]=root;
				}
				pre=root;
				root=root.right;
			}
		}
		return res;
	}

	//找到两个节点的父节点
	public static Node[] getTwoErrParents(Node root,Node e1,Node e2){
		Node[] parents=new Node[2];
		if(root==null){
			return parents;
		}

		Stack<Node> stack=new Stack<>();
		while(!stack.isEmpty()||root!=null){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				if(root.left==e1||root.right==e1){
					parents[0]=root;
				}
				if(root.left==e2||root.right==e2){
					parents[1]=root;
				}
				root=root.right;	
			}
		}
		
		return parents;
	}

	public static void adjustBST(Node root){
		if(root==null){
			return;
		}

		Node[] errors=getWrongNode3(root);
		Node[] parents=getTwoErrParents(root,errors[0],errors[1]);
		
		Node err1=errors[0];
		Node par1=parents[0];

		Node err2=errors[1];
		Node par2=parents[1];

		if(err1==root){//err1是根节点
			if(err1.right==err2){//err2是err1的右子树
				Node left=err1.left;
				err1.left=err2.left;
				err1.right=err2.right;
				err2.left=left;
				err2.right=err1;
			} else {
				Node left=err1.left;
				Node right=err1.right;
				err1.left=err2.left;
				err1.right=err2.right;
				err2.left=left;
				err2.right=right;
				
				if(par2.left==err2){
					par2.left=err1;
				} else {
					par2.right=err1;
				}
			}
		} else if(err2==root){//err1、err2
			if(err2.left==err1){
				Node right=err2.right;
				err1.left=err2.left;
				err1.right=err2.right;
				err2.left=err1;
				err2.right=right;
			} else {
				Node left=err2.left;
				Node right=err2.right;
				err2.left=err1.left;
				err2.right=err1.right;
				err1.left=left;
				err1.right=right;
				if(par1.left==err1){
					par1.left=err2;
				} else {
					par1.right=err2;
				}
			}
		} else if(err2.left==err1){
			Node right=err2.right;
			err2.left=err1.left;
			err2.right=err1.right;
			err1.left=err2;
			err1.right=right;
			if(par2.left==err2){
				par2.left=err1;
			} else {
				par2.right=err1;
			}
		} else if(err1.right==err2){
			Node left=err1.left;
			err1.left=err2.left;
			err1.right=err2.right;
			err2.left=left;
			err2.right=err1;
			if(par1.left==err1){
				par1.left=err2;
			} else {
				par1.right=err2;
			}
		} else {
			System.out.println("\n\n\n");
			Node left=err1.left;
			Node right=err1.right;
			err1.left=err2.left;
			err1.right=err2.right;
			err2.left=left;
			err2.right=right;

			if(par1==par2){
				Node tmp=err1;
				par1.left=err2;
				par1.right=err1;
				return;
			}

			if(par1.left==err1){
				par1.left=err2;
			} else {
				par1.right=err2;
			}

			if(par2.left==err2){
				par2.left=err1;
			} else {
				par2.right=err1;
			}
		}
	}

	public static void mid(Node root){
		if(root==null){
			return;
		}

		mid(root.left);
		System.out.print(root.val+"	");
		mid(root.right);
	}

		


	
	public static void main(String[] args){
		Node root=new Node(8);
		
		root.left=new Node(5);
		root.left.left=new Node(3);
		root.left.left.left=new Node(2);
		root.left.left.right=new Node(4);
		
		root.left.right=new Node(7);
		root.left.right.left=new Node(6);


		root.right=new Node(10);
		root.right.left=new Node(9);
		root.right.right=new Node(11);
		
		
		
		root.left.val=10;
		root.right.val=5;
		Node[] res=getWrongNode1(root);	
		for(int i=0;i<res.length;i++){
			System.out.println(res[i].val+"	");
		}
		System.out.println("\n\n\n");

		res=getWrongNode2(root);
		for(int i=0;i<res.length;i++){
			System.out.println(res[i].val);
		}
		System.out.println("\n\n\n");
		
		mid(root);
		System.out.println("\n\n\n");
		adjustBST(root);
		mid(root);
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}





