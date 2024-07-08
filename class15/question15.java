/*************************************************************************
    > File Name: question15.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May  9 10:58:24 2024
 ************************************************************************/

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class question15{
	
	//从左下角
	public static boolean isExist1(int[][] arr,int aim){
		if(arr==null){
			return false;
		}

		int row=arr.length;
		int col=arr[0].length;

		int x=row-1;
		int y=0;
		while(x>=0&&y<col){
			if(arr[x][y]==aim){
				return true;
			} else if(arr[x][y]<aim){
				++y;
			} else {
				--x;
			}
		}
		return false;
	}
	
	//从右上角
	public static boolean isExist2(int[][] arr,int aim){
		if(arr==null){
			return false;
		}

		int row=arr.length;
		int col=arr[0].length;
		
		int x=0;
		int y=col-1;
		while(x<row&&y>=0){
			if(arr[x][y]==aim){
				return true;
			} else if(arr[x][y]<aim){
				++x;
			} else {
				--y;
			}
		}
		return false;
	}
	
	
	public static LinkedList<Integer> getMostOneRow(int[][] arr){
		if(arr==null){
			return null;
		}

		LinkedList<Integer>res=new LinkedList<>();
		int row=arr.length;
		int col=arr[0].length;
		
		int x=0;
		int y=col-1;

		int max=0;
		while(x<row){
			if(arr[x][y]==1){
				if(y==0||y-1>=0&&arr[x][y-1]==0){
					int tmp=col-y;
					if(tmp>=max){
						if(tmp>max){
							res.clear();
						}
						max=tmp;
						res.add(x);
					}
					++x;
				} else {
					--y;
				}
			}else{
				++x;
			}
		}
		
		return res;
	}




	public static void main(String[] args){
		int[][] arr={
			{
				1,2,3
			},
			{
				4,5,6
			}
		};

		int aim=4;
		System.out.println(isExist1(arr,aim));
		System.out.println(isExist2(arr,aim));
		
		aim=434;
		System.out.println(isExist1(arr,aim));
		System.out.println(isExist2(arr,aim));

		
		int[][] arr1={
			{
				0,0,0,0,0,1,1,1,1
			},
			{
				0,0,0,0,0,0,0,1,1
			},
			{
				0,0,0,0,1,1,1,1,1
			},
			{	
				0,0,0,0,1,1,1,1,1
			},
			{
				0,0,0,0,0,0,0,0,0
			},
			{
				0,1,1,1,1,1,1,1,1
			}
		};
		List<Integer> list=getMostOneRow(arr1);
		for(int x:list){
			System.out.print(x+"	");
		}
		System.out.println("\n\n\n");
	
		int[][] arr2={
			{
				0,0,0,0,0,1,1,1,1
			},
			{
				0,0,0,0,0,0,0,1,1
			},
			{
				0,0,0,0,1,1,1,1,1
			},
			{	
				0,0,0,0,1,1,1,1,1
			},
			{
				0,0,0,0,0,0,0,0,0
			}
		};
		list=getMostOneRow(arr2);
		for(int x:list){
			System.out.print(x+"	");
		}
		System.out.println("\n\n\n");
	
		System.out.println("hello world");
	}
}






