/*************************************************************************
    > File Name: AVLTree.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Apr 30 22:04:34 2024
 ************************************************************************/

import java.lang.Comparable;
import java.util.Random;
import java.util.Stack;

public class AVLTree{

	public static class Node<K extends Comparable<K>,V>{
		public Node<K,V> parent;
		public Node<K,V> left;
		public Node<K,V> right;
		public K key;
		public V val;
		public int height;

		public Node(K k,V v){
			parent=left=right=null;
			val=v;
			key=k;
			height=0;
		}

		public boolean isKeyEqual(K otherKey){
			return (key==null&&otherKey==null)||(key!=null&&otherKey!=null&&key.compareTo(otherKey)==0);
		}

		public boolean isKeyLess(K otherKey){
			return (otherKey==null)&&(key==null||key.compareTo(otherKey)<0);
		}

		public boolean isKeyGreater(K otherKey){
			return (key!=null)&&(otherKey==null||key.compareTo(otherKey)>0);
		}
	}
	

	public static class AVL<K extends Comparable<K>,V>{
		private Node<K,V> root;//根节点
		private int size;//节点个数

		public AVL(){
			root=null;
			size=0;
		}

		public AVL(K key,V val){
			root=new Node<K,V>(key,val);
			size=1;
		}
	
		public boolean insertNode(K key,V val){
			if(root==null){
				root=new Node<K,V>(key,val);
				System.out.println("Insert Successfully");
				++size;
				return true;
			}

			Node<K,V> p=null;
			Node<K,V> c=root;
			while(c!=null){
				p=c;
				if(c.isKeyEqual(key)){
					System.out.println("Already Exists and Insert failed");
					return false;
				} else if(c.isKeyEqual(key)){
					c=c.right;
				} else {
					c=c.left;
				}
			}
			
			//可以插入节点
			Node<K,V> node=new Node<K,V>(key,val);
			if(p.isKeyLess(key)){//key比p的key大
				p.right=node;
			} else {
				p.left=node;
			}
			node.parent=p;
			checkBalance(p);
			++size;
			System.out.println("Insert Successfully");
			return true;
		}

		public boolean deleteNode(K key){
			Node<K,V> p=null;
			Node<K,V> c=root;
			while(c!=null){
				if(c.isKeyEqual(key)){
					p=delete(p,c);//返回要检查平衡性的起始点
					System.out.println("Delete Successfully");
					return true;
				} else if(c.isKeyLess(key)){
					p=c;
					c=c.right;
				} else {
					p=c;
					c=c.left;
				}
			}

			System.out.println("Not Found and Delete Successfully");
			return false;	
		}

		private Node<K,V> delete(Node<K,V> parent,Node<K,V> child){
			Node<K,V> res=null;
			if(child.left!=null&&child.right!=null){
				//找右边最小的
				Node<K,V> ch=child.right;
				Node<K,V> p=child;
				while(ch.left!=null){
					p=ch;
					ch=ch.left;
				}
				p.left=ch.right;
				if(ch.right!=null){
					ch.right.parent=p;
				}
				return p;

			} else if(child.left!=null){
				res=child.left;
			} else{
				res=child.right;
			}
			
			if(res!=null){
				res.parent=parent;
			}
			if(parent.left==child){
				parent.left=res;
			} else{
				parent.right=res;
			}
			return parent;
		}

	
		public boolean changeNode(K key,V val){
			Node<K,V> tmp=root;
			while(tmp!=null){
				if(tmp.isKeyEqual(key)){
					tmp.val=val;
					System.out.println("Change Successfully");
					return true;
				}
				if(tmp.isKeyLess(key)){
					tmp=tmp.right;
				} else {
					tmp=tmp.left;
				}
			}
			System.out.println("Not Found");
			return false;
		}

		public boolean searchNode(K key){
			Node<K,V> tmp=root;
			while(tmp!=null){
				if(tmp.isKeyEqual(key)){
					System.out.println("Found,key:"+key+" val:"+tmp.val);
					return true;
				}
				if(tmp.isKeyLess(key)){
					tmp=tmp.right;
				} else {
					tmp=tmp.left;
				}
			}
			System.out.println("Not Found");
			return false;	
		}

		public int getSize(){
			return size;
		}

		public boolean empty(){
			return size==0;
		}

		public void showAVL(){
			
			
		}

		//利用后续遍历检查平衡性
		public boolean isBalance(){
			Stack<Node<K,V>> stack=new Stack<>();
			Node<K,V> rootTmp=root;
			Node<K,V> pre=null;
			while(rootTmp!=null||!stack.empty()){
				if(rootTmp!=null){
					stack.push(rootTmp);
					rootTmp=rootTmp.left;
				} else {
					rootTmp=stack.peek();
					if(rootTmp.right==null||rootTmp.right==pre){
						stack.pop();
						//检测是否平衡
						int l=rootTmp.left==null?0:rootTmp.left.height;
						int r=rootTmp.right==null?0:rootTmp.right.height;
						if(Math.abs(l-r)>1){
							return false;
						}
						pre=rootTmp;
						rootTmp=null;
						continue;
					}
					rootTmp=rootTmp.right;
				}
			}
			return true;
		}
		
		//检查平衡性
		private void checkBalance(Node<K,V> node){
			Node<K,V> p=null;
			while(node!=null){
				p=node.parent;
				int lh=node.left==null?0:node.left.height;
				int rh=node.right==null?0:node.right.height;
				if(Math.abs(lh-rh)<=1){
					node.height=1+Math.max(lh,rh);
					node=p;
					continue;
				} 
				
				//开始区分破坏平衡的四种操作：ll、lr、rr、rl
				if(lh>rh){//ll、lr
					Node<K,V> left=node.left;
					int l=left.left==null?0:left.left.height;
					int r=left.right==null?0:left.right.height;
					if(l>r){//ll
						//对node做一次右旋
						rightRotate(node);
					} else{//lr
						leftRotate(left);//对left左旋
						rightRotate(node);//对node右旋
					}
				} else {//rr、rl
					Node<K,V> right=node.right;
					int l=right.left==null?0:right.left.height;
					int r=right.right==null?0:right.right.height;
					if(l>r){//rl
						rightRotate(right);//对right右旋
						leftRotate(node);//对node左旋
					} else {//rr
						//对node做一次左旋
						leftRotate(node);
					}
				}
				node=p;
			}
			
		}

		private void rightRotate(Node<K,V> node){
			Node<K,V> newParent=node.left;
			
			Node<K,V> newNode=new Node<K,V>(node.key,node.val);
			newNode.right=node.right;
			if(node.right!=null){
				node.right.parent=newNode;
			}
			newNode.left=newParent.right;
			if(newParent.right!=null){
				newParent.right.parent=newNode;
			}
			setNodeHeight(newNode);

			//设置新父节点的右子树
			newParent.right=newNode;
			newNode.parent=newParent;

			//开始对node赋值
			deepCopy(node,newParent);
		}

		private void leftRotate(Node<K,V> node){
			Node<K,V> newParent=node.right;
			
			Node<K,V> newNode=new Node<K,V>(node.key,node.val);
			newNode.left=node.left;
			if(node.left!=null){
				node.left.parent=newNode;
			}
			newNode.right=newParent.left;
			if(newParent.left!=null){
				newParent.left.parent=newNode;
			}
			setNodeHeight(newNode);

			//对newParent的左子树赋值
			newParent.left=newNode;
			newNode.parent=newParent;

			//开始对node赋值
			deepCopy(node,newParent);
		}
	
		private void setNodeHeight(Node<K,V> node){
			if(node==null){
				return;
			}

			int lh=node.left==null?0:node.left.height;
			int rh=node.right==null?0:node.right.height;
			node.height=1+Math.max(lh,rh);
		}
		
		private void deepCopy(Node<K,V> dst,Node<K,V> src){
			dst.left=src.left;
			dst.right=src.right;
			dst.key=src.key;
			dst.val=src.val;
			setNodeHeight(dst);
		}
	}
	
	public static void main(String[] args){
		AVL<Integer,Integer> avl=new AVL<Integer,Integer>();
		Random rand=new Random();

		System.out.println("插入");
		for(int i=0;i<10;i++){
			avl.insertNode(rand.nextInt(20),rand.nextInt(100));
			if(!avl.isBalance()){
				System.out.println("不平衡");
			}
		}

		for(int i=0;i<10;i++){
			avl.insertNode(i,rand.nextInt(100));
			if(!avl.isBalance()){
				System.out.println("不平衡");
			}
		}
		System.out.println("\n\n\n");

		System.out.println("搜索");
		for(int i=0;i<10;i++){
			avl.searchNode(i);	
			avl.searchNode(rand.nextInt(20));
		}
		System.out.println("\n\n\n");

		System.out.println("修改");
		for(int i=0;i<10;i++){
			avl.changeNode(i,i*10);
			avl.changeNode(rand.nextInt(20),rand.nextInt(1000));
		}
		System.out.println("\n\n\n");
		
		System.out.println("删除");
		for(int i=0;i<10;i++){
			avl.deleteNode(i);
			if(!avl.isBalance()){
				System.out.println("不平衡");
			}
			avl.deleteNode(rand.nextInt(20));
			if(!avl.isBalance()){
				System.out.println("不平衡");
			}
		}
		System.out.println("\n\n\n");
		
		System.out.println("hello world");
	}
}



