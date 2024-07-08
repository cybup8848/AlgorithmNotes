/*************************************************************************
    > File Name: QuickSort.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon Jan 15 10:49:58 2024
 ************************************************************************/
import java.util.Arrays;
public class QuickSort{
	/*
	public static void quickSort1(int[] arr,int left,int right){
		if(left>=right)
			return;
		
		//从小到大
		int l=left;
		int r=right-1;
		while(l<=r){
			while((l<=r)&&(arr[l]<=arr[right])){
				l++;
			}
			while((l<=r)&&(arr[r]>arr[right])){
				r--;
			}
			if(l<r){
				swap(arr,l,r);
			}
		}//肯定不会出现l==r，因为数组中的数字要么<=arr[right]、要么>arr[right]
		swap(arr,l,right);
		quickSort1(arr,left,l-1);
		quickSort1(arr,l+1,right);
	}
	*/
	public static void quickSort1(int[] arr,int left,int right){
		if(left>=right)
			return;
		int l=left;
		int r=right-1;
		int rand=left+(int)((right-left+1)*Math.random());
		swap(arr,rand,right);
		while(l<=r){
			while((l<=r)&&(arr[l]<=arr[right]))
				l++;
			while((l<=r)&&(arr[r]>arr[right]))
				r--;
			if(l<r)
				swap(arr,l,r);
		}
		swap(arr,l,right);
		quickSort1(arr,left,l-1);
		quickSort1(arr,l+1,right);
	}

	public static void quickSort1_1(int[] arr,int left,int right){
		if(left>=right)
			return;
		int index=left;
		for(int i=left;i<right;i++){
			if(arr[i]<=arr[right]){
				swap(arr,index++,i);
			}
		}
		swap(arr,index,right);
		quickSort1_1(arr,left,index-1);
		quickSort1_1(arr,index+1,right);
	}


	//利用荷兰国旗问题
	public static void quickSort2(int[] arr,int left,int right){
		if(left>=right)
			return;
		
		int l=left;
		int r=right-1;
		for(int i=left;i<=r;){
			if(arr[i]<arr[right]){
				swap(arr,i,l);
				l++;
				i++;
			} else if(arr[i]>arr[right]){
				swap(arr,i,r);
				r--;
			} else {//arr[i]==arr[right]的情况
				i++;
			}
		}
		swap(arr,r+1,right);
		quickSort2(arr,left,l-1);
		quickSort2(arr,r+2,right);
	}

	public static void quickSort3(int[] arr,int left,int right){
		if(left>=right)
			return;
		int r=left+(int)((right-left+1)*Math.random());
		swap(arr,r,right);
		int less=left-1;
		int more=right;
		int i=left;
		while(i<more){
			if(arr[i]<arr[right])
				swap(arr,++less,i++);
			else if(arr[i]>arr[right])
				swap(arr,--more,i);
			else
				++i;
		}
		swap(arr,more,right);
		quickSort3(arr,left,less);
		quickSort3(arr,more+1,right);
	}


	//for test
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


	
	public static void swap(int[] arr,int i,int j){
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;
	}

	public static void main(String[] args){
		int testTimes=500000;
		int maxValue=100;
		int maxSize=100;
		boolean flag=true;
		for(int i=0;i<testTimes;i++){
			int[] arr1=generateRandomArray(maxSize,maxValue);
			int[] arr2=copyArray(arr1);
			comparator(arr1);
			//quickSort1(arr2,0,arr2.length-1);
			quickSort1_1(arr2,0,arr2.length-1);
			//quickSort2(arr2,0,arr2.length-1);
			//quickSort3(arr2,0,arr2.length-1);
			if(!isEqual(arr1,arr2)){
				flag=false;
			}
		}
		System.out.println(flag?"nice":"wrong");
		
		
		
		System.out.println("Hello World");
	}
}






