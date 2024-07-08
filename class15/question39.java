/*************************************************************************
    > File Name: question39.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 20 22:44:42 2024
 ************************************************************************/


public class question39{


	public static int[] getPosArray(int[] pre,int[] in){
		if(pre==null){
			return null;
		}

		int N=pre.length;

		int[] pos=new int[N];

		set(pre,in,pos,0,N-1,0,N-1,0,N-1);
		
		return pos;
	}

	//利用pre[prei..prej] 结合 in[ini..inj]，填写好 pos[posi..posj]
	public static void set(int[] pre,int[] in,int[] pos,int prei,int prej,int ini,int inj,int posi,int posj){
		if(prei>prej){
			return;
		}

		if(prei==prej){// 只剩下一个数了，直接填
			pos[posi]=pre[prei];
		}

		pos[posj]=pre[prei];
		
		int find=ini;

		for(;find<=inj;find++){
			if(in[find]==pre[prei]){
				break;
			}
		}

		// in ini..find-1  find+1..inj
		set(pre,in,pos,prei+1,prei+find-ini,ini,find-1,posi,posi+find-ini-1);
		set(pre,in,pos,prei+find-ini+1,prej,find+1,inj,posi+find-ini,posj-1);
	}

	public static void printArray(int[] arr){
		for(int x:arr){
			System.out.print(x+"	");
		}
		System.out.println();
	}


	//根据二叉树的前序遍历和中序遍历。生成二叉树
	
	public static class Node{
		public int val;
		public Node left;
		public Node right;

		public Node(int v){
			val=v;
			left=right=null;
		}
	}

	public static Node generateBinaryTree(int[] pre,int[] in){
		if(pre==null){
			return null;
		}
		int len=pre.length;
		return set(pre,0,len-1,in,0,len-1);
	}
	
	//flag=0：左子树，flag=1：右子树
	public static Node set(int[] pre,int prei,int prej,int[] in,int ini,int inj){
		if(prei>prej){
			return null;
		}

		Node root=new Node(pre[prei]);
		
		int find=ini;
		for(;find<=inj;++find){
			if(in[find]==pre[prei]){
				break;
			}
		}

		root.left=set(pre,prei+1,prei+find-ini,in,ini,find-1);
		root.right=set(pre,prei+find-ini+1,prej,in,find+1,inj);
		
		return root;
	}

	public static Node generateBinaryTree1(int[] in,int[] pos){
		if(in==null){
			return null;
		}

		int len=in.length;
		return set1(in,0,len-1,pos,0,len-1);
	}

	public static Node set1(int[] in,int ini,int inj,int[] pos,int posi,int posj){
		if(ini>inj){
			return null;
		}

		Node root=new Node(pos[posj]);
		
		int find=ini;
		for(;find<=inj;++find){
			if(in[find]==pos[posj]){
				break;
			}
		}

		root.left=set1(in,ini,find-1,pos,posi,posi+find-ini-1);
		root.right=set1(in,find+1,inj,pos,posi+find-ini,posj-1);
		
		return root;
	}


	public static void preTraverse(Node root){
		if(root==null){
			return;
		}
		System.out.print(root.val+"	");
		preTraverse(root.left);
		preTraverse(root.right);
	}

	public static void inTraverse(Node root){
		if(root==null){
			return;
		}

		inTraverse(root.left);
		System.out.print(root.val+"	");
		inTraverse(root.right);
	}

	public static void posTraverse(Node root){
		if(root==null){
			return;
		}

		posTraverse(root.left);
		posTraverse(root.right);
		System.out.print(root.val+"	");
	}

	
	public static void main(String[] args){
		int[] pre={
			1,2,4,5,3,6,7
		};
		int[] in={
			4,2,5,1,6,3,7
		};

		int[] pos=getPosArray(pre,in);
		printArray(pos);
		System.out.println("\n\n\n");


		Node root=generateBinaryTree(pre,in);
		preTraverse(root);
		System.out.println("\n\n\n");
		inTraverse(root);
		System.out.println("\n\n\n");
		posTraverse(root);
		System.out.println("\n\n\n");
		
		
		System.out.println("根据中序、后序遍历生成二叉树");
		Node r=generateBinaryTree1(in,pos);
		preTraverse(r);
		System.out.println("\n\n\n");
		inTraverse(r);
		System.out.println("\n\n\n");
		posTraverse(r);
		System.out.println("\n\n\n");
		
		
		System.out.println("hello world");
	}
}
