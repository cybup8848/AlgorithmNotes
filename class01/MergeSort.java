/*************************************************************************
    > File Name: MergeSort.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun Jan 14 14:34:46 2024
 ************************************************************************/
//package class01;
import java.util.Arrays;
public class MergeSort{
	//归并
	public static void merge(int[] arr,int left,int mid,int right){
		int[] res=new int[right-left+1];
		int i=left;
		int j=mid+1;
		int k=0;
		while((i<=mid)&&(j<=right)){
			if(arr[i]<=arr[j]){
				res[k]=arr[i++];
			} else {
				res[k]=arr[j++];
			}
			k++;
		}
		if(i>mid){
			while(j<=right)
				res[k++]=arr[j++];
		} else {
			while(i<=mid)
				res[k++]=arr[i++];
		}
		
		/*
		while(i<=mid)
			res[k++]=arr[i++];
		while(j<=right)
			res[k++]=arr[j++];
		*/

		k=0;
		for(int m=left;m<=right;m++)
			arr[m]=res[k++];
	}

	//归并排序
	public static void mergeSort(int[] arr,int left,int right){
		if(left>=right)
			return;
				
		//划分
		int mid=left+((right-left)>>1);//不可以写成：int mid=left+(right-left)>>1,因为+比>>运算符优先级高
		mergeSort(arr,left,mid);
		mergeSort(arr,mid+1,right);
		merge(arr,left,mid,right);
	}

	//for test
	public static void comparator(int[] arr){
		Arrays.sort(arr);
	}

	//generate random array
	public static int[] generateRandomArray(int maxSize,int maxValue){
		int len=(int)((maxSize+1)*Math.random());
		int[] rand=new int[len];
		for(int i=0;i<len;i++){
			rand[i]=(int)((maxValue+1)*Math.random())-(int)(maxValue*Math.random());
		}
		return rand;
	}

	//compare is equal
	public static boolean isEqual(int[] arr1,int[] arr2){
		int len1=arr1.length;
		int len2=arr2.length;
		if(len1!=len2)
			return false;
		for(int i=0;i<len1;i++){
			if(arr1[i]!=arr2[i])
				return false;
		}
		return true;
	}
	
	//cpay array
	public static int[] copyArray(int[] arr){
		if(arr==null)
			return null;
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++)
			res[i]=arr[i];
		return res;
	}
	
	public static void printArray(int[] arr){
		for(int x:arr)
			System.out.println(x);
	}

	public static void main1(String[] args){
		int testTimes=500000;
		int maxSize=100;
		int maxValue=100;
		boolean flag=true;
		for(int i=0;i<testTimes;i++){
			int[] arr1=generateRandomArray(maxSize,maxValue);
			int[] arr2=copyArray(arr1);
			comparator(arr1);
			mergeSort(arr2,0,arr2.length-1);
			if(!isEqual(arr1,arr2)){
				flag=false;
				break;
			}
		}
		System.out.println(flag?"Nice":"error");
		
		/*
		int[] res={1,2,3,5,67,4,1};
		//comparator(res);
		mergeSort(res,0,res.length-1);
		printArray(res);	
		*/
		
		System.out.println("Hello World");
	}

	//小和问题：在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。求一个数组的小和。
	//eg：[1，3，4，2，5]，1左边比1小的数，没有；3左边比3小的数，1；4左边比4小的数，1、3；
	//2左边比2小的数，1；5左边比5小的数，1、3、4、2；所以小和为1+1+3+1+1+3+4+2=1c6
	public static int sum=0;
	public static int minSum(int[] arr,int left,int right){
		if(left==right)
			return 0;
		int mid=left+((right-left)>>1);
		minSum(arr,left,mid);
		minSum(arr,mid+1,right);
		sum+=merge2(arr,left,mid,right);
		return sum;
	}
	
	public static int merge2(int[] arr,int left,int mid,int right){
		int[] res=new int[right-left+1];
		int i=left;
		int j=mid+1;
		int k=0;
		int sum=0;

		/*
		for(;(i<=mid)&&(j<=right);k++){
			if(arr[i]<arr[j]){
				res[k]=arr[j++];
				for(int l=i;l<=mid;l++)
					sum+=arr[l];
			} else {
				res[k]=arr[i++];
			}
		}
		*/
		
		while((i<=mid)&&(j<=right)){
			if(arr[i]<arr[j]){
				res[k++]=arr[i];
				sum+=(right-j+1)*arr[i];
				i++;
			} else if(arr[i]==arr[j]){
				res[k++]=arr[j++];
			} else {
				res[k++]=arr[j++];
			}
		}

		/*可以简写成这样，上述为了说明：相等的情况下，必须先拷贝右侧；否则会出现遗漏的情况
		while((i<=mid)&&(j<=right)){
			if(arr[i]<arr[j]){
				res[k++]=arr[i];
				sum+=(right-j+1)*arr[i];
				i++;
			} else {
				res[k++]=arr[j++];
			}
		}
		*/
		
		

		while(i<=mid)
			res[k++]=arr[i++];
		while(j<=right)
			res[k++]=arr[j++];

		k=0;
		for(int m=left;m<=right;m++)
			arr[m]=res[k++];
		return sum;
	}


	//逆序对问题：在一个数组中，左边的数如果比右边的数大，则这两个数构成一个逆序对，请打印所有逆序对。
	public static void merge1(int[] arr,int left,int mid,int right){
		int[] res=new int[right-left+1];
		int i=left;
		int j=mid+1;
		int k=0;
		for(;(i<=mid)&&(j<=right);k++){
			if(arr[i]>arr[j]){
				res[k]=arr[i++];
				for(int l=j;l<=right;l++)
					System.out.println(res[k]+"   "+arr[l]);
			} else {
				res[k]=arr[j++];
			}
		}
		while(i<=mid)
			res[k++]=arr[i++];
		while(j<=right)
			res[k++]=arr[j++];
		k=0;
		for(int m=left;m<=right;m++,k++)
			arr[m]=res[k];

	}
	public static void printReverseOrderPairs(int[] arr,int left,int right){
		if(left==right)
			return;
		int mid=left+((right-left)>>1);
		printReverseOrderPairs(arr,left,mid);
		printReverseOrderPairs(arr,mid+1,right);
		merge1(arr,left,mid,right);
	}

	public static void main(String[] args){
		int[] arr={5,6,7,1,2,4,9};
		printReverseOrderPairs(arr,0,arr.length-1);
		System.out.println("Hello World!");
		int[] arr1={3,2,4,5,0};
		printReverseOrderPairs(arr1,0,arr1.length-1);


		int[] array={1,3,4,2,5};
		System.out.println(minSum(array,0,array.length-1));
		int[] array1={1,1,1,1,2,2,3,3,5,1,1,1,2,2,6,6,7};
		sum=0;
		System.out.println(minSum(array1,0,array1.length-1));
	}
}
