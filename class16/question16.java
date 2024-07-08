/*************************************************************************
    > File Name: question16.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jun 18 21:26:07 2024
 ************************************************************************/

import java.util.Arrays;

public class question16{
	
	public static void qs(int[] arr,int start,int end){
		if(start>=end){
			return;
		}
		
		int index=start;
		for(int i=start+1;i<=end;i++){
			if(arr[i]<=arr[start]){
				index++;
				int tmp=arr[i];
				arr[i]=arr[index];
				arr[index]=tmp;
			}
		}

		int tmp=arr[index];
		arr[index]=arr[start];
		arr[start]=tmp;

		qs(arr,start,index-1);
		qs(arr,index+1,end);
	}
	
	//通过二分查找，在有序数组中，找到最右边<=num的索引下标
	public static int findMostRightLessEqual(int[] arr,int num){
		if(arr==null||arr.length==0){
			return -1;
		}

		int res=-1;
		int l=0;
		int r=arr.length;
		int mid=0;
		while(l<r){
			mid=l+(r-l)/2;
			if(arr[mid]<=num){
				res=mid;
				l=mid+1;
			} else {
				r=mid;
			}
		}

		return res;
	}

	public static int minBoats(int[] arr,int limit){
		if(arr==null||limit<=0){
			return Integer.MAX_VALUE;
		}
		
		int len=arr.length;

		//大的过滤，如果体重超过limit，则需要的船数无限大
		for(int i=0;i<len;i++){
			if(arr[i]>limit){
				return Integer.MAX_VALUE;
			}
		}
		
		if(arr[0]>limit/2){
			return len;
		}
		if(arr[len-1]<=limit/2){
			return (len+1)/2;
		}

		int cn0=0;//代表l左侧不匹配的个数，也就是❌个数
		int cn1=0;//代表左侧匹配的个数，也就是对号个数
		int cn2=0;//代表r右侧没有匹配的个数，也就是三角号的个数
		
		int l=findMostRightLessEqual(arr,limit/2);
		int r=l+1;
		int prer=r;
		while(l>=0&&r<len){
			if(arr[l]+arr[r]<=limit){
				++r;
				--l;
				cn1++;
			} else {
				--l;
				cn0++;
			}
		}
		
		cn2=len-r;
		cn1+=l+1;

		return (cn0+1)/2+cn1+cn2;
	}
	
	
	//左程云实现
	//请保证arr有序
	public static int minBoat(int[] arr,int limit){
		//arr需要先排序
		if(arr==null||arr.length==0){
			return 0;
		}
		
		if(arr[arr.length-1]<=limit/2){
			return (arr.length+1)/2;
		}

		if(arr[0]>limit){
			return arr.length;
		}


		//遍历数组arr，如果发现某个值>limit，怎么也装不下，直接返回
		int lessR=-1;
		for(int i=arr.length-1;i>=0;--i){
			if(arr[i]<=(limit/2)){
				lessR=i;
				break;
			}
		}

		if(lessR==-1){
			return arr.length;
		}

		int L=lessR;
		int R=lessR+1;
		int lessUnused=0;
		while(L>=0){
			int solved=0;
			while(R<arr.length&&arr[L]+arr[R]<=limit){
				++R;
				++solved;
			}
			
			if(solved==0){
				lessUnused++;
				L--;
			} else {
				L=Math.max(-1,L-solved);
			}
		}
		
		int lessAll=lessR+1;//左半区域总个数<=limit/2的区域
		int lessUsed=lessAll-lessUnused;//画对号的量
		int moreUnsolved=arr.length-lessR-1-lessUsed;// >limit/2区中，没搞定的数量
													 
		return lessUsed+((lessUnused+1)>>1)+moreUnsolved;
	}


	public static void main(String[] args){
		int[] arr={
			23543,3464,35,24,65,23,465,4
		};

		qs(arr,0,arr.length-1);
		for(int x:arr){
			System.out.print(x+"	");
		}
		System.out.println("\n\n");
		
		arr=new int[]{
			1,1,3,3,3,4,4,5,5,5,7,7,9,9,9
		};
		int limit=10;
		System.out.println(minBoats(arr,limit));
		System.out.println(minBoat(arr,limit));	

		arr=new int[]{
			2,2,2,3,3,5,5,5,6,6,6,8,8,9,9,9,9
		};
		limit=10;
		System.out.println(minBoats(arr,limit));
		System.out.println(minBoat(arr,limit));

		System.out.println("hello world");
	}
}




