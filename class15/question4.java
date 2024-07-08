/*************************************************************************
    > File Name: question4.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May  7 14:05:10 2024
 ************************************************************************/

public class question4{

	//O(N^4)
	public static int maxAllOneBorder(int[][] arr){
		if(arr==null){
			return 0;
		}
		
		int max=0;
		
		int res=0;
		int len=arr.length;
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				res=process(arr,i,j);
				max=Math.max(res,max);
			}
		}

		return max;
	}
	
	public static int process(int[][] arr,int leftX,int leftY){
		int len=arr.length;
		
		
		int maxLength=Math.min(len-leftX,len-leftY);
		int max=0;
		for(int i=1;i<=maxLength;i++){
			if(checkAllOneBorder(arr,leftX,leftY,i)){
				max=Math.max(max,i);
			}
		}
		return max;
	}

	private static boolean checkAllOneBorder(int[][] arr,int leftX,int leftY,int len){
		int rightX=leftX+len-1;
		int rightY=leftY+len-1;

		//检查上边
		for(int i=leftY;i<=rightY;i++){
			if(arr[leftX][i]==0){
				return false;
			}
		}

		//检查下边
		for(int i=leftY;i<=rightY;i++){
			if(arr[rightX][i]==0){
				return false;
			}
		}
		
		//检查左边
		for(int i=leftX;i<=rightX;i++){
			if(arr[i][leftY]==0){
				return false;
			}
		}
		
		//检查右边
		for(int i=leftX;i<=rightX;i++){
			if(arr[i][rightY]==0){
				return false;
			}
		}

		return true;
	}
	
	
	//检测全是1的边界
	//时间复杂度O(N^3)
	public static int maxAllOneBorder1(int[][] arr){
		if(arr==null){
			return 0;
		}
		
		int max=0;
		int tmp=0;

		point[][] points=getMatrixInfo(arr);

		int len=arr.length;
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				tmp=getMaxOneLength(points,i,j);
				max=Math.max(tmp,max);
			}
		}
		return max;
	}

	public static int getMaxOneLength(point[][] points,int x,int y){
		int len=Math.min(points[x][y].xOneLength,points[x][y].yOneLength);
		int max=0;
		for(int i=1;i<=len;i++){
			if((i<=points[x][y+i-1].xOneLength)&&(i<=points[x+i-1][y].yOneLength)){
				max=i;
			}
		}
		return max;
	}

	//因为有很多检测，所以使用空间换取时间，生成辅助结构数组
	private static class point{
		public int xOneLength;
		public int yOneLength;
		public point(){
			xOneLength=0;
			yOneLength=0;
		}
	}

	public static point[][] getMatrixInfo(int[][] arr){
		int len=arr.length;
		point[][] points=new point[len][len];
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				points[i][j]=new point();
			}
		}
		
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				//计算横向1长度
				if(j==0){
					points[i][j].yOneLength=getRowMaxOneLength(arr,i,j);
				} else {
					int tmp=points[i][j-1].yOneLength;
					if(tmp>1){
						points[i][j].yOneLength=tmp-1;
					} else {
						points[i][j].yOneLength=getRowMaxOneLength(arr,i,j);
					}
				}

				//计算纵向1长度
				if(i==0){
					points[i][j].xOneLength=getColMaxOneLength(arr,i,j);
				} else {
					int tmp=points[i-1][j].xOneLength;
					if(tmp>1){
						points[i][j].xOneLength=tmp-1;
					} else {
						points[i][j].xOneLength=getColMaxOneLength(arr,i,j);
					}
				}
			}
		}
		
		return points;
	}
	
	//计算
	public static int getRowMaxOneLength(int[][] arr,int leftX,int leftY){
		int len=arr.length;
		int cn=0;
		for(int i=leftY;i<len;i++){
			if(arr[leftX][i]==0){
				break;
			}
			++cn;
		}
		return cn;
	}

	public static int getColMaxOneLength(int[][] arr,int leftX,int leftY){
		int len=arr.length;
		int cn=0;
		for(int i=leftX;i<len;i++){
			if(arr[i][leftY]==0){
				break;
			}
			++cn;
		}
		return cn;
	}	
	
	



	public static void main(String[] args){
		int[][] arr={
			{
				0,1,1,1,1
			},
			{
				0,1,0,0,1
			},
			{
				0,1,0,0,1
			},
			{
				0,1,1,1,1
			},
			{
				0,1,0,1,1
			}
		};
		System.out.println(maxAllOneBorder(arr));
		System.out.println(maxAllOneBorder1(arr));
		
		point[][] points=new point[2][2];
		System.out.println(points==null);
		System.out.println(points[0][0]==null);

		System.out.println("hello world");
	}
}



