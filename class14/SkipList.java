/*************************************************************************
    > File Name: SkipList.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May  1 17:58:39 2024
 *c ***********************************************************************/
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.Random;
import java.util.Iterator;
import java.lang.reflect.Array;

public class SkipList{
	
	public static class Person implements Comparator<Person>{
		@Override
		public int compare(Person o1,Person o2){
			return o1.getAge()-o2.getAge();
		}
		
		public int getAge(){
			return age;
		}
		
		private int age;

	}
	
	// K 必须保证是可以比较大
	public static class SkipListNode<K extends Comparable<K>,V>{
		public K key;
		public V val;

		public ArrayList<SkipListNode<K,V>> nextNodes;
		
		public SkipListNode(K k,V v){
			key=k;
			val=v;
			nextNodes=new ArrayList<SkipListNode<K,V>>();
		}

		public boolean isKeyLess(K otherKey){
			return otherKey!=null&&(key==null||key.compareTo(otherKey)<0);
		}

		public boolean isKeyEqual(K otherKey){
			return (key==null&&otherKey==null)||(key!=null&&otherKey!=null&&key.compareTo(otherKey)==0);
		}
	}
	
	public static class SkipListMap<K extends Comparable<K>,V>{
		private static final double PROBABILITY=0.5;
		private SkipListNode<K,V> head;//所有节点的根节点，认为最小,全局最小key，默认无穷小
		private int size;
		private int maxLevel;

		public SkipListMap(){
			head=new SkipListNode<K,V>(null,null);
			head.nextNodes.add(null);
			size=0;
			maxLevel=1;
		}

		private SkipListNode<K,V> mostRightLessNodeInTree(K key){
			if(key==null){
				return null;
			}

			int level=maxLevel;
			SkipListNode<K,V> cur=head;
			while(level>=0){
				cur=mostRightLessNodeInLevel(key,cur,level--);
			}
			return cur;
		}
		//出现多少个1就多高，但是要一直出现1，直到出现0
		//只有默认节点会扩充节点，默认节点初始化为0层
		//默认节点跟着最高节点扩充
		
		private SkipListNode<K,V> mostRightLessNodeInLevel(K key,SkipListNode<K,V> cur,int level){
			while(cur.nextNodes.get(level)!=null&&cur.nextNodes.get(level).key.compareTo(key)<0){
				cur=cur.nextNodes.get(level);
			}
			return cur;
		}
		
		//产生高度
		private int getRandHeight(){
			Random rand=new Random();
			int cn=0;
			while(rand.nextInt(10)>=5){//[0,9)，大于等于5就加1
				++cn;
			}
			return cn==0?1:cn;
		}

		//如果有就返回false，如果无就插入
		public boolean insertNode(K key,V val){
			//SkipListNode<K,V>[] updates=new SkipListNode<K,V>[maxLevel];
			//SkipListNode<K,V>[] updates=(SkipListNode<K,V>[])Array.newInstance(SkipListNode<K,V>.class,maxLevel);
			ArrayList<SkipListNode<K,V>> updates=new ArrayList<>(maxLevel);
			for(int i=0;i<maxLevel;i++){
				updates.add(null);
			}
			SkipListNode<K,V> cur=head;
			for(int i=maxLevel-1;i>=0;--i){
				while(cur.nextNodes.get(i)!=null&&cur.nextNodes.get(i).key.compareTo(key)<0){
					cur=cur.nextNodes.get(i);
				}
				updates.set(i,cur);
			}
			
			if(cur.nextNodes.get(0)!=null&&cur.nextNodes.get(0).key.compareTo(key)==0){
				System.out.println("already exists and insert failed");
				return false;
			}

			int h=getRandHeight();
			int min=maxLevel<=h?maxLevel:h;
			SkipListNode<K,V> node=new SkipListNode<K,V>(key,val);
			for(int i=0;i<min;++i){
				node.nextNodes.add(updates.get(i).nextNodes.get(i));
				updates.get(i).nextNodes.set(i,node);
			}

			if(h>maxLevel){
				for(int i=maxLevel;i<h;i++){
					head.nextNodes.add(node);
					node.nextNodes.add(null);
				}
				maxLevel=h;
			}

			++size;
			System.out.println("insert successfully");
			return true;	
		}

		//删除
		public boolean deleteNode(K key){
			//SkipListNode<K,V>[] updates=new (SkipListNode<K,V>[])Object[maxLevel];
			ArrayList<SkipListNode<K,V>> updates=new ArrayList<>(maxLevel);
			for(int i=0;i<maxLevel;i++){
				updates.add(null);
			}
			SkipListNode<K,V> cur=head;
			for(int i=maxLevel-1;i>=0;--i){
				while(cur.nextNodes.get(i)!=null&&cur.nextNodes.get(i).key.compareTo(key)<0){
					cur=cur.nextNodes.get(i);
				}
				updates.set(i,cur);
			}
			
			cur=cur.nextNodes.get(0);
			if(cur!=null&&cur.key.compareTo(key)==0){
				cur=cur.nextNodes.get(0);
				int level=cur.nextNodes.size();
				for(int i=0;i<level;++i){
					updates.get(i).nextNodes.set(i,cur.nextNodes.get(i));
				}
				
				while(head.nextNodes.get(maxLevel-1)==null){
					head.nextNodes.remove(maxLevel-1);
					maxLevel-=1;
				}
				
				--size;
				System.out.println("Delete successfully");
				return true;
			}
			
			System.out.println("Delete fialed");
			return false;
		}
		
		//修改
		public boolean changeNode(K key,V val){
			SkipListNode<K,V> cur=head;
			for(int i=maxLevel-1;i>=0;--i){
				while(cur.nextNodes.get(i)!=null&&cur.nextNodes.get(i).key.compareTo(key)<0){
					cur=cur.nextNodes.get(i);
				}
			}
			
			cur=cur.nextNodes.get(0);
			if(cur!=null&&cur.key.compareTo(key)==0){
				cur.val=val;
				System.out.println("change successfully");
				return true;
			}

			System.out.println("Not Found and Change failed");
			return false;
		}

		//搜索
		public boolean searchNode(K key){
			SkipListNode<K,V> cur=head;
			for(int i=maxLevel-1;i>=0;--i){
				while(cur.nextNodes.get(i)!=null&&cur.nextNodes.get(i).key.compareTo(key)<0){
					cur=cur.nextNodes.get(i);
				}
				if(cur.nextNodes.get(i)!=null&&cur.nextNodes.get(i).key.compareTo(key)==0){
					System.out.println("Found:("+key+","+cur.nextNodes.get(i).val+")");
					return true;
				}
			}
			System.out.println("Not Found!");
			return false;
		}

		
		public int getSize(){
			return size;
		}
		
		public void showList(){
			for(int i=maxLevel-1;i>=0;--i){
				SkipListNode<K,V> cur=head.nextNodes.get(i);
				while(cur!=null){
					System.out.print(cur.key+"	");
					cur=cur.nextNodes.get(i);
				}
				System.out.println();
			}
		}
		

	}


	public static void main(String[] args){
		
		
		//测试ArrayList
		ArrayList<Integer> arrayList=new ArrayList<Integer>();
		arrayList.add(1);
		arrayList.add(-1);
		for(int i=0;i<arrayList.size();i++){
			System.out.print(arrayList.get(i)+" ");
		}
		System.out.println("\n测试跳表：");
		
		Random rand=new Random();
		SkipListMap<Integer,Integer> skipList=new SkipListMap<Integer,Integer>();
		for(int i=0;i<10;i++){
			skipList.insertNode(i,rand.nextInt(100));
		}
		
		skipList.searchNode(0);
		skipList.searchNode(3);
		skipList.searchNode(8);
		skipList.searchNode(100);
		skipList.searchNode(-1);

		skipList.deleteNode(0);
		skipList.deleteNode(7);
		skipList.deleteNode(-1);
		skipList.deleteNode(1000);
		skipList.searchNode(7);

		skipList.searchNode(9);
		skipList.changeNode(9,100);
		skipList.changeNode(100,20);

		System.out.println("hello world");
	}
}



