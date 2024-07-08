/*************************************************************************
    > File Name: question47.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 21 19:59:52 2024
 ************************************************************************/


public class question47{

	//最长递增子序列问题
	
	//自己实现
	private static int maxlen=0;
	public static int getMaxLen(int[] arr){
		if(arr==null){
			return 0;
		}

		process(arr,Integer.MIN_VALUE,0,0);
		
		return maxlen;
	}
		
	public static void process(int[] arr,int pre,int index,int cn){
		if(index==arr.length){
			maxlen=Math.max(maxlen,cn);
			return;
		}

		if(arr[index]>=pre){
			process(arr,arr[index],index+1,cn+1);//要
			//process(arr,pre,index+1,cn);//不要
		} 
		process(arr,pre,index+1,cn);//不能要，能要但是不要
	}


	public static int getMaxLen1(int[] arr){
		if(arr==null){
			return 0;
		}

		maxlen=0;
		
		process1(arr,Integer.MIN_VALUE,0,0);

		return maxlen;
	}

	public static void process1(int[] arr,int pre,int index,int cn){
		if(index==arr.length){
			maxlen=Math.max(maxlen,cn);
		}

		for(int i=index;i<arr.length;i++){
			if(arr[i]>=pre){
				process1(arr,arr[i],i+1,cn+1);
			}
			process1(arr,pre,i+1,cn);
		}
	}


	//左程云实现
	public static int getMaxLenDP(int[] arr){
		if(arr==null){
			return 0;
		}
		
		int len=arr.length;
		int[] dp=new int[len];
		dp[0]=1;

		int max=1;
		for(int i=1;i<len;i++){
			dp[i]=1;
			for(int j=0;j<i;++j){
				if(arr[i]>=arr[j]){
					dp[i]=Math.max(dp[i],dp[j]+1);
				}
			}
			
			max=Math.max(dp[i],max);
		}
		 
		return max;
	}

	

	//左程云实现：O(NlogN)	
	public static int getMaxLenDP1(int[] arr){
		if(arr==null){
			return 0;
		}

		int len=arr.length;

		int[] dp=new int[len];

		int[] ends=new int[len];
		int cn=0;//有效区域长度
		int findIndex=-1;

		for(int i=0;i<len;i++){
			findIndex=binarySearch(ends,cn,arr[i]);

			if(findIndex==-1){
				findIndex=cn;
				++cn;
			}
			ends[findIndex]=arr[i];
			dp[i]=findIndex+1;

			/*
			if(findIndex==-1){
				ends[cn++]=arr[i];
				dp[i]=cn;
			} else {
				ends[findIndex]=arr[i];
				dp[i]=findIndex+1;
			}
			*/
		}
		
		return cn;
	}

	public static int binarySearch(int[] ends,int len,int find){
		int start=0;
		int end=len;
		int middle=-1;

		int pre=-1;
		while(start<end){
			middle=(start+end)/2;

			if(ends[middle]>=find){
				pre=middle;
				end=middle;
			} else {
				start=middle+1;
			}
		}

		return pre;
	}



	public static void main(String[] args){

		int[] arr={
			3,1,2,6,3,4
		};

		System.out.println(getMaxLen(arr));
		System.out.println(getMaxLen1(arr));
		System.out.println(getMaxLenDP(arr));
		System.out.println(getMaxLenDP1(arr));

		System.out.println("hello world");
	}
}





