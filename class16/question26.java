/*************************************************************************
    > File Name: question26.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jun 28 15:07:07 2024
 ************************************************************************/

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class question26{

	//自己实现
	public static boolean matchRegExp1(String str,String pattern){
		if(str==null||pattern==null||str.length()==0||pattern.length()==0){
			return false;
		}
		
		char[] s=str.toCharArray();
		char[] p=pattern.toCharArray();
		return process1(s,0,p,0);
	}

	public static boolean process1(char[] s,int sindex,char[] p,int pindex){
		if(sindex==s.length){
			return pindex==p.length||((pindex==p.length-1)&&(p[pindex]=='*'))?true:false;
		}
		if(sindex<s.length&&pindex==p.length){
			return false;
		}


		boolean res=false;
		if(pindex+1<p.length&&p[pindex+1]=='*'){
			res=process1(s,sindex,p,pindex+1);
		} else if(p[pindex]=='?'||s[sindex]==p[pindex]){
			res=process1(s,sindex+1,p,pindex+1);
		} else if(p[pindex]=='*') {
			if(p[pindex-1]=='?'||s[sindex]==p[pindex-1]){
				res=process1(s,sindex+1,p,pindex);//匹配1个或多个
			}
			res=res||process1(s,sindex,p,pindex+1);//匹配0个
		}
		
		return res;
	}

	//动态规划
	public static boolean matchRegExp2(String str,String pattern){
		if(str==null||str.length()==0||pattern==null||pattern.length()==0){
			return false;
		}

		char[] s=str.toCharArray();
		char[] p=pattern.toCharArray();
		return process2(s,p);
	}

	public static boolean process2(char[] s,char[] p){
		int slen=s.length;
		int plen=p.length;
		boolean[][] dp=new boolean[slen+1][plen+1];
		for(int i=0;i<=slen;i++){
			dp[i][plen]=false;
		}
		for(int j=0;j<=plen;j++){
			dp[slen][j]=false;
		}

		dp[slen][plen]=true;
		dp[slen][plen-1]=(p[plen-1]=='*'?true:false);

		for(int i=slen-1;i>=0;i--){
			for(int j=plen-1;j>=0;j--){
				dp[i][j]=false;
				if(j+1<plen&&p[j+1]=='*'){
					dp[i][j]=dp[i][j+1];
				} else if(p[j]=='?'||s[i]==p[j]){
					dp[i][j]=dp[i+1][j+1];
				} else if(p[j]=='*'){
					if(p[j-1]=='?'||s[i]==p[j-1]){
						dp[i][j]=dp[i+1][j];
					}
					dp[i][j]=dp[i][j]||dp[i][j+1];
				}
			}
		}

		return dp[0][0];
	}	

	//状态压缩
	public static boolean matchRegExp3(String str,String pattern){
		if(str==null||pattern==null||str.length()==0||pattern.length()==0){
			return false;
		}

		char[] s=str.toCharArray();
		char[] p=pattern.toCharArray();
		return process3(s,p);
	}

	public static boolean process3(char[] s,char[] p){
		int slen=s.length;
		int plen=p.length;
		
		boolean[] dp=new boolean[plen+1];
		for(int j=0;j<=plen;j++){
			dp[j]=false;
		}
		dp[plen]=true;
		dp[plen-1]=(p[plen-1]=='*'?true:false);
		
		for(int i=slen-1;i>=0;i--){
			boolean pre=dp[plen];
			dp[plen]=false;
			for(int j=plen-1;j>=0;j--){
				boolean tmp=dp[j];
				dp[j]=false;
				if(j+1<plen&&p[j+1]=='*'){
					dp[j]=dp[j+1];
				} else if(p[j]=='?'||s[i]==p[j]){
					dp[j]=pre;
				} else if(p[j]=='*'){
					if(p[j-1]=='?'||s[i]==p[j-1]){
						dp[j]=tmp;
					}
					dp[j]=dp[j]||dp[j+1];
				}
				pre=tmp;
			}
		}
		
		return dp[0];
	}

	
	//左程云实现	
	public static boolean isValid(char[] s,char[] e){
		for(int i=0;i<s.length;i++){
			if(s[i]=='*'||s[i]=='?'){
				return false;
			}
		}

		for(int i=0;i<e.length;i++){
			if(e[i]=='*'&&(i==0||e[i-1]=='*')){//也就是说不可能出现*****这样的情况，也就是说不存在 “**” 这样的子串
				return false;
			}
		}

		return true;
	}

	public static boolean isMatch(String str,String exp){
		if(str==null||exp==null){
			return false;
		}

		char[] s=str.toCharArray();
		char[] e=exp.toCharArray();
		return isValid(s,e)?process11(s,e,0,0):false;
	}
	
	//s[si...] 能否被 e[ei...] 配出来
	//必须保证ei压中的不是 *，保证前面的决定不会影响后面的决定，无后效性
	public static boolean process11(char[] s,char[] e,int si,int ei){
		if(ei==e.length){//base case       exp已经耗尽了
			return si==s.length;
		}

		//可能性1，ei+1位置，不是 *,并且str[si]必须和exp[ei]相等，后续能走通
		if(ei+1==e.length||e[ei+1]!='*'){//没有ei+1位置、由ei+1位置，但是e[ei+1]不是*
			return si!=s.length&&(e[ei]==s[si]||e[ei]=='?')&&process11(s,e,si+1,ei+1);
		}

		//可能性二：ei+1位置是*
		while(si!=s.length&&(e[ei]==s[si]||e[ei]=='?')){//替代0、1、2、3、4、5、....、n-1个字符
			if(process11(s,e,si,ei+2)){
				return true;
			}
			si++;
		}

		//替代n个字符
		return process11(s,e,si,ei+2);//保证了无后效性，前面的东西不会影响后面的东西
	}

	//动态规划版本
	public static boolean isMatchDP(String str,String exp){
		if(str==null||exp==null){
			return false;
		}

		char[] s=str.toCharArray();
		char[] e=exp.toCharArray();
		return isValid(s,e)?process22(s,e):false;
	}

	public static boolean process22(char[] s,char[] e){
		int slen=s.length;
		int elen=e.length;
		boolean[][] dp=initMap(s,e);
		
		for(int i=slen-1;i>=0;i--){
			for(int j=elen-2;j>=0;j--){
				if(e[j+1]!='*'){
					dp[i][j]=(i!=slen&&(e[j]==s[i]||e[j]=='?')&&dp[i+1][j+1]);
				} else {
					int k=i;
					while(k!=slen&&(e[j]==s[k]||e[j]=='?')){
						if(dp[k][j+2]){
							dp[i][j]=true;
							break;
						}
						k++;
					}
					if(!dp[i][j]){
						dp[i][j]=dp[k][j+2];
					}
				}
			}
		}

		return dp[0][0];
	}

	public static boolean[][] initMap(char[] s,char[] e){
		int slen=s.length;
		int elen=e.length;
		boolean[][] dp=new boolean[slen+1][elen+1];//java中boolean默认是false
		dp[slen][elen]=true;
		for(int j=elen-2;j>-1;j=j-2){
			if(e[j]!='*'&&e[j+1]=='*'){//可以简化为：if(e[j+1]=='*')，因为之前已经判断过isValid，不会出现"**"这样的子串
				dp[slen][j]=true;
			} else {
				break;
			}
		}

		if(slen>0&&elen>0){
			if(e[elen-1]=='?'||e[elen-1]==s[slen-1]){
				dp[slen-1][elen-1]=true;
			}
		}

		return dp;
	}



	public static void main(String[] args){
		String s="abcdef";
		String pattern="?*";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");
		
		pattern="?*?";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");


		pattern="?*?";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");

		pattern="???";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");
		
		pattern="??????????????";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");

		pattern="???????*????";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");

		s="abcd";
		pattern="a??d";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");

		s="abcde";
		pattern="a??e";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");

		s="abcd";
		pattern="a???d";
		System.out.println(matchRegExp1(s,pattern));
		System.out.println(matchRegExp2(s,pattern));
		System.out.println(matchRegExp3(s,pattern));
		System.out.println(isMatch(s,pattern));
		System.out.println(isMatchDP(s,pattern));
		System.out.println("\n\n\n");


		boolean[] flags=new boolean[2];
		for(boolean x:flags){
			System.out.print(x+"	");
		}
		System.out.println();


		System.out.println("hello world");
	}


}



