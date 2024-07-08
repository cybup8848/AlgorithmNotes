/*************************************************************************
    > File Name: NQueen.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Feb  2 09:48:03 2024
 ************************************************************************/
public class NQueen{

	public static int NQueen(int n){
		if(n<1){
			return 0;
		}
		
		int limit=n==32?-1:(1<<n)-1;
		return process(limit,0,0,0);
	}

	public static int process(int limit,int colLim,int leftDiaLim,int rightDiaLim){
		if(limit==colLim){
			return 1;
		}
		int mostRightOne=0;
		int pos=0;
		int res=0;
		pos=limit&(~(colLim|leftDiaLim|rightDiaLim));
		while(pos!=0){
			mostRightOne=pos&((~pos)+1);
			pos-=mostRightOne;
			res+=process(limit,colLim|mostRightOne,(leftDiaLim|mostRightOne)<<1,(rightDiaLim|mostRightOne)>>>1);
		}
		return res;
	}

	public static void main(String[] args){
		System.out.println(NQueen(1));
		System.out.println(NQueen(2));
		System.out.println(NQueen(3));
		System.out.println(NQueen(8));


		System.out.println("hello world");
	}

}
