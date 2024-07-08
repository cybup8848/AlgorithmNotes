/*************************************************************************
    > File Name: question9.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu Jun 13 11:46:52 2024
 ************************************************************************/


public class question9{

	//绝顶聪明问题
	public static void smart(int[] arr){
		if(arr==null||arr.length==0){
			throw new RuntimeException("arr is invalid!");
		}
		
		int len=arr.length;
		int f=first(arr,0,len-1);
		int s=second(arr,0,len-1);
		
		if(f>s){
			System.out.println("先手赢 ");
		} else if(f==s){
			System.out.println("平局");
		} else {
			System.out.println("后手赢");
		}

		System.out.println("先手："+f+"	"+"后手："+s);
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

	//KMP算法
	public static int[] getNext(char[] arr){
		if(arr==null||arr.length==0){
			return null;
		}

		int len=arr.length;
		int[] next=new int[len];
		next[0]=-1;
		if(len>1){
			next[1]=0;
		}
		int cn=0;
		int index=2;
		while(index<len){
			if(arr[index-1]==arr[cn]){
				++cn;
				next[index]=cn;
				++index;
			} else if(cn>0){
				cn=next[cn];
			} else {
				next[index]=0;
				++index;
				cn=0;
			}
		}

		return next;
	}

	public static int kmp(String s1,String s2){
		if(s1==null||s2==null||s1.length()==0||s2.length()==0){
			return -1;
		}

		char[] str1=s1.toCharArray();
		char[] str2=s2.toCharArray();
		int[] next=getNext(str2);

		int len1=str1.length;
		int len2=str2.length;
		
		int index1=0;
		int index2=0;
		while(index1<len1&&index2<len2){
			if(str1[index1]==str2[index2]){
				++index1;
				++index2;
			} else if(index2>0){
				index2=next[index2];
			} else {
				++index1;
				index2=0;
			}
		}
		
		return index2==len2?index1-len2:-1;
	}

	//Nim博弈问题
	public static void nim(int[] arr){
		if(arr==null||arr.length==0){
			throw new RuntimeException("the argument arr is invalid!");
		}
		
		//保证非负数组
		int len=arr.length;
		for(int i=0;i<len;i++){
			if(arr[i]<0){
				throw new RuntimeException("the argument arr is invalid!");
			}
		}

		int res=0;
		for(int i=0;i<len;i++){
			res^=arr[i];
		}
	
		System.out.println(res!=0?"先手赢":"后手赢");

	}





	public static void main(String[] args){
		int[] arr={
			1,100,2,4
		};
		smart(arr);

		int[] arr1={
			1,100
		};
		smart(arr1);

		String s1="abcdvferfewg";
		String s2="dvf";
		System.out.println(kmp(s1,s2));

		s1="sdfgdshrtjhtyj";
		s2="tuyiy";
		System.out.println(kmp(s1,s2));

		int[] arr2={
			1,2,3,4,56,6
		};
		nim(arr2);

		int[] arr3={
			1,2,3,4,56,6,-1
		};
		nim(arr3);
		

		System.out.println("hello world");
	}
}













