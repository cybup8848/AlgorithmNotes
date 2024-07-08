/*************************************************************************
    > File Name: question13.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Jun 17 15:14:26 2024
 ************************************************************************/

import java.util.Stack;
import java.util.HashMap;


public class question13{
	
	public static int calcExpr(String s){
		if(s==null||s.length()==0){
			return Integer.MIN_VALUE;
		}
		
		Stack<Integer> numStack=new Stack<>();
		
		Stack<Character> opStack=new Stack<>();
		
		HashMap<Character,Integer> hashMap=new HashMap<>();
		hashMap.put('-',0);
		hashMap.put('+',0);
		hashMap.put('/',1);
		hashMap.put('*',1);
		hashMap.put('(',2);

		int len=s.length();
		for(int i=0;i<len;i++){
			if(s.charAt(i)>='0'&&s.charAt(i)<='9'||s.charAt(i)=='-'&&isNegative(s,i)){
				int end=getEndIndex(s,i);
				numStack.push(Integer.valueOf(s.substring(i,end)));
				i=end-1;
			} else {

				char op=s.charAt(i);
				if(op==')'){
					while(opStack.peek()!='('){
						char top=opStack.pop();
						calc(numStack,top);
					}
					opStack.pop();
				} else {
					int opCode=hashMap.get(op);
					while(!opStack.isEmpty()){
						char top=opStack.peek();
						int topCode=hashMap.get(top);
						if(topCode<opCode||top=='('){
							break;
						}
					
						calc(numStack,top);
						opStack.pop();
					}
					opStack.push(op);
				}

			}
		}
		
		while(!opStack.isEmpty()){
			char op=opStack.pop();
			calc(numStack,op);
		}
		return numStack.peek();
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
	
	public static boolean isNegative(String s,int index){
		if(index==0){
			return true;
		}

		if(index-1>=0&&s.charAt(index-1)=='('){
			return true;
		}
		
		return false;
	}

	public static int getEndIndex(String s,int startIndex){
		int len=s.length();
		if(s.charAt(startIndex)=='-'){
			startIndex+=1;
		}

		while(startIndex<len){
			if(s.charAt(startIndex)<'0'||s.charAt(startIndex)>'9'){
				break;
			}
			++startIndex;
		}
		return startIndex;
	}


	public static void main(String[] args){
		String ss="48*((70-65)-43)+8*1";
		System.out.println(calcExpr(ss));
		ss="3+1*4";
		System.out.println(calcExpr(ss));
		ss="3+(1*4)";
		System.out.println(calcExpr(ss));
		ss="-3*4+7*(6-5)";
		System.out.println(calcExpr(ss));
		ss="5*(-5*(4-6))";
		System.out.println(calcExpr(ss));



		String s="12432";
		System.out.println(s.substring(1));
		System.out.println(s.substring(1,4));

		System.out.println(Integer.valueOf("12342"));
		System.out.println(Integer.valueOf("-123423"));
		System.out.println(Integer.parseInt("235"));
		System.out.println("hello world");
	}

}













