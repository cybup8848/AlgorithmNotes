/************************************************************************
    > File Name: question11.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May  8 16:47:34 2024
 ************************************************************************/
public class question11{

	public static int maxDeep(String s){
		if(s==null){
			return 0;
		}
		int len=s.length();
		int res=0;
		int cn=0;
		for(int i=0;i<len;i++){
			if(s.charAt(i)=='('){
				++cn;
				res=Math.max(res,cn);	
			} else {
				--cn;
			}
		}
		
		return res;
	}


	//自己实现
	public static int maxValidSubStr(String s){
		if(s==null){
			return 0;
		}

		int cn=0;
		int preLength=0;
		
		int max=0;

		int startIndex=0;
		int len=s.length();
		for(int i=0;i<len;i++){
			if(s.charAt(i)=='('){
				++cn;
			} else {
				--cn;
				if(cn>0){
					continue;
				}

				if(cn==0){
					preLength=preLength+i-startIndex+1;
					max=Math.max(max,preLength);
				} else if(cn<0){
					preLength=0;
					cn=0;
				}
				startIndex=i+1;
			}
		}

		return max;
	}
	
	//左程云实现
	public static int maxValidSubStr1(String s){
		if(s==null){
			return 0;
		}

		int len=s.length();
		int[] dp=new int[len];
		int max=0;
		dp[0]=0;
		for(int i=1;i<len;i++){
			if(s.charAt(i)=='('){
				dp[i]=0;
			} else {
				int isynPos=i-dp[i-1]-1;
				if(isynPos<0||s.charAt(isynPos)==')'){
					dp[i]=0;
					continue;
				}
				dp[i]=2+dp[i-1]+(isynPos-1>=0?dp[isynPos-1]:0);
			}
			max=Math.max(max,dp[i]);
		}
		return max;
	}


	//左程云实现
	public static int maxLength(String s){
		if(s==null||s.equals("")){
			return 0;
		}

		char[] str=s.toCharArray();
		int len=s.length();
		int[] dp=new int[len];
		int pre=0;
		int res=0;
		for(int i=0;i<len;i++){
			if(str[i]==')'){
				pre=i-dp[i-1]-1;// 与str[i]配对的左括号位置pre
				if(pre>=0&&str[pre]=='('){
					dp[i]=dp[i-1]+2+(pre>0?dp[pre-1]:0);
				}
			}
			res=Math.max(res,dp[i]);
		}
		return res;
	}
	

	public static void main(String[] args){
		String s1="()()()";
		System.out.println(maxDeep(s1));

		String s2="(((())))";
		System.out.println(maxDeep(s2));

		String s3="()";
		System.out.println(maxDeep(s3));

		

		String s4="())()(())()))(())";
		System.out.println(maxValidSubStr(s4));
		System.out.println(maxValidSubStr1(s4));
		System.out.println(maxLength(s4));

		String s5="())";
		System.out.println(maxValidSubStr(s5));
		System.out.println(maxValidSubStr1(s5));
		System.out.println(maxLength(s5));

		String s6="((()()))";
		System.out.println(maxValidSubStr(s6));
		System.out.println(maxValidSubStr1(s6));
		System.out.println(maxLength(s6));

		String s7="(()())";
		System.out.println(maxValidSubStr(s7));
		System.out.println(maxValidSubStr1(s7));
		System.out.println(maxLength(s7));

		System.out.println("hello world");
	}
}
