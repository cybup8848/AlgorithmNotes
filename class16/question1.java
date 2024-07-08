/*************************************************************************
    > File Name: question1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 28 18:51:17 2024
 ************************************************************************/


public class question1{

	public static int getMaxDiff(int[] arr){
		if(arr==null||arr.length<=1){
			return 0;
		}

		int min=Integer.MAX_VALUE;
		int max=Integer.MIN_VALUE;
		int len=arr.length;
		for(int i=0;i<len;i++){
			max=Math.max(max,arr[i]);
			min=Math.min(min,arr[i]);
		}

		if(max==min){
			return 0;
		}

		int[] minArr=new int[len+1];
		int[] maxArr=new int[len+1];
		for(int i=0;i<len+1;i++){
			minArr[i]=Integer.MAX_VALUE;
			maxArr[i]=Integer.MIN_VALUE;
		}

		double step=(max-min)/(len+1+0.0);
		for(int x:arr){
			int index=(int)((x-min)/step);
			int bucketIndex=index==len+1?len:index;
			minArr[bucketIndex]=Math.min(minArr[bucketIndex],x);
			maxArr[bucketIndex]=Math.max(maxArr[bucketIndex],x);
		}
		

		int maxDiff=Integer.MIN_VALUE;
		int cur=maxArr[0];
		int nextIndex=1;
		while(nextIndex<=len){
			if(minArr[nextIndex]!=Integer.MAX_VALUE){
					maxDiff=Math.max(maxDiff,minArr[nextIndex]-cur);
					cur=maxArr[nextIndex];
			}
			++nextIndex;
		}
		
		
		return maxDiff;
	}

	//左程云实现
	public static int maxGap(int[] nums){
		if(nums==null||nums.length<2){
			return 0;
		}

		int len=nums.length;

		int min=Integer.MAX_VALUE;
		int max=Integer.MIN_VALUE;

		for(int i=0;i<len;i++){
			min=Math.min(min,nums[i]);
			max=Math.max(max,nums[i]);
		}

		if(min==max){
			return 0;
		}


		boolean[] hasNum=new boolean[len+1];//hasNum[i] i号桶是否进来过数字
		int[] maxs=new int[len+1];// maxs[i] i号桶收集的所有数字的最大值
		int[] mins=new int[len+1];// mins[i] i号桶收集的所有数字的最小值
		
		int bid=0;//桶号
		for(int i=0;i<len;i++){
			bid=bucket(nums[i],len,min,max);
			mins[bid]=hasNum[bid]?Math.min(mins[bid],nums[i]):nums[i];
			maxs[bid]=hasNum[bid]?Math.max(maxs[bid],nums[i]):nums[i];
			hasNum[bid]=true;
		}
		
		int res=0;
		int lastMax=maxs[0];//上一个非空桶的最大值
		int i=1;
		for(;i<=len;++i){
			if(hasNum[i]){
				res=Math.max(res,mins[i]-lastMax);
				lastMax=maxs[i];
			}
		}
		
		System.out.println("第一个桶："+maxs[0]+"	"+mins[0]);
		System.out.println("最后一个桶数据："+maxs[len]+"	"+mins[len]);
		return res;
	}

	//按照这种方法：最后一个桶：最小值和最大值都是max
	public static int bucket(long num,long len,long min,long max){
		return (int)((num-min)*len/(max-min));
	}


	
	public static void main(String[] args){

		int[] arr={
			9,0,17,4,63,72,65,67,99
		};

		System.out.println(getMaxDiff(arr));
		System.out.println(maxGap(arr));	


		System.out.println("hello world");
	}

}




