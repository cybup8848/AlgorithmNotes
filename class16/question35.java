/*************************************************************************
    > File Name: question35.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jul  5 17:10:03 2024
 ************************************************************************/


public class question35{


	//自己实现，时间复杂度O(N^2)
	public static boolean[] isGood1(int[] canRun,int[] distance){
		if(canRun==null||distance==null||canRun.length!=distance.length){
			return null;
		}

		int len=canRun.length;
		boolean[] res=new boolean[len];
		for(int i=0;i<len;i++){
			res[i]=isBackOrigin1(canRun,distance,i);
		}

		return res;
	}

	public static boolean isBackOrigin1(int[] canRun,int[] distance,int index){
		int res=0;
		int cur=index;
		int len=distance.length;

		do{
			res+=canRun[cur]-distance[cur];
			if(res<0){
				return false;
			}
			cur=(cur+1)%len;
		}while(cur!=index);

		return true;
	}

	public static boolean[] isGood2(int[] canRun,int[] distance){
		if(canRun==null||distance==null||canRun.length!=distance.length){
			return null;
		}

		int len=canRun.length;
		
		int[] resDistance=new int[len];
		for(int i=0;i<len;i++){
			resDistance[i]=canRun[i]-distance[i];
		}
		
		boolean[] res=new boolean[len];
		for(int i=0;i<len;i++){
			res[i]=isBackOrigin2(resDistance,i);
		}

		return res;
	}

	public static boolean isBackOrigin2(int[] distance,int index){
		int len=distance.length;
		int cur=index;
		int res=0;
		do{
			res+=distance[cur];
			if(res<0){
				return false;
			}
			cur=(cur+1)%len;
		}while(cur!=index);
		
		return true;
	}

	
	//左程云实现
	public static boolean[] isGood3(int[] oil,int[] distance){
		if(oil==null||distance==null||oil.length!=distance.length){
			return null;
		}
	
		return process3(oil,distance);
	}

	public static boolean[] process3(int[] oil,int[] distance){
		int len=oil.length;
		
		int[] pure=new int[len];

		int start=-1;
		for(int i=0;i<len;i++){
			pure[i]=oil[i]-distance[i];
			if(pure[i]>=0){
				start=i;
			}
		}

		boolean[] res=new boolean[len];//默认全为false
		if(start==-1){
			return res;
		}


		
		int left=start;
		int right=(start+1)%len;
		int rest=pure[start];
		int need=0;
		while(left!=right){
			if(need+rest+pure[right]>=0){
				rest=need+rest+pure[right];
				need=0;
				right=(right+1)%len;
			} else {
				left=left==0?len:left;
				left-=1;
				need+=pure[left];
			}
		}

		if(need>=0){//说明left、right为良好出发点，检验能够接到left上
			res[left]=true;
			int index=left==0?len-1:left-1;
			need=0;
			while(index!=left){
				need+=pure[index];
				if(pure[index]>=0&&need>=0){
					need=0;
					res[index]=true;
				}
				index=(index==0?len-1:index-1);
			}
		}

		return res;
	}

	public static int[] genArray(int maxValue,int size){
		int[] res=new int[size];
		for(int i=0;i<size;i++){
			res[i]=(int)(Math.random()*maxValue);
		}

		return res;
	}

	public static int[] copyArray(int[] arr){
		int len=arr.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=arr[i];
		}

		return res;
	}

	public static boolean compareArray(boolean[] arr1,boolean[] arr2){
		if(arr1==null&&arr2==null){
			return true;
		}

		if(arr1==null||arr2==null||arr1.length!=arr2.length){
			return false;
		}
		int len=arr1.length;
		for(int i=0;i<len;i++){
			if(arr1[i]!=arr2[i]){
				return false;
			}
		}

		return true;
	}

	public static void printArray1(int[] arr){
		if(arr==null||arr.length==0){
			return;
		}

		int len=arr.length;
		for(int i=0;i<len;i++){
			System.out.print(arr[i]+"	");
		}
		System.out.println("\n");
	}
	
	public static void printArray2(boolean[] arr){
		if(arr==null||arr.length==0){
			return;
		}

		int len=arr.length;
		for(int i=0;i<len;i++){
			System.out.print(arr[i]+"	");
		}
		System.out.println("\n");
	}


	public static void main(String[] args){
		int[] canRun={
			3,2,1,4,2
		};

		int[] distance={
			4,1,3,2,1
		};

		boolean[] res=isGood1(canRun,distance);
		for(int i=0;i<res.length;i++){
			System.out.print(res[i]+"	");
		}
		System.out.println();

		res=isGood2(canRun,distance);
		for(int i=0;i<res.length;i++){
			System.out.print(res[i]+"	");
		}
		System.out.println();
			
		res=isGood3(canRun,distance);
		for(int i=0;i<res.length;i++){
			System.out.print(res[i]+"	");
		}
		System.out.println("\n\n\n");

		{
			boolean flag=true;
			int maxValue=100;
			int size=500000;
			for(int i=0;i<size;i++){
				int len=(int)(Math.random()*20);
				int[] oil1=genArray(maxValue,len);
				int[] dis1=genArray(maxValue,len);

				int[] oil2=copyArray(oil1);
				int[] dis2=copyArray(dis1);
				
				boolean[] res1=isGood1(oil1,dis1);
				boolean[] res2=isGood3(oil2,dis2);
				
				if(compareArray(res1,res2)==false){
					flag=false;
					break;
				}
			}
			
			System.out.println(flag?"right":"wrong");
		}


		System.out.println("hello world");
	}
}







