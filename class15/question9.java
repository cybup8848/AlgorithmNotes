/*************************************************************************
    > File Name: question9.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May  8 10:17:20 2024
 ************************************************************************/

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Arrays;

public class question9{

	//自己实现
	public static int maxMagic(HashSet<Integer> set1,HashSet<Integer> set2){
		if(set1==null||set2==null){
			return 0;
		}
		
		int sum1=getSum(set1);
		int cn1=set1.size();

		int sum2=getSum(set2);
		int cn2=set2.size();

		double ave1=sum1/(cn1+0.0);
		double ave2=sum2/(cn2+0.0);
		if(ave1==ave2){
			return 0;
		}
		
		//sum1、cn1、ave1是平均值最大的
		HashSet<Integer> max=new HashSet<Integer>();
		for(int x:set1){
			max.add(x);
		}
		HashSet<Integer> min=new HashSet<>();
		for(int x:set2){
			min.add(x);
		}
		if(ave1<ave2){
			int tmp=sum1;
			sum1=sum2;
			sum2=tmp;

			tmp=cn1;
			cn1=cn2;
			cn2=tmp;
			
			max=new HashSet<>();
			for(int x:set2){
				max.add(x);
			}
			min=new HashSet<>();
			for(int x:set1){
				min.add(x);
			}

			double tmp1=ave1;
			ave1=ave2;
			ave2=tmp1;
		}

		PriorityQueue<Integer> priorityQueue=new PriorityQueue<>();
		for(int x:max){
			if(x>ave2&&x<ave1){
				priorityQueue.add(x);
			}
		}

		int res=0;
		int top=0;
		while(!priorityQueue.isEmpty()){
			top=priorityQueue.poll();
			if(min.contains(top)){
				max.remove(top);
				continue;
			}

			++res;

			sum1-=top;
			cn1-=1;

			sum2+=top;
			cn2+=1;
			
			max.remove(top);
			min.add(top);
			
			ave1=sum1/(cn1+0.0);
			ave2=sum2/(cn2+0.0);

			for(int x:max){
				if(x>ave2&&x<ave1){
					priorityQueue.add(x);
				}
			}
		}

		return res;
	}

	private static int getSum(Set<Integer> set){
		if(set==null){
			return 0;
		}
		int res=0;
		for(int x:set){
			res+=x;
		}
		return res;
	}

	//左程云实现
	//请保证arr1无重复值，arr2种无重复值，且arr1和arr2肯定有数字
	public static int maxOps(int[] arr1,int[] arr2){
		double sum1=0.0;
		for(int i=0;i<arr1.length;i++){
			sum1+=(double)arr1[i];
		}
		
		double sum2=0.0;
		for(int i=0;i<arr2.length;i++){
			sum2+=(double)arr2[i];
		}

		if(avg(sum1,arr1.length)==avg(sum2,arr2.length)){
			return 0;
		}

		//平均值不相等
		int[] arrMore=null;
		int[] arrLess=null;
		double sumMore=0.0;
		double sumLess=0.0;
		if(avg(sum1,arr1.length)>avg(sum2,arr2.length)){
			arrMore=arr1;
			sumMore=sum1;
			arrLess=arr2;
			sumLess=sum2;
		} else {
			arrMore=arr2;
			sumMore=sum2;
			arrLess=arr1;
			sumLess=sum1;
		}

		Arrays.sort(arrMore);
		HashSet<Integer> setLess=new HashSet<>();
		for(int num:arrLess){
			setLess.add(num);
		}
		
		int moreSize=arrMore.length;// 平均值大的集合还剩几个数
		int lessSize=arrLess.length;// 平均值小的集合还剩几个数
		int ops=0;
		for(int i=0;i<arrMore.length;i++){//小-->大，排完序后，可以找到候选数据的左右边界，确实高明，代码很简洁
			double cur=(double)arrMore[i];
			if(cur<avg(sumMore,moreSize)&&cur>avg(sumLess,lessSize)&&!setLess.contains(arrMore[i])){
				
				sumMore-=cur;
				moreSize--;

				sumLess+=cur;
				lessSize++;
				setLess.add(arrMore[i]);
				ops++;
			}
		}
		return ops;
	}

	public static double avg(double sum,int size){
		return sum/(double)(size);
	}



	private static double getAve(Set<Integer> set){
		if(set==null){
			return  0.0;
		}
		double res=0.0;
		int size=set.size();
		for(int x:set){
			res+=x;
		}
		return res/size;
	}

	public static void main(String[] args){
		
		int[] arr1={
			1,2,5
		};
		int[] arr2={
			2,3,4,5,6
		};

		System.out.println(maxOps(arr1,arr2));


		HashSet<Integer> set1=new HashSet<Integer>(Arrays.asList(1,2,5));
		HashSet<Integer> set2=new HashSet<Integer>(Arrays.asList(2,3,4,5,6));
		System.out.println(maxMagic(set1,set2));

		System.out.println(5/(2+0.0));

		System.out.println("hello world");
	}
}
