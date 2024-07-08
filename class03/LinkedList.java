/*************************************************************************
    > File Name: LinkedList.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jan 18 21:11:53 2024
 ************************************************************************/
import java.util.Stack;
import java.util.Vector;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashSet;
public class LinkedList{

	public static class OneWayNode{
		int value;
		OneWayNode next;

		public OneWayNode(int val){
			value=val;
			next=null;

		}
	}
		
	public static class TwoWayNode{
		int value;
		TwoWayNode last;
		TwoWayNode next;
		public TwoWayNode(int val){
			value=val;
			last=null;
			next=null;
		}
	}




	
	public static OneWayNode reverseOneWayLinkedList(OneWayNode head){
		OneWayNode tmp=null;
		OneWayNode cur=head;
		OneWayNode next=null;
		while(cur!=null){
			next=cur.next;
			cur.next=tmp;
			tmp=cur;
			cur=next;
		}
		return tmp;
	}

	public static TwoWayNode reverseTwoWayLinkedList(TwoWayNode head){
		TwoWayNode last=null;
		TwoWayNode cur=head;
		TwoWayNode next=null;
		TwoWayNode tmp=null;
		while(cur!=null){
			next=cur.next;
			cur.next=last;
			cur.last=next;

			last=cur;
			cur=next;
		}
		return last;
	}

	public static void printCommonOfTwoLists(OneWayNode head1,OneWayNode head2){
		OneWayNode list1=head1;
		OneWayNode list2=head2;
		int val1=0,val2=0;
		while((list1!=null)&&(list2!=null)){
			val1=list1.value;
			val2=list2.value;
			if(val1==val2){
				System.out.println(val1);
				list1=list1.next;
				list2=list2.next;
			}
			else if(val1<val2)
				list1=list1.next;
			else
				list2=list2.next;
		}
	}
	
	public static int getListLen(OneWayNode head){
		int len=0;
		while(head!=null){
			len++;
			head=head.next;
		}
		return len;
	}

	public static boolean isPalList(OneWayNode head){
		int len=getListLen(head);
		if(len<2)
			return true;
		int steps=len/2;
		Stack<Integer> stack=new Stack<>();
		while(steps>0){
			stack.push(head.value);
			head=head.next;
			steps--;
		}
		if(len%2==1)
			head=head.next;

		//判断回文数字
		while(head!=null){
			int tmp1=head.value;
			int	tmp2=stack.pop();
			if(tmp1!=tmp2)
				return false;
			head=head.next;
		}
		return true;
	}


	//额外空间复杂度O(N)
	public static boolean isPalList1(OneWayNode head){
		if((head==null)||(head.next==null))
			return true;
		OneWayNode slow=head;
		OneWayNode fast=head;
		//快指针一次走两步，慢指针一次走一步，那么当快指针走到尾部的时候，慢指针恰好到链表中间位置
		
		Stack<Integer> stack=new Stack<>();
		OneWayNode old=head;
		while(fast.next!=null){
			slow=slow.next;
			fast=fast.next;
			if(fast.next!=null)
				fast=fast.next;
		}
		
		//压栈
		while(slow!=null){
			stack.push(slow.value);
			slow=slow.next;
		}
		while(!stack.isEmpty()){
			int tmp1=stack.pop();
			int tmp2=old.value;
			if(tmp1!=tmp2)
				return false;
			old=old.next;
		}
		return true;
	}

	public static boolean isPalList2(OneWayNode head){
		if((head==null)||(head.next==null))
			return true;
		int len=getListLen(head);
		int steps=(int)Math.ceil(len/2.0);
		OneWayNode old=head;
		OneWayNode last=null;
		while(steps>0){
			last=head;
			head=head.next;
			steps--;
		}
	
		//使用(int)Math.ceil(len/2.0)
		/*
		if(len%2!=0){
			last=head;
			head=head.next;
		}
		*/

		boolean flag=true;
		head=reverseOneWayLinkedList(head);
		OneWayNode save=head;
		//printList1(head);
		while(head!=null){
			if(head.value!=old.value){
				flag=false;
				break;
			}
			head=head.next;
			old=old.next;
		}
		last.next=reverseOneWayLinkedList(save);
		return flag;
	}
	
	public static boolean isPalList3(OneWayNode head){
		if(head==null||head.next==null)
			return true;
		OneWayNode n1=head;
		OneWayNode n2=head;

		while(n2.next!=null&&n2.next.next!=null){// find mid node，快指针还能跳两步。无论奇数、偶数，都能走到中点
			n1=n1.next;// n1 -> mid
			n2=n2.next.next;// n2 -> end
		}

		n2=n1.next;// n2 --> right part first node
		n1.next=null;// mid.next=null
		OneWayNode n3=null;
		while(n2!=null){//链表反转
			n3=n2.next;
			n2.next=n1;
			n1=n2;
			n2=n3;
		}

		n3=n1;//n3 --> save last node
		n2=head;//n2 --> left first node
		boolean res=true;
		while(n1!=null&&n2!=null){
			if(n1.value!=n2.value){
				res=false;
				break;
			}
			n1=n1.next;
			n2=n2.next;
		}

		//再反转链表，因为n1是一体的，所以可以直接反转
		n1=null;
		n2=null;
		while(n3!=null){
			n2=n3.next;
			n3.next=n1;
			n1=n3;
			n3=n2;
		}
		return res;
	}

	

	public static void printList1(OneWayNode head){
		while(head!=null){
			System.out.println(head.value);
			head=head.next;
		}
	}

	public static void printList2(TwoWayNode head){
		while(head!=null){
			System.out.println(head.value);
			head=head.next;
		}
	}

	public static OneWayNode createOneWayList(int[] arr){
		int len=arr.length;
		if(len<1)
			return null;
		OneWayNode head=new OneWayNode(arr[0]);
		OneWayNode last=head;
		for(int i=1;i<len;i++){
			OneWayNode cur=new OneWayNode(arr[i]);
			last.next=cur;
			last=cur;
		}
		return head;
	}

	public static TwoWayNode createTwoWayList(int[] arr){
		int len=arr.length;
		if(len<1)
			return null;
		TwoWayNode head=new TwoWayNode(arr[0]);
		TwoWayNode last=head;
		for(int i=1;i<len;i++){
			TwoWayNode cur=new TwoWayNode(arr[i]);
			last.next=cur;
			cur.last=last;
			last=cur;
		}
		return head;
	}

	public static void swap(Vector<Integer> vec,int i,int j){
		int tmp=vec.get(i);
		vec.set(i,vec.get(j));
		vec.set(j,tmp);
	}
	public static void divideList(OneWayNode head,int pivot){
		Vector<Integer> vec=new Vector<Integer>();
		OneWayNode cur=head;
		while(cur!=null){
			vec.add(cur.value);
			cur=cur.next;
		}
		int less=-1;
		int more=vec.size();
		for(int i=0;i<more;){
			if(vec.get(i)<pivot)
				swap(vec,++less,i++);
			else if(vec.get(i)==pivot)
				i++;
			else
				swap(vec,--more,i);
		}
		
		int index=0;
		while(head!=null){
			head.value=vec.get(index++);
			head=head.next;
		}
	}

	public static void divideList1(OneWayNode head,int pivot){
		TreeSet<Integer> treeSet=new TreeSet<Integer>();
		OneWayNode old=head;
		while(old!=null){
			treeSet.add(old.value);
			old=old.next;
		}

		while(head!=null){
			head.value=treeSet.pollFirst();
			head=head.next;
		}
	}

	//左程云思路
	public static void swap2(Vector<OneWayNode> vec,int i,int j){
		OneWayNode tmp=vec.get(i);
		vec.set(i,vec.get(j));
		vec.set(j,tmp);
	}
	public static OneWayNode divideList2(OneWayNode head,int pivot){
		Vector<OneWayNode> vec=new Vector<OneWayNode>();
		while(head!=null){
			vec.add(head);
			head=head.next;
		}

		int less=-1;
		int len=vec.size();
		int more=len;
		for(int i=0;i<more;){
			if(vec.get(i).value<pivot){
				swap2(vec,++less,i++);
			} else if(vec.get(i).value==pivot){
				i++;
			} else {
				swap2(vec,--more,i);
			}
		}

		//穿起来
		for(int i=0;i<len-1;){
			vec.get(i).next=vec.get(++i);
		}
		vec.get(len-1).next=null;
		return vec.get(0);
	}

	public static OneWayNode divideList3(OneWayNode head,int pivot){
		OneWayNode lessHead=null;
		OneWayNode lessTail=null;

		OneWayNode equalHead=null;
		OneWayNode equalTail=null;

		OneWayNode moreHead=null;
		OneWayNode moreTail=null;
		
		OneWayNode tmp=null;
		while(head!=null){
			tmp=head.next;
			head.next=null;
			if(head.value<pivot){
				if(lessHead==null){
					lessHead=head;
					lessTail=head;
				} else {
					lessTail.next=head;
					lessTail=head;
				}
			} else if(head.value==pivot){
				if(equalHead==null){
					equalHead=head;
					equalTail=head;
				} else {
					equalTail.next=head;
					equalTail=head;
				}
			} else {
				if(moreHead==null){
					moreHead=head;
					moreTail=head;
				} else {
					moreTail.next=head;
					moreTail=head;
				}
			}
			head=tmp;
		}

		/*
		if(lessHead!=null){
			if(equalHead!=null){
				lessTail.next=equalHead;
				lessTail=equalTail;
			}
			lessTail.next=moreHead;
			head=lessHead;	
		} else if(equalHead!=null){
			head=equalHead;
			equalTail.next=moreHead;
		} else {
			head=moreHead;
		}*/

		if(lessTail!=null){//如果有小于区域
			lessTail.next=equalHead;
			equalTail=equalHead==null?lessTail:equalTail;//下一步，谁去连大于区域的头，谁就变成eT
		}
		if(equalHead!=null)
			equalTail.next=moreHead;//如果有小于区域和等于区域，不是都没有

		return lessHead!=null?lessHead:(equalHead!=null?equalHead:moreHead);
	}

	public static class Node{
		int value;
		Node next;
		Node rand;
		Node(int val){
			value=val;
		}
	}

	//使用HashMap进行映射，计算
	public static Node copyList(Node head){
		if(head==null)
			return null;
		HashMap<Node,Node> hashMap=new HashMap<Node,Node>();
		Node old=head;
		Node tmp=null;
		while(old!=null){
			tmp=new Node(old.value);
			hashMap.put(old,tmp);
			old=old.next;
		}

		Node copyHead=hashMap.get(head);
		Node cycleVar=copyHead;
		while(head!=null){
			cycleVar.next=hashMap.get(head.next);
			cycleVar.rand=hashMap.get(head.rand);
			head=head.next;
			cycleVar=cycleVar.next;
		}
		return copyHead;
	}

	public static Node copyList2(Node head){
		HashMap<Node,Node> hashMap=new HashMap<Node,Node>();
		Node cur=head;
		while(cur!=null){
			hashMap.put(cur,new Node(cur.value));
			cur=cur.next;
		}

		cur=head;
		while(cur!=null){
			hashMap.get(cur).next=hashMap.get(cur.next);
			hashMap.get(cur).rand=hashMap.get(cur.next);
			cur=cur.next;
		}
		return hashMap.get(head);
	}
	
	//不使用哈希表
	//利用克隆节点放的位置关系，实现一一对应，省掉了哈希票
	public static Node copyList3(Node head){
		if(head==null)
			return null;
		Node cur=head;
		Node next=null;
		Node copy=null;
		while(cur!=null){
			copy=new Node(cur.value);
			next=cur.next;
			cur.next=copy;
			copy.next=next;
			cur=next;
		}

		//处理rand指针
		cur=head;
		while(cur!=null){
			copy=cur.next;
			copy.rand=(cur.rand!=null?cur.rand.next:null);
			cur=cur.next.next;
		}
		
		//分离新链表和老链表
		cur=head;
		Node copyHead=head.next;
		copy=copyHead;
		cur=cur.next.next;
		while(cur!=null){
			head.next=cur;
			copy.next=cur.next;
			copy=copy.next;
			head=head.next;
			cur=cur.next.next;
		}
		return copyHead;
	}

	//左程云版本
	public static Node copyList4(Node head){
		if(head==null)
			return null;
		Node cur=head;
		Node next=null;
		//copy node and link to every node
		//1->2
		//1->1'->2->2'
		while(cur!=null){
			next=cur.next;
			cur.next=new Node(cur.value);
			cur.next.next=next;
			cur=next;
		}

		cur=head;
		Node curCopy=null;
		//set copy node rand
		//1->1'->2->2'
		while(cur!=null){
			next=cur.next.next;
			curCopy=cur.next;
			curCopy.rand=cur.rand!=null?cur.rand.next:null;
			cur=next;
		}

		Node res=head.next;
		cur=head;
		//split
		while(cur!=null){
			next=cur.next.next;
			curCopy=cur.next;
			cur.next=next;
			curCopy.next=next!=null?next.next:null;
			cur=next;
		}
		return res;
	}

	//根据之前(Carl公众号)对这道题的记忆，写出来的,使用数学分析和快慢指针
	public static Node isHasRing(Node head){
		if(head==null||head.next==null)
			return null;
		Node slow=head;
		Node fast=head.next.next;
		while((slow!=fast)){
			slow=slow.next;
			fast=fast==null?null:fast.next;
			fast=fast==null?null:fast.next;
		}
		if(fast==null)
			return null;

		slow=head;
		while(slow!=fast){
			slow=slow.next;
			fast=fast.next;
		}
		return slow;//or return fast;
	}

	//优化后的代码
	public static Node isHasRing1(Node head){
		Node slow=head;
		Node fast=head;
		//快指针一次走两步，如果没有环，快指针肯定先到达链表结尾
		while((fast!=null)&&(fast.next!=null)){
			slow=slow.next;
			fast=fast.next.next;
			if(slow==fast)
				break;
		}

		fast=head;
		while(slow!=fast){
			slow=slow.next;
			fast=fast.next;
		}
		return slow;//or return fast;
	}

	//使用哈希表
	public static Node isHasRing2(Node head){
		HashSet<Node> hashSet=new HashSet<Node>();
		Node cur=head;
		while((cur!=null)&&(hashSet.contains(cur)==false)){
			hashSet.add(cur);
			cur=cur.next;
		}
		return cur;
	}
	
	public static Node getLoopNode(Node head){
		if(head==null||head.next==null||head.next.next==null)
			return null;
		Node n1=head;
		Node n2=head;
		while(n1!=n2){
			if(n2.next==null||n2.next.next==null)
				return null;
			n1=n1.next;
			n2=n2.next.next;
		}
		
		n2=head;// n2 -> walk again from head
		while(n1!=n2){
			n1=n1.next;
			n2=n2.next;
		}
		return n1;
	}
	
	//如果两个链表都无环，返回第一个相交节点，如果不相交，返回null
	//使用哈希表
	public static Node noLoop1(Node head1,Node head2){
		if(head1==null||head2==null)
			return null;
		HashSet<Node> hashSet=new HashSet<>();
		while(head1!=null){
			hashSet.add(head1);
			head1=head1.next;
		}

		while((head2!=null)&&(hashSet.contains(head2)==false))
			head2=head2.next;
		return head2;
	}

	//不使用哈希表
	public static Node noLoop2(Node head1,Node head2){
		//借助反转链表
		if(head1==null||head2==null)
			return null;
		Node cur1=head1;
		Node cur2=head2;
		int len1=1;
		while(cur1.next!=null){
			len1++;
			cur1=cur1.next;
		}

		int len2=1;
		while(cur2.next!=null){
			cur2=cur2.next;
			len2++;
		}
		
		if(cur1!=cur2)
			return null;

		int steps=len1-len2;
		cur1=head1;
		cur2=head2;
		if(len1<len2){
			steps=len2-len1;
			cur1=head2;
			cur2=head1;
		}
		
		while(steps>0){
			cur1=cur1.next;
			steps--;
		}

		while(cur1!=cur2){
			cur1=cur1.next;
			cur2=cur2.next;
		}

		return cur1;//or return cur2;
	}

	//左程云版本
	public static Node noLoop3(Node head1,Node head2){
		if(head1==null||head2==null)
			return null;
		Node cur1=head1;
		Node cur2=head2;
		int n=0;
		while(cur1.next!=null){//不是走到空停，而是来到最后一个节点停
			n++;
			cur1=cur1.next;
		}
		while(cur2.next!=null){
			n--;
			cur2=cur2.next;
		}
		if(cur1!=cur2)
			return null;
		//n :链表1长度减去链表2长度的值
		cur1=n>0?head1:head2;//谁长，谁的头变成cur1
		cur2=cur1==head1?head2:head1;//谁短，谁的头变成cur2
		n=Math.abs(n);
		while(n!=0){
			n--;
			cur1=cur1.next;
		}
		while(cur1!=cur2){
			cur1=cur1.next;
			cur2=cur2.next;
		}
		return cur1;//or return cur2
	}

	//两个有环链表，返回第一个相交节点，如果不相交返回null
	public static Node bothLoop(Node head1,Node head2,Node loop1,Node loop2){
		Node res=null;
		if(loop1==loop2){
			Node cur1=head1;
			Node cur2=head2;
			int n=0;
			while(cur1!=loop1){
				cur1=cur1.next;
				n++;
			}

			while(cur2!=loop2){
				cur2=cur2.next;
				n--;
			}

			cur1=n>0?head1:head2;
			cur2=cur1==head1?head2:head1;
			n=Math.abs(n);
			while(n>0){
				cur1=cur1.next;
				n--;
			}
			while(cur1!=cur2){
				cur1=cur1.next;
				cur2=cur2.next;
			}
			res=cur1;
		} else {
			Node next=loop1.next;
			while(next!=loop1){
				if(next==loop2){
					res=loop1;//or res=loop2;
				}
				next=next.next;
			}
			res=null;
		}
		return res;
	}

	public static Node bothLoop1(Node head1,Node head2,Node loop1,Node loop2){
		Node cur1=null;
		Node cur2=null;
		if(loop1==loop2){
			cur1=head1;
			cur2=head2;
			int n=0;
			while(cur1!=loop1){
				n++;
				cur1=cur1.next;
			}
			while(cur2!=loop2){
				n--;
				cur2=cur2.next;
			}
			cur1=n>0?head1:head2;
			cur2=cur1==head1?head2:head1;
			n=Math.abs(n);
			while(n!=0){
				n--;
				cur1=cur1.next;
			}
			while(cur1!=cur2){
				cur1=cur1.next;
				cur2=cur2.next;
				
			}
			return cur1;//or return cur2;
		} else {
			cur1=loop1.next;
			while(cur1!=loop1){
				if(cur1==loop2)
					return loop1;
				cur1=cur1.next;
			}
			return null;
		}
	}

	public static Node getIntersectNode(Node head1,Node head2){
		if(head1==null||head2==null)
			return null;
		Node loop1=getLoopNode(head1);
		Node loop2=getLoopNode(head2);
		if(loop1==null&&loop2==null)
			return noLoop3(head1,head2);
		if(loop1!=null&&loop2!=null)
			return bothLoop1(head1,head2,loop1,loop2);
		return null;
	}


	public static void main(String[] args){
		OneWayNode node1=new OneWayNode(10);
		OneWayNode node2=new OneWayNode(20);
		System.out.println(node1.value);
		System.out.println(node2.value);

		node1=node2;
		System.out.println(node1.value);
		System.out.println(node2.value);
		System.out.println("Hello World");
		
		//反转单向链表
		int[] arr={1,2,3,4,5,6};
		OneWayNode list1=createOneWayList(arr);
		printList1(list1);
		OneWayNode list2=reverseOneWayLinkedList(list1);
		printList1(list2);
		System.out.println("\n\n\n\n");

		
		//反转双向链表
		TwoWayNode list3=createTwoWayList(arr);
		printList2(list3);
		TwoWayNode list4=reverseTwoWayLinkedList(list3);
		printList2(list4);
		
		System.out.println("\n\n\nHello World");
		for(int i=4;i<2;i++)
			System.out.println(i);

		//判断是否回文链表
		int[] arr1={1,2,3,2,1};
		int[] arr2={1,2,3,4,5};
		int[] arr3={1,2,3,3,2,1};
		OneWayNode list11=createOneWayList(arr1);
		OneWayNode list22=createOneWayList(arr2);
		OneWayNode list33=createOneWayList(arr3);
		System.out.println(isPalList3(list11));
		printList1(list11);
		System.out.println("\n\n\n");

		System.out.println(isPalList3(list22));
		printList1(list22);
		System.out.println("\n\n\n");

		System.out.println(isPalList3(list33));
		printList1(list33);
		System.out.println("\n\n\n");

		int[] arr4={1,2,3,4,6,8,10,6};
		int pivot=6;
		OneWayNode list5=createOneWayList(arr4);
		divideList3(list5,pivot);
		//divideList1(list5,pivot);//这个算法有错误，TreeSet是不重复有序集合
		printList1(list5);

		//TreeSet、TreeMap都是不重复的
		/*
		TreeSet<Integer> treeSet=new TreeSet<Integer>();
		treeSet.add(1);
		treeSet.add(2);
		treeSet.add(5);
		treeSet.add(2);
		while(!treeSet.isEmpty()){
			System.out.println(treeSet.pollFirst());
		}
		*/


	}
}

