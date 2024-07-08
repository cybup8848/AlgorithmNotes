/*************************************************************************
    > File Name: question3.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 29 15:44:59 2024
 ************************************************************************/


public class question3{

	public static int minCoins(int[] arr,int m1,int m2,int m){
		if(arr==null||m1<=0||m2<=0||arr.length!=m1+m2||m<0){
			return 0;
		}

		boolean[] isInfinite=new boolean[m1+m2];//硬币是否无限
		for(int i=0;i<m1;i++){
			isInfinite[i]=true;
		}
		for(int i=m1;i<m1+m2;i++){
			isInfinite[i]=false;
		}

		return process(arr,isInfinite,0,m);
	}


	public static int process(int[] arr,boolean[] isInfinite,int index,int rest){
		if(index==arr.length){
			return rest==0?1:0;
		}
		if(rest<=0){
			return rest==0?1:0;
		}
		
		int res=0;
		if(isInfinite[index]){
			for(int i=0;i*arr[index]<=rest;i++){
				res+=process(arr,isInfinite,index+1,rest-i*arr[index]);
			}
		} else {
			res=process(arr,isInfinite,index+1,rest)+process(arr,isInfinite,index+1,rest-arr[index]);
		}
		
		return res;
	}

	public static int minCoins1(int[] arr,int m1,int m2,int m){
		if(arr==null||m1<=0||m2<=0||arr.length!=m1+m2||m<0){
			return 0;
		}

		boolean[] isInfinite=new boolean[m1+m2];//硬币是否无限
		for(int i=0;i<m1;i++){
			isInfinite[i]=true;
		}
		for(int i=m1;i<m1+m2;i++){
			isInfinite[i]=false;
		}

		return process1(arr,isInfinite,0,m);
	}

	public static int process1(int[] arr,boolean[] isInfinite,int index,int rest){
		if(index==arr.length){
			return rest==0?1:0;
		}

		if(rest<=0){
			return rest==0?1:0;
		}

		int res=0;
		int cn=isInfinite[index]?Integer.MAX_VALUE:1;
		for(int i=0;i<=cn&&i*arr[index]<=rest;i++){
			res+=process1(arr,isInfinite,index+1,rest-i*arr[index]);
		}
	
		return res;
	}

	//动态规划
	public static int minCoinsDP(int[] arr,int m1,int m2,int m){
		if(arr==null||m1<=0||m2<=0||arr.length!=m1+m2||m<0){
			return 0;
		}

		boolean[] isInfinite=new boolean[m1+m2];//硬币是否无限
		for(int i=0;i<m1;i++){
			isInfinite[i]=true;
		}

		int len=arr.length;
		for(int i=m1;i<len;i++){
			isInfinite[i]=false;
		}

		int[][] dp=new int[len+1][m+1];
		for(int i=0;i<=len;i++){
			dp[i][0]=1;
		}

		for(int i=len-1;i>=0;--i){
			for(int j=1;j<=m;j++){
				if(isInfinite[i]){
					for(int k=0;k*arr[i]<=j;++k){
						dp[i][j]+=dp[i+1][j-k*arr[i]];
					}
				} else {
					dp[i][j]=dp[i+1][j]+(j-arr[i]>=0?dp[i+1][j-arr[i]]:0);
				}
			}
		}
		
		return dp[0][m];
	}

	//左程云实现
	public static int minCoins2(int[] arr1,int[] arr2,int m){
		if(arr1==null||arr2==null||m<0){
			return 0;
		}
		
		int res=0;
		for(int i=0;i<=m;i++){
			int res1=process2_1(arr1,0,i);
			int res2=process2_2(arr2,0,m-i);
			res+=res1*res2;
		}
		return res;
	}

	//每种硬币有任意枚
	public static int process2_1(int[] arr,int index,int rest){
		if(index==arr.length){
			return rest==0?1:0;
		}
		if(rest<=0){
			return rest==0?1:0;
		}
		
		int res=0;
		for(int i=0;i*arr[index]<=rest;i++){
			res+=process2_1(arr,index+1,rest-i*arr[index]);
		}
		
		return res;
	}
	
	//	每种硬币只有一种
	public static int process2_2(int[] arr,int index,int rest){
		if(index==arr.length){
			return rest==0?1:0;
		}
		if(rest<=0){
			return rest==0?1:0;
		}

		return process2_2(arr,index+1,rest)+process2_2(arr,index+1,rest-arr[index]);
	}

	//动态规划
	public static int minCoinsDP1(int[] arr1,int[] arr2,int m){
		if(arr1==null||arr2==null||m<0){
			return 0;
		}

		int[] coins1=randNumCoins(arr1,m);
		int[] coins2=oneNumCoins(arr2,m);
		int res=0;
		for(int i=0;i<=m;++i){
			res+=coins1[i]*coins2[m-i];
		}
		return res;
	}

	public static int[] randNumCoins(int[] arr,int m){
		if(arr==null||arr.length<1||m<0){
			return null;
		}

		int[] arr1=new int[m+1];
		arr1[0]=1;

		int[] arr2=new int[m+1];
		for(int i=arr.length-1;i>=0;--i){
			for(int j=0;j<=m;j++){
				arr2[j]=0;
				for(int k=0;k*arr[i]<=j;++k){
					arr2[j]+=arr1[j-k*arr[i]];
				}
			}
			int[] tmp=arr1;
			arr1=arr2;
			arr2=tmp;
		}

		return arr1;
	}

	public static int[] oneNumCoins(int[] arr,int m){
		if(arr==null||arr.length<1||m<0){
			return null;
		}

		int[] arr1=new int[m+1];
		arr1[0]=1;

		int[] arr2=new int[m+1];
		for(int i=arr.length-1;i>=0;--i){
			for(int j=0;j<=m;j++){
				arr2[j]=arr1[j]+(j-arr[i]>=0?arr1[j-arr[i]]:0);
			}
			int[] tmp=arr1;
			arr1=arr2;
			arr2=tmp;
		}
		
		return arr1;
	}

	//左程云实现
	// dp[i][j]：arr[0..i]上的硬币随便用，组成j块钱的方法
	public static int minCoinsDP2(int[] arr1,int[] arr2,int m){
		if(arr1==null||arr2==null||m<0){
			return 0;
		}

		int[] coins1=randCoins(arr1,m);
		int[] coins2=oneCoins(arr2,m);

		int res=0;
		for(int i=0;i<=m;i++){
			res+=coins1[i]*coins2[m-i];
		}
		return res;
	}

	public static int[] randCoins(int[] arr,int m){
		if(arr==null||m<0){
			return null;
		}
		
		int len=arr.length;
		int[] arr1=new int[m+1];
		int[] arr2=new int[m+1];

		for(int j=0;j<=m;++j){
			if(j%arr[0]==0){
				arr1[j]=1;
			}
		}
		
		for(int i=1;i<len;i++){
			for(int j=0;j<=m;++j){
				arr2[j]=0;
				for(int k=0;k*arr[i]<=j;++k){
					arr2[j]+=arr1[j-k*arr[i]];
				}
			}
			int[] tmp=arr1;
			arr1=arr2;
			arr2=tmp;
		}

		return arr1;
	}
	
	public static int[] oneCoins(int[] arr,int m){
		if(arr==null||arr.length<1||m<0){
			return null;
		}
		
		int len=arr.length;
		int[] arr1=new int[m+1];
		int[] arr2=new int[m+1];
		arr1[0]=1;
		if(arr[0]<=m){
			arr1[arr[0]]=1;
		}

		for(int i=1;i<len;++i){
			for(int j=0;j<=m;++j){
				arr2[j]=arr1[j]+(j-arr[i]>=0?arr1[j-arr[i]]:0);
			}
			int[] tmp=arr1;
			arr1=arr2;
			arr2=tmp;
		}

		return arr1;
	}

	public static int minCoinsDP3(int[] arr1,int[] arr2,int m){
		if(arr1==null||arr2==null||m<0){
			return 0;
		}

		int[] coins1=randCoins3(arr1,m);
		int[] coins2=oneCoins3(arr2,m);

		int res=0;
		for(int i=0;i<=m;i++){
			res+=coins1[i]*coins2[m-i];
		}
		return res;
	}

	//使用一维表
	public static int[] randCoins3(int[] arr,int m){
		if(arr==null||m<0){
			return null;
		}
		
		int len=arr.length;
		int[] res=new int[m+1];

		for(int j=0;j<=m;++j){
			if(j%arr[0]==0){
				res[j]=1;
			}
		}
		
		for(int i=1;i<len;i++){
			for(int j=m;j>=0;--j){
				int cn=0;
				for(int k=0;k*arr[i]<=j;++k){
					cn+=res[j-k*arr[i]];
				}
				res[j]=cn;
			}
		}

		return res;
	}

	//使用一维表格
	public static int[] oneCoins3(int[] arr,int m){
		if(arr==null||arr.length<1||m<0){
			return null;
		}
		
		int len=arr.length;
		int[] res=new int[m+1];
		res[0]=1;
		if(arr[0]<=m){
			res[arr[0]]=1;
		}

		for(int i=1;i<len;++i){
			for(int j=m;j>=0;--j){
				res[j]=res[j]+(j-arr[i]>=0?res[j-arr[i]]:0);
			}
		}

		return res;
	}

	public static int minCoinsDP4(int[] arr1,int[] arr2,int m){
		if(arr1==null||arr2==null||m<0){
			return 0;
		}

		int[] coins1=randCoins4(arr1,m);
		int[] coins2=oneCoins4(arr2,m);

		int res=0;
		for(int i=0;i<=m;i++){
			res+=coins1[i]*coins2[m-i];
		}
		return res;
	}
	
	//求每一个格子，有枚举行为
	
	//使用一维表,且进行斜率优化
	public static int[] randCoins4(int[] arr,int m){
		if(arr==null||m<0){
			return null;
		}
		
		int len=arr.length;
		int[] res=new int[m+1];

		for(int j=0;j<=m;++j){
			if(j%arr[0]==0){
				res[j]=1;
			}
		}
		
		for(int i=1;i<len;i++){
			for(int j=0;j<=m;++j){
				res[j]=res[j]+(j-arr[i]>=0?res[j-arr[i]]:0);
			}
		}

		return res;
	}

	//使用一维表格，且进行斜率优化
	public static int[] oneCoins4(int[] arr,int m){
		if(arr==null||arr.length<1||m<0){
			return null;
		}
		
		int len=arr.length;
		int[] res=new int[m+1];
		res[0]=1;
		if(arr[0]<=m){
			res[arr[0]]=1;
		}

		for(int i=1;i<len;++i){
			for(int j=m;j>=0;--j){
				res[j]=res[j]+(j-arr[i]>=0?res[j-arr[i]]:0);
			}
		}

		return res;
	}



	public static void main(String[] args){
		int[] arr={
			1,2,3,4,5,6,7,8,9,10
		};
		int m1=3;
		int m2=arr.length-m1;
		int m=30;
		System.out.println(minCoins(arr,m1,m2,m));
		System.out.println(minCoins1(arr,m1,m2,m));
		System.out.println(minCoinsDP(arr,m1,m2,m));
		
		int[] arr1={
			1,2,3
		};
		int[] arr2={
			4,5,6,7,8,9,10
		};
		System.out.println(minCoins2(arr1,arr2,m));
		System.out.println(minCoinsDP1(arr1,arr2,m));
		System.out.println(minCoinsDP2(arr1,arr2,m));
		System.out.println(minCoinsDP3(arr1,arr2,m));
		System.out.println(minCoinsDP4(arr1,arr2,m));

		System.out.println("hello world");
	}
}






