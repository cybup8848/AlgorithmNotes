/*************************************************************************
    > File Name: question28.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 14 15:18:26 2024
 ************************************************************************/



public class question28{

	
	public static int[] getNext(char[] chs){
		if(chs==null){
			return null;
		}

		int len=chs.length;
		int[] next=new int[len];
		next[0]=-1;
		next[1]=0;
		int cn=0;
		for(int i=2;i<len;){
			if(chs[i-1]==chs[cn]){
				++cn;
				next[i]=cn;
				++i;
			} else if(cn==0){
				next[i]=0;
				++i;
			} else {
				cn=next[cn];
			}
		}
		
		return next;
	}


	public static int kmp(String s,String p){
		if(s==null||p==null){
			return -1;
		}

		char[] str=s.toCharArray();
		char[] ptr=p.toCharArray();

		int[] next=getNext(ptr);
		
		int slen=str.length;
		int plen=p.length();

		int sindex=0;
		int pindex=0;
		
		while(sindex<slen&&pindex<plen){
			if(str[sindex]==ptr[pindex]){
				++sindex;
				++pindex;
			} else if(pindex==0){
				++sindex;
			} else {
				pindex=next[pindex];
			}
		}
		
		int startIndex=-1;
		if(pindex==plen){
			startIndex=sindex-plen;
		}
		return startIndex;
	}
	
	
	public static boolean isSpinWord(String s,String p){
		if(s==null||p==null||s.length()!=p.length()){
			return false;
		}

		String ss=s+s;
		
		return kmp(ss,p)!=-1;
	}


	
	
	public static void main(String[] args){
		
		String s="abcdwegergerggdhtruyt";
		String p="wegerg";
		System.out.println(kmp(s,p));

		p="asfsdgd";
		System.out.println(kmp(s,p));
		System.out.println("\n\n\n");

		
		
		String a="cdab";
		String b="abcd";
		System.out.println(isSpinWord(a,b));

		a="1ab2";
		b="ab12";
		System.out.println(isSpinWord(a,b));

		a="2ab1";
		b="ab12";
		System.out.println(isSpinWord(a,b));
		

		
		System.out.println("hello world");

		
		System.out.println("hello world");
	}
}
