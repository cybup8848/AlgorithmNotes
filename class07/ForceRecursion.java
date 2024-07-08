/*************************************************************************
    > File Name: ForceRecursion.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jan 31 11:57:53 2024
 ************************************************************************/
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import java.util.Queue;
import java.util.PriorityQueue;
public class ForceRecursion{
	//汉诺塔问题：打印n层汉诺塔从最左边移动到最右边的全部过程
	//自己实现
	public static void hanNuo(int n,int from,int other,int to){
		if(n==0){
			return;
		}
		hanNuo(n-1,from,other,to);
		System.out.println(n+"	"+from+"----->"+to);
		hanNuo(n-1,other,to,from);
	}

	//左程云实现
	public static void hanoi(int n){
		if(n>0){
			func(n,"左","中","右");
		}
	}

	//1~i圆盘 目标是from--->to，other是另一个
	public static void func(int i,String start,String end,String other){
		if(i==1){
			System.out.println("Move 1 from "+start+" to "+end);
		} else {
			func(i-1,start,other,end);
			System.out.println("Move "+i+" from "+start+" to "+end);
			func(i-1,other,end,start);
		}
	}
	
	//打印字符串的全部子序列
	public static void printSubSequence1(String str,int index,String sub){
		//将数据转换为大数据		
		if(index==str.length()){
			System.out.println(sub);
			return;
		}
		printSubSequence1(str,index+1,sub.concat(String.valueOf(str.charAt(index)+"")));
		printSubSequence1(str,index+1,sub);
	}

	public static void printAllSubsquence(String str){
		char[] chs=str.toCharArray();
		process(chs,0);
	}
	//当前来到i位置，要和不要，走两条路
	//之前的选择，所形成的结果，是str，通过chs空间的复用实现的，\0表示空字符
	public static void process(char[] chs,int i){
		if(i==chs.length){
			System.out.println(String.valueOf(chs));
			return;
		}
		process(chs,i+1);//要当前字符的路
		char tmp=chs[i];
		chs[i]=0;//将当前字符置为0，0打印不出来
		process(chs,i+1);//不要当前字符的路
		chs[i]=tmp;//复原
	}

	public static void function(String str){
		char[] chs=str.toCharArray();
		process(chs,0,new ArrayList<Character>());
	}

	//当前来到i位置，要和不要，走两条路
	//res之前的选择，所形成的列表
	public static void process(char[] str,int i,List<Character> res){
		if(i==str.length){
			printList(res);
			return;
		}
		List<Character> resKeep=copyList(res);
		resKeep.add(str[i]);
		process(str,i+1,resKeep);//要当前字符的路
		List<Character> resNoInclude=copyList(res);
		process(str,i+1,resNoInclude);//不要当前字符的路
	}

	public static void printList(List<Character> res){
		for(Character ch:res){
			System.out.print(ch);
		}
		System.out.println();
	}

	public static List<Character> copyList(List<Character> res){
		List<Character> list=new ArrayList<Character>();
		for(Character ch:res){
			list.add(ch);
		}
		return list;
	}

	//打印一个字符串的全部排列
	//自己实现
	public static void printAllPermutation1(String str){
		if(str==null||str.length()==0){
			return;
		}
		char[] chs=str.toCharArray();
		processPermutation1(chs,chs.length,new LinkedList<Integer>());
	}
	public static void processPermutation1(char[] chs,int len,LinkedList<Integer> list){
		if(list.size()==len){
			printString(chs,list);
			return;
		}
		for(int i=0;i<len;i++){
			if(list.contains(i)){
				continue;
			}
			list.add(i);
			processPermutation1(chs,len,list);
			list.removeLast();
		}
	}
	public static void printString(char[] chs,LinkedList<Integer> list){
		for(Integer num:list){
			System.out.print(chs[num]);
		}
		System.out.println();
	}

	//左程云实现
	public static void function2(String str){
		if(str==null||str.length()==0){
			return;
		}
		char[] chs=str.toCharArray();
		processPermutation2(chs,0);
	}
	//str[i...]范围上，所有的字符，都可以在i位置上，后续都去尝试
	//str[0..i-1]范围上，是之前做的选择
	public static void processPermutation2(char[] chs,int j){
		if(j==chs.length){
			System.out.println(String.valueOf(chs));
			return;
		}

		for(int i=j;i<chs.length;i++){
			swap(chs,i,j);
			processPermutation2(chs,j+1);
			swap(chs,i,j);
		}
	}
	public static void swap(char[] chs,int i,int j){
		char ch=chs[i];
		chs[i]=chs[j];
		chs[j]=ch;
	}

	//打印一个字符串的全部排列，要求不要出现重复的排列
	//自己实现
	public static void function3(String str){
		char[] chs=str.toCharArray();
		HashSet<String> hashSet=new HashSet<String>();
		processPermutation3(chs,0,hashSet);
		for(String tmp:hashSet){
			System.out.println(tmp);
		}
	}
	public static void processPermutation3(char[] chs,int i,HashSet<String> hashSet){
		if(i==chs.length){
			hashSet.add(String.valueOf(chs));
			return;
		}

		for(int j=i;j<chs.length;j++){
			swap(chs,i,j);
			processPermutation3(chs,i+1,hashSet);
			swap(chs,i,j);
		}
	}

	//左程云实现
	public static void function4(String str){
		char[] chs=str.toCharArray();
		processPermutation4(chs,0);
	}
	public static void processPermutation4(char[] chs,int i){
		if(i==chs.length){
			System.out.println(String.valueOf(chs));
			return;
		}

		boolean[] visited=new boolean[26];
		for(int j=i;j<chs.length;j++){
			if(!visited[chs[j]-'a']){
				swap(chs,i,j);
				processPermutation4(chs,i+1);
				swap(chs,i,j);
				visited[chs[j]-'a']=true;
			}
		}
	}

	//请返回最后获胜者的分数，[start,end)
	//自己实现，错误代码，没有考虑他们绝顶聪明
	public static int getMaxScore1(int[] arr,int start,int end){
		if(start>=end){
			return 0;
		}
		
		//A拿左，B拿左
		int A1=arr[start]+getMaxScore1(arr,start+2,end);
		int B1=0;
		if(start+1<end){
			B1=arr[start+1]+getMaxScore1(arr,start+2,end);
		}

		//A拿左，B拿右
		int A2=arr[start]+getMaxScore1(arr,start+1,end-1);
		int B2=0;
		if(start<end-1){
			B2=arr[end-1]+getMaxScore1(arr,start+1,end-1);
		}
		
		//A拿右，B拿左
		int A3=arr[end-1]+getMaxScore1(arr,start+1,end-1);
		int B3=0;
		if(start<end-1){
			B3=arr[start]+getMaxScore1(arr,start+1,end-1);
		}

		//A拿右，B拿右
		int A4=arr[end-1]+getMaxScore1(arr,start,end-2);
		int B4=0;
		if(start<end-1){
			B4=arr[end-2]+getMaxScore1(arr,start,end-2);
		}

		return Math.max(Math.max(Math.max(A1,B1),Math.max(A2,B2)),Math.max(Math.max(A3,B3),Math.max(A4,B4)));
	}

	//左程云实现，考虑绝顶聪明
	public static int win1(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
		return Math.max(f(arr,0,arr.length-1),s(arr,0,arr.length-1));//f：先手函数，先拿的      s：后手函数，后拿的
	}

	public static int f(int[] arr,int i,int j){
		if(i==j){
			return arr[i];
		}
		return Math.max(arr[i]+s(arr,i+1,j),arr[j]+s(arr,i,j-1));//(arr,i+1,j)和(arr,i,j-1)，先手变成了后手
																 //当前获得收益，加上先手变为后手获得的最大收益  
	}

	public static int s(int[] arr,int i,int j){
		if(i==j){
			return 0;
		}
		return Math.min(f(arr,i+1,j),f(arr,i,j-1));
	}
	
	//左程云实现，动态规划
	//暴力递归--->动态规划
	public static int win2(int[] arr){

		return 0;
	}

	//逆序栈
	//自己实现
	public static void reverseStack(Stack<Integer> stack){
		if(stack.isEmpty()){
			return;
		}
		int tmp=process(stack);
		reverseStack(stack);
		stack.push(tmp);
	}

	public static int process(Stack<Integer> stack){
		if(stack.size()==1){
			return stack.pop();
		}
		int temp=stack.pop();
		int res=process(stack);
		stack.push(temp);
		return res;
	}

	public static void printStack(Stack<Integer> stack){
		if(stack==null){
			return;
		}
		while(!stack.isEmpty()){
			System.out.print(stack.pop()+"	");
		}
		System.out.println();
	}
	
	//左程云实现
	public static void reverse(Stack<Integer> stack){
		if(stack.isEmpty()){
			return;
		}
		int i=f(stack);
		reverse(stack);
		stack.push(i);
	}
	public static int f(Stack<Integer> stack){
		int result=stack.pop();
		if(stack.isEmpty()){
			return result;
		} else {
			int last=f(stack);
			stack.push(result);
			return last;
		}
	}

	//给定一个只有数字字符组成的字符串str，返回有多少种转化结果
	//自己实现
	public static int function7(String str){
		if(str==null||str.length()==0){
			return 0;
		}
		return transfer(str,0);
	}
	public static int transfer(String str,int index){
		if(index==str.length()){
			return 1;
		}
		if(str.charAt(index)=='0'){//碰见字符'0'，返回0
			return 0;
		}
		int sum=transfer(str,index+1);
		if(index+2<=str.length()&&(str.charAt(index)=='2'&&str.charAt(index+1)<'7'||str.charAt(index)=='1')){
			sum+=transfer(str,index+2);
		}
		return sum;
	}

	//左程云实现
	public static int number(String str){
		if(str==null||str.length()==0){
			return 0;
		}
		char[] chs=str.toCharArray();
		return processNumber(chs,0);
	}

	//i之前的位置，如何转化已经做过决定了
	//i.... 有多少种转化结果
	public static int processNumber(char[] chs,int i){
		if(i==chs.length){
			return 1;
		}
		if(chs[i]=='0'){
			return 0;
		}

		if(chs[i]=='1'){
			int res=processNumber(chs,i+1);//i自己作为单独的部分，后续有多少种方法
			if(i+1<chs.length){
				res+=processNumber(chs,i+2);//(i和i+1)作为单独的部分，后续有多少种方法
			}
			return res;
		}
		if(chs[i]=='2'){
			int res=processNumber(chs,i+1);// i自己作为单独的部分，后续有多少种方法
									 // (i和i+1)作为单独的布恩并且没有超过26，后续有多少种方法
			if(i+1<chs.length&&chs[i+1]>='0'&&chs[i+1]<='6'){
				res+=processNumber(chs,i+2);
			}
			return res;
		}
		// chs[i]=='3'~'9'
		return processNumber(chs,i+1);
	}

	//能装下最多的价值
	//自己实现
	public static int maxValue(int[] weights,int[] values,int bag){
		if(weights==null||weights.length==0){
			return 0;
		}
		return getMaxValue(weights,values,0,bag);
	}

	public static int getMaxValue(int[] weights,int[] values,int index,int bag){
		if(index==weights.length){
			return 0;
		}
		int sum1=0;
		if(weights[index]<=bag){
			sum1+=values[index];
			sum1+=getMaxValue(weights,values,index+1,bag-weights[index]);
		}
		int sum2=getMaxValue(weights,values,index+1,bag);
		return Math.max(sum1,sum2);
	}

	//左程云实现
	// i... 的货物自由选择，形成的最大价值返回
	public static int process1(int[] weights,int[] values,int i,int alreadyweight,int bag){
		if(i==weights.length){
			return 0;
		}
		if(alreadyweight+weights[i]>bag){
			return 0;
		}
		return Math.max(
				process1(weights,values,i+1,alreadyweight,bag),
				values[i]+process1(weights,values,i+1,alreadyweight+weights[i],bag)
				);
	}
	public static int process2(int[] weights,int[] values,int i,int alreadyWeight,int alreadyValue,int bag){
		if(alreadyWeight>bag){
			return 0;
		}
		if(i==values.length){
			return alreadyValue;
		}
		return Math.max(process2(weights,values,i+1,alreadyWeight,alreadyValue,bag),
				process2(weights,values,i+1,alreadyWeight+weights[i],alreadyValue+values[i],bag));
	}

	//动态规划
	public static int maxValue2(int[] c,int[] p,int bag){
		int[][] dp=new int[c.length+1][bag+1];
		for(int i=c.length-1;i>=0;i--){
			for(int j=bag;j>=0;j--){
				dp[i][j]=dp[i+1][j];
				if(j+c[i]<=bag){
					dp[i][j]=Math.max(dp[i][j],p[i]+dp[i+1][j+c[i]]);
				}
			}
		}
		return dp[c.length][bag];
	}

	//N皇后问题
	//自己实现
	public static int NQueens(int N){
		if(N<1){
			return 0;
		}
		int[] record=new int[N];
		return NQueen(record,N,0);
	}
	public static int NQueen(int[] record,int N,int row){
		if(row==N){
			return 1;
		}
		int sum=0;
		for(int i=0;i<N;i++){
			if(!isValid(record,row,i)){
				continue;
			}
			record[row]=i;
			sum+=NQueen(record,N,row+1);
		}
		return sum;
	}
	public static boolean isValid(int[] record,int row,int col){
		for(int i=0;i<row;i++){
			if(record[i]==col||Math.abs(row-i)==Math.abs(col-record[i])){//利用对角线
				return false;
			}
		}
		return true;
	}

	//左程云实现
	public static int num1(int n){
		if(n<1){
			return 0;
		}
		int[] record=new int[n];// record[i]  --->  i行的皇后，放在了第几列
		return process3(0,record,n);
	}
	public static int process3(int i,int[] record,int n){
		if(i==n){
			return 1;
		}
		int res=0;
		for(int j=0;j<n;j++){
			//当前i行的皇后，放在j列，会不会和之前(0..i-1)的皇后，共行共列或者共斜线
			//如果是，认为无效
			//如果不是，认为有效
			if(isValid1(record,i,j)){
				record[i]=j;
				res+=process3(i+1,record,n);
			}
		}
		return res;
	}
	public static boolean isValid1(int[] record,int i,int j){
		for(int k=0;k<i;k++){
			if(j==record[k]||Math.abs(record[k]-j)==Math.abs(i-k)){
				return false;
			}
		}
		return true;
	}

	public static int num2(int n){
		if(n<1||n>32){
			return 0;
		}
		int upperLim=n==32?-1:(1<<n)-1;
		return process4(upperLim,0,0,0);
	}
	public static int process4(int upperLim,int colLim,int leftDiaLim,int rightDiaLim){
		if(colLim==upperLim){
			return 1;
		}
		int pos=0;
		int mostRightOne=0;
		pos=upperLim&(~(colLim|leftDiaLim|rightDiaLim));
		int res=0;
		while(pos!=0){
			mostRightOne=pos&(~pos+1);
			pos=pos-mostRightOne;
			res+process4(upperLim,colLim|mostRightOne,(leftDiaLim|mostRightOne)<<1,(rightDiaLim|mostRightOne)>>>1);
		}
		return res;
	}



	


	public static void main(String[] args){
		System.out.println(NQueens(1)+"	"+num1(1));
		System.out.println(NQueens(2)+"	"+num1(2));
		System.out.println(NQueens(3)+"	"+num1(3));
		System.out.println(NQueens(8)+"	"+num1(8));

		//获取最大价值
		int[] weights=new int[]{14,6,8};
		int[] values=new int[]{200,150,80};
		int bag=15;
		System.out.println(maxValue(weights,values,bag));
		System.out.println(process1(weights,values,0,0,bag));
		System.out.println(process2(weights,values,0,0,0,bag));

		hanNuo(3,1,2,3);
		
		printSubSequence1("abc",0,"");
		System.out.println("\n\n\n");	

		printAllSubsquence("abc");

		char[] chs={'a','b','c','c'};
		System.out.println(String.valueOf(chs));
		chs[2]=0;
		System.out.println(String.valueOf(chs));
		
		System.out.println("全排列");
		printAllPermutation1("abc");
		System.out.println("\n\n\n");

		//打印一个字符串的全部排列
		function2("abc");

		//打印一个字符串的全部排列，要求不要出现重复的排列
		System.out.println("全排列，不要求出现重复排列");
		System.out.println("自己实现");
		function3("aabc");

		System.out.println("左程云实现");
		function4("aabc");

		int[] arr=new int[]{1,2,100,4};
		System.out.println(getMaxScore1(arr,0,arr.length));
		System.out.println(win1(arr));

		arr=new int[]{1,2,100,4,1};
		System.out.println(getMaxScore1(arr,0,arr.length));
		System.out.println(win1(arr));

		arr=new int[]{1,2};
		System.out.println(getMaxScore1(arr,0,arr.length));
		System.out.println(win1(arr));

		arr=new int[]{1};
		System.out.println(getMaxScore1(arr,0,arr.length));
		System.out.println(win1(arr));

		//逆序栈
		Stack<Integer> stack=new Stack<Integer>();
		stack.push(3);
		stack.push(2);
		stack.push(1);
		reverseStack(stack);
		printStack(stack);

		System.out.println(function7("111"));
		System.out.println(function7("2334452312"));


		System.out.println(function7("1112343203323342389"));
		System.out.println(number("1112343203323342389"));
		/*	
		LinkedList<Integer> hash=new LinkedList<Integer>();
		hash.add(1);
		hash.add(5);
		hash.add(3);
		hash.removeLast();
		for(Integer num:hash){
			System.out.println(num);
		}
		*/


		System.out.println("Hello World");
	}
}
