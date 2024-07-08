/*************************************************************************
    > File Name: InsertionSort.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sat Jan 13 20:08:45 2024
 ************************************************************************/
package class00;
import java.util.Arrays;
public class InsertionSort{
	public static void insertionSort(int[] arr){
		int len=arr.length;
		if(arr==null||len<2){
			return;
		}

		for(int i=1;i<len;i++){
			int tmp=arr[i];
			int j=i-1;
			for(;j>=0;j--){
				if(tmp>=arr[j]){
					break;
				}
				arr[j+1]=arr[j];
			}
			arr[j+1]=tmp;
		}
	}

	public static void insert(int[] arr){
		int len=arr.length;
		if(arr==null||len<2){
			return;
		}

		for(int i=1;i<len;i++){
			for(int j=i;j>0;j--){
				if(arr[j]<=arr[j-1])
					break;
				swap(arr,j,j-1);
			}
		}
	}
	
	public static void insert1(int[] arr){
		int len=arr.length;
		if(arr==null||len<2){
			return;
		}
		
		for(int i=1;i<len;i++){
			for(int j=i-1;(j>=0)&&(arr[j]>arr[j+1]);j--){
				swap(arr,j,j+1);
			}
		}
	}
	
	//i和j是一个位置的话，会出错
	public static void swap(int[] arr,int i,int j){
		arr[i]=arr[i]^arr[j];
		arr[j]=arr[i]^arr[j];
		arr[i]=arr[i]^arr[j];
	}
	public static void printArr(int[] arr){
		int len=arr.length;
		for(int i=0;i<len;i++){
			System.out.println(arr[i]);
		}
	}

	//for test
	public static void comparator(int[] arr){
		Arrays.sort(arr);
	}

	//for test.相当于方法b
	public static int[] generateRandomArray(int maxSize,int maxValue){
		// Math.random()->[0,1)所有的小数，等概率返回一个
		// Math.ramdom()*N->[0,N)所有小数，等概率返回一个
		// (int)(Math.random()*N)->[0,N-1]所有整数，等概率返回一个
		int len=(int)((maxSize+1)*Math.random());//长度随机
		int[] arr=new int[len];
		for(int i=0;i<len;i++){
			arr[i]=(int)((maxValue+1)*Math.random())-(int)(maxValue*Math.random());
		}
		return arr;
	}

	public static int[] copyArray(int[] arr){
		if(arr==null)
			return null;
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=arr[i];
		}
		return res;
	}

	public static boolean isEqual(int[] arr1,int[] arr2){
		int len1=arr1.length;
		int len2=arr2.length;
		if(len1!=len2)
			return false;
		boolean equal=true;
		for(int i=0;i<len1;i++){
			if(arr1[i]!=arr2[i]){
				equal=false;
				break;
			}
		}
		return equal;
	}



	//在一个有序数组中，找某个数是否存在	
	public static boolean binarySearch(int[] arr,int num){
		int len=arr.length;
		int left=0,right=len-1;
		int mid=0;
		boolean isFind=false;
		while(left<=right){
			mid=(left+right)/2;
			if(arr[mid]==num){
				isFind=true;
				break;
			} else if(arr[mid]>num){
				right=mid-1;
			} else {
				left=mid+1;
			}
		}
		return isFind;
	}

	//在一个有序数组中，找>=某个数最左侧的位置：二分到结束
	public static int findIndex(int[] arr,int num){
		int len=arr.length;
		int left=0;
		int right=len-1;
		int mid=0;
		int index=-1;
		while(left<=right){
			mid=(left+right)/2;
			if(arr[mid]>=num){
				index=mid;
				right=mid-1;
			} else {
				left=mid+1;
			}
		}
		return index;
	}


	//局部最小值问题：在一个无序数组中，但是任何相邻的两个数不相等，求一个局部最小的位置/索引就可以
	//局部最小：arr[0]<arr[1]，则arr[0]为局部最小；arr[N-2]>arr[N-1]，则arr[N-2]为局部最小；arr[i-1]>arr[i] && arr[i]<arr[i+1]，则arr[i]局部最小
	//二分法不一定有序
	public static int findLocalMin(int[] arr){
		int len=arr.length;
		if(arr==null||len<2){
			return -1;
		}
		int left=0,right=len-1;
		int mid=0;
		int index=-1;

		if(arr[0]<arr[1])//判断开头
			return 0;

		if(arr[len-2]>arr[len-1])//判断结尾
			return len-1;
		
		//根据罗尔定理、费马定理、极值定理，肯定存在局部最小值
		while(left<=right){
			mid=(left+right)/2;
			if((arr[mid]<arr[mid-1])&&(arr[mid]<arr[mid+1])){
				index=mid;
				break;
			}
			else if(arr[mid]>=arr[mid-1])
				right=mid-1;
			else
				left=mid+1;
		}
		return index;
	}

	public static void main1(String[] args){
		System.out.println("Hwllo World");
		int[] arr={23,45,12,6,35,12,56};
		insertionSort(arr);
		printArr(arr);
		
		//进行二叉搜索，数组从小到大
		boolean flag=binarySearch(arr,45);
		System.out.println(flag);
		flag=binarySearch(arr,100);
		System.out.println(flag);
		System.out.println(findIndex(arr,6));
		System.out.println(findIndex(arr,25));
		System.out.println(findIndex(arr,100));
		insert(arr);
		printArr(arr);
		insert1(arr);
		printArr(arr);


		int[] arr1={1,2,2,2,2,2,2,3,3,3,3,3,3,3,4,4,4,5,5,5,6};
		System.out.println(findIndex(arr1,3));
	}

	public static void main(String[] args){
		int testTime=500000;
		int maxSize=100;
		int maxValue=100;
		boolean succeed=true;
		for(int i=0;i<testTime;i++){
			int[] arr1=generateRandomArray(maxSize,maxValue);
			int[] arr2=copyArray(arr1);
			insertionSort(arr1);
			comparator(arr2);
			if(!isEqual(arr1,arr2)){
				//打印arr1
				//打印arr2
				succeed=false;
				break;
			}
		}
		System.out.println(succeed?"Nice!":"Fucking fucked!");
	}
}




