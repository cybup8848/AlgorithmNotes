/*************************************************************************
    > File Name: question19.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 10 16:40:10 2024
 ************************************************************************/

public class question19{

	//自己实现
	public static void revolve90(int[][] arr){
		int tR=0;
		int tC=0;
		
		int dR=arr.length-1;
		int dC=arr[0].length-1;

		while(tR<=dR&&tC<=dC){
			revolve(arr,tR++,tC++,dR--,dC--);
		}
	}

	public static void revolve(int[][] matrix,int a,int b,int c,int d){
		//第一次交换
		
		int x=c-1;
		int y=d-1;
		while(x!=a){
			swap(matrix,x,d,a,y);

			swap(matrix,c,y,x,b);
			--x;
			--y;
		}

		y=b+1;
		int y1=d-1;
		while(y!=d){
			swap(matrix,a,y1,c,y);
			y++;
			--y1;
		}

		//转换四角
		swap(matrix,a,b,a,d);
		swap(matrix,c,b,c,d);
		swap(matrix,a,b,c,d);
		
	}

	public static void swap(int[][] matrix,int a,int b,int c,int d){
		int tmp=matrix[a][b];
		matrix[a][b]=matrix[c][d];
		matrix[c][d]=tmp;
	}

	public static void revolve90_1(int[][] arr){
		int tR=0;
		int tC=0;

		int dR=arr.length-1;
		int dC=arr[0].length-1;

		while(tR<=dR&&tC<=dC){
			revolve_1(arr,tR++,tC++,dR--,dC--);
		}
	}

	public static void revolve_1(int[][] arr,int a,int b,int c,int d){
		int len=d-b;
		for(int i=0;i<len;i++){
			swap(arr,a,b+i,c-i,b);
			swap(arr,a+i,d,c,d-i);
			swap(arr,c-i,b,a+i,d);
		}
	}

	//左程云实现
	public static void rotate(int[][] matrix){
		int tR=0;
		int tC=0;
		int dR=matrix.length-1;
		int dC=matrix[0].length-1;

		while(tR<dR){
			rotateEdge(matrix,tR++,tC++,dR--,dC--);
		}
	}

	public static void rotateEdge(int[][] arr,int a,int b,int c,int d){
		int tmp=0;
		int times=d-b;
		for(int i=0;i<times;i++){
			tmp=arr[a][b+i];
			arr[a][b+i]=arr[c-i][b];
			arr[c-i][d]=arr[c][d-i];
			arr[c][d-i]=arr[a+i][d];
			arr[a+i][d]=tmp;
		}
	}


	public static void printMatrix(int[][] arr){
		if(arr==null){
			return;
		}

		for(int[] x:arr){
			for(int y:x){
				System.out.print(y+"	");
			}
			System.out.println();
		}
	}

	public static void main(String[] args){
		int[][] matrix={
			{
				0,1,2,3
			},
			{
				4,5,6,7
			},
			{
				8,9,10,11
			},
			{
				12,13,14,15
			}
		};
		printMatrix(matrix);
		System.out.println("\n\n\n");
		revolve90(matrix);
		printMatrix(matrix);
		System.out.println("\n\n\n");

		revolve90_1(matrix);
		printMatrix(matrix);
		System.out.println("\n\n\n");	

		rotate(matrix);
		printMatrix(matrix);
		System.out.println("\n\n\n");


		System.out.println("hello world");
	}
}




