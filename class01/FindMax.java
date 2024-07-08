/*************************************************************************
    > File Name: FindMax.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun Jan 14 13:24:32 2024
 ************************************************************************/
package class01;
public class FindMax{
	//arr[left...right]范围上求最大值
	public static int findMax(int[] arr,int left,int right){//left、right代表递归状态
		if(left==right){
			return arr[left]; 
		}
		//int mid=(left+right)/2;//如果长度太大，会溢出；这种写法不是无懈可击
		int mid=left+(right-left)>>1;
		int leftMax=findMax(arr,left,mid); 
		int rightMax=findMax(arr,mid+1,right);
		//return leftMax>rightMax?leftMax:rightMax;
		return Math.max(leftMax,rightMax);
	}

	



	public static void main(String[] args){
		int[] arr={1,2,23,57,23,78,12,100};
		System.out.println(findMax(arr,0,arr.length-1));
		System.out.println("Hello World");
	}
}






