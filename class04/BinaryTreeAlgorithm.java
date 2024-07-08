/*************************************************************************
    > File Name: BinaryTreeAlgorithm.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jan 24 11:21:11 2024
 ************************************************************************/
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class BinaryTreeAlgorithm{
	public static class Node{
		public int value;
		public Node left;
		public Node right;
		public Node(int val){
			this.value=val;
			this.left=null;
			this.right=null;
		}
	}

	public static Stack<Node> copyStack(Stack<Node> stack){
		Stack<Node> tmpStack=new Stack<Node>();
		Stack<Node> res=new Stack<Node>();
		Node cur=null;
		while(!stack.isEmpty()){
			cur=stack.pop();
			tmpStack.push(cur);
		}
		while(!tmpStack.isEmpty()){
			cur=tmpStack.pop();
			res.push(cur);
			stack.push(cur);
		}
		return res;
	}

	public static Stack<Node> reverseStack(Stack<Node> stack){
		Stack<Node> res=new Stack<Node>();
		Node cur=null;
		while(!stack.isEmpty()){
			cur=stack.pop();
			res.push(cur);
		}
		return res;
	}

	//给定两个二叉树的节点node1和node2，找到他们的最低公共祖先节点
	public static Node findSmallestCommonAncestor(Node root,Node node1,Node node2){
		if(root==null||node1==null||node2==null){
			return null;
		}

		Stack<Node> stack1=new Stack<Node>();
		Stack<Node> stack2=new Stack<Node>();
		int count=0;//找到个数
		Node cur=null;
		Node last=null;
		while((root!=null)||(!stack1.isEmpty())){
			if(root!=null){
				stack1.push(root);
				root=root.left;
			} else {
				Node tmp=stack1.peek();
				if((tmp.left==last&&tmp.right==null)||(tmp.right==last)||(tmp.left==null&&tmp.right==null)){
					if(tmp==node1){
						count++;
						if(count==1){
							stack2=copyStack(stack1);
						}
					}
					if(tmp==node2){
						count++;
						if(count==1){
							stack2=copyStack(stack1);
						}
					}
					if(count==2){
						break;
					}
					last=tmp;
					stack1.pop();
					continue;
				}
				root=tmp.right;
			}
		}

		//反转栈
		stack1=reverseStack(stack1);
		stack2=reverseStack(stack2);
		Node s1=null;
		Node s2=null;
		Node ancestor=null;
		while((!stack1.isEmpty())&&(!stack2.isEmpty())){
			s1=stack1.pop();
			s2=stack2.pop();
			if(s1!=s2){
				break;
			}
			ancestor=s1;
		}
		return ancestor;
	}

	//左程云写法,自己的随意发挥
	//o1、o2一定属于head为头的树
	//返回o1和o2的最低公共祖先
	public static Node lowestCommonAncestor1(Node root,Node o1,Node o2){
		HashMap<Node,Node> fatherMap=new HashMap<Node,Node>();
		fatherMap.put(root,null);
		process(root,fatherMap);
		HashSet<Node> hashSet=new HashSet<Node>();
		Node ancestorO1=o1;
		while(ancestorO1!=null){
			hashSet.add(ancestorO1);
			ancestorO1=fatherMap.get(ancestorO1);
		}
		
		Node ancestor=o2;
		while(ancestor!=null){
			if(hashSet.contains(ancestor)){
				break;
			}
			ancestor=fatherMap.get(ancestor);
		}
		return ancestor;
	}
	//使用先序遍历，设置父节点与孩子节点映射关系
	public static void process(Node root,HashMap<Node,Node> fatherMap){
		if(root==null){
			return;
		}
		if(root.left!=null){
			fatherMap.put(root.left,root);
		}
		if(root.right!=null){
			fatherMap.put(root.right,root);
		}
		process(root.left,fatherMap);
		process(root.right,fatherMap);
	}

	//左程云写法1
	public static Node lowestCommonAncestor2(Node root,Node o1,Node o2){
		HashMap<Node,Node> fatherMap=new HashMap<Node,Node>();
		fatherMap.put(root,root);
		process(root,fatherMap);
		HashSet<Node> set1=new HashSet<Node>();
		Node cur=o1;
		while(cur!=fatherMap.get(cur)){
			set1.add(cur);
			cur=fatherMap.get(cur);
		}
		
		Node ancestor=o2;
		while(!set1.contains(ancestor)){
			ancestor=fatherMap.get(ancestor);
		}
		return ancestor;
	}

	//左程云写法2
	public static Node lowestCommonAncestor3(Node root,Node o1,Node o2){
		if(root==null||root==o1||root==o2){
			return root;
		}
		
		Node left=lowestCommonAncestor3(root.left,o1,o2);
		Node right=lowestCommonAncestor3(root.right,o1,o2);
		if(left!=null&&right!=null){//说明o1、o2分别在head的左右两棵子树上
			return root;
		}
		return left!=null?left:right;//left、right有一个为null，或者都为null
	}

	//在二叉树中找到一个节点的后继节点
	public static class Node1{
		public int value;
		public Node1 left;
		public Node1 right;
		public Node1 parent;
		public Node1(int val){
			this.value=val;
		}
	}
	
	//直接中序遍历
	public static Node1 findSubsequentNode1(Node1 root,Node1 node){
		boolean flag=false;
		Stack<Node1> stack=new Stack<Node1>();
		while(root!=null || !stack.isEmpty()){
			if(root!=null){
				stack.push(root);
				root=root.left;
			} else {
				root=stack.pop();
				if(flag){
					break;
				}
				if(root==node){
					flag=true;
				}
				root=root.right;

			}
		}
		return root;
	}
	
	//根据上述非递归中序遍历写出的递归遍历，不会写
	public static Node1 findSubsequentNode2(Node1 root,Node1 node){
		if(root==null||root.left==node){
			return root;
		}
		findSubsequentNode2(root.left,node);
		
		
		return null;
	}

	public static Node1 findSubsequentNode3(Node1 root,Node1 node){
		if(root==null){
			return null;
		}
		Node1 subsequent=null;
		if(node.right!=null){
			subsequent=node.right;
			while(subsequent.left!=null){
				subsequent=subsequent.left;
			}
		} else {
			subsequent=node.parent;
			while(subsequent!=null&&subsequent.left!=node){
				node=subsequent;
				subsequent=subsequent.parent;
			}
		}
		return subsequent;
	}

	//左程云写法
	public static Node1 findSubsequentNode4(Node1 root,Node1 node){
		if(root==null){
			return null;
		}

		if(node.right!=null){
			return getLeftMost(root.right);
		} else {//无右子树
			Node1 parent=node.parent;
			while(parent!=null&&parent.left!=node){//当前节点是其父亲节点右孩子
				node=parent;
				parent=node.parent;
			}
			return parent;
		}
	}

	public static Node1 getLeftMost(Node1 node){
		if(node==null){
			return node;
		}
		while(node.left!=null){
			node=node.left;
		}
		return node;
	}

	//序列化
	//#：表示null
	//_：表示结束
	//先序遍历
	public static String serialization(Node root){
		//生成一个StringBuilder对象
		StringBuilder stringBuilder=new StringBuilder(10);
		Stack<Node> stack=new Stack<Node>();
		while(root!=null || !stack.isEmpty()){
			if(root!=null)	{
				stringBuilder.append(root.value);
				stringBuilder.append("_");
				stack.push(root.right);
				root=root.left;
			} else {
				stringBuilder.append("#_");
				root=stack.pop();
			}
		}
		stringBuilder.append("#_");//忘记了结束条件，当roo==null且stack为空时，还有一个null，"#_"忘了追加append
		return stringBuilder.toString();
	}

	//先序遍历的反序列化，不会写，错误代码
	public static Node deserialization(String str){
		if(str.length()==0){
			return null;
		}
		String[] strs=str.split("_");	
		Node root=new Node(Integer.parseInt(strs[0]));
		Stack<Node> stack=new Stack<Node>();
		stack.push(root);
		Node cur=root;
		Node last=null;
		int len=strs.length;
		for(int i=1;i<len;i++){
			//从左边返回，先处理右边
			if(cur.left==last){
				if(strs[i].equals("#")){
					last=cur;
					stack.pop();
					cur=stack.pop();
				} else {
					cur.right=new Node(Integer.parseInt(strs[i]));
					last=cur;
					cur=cur.right;
				}
			} else if(cur.right==last){//从右边返回，表示结束这一层，返回上一层
				last=cur;
				cur=stack.pop();
			} else if(strs[i].equals("#")){
				last=null;
				//cur=stack.pop();//此时只是到达左边，还需要判断、计算右边，才能返回上一层
			} else {
				cur.left=new Node(Integer.parseInt(strs[i]));
				last=cur;
				cur=cur.left;
				stack.push(cur);
			}

		}
		return root;
	}


	//以head为头的树，请序列化成字符串返回
	public static String serialByPre1(Node head){
		if(head==null){
			return "#_";
		}
		String res=head.value+"_";
		res+=serialByPre1(head.left);
		res+=serialByPre1(head.right);
		return res;
	}

	public static Node reconByPreString(String preStr){
		String[] values=preStr.split("_");
		Queue<String> queue=new LinkedList<String>();
		int len=values.length;
		for(int i=0;i<len;i++){
			queue.offer(values[i]);
		}
		return reconPreOrder(queue);
	}

	public static Node reconPreOrder(Queue<String> queue){
		String value=queue.poll();
		if(value.equals("#")){
			return null;
		}
		Node head=new Node(Integer.valueOf(value));
		head.left=reconPreOrder(queue);
		head.right=reconPreOrder(queue);
		return head;
	}
	
	public static String serialByPre2(Node head){
		if(head==null){
			return "#_";
		}
		return head.value+"_"+serialByPre2(head.left)+serialByPre2(head.right);
	}
	
	
	public static String serialByIn1(Node head){
		if(head==null){
			return "#_";
		}
		return serialByIn1(head.left)+head.value+"_"+serialByIn1(head.right);
	}
	
	public static Node reconByInString(String inStr){
        String[] strs=inStr.split("_");
        Queue<String> queue=new LinkedList<String>();
		int len=strs.length;
        for(int i=0;i<len;i++){
            queue.offer(strs[i]);
        }
		return reconInOrder(queue);
    }

    public static Node reconInOrder(Queue<String> queue){
        String value=queue.poll();
        if(value.equals("#")){
            return null;
        }
        Node left=reconInOrder(queue);
        Node head=new Node(Integer.parseInt(value));
        head.left=left;
        head.right=reconInOrder(queue);
        return head;
    }

	public static String serialByIn2(Node head){
		if(head==null){
			return "#_";
		}
		String res=serialByIn2(head.left);
		res+=head.value+"_";
		res+=serialByIn2(head.right);
		return res;
	}


	public static String serialByPos1(Node head){
		if(head==null){
			return "#_";
		}
		return serialByPos1(head.left)+serialByPos1(head.right)+head.value+"_";
	}

	public static Node reconByPosString(String posStr){
		String[] strs=posStr.split("_");
		Queue<String> queue=new LinkedList<String>();
		int len=strs.length;
		for(int i=0;i<len;i++){
			queue.offer(strs[i]);
		}
		return reconPosOrder(queue);
	}

	public static Node reconPosOrder(Queue<String> queue){
		String value=queue.poll();
		if(value.equals("#")){
			return null;
		}
		Node left=reconPosOrder(queue);
		Node right=reconPosOrder(queue);
		Node head=new Node(Integer.parseInt(value));
		head.left=left;
		head.right=right;
		return head;
	}
	
	public static String serialByPos2(Node head){
		if(head==null){
			return "#_";
		}
		String res=serialByPos2(head.left);
		res+=serialByPos2(head.right);
		res+=head.value+"_";
		return res;
	}
	

	public static void preOrderUnRecur(Node root){
		Stack<Node> stack=new Stack<Node>();
		while(root!=null||!stack.isEmpty()){
			if(root!=null){
				System.out.print(root.value+"	");
				stack.push(root.right);
				root=root.left;
			} else {
				root=stack.pop();
			}
		}
		System.out.println();
	}

	public static void preOrder(Node root){
		Stack<Node> stack=new Stack<Node>();
		stack.push(root);
		while(!stack.isEmpty()){
			Node cur=stack.pop();
			while(cur!=null){
				System.out.print(cur.value+"	");
				stack.push(cur.right);
				cur=cur.left;
			}
		}
		System.out.println();
	}

	//判断一颗二叉树root2是不是另一颗二叉树root1的子树
	//第一种方法，各自生成相同遍历方式的序列，然后运用KMP算法
	//第二种方法，考虑应用递归的方式
	public static boolean isSubTree1(Node root1,Node root2){
		
		if(root1.value!=root2.value){
			
		}
		
		return true;
	}

	public static boolean isSubTree2(Node root1,Node root2){
		if(root2==null){
			return true;
		}
		if(root1==null && root2!=null){
			return false;
		}

		boolean binLeft=false;
		boolean binRight=false;
		if(root1.value==root2.value){
			binLeft=true;
			binRight=true;
			if(root2.left!=null){
				binLeft=isSubTree2(root1.left,root2.left);
			}
			if(root2.right!=null){
				binRight=isSubTree2(root1.right,root2.right);
			}
		}	
		boolean left=isSubTree2(root1.left,root2);
		boolean right=isSubTree2(root1.right,root2);
		return left || right || (binLeft && binRight);	
	}

	//折纸问题，自己实现
	public static Node genTree(int num,int n){
		if(n==0){
			return null;
		}
		//Node left=genTree(1,--n);// --n：把下面那个right的n给修改了
		//更正：
		Node left=genTree(1,n-1);
		Node root=new Node(num);
		root.left=left;
		root.right=genTree(0,n-1);
		return root;
	}
	public static void inOrderRecur(Node root){
		if(root==null){
			return;
		}
		inOrderRecur(root.left);
		System.out.print((root.value==1?"down":"up")+"	");
		inOrderRecur(root.right);
	}
	
	//将生成和中序遍历相结合，参考了左程云的写法
	public static void printFolds(int n,boolean down){
		if(n==0){
			return;
		}
		printFolds(n-1,true);
		System.out.print((down?"down":"up")+"	");
		printFolds(n-1,false);
	}

	
	//左程云实现
	public static void printAllFolds(int N){
		printProcess(1,N,true);
	}

	//递归过程，来到了某一个节点
	//i是节点的层数，N一共的层数。down==true  凹；down==false 凸
	public static void printProcess(int i,int N,boolean down){
		if(i>N){
			return;
		}
		printProcess(i+1,N,true);
		System.out.print((down?"down":"up")+"	");
		printProcess(i+1,N,false);
	}
	
	//逆转栈
	public static int reverseStack1(Stack<Integer> stack){
		return 0;
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

		Node cur=findSmallestCommonAncestor(root,root.left.left.right,root.right.left);
		System.out.println(cur.value);
		cur=findSmallestCommonAncestor(root,root.left.left.right,root.left.right);
		System.out.println(cur.value);

		System.out.println("Hello World");

		String str=serialization(root);
		System.out.println(str);
		System.out.println(serialByPre1(root));
		System.out.println(serialByPre2(root));
		System.out.println(serialByIn1(root));
		System.out.println(serialByIn2(root));
		System.out.println(serialByPos1(root));
		System.out.println(serialByPos2(root));
		System.out.println("\n\n\n\n");

		//Node deRoot=deserialization(str);
		//System.out.println(serialization(deRoot));
		Node deRoot=reconByPreString(str);
		System.out.println(serialByPre1(deRoot));
		preOrderUnRecur(root);
		preOrder(deRoot);

	
		str=serialByIn1(root);
		System.out.println(str);
		deRoot=reconByInString(str);
		str=serialByIn1(deRoot);
		System.out.println(str);
		
		str=serialByPos1(root);
		System.out.println(str);
		deRoot=reconByPosString(str);
		str=serialByPos2(deRoot);
		System.out.println(str);

		//判断一棵树是另一颗树的子树
		System.out.println("判断子树");
		System.out.println(isSubTree2(root,root.left.left));
		Node tmp=new Node(100);
		System.out.println(isSubTree2(root,tmp));
		tmp.value=1;
		tmp.left=new Node(20);
		System.out.println(isSubTree2(root,tmp));
		tmp.left.value=2;
		tmp.right=new Node(3);
		System.out.println(isSubTree2(root,tmp));
		tmp.right.value=30;
		System.out.println(isSubTree2(root,tmp));
		System.out.println("判断子树完成");

		//左程云实现		
		int N=3;
		printAllFolds(N);
		System.out.println();	

		//自己实现，按部就班，实现了树、中序遍历。而没有把树和中序遍历结合在一起
		Node root1=genTree(1,N);
		inOrderRecur(root1);
		System.out.println();
		printFolds(N,true);
		System.out.println();
	}
}


