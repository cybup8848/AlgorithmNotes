/*************************************************************************
    > File Name: question17.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 10 14:02:08 2024
 ************************************************************************/


public class question17{

	//按照zig-zag的方式打印矩阵
	public static void printByZigZag(int[][] arr){
		if(arr==null){
			return;
		}

		int row=arr.length;
		int col=arr[0].length;
		int x=0;
		int y=0;

		boolean up=true;
		while(x<row&&y<col){
			System.out.print(arr[x][y]+"	");
			if(up){
				if(x==0&&y+1<col){
					y+=1;
					up=false;
				} else if(y==col-1){
					x+=1;
					up=false;
				} else {
					x-=1;
					y+=1;
				}
			} else {
				if(y==0&&x+1<row){
					x+=1;
					up=true;
				} else if(x==row-1){
					y+=1;
					up=true;
				} else {
					x+=1;
					y-=1;
				}
			}
		}
	}

	//网络实现
	public static void printZigZag(int[][] arr){
		if(arr==null){
			return;
		}

		int row=arr.length;
		int col=arr[0].length;
		int cn=row*col;

		int i=0;
		int j=0;

		for(int k=0;k<cn;k++){
			System.out.print(arr[i][j]+"	");
			
			//根据奇偶决定向上、向下移动
			if((i+j)%2==0){//向上移动
				if(j==col-1){
					i++;
				} else if(i==0){
					j++;
				} else {
					i--;
					j++;
				}
			} else {//向下移动
				if(i==row-1){
					j++;
				} else if(j==0){
					i++;
				} else {
					i++;
					j--;
				}
			}
		}
	}
	//在上面的程序中，我们首先定义了一个grade型向量matrix，它保存了我们要打印的矩阵。我们用两个循环变量i和j来记录当前的位置。
	//然后我们使用for循环来遍历所有元素。我们首先打印出矩阵中当前位置的元素，然后根据所在位置更新i和j。
	//如果i+j是偶数，那么我们向上或向下移动，并根据当前的位置决定我们是向左下角或向右上角移动。
	//如果i+j是奇数，我们向左或向右移动，并根据当前的位置决定我们是向上还是向下移动。


	//左程云实现
	public static void printZigZag1(int[][] arr){
		int row=arr.length;
		int col=arr[0].length;
		
		//a往右走，往下走
		int ax=0;
		int ay=0;

		//b往下走，往右走
		int bx=0;
		int by=0;

		boolean flag=true;//flag为true，从左下往右上打印；flag为false，从右上往左下打印
		while(ax<row&&by<col){
			if(flag){
				printLeftDownToRightUp(arr,bx,by,ax,ay);
			} else {
				printRightUpToLeftDown(arr,bx,by,ax,ay);
			}
			
			flag=!flag;
			
			if(ay+1<col){
				ay+=1;
			} else {
				ax+=1;
			}
			
			if(bx+1<row){
				bx+=1;
			} else {
				by+=1;
			}
		}

	}

	private static void printLeftDownToRightUp(int[][] arr,int a,int b,int c,int d){
		int times=d-b+1;
		for(int i=0;i<times;i++){
			System.out.print(arr[a--][b++]+"	");
		}
	}

	private static void printRightUpToLeftDown(int[][] arr,int a,int b,int c,int d){
		int times=d-b+1;
		for(int i=0;i<times;i++){
			System.out.print(arr[c++][d--]+"	");
		}
	}
	
	//左程云实现
	public static void printMatrixZigZag(int[][] matrix){
		int ar=0;
		int ac=0;
		int br=0;
		int bc=0;

		int endR=matrix.length-1;
		int endC=matrix[0].length-1;
		boolean fromUp=false;
		while(ar!=endR+1){
			printLevel(matrix,ar,ac,br,bc,fromUp);
			ar=ac==endC?ar+1:ar;
			ac=ac==endC?ac:ac+1;
			bc=br==endR?bc+1:bc;
			br=br==endR?br:br+1;
			fromUp=!fromUp;
		}
	}
	
	public static void printLevel(int[][] m,int tR,int tC,int dR,int dC,boolean f){
		if(f){
			while(tR!=dR+1){
				System.out.print(m[tR++][tC--]+"	");
			}
		} else {
			while(dR!=tR-1){
				System.out.print(m[dR--][dC++]+"	");
			}
		}
	}
	



	public static void main(String[] args){
		int[][] arr={
			{
				1,2,3,4
			},
			{
				5,6,7,8
			},
			{	
				9,10,11,12
			},
			{
				13,14,15,16
			}
		};
		printByZigZag(arr);
		System.out.println();
		printZigZag(arr);
		System.out.println();
		printZigZag1(arr);
		System.out.println();
		printMatrixZigZag(arr);

		System.out.println("\n\n\n");
		
		int[][] arr1={
			{
				1,2,3,4,5
			},
			{
				6,7,8,9,10
			}
		};
		printByZigZag(arr1);
		System.out.println();
		printZigZag(arr1);
		System.out.println();
		printZigZag1(arr1);
		System.out.println();
		printMatrixZigZag(arr1);
		System.out.println("\n\n\n");
		


		System.out.println("hello world");
	}
}

