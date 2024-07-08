/*************************************************************************
    > File Name: question37.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 17 19:29:22 2024
 ************************************************************************/

import java.util.Stack;

public class question37{

	public static class TreeNode{
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v){
			val=v;
			left=right=null;
		}
	}

	public static class ListNode{
		public int val;
		public ListNode last;
		public ListNode next;

		public ListNode(int v){
			val=v;
			last=next=null;
		}
	}

	//中序遍历，非递归，使用栈
	public static ListNode tree2list(TreeNode root){
		if(root==null){
			return null;
		}

		boolean alreadyHead=false;
		
		ListNode pre=null;
		ListNode head=null;
		
		Stack<TreeNode> stack=new Stack<>();

		while(root!=null||!stack.isEmpty()){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				ListNode tmp=new ListNode(root.val);

				if(!alreadyHead){
					head=tmp;
					alreadyHead=true;
				} else {
					pre.next=tmp;
					tmp.last=pre;
				}

				pre=tmp;
				root=root.right;
			}
		}

		return head;
	}

	public static void midTraverse(TreeNode root){
		if(root==null){
			return;
		}
		
		midTraverse(root.left);
		System.out.print(root.val+"	");
		midTraverse(root.right);
	}

	public static void printList(ListNode head){
		while(head!=null){
			System.out.print(head.val+"	");
			head=head.next;
		}
	}

	public static void printRevertList(ListNode head){
		while(head.next!=null){
			head=head.next;
		}

		while(head!=null){
			System.out.print(head.val+"	");
			head=head.last;
		}
	}


	//递归
	
	public static class ReturnStruct{
		public ListNode head;
		public ListNode rear;

		public ReturnStruct(){
			head=rear=null;
		}
	}

	public static ListNode tree2list1(TreeNode root){
		if(root==null){
			return null;
		}


		return process(root).head;
	}

	public static ReturnStruct process(TreeNode root){
		if(root==null){
			return new ReturnStruct();
		}

		ReturnStruct left=process(root.left);
		ReturnStruct right=process(root.right);
		
		ListNode tmp=new ListNode(root.val);
		
		tmp.last=left.rear;
		if(left.rear!=null){
			left.rear.next=tmp;
		}
		
		tmp.next=right.head;
		if(right.head!=null){
			right.head.last=tmp;
		}
		
		
		ReturnStruct res=new ReturnStruct();
		res.head=left.head;
		res.rear=right.rear;
		if(left.head==null){
			res.head=tmp;
		}
		if(right.rear==null){
			res.rear=tmp;
		}

		return res;
	}

	public static class ReturnType{
		public TreeNode start;
		public TreeNode end;

		public ReturnType(TreeNode start,TreeNode end){
			this.start=start;
			this.end=end;
		}
	}
	
	public static TreeNode tree2list2(TreeNode root){
		return process2(root).start;
	}

	//以x为头的整棵搜索二叉树，请全部以有序双向链表的方式，连好
	//并且返回，整个有序双向链表的头结点和尾节点
	public static ReturnType process2(TreeNode x){
		if(x==null){
			return new ReturnType(null,null);
		}

		ReturnType left=process2(x.left);
		ReturnType right=process2(x.right);

		if(left.end!=null){
			left.end.right=x;
		}

		x.left=left.end;
		x.right=right.start;
		
		if(right.start!=null){
			right.start.left=x;
		}
		
		return new ReturnType(left.start!=null?left.start:x,right.end!=null?right.end:x);
	}

	public static void printTree(TreeNode h){
		while(h!=null){
			System.out.print(h.val+"	");
			h=h.right;
		}
	}

	

	public static void main(String[] args){
		TreeNode root=new TreeNode(8);
		root.left=new TreeNode(6);
		root.right=new TreeNode(9);
		
		root.left.left=new TreeNode(5);
		root.left.right=new TreeNode(7);

		root.left.left.left=new TreeNode(4);

		root.right.right=new TreeNode(10);


		midTraverse(root);
		System.out.println("\n\n");


		ListNode head=tree2list(root);
		printList(head);
		System.out.println("\n\n");
			
		printRevertList(head);
		System.out.println("\n\n");
	
		System.out.println("\n\n\n");


		head=tree2list1(root);
		printList(head);
		System.out.println("\n\n");
			
		printRevertList(head);
		System.out.println("\n\n");
	

		TreeNode h=tree2list2(root);
		printTree(h);
		System.out.println("\n\n\n");	

		System.out.println("hello world");
	}
}



