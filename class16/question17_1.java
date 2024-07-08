/*************************************************************************
    > File Name: question17_1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 19 19:15:30 2024
 ************************************************************************/


public class question17_1{

	//最长回文子串
	public static String maxPaSubString1(String s){
		if(s==null||s.length()==0){
			return null;
		}

		char[] str=s.toCharArray();
		int len=str.length;
		
		char split='#';//假设 '#' 在字符串s中不存在
		String newStr="";
		for(int i=0;i<len;i++){
			newStr=newStr+split+str[i];
		}
		newStr+=split;
		
		str=newStr.toCharArray();
		len=str.length;
		
		int start=-1;
		int end=-1;
		int max=1;
		

		for(int i=1;i<len-1;i++){
			int tmplen=1;
			int l=i-1;
			int r=i+1;
			while(l>=0&&r<len&&str[l]==str[r]){
				--l;
				++r;
				++tmplen;
			}

			if(max<tmplen){
				start=l+2;//此时的最长回文字符串肯定是以字符不匹配结束，而不会因为#不想等而结束
				end=r-2;
				max=tmplen;
			}
		}

		return s.substring((start-1)/2,(end-1)/2+1);
	}

	//manacher算法
	public static String maxPaSubString2(String s){
		if(s==null||s.length()==0){
			return null;
		}

		char[] str=s.toCharArray();
		int len=str.length;
		
		String newStr="";
		char split='#';
		for(int i=0;i<len;i++){
			newStr=newStr+split+str[i];
		}
		newStr+=split;

		str=newStr.toCharArray();
		len=str.length;
		
		int c=0;
		int mostR=1;

		int[] radiusArr=new int[len];//可以代表他们最远能扩散到哪个位置,这样表示[i,r),表示一半回文子串
		radiusArr[0]=1;
		radiusArr[len-1]=len;
		
		int max=1;
		int pac=0;
		
		for(int i=1;i<len-1;i++){
			if(i>=mostR){//i在[c,mostR)外，直接扩散
				int l=i-1;
				int r=i+1;
				while(l>=0&&r<len&&str[l]==str[r]){
					--l;
					++r;
				}
				if(r>mostR){
					c=i;
					mostR=r;
				}
				radiusArr[i]=r;
			} else {// i在[c,mostR)内（包括边界）
				int mostL=2*c-mostR;
				
				//i关于c的对称点
				int sysi=2*c-i;
				int mostiR=radiusArr[sysi];
				int mostiL=2*sysi-mostiR;

				if(mostiL>mostL){//sysi对称范围在c的对称范围内
					radiusArr[i]=i+mostiR-sysi;
				} else if(mostiL<mostL){//sysi对称范围在c的对称范围外
					radiusArr[i]=mostR;
				} else {//sysi对称范围在c的对称范围边界上，即：mostiL=mostL-1
					int l=2*i-mostR;
					int r=mostR;
					while(l>=0&&r<len&&str[l]==str[r]){
						--l;
						++r;
					}
					radiusArr[i]=r;
					if(r>mostR){
						c=i;
						mostR=r;
					}
				}
			}

			if(max<radiusArr[i]-i){
				max=radiusArr[i]-i;
				pac=i;
			}
		}

		return s.substring((pac-max+2-1)/2,(pac+max-2-1)/2+1);
	}

	//manacher算法的简化版
	public static String maxPaSubString2_1(String s){
		if(s==null||s.length()==0){
			return null;
		}
		
		char[] str=s.toCharArray();
		int len=str.length;

		char split='#';
		String newStr="";
		for(int i=0;i<len;i++){
			newStr=newStr+split+str[i];
		}
		newStr+=split;

		str=newStr.toCharArray();
		len=str.length;
		
		int[] radiusArr=new int[len];//回文半径数组
		radiusArr[0]=1;
		radiusArr[len-1]=len;

		int c=0;
		int mostR=1;
		
		//记录最长回文子串的中心点pac和回文半径max
		int max=1;
		int pac=0;
		
		for(int i=1;i<len-1;i++){
			int l=-1;
			int r=-1;
			if(i>=mostR){
				l=i-1;
				r=i+1;
			} else {
				int sysi=2*c-i;
				int curR=mostR-c;
				int sysiR=radiusArr[sysi]-sysi;
				r=i+Math.min(curR,sysiR);
				l=2*i-r;
			}
			
			while(l>=0&&r<len&&str[l]==str[r]){
				--l;
				++r;
			}
			radiusArr[i]=r;
			if(r>mostR){
				c=i;
				mostR=r;
			}

			if(max<r-i){
				max=r-i;
				pac=i;
			}
		}

		return s.substring((pac-max+2-1)/2,(pac+max-2-1)/2+1);
	}

	
	public static String maxPaSubString3(String s){
		if(s==null||s.length()==0){
			return null;
		}

		String copys="";
		int len=s.length();
		for(int i=0;i<len;i++){
			copys=s.charAt(i)+copys;
		}

		return process(s,copys);
	}

	public static String process(String s1,String s2){
		char[] str1=s1.toCharArray();
		int len1=str1.length;

		char[] str2=s2.toCharArray();
		int len2=str2.length;

		int end=0;//记录s1、s2最长公共子串在s1中的位置
		int max=0;

		int[][] dp=new int[len1][len2];
		for(int i=0;i<len1;i++){
			if(str1[i]==str2[0]){
				dp[i][0]=1;
				max=1;
			}
		}

		for(int j=0;j<len2;j++){
			if(str1[0]==str2[j]){
				dp[0][j]=1;
				max=1;
			}
		}


		for(int i=1;i<len1;i++){
			for(int j=1;j<len2;j++){
				if(str1[i]==str2[j]){
					dp[i][j]=dp[i-1][j-1]+1;
					if(max<dp[i][j]){
						end=i;
						max=dp[i][j];
					}
				}
			}
		}

		return s1.substring(end+1-max,end+1);
	}


	public static String maxPaSubString3_1(String s){
		if(s==null||s.length()==0){
			return null;
		}

		String copys="";
		int len=s.length();
		for(int i=0;i<len;i++){
			copys=s.charAt(i)+copys;
		}

		return process1(s,copys);
	}

	public static String process1(String s1,String s2){	
		char[] str1=s1.toCharArray();
		int len1=str1.length;

		char[] str2=s2.toCharArray();
		int len2=str2.length;

		int end=0;
		int max=0;
		
		int row=len1-1;
		int col=0;
		while(col<len2){
			int r=row;
			int c=col;
			int len=0;
			while(r<len1&&c<len2){
				if(str1[r]==str2[c]){
					++len;
					if(max<len){
						end=r;
						max=len;
					}	
				} else {
					len=0;
				}
				++r;
				++c;
			}

			if(row>0){
				row-=1;
			} else {
				col+=1;
			}
		}
		

		return s1.substring(end+1-max,end+1);
	}


	public static void main(String[] args){
		String s="abccbe";
		System.out.println(maxPaSubString1(s));
		System.out.println(maxPaSubString2(s));
		System.out.println(maxPaSubString2_1(s));
		System.out.println(maxPaSubString3(s));
		System.out.println(maxPaSubString3_1(s));
		System.out.println("\n\n\n");

		s="abcdcba";
		System.out.println(maxPaSubString1(s));
		System.out.println(maxPaSubString2(s));
		System.out.println(maxPaSubString2_1(s));
		System.out.println(maxPaSubString3(s));
		System.out.println(maxPaSubString3_1(s));
		System.out.println("\n\n\n");


		s="abcdef";
		System.out.println(maxPaSubString1(s));
		System.out.println(maxPaSubString2(s));
		System.out.println(maxPaSubString2_1(s));
		System.out.println(maxPaSubString3(s));
		System.out.println(maxPaSubString3_1(s));
		System.out.println("\n\n\n");


		System.out.println("hello world");
	}


}




