/*************************************************************************
    > File Name: NetherlandsFlag.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun Jan 14 22:37:53 2024
 ************************************************************************/
public class NetherlandsFlag{
	//给定一个数组arr，和一个数num，请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。要求额外空间复杂度O(1)。时间复杂度O(N)
	public static void netherlandFlag1(int[] arr,int num){
		int len=arr.length;
		if(len<2)
			return;
		int index=0;
		for(int i=0;i<len;i++){
			if(arr[i]<=num){
				/* index和i可能时同一个位置，所以不保险
				arr[index]=arr[index]^arr[i];
				arr[i]=arr[index]^arr[i];
				arr[index]=arr[index]^arr[i];
				*/
				int tmp=arr[i];
				arr[i]=arr[index];
				arr[index]=tmp;
				index++;
			}
		}
	}
	
	//荷兰国旗问题
	//给定一个数组arr，和一个数组num，请把小于num的数放在数组的左边，等于num的数放在数组的中间，大于num的数放在数组的右边。要求额外空间复杂度
	//O(1)，时间复杂度O(N)
	public static void netherlandFlag2(int[] arr,int num){
		int len=arr.length;
		if(len<2)
			return;
		int index=0;
		for(int i=0;i<len;i++){
			if(arr[i]<=num){
				int tmp=arr[i];
				arr[i]=arr[index];
				arr[index]=tmp;
				index++;
			}
		}
		
		len=index;
		index=0;
		for(int i=0;i<len;i++){
			if(arr[i]<num){
				int tmp=arr[i];
				arr[i]=arr[index];
				arr[index]=tmp;
				index++;
			}
		}
	}

	//荷兰国旗问题，版本2
	public static void netherland(int[] arr,int num){
		int len=arr.length;
		if(len<2)
			return;
		
		int left=0;
		int right=len-1;
		int index=0;
		for(int i=0;i<=right;){
			if(arr[i]<num){//  <num
				int tmp=arr[i];
				arr[i]=arr[index];
				arr[index]=tmp;
				index++;
				i++;
			} else if(arr[i]>num){//  >num
				int tmp=arr[i];
				arr[i]=arr[right];
				arr[right]=tmp;
				right--;
			} else {//  ==num
				i++;
			}
		}
	}


	public static void printArray(int[] arr){
		for(int x:arr)
			System.out.println(x);
	}
	
	
	public static void main(String[] args){
		int[] arr={1,4,34,57,23,90};
		netherlandFlag1(arr,45);
		printArray(arr);

		


		int[] arr1={1,4,34,57,23,90,34};
		//netherlandFlag2(arr1,34);
		netherland(arr1,34);
		printArray(arr1);
	
		System.out.println("\n\n\n");
		int[] arr2={3,5,6,3,4,5,2,6,9,0};
		netherland(arr2,5);
		printArray(arr2);
		

		System.out.println("Hello World");
	}
}
