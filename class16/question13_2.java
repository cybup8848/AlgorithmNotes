/*************************************************************************
    > File Name: question13_1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Jun 17 19:58:05 2024
 ************************************************************************/


import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class question13_2{
	
	public static String processStr(String s){
		int len=s.length();
		LinkedList<Integer> list=new LinkedList<>();
		if(s.charAt(0)=='-'){
			list.add(0);
		}
		for(int i=1;i<len;i++){
			if(s.charAt(i)=='-'&&s.charAt(i-1)=='('){
				list.add(i);
			}
		}

		String tmp="";
		int pre=0;
		for(int x:list){
			tmp=tmp+s.substring(pre,x)+String.valueOf(0);
			pre=x;
		}

		tmp=tmp+s.substring(pre,len);
		
		return tmp;
	}


	public static int calcExpr(String s){
		if(s==null||s.length()==0){
			return Integer.MIN_VALUE;
		}

		s=processStr(s);
		System.out.println(s);
		
		HashMap<Character,Integer> map=new HashMap<>();
		map.put('+',0);
		map.put('-',0);
		map.put('*',1);
		map.put('/',1);
		map.put('(',2);
		map.put(')',-1);//保证')'的优先级最低，'()'里面的数据全部计算完成

		Stack<Integer> numStack=new Stack<>();
		Stack<Character> opStack=new Stack<>();

		int len=s.length();
		for(int i=0;i<len;i++){
			if(isNum(s,i)){
				int end=getNumEnd(s,i);//[i,end)
				String sub=s.substring(i,end);
				numStack.push(Integer.valueOf(sub));
				i=end-1;
			} else {
				char op=s.charAt(i);
				int opCode=map.get(op);

				char top=0;
				int topCode=0;
				while(!opStack.isEmpty()&&opStack.peek()!='('){
					top=opStack.peek();
					topCode=map.get(top);
					if(topCode<opCode){
						break;
					}
					calc(numStack,top);
					opStack.pop();
				}

				if(op==')'){
					opStack.pop();
				}else {
					opStack.push(op);
				}
			}
		}

		char op=0;
		while(!opStack.isEmpty()){
			op=opStack.pop();
			calc(numStack,op);
		}
		
		return numStack.peek();
	}


	public static boolean isNum(String s,int index){
		int len=s.length();
		if(s.charAt(index)>='0'&&s.charAt(index)<='9'){
			return true;
		}

		//因为processStr函数已经处理过负数，所以这里不再需要判断是否为负数
		/*负数
		if(s.charAt(index)=='-'&&(index==0||
			index-1>=0&&s.charAt(index-1)=='('&&index+1<len&&s.charAt(index+1)>='0'&&s.charAt(index+1)<='9'
			)
		  ){
			return true;
		  }
		*/
		
		return false;
	}
	
	public static int getNumEnd(String s,int i){
		if(s.charAt(i)=='-'){
			i+=1;
		}
		
		int len=s.length();
		while(i<len&&s.charAt(i)>='0'&&s.charAt(i)<='9'){
			++i;
		}

		return i;
	}

	public static void calc(Stack<Integer> numStack,char op){
		int num2=numStack.pop();
		int num1=numStack.pop();
		
		int res=0;
		switch(op){
			case '+':
				{
					res=num1+num2;
					break;
				}
			case '-':
				{
					res=num1-num2;
					break;
				}
			case '*':
				{
					res=num1*num2;
					break;
				}
			case '/':
				{
					res=num1/num2;
					break;
				}
			default:
				break;
		}
		numStack.push(res);
	}

	
	//没有小括号，只有+、-、*、/
	public static int calcExprNoBracket(String s){
		if(s==null||s.length()==0){
			return Integer.MIN_VALUE;
		}
		
		HashMap<String,Integer> map=new HashMap<>();
		map.put("+",0);
		map.put("-",0);
		map.put("*",1);
		map.put("/",1);


		int len=s.length();
		Stack<String> stack=new Stack<>();
		int res=0;
		for(int i=0;i<len;i++){
			if(s.charAt(i)>='0'&&s.charAt(i)<='9'){
				res=res*10+Integer.valueOf(s.charAt(i)+"");// 加上""，变成了字符串
			} else {
				String op=String.valueOf(s.charAt(i));
				int opCode=map.get(op);

				if(stack.isEmpty()){
					stack.push(String.valueOf(res));
					stack.push(String.valueOf(op));
				} else {
					String top=stack.peek();
					int topCode=map.get(top);

					//topCode=opCode，说明他们的优先级相同，需要按照从左到右的顺序进行计算，所以topCode需要先进行计算
					if(topCode>=opCode){//其实topCode>opCode，topCode的优先级比较高，所以topCode要先进性运算
						stack.pop();
						String s1=stack.pop();
						res=calcNoBracket(Integer.valueOf(s1),res,top);
					}
					stack.push(String.valueOf(res));
					stack.push(op);
				}
				res=0;
			}
		}
		stack.push(String.valueOf(res));

		while(stack.size()>1){
			String s2=stack.pop();
			String op=stack.pop();
			String s1=stack.pop();
			res=calcNoBracket(Integer.valueOf(s1),Integer.valueOf(s2),op);
			stack.push(String.valueOf(res));
		}
		
		return Integer.valueOf(stack.pop());
	}

	public static int calcNoBracket(int num1,int num2,String op){
		int res=0;
		switch(op){
			case "+":
				{
					res=num1+num2;
					break;
				}
			case "-":
				{
					res=num1-num2;
					break;
				}
			case "*":
				{
					res=num1*num2;
					break;
				}
			case "/":
				{
					res=num1/num2;
					break;
				}
			default:
				break;
		}
		
		return res;
	}


	//左程云实现
	public static int getValue(String str){
		return value(str.toCharArray(),0)[0];
	}
	

	//请从str[i...] 往下算，遇到字符串终止位置活着右括号，就停止递归，返回栈结果
	//返回两个值，长度为2的数组
	// 0)：负责的这一段的结果是多少
	// 1)：负责的这一段计算到了哪个位置
	//左程云的写法：把符号当成了减号处理
	public static int[] value(char[] str,int i){
		LinkedList<String> que=new LinkedList<String>();
		
		int num=0;
		int[] bra=null;
		
		int len=str.length;
		while(i<len&&str[i]!=')'){
			if(str[i]>='0'&&str[i]<='9'){
				num=num*10+str[i++]-'0';
			} else if(str[i]!='('){//遇到的是运算符号
				addNum(que,num);
				que.addLast(String.valueOf(str[i++]));
				num=0;
			} else {//遇到左括号了
				bra=value(str,i+1);
				num=bra[0];
				i=bra[1]+1;
			}
		}

		addNum(que,num);
		return new int[]{getNum(que),i};
	}

	public static void addNum(LinkedList<String> que,int num){
		if(!que.isEmpty()){
			int cur=0;
			String top=que.removeLast();
			if(top.equals("+")||top.equals("-")){
				que.addLast(top);
			} else {//如果乘、除运算符，就要先进行运算，然后再压队列；保证队列里面只有加、减运算符
				cur=Integer.valueOf(que.pollLast());
				num=top.equals("*")?(cur*num):(cur/num);
			}
		}
		que.addLast(String.valueOf(num));
	}

	public static int getNum(LinkedList<String> que){
		int res=0;
		boolean add=true;
		String cur=null;
		int num=0;
		while(!que.isEmpty()){
			cur=que.pollFirst();
			if(cur.equals("+")){
				add=true;
			} else if(cur.equals("-")){
				add=false;
			} else {
				num=Integer.valueOf(cur);
				res+=add?num:(-num);
			}
		}
		return res;

		/*
		while(que.size()>1){
			int num1=Integer.valueOf(que.removeFirst());
			Srting op=que.removeFirst();
			int num2=Integer.valueOf(que.removeFirst());
			int res=0;
			switch(op){
				case "+":
					{
						res=num1+num2;
						break;
					}
				case "-":
					{
						res=num1-num2;
						break;
					}
				case "*":
					{
						re=num1*num2;
						break;
					}
				case "/":
					{
						res=num1/num2;
						break;
					}
				default:
					break;
			}
			que.addFirst(String.valueOf(res));
		}

		return Integer.valueOf(que.peek());
		*/
	}
	
	

	public static void main(String[] args){
		String s="48*((70-65)-43)+8*1";
		System.out.println(calcExpr(s));
		System.out.println(getValue(s));
		System.out.println("\n");

		s="3+1*4";
		System.out.println(calcExpr(s));
		System.out.println(getValue(s));
		System.out.println("\n");

		s="3+(1*4)";
		System.out.println(calcExpr(s));
		System.out.println(getValue(s));
		System.out.println();
		
		s="-(7-4*5)+4-(6-7*7)+(-8*4-6+((-6)*7))";
		System.out.println(calcExpr(s));
		System.out.println(getValue(s));
		System.out.println("\n");

		s="1*((23-5)-6)-(-5+7*(3-4))";
		System.out.println(calcExpr(s));
		System.out.println(getValue(s));
		System.out.println("\n");



		System.out.println("\n\n\n");




		s="1+3*4";
		System.out.println(calcExprNoBracket(s));

		s="1+2-3*4/2";
		System.out.println(calcExprNoBracket(s));

		s="1+2-3+4-5+6";
		System.out.println(calcExprNoBracket(s));

		System.out.println("hello world");
	}
}



