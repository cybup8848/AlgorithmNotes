/*************************************************************************
    > File Name: question21bfprt.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jun 26 17:24:20 2024
 ************************************************************************/



public class question21bfprt{

		
	public static int[] getMinKNumsByBFPRT(int[] arr,int k){
		if(k<1||k>arr.length){
			return arr;
		}

		int minKth=getMinKthByBFPRT(arr,k);
		int[] res=new int[k];
		
		int index=0;
		for(int i=0;i<arr.length;i++){
			if(arr[i]<minKth){
				res[index++]=arr[i];
			}
		}

		for(;index!=res.length;index++){
			res[index]=minKth;
		}

		return res;
	}

	public static int getMinKthByBFPRT(int[] arr,int k){
		int[] copyArr=copyArray(arr);
		return select(copyArr,0,copyArr.length-1,k-1);
	}

	public static int[] copyArray(int[] arr){
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=arr[i];
		}
		
		return res;
	}
	
	//在arr[begin...end]范围上，求如果排序的话，i位置的数是谁，返回
	//i一定在begin~end范围上
	//在 [begin...end] 范围上求第 i 小的数
	public static int select(int[] arr,int begin,int end,int i){
		if(begin==end){
			return arr[begin];
		}

		//分组+组内排序+组成newarr+选出newarr的上中位数 pivot
		int pivot=medianOfMedians(arr,begin,end);//bfprt算法的前3步，返回base

		//下面开始玩 partition
		// 根据pivot做划分值 <p、=p、>p，返回等于区域的左边界和右边界
		// pivotRange[0] 等于区域的左边界
		// pivotRange[1] 等于区域的右边界

		int[] pivotRange=partition(arr,begin,end,pivot);
		if(i>=pivotRange[0]&&i<=pivotRange[1]){
			return arr[i];
		} else if(i<pivotRange[0]){
			return select(arr,begin,pivotRange[0]-1,i);
		} else {
			return select(arr,pivotRange[1]+1,end,i);
		}
	}

	public static int medianOfMedians(int[] arr,int begin,int end){
		int num=end-begin+1;
		int offset=num%5==0?0:1;
		int[] mArr=new int[num/5+offset];
		for(int i=0;i<mArr.length;i++){
			int beginI=begin+i*5;
			int endI=beginI+4;
			mArr[i]=getMedian(arr,beginI,Math.min(end,endI));
		}
		
		return select(mArr,0,mArr.length-1,mArr.length/2);//第三步是一个调用bfprt本身的过程
	}

	public static int[] partition(int[] arr,int begin,int end,int pivotValue){
		int small=begin-1;
		int cur=begin;
		int big=end+1;

		while(cur!=big){
			if(arr[cur]<pivotValue){
				swap(arr,++small,cur++);
			} else if(arr[cur]>pivotValue){
				swap(arr,cur,--big);
			} else {
				cur++;
			}
		}
		
		int[] range=new int[2];
		range[0]=small+1;
		range[1]=big-1;
		
		return range;
	}

	public static void swap(int[] arr,int i,int j){
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;
	}

	public static int getMedian(int[] arr,int begin,int end){
		insertSort(arr,begin,end);
		int sum=end+begin;
		int mid=(sum/2)+(sum%2);
		return arr[mid];
	}
	
	public static void insertSort(int[] arr,int begin,int end) {
		for(int i=begin+1;i<=end;i++){
			for(int j=i;j>begin&&arr[j]<arr[j-1];j--){
				swap(arr,j,j-1);
			}
		}
	}
	
	public static void insertSort(int[] arr){
		if(arr==null||arr.length<2){
			return;
		}

		int len=arr.length;
		for(int i=1;i<len;i++){
			for(int j=i;j>0&&arr[j]<arr[j-1];j--){
				swap(arr,j,j-1);
			}
		}
	}


	
	public static void main(String[] args){
		int[] arr={
			1,2,3,23,4,13,465,3,576,3,576,9
		};
		
		int[] copy=copyArray(arr);

		insertSort(arr);
		for(int x:arr){
			System.out.print(x+"	");
		}

		System.out.println("\n\n\n");

		for(int i=1;i<arr.length;i++){
			int[] res=getMinKNumsByBFPRT(copy,i);
			System.out.println(res[res.length-1]+"	"+arr[i-1]);
		}

		System.out.println("hello world");
	}
}





