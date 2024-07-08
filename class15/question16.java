/*************************************************************************
    > File Name: question16.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May  9 14:09:29 2024
 ************************************************************************/

public class question16{

	public static int minMoveCount(int[] arr){
		if(arr==null){
			return 0;
		}


		int len=arr.length;
		int s=sum(arr);
		if(s%len!=0){
			return -1;
		}
		
		int max=0;
		int ave=s/len;
		for(int i=0;i<len;i++){
			int left=getLeftSum(arr,i);
			int right=getRightSum(arr,i);
			int leftNeed=left-i*ave;
			int rightNeed=right-(len-1-i)*ave;
			
			if(leftNeed<0&&rightNeed<0){
				max=Math.max(max,Math.abs(leftNeed+rightNeed));
			} else {
				max=Math.max(max,Math.max(Math.abs(leftNeed),Math.abs(rightNeed)));
			}
			
		}

		return max;
	}

	private static int getLeftSum(int[] arr,int l){
		int res=0;
		for(int i=0;i<l;i++){
			res+=arr[i];
		}
		return res;
	}

	private static int getRightSum(int[] arr,int s){
		int res=0;
		int len=arr.length;
		for(int i=s+1;i<len;i++){
			res+=arr[i];
		}
		return res;
	}
	
	private static int sum(int[] arr){
		int res=0;
		for(int x:arr){
			res+=x;
		}
		return res;
	}



	//预处理，预处理数组，加速
	public static int minMoveCount1(int[] arr){
		if(arr==null){
			return 0;
		}

		int len=arr.length;
		int[] left=getLeftSumArr(arr);

		if((left[len-1]+arr[len-1])%len!=0){
			return -1;
		}

		int[] right=getRightSumArr(arr);
		
		int ave=(left[len-1]+arr[len-1])/len;
		int max=0;
		for(int i=0;i<len;i++){
			int leftNeed=left[i]-i*ave;
			int rightNeed=right[i]-(len-1-i)*ave;
			if(leftNeed<0&&rightNeed<0){
				max=Math.max(max,Math.abs(leftNeed+rightNeed));
			} else {
				max=Math.max(max,Math.max(Math.abs(leftNeed),Math.abs(rightNeed)));
			}
		}

		return max;
	}

	private static int[] getLeftSumArr(int[] arr){
		int len=arr.length;
		int[] res=new int[len];
		res[0]=0;
		for(int i=1;i<len;i++){
			res[i]=res[i-1]+arr[i-1];
		}
		return res;
	}

	private static int[] getRightSumArr(int[] arr){
		int len=arr.length;
		int[] res=new int[len];
		res[len-1]=0;
		for(int i=len-2;i>=0;--i){
			res[i]=res[i+1]+arr[i+1];
		}
		return res;
	}

	//左程云实现
	public static int MinOps(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int size=arr.length;
		int s=0;
		for(int i=0;i<size;i++){
			s+=arr[i];
		}

		if(s%size!=0){
			return -1;
		}
		
		int ave=s/size;
		int leftSum=0;
		int ans=0;
		for(int i=0;i<size;i++){

			//负需要输入，正需要输出
			int leftRest=leftSum-i*ave;

			int rightRest=(s-leftSum-arr[i])-(size-1-i)*ave;

			if(leftRest<0&&rightRest<0){
				ans=Math.max(ans,Math.abs(leftRest)+Math.abs(rightRest));
			} else {
				ans=Math.max(ans,Math.max(Math.abs(leftRest),Math.abs(rightRest)));
			}

			leftSum+=arr[i];
		}
		return ans;
	}

	public static void main(String[] args){
		int[] arr1={
			100,0,0,0
		};
		System.out.println(minMoveCount(arr1));
		System.out.println(minMoveCount1(arr1));
		System.out.println(MinOps(arr1));


		int[] arr2={
			0,50,30
		};
		System.out.println(minMoveCount(arr2));
		System.out.println(minMoveCount1(arr2));
		System.out.println(MinOps(arr2));

		int[] arr3={
			0,50,40
		};
		System.out.println(minMoveCount(arr3));
		System.out.println(minMoveCount1(arr3));
		System.out.println(MinOps(arr3));

		System.out.println("hello world");
	}
}


