/*************************************************************************
    > File Name: Morris.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sat Mar  2 16:06:33 2024
 ************************************************************************/

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;

public class Morris{

	public static class Node{
		int num;
		Node left;
		Node right;
		public Node(int num){
			this.num=num;
			this.left=null;
			this.right=null;
		}
	}
	

	//前序递归
	public static void preOrderRecur(Node root){
		if(root==null){
			return;
		}
		System.out.print(root.num+"	");
		preOrderRecur(root.left);
		preOrderRecur(root.right);
	}
	
	//中序递归
	public static void midOrderRecur(Node root){
		if(root==null){
			return;
		}
		midOrderRecur(root.left);
		System.out.print(root.num+"	");
		midOrderRecur(root.right);
	}
	
	//后续递归
	public static void backOrderRecur(Node root){
		if(root==null){
			return;
		}
		backOrderRecur(root.left);
		backOrderRecur(root.right);
		System.out.print(root.num+"	");
	}

	//前序非递归
	public static void preOrderUnRecur1(Node root){
		Stack<Node> stack=new Stack<Node>();
		//stack.push(root);
		while(root!=null||!stack.isEmpty()){
			if(root!=null){
				System.out.print(root.num+"	");
				stack.push(root.right);
				root=root.left;
			} else{
				root=stack.pop();
			}
		}
	}

	public static void preOrderUnRecur2(Node root){
		Stack<Node> stack=new Stack<Node>();
		while(root!=null||!stack.isEmpty()){
			while(root!=null){
				System.out.print(root.num+"	");
				stack.push(root.right);
				root=root.left;
			}
			root=stack.pop();	
		}
	}
	
	//中序非递归
	public static void midOrderUnRecur1(Node root){
		Stack<Node> stack=new Stack<Node>();
		while(root!=null||!stack.isEmpty()){
			while(root!=null){
				stack.push(root);
				root=root.left;
			}
			root=stack.pop();
			System.out.print(root.num+"	");
			root=root.right;
		}
	}
	
	public static void midOrderUnRecur2(Node root){
		Stack<Node> stack=new Stack<Node>();
		while(root!=null||!stack.isEmpty()){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				System.out.print(root.num+"	");
				root=root.right;
			}		
		}
	}

	
	//后续非递归
	public static void backOrderUnRecur1(Node root){
		Stack<Node> stack=new Stack<Node>();
		Node pre=null;
		while(root!=null||!stack.isEmpty()){
			while(root!=null){
				stack.push(root);
				root=root.left;
			}
			
			root=stack.peek();
			if(root.right==null||root.right==pre){
				System.out.print(root.num+"	");
				pre=stack.pop();
				root=null;
				continue;
			}
			root=root.right;
		}	
	}
	
	public static void backOrderUnRecur2(Node root){
		Stack<Node> stack=new Stack<Node>();
		Node pre=null;
		while(root!=null||!stack.isEmpty()){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.peek();
				if(root.right==null||root.right==pre){
					System.out.print(root.num+"	");
					pre=stack.pop();
					root=null;
				} else {
					root=root.right;
				}
			}
		}
	}
	
	public static void backOrderUnRecur3(Node root){
		if(root==null){
			return;
		}
		Stack<Node> s1=new Stack<Node>();
		Stack<Node> s2=new Stack<Node>();
		s1.push(root);
		while(!s1.isEmpty()){
			root=s1.pop();
			s2.push(root);
			if(root.left!=null){
				s1.push(root.left);
			}
			if(root.right!=null){
				s1.push(root.right);
			}
		}	
		while(!s2.isEmpty()){
			System.out.print(s2.pop().num+"	");
		}
	}

	//Morris遍历
	public static void morris(Node head){
		if(head==null){
			return;
		}
		Node cur=head;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;//mostRight是cur左孩子
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}
				
				//mostRight变成了cur左子树上，最右的节点
				if(mostRight.right==null){
					mostRight.right=cur;
					cur=cur.left;
					continue;
				} else { //mostRight.right=cur
					mostRight.right=null;//第二次到达cur，说明已回到父节点
				}
			}
			cur=cur.right;
		}

	}
	
	//morris 前序遍历
	//先序：如果一个节点，只能到达一次，直接打印；如果一个节点，可以到达两次，第一次打印
	public static void preMorris(Node root){
		if(root==null){
			return;
		}
		Node cur=root;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}

				if(mostRight.right==null){
					mostRight.right=cur;
					System.out.print(cur.num+"	");
					cur=cur.left;
				} else { //mostRight.right=cur
					mostRight.right=null;
					cur=cur.right;
				}
				continue;
			}
			System.out.print(cur.num+"	");
			cur=cur.right;
		}
	}

	//左程云实现
	public static void morrisPre(Node head){
		if(head==null){
			return;
		}

		Node cur=head;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}
				
				//mostRight变成了cur左子树上，最右的节点
				if(mostRight.right==null){//第一次来到cur
					System.out.print(cur.num+"	");
					mostRight.right=cur;
					cur=cur.left;
					continue;
				} else { // mostRight.right==cur，第二次来到cur
					mostRight.right=null;
				}

			} else {// 没有左子树的情况
				System.out.print(cur.num+"	");
			}
			cur=cur.right;
		}
	}

	//morris 中序遍历
	//中序：如果一个节点，只能到达一次，直接打印；如果一个节点，可以到达两次，第二次打印
	public static void midMorris(Node root){
		if(root==null){
			return;
		}
		Node cur=root;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}

				if(mostRight.right==null){
					mostRight.right=cur;
					cur=cur.left;
				} else {// mostRight.right=cur
					System.out.print(cur.num+"	");
					mostRight.right=null;
					cur=cur.right;
				}
				continue;
			}
			System.out.print(cur.num+"	");
			cur=cur.right;
		}
	}
	
	public static void morrisIn1(Node head){
		if(head==null){
			return;
		}
		
		Node cur=head;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}
				
				if(mostRight.right==null){
					mostRight.right=cur;
					cur=cur.left;
					continue;
				} else {
					mostRight.right=null;
					System.out.print(cur.num+"	");
				}
			} else {
				System.out.print(cur.num+"	");
			}
			cur=cur.right;
		}
	}

	public static void morrisIn2(Node head){
		if(head==null){
			return;
		}

		Node cur=head;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}

				if(mostRight.right==null){
					mostRight.right=cur;
					cur=cur.left;
					continue;
				} else {
					mostRight.right=null;
				}
			}
			System.out.print(cur.num+"	");
			cur=cur.right;
		}
	}
	
	public static void backMorris(Node root){
		if(root==null){
			return;
		}

		Node cur=root;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null){
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}

				if(mostRight.right==null){
					mostRight.right=cur;
					cur=cur.left;
					continue;
				} else {
					mostRight.right=null;
					revPrint(cur.left);
				}
			}
			cur=cur.right;
		}
		revPrint(root);
	}

	public static void revPrint(Node head){
		Node pre=null;
		
		//逆序打印
		while(head!=null){
			Node next=head.right;
			head.right=pre;
			pre=head;
			head=next;
		}

		//调整回来
		head=pre;
		pre=null;
		while(head!=null){
			System.out.print(head.num+"	");
			Node next=head.right;
			head.right=pre;
			pre=head;
			head=next;
		}
	}

	//左程云实现
	public static void morrisPos(Node head){
		if(head==null){
			return;
		}

		Node cur1=head;
		Node cur2=null;
		while(cur1!=null){
			cur2=cur1.left;
			if(cur2!=null){
				while(cur2.right!=null&&cur2.right!=cur1){
					cur2=cur2.right;
				}

				if(cur2.right==null){
					cur2.right=cur1;
					cur1=cur1.left;
					continue;
				} else {
					cur2.right=null;
					printEdge(cur1.left);
				}
			}
			cur1=cur1.right;
		}
		printEdge(head);
	}
	
	//以 X 为头的树，逆序打印这棵树的右边界
	public static void printEdge(Node X){
		Node tail=reverseEdge(X);
		Node cur=tail;
		while(cur!=null){
			System.out.print(cur.num+"	");
			cur=cur.right;
		}
		reverseEdge(tail);
	}

	public static Node reverseEdge(Node from){
		Node pre=null;
		Node next=null;
		while(from!=null){
			next=from.right;
			from.right=pre;
			pre=from;
			from=next;
		}
		return pre;
	}
	
	//判断是否是搜索二叉树
	public static boolean isSearchTree1(Node root){
		Stack<Node> stack=new Stack<Node>;
		Stack<Node> res=new Stack<Node>;
		while(root!=null || !stack.isEmpty()){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				res.push(tmp.num);
				root=root.right;
			}
		}
		
		int pre=Integer.MAX_VALUE;
		boolean flag=true;
		while(!res.isEmpty()){
			int val=res.pop();
			if(val>pre){
				flag=false;
				break;
			}
			pre=val;
		}
		return flag;
	}


	public static boolean isSearchTree2(Node root){
		Stack<Node> stack=new Stack<Node>;
		int pre=Integer.MIN_VALUE;
		while(root!=null || !stack.isEmpty()){
			while(root!=null){
				stack.push(root);
				root=root.left;
			}
			root=stack.pop();
			if(pre>root.num){
				return false;
			}
			pre=root.num;
			root=root.right;
		}
		return true;
	}

	public static boolean isSearchTree3(Node root){
		if(root==null){
			return true;
		}
		int pre=Integer.MIN_VALUE;
		Node cur=root;
		Node mostRight=null;
		while(cur!=null){
			mostRight=cur.left;
			if(mostRight!=null)
			{
				while(mostRight.right!=null&&mostRight.right!=cur){
					mostRight=mostRight.right;
				}
			
				if(mostRight.right==null){
					mostRight.right=cur;
					cur=cur.left;
					continue;
				} else {
					mostRight.right=null;
				}
			}
			if(pre>cur.num){
				return false;
			}
			pre=cur.num;
			cur=cur.right;
		}
		return true;
	}


	public static void main(String[] args){
		Node root=new Node(3);
		root.left=new Node(2);
		root.right=new Node(6);
		root.left.left=new Node(7);
		root.left.right=new Node(8);
		root.right.right=new Node(9);
		
		root.left.left.left=new Node(7);
		root.left.right.left=new Node(6);
		root.left.right.left.right=new Node(8);

		preOrderRecur(root);
		System.out.println();
		midOrderRecur(root);
		System.out.println();
		backOrderRecur(root);
		System.out.println();

		//非递归
		System.out.println("\n\n\n");

		preOrderUnRecur1(root);
		System.out.println();
		preOrderUnRecur2(root);
		System.out.println();

		midOrderUnRecur1(root);
		System.out.println();
		midOrderUnRecur2(root);
		System.out.println();
		backOrderUnRecur1(root);
		System.out.println();
		backOrderUnRecur2(root);
		System.out.println();
		backOrderUnRecur3(root);
		System.out.println();
		
		System.out.println("\n\n\n\n Morris遍历");
		preMorris(root);
		System.out.println();
		morrisPre(root);
		System.out.println();

		midMorris(root);
		System.out.println();
		morrisIn1(root);
		System.out.println();
		morrisIn2(root);
		System.out.println();

		System.out.println("\n\n\n\n");
		backMorris(root);
		System.out.println();
		morrisPos(root);	
		System.out.println();

		System.out.println("hello world");
	}
}
