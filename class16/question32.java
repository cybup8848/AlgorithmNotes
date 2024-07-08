/*************************************************************************
    > File Name: question32.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jul  3 20:27:43 2024
 ************************************************************************/

import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Vector;

public class question32{

	public static int length1(int n){
		if(n<=1){
			return 0;
		}

		return process1(n);
	}

	public static int process1(int n){
		if(n==1){
			return 1;
		}
		
		int sum=0;
		for(int i=1;i<n;i++){
			sum+=process1(i)*process1(n-i);
		}

		return sum;
	}

	//动态规划
	public static int length2(int n){
		if(n<=1){
			return 0;
		}

		return process2(n);
	}
	
	public static int process2(int n){
		int[] dp=new int[n+1];
		dp[1]=1;
		for(int i=2;i<=n;i++){
			for(int j=1;j<i;j++){
				dp[i]+=dp[j]*dp[i-j];
			}
		}

		return dp[n];
	}

	//判断是否互为旋变串
	public static boolean isRotateChangeString(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0||s1.length()!=s2.length()){
			return false;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		if(!typeAndNum(str1,str2)){
			return false;
		}

		LinkedList<String> list=findAllRotateString(str1,0,str1.length-1);
		
		HashSet<String> hashSet=new HashSet<>(list);

		return hashSet.contains(s2);
	}

	public static LinkedList<String> findAllRotateString(char[] str,int s,int e){
		if(s==e){
			LinkedList<String> tmp=new LinkedList<>();
			tmp.add(String.valueOf(str[s]));
			return tmp;
		}

		LinkedList<String> res=new LinkedList<>();
		for(int i=s;i<e;i++){
			LinkedList<String> list1=findAllRotateString(str,s,i);
			LinkedList<String> list2=findAllRotateString(str,i+1,e);
			for(String s1:list1){
				for(String s2:list2){
					res.add(s1+s2);
					res.add(s2+s1);
				}
			}
		}

		return res;
	}


	public static boolean isRotateChangeString1(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0||s1.length()!=s2.length()){
			return false;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		if(!typeAndNum(str1,str2)){
			return false;
		}

		LinkedList<String> list=findAllRotateStringDP(str1);
		
		HashSet<String> hashSet=new HashSet<>(list);

		return hashSet.contains(s2);
	}

	public static LinkedList<String> findAllRotateStringDP(char[] str){
		int len=str.length;
		
		Vector<Vector<LinkedList<String>>> dp=new Vector<Vector<LinkedList<String>>>(len);
		for(int i=0;i<len;i++){
			dp.add(new Vector<LinkedList<String>>(len));
			for(int j=0;j<len;j++){
				dp.get(i).add(new LinkedList<String>());
			}
			dp.get(i).get(i).add(String.valueOf(str[i]));
		}


		for(int i=len-2;i>=0;i--){
			for(int j=i+1;j<len;j++){
				for(int k=i;k<j;k++){
					LinkedList<String> list1=dp.get(i).get(k);
					LinkedList<String> list2=dp.get(k+1).get(j);
					for(String s1:list1){
						for(String s2:list2){
							dp.get(i).get(j).add(s1+s2);
							dp.get(i).get(j).add(s2+s1);
						}
					}
				}
			}
		}

		return dp.get(0).get(len-1);
	}

	public static boolean typeAndNum(char[] str1,char[] str2){
		int len1=str1.length;
		int len2=str2.length;
		
		char[] count=new char[256];
		for(int i=0;i<len1;i++){
			count[str1[i]]++;
		}

		for(int i=0;i<len2;i++){
			count[str2[i]]--;
		}

		for(int i=0;i<256;i++){
			if(count[i]!=0){
				return false;
			}
		}
		
		return true;
	}


	//范围尝试模型
	public static boolean isRotateChangeString2(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0||s1.length()!=s2.length()){
			return false;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();

		return process2(str1,str2);
	}

	public static boolean process2(char[] str1,char[] str2){
		int len=str1.length;
		
		boolean[][][] dp=new boolean[len][len][len];
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				dp[0][i][j]=(str1[i]==str2[j]);
			}
		}

		for(int k=1;k<len;k++){
			for(int i=0;i<len-k;i++){
				for(int j=0;j<len-k;j++){
					for(int split=0;split<k;split++){
						dp[k][i][j]=dp[split][i][j]&&dp[k-split-1][i+split+1][j+split+1];
						dp[k][i][j]=dp[k][i][j]||dp[split][i][j+k-split]&&dp[k-split-1][i+split+1][j];
						if(dp[k][i][j]){
							break;
						}
					}
				}
			}
		}

		return dp[len-1][0][0];
	}


	//左程云实现
	public static boolean sameTypeSameNumber(char[] str1,char[] str2){
		int len1=str1.length;
		int len2=str2.length;
	
		if(len1!=len2){
			return false;
		}

		int[] map=new int[256];
		for(int i=0;i<len1;i++){
			++map[str1[i]];
		}

		for(int i=0;i<len2;i++){
			if(--map[str2[i]]<0){
				return false;
			}
		}
		
		return true;
	}

	//递归
	public static boolean isScramble(String s1,String s2){
		if((s1==null&&s2!=null)||(s1!=null&&s2==null)){
			return false;
		}

		if(s1==null||s2==null){
			return true;
		}

		if(s1.equals(s2)){
			return true;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		if(!sameTypeSameNumber(str1,str2)){
			return false;
		}

		int N=s1.length();
		return process(str1,str2,0,0,N);
	}

	public static boolean process(char[] str1,char[] str2,int s1,int s2,int size){
		if(size==1){
			return str1[s1]==str2[s2];
		}

		int len=str1.length;
		for(int k=1;k<size;k++){
			if(process(str1,str2,s1,s2,k)&&process(str1,str2,s1+k,s2+k,size-k)){
				return true;
			}
			if(process(str1,str2,s1,s2+size-k,k)&&process(str1,str2,s1+k,s2,size-k)){
				return true;
			}
		}

		return false;
	}

	//递归
	public static boolean isScramble1(String s1,String s2){
		if((s1==null&&s2!=null)||(s1!=null&&s2==null)){
			return false;
		}

		if(s1==null&&s2==null){
			return true;
		}

		if(s1.equals(s2)){
			return true;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		if(!sameTypeSameNumber(str1,str2)){
			return false;
		}

		int N=s1.length();
		return process11(str1,str2,0,0,N);
	}

	//返回str1[从L1开始往右长度为size的子串]和str2[从L2开始往右长度为size的子串]是否互为旋变字符串
	//在str1中的这一段和str2中的这一段一定是等长的，所以只用一个参数size
	public static boolean process11(char[] str1,char[] str2,int L1,int L2,int size){
		if(size==1){
			return str1[L1]==str2[L2];
		}
		
		for(int leftPart=1;leftPart<size;leftPart++){
			//如果1左对2左，并且1右对2有
			if(process11(str1,str2,L1,L2,leftPart)&&process11(str1,str2,L1+leftPart,L2+leftPart,size-leftPart)){
				return true;
			}

			//如果1左对2右，并且1右对2左
			if(process11(str1,str2,L1,L2+size-leftPart,leftPart)&&process11(str1,str2,L1+leftPart,L2,size-leftPart)){
				return true;
			}
		}

		return false;
	}

	//动态规划
	public static boolean isScramble2(String s1,String s2){
		if((s1==null&&s2!=null)||(s1!=null&&s2==null)){
			return false;
		}

		if(s1==null&&s2==null){
			return true;
		}

		if(s1.equals(s2)){
			return true;
		}
		
		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		if(!sameTypeSameNumber(str1,str2)){
			return false;
		}

		int N=s1.length();
		return process22(str1,str2);
	}

	public static boolean process22(char[] str1,char[] str2){
		int N=str1.length;
		
		//L1可能性  L2可能性    size可能性
		boolean[][][] dp=new boolean[N][N][N+1];
		for(int L1=0;L1<N;L1++){
			for(int L2=0;L2<N;L2++){
				dp[L1][L2][1]	=(str1[L1]==str2[L2]);		
			}
		}

		for(int size=2;size<=N;size++){//从2层开始往上填写，一直填写到第N层
			//本层的东西，不互相依赖
			//任何一个dp[i][j][size] 都依赖 dp[...][...][k]（k<size）
			for(int L1=0;L1<=N-size;L1++){
				for(int L2=0;L2<=N-size;L2++){
					for(int leftPart=1;leftPart<size;leftPart++){
						if(dp[L1][L2][leftPart]&&dp[L1+leftPart][L2+leftPart][size-leftPart]||
							dp[L1][L2+size-leftPart][leftPart]&&dp[L1+leftPart][L2][size-leftPart]){
							dp[L1][L2][size]=true;
							break;
						}
					}
				}
			}
		}
		
		return dp[0][0][N];
	}

	



	public static void main(String[] args){
		String s1="abcd";
		int n=s1.length();
		System.out.println(length1(n));
		System.out.println(length2(n));
		System.out.println("\n\n\n");


		String s2="cadb";
		System.out.println(isRotateChangeString(s1,s2));
		System.out.println(isRotateChangeString1(s1,s2));
		System.out.println(isRotateChangeString2(s1,s2));
		System.out.println(isScramble(s1,s2));
		System.out.println(isScramble1(s1,s2));
		System.out.println(isScramble2(s1,s2));
		System.out.println("\n\n\n");

		s2="cdab";
		System.out.println(isRotateChangeString(s1,s2));
		System.out.println(isRotateChangeString1(s1,s2));
		System.out.println(isRotateChangeString2(s1,s2));
		System.out.println(isScramble(s1,s2));
		System.out.println(isScramble1(s1,s2));
		System.out.println(isScramble2(s1,s2));
		System.out.println("\n\n\n");


		HashSet<String> set=new HashSet<>();
		set.add("acs");
		set.add("sdf");
		String s3="sdf";
		System.out.println(set.contains(s3));

		System.out.println("hello world");
	}
}



