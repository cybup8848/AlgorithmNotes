/*************************************************************************
    > File Name: question3.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May  7 09:26:07 2024
 ************************************************************************/
public class question3{

	//自己实现
	//思路错误：反例s1="RRGR"
	public static int minPaintSquares(char[] arr){
		return process(arr);
	}

	public static int process(char[] arr){
		if(arr==null){
			return 0;
		}
		int len=arr.length;
		int l=0;
		int r=len-1;

		int cn=0;
		while(l<r){
			while(l<=r&&arr[l]=='R'){
				++l;
			}

			while(l<=r&&arr[r]=='G'){
				--r;
			}

			if(l<r){
				arr[l]='R';
				arr[r]='G';
				cn+=2;
			}
		}
		return cn;
	}


	//错误，必须从两边开始扫描
	public static int minPaintSquares1(char[] arr){
		return process1(arr);
	}

	public static int process1(char[] arr){
		if(arr==null){
			return 0;
		}

		int len=arr.length;
		int index=0;
		int cn=0;
		for(int i=0;i<len;i++){
			while(index<len&&arr[index]=='R'){
				++index;
			}
			if(index==len){
				break;
			}
			if(arr[i]=='R'){
				arr[i]='G';
				arr[index]='R';
				cn+=2;
			}
		}
		return cn;
	}
	

	//左程云实现
	public static int minPaintTest(String s){
		char[] str=s.toCharArray();
		return process2(str);
	}

	public static int minPaintTest(char[] s){
		return process2(s);
	}

	public static int process2(char[] str){
		int min=Integer.MAX_VALUE;	
		int len=str.length;
		int l=0;
		int r=0;
		for(int i=0;i<=len;i++){
			l=countLeftG(str,i);
			r=countRightR(str,i);
			min=Math.min(min,l+r);
		}
		
		return min;
	}

	private static int countLeftG(char[] str,int L){//统计左边G的个数
		int cn=0;
		for(int i=0;i<L;i++){
			if(str[i]=='G'){
				++cn;
			}
		}
		return cn;
	}

	private static int countRightR(char[] str,int start){//统计右边R的个数
		int cn=0;
		int len=str.length;
		for(int i=start;i<len;i++){
			if(str[i]=='R'){
				++cn;
			}
		}
		return cn;
	}
	
	//生成辅助结构
	public static int minPaintTest3(String s){
		if(s==null){
			return 0;
		}
		char[] str=s.toCharArray();
		return process3(str);
	}

	public static int minPaintTest3(char[] s){
		if(s==null){
			return 0;
		}
		return process3(s);
	}
	
	public static int process3(char[] str){
		int len=str.length;
		int[] redInfo=new int[len+1];//[0,i)有多少绿色
		int[] greenInfo=new int[len+1];//[i,len)有多少红色
		redInfo[0]=0;
		for(int i=1;i<=len;i++){
			redInfo[i]=redInfo[i-1]+(str[i-1]=='G'?1:0);
		}

		greenInfo[len]=0;
		for(int i=len-1;i>=0;--i){
			greenInfo[i]=greenInfo[i+1]+(str[i]=='R'?1:0);
		}
		
		int min=Integer.MAX_VALUE;
		for(int i=0;i<=len;i++){
			min=Math.min(min,redInfo[i]+greenInfo[i]);
		}
		
		return min;
	}






	public static void main(String[] args){
		char[] s={'R','G','R','G','R'};
		//System.out.println(minPaintSquares(s));
		//System.out.println(minPaintSquares1(s));
		System.out.println(minPaintTest(s));
		System.out.println(minPaintTest3(s));

		char[] s1={
			'R','R','R','G','R','R'
		};
		//System.out.println(minPaintSquares(s1));
		System.out.println(minPaintTest(s1));
		System.out.println(minPaintTest3(s1));
		
		char[] s2={
			'R','R','R','G','G','R'
		};
		//System.out.println(minPaintSquares(s2));
		System.out.println(minPaintTest(s2));
		System.out.println(minPaintTest3(s2));
		

		String str1="abcd";
		String str2="sfs";
		System.out.println(str1.compareTo(str2));

		System.out.println("hello world");
	}
}
