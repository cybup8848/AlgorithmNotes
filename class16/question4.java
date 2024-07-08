/*************************************************************************
    > File Name: question4.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May 29 22:53:45 2024
 ************************************************************************/



public class question4{

	//常规做法，但是没分
	public static int[] topK(int[] arr1,int[] arr2,int k){
		if(k<=0||arr1==null||arr2==null){
			return null;
		}

		int[] res=new int[k];
		int len1=arr1.length;
		int len2=arr2.length;
		
		int index=k-1;
		int index1=len1-1;
		int index2=len2-1;
		while(index>=0&&index1>=0&&index2>=0){
			if(arr1[index1]>=arr2[index2]){
				res[index--]=arr1[index1--];
			} else {
				res[index--]=arr2[index2--];
			}
		}

		if(index<0){
			return res;
		}

		if(index1>=0){
			while(index>=0&&index1>=0){
				res[index--]=arr1[index1--];
			}
		} else {
			while(index>=0&&index2>=0){
				res[index--]=arr2[index2--];
			}
		}

		return res;
	}
	
	//左程云实现
	public static int[] topK1(int[] arr1,int[] arr2,int k){
		if(arr1==null||arr2==null||k<=0){
			return null;
		}
		
		
		
		
		return null;
	}
	
	//给定两个有序数组，找到第k小的数
	//时间复杂度：2log(N)log(<)
	public static int minK(int[] arr1,int[] arr2,int k){
		if(arr1==null||arr2==null||k<=0||k>arr1.length+arr2.length){
			return Integer.MIN_VALUE;
		}
		
		//假设第k小的数在arr1中
		int left=0;
		int right=arr1.length-1;
		int middle=0;
		while(left<=right){
			middle=left+(right-left)/2;
			
			//在arr2中找到最右边比arr1[middle]小(<=)的数的索引
			int mostRight=findMostRight(arr2,arr1[middle]);
			
			if(middle+mostRight+2==k){
				return arr1[middle];
			} else if(middle+mostRight+2<k){
				left=middle+1;
			} else {
				right=middle-1;
			}
		}

		//如果在arr1中找不到，那么就在arr2中找
		left=0;
		right=arr2.length;
		while(left<right){
			middle=left+(right-left)/2;

			//在arr1中找到最右边不大于arr2[middle]的数的索引
			int mostRight=findMostRight(arr1,arr2[middle]);
			
			if(middle+mostRight+2==k){
				return arr2[middle];
			} else if(middle+mostRight+2<k){
				left=middle+1;
			} else {
				right=middle;
			}
		}

		return Integer.MIN_VALUE;
	}

	//找到不大于target的最右边的索引
	public static int findMostRight(int[] arr,int target){
		if(arr==null||arr.length<1){
			return -1;
		}
		
		int l=0;
		int r=arr.length;
		int mid=0;
		int res=-1;
		while(l<r){
			mid=(r-l)/2+l;
			if(arr[mid]<=target){
				res=mid;
				l=mid+1;
			} else {
				r=mid;
			}
		}

		return res;
	}



	public static void main(String[] args){
		int[] arr1={
			1,3,5,7,9
		};
		
		int[] arr2={
			2,4,6,8,10
		};

		int len=arr1.length+arr2.length;
		for(int i=1;i<=len;++i){
			System.out.println(minK(arr1,arr2,i));
		}


		System.out.println("hello world");
	}
}



