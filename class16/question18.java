/*************************************************************************
    > File Name: question18.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jun 20 13:56:14 2024
 ************************************************************************/

import java.util.LinkedList;
import java.util.Stack;


public class question18{

	
	public static int minAddChar1(String s){
		if(s==null||s.length()<2){
			return 0;
		}
		
		int[][] dp=process1(s);
		return dp[0][s.length()-1];
	}

	public static int[][] process1(String s){
		char[] str=s.toCharArray();
		int len=str.length;
		
		int[][] dp=new int[len][len];
		dp[len-1][len-1]=0;
		for(int i=len-2;i>=0;i--){
			dp[i][i]=0;
			dp[i][i+1]=(str[i]==str[i+1]?0:1);
			for(int j=i+2;j<len;j++){
				dp[i][j]=Math.min(dp[i][j-1],dp[i+1][j])+1;
				if(str[i]==str[j]){
					dp[i][j]=Math.min(dp[i][j],dp[i+1][j-1]);
				}
			}
		}

		return dp;
	}
	
	
	public static int minAddChar2(String s){
		if(s==null||s.length()==0){
			return 0;
		}

		int[] dp=process2(s);
		return dp[s.length()-1];
	}

	public static int[] process2(String s){
		char[] str=s.toCharArray();
		int len=str.length;

		int[] dp=new int[len];
		dp[len-1]=0;
		for(int i=len-2;i>=0;i--){
			dp[i]=0;
			dp[i+1]=(str[i]==str[i+1]?0:1);

			int pre=0;
			for(int j=i+2;j<len;j++){
				int tmp=dp[j];
				dp[j]=Math.min(dp[j],dp[j-1])+1;
				if(str[i]==str[j]){
					dp[j]=Math.min(dp[j],pre);
				}
				pre=tmp;
			}
		}

		return dp;
	}

	public static LinkedList<String> minAddChar3(String s){
		if(s==null||s.length()==0){
			return null;
		}
		
		int len=s.length();

		int[][] dp=process1(s);
		
		int newLen=len+dp[0][len-1];
		char[] newArr=new char[newLen];
		
		char[] arr=s.toCharArray();
		LinkedList<String> list=new LinkedList<>();
		
		getAllString1(arr,newArr,0,dp,0,len-1,list);
		
		return list;
	}

	//递归实现
	public static void getAllString1(char[] arr,char[] newArr,int l,int[][] dp,int i,int j,LinkedList<String>list){
		if(i==j){
			newArr[l]=arr[i];
			list.add(new String(newArr));
			return;
		}
		
		int len=newArr.length;

		//先判断是否来自左侧
		if(dp[i][j]==dp[i][j-1]+1){
			newArr[l]=newArr[len-1-l]=arr[j];
			getAllString1(arr,newArr,l+1,dp,i,j-1,list);
		}

		//判断是否从下方过来
		if(dp[i][j]==dp[i+1][j]+1){
			newArr[l]=newArr[len-1-l]=arr[i];
			getAllString1(arr,newArr,l+1,dp,i+1,j,list);
		}

		//判断是否来自左侧
		if(arr[i]==arr[j]&&dp[i][j]==dp[i+1][j-1]){
			newArr[l]=newArr[len-1-l]=arr[i];
			getAllString1(arr,newArr,l+1,dp,i+1,j-1,list);
		}
	}

	
	public static LinkedList<String> minAddChar4(String s){
		if(s==null||s.length()==0){
			return null;
		}

		char[] arr=s.toCharArray();

		int[][] dp=process1(s);
		
		return getAllString2(arr,dp);
	}		

	//栈实现
	
	public static class Node{
		public int start;
		public int end;
		public int index;
		public int flag;//在方向
		//0：从左侧来
		//1：从下方来
		//2：从左下方来

		public Node(int s,int e,int id,int f){
			this.start=s;
			this.end=e;
			this.index=id;
			this.flag=f;//应该用哪个index数据给新数组索引left位置赋值
		}
	}
	public static LinkedList<String> getAllString2(char[] arr,int[][] dp){

		int len=arr.length;
		int newLen=len+dp[0][len-1];

		char[] newArr=new char[newLen];
	
		LinkedList<String> list=new LinkedList<>();
		
		Stack<Node> stack=new Stack<>();
	
		if(dp[0][len-1]==dp[0][len-2]+1){
			stack.push(new Node(0,len-2,0,0));
		}
		if(dp[0][len-1]==dp[1][len-1]+1){
			stack.push(new Node(1,len-1,0,1));
		}
		if(dp[0][len-1]==dp[1][len-2]&&arr[0]==arr[len-1]){
			stack.push(new Node(1,len-2,0,2));
		}

		while(!stack.isEmpty()){
			Node node=stack.pop();
			int s=node.start;
			int e=node.end;
			int index=node.index;
			int flag=node.flag;
			char ch=' ';
			if(flag==0){
				ch=arr[e+1];
			} else if(flag==1||flag==2){
				ch=arr[s-1];
			}

			newArr[index]=newArr[newLen-1-index]=ch;

			if(s>e){
				list.add(new String(newArr));
				continue;
			}

			if(dp[s][e]==dp[s][e-1]+1){
				stack.push(new Node(s,e-1,index+1,0));
			}
			if(dp[s][e]==dp[s+1][e]+1){
				stack.add(new Node(s+1,e,index+1,1));
			}
			if(dp[s][e]==dp[s+1][e-1]&&arr[s]==arr[e]){
				stack.add(new Node(s+1,e-1,index+1,2));
			}
		}
		
		return list;
	}

	//实现有问题
	public static LinkedList<String> minAddChar5(String s){
		if(s==null||s.length()==0){
			return null;
		}

		char[] arr=s.toCharArray();
		int[][] dp=process1(s);
		
		return getAllString3(arr,dp);
	}
	
	public static class Node1{
		public int start;
		public int end;
		public int flag;//表示可以向哪个方向
		//0：向左
		//1：向下
		//2：向左下
		public Node1(int s,int e,int f){
			start=s;
			end=e;
			flag=f;
		}
	}

	public static LinkedList<String> getAllString3(char[] arr,int[][] dp){

		int len=arr.length;
		int newLen=len+dp[0][len-1];

		char[] newStr=new char[newLen];
	
		LinkedList<String> list=new LinkedList<>();
		
		Stack<Node1> stack=new Stack<>();
	
		if(dp[0][len-1]==dp[0][len-2]+1){
			stack.push(new Node1(0,len-1,0));
		}
		if(dp[0][len-1]==dp[1][len-1]+1){
			stack.push(new Node1(0,len-1,1));
		}
		if(dp[0][len-1]==dp[1][len-2]&&arr[0]==arr[len-1]){
			stack.push(new Node1(0,len-1,2));
		}
		
		int index=0;
		
		while(!stack.isEmpty()){
			Node1 node=stack.pop();
			int s=node.start;
			int e=node.end;
			int flag=node.flag;

			int pres=s;
			int pree=e;
			char ch=' ';
			if(flag==0){
				ch=arr[e];
				e-=1;
			}  else if(flag==1){
				ch=arr[s];
				s+=1;
			} else if(flag==2){
				ch=arr[s];
				s+=1;
				e-=1;
			}

			newStr[index]=newStr[newLen-1-index]=ch;
			if(pres==pree){
				list.add(new String(newStr));
				--index;
				continue;
			}

			++index;
			if(dp[s][e]==dp[s][e-1]+1){
				stack.push(new Node1(s,e,0));
			}
			if(dp[s][e]==dp[s+1][e]+1){
				stack.push(new Node1(s,e,1));
			}
			if(arr[s]==arr[e]&&dp[s][e]==dp[s+1][e-1]){
				stack.push(new Node1(s,e,2));
			}

		}
		
		return list;
	}
	
	public class TNode{
		public int val;
		public TNode left;
		public TNode right;
		public TNode(int v){
			val=v;
			left=right=null;
		}
	}

	public static void preTrav(TNode root){
		if(root==null){
			return;
		}
		
		Stack<TNode> stack=new Stack<>();
		while(!stack.isEmpty()||root!=null){
			if(root!=null){
				System.out.print(root.val+"	");
				stack.push(root.right);
				root=root.left;
			} else {
				root=stack.pop();
			}
		}

		System.out.println("\n\n\n");
	}


	
	
	public static void main(String[] args){
		String s="a12bc321";
		System.out.println(minAddChar1(s));
		System.out.println(minAddChar2(s));

		System.out.println("\n\n\n");

		LinkedList<String> res=minAddChar3(s);
		for(String tmp:res){
			System.out.println(tmp);
		}

		System.out.println("\n\n\n");
		
		res=minAddChar4(s);
		for(String tmp:res){
			System.out.println(tmp);
		}
		
		System.out.println("\n\n\n");

		res=minAddChar5(s);
		for(String tmp:res){
			System.out.println(tmp);
		}



		System.out.println("hello world");
	}
}





