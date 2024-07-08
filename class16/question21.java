/*************************************************************************
    > File Name: question21.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jun 21 12:18:24 2024
 ************************************************************************/





public class question21{

	public static int minK(int[] arr,int k){//k从1开始
		if(arr==null||arr.length==0||k<=0){
			return Integer.MIN_VALUE;
		}

		quickSort(arr,0,arr.length-1);
		return arr[k-1];
	}

	public static void quickSort(int[] arr,int start,int end){
		if(start>=end){
			return;
		}

		int base=arr[start];
		int index=start;
		for(int i=start+1;i<=end;i++){
			if(arr[i]<=base){
				index++;
				int tmp=arr[i];
				arr[i]=arr[index];
				arr[index]=tmp;
			}
		}

		int tmp=arr[index];
		arr[index]=base;
		arr[start]=tmp;

		quickSort(arr,start,index-1);
		quickSort(arr,index+1,end);
	}

	
	public static int minK1(int[] arr,int k){
		if(arr==null||arr.length==0||k<=0||arr.length<k){
			return Integer.MIN_VALUE;
		}
		
		return findMinK(arr,0,arr.length-1,k);
	}
	
	public static int findMinK(int[] arr,int start,int end,int k){
		
		int index=start;
		for(int i=start+1;i<=end;i++){
			if(arr[i]<=arr[start]){
				index++;
				int tmp=arr[index];
				arr[index]=arr[i];
				arr[i]=tmp;
			}
		}

		int tmp=arr[index];
		arr[index]=arr[start];
		arr[start]=tmp;
		
		int num=index-start+1;
		if(num==k){
			return arr[index];
		} else if(num<k){
			return findMinK(arr,index+1,end,k-num);
		} else {
			return findMinK(arr,start,index-1,k);
		}
	}



	public static int[] genArray(int maxVal,int size){
		int[] arr=new int[size];
		for(int i=0;i<size;i++){
			arr[i]=(int)(Math.random()*maxVal)-(int)(Math.random()*(maxVal+1));
		}
		
		return arr;
	}

	public static int[] copyArray(int[] arr){
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=arr[i];
		}
		return res;
	}
	
	public static void printArray(int[] arr){
		int len=arr.length;
		for(int i=0;i<len;i++){
			System.out.print(arr[i]+"	");
		}
		System.out.println();
	}


	public static void main(String[] args){
		int maxVal=100;
		int size=50;
		
		int[] arr=genArray(maxVal,size);
		printArray(arr);
		System.out.println("\n\n\n");
		quickSort(arr,0,arr.length-1);
		printArray(arr);
				
		arr=genArray(maxVal,size);
		int[] copy=copyArray(arr);
		for(int i=1;i<=size;i++){
			System.out.println(minK(arr,i)+"	"+minK1(copy,i));
		}

		System.out.println(minK(null,1));

		System.out.println("hello world");
	}
}
