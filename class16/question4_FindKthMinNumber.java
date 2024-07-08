/*************************************************************************
    > File Name: question4_FindKthNumber.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 30 14:50:59 2024
 ************************************************************************/


public class question4_FindKthMinNumber{

	public static int findKthNum(int[] arr1,int[] arr2,int kth){
		if(arr1==null||arr2==null){
			throw new RuntimeException("Your arr is invalid!");
		}

		if(kth<1||kth>arr1.length+arr2.length){
			throw new RuntimeException("K is invalid!");
		}

		int[] longs=arr1.length>=arr2.length?arr1:arr2;
		int[] shorts=arr1.length<arr2.length?arr1:arr2;

		int l=longs.length;
		int s=shorts.length;
		
		//kth属于[1,s]
		if(kth<=s){
			return getUpMedian(shorts,0,kth-1,longs,0,kth-1);
		}
		
		//kth属于(l,l+s]
		if(kth>l){
			if(shorts[kth-l-1]>longs[l-1]){//手动检查
				return shorts[kth-l-1];
			}
			if(longs[kth-s-1]>=shorts[s-1]){//手动检查
				return longs[kth-s-1];
			}

			return getUpMedian(shorts,kth-l,s-1,longs,kth-s,l-1);
		}
		
		//kth属于(s,l]
		if(longs[kth-s-1]>=shorts[s-1]){//手动检查
			return longs[kth-s-1];
		}
		return getUpMedian(shorts,0,s-1,longs,kth-s,kth-1);
	}
	
	//a1[s1...e1]    a2[s2...e2] 一定要等长
	public static int getUpMedian(int[] a1,int s1,int e1,int[] a2,int s2,int e2){
		int mid1=0;
		int mid2=0;
		int offset=0;
		while(s1<e1){
			mid1=(s1+e1)/2;
			mid2=(s2+e2)/2;
			offset=((e1-s1+1)&1)^1;
			if(a1[mid1]>a2[mid2]){
				e1=mid1;
				s2=mid2+offset;
			} else if(a1[mid1]<a2[mid2]){
				s1=mid1+offset;
				e2=mid2;
			} else {
				return a1[mid1];
			}
		}

		return Math.min(a1[s1],a2[s2]);
	}
	
	public static int str2num(String s){
		if(s==null){
			return -1;
		}
		int res=0;
		try{
			res=Integer.parseInt(s);
		} catch(Exception e){
			System.out.println("转换失败");
			return -1;
		}

		return res;
	}
	
	public static void main(String[] args){
		int[] arr1={
			1,2,4,6,57,232
		};

		int[] arr2={
			2,5,23,57,79,1235
		};

		for(int i=1;i<=arr1.length+arr2.length;i++){
			System.out.println(findKthNum(arr1,arr2,i));
		}
		
		System.out.println("\n\n\n\n");
		
		String s="2353476548659876967";
		System.out.println(str2num(s));
		s="-235437654867976326543";
		System.out.println(str2num(s));
		s="54364";
		System.out.println(str2num(s));
		s="-234235";
		System.out.println(str2num(s));

		int[] arr3={
			1,6,10,15
		};
		int[] arr4={
			2,3,9,20
		};
		System.out.println(getUpMedian(arr3,0,3,arr4,0,3));

		System.out.println("hello world");
	}


}




