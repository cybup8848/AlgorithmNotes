/*************************************************************************
    > File Name: question27.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 14 11:04:51 2024
 ************************************************************************/



public class question27{

	//时间复杂度和空间复杂度均为O(N)
	public static int maxValue(int[] arr){
		if(arr==null||arr.length<2){
			return 0;
		}
		
		int len=arr.length;

		//[0,i]最大值
		int[] left=new int[len];
		left[0]=arr[0];
		for(int i=1;i<len;i++){
			left[i]=Math.max(left[i-1],arr[i]);
		}

		//(i,N)最大值
		int[] right=new int[len];
		right[len-1]=Integer.MIN_VALUE;
		for(int i=len-2;i>=0;i--){
			right[i]=Math.max(right[i+1],arr[i+1]);
		}

		int res=0;
		for(int i=0;i<len-1;i++){
			res=Math.max(res,Math.abs(right[i]-left[i]));
		}
		
		return res;
	}

	//时间复杂度O(N)，空间复杂度O(1)
	public static int maxValue1(int[] arr){
		if(arr==null||arr.length<2){
			return 0;
		}
		
		int len=arr.length;
		int max=arr[0];
		for(int i=1;i<len;i++){
			max=Math.max(max,arr[i]);
		}

		return max-Math.min(arr[0],arr[len-1]);
	}





	public static void main(String[] args){
		int[] arr={
			1,2,3,45,23,454,2424,214,436,2421,6876,8,23242
		};

		System.out.println(maxValue(arr));
		System.out.println(maxValue1(arr));

		int[] arr1={
			2134,543,543,654,54,7,3253,3254,4,23565436,324,1,235345
		};
		System.out.println(maxValue(arr1));
		System.out.println(maxValue1(arr1));
		System.out.println(23565436-3254);
		

		System.out.println("hello world");
	}
}
