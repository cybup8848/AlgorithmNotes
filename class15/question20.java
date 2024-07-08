/*************************************************************************
    > File Name: question20.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 10 20:09:52 2024
 ************************************************************************/

public class question20{

	//自己实现
	public static int minOps(String s,String m,int n){
		if(s.length()==n){
			return 0;
		}
		if(s.length()>n){
			return -1;
		}

		int first=minOps(s+s,s,n);
		int second=minOps(s+m,m,n);
		
		if(first==-1&&second==-1){
			return -1;
		} else if(first==-1){
			return 1+second;
		} else if(second==-1){
			return 1+first;
		} else{
			return 1+Math.min(first,second);
		}
	}

	//左程云实现
	public static boolean isPrime(int n){
		int len=(int)Math.sqrt(n);
		for(int i=2;i<=len;i++){
			if(n%i==0){
				return false;
			}
		}
		return true;
	}	

	//请保证n不是质数，返回：
	//（1）所有因子的和，但是因子不包括1
	//（2）所有因子的个数，但是因此不包括1
	
	public static int[] divSumAndCount(int n){
		int sum=0;
		int count=0;
		for(int i=2;i<=n;i++){
			while(n%i==0){
				sum+=i;
				count++;
				n/=i;
			}
		}
		return new int[]{sum,count};
	}

	public static int minOps1(int n){
		if(n<2){
			return 0;
		}

		if(isPrime(n)){//如果是质数
			return n-1;
		}

		//n不是质数
		int[] res=divSumAndCount(n);
		return res[0]-res[1];
	}

	public static void main(String[] args){
		{
			String s="abc";
			String s1=s+"ac";
			System.out.println(s1);

			String s2=s+'a';
			System.out.println(s2);
		}
		
		{
			String s="a";
			String m=s;
			System.out.println(s);
			System.out.println(m);
			
			int n=4;
			System.out.println(minOps(s,m,n));
			System.out.println(minOps1(n));

			n=7;
			System.out.println(minOps(s,m,n));
			System.out.println(minOps1(n));

		}
		

	
		
		System.out.println("hello world");
	}
}



