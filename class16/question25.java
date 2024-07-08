/*************************************************************************
    > File Name: question25.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jun 27 20:45:24 2024
 ************************************************************************/

import java.util.LinkedList;

public class question25{
	

	public static int[] change1(int[] arr){
		if(arr==null||arr.length%2!=0){
			return arr;
		}

		int len=arr.length;
		int index1=0;
		int len1=len/2;
		
		int[] tmp=new int[len];
		int index=0;
		for(int i=0;i<len1;i++){
			tmp[index++]=arr[i+len1];
			tmp[index++]=arr[i];
		}

		return tmp;
	}


	//时间复杂度：O(N)
	//空间复杂度：O(1)
	
	//自己实现
	public static void change2(int[] arr){
		if(arr==null||arr.length%2!=0){
			return;
		}

		process2(arr,0,arr.length-1);
	}

	public static void process2(int[] arr,int s,int e){//[s,e]
		if(s>=e){
			return;
		}

		int len=e-s+1;

		LinkedList<Integer> list=nearestN(len);
		int lastK=list.getLast();
		int changeLen=(int)(Math.pow(3,lastK))-1;
		int half=changeLen/2;
		swapWhole(arr,s+half,s+len/2-1,s+len/2,s+len/2+half-1);
		indexCycleSwap(arr,s,s+changeLen-1,list);
		process2(arr,s+changeLen,e);
	}

	public static void indexCycleSwap(int[] arr,int s,int e,LinkedList<Integer> list){
		for(Integer x:list){
			int index=(int)(Math.pow(3,x-1))+s-1;
			int base=index;

			int tmpVal=arr[index];
			do{
				int changePos=posAfterChange(index,s,e);
				int tmp=arr[changePos];
				arr[changePos]=tmpVal;
				tmpVal=tmp;
				index=changePos;
			}while(index!=base);
		}
	}

	public static LinkedList<Integer> nearestN(int n){// 返回值=3^k-1,    k=1,2,3,......
		int k=1;
		int cur=3;
		LinkedList<Integer> list=new LinkedList<>();
		
		while(cur-1<=n){
			list.add(k);
			++k;
			cur*=3;
		}

		return list;
	}

	public static int posAfterChange(int index,int start,int end){
		int len=end-start+1;
		int half=len/2;
		int res=0;
		if((index-start+1)<=half){
			res=2*(index-start+1)-1;
		} else {
			res=2*(index-start+1-half)-1-1;
		}

		return res+start;
	}

	public static void swapWhole(int[] arr,int s1,int e1,int s2,int e2){
		reverse(arr,s1,e1);
		reverse(arr,s2,e2);
		reverse(arr,s1,e2);
	}

	public static void reverse(int[] arr,int start,int end){
		while(start<end){
			swap(arr,start,end);
			++start;
			--end;
		}
	}

	public static void swap(int[] arr,int i,int j){
		if(i==j){
			return;
		}

		arr[i]=arr[i]^arr[j];
		arr[j]=arr[i]^arr[j];
		arr[i]=arr[i]^arr[j];
	}



	//左程云实现
	//数组的长度为len，调整前的位置是i，返回调整之后的位置
	//下标不从0开始，从1开始
	public static int modifyIndex1(int i,int len){
		if(i<=len/2){
			return 2*i;
		} else {
			return 2*(i-len/2)-1;
		}
	}
	
	//数组的长度为len，调整前的位置为i，返回调整之后的位置
	//下标不从0开始，从1开始
	public static int modifyIndex2(int i,int len){
		return (2*i)%(len+1);
	}
	
	//主函数
	//数组必须不为空，且长度为偶数
	public static void shuffle(int[] arr){
		if(arr==null||arr.length==0||(arr.length&1)!=0){
			return;
		}
		shuffle(arr,0,arr.length-1);
	}

	//在arr[L...R]上做完美洗牌的调整
	public static void shuffle(int[] arr,int L,int R){
		while(R-L+1>0){//切成一块一块的解决，每一块的长度满足(3^k-1)
			int len=R-L+1;
			int base=3;
			int k=1;

			//计算小于等于len，并且是离len最近的，满足(3^k-1)的数
			//也就是找到最大的k，满足 3^k<=len+1
			while(base<=(len+1)/3){
				base*=3;
				k++;
			}

			//当前要解决长度为base-1的块，一半就是再除2
			int half=(base-1)/2;

			//[L...R] 的中点位置
			int mid=(L+R)/2;

			//要旋转的左部分为[L+half...mid]，右部分为 arr[mid+1..mid+half]
			//注意在这里，arr下标是从0开始的
			rotate(arr,L+half,mid,mid+half);

			//旋转完成后，从L开始算起，长度为base-1的部分进行下标连续推
			cycles(arr,L,base-1,k);

			//解决了前base-1部分，剩下的部分继续处理
			L=L+base-1;
		}
	}
	
	//从start位置开始，往右len的长度这一段，做下标连续推
	//出发位置依次是1，3，9
	public static void cycles(int[] arr,int start,int len,int k){
		//找到每一个出发位置trigger，一共k个
		//每一个trigger都进行下标连续推
		//出发位置是从1开始算的，而数组下标是从0开始算的
		for(int i=0,trigger=1;i<k;i++,trigger*=3){
			int preValue=arr[trigger+start-1];
			int cur=modifyIndex2(trigger,len);
			while(cur!=trigger){
				int tmp=arr[cur+start-1];
				arr[cur+start-1]=preValue;
				preValue=tmp;
				cur=modifyIndex2(cur,len);
			}

			arr[cur+start-1]=preValue;
		}
	}

	//[L...M]为左部分，[M+1...R]为右部分，左右两部分互换
	public static void rotate(int[] arr,int L,int M,int R){
		reverse(arr,L,M);
		reverse(arr,M+1,R);
		reverse(arr,L,R);
	}
	
	
	public static void printArray(int[] arr){
		int len=arr.length;
		for(int i=0;i<len;i++){
			System.out.print(arr[i]+"	");
		}
		System.out.print("\n");
	}

	public static int[] copyArray(int[] arr){
		if(arr==null){
			return null;
		}
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=arr[i];
		}
		return res;
	}

	//附加题,无序数组，构建特定规则数组
	public static void heapInsert(int[] arr,int index){//向上调整，构建小顶堆
		int child=index;
		int parent=(child-1)/2;
		while(arr[child]<arr[parent]){
			swap(arr,child,parent);
			child=parent;
			parent=(child-1)/2;
		}
	}

	//堆化，向下调整,构建小顶堆
	public static void heapify(int[] arr,int index,int len){
		int parent=index;
		int left=2*parent+1;
		while(left<len){
			int right=left+1<len?left+1:left;
			int smallest=arr[left]<=arr[right]?left:right;
			smallest=arr[parent]<=arr[smallest]?parent:smallest;
			if(smallest==parent){
				break;
			}
			swap(arr,parent,smallest);
			parent=smallest;
			left=2*parent+1;
		}
	}

	public static void heapifyWhole(int[] arr){
		if(arr==null||arr.length<2){
			return;
		}

		int len=arr.length;
		for(int i=len/2;i>=0;i--){
			heapify(arr,i,len);
		}
	}

	public static void heapSort(int[] arr){
		if(arr==null||arr.length<2){
			return;
		}

		int len=arr.length;
		heapifyWhole(arr);

		//堆化，构成小顶堆
		/*
		for(int i=1;i<len;i++){
			heapInsert(arr,i);
		}
		*/
		
		swap(arr,0,--len);
		while(len>1){
			heapify(arr,0,len);
			swap(arr,0,--len);
		}
	}

	public static void specialSort(int[] arr){
		if(arr==null||arr.length<2){
			return;
		}
		
		int len=arr.length;
		heapSort(arr);
		reverse(arr,0,len-1);
		
		if((len&1)!=0){//奇数
			shuffle(arr,1,len-1);	
		} else {//偶数
			shuffle(arr,0,len-1);
			for(int i=0;i<len;i+=2){
				swap(arr,i,i+1);
			}
		}

	}


	

	public static void main(String[] args){
		int[] arr={
			1,2,3,4
		};
		int[] copy=copyArray(arr);
		int[] copy1=copyArray(arr);

		int[] res=change1(arr);
		printArray(res);

		change2(copy);
		printArray(copy);

		shuffle(copy1);
		printArray(copy1);

		System.out.println("\n\n\n");

		arr=new int[]{
			1,2,3,4,56,5,7,345,345,75,456,67,24,24,24,2543
		};
		copy=copyArray(arr);
		copy1=copyArray(arr);
		res=change1(arr);
		printArray(res);
		change2(copy);
		printArray(copy);
		shuffle(copy1);
		printArray(copy1);
		
		System.out.println("\n\n\n");
		
		arr=new int[]{
			1,324,46,23,76,23,65,23,5877,9,242,76
		};
		printArray(arr);
		specialSort(arr);
		printArray(arr);

		heapSort(arr);
		printArray(arr);
		

		System.out.println("hello world");
	}
}









