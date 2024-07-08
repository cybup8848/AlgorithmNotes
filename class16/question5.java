/*************************************************************************
    > File Name: question5.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jun 11 13:54:21 2024
 ************************************************************************/

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class question5{

	//自己实现
	public static int get(int n,int[] m){
		if(n<=0||m==null||m.length==0){
			return -1;
		}
		
		LinkedList<Integer> list=new LinkedList<Integer>();
		for(int i=0;i<n;i++){
			list.addLast(i);
		}

		int numIndex=0;
		int start=0;
		for(int i=1;i<n;i++){
			int size=list.size();
			int index=(start+m[numIndex]-1)%size;
			list.remove(index);
			numIndex=(numIndex+1)%m.length;
			start=index%(size-1);
		}
		
		return list.getFirst();
	}

	//链表节点
	public static class Node{
		int val;
		Node next;
	}

	//左程云实现
	public static Node josephusKill2(Node head,int m){
		if(head==null||head.next==head||m<1){
			return head;
		}

		Node cur=head.next;
		int tmp=1;//tmp --》 list size
		while(cur!=head){
			++tmp;
			cur=cur.next;
		}

		tmp=getLive(tmp,m);// tmp --> service node position，直接算出活下来的编号
		while(--tmp!=0){
			head=head.next;
		}
		head.next=head;
		return head;
	}
	
	//长度为i-1情况下，编号为x，返回同一个节点，杀之前的编号
	// a=getNo(1,2,m)
	// b=getNo(a,3,m)
	// c=getNo(b,4,m)
	// ...... 
	public static int getNo(int x,int i,int m){
		return (x+m-1)%i+1;    
	}

	//现在一共有i个节点，数到m就杀死节点，最终会活下来的节点，请返回它在有i个节点时候的编号
	//旧
	//getLive(N,m)
	public static int getLive(int i,int m){
		if(i==1){
			return 1;
		}
		return (getLive(i-1,m)+m-1)%i+1;
	}

	// 0......n-1个人，围成一圈，依次循环取用arr中的数字，
	// 杀n-1轮，返回活的人的原始编号
	public static int live(int n,int[] m){
		//return oldNo(n,m,0);//返回的编号为1，2，3，4，这样的顺序，所以要-1
		
		return oldNo(n,m,0)-1;
	}

	//还剩i个人，当前取用的数字是arr[index],并且下面的过程，从index出发，循环取用
	//返回哪个人会活（在i个人中的编号）
	public static int oldNo(int i,int[] arr,int index){
		if(i==1){
			return 1;
		}
		
		//老=(新+m-1)%i+1
		return (oldNo(i-1,arr,nextIndex(arr.length,index)) //新
				+arr[index]-1)%i
				+1;

	}
	
	//如果数组长度为size，当前下标为index，返回循环的模型下，下一个index是多少
	public static int nextIndex(int size,int index){
		return index==size-1?0:index+1;
	}


	//计算x的k次幂
	public static int powXK1(int x,int k){
		if(k<0){
			return 0;
		}
		
		int res=1;
		int base=x;
		if((k&1)!=0){
			res*=base;
		}
		k>>=1;

		while(k!=0){
			if((k&1)!=0){
				res*=base*base;
			}
			base*=x;
			k>>=1;
		}

		return res;
	}
	
	public static int powXK2(int x,int k){
		if(k<0){
			return 0;
		}

		int res=1;
		for(int i=0;i<k;i++){
			res*=x;
		}
		
		return res;
	}





	public static void main(String[] args){
		
		int n=10;
		/*
		int[] m={
			1,2,56,23,3,24,3,46,242,12
		};
		*/
		
		int[] m={
			1,2
		};
		System.out.println(get(n,m));
		System.out.println(live(n,m));

		System.out.println(powXK1(2,4));
		System.out.println(powXK2(2,4));
		System.out.println(powXK1(3,5));
		System.out.println(powXK2(3,5));
		System.out.println("hello world");
	}
}






