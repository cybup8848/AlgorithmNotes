/*************************************************************************
    > File Name: question7.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 12 17:20:30 2024
 ************************************************************************/




public class question7{

	public static int getMaxLength(int[] arr,int k){
		if(arr==null||arr.length==0||k<=0){
			return 0;
		}
		
		int len=arr.length;
		int l=-1;//(l,r]
		int r=-1;
		int sum=0;
		int res=0;
		while(r<len){
			if(sum<=k){
				if(sum==k){
					res=Math.max(res,r-l);
				}
				r++;
				sum+=(r<len?arr[r]:0);
			} else if(sum>k){
				l++;
				sum-=arr[l];
			}
		}

		return res;
	}

	
	//左程云实现
	public static int getMaxLength1(int[] arr,int k){
		if(arr==null||arr.length==0||k<=0){
			return 0;
		}

		//如果 left==right+1 ，表示窗口不再有数
		int left=0;//[left,right]
		int right=0;
		int sum=arr[0];
		int len=0;
		while(right<arr.length){
			if(sum==k){
				len=Math.max(len,right-left+1);
				sum-=arr[left++];
			} else if(sum<k){
				right++;
				if(right==arr.length){
					break;
				}
				sum+=arr[right];
			} else {
				sum-=arr[left++];
			}
		}

		return len;
	}




	public static void main(String[] args){
		int[] arr1={
			1,2,1,1,1
		};
		int k=3;
		System.out.println(getMaxLength(arr1,k));
		System.out.println(getMaxLength1(arr1,k));
		
		k=-1;
		System.out.println(getMaxLength(arr1,k));
		System.out.println(getMaxLength1(arr1,k));
		
		k=14;
		System.out.println(getMaxLength(arr1,k));
		System.out.println(getMaxLength1(arr1,k));


		System.out.println("hello world");
	}
}





