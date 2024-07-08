/*************************************************************************
    > File Name: question24.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jun 27 11:15:05 2024
 ************************************************************************/

import java.util.Map;
import java.util.HashMap;

public class question24{
	
	public static class Node{
		public int val;
		public Node left;
		public Node right;
		public Node(int v){
			this.val=v;
			left=right=null;
		}
	}
	

	//符合搜索二叉树的最大子树
	public static class Info{
		public Node root;
		public int minVal;
		public int maxVal;
		public int count;
		public Info(Node root,int min,int max,int count){
			this.root=root;
			minVal=min;
			maxVal=max;
			this.count=count;
		}
	}

	public static Node maxBSTSubTree(Node root){
		Info res=process1(root);
		return res.root;
	}

	public static Info process1(Node root){
		if(root==null){
			return null;
		}

		Info left=process1(root.left);
		Info right=process1(root.right);
		
		
		Info res=new Info(root,root.val,root.val,1);
		if(left!=null&&(root.left!=left.root||left.maxVal>root.val)||right!=null&&(root.right!=right.root||right.minVal<root.val)){
			int leftNum=left==null?0:left.count;
			int rightNum=right==null?0:right.count;
			if(leftNum>=rightNum){
				res=left;
			} else {
				res=right;
			}
		} else {
			if(left!=null){
				res.minVal=left.minVal;
				res.count+=left.count;
			}
			if(right!=null){
				res.maxVal=right.maxVal;
				res.count+=right.count;
			}
		}

		return res;
	}
	

	//最大的且符合搜索二叉树条件的最大拓扑结构的大小
	public static class Record{
		public int l;
		public int r;

		public Record(int left,int right){
			this.l=left;
			this.r=right;
		}
	}

	public static int bstTopoSize2(Node head){
		Map<Node,Record> map=new HashMap<Node,Record>();
		return posOrder(head,map);
	}

	public static int posOrder(Node h,Map<Node,Record> map){
		if(h==null){
			return 0;
		}

		int ls=posOrder(h.left,map);
		int rs=posOrder(h.right,map);
		modifyMap(h.left,h.val,map,true);
		modifyMap(h.right,h.val,map,false);
		
		Record lr=map.get(h.left);
		Record rr=map.get(h.right);
		int lbst=lr==null?0:lr.l+lr.r+1;
		int rbst=rr==null?0:rr.l+rr.r+1;
		map.put(h,new Record(lbst,rbst));

		return Math.max(lbst+rbst+1,Math.max(ls,rs));
	}

	//s为true，代表检查左子树
	//s为false，代表检查右子树
	public static int modifyMap(Node n,int v,Map<Node,Record> map,boolean s){
		if(n==null||(!map.containsKey(n))){
			return 0;
		}
		
		Record r=map.get(n);
		if((s&&n.val>v)||((!s)&&n.val<v)){
			map.remove(n);//因为不满足搜索二叉树条件，所以后面的节点都不可能为上面的节点贡献个数，不可能与上面的节点连成一片
			return r.l+r.r+1;
		} else {
			int minus=modifyMap(s?n.right:n.left,v,map,s);
			if(s){
				r.r=r.r-minus;
			} else {
				r.l=r.l-minus;
			}

			map.put(n,r);
			return minus;
		}
	}





	public static void preTrave(Node root){
		if(root==null){
			return;
		}

		System.out.print(root.val+"	");
		preTrave(root.left);
		preTrave(root.right);
	}


	public static void main(String[] args){
		Node root=new Node(1);
		root.left=new Node(6);
		root.right=new Node(7);
		root.left.left=new Node(4);
		root.left.right=new Node(8);
		
		Node res=maxBSTSubTree(root);
		preTrave(res);
		System.out.println();
		System.out.println(bstTopoSize2(root));
		System.out.println("\n\n\n");

		root.val=8;
		root.left.right.val=9;
		res=maxBSTSubTree(root);
		preTrave(res);
		System.out.println();
		System.out.println(bstTopoSize2(root));
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}




