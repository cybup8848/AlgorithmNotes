/*************************************************************************
    > File Name: question2.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May  6 14:39:23 2024
 ************************************************************************/
public class question2{
	
	//小虎买苹果，只有6个每袋，8个每袋
	public static int getMinBags(int[] arr,int n){
		return process(arr,0,n);
	}
	
	public static int process(int[] arr,int index,int rest){
		if(rest<0||index==arr.length&&rest>0){
			return -1;
		}
		if(rest==0){
			return 0;
		}

		int min=-1;
		for(int i=0;i*arr[index]<=rest;i++){
			int cn=process(arr,index+1,rest-i*arr[index]);
			if(cn==-1){
				continue;
			}
			if(min==-1){
				min=cn+i;
				continue;
			}
			min=Math.min(min,cn+i);
		}
		return min;
	}

	//动态规划
	public static int getMinBags1(int[] arr,int n){
		if(arr==null){
			return -1;
		}
		
		int len=arr.length;
		int[][] dp=new int[len+1][n+1];
		dp[len][0]=0;
		for(int i=1;i<=n;i++){
			dp[len][i]=-1;
		}

		for(int i=len-1;i>=0;i--){
			for(int j=0;j<=n;j++){
				dp[i][j]=-1;
				for(int k=0;k*arr[i]<=j;k++){
					if(j-k*arr[i]>=0){
						if(dp[i+1][j-k*arr[i]]==-1){
							continue;
						}
						if(dp[i][j]==-1){
							dp[i][j]=k+dp[i+1][j-k*arr[i]];
							continue;
						}
						dp[i][j]=Math.min(dp[i][j],k+dp[i+1][j-k*arr[i]]);
					}
				}
			}
		}
		return dp[0][n];
	}
	
	//分为奇数、偶数
	public static int getMinBags2(int[] arr,int n){
		return process2(arr,0,n);
	}
	
	public static int process2(int[] arr,int index,int rest){
		if(rest<0||index==arr.length&&rest>0||rest%2!=0){//rest为奇数时，不可能实现
			return -1;
		}

		if(rest==0){
			return 0;
		}
		
		int min=-1;
		int cn=0;
		for(int i=0;i*arr[index]<=rest;i++){
			cn=process2(arr,index+1,rest-i*arr[index]);
			if(cn!=-1){
				if(min==-1){
					min=cn+i;
				} else{
					min=Math.min(min,cn+i);
				}
			}
		}
		return min;
	}	


	//当剩余的苹果数量大于24时，不需要再试，肯定搞不定，因为6和8的最小公倍数是24
	//左程云实现
	public static int minBags(int apple){
		if(apple<0){
			return -1;
		}
		int bag6=-1;
		int bag8=apple/8;
		int rest=apple-8*bag8;
		
		//rest很快就能超过24
		while(bag8>=0&&rest<24){//rest是留给6使用的，rest>=24说明之前8已经试验过了；24是6和8的最小公倍数，有限使用8来搞定
			int restUse6=minBagBase6(rest);
			if(restUse6!=-1){//也就是尽可能用8的袋子，因为8能装的苹果多；所以如果遇见能够装满的，直接返回
				bag6=restUse6;
				break;
			}
			rest=apple-7*(--bag8);
		}
		return bag6==-1?-1:bag6+bag8;
	}

	//如果剩余苹果rest可以被装6个苹果的袋子搞定，返回袋子数量
	//不能搞定返回-1
	public static int minBagBase6(int rest){
		return rest%6==0?rest/6:-1;
	}

	//最优解
	public static int minBagAwesome(int apple){
		if((apple&1)!=0){//如果是奇数，返回-1
			return -1;
		}

		if(apple<18){
			return apple==0?0:(apple==6||apple==8)?1:(apple==12||apple==14||apple==16)?2:-1;
		}
		
		return (apple-18)/8+3;
	}

	//汉诺塔问题
	private static int count=0;

	public static int hanoi(int n){
		processHanoi(n,'l','m','r');
		return count;
	}

	private static void move(char x,char y){
		System.out.println(x+" -> "+y);
	}
	public static void processHanoi(int n,char left,char middle,char right){
		if(n==1){
			++count;
			move(left,right);
			return;
		}
		processHanoi(n-1,left,right,middle);//将A座上的n-1个盘子借助C座移向B座
		++count;
		move(left,right);//将A座上最后一个盘子移向C座
		processHanoi(n-1,middle,left,right);//将B座上的n-1个盘子借助A座移向C座
	}

	//绝顶聪明问题，假设先手先拿
	
	//先手赢返回1，后手赢返回0
	public static int smartWinner(int[] arr){
		if(arr==null){
			return 0;
		}
		int len=arr.length;
		if(len==1){
			return 1;
		}

		int ft=first(arr,0,len-1);
		int sd=second(arr,0,len-1);
		return ft>sd?1:ft==sd?0:-1;
	}

	public static int first(int[] arr,int start,int end){
		if(start==end){
			return arr[start];
		}
		return Math.max(arr[start]+second(arr,start+1,end),arr[end]+second(arr,start,end-1));
	}

	public static int second(int[] arr,int start,int end){
		if(start==end){
			return 0;
		}
		return Math.min(first(arr,start+1,end),first(arr,start,end-1));
	}

	public static void judgeCom(int[] arr){
		int res=smartWinner(arr);
		switch(res){
			case 1:
				System.out.println("先手赢");
				break;
			case 0:
				System.out.println("平局");
				break;
			case -1:
				System.out.println("后手赢");
				break;
			default:
				;
		}
	}	
	

	public static void main(String[] args){
		int[] arr={
			6,8
		};
		int n=12;
		System.out.println(getMinBags(arr,n));
		System.out.println(getMinBags1(arr,n));
		System.out.println(getMinBags2(arr,n));
		System.out.println(minBags(n));
		System.out.println(minBagAwesome(n));

		n=24;
		System.out.println(getMinBags(arr,n));
		System.out.println(getMinBags1(arr,n));
		System.out.println(getMinBags2(arr,n));
		System.out.println(minBags(n));
		System.out.println(minBagAwesome(n));
		
		n=13;
		System.out.println(getMinBags(arr,n));
		System.out.println(getMinBags1(arr,n));
		System.out.println(getMinBags2(arr,n));
		System.out.println(minBags(n));
		System.out.println(minBagAwesome(n));
		
		System.out.println(hanoi(5));
		//System.out.println(hanoi(10));
		//System.out.println(hanoi(15));
		//System.out.println(hanoi(20));
	
		System.out.println("\n\n\n");
		int[] arr1={
			100,2,4,6,50,234,45,23
		};
		judgeCom(arr1);

		int[] arr2={
			100,1,2,3
		};
		judgeCom(arr2);
		
		int[] arr3={
			1,100,2,3
		};
		judgeCom(arr3);

		int[] arr4={
			1,100,3
		};
		judgeCom(arr4);


		System.out.println("hello world");
	}
}
