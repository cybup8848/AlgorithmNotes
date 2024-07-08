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

public class question13_1{
	
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
			tmp=tmp+String.valueOf(0)+s.substring(pre,x);
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

		//负数
		if(s.charAt(index)=='-'&&(index==0||
			index-1>=0&&s.charAt(index-1)=='('&&index+1<len&&s.charAt(index+1)>='0'&&s.charAt(index+1)<='9'
			)
		  ){
			return true;
		  }
		
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


	public static void main(String[] args){
		String s="48*((70-65)-43)+8*1";
		System.out.println(calcExpr(s));

		s="3+1*4";
		System.out.println(calcExpr(s));

		s="3+(1*4)";
		System.out.println(calcExpr(s));
		
		s="-(7-4*5)+4-(6-7*7)+(-8*4-6+((-6)*7))";
		System.out.println(calcExpr(s));



		System.out.println("hello world");
	}
}



