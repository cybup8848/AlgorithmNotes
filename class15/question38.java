/*************************************************************************
    > File Name: question38.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 17 23:10:19 2024
 ************************************************************************/


public class question38{

	public static class Node{
		public int val;
		public Node left;
		public Node right;
		
		public Node(int v){
			val=v;
			left=right=null;
		}

	}


	public static class ReturnType{
		public boolean isBST;
		public Node head;
		public int cn;

		public ReturnType(boolean is,Node r,int c){
			isBST=is;
			head=r;
			cn=c;
		}
	}

	public static ReturnType getMaxBST(Node root){
		return process(root);
	}

	//自己实现
	public static ReturnType process(Node root){
		if(root==null){
			return new ReturnType(true,null,0);
		}

		ReturnType left=process(root.left);
		ReturnType right=process(root.right);

		boolean isBST=true;
		Node h=null;
		int count=0;
		
		if(left.isBST&&right.isBST&&((root.left==null||root.left.val<root.val)&&(root.right==null||root.val<root.right.val))){
			isBST=true;
			h=root;
			count=1+left.cn+right.cn;
		} else {
			isBST=false;
			if(left.cn>right.cn){
				h=left.head;
				count=left.cn;
			} else {
				h=right.head;
				count=right.cn;
			}
		}
		
		return new ReturnType(isBST,h,count);
	}


	//左程云实现
	public static class Info{
		public Node maxBSTHead;
		public boolean isBST;
		public int min;
		public int max;
		public int maxBSTSize;

		public Info(Node head,boolean is,int mi,int ma,int size){
			maxBSTHead=head;
			isBST=is;
			min=mi;
			max=ma;
			maxBSTSize=size;
		}
	}
	
	public static Info getMaxBST1(Node root){
		return process1(root);
	}


	public static Info process1(Node root){
		if(root==null){
			//return new Info(null,true,Integer.MAX_VALUE,Integer.MIN_VALUE,0);
			return null;
		}
		
		Info left=process1(root.left);
		Info right=process1(root.right);

		int min=root.val;
		int max=root.val;

		if(left!=null){
			min=Math.min(min,left.min);
			max=Math.max(max,left.max);
		}

		if(right!=null){
			min=Math.min(min,right.min);
			max=Math.max(max,right.max);
		}

		int maxBSTSize=0;
		Node maxBSTHead=null;
		if(left!=null){
			maxBSTSize=left.maxBSTSize;
			maxBSTHead=left.maxBSTHead;
		}

		if(right!=null&&right.maxBSTSize>maxBSTSize){
			maxBSTSize=right.maxBSTSize;
			maxBSTHead=right.maxBSTHead;
		}

		boolean isBST=false;
		if((left==null||left.isBST)&&(right==null||right.isBST)){
			if((left==null||left.max<root.val)&&(right==null||right.min>root.val)){
				isBST=true;
				maxBSTHead=root;

				int leftSize=left==null?0:left.maxBSTSize;
				int rightSize=right==null?0:right.maxBSTSize;
				maxBSTSize=1+leftSize+rightSize;
			}
		}

		return new Info(maxBSTHead,isBST,min,max,maxBSTSize);
	}



	public static void midTraverse(Node root){
		if(root==null){
			return;
		}

		midTraverse(root.left);
		System.out.print(root.val+"	");
		midTraverse(root.right);
	}


	public static void main(String[] args){
		Node root=new Node(3);
		root.left=new Node(5);
		root.right=new Node(7);

		root.left.left=new Node(1);
		root.left.right=new Node(6);
		root.right.left=new Node(4);
		root.right.right=new Node(8);

		root.left.left.right=new Node(2);
		root.right.left.left=new Node(2);
		root.right.right.right=new Node(9);

		root.right.left.left.left=new Node(1);

		ReturnType res=getMaxBST(root);
		
		System.out.println(res.isBST);
		System.out.println(res.cn);
		
		midTraverse(res.head);

		System.out.println("\n\n\n");

		
		Info res1=getMaxBST1(root);
		System.out.println(res1.isBST);
		System.out.println(res1.maxBSTSize);
		midTraverse(res1.maxBSTHead);

		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}





