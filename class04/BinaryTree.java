/*************************************************************************
    > File Name: BinaryTree.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Jan 22 10:13:00 2024
 ************************************************************************/
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class BinaryTree{
	public static void f(Node head){
		//1
		if(head==null)
			return;
		//1：第一次回到自己

		f(head.left);
		//2
		//2：第二次回到自己本身，才能调用f(head.right)
		f(head.right);
		//3
		//3：第三次回到自己本身
		

		//我们把上面的顺序叫做递归序
	}


	public static class Node{
		public int value;
		public Node left;
		public Node right;
		
		public Node(int data){
			this.value=data;
			this.left=null;
			this.right=null;
		}
	}
	
	//递归方式
	public static void preOrderRecur(Node root){
		if(root==null)
			return;
		System.out.print(root.value+"	");
		preOrderRecur(root.left);
		preOrderRecur(root.right);
	}

	public static void inOrderRecur(Node root){
		if(root==null)
			return;
		inOrderRecur(root.left);
		System.out.print(root.value+"	");
		inOrderRecur(root.right);
	}

	public static void posOrderRecur(Node root){
		if(root==null)
			return;
		posOrderRecur(root.left);
		posOrderRecur(root.right);
		System.out.print(root.value+"	");
	}

	//任何递归，都可以修改为非递归
	//非递归方式
	public static void preOrderStack(Node root){
		Stack<Node> stack=new Stack<Node>();
		stack.push(root);
		Node tmp=null;
		while(!stack.empty()){
			tmp=stack.pop();
			while(tmp!=null){
				System.out.print(tmp.value+"	");
				stack.push(tmp.right);
				tmp=tmp.left;
			}
		}
	}

	public static void inOrderStack(Node root){
		Stack<Node> stack=new Stack<Node>();
		//stack.push(root);
		while((root!=null)||(!stack.empty())){
			while(root!=null){
				stack.push(root);
				root=root.left;
			}
			Node tmp=stack.pop();
			System.out.print(tmp.value+"	");
			root=tmp.right;
		}
	}

	public static void posOrderStack(Node root){
		Stack<Node> stack=new Stack<Node>();
		Node lastPrint=null;//记录上一个打印是哪个节点
		while((root!=null)||(!stack.empty())){
			while(root!=null){
				stack.push(root);
				root=root.left;
			}
			Node tmp=stack.peek();
			if((tmp.right==lastPrint)||((tmp.left==null)&&(tmp.right==null))||((tmp.left==lastPrint)&&(tmp.right==null))){
				System.out.print(tmp.value+"	");
				lastPrint=tmp;
				stack.pop();
				continue;
			}
			root=tmp.right;
		}
	}

	//左程云版本
	//前序遍历
	public static void preOrderUnRecur(Node head){
		System.out.println("pre-order:");
		if(head!=null){
			Stack<Node> stack=new Stack<Node>();
			stack.add(head);
			while(!stack.isEmpty()){
				head=stack.pop();
				System.out.print(head.value+"	");
				if(head.right!=null){
					stack.push(head.right);
				}
				if(head.left!=null){
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	//中序遍历
	public static void inOrderUnRecur(Node head){
		System.out.println("in-order:");
		if(head!=null){
			Stack<Node> stack=new Stack<Node>();
			while(!stack.isEmpty()||head!=null){
				if(head!=null){
					stack.push(head);
					head=head.left;
				} else {
					head=stack.pop();
					System.out.print(head.value+"	");
					head=head.right;
				}
			}
		}
		System.out.println();
	}

	//后序遍历
	public static void posOrderUnRecur1(Node head){
		System.out.println("pos-order:");
		if(head==null)
			return;
		Stack<Node> stack1=new Stack<Node>();
		stack1.add(head);
		Stack<Node> stack2=new Stack<Node>();
		Node tmp=null;
		while(!stack1.isEmpty()){
			tmp=stack1.pop();
			stack2.push(tmp);
			if(tmp.left!=null){
				stack1.push(tmp.left);
			}
			if(tmp.right!=null){
				stack1.push(tmp.right);
			}
		}
		
		//打印stack2
		while(!stack2.isEmpty()){
			tmp=stack2.pop();
			System.out.print(tmp.value+"	");
		}
		System.out.println();
	}

	public static void posOrderUnRecur2(Node head){
				


	}

	//二叉树层序遍历
	public static void w(Node head){
		if(head==null){
			return;
		}

		Queue<Node> queue=new LinkedList<Node>();
		queue.add(head);
		while(!queue.isEmpty()){
			Node cur=queue.poll();
			System.out.print(cur.value+"	");
			if(cur.left!=null){
				queue.add(cur.left);
			}
			if(cur.right!=null){
				queue.add(cur.right);
			}
		}
		System.out.println();
	}


	public static int getWidth(Node root){
		if(root==null)
			return 0;
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(root);
		Node flag=root;
		Node nextEnd=root;
		int maxWidth=-1;
		int width=0;
		Node tmp=null;
		while(!queue.isEmpty()){
			tmp=queue.poll();
			width++;
			System.out.print(tmp.value+"	");
			if(tmp.left!=null){
				nextEnd=tmp.left;
				queue.offer(nextEnd);
			}
			if(tmp.right!=null){
				nextEnd=tmp.right;
				queue.offer(nextEnd);
			}

			if(flag==tmp){
				maxWidth=maxWidth<width?width:maxWidth;
				width=0;
				flag=nextEnd;		
			}
		}
		System.out.println();
		return maxWidth;
	}

	//左程云版本
	public static int getMaxWidth1(Node root){
		if(root==null)
			return 0;
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(root);
		HashMap<Node,Integer> hashMap=new HashMap<Node,Integer>();
		hashMap.put(root,1);
		int curLevel=1;
		int curLevelNodes=0;
		int max=Integer.MIN_VALUE;
		while(!queue.isEmpty()){
			Node cur=queue.poll();
			int curNodeLevel=hashMap.get(cur);
			if(curNodeLevel==curLevel){
				curLevelNodes++;
			} else {
				curLevel=curNodeLevel;
				max=max<curLevelNodes?curLevelNodes:max;
				curLevelNodes=1;
			}

			//进栈标记好层数，出栈时才能感知到节点所在层数
			if(cur.left!=null){
				hashMap.put(cur.left,curNodeLevel+1);
				queue.offer(cur.left);
			}
			if(cur.right!=null){
				hashMap.put(cur.right,curNodeLevel+1);
				queue.offer(cur.right);
			}
		}//当弹出最后一层最后一个节点，队列为空，不会进入循环，没有将最后一层的节点数与max比较
		return Math.max(max,curLevelNodes);//max与最后一层的节点个数比较
	}
	
	public static int getMaxWidth2(Node root){
		if(root==null){
			return 0;
		}
		
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(root);
		Node curEnd=root;
		Node nextEnd=null;
		Node cur=null;
		int curLevelNodes=0;
		int max=-1;
		while(!queue.isEmpty()){
			cur=queue.poll();
			curLevelNodes+=1;
			if(cur.left!=null){
				nextEnd=cur.left;
				queue.offer(nextEnd);
			}
			if(cur.right!=null){
				nextEnd=cur.right;
				queue.offer(nextEnd);
			}
			if(cur==curEnd){
				curEnd=nextEnd;
				max=max<curLevelNodes?max:curLevelNodes;
				curLevelNodes=0;
			}
		}
		return max;
	}

	//如何判断一棵二叉树是否是搜索二叉树，这个是错误的，左子树 < 父节点 < 右子树，不能保证整棵树是二叉树
	//                 2
	//          1             3
	//                     1      4
	public static boolean isSearchTreeRecur(Node root){
		if(root==null)
			return true;

		if(((root.left!=null)&&(root.left.value>root.value))||((root.right!=null)&&(root.right.value<root.value))){
			return false;
		}

		boolean leftFlag=isSearchTreeRecur(root.left);
		if(!leftFlag){
			return false;
		}

		boolean rightFlag=isSearchTreeRecur(root.right);
		if(!rightFlag){
			return false;
		}

		return true;
	}
	

	//左程云版本
	public static int preValue=Integer.MIN_VALUE;
	public static boolean isBSTRecur(Node root){
		if(root==null){
			return true;
		}
		
		//提前一个值
		boolean isLeftBST=isBSTRecur(root.left);
		if(isLeftBST==false){
			return false;
		}
		if(root.value<=preValue){
			return false;
		}
		preValue=root.value;
		return isBSTRecur(root.right);
	}

	public static boolean checkBST(Node root){
		List<Node> inOrderList=new ArrayList<Node>();
		process(root,inOrderList);//inOrderList里面就是中序遍历的顺序

		int len=inOrderList.size();
		if(len==0){
			return true;
		}
		
		Node last=inOrderList.get(0);
		Node cur=null;
		for(int i=1;i<len;i++){
			cur=inOrderList.get(i);
			if(last.value>=cur.value){
				return false;
			}
			last=cur;
		}
		return true;
	}

	public static void process(Node root,List<Node> inOrderList){
		if(root==null){
			return;
		}
		process(root.left,inOrderList);
		inOrderList.add(root);
		process(root.right,inOrderList);

	}

	//使用中序遍历，非降序
	public static boolean isSearchTreeUnRecur1(Node root){
		if(root==null){
			return true;
		}
		
		boolean flag=true;
		Stack<Node> stack=new Stack<Node>();
		int lastValue=Integer.MIN_VALUE;
		while((root!=null)||(!stack.isEmpty())){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				if(lastValue>=root.value){
					flag=false;
					break;
				}
				lastValue=root.value;
				root=root.right;
			}
		}
		return flag;
	}

	//使用层序遍历，错误的
	public static boolean isSearchTreeUnRecur2(Node root){
		if(root==null){
			return true;
		}

		Queue<Node> queue=new LinkedList<Node>();
		boolean flag=true;
		Node cur=null;
		while(!queue.isEmpty()){
			cur=queue.poll();
			if(((cur.left!=null)&&(cur.left.value>cur.value))||((cur.right!=null)&&(cur.right.value<cur.value))){
				flag=false;
				break;
			}
			if(cur.left!=null){
				queue.offer(cur.left);
			}
			if(cur.right!=null){
				queue.offer(cur.right);
			}
		}
		return flag;
	}

	//获取一棵二叉树的高度
	public static int getHeightRecur(Node root,int depth){
		if(root==null){
			return depth;
		}
		int leftHeight=depth+1;
		if(root.left!=null){
			leftHeight=getHeightRecur(root.left,leftHeight);
		}
		int rightHeight=depth+1;
		if(root.right!=null){
			rightHeight=getHeightRecur(root.right,rightHeight);
		}
		return Math.max(leftHeight,rightHeight);
	}//也可以使用层序遍历，获取二叉树高度

	public static int getHeightRecur1(Node root){
		if(root==null)
			return 0;
		int leftHeight=1+getHeightRecur1(root.left);
		int rightHeight=1+getHeightRecur1(root.right);
		return Math.max(leftHeight,rightHeight);
	}

	public static int getHeightUnRecur(Node root){
		if(root==null){
			return 0;
		}
		int maxHeight=0;
		int height=0;
		Stack<Node> stack=new Stack<Node>();
		Node last=null;
		while((root!=null)||(!stack.isEmpty())){
			while(root!=null){
				stack.push(root);
				height++;
				root=root.left;
			}
			maxHeight=maxHeight<height?height:maxHeight;
			Node tmp=stack.peek();
			if(((tmp.left==last)&&(tmp.right==null))||(tmp.right==last)||((tmp.left==null)&&(tmp.right==null))){
				tmp=stack.pop();
				height--;
				last=tmp;
				continue;
			}
			root=tmp.right;
		}
		return maxHeight;
	}

	//如何判断一棵二叉树是完全二叉树
	public static boolean isCompleteBinaryTree(Node root){
		if(root==null){
			return true;
		}

		Queue<Node> queue=new LinkedList<Node>();
		Node cur=null;
		boolean leaf=false;
		while(!queue.isEmpty()){
			cur=queue.poll();
			
			//第一种情况
			if(((cur.left==null)&&(cur.right!=null))||(leaf&&(!((cur.left==null)&&(cur.right==null))))){
				return false;
			}
			if((cur.left==null)||(cur.right==null)){
				leaf=true;
			}
			if(cur.left!=null){
				queue.offer(cur.left);
			}

			if(cur.right!=null){
				queue.offer(cur.right);
			}
		}
		return true;
	}

	//左程云版本，CBT：complete binary tree
	public static boolean isCBT(Node root){
		if(root==null){
			return true;
		}

		LinkedList<Node> queue=new LinkedList<Node>();
		//是否遇到过左右两个孩子不双全的节点
		boolean leaf=false;
		Node l=null;
		Node r=null;
		queue.add(root);
		while(!queue.isEmpty()){
			root=queue.poll();
			l=root.left;
			r=root.right;
			//如果遇到了不双全的节点之后，又发现当前节点不是叶节点
			if((leaf&&(l!=null||r!=null)) || (l==null&&r!=null)){
				return false;
			}

			if(l!=null){
				queue.add(l);
			}
			if(r!=null){
				queue.add(r);
			}
			if(l==null||r==null){
				leaf=true;
			}
		}	
		return true;
	}
	
	//如何判断一棵二叉树是否是满二叉树
	public static boolean isFullBinaryTree(Node root){
		if(root==null){
			return true;
		}
		Queue<Node> queue=new LinkedList<Node>();
		queue.offer(root);
		Node cur=null;
		Node last=root;
		boolean flag=true;
		while(!queue.isEmpty()){
			cur=queue.poll();
			if(!((cur.left!=null&&cur.right!=null&&last.left!=null&&last.right!=null)||
			   (cur.left==null&&cur.right==null&&last.left==null&&last.right==null))){
				flag=false;
				break;
			}
			if(cur.left!=null){
				queue.offer(cur.left);
			}
			if(cur.right!=null){
				queue.offer(cur.right);
			}
			last=cur;
		}
		return flag;
	}
	
	//如何判断一颗二叉树是否是平衡二叉树
	public static boolean isAVL(Node root){
		if(root==null){
			return true;
		}
		
		//左右子树只存在一个，直接返回false
		if(((root.left!=null)&&(root.right==null))||((root.left==null)&&(root.right!=null)))
			return false;
		
		//判断高度是否相同
		int leftH=getHeightRecur1(root.left);
		int rightH=getHeightRecur1(root.right);
		if(leftH!=rightH)
			return false;

		return isAVL(root.left)&&isAVL(root.right);
	}
	
	//根据左程云讲解的套路，左子树平衡、右子树平衡，|左子树高度-右子树高度|<=1
	public static boolean isAVL1(Node root,int height){
		if(root==null){
			return true;
		}
		
		//int leftH=isAVL1
		
		return true;
	}

	//左程云写法，将他们封装成了一个结构体
	public static boolean isBalanced(Node head){
		return processAVL(head).isBalanced;
	}

	public static class ReturnType{
		public boolean isBalanced;
		public int height;

		public ReturnType(boolean isB,int hei){
			this.isBalanced=isB;
			this.height=hei;
		}
	}

	public static ReturnType processAVL(Node x){
		if(x==null){
			return new ReturnType(true,0);
		}

		ReturnType leftData=processAVL(x.left);
		ReturnType rightData=processAVL(x.right);
		int height=Math.max(leftData.height,rightData.height)+1;
		boolean isBalanced=leftData.isBalanced && rightData.isBalanced && Math.abs(leftData.height-rightData.height)<2;
		return new ReturnType(isBalanced,height);
	}

	//套路判定搜索二叉树
	public static boolean isBST(Node root){
		return processBST(root).isBST;
	}

	public static class ReturnTypeBST{
		public boolean isBST;
		public int max;
		public int min;

		public ReturnTypeBST(boolean isBST,int min,int max){
			this.isBST=isBST;
			this.max=max;
			this.min=min;
		}
	}

	public static ReturnTypeBST processBST(Node x){
		if(x==null){
			return new ReturnTypeBST(true,Integer.MIN_VALUE,Integer.MAX_VALUE);
		}
		
		ReturnTypeBST leftData=processBST(x.left);
		ReturnTypeBST rightData=processBST(x.right);
		
		//先判断左右子树是否为二叉搜索树
		boolean isBST=leftData.isBST && rightData.isBST;
		if(!isBST){
			return new ReturnTypeBST(false,0,0);
		}

		//再判断 左子树最大值<当前节点、右子树最小值>当前节点
		if(leftData.min!=Integer.MIN_VALUE && (leftData.max>=x.value)){
			return new ReturnTypeBST(false,0,0);
		}

		if(leftData.min!=Integer.MIN_VALUE && (rightData.min<=x.value)){
			return new ReturnTypeBST(false,0,0);
		}
		
		//可以判断以 节点x 为根节点的子树是二叉搜索树，直接判断
		int min=leftData.min==Integer.MIN_VALUE?x.value:leftData.min;
		int max=rightData.min==Integer.MIN_VALUE?x.value:rightData.max;
		return new ReturnTypeBST(isBST,min,max);
	}

	//套路判定搜索二叉树，左程云写法
	public static class ReturnData{
		public boolean isBST;
		public int min;
		public int max;
		public ReturnData(boolean is,int mi,int ma){
			this.isBST=is;
			this.min=min;
			this.max=ma;
		}
	}

	public static ReturnData processBST1(Node x){
		if(x==null){
			return null;
		}

		ReturnData leftData=processBST1(x.left);
		ReturnData rightData=processBST1(x.right);
		int min=x.value;
		int max=x.value;
		if(leftData!=null){
			//由于他们可能不满足搜索二叉树
			min=Math.min(min,leftData.min);
			max=Math.max(max,leftData.max);

			//如果他们满足搜索二叉树。则
			//min=Math.min(min,leftData.min);
		}
		if(rightData!=null){
			//由于他们可能不满足搜索二叉树
			min=Math.min(min,rightData.min);
			max=Math.max(max,rightData.max);

			//如果他们满足搜索二叉树。则
			//max=Math.max(max,rightData.max);
		}

		boolean isBST=true;
		if(leftData!=null && (!leftData.isBST || leftData.max>=x.value)){
			isBST=false;
		}
		if(rightData!=null && (!rightData.isBST || rightData.min<=x.value)){
			isBST=false;
		}

		return new ReturnData(isBST,min,max);
	}

	//套路判定满二叉树
	public static boolean isFBT(Node root){
		return processFBT1(root).isFBT;
	}

	public static class ReturnTypeFBT{
		public boolean isFBT;
		public int height;
		public ReturnTypeFBT(boolean isFBT,int height){
			this.isFBT=isFBT;
			this.height=height;
		}
	}

	public static ReturnTypeFBT processFBT1(Node x){
		if(x==null){
			return new ReturnTypeFBT(true,0);
		}

		ReturnTypeFBT leftData=processFBT1(x.left);
		ReturnTypeFBT rightData=processFBT1(x.right);
		int height=Math.max(leftData.height,rightData.height)+1;
		boolean isFBT=leftData.isFBT && rightData.isFBT && 
			((x.left!=null && x.right!=null)||(x.left==null && x.right==null)) && 
			(leftData.height==rightData.height);
		return new ReturnTypeFBT(isFBT,height);
	}

	//套路判定满二叉树，左程云写法
	public static boolean isFull(Node head){
		if(head==null){
			return true;
		}
		Info data=processFBT(head);
		//return info.nodes==(int)(Math.pow(2,info.height)-1);
		return data.nodes==(1<<data.height-1);
	}
	
	//整棵树高度、整棵树节点个数
	public static class Info{
		public int height;
		public int nodes;
		public Info(int h,int n){
			this.height=h;
			this.nodes=n;
		}
	}

	public static Info processFBT(Node x){
		if(x==null){
			return new Info(0,0);
		}
		
		Info leftData=processFBT(x.left);
		Info rightData=processFBT(x.right);
		int height=Math.max(leftData.height,rightData.height)+1;
		int nodes=leftData.nodes+rightData.nodes+1;
		return new Info(height,nodes);
	}


	
	//直观地打印一棵树
	public static void printTree(Node head){
		System.out.println("Binary Tree:");
		printInOrder(head,0,"H",17);
		System.out.println();
	}

	public static void printInOrder(Node head,int height,String to,int len){
		if(head==null)
			return;
		printInOrder(head.right,height+1,"v",len);
		String val=to+head.value+to;
		int lenM=val.length();
		int lenL=(len-lenM)/2;
		int lenR=len-lenM-lenL;
		val=getSpace(lenL)+val+getSpace(lenR);
		System.out.println(getSpace(height*len)+val);
		printInOrder(head.left,height+1,"^",len);
	}
	
	public static String getSpace(int num){
		String space=" ";
		StringBuffer buf=new StringBuffer();
		for(int i=0;i<num;i++){
			buf.append(space);
		}
		return buf.toString();
	}

	public static Node createBinaryTree(int[] arr){
		int len=arr.length;
		if(len<1)
			return null;
		int identifier=0;
		Node root=new Node(arr[0]);
		Node cur=root;
		Node tmp=null;
		for(int i=1;i<len;i++){
			tmp=new Node(arr[i]);
			identifier=(int)(2*Math.random());
			if(identifier==0)//0代表左子树，1代表右子树
				cur.left=tmp;
			else
				cur.right=tmp;
			cur=tmp;
		}
		return root;
	}

	

	public static void main(String[] args){
		int[] arr={1,2,3,4,5,6,45,23,6723,3,23};
		Node root=new Node(1);
		root.left=new Node(2);
		root.right=new Node(3);
		root.left.left=new Node(4);
		root.left.right=new Node(5);
		root.right.left=new Node(6);
		root.right.right=new Node(45);

		root.left.left.right=new Node(23);
		root.left.left.right.left=new Node(6723);
		root.right.right.left=new Node(3);
		root.right.left.left=new Node(23);

		System.out.println("\n前序遍历");
		preOrderRecur(root);
		
		System.out.println("\n中序遍历");
		inOrderRecur(root);

		System.out.println("\n后序遍历");
		posOrderRecur(root);

		System.out.println("\n前序遍历");
		preOrderStack(root);

		System.out.println("\n中序遍历");
		inOrderStack(root);

		System.out.println("\n后序遍历");
		posOrderStack(root);

		System.out.println("\nHello World");
		
		printTree(root);

		System.out.println("the width of binary is "+getWidth(root));
		System.out.println("层序遍历：");
		w(root);

		System.out.println();
		System.out.println(getHeightRecur(root,0));
		System.out.println(getHeightRecur1(root));
		System.out.println(getHeightUnRecur(root));

		System.out.println(isCompleteBinaryTree(root));
		System.out.println(isCBT(root));
		System.out.println(isFullBinaryTree(root));
		System.out.println(isFull(root));
		System.out.println(isFBT(root));
	}
}
