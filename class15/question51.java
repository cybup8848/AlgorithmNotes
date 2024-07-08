/*************************************************************************
    > File Name: question51.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 23 09:44:00 2024
 ************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

public class question51{

	public static int getExpNums(String exp,int target){
		if(exp==null){
			return 0;
		}
		
		int len=exp.length();
		LinkedList<Integer> res=process(exp,0,len-1);
		int cn=0;
		for(int x:res){
			if(x==target){
				++cn;
			}
		}
		return cn;
	}
	
	//[start,end]
	public static LinkedList<Integer> process(String exp,int start,int end){
		if(start==end){
			LinkedList<Integer> list=new LinkedList<>();
			list.add(exp.charAt(start)=='1'?1:0);
			return list;
		}
		
		LinkedList<Integer> res=new LinkedList<>();
		for(int i=start;i<=end;i++){
			if(exp.charAt(i)=='0'||exp.charAt(i)=='1'){
				continue;
			}
			
			LinkedList<Integer> left=process(exp,start,i-1);
			LinkedList<Integer> right=process(exp,i+1,end);

			for(int x:left){
				for(int y:right){
					int result=0;
					switch(exp.charAt(i)){
						case '&':
							result=x&y;
							break;
						case '|':
							result=x|y;
							break;
						case '^':
							result=x^y;
							break;
					}
					res.add(result);
				}
			}
		}

		return res;
	}

	//根据递归修改为动态规划版本
	public static int getExpNums1(String exp,int target){
		if(exp==null||exp.length()==0){
			return 0;
		}


		int len=exp.length();
		ArrayList<ArrayList<LinkedList<Integer>>> dp=new ArrayList<>();
		for(int i=0;i<len;i++){
			dp.add(new ArrayList<LinkedList<Integer>>());
			for(int j=0;j<len;j++){
				dp.get(i).add(new LinkedList<Integer>());
			}
		}

		for(int i=0;i<len;i+=2){
			LinkedList<Integer> tmp=new LinkedList<>();
			if(exp.charAt(i)=='0'){
				tmp.add(0);
			} else if(exp.charAt(i)=='1'){
				tmp.add(1);
			}
			dp.get(i).set(i,tmp);
		}

		//这里没有跳过符号，符号填了也没事，因为我们计算的时候不会用到符号位
		for(int i=len-2;i>=0;--i){
			for(int j=0;j<len;j++){

				for(int k=i;k<=j;k++){
					if(exp.charAt(k)=='0'||exp.charAt(k)=='1'){
						continue;
					}

					for(int x:dp.get(i).get(k-1)){
						for(int y:dp.get(k+1).get(j)){
							int result=0;
							switch(exp.charAt(k)){
								case '&':
									result=x&y;
									break;
								case '|':
									result=x|y;
									break;
								case '^':
									result=x^y;
									break;
							}
							dp.get(i).get(j).add(result);
						}
					}
				}
			}
		}

		int cn=0;
		for(int x:dp.get(0).get(len-1)){
			if(x==target){
				++cn;
			}
		}

		return cn;
	}
	
	//左程云实现
	public static boolean isValid(char[] exp){
		if((exp.length&1)==0){
			return false;
		}

		for(int i=0;i<exp.length;i+=2){
			if(exp[i]!='1'&&exp[i]!='0'){
				return false;
			}
		}

		for(int i=1;i<exp.length;i+=2){
			if(exp[i]!='&'&&exp[i]!='|'&&exp[i]!='^'){
				return false;
			}
		}

		return true;
	}

	public static int num1(String express,boolean desired){
			if(express==null||express.equals("")){
				return 0;
			}

			char[] exp=express.toCharArray();
			if(!isValid(exp)){
				return 0;
			}
		
			return p(exp,desired,0,exp.length-1);
	}

	//exp[L..R] 返回期待为desireed的方法数
	//潜台词：L R 一定不要压中逻辑符号
	public static int p(char[] exp,boolean desired,int L,int R){
		if(L==R){
			if(exp[L]=='1'){
				return desired?1:0;
			} else {
				return desired?0:1;
			}
		}
		
		int res=0;
		if(desired){//期待为true
					//i位置尝试L...R范围上的每一个逻辑符号，都是最后结合的
			for(int i=L+1;i<R;i+=2){
				switch(exp[i]){
					case '&':
						res+=p(exp,true,L,i-1)*p(exp,true,i+1,R);
						break;
					case '|':
						res+=p(exp,true,L,i-1)*p(exp,false,i+1,R);
						res+=p(exp,false,L,i-1)*p(exp,true,i+1,R);
						res+=p(exp,true,L,i-1)*p(exp,true,i+1,R);
						break;
					case '^':
						res+=p(exp,true,L,i-1)*p(exp,false,i+1,R);
						res+=p(exp,false,L,i-1)*p(exp,true,i+1,R);
						break;
				}	
			}
		} else {//期待为false
			for(int i=L+1;i<R;i+=2){
				switch(exp[i]){
				case '&':
					res+=p(exp,false,L,i-1)*p(exp,true,i+1,R);
					res+=p(exp,true,L,i-1)*p(exp,false,i+1,R);
					res+=p(exp,false,L,i-1)*p(exp,false,i+1,R);
					break;
				case '|':
					res+=p(exp,false,L,i-1)*p(exp,false,i+1,R);
					break;
				case '^':
					res+=p(exp,true,L,i-1)*p(exp,true,i+1,R);
					res+=p(exp,false,L,i-1)*p(exp,false,i+1,R);
					break;
				}	
			}
		}

		return res;
	}

	//左程云版本有三个变量
	//desired：true false
	//L
	//R
	//所以是一个三维表
	public static int num2(String express,boolean desired){
		if(express==null||express.equals("")){
			return 0;
		}
		
		char[] exp=express.toCharArray();

		if(!isValid(exp)){
			return 0;
		}
		
		int len=exp.length;
		
		//0：false    1：true
		int[][][] dp=new int[len][len][2];

		for(int i=0;i<len;i+=2){
			int n0=0;
			int n1=1;
			if(exp[i]=='0'){
				n0=1;
				n1=0;
			}
			
			dp[i][i][0]=n0;
			dp[i][i][1]=n1;
		}

		for(int i=len-1;i>=0;i-=2){
			for(int j=i+2;j<len;j+=2){
				for(int k=0;k<2;k++){
					int result=0;
					if(k==0){//期待为false
						for(int m=i+1;m<j;m++){
							switch(exp[m]){
								case '&':
									result+=dp[i][m-1][0]*dp[m+1][j][1]+dp[i][m-1][1]*dp[m+1][j][0]+dp[i][m-1][0]*dp[m+1][j][0];
									break;
								case '|':
									result+=dp[i][m-1][0]*dp[m+1][j][0];
									break;
								case '^':
									result+=dp[i][m-1][1]*dp[m+1][j][1]+dp[i][m-1][0]*dp[m+1][j][0];
									break;
							}
						}
					} else {//起到为true
						for(int m=i+1;m<j;m+=2){
							switch(exp[m]){
								case '&':
									result+=dp[i][m-1][1]*dp[m+1][j][1];
									break;
								case '|':
									result+=dp[i][m-1][1]*dp[m+1][j][0]+dp[i][m-1][0]*dp[m+1][j][1]+dp[i][m-1][1]*dp[m+1][j][1];
									break;
								case '^':
									result+=dp[i][m-1][1]*dp[m+1][j][0]+dp[i][m-1][0]*dp[m+1][j][1];
									break;
							}
						}
					}
					dp[i][j][k]=result;
					
				}
			}
		}

		int index=desired?1:0;
		return dp[0][len-1][index];
	}
	
	//左程云实现动态规划
	public static int dpLive(String express,boolean desired){
		char[] str=express.toCharArray();

		int N=str.length;

		int[][] tMap=new int[N][N];
		int[][] fMap=new int[N][N];

		for(int i=0;i<N;i+=2){
			tMap[i][i]=str[i]=='1'?1:0;
			fMap[i][i]=str[i]=='0'?1:0;
		}
		
		//有的row、col压着符号，不该填，所以row-=2是为了跳过符号
		for(int row=N-3;row>=0;row-=2){//row、col不能压着符号，，所以row从N-1开始，row-=2是为了跳过符号 col=row+2
			for(int col=row+2;col<N;col+=2){
				for(int i=row+1;i<col;i+=2){
					switch(str[i]){
						case '&':
							tMap[row][col]+=tMap[row][i-1]*tMap[i+1][col];
							break;
						case '|':
							tMap[row][col]+=tMap[row][i-1]*fMap[i+1][col];
							tMap[row][col]+=fMap[row][i-1]*tMap[i+1][col];
							tMap[row][col]+=tMap[row][i-1]*tMap[i+1][col];
							break;
						case '^':
							tMap[row][col]+=tMap[row][i-1]*fMap[i+1][col];
							tMap[row][col]+=fMap[row][i-1]*tMap[i+1][col];
							break;
					}
					switch(str[i]){
						case '&':
							fMap[row][col]+=tMap[row][i-1]*fMap[i+1][col];
							fMap[row][col]+=fMap[row][i-1]*tMap[i+1][col];
							fMap[row][col]+=fMap[row][i-1]*fMap[i+1][col];
							break;
						case '|':
							fMap[row][col]+=fMap[row][i-1]*fMap[i+1][col];
							break;
						case '^':
							fMap[row][col]+=tMap[row][i-1]*tMap[i+1][col];
							fMap[row][col]+=fMap[row][i-1]*fMap[i+1][col];
							break;
					}
				}
			}
		}
		
		return desired?tMap[0][N-1]:fMap[0][N-1];
	}


	public static void main(String[] args){


		String exp="1^0|0|1";
		int target=0;
		System.out.println(getExpNums(exp,target));
		System.out.println(getExpNums1(exp,target));
		System.out.println(num1(exp,target==1?true:false));
		System.out.println(num2(exp,target==1?true:false));
		System.out.println(dpLive(exp,target==1?true:false));


		System.out.println("\n\n\n");



		exp="1";
		target=0;
		System.out.println(getExpNums(exp,target));
		System.out.println(getExpNums1(exp,target));
		System.out.println(num1(exp,target==1?true:false));
		System.out.println(num2(exp,target==1?true:false));
		System.out.println(dpLive(exp,target==1?true:false));
		
		System.out.println("\n\n\n");

		
		exp="1&1&0^1^0^1|0^1&1&0^1&0|1&1^0";
		target=0;
		System.out.println(getExpNums(exp,target));
		System.out.println(getExpNums1(exp,target));
		System.out.println(num1(exp,target==1?true:false));
		System.out.println(num2(exp,target==1?true:false));
		System.out.println(dpLive(exp,target==1?true:false));
		System.out.println("\n\n\n");

		target=1;
		System.out.println(getExpNums(exp,target));
		System.out.println(getExpNums1(exp,target));
		System.out.println(num1(exp,target==1?true:false));
		System.out.println(num2(exp,target==1?true:false));
		System.out.println(dpLive(exp,target==1?true:false));


		System.out.println("hello world");
	}

}



