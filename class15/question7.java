/*************************************************************************
    > File Name: question7.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May  7 20:49:45 2024
 ************************************************************************/
public class question7{
	
	//错误
	//反例："())("
	public static int getMinBracket(String s){
		if(s==null){
			return 0;
		}
		
		int min=Integer.MAX_VALUE;
		int len=s.length();
		
		int[] left=getLeft(s);
		int[] right=getRight(s);
		for(int i=0;i<=len;i++){
			min=Math.min(min,left[i]+right[i]);
		}
		return min;
	}
	
	private static int[] getLeft(String s){
		int len=s.length();
		int[] res=new int[len+1];
		res[0]=0;
		char flag='(';
		int cn=0;
		for(int i=1;i<=len;i++){
			cn=cn+(s.charAt(i-1)=='('?1:-1);
			res[i]=Math.abs(cn);
		}
		return res;
	}

	private static int[] getRight(String s){
		int len=s.length();
		int[] res=new int[len+1];
		res[len]=0;
		int cn=0;
		for(int i=len-1;i>=0;--i){
			cn=cn+(s.charAt(i)=='('?1:-1);
			res[i]=Math.abs(cn);
		}
		return res;
	}

	//左程云实现
	public static int getMinBracket1(String s){
		if(s==null){
			return 0;
		}
		int ans=0;
		int count=0;
		int len=s.length();
		for(int i=0;i<len;i++){
			count=count+(s.charAt(i)=='('?1:-1);
			if(count==-1){
				++ans;
				count=0;
			}
		}
		return ans+count;
	}

	//左程云实现
	public static int needParentheses(String str){
		if(str==null){
			return 0;
		}
		int cn=0;
		int ans=0;
		int len=str.length();
		for(int i=0;i<len;i++){
			if(str.charAt(i)=='('){
				++cn;
			} else {
				if(cn==0){
					++ans;
				} else {
					--cn;
				}
			}
		}
		return ans+cn;
	}




	public static void main(String[] args){
		String s1="(()())";
		System.out.println(getMinBracket(s1));
		System.out.println(getMinBracket1(s1));

		String s2="(())()";
		System.out.println(getMinBracket(s2));
		System.out.println(getMinBracket1(s2));
		
		String s3="())(";
		System.out.println(getMinBracket(s3));
		System.out.println(getMinBracket1(s3));

		String s4="(";
		System.out.println(getMinBracket(s4));
		System.out.println(getMinBracket1(s4));

		String s5=")";
		System.out.println(getMinBracket(s5));
		System.out.println(getMinBracket1(s5));


		System.out.println("hello world");
	}
}




