package class00;
public class BubbleSort{
	public static void bubbleSort(int[] arr){
		int len=arr.length;
		if(arr==null||len<2){
			return;
		}
		
		/*
		for(int i=0;i<len;i++){
			for(int j=0;j<len-1-i;j++){
				if(arr[j]<arr[j+1]){
					swap(arr,j,j+1);
				}
			}
		}
		*/

		for(int end=len-1;end>0;end--){
			boolean flag=true;
			for(itn i=0;i<end;i++){
				if(arr[i]>arr[i+1]){
					flag=false;
					swap(arr,i,i+1);
				}
			}
			if(flag)
				return;
		}
	}
	
	//交换arr的i和j位置上的值
	public static void swap(int[] arr,int i,int j){
		arr[i]=arr[i]^arr[j];
		arr[j]=arr[i]^arr[j];
		arr[i]=arr[i]^arr[j];
		//异或运算可以理解为：无进位相加
		//异或运算性质：0和任何数相加都是
	}
	
	//for test
	public static void comparator(int[] arr){
		Arrays.sort(arr);
	}

	public static int[] generateRandomArray(int maxSize,int maxValue){
		int len=(int)((maxSize+1)*Math.random());
		int[] arr=new int[len];
		for(int i=0;i<len;i++){
			arr[i]=(int)((maxValue+1)*Math.random());
		}
		return arr;
	}

	//要求：time complexoty O(N)   space complexity O(1)

	//与异或相关的题目，在一个数组中，一种数出现奇数次，其他数出现偶数次，找出这个数
	public static int findNumber1(int[] arr){
		int len=arr.length;
		int num=0;
		if(len>0){
			num=arr[0];
			for(int i=1;i<len;i++){
				num^=arr[i];
			}
		}
		return num;
	}

	//在这个数组中，如果有两种数出现了奇数次，其他所有数出现了偶数次，	找出这两种数
	//这种写法是错误的，因为a^b的值可能是数组中出现偶数次的值，所以不对
	public static int[] findNumber2(int[] arr){
		//第一次循环获得a^b
		int len=arr.length();
		int[] tmp=new int[2];
		if(len>1){
			int tmp1=arr[0];
			for(int i=1;i<len;i++){
				tmp1^=arr[i];
			}

			int save=tmp1;
			for(int i=0;i<len;i++){
				tmp1^=arr[i];
				if(tmp1==0){
					break;
				}
			}
			tmp[0]=tmp1;
			tmp[1]=save^tmp1;
		}
		return tmp;
	}

	//左程云版本
	//一种数出现奇数次，其他数都出现偶数次
	public static void printOddTimesNum1(int[] arr){
		int eor=0;
		for(int cur:arr){
			eor^=arr;
		}
		System.out.println(eor);
	}

	//两种数出现奇数次，其他都出现偶数次
	public static void printOddTimesNum2(int[] arr){
		int eor=0;
		int onlyOne=0;
		for(int curNum:arr){
			eor^=curNum;
		}

		//eor=a^b;
		//eor!=0
		//eor必然有一个位置上是1
		int rightOne=eor&(~eor+1);//提取出最右的1
		for(int cur:arr){
			//if((cur&rightOne)==1)，这样也可以
			if((cur & rihgtOne)==0){//根据最右侧的1将数组分类，之后只有那一位为0，才将其与 eor‘ 异或，得到a or b
				onlyOne^=cur;
			}
		}
		System.out.println(onlyOne+"	"+(eor ^ onlyOne));
	}



}
