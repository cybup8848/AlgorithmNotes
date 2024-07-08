/*************************************************************************
    > File Name: question29.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jul  3 14:05:21 2024
 ************************************************************************/


public class question29{
	
	public static void hano(int n,char l,char mid,char r){
		if(n==1){
			System.out.println(l+" ---> "+r);
			return;
		}

		hano(n-1,l,r,mid);
		System.out.println(l+" ---> "+r);
		hano(n-1,mid,l,r);
	}


	//左程云实现
	public static int step1(int[] arr){
		if(arr==null||arr.length==0){
			return -1;
		}
		
		return process1(arr,arr.length-1,1,2,3);
	}

	//目标是：把arr[0~i]的圆盘，从from全部挪到to上
	//返回：根据arr中的状态arr[0...i]，它是最优解阿第几步？
	//时间复杂度：O(N),分支都只走一条，且i每次都减1
	public static int process1(int[] arr,int i,int from,int other,int to){
		if(i==-1){
			return 0;
		}

		if(arr[i]!=from&&arr[i]!=to){
			return -1;
		}

		if(arr[i]==from){//第一大步没走完
			return process1(arr,i-1,from,to,other);
		} else {//arr[i]=to
			int rest=process1(arr,i-1,other,from,to);//第三大步完成的程度
			if(rest==-1){
				return -1;
			}
			
			return (1<<i)+rest;
		}
	}

	//动态规划
	public static int step2(int[] arr){
		if(arr==null||arr.length==0){
			return -1;
		}

		int from=1;
		int mid=2;
		int to=3;
		int len=arr.length;
		int i=len-1;
		int res=0;
		int tmp=0;
		while(i>=0){
			if(arr[i]==mid){
				return -1;
			}

			if(arr[i]==to){
				res+=1<<i;
				tmp=from;
				from=mid;
				mid=tmp;
			} else {
				tmp=to;
				to=mid;
			}

			mid=tmp;
			i--;
		}
		
		return res;
	}




	public static void main(String[] args){
		int n=10;
		char l='l';
		char mid='m';
		char r='r';
		hano(n,l,mid,r);
		System.out.println("\n\n\n");

		n=3;
		hano(n,l,mid,r);
		System.out.println("\n\n\n");

		
		System.out.println("下面判断汉诺塔问题的最优解");
		int[] arr={
			3,3,2,1	
		};
		System.out.println(step1(arr));
		System.out.println(step2(arr));




		System.out.println("hello world");
	}
}




