/*************************************************************************
    > File Name: question32.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Thu May 16 14:51:24 2024
 ************************************************************************/




public class question32{

	public static int str2int1(String s){
		if(s==null){
			return -1;
		}

		char[] str=s.toCharArray();
		int len=str.length;
		int i=0;
		int flag=1;
		if(str[i]=='-'){
			++i;
			flag=-1;
		} else if(str[i]=='+'){
			++i;
		}


		int sum=0;
		int tmp=0;
		for(;i<len;i++){
			if(str[i]<'0'||str[i]>'9'){
				return -1;
			}

			tmp=flag*((int)(str[i]-'0'));
			if((sum>Integer.MAX_VALUE/10)||(sum==Integer.MAX_VALUE/10&&tmp>7)){
				return -1;
			}

			if((sum<Integer.MIN_VALUE/10)||(sum==Integer.MIN_VALUE/10&&tmp<-8)){
				return -1;
			}
			
			sum=sum*10+tmp;
		}


		return sum;
	}

	
	public static int str2int2(String s){
		if(s==null){
			return -1;
		}
		
		int res=0;
		try{
			res=Integer.parseInt(s);
		} catch(NumberFormatException e){
			System.out.println("转换失败:"+e.getMessage());
			return -1;
		}
		
		return res;
	}


	//左程云实现
	//检查某一个字符串str，是否符合日常规范
	public static boolean isValid(char[] str){
		if(str[0]!='-'&&(str[0]<'0'||str[0]>'9')){//开头不是减号、数字字符
			return false;
		}

		//1) str[0]=='-' && str.length==1
		//2) str[0]=='-' && str.length!=1 &&str[1]=='0'
		if(str[0]=='-'&&(str.length==1||str[1]=='0')){
			return false;
		}

		if(str[0]=='0'&&str.length>1){
			return false;
		}

		//上面是检查开头

		for(int i=1;i<str.length;i++){
			if(str[i]<'0'||str[i]>'9'){
				return false;
			}
		}
		
		return true;
	}

	public static int convert(String s){
		if(s==null||s.equals("")){
			return 0;//can not convert
		}

		char[] str=s.toCharArray();
		if(!isValid(str)){
			throw new RuntimeException("can not convert");
		}

		boolean neg=str[0]=='-'?true:false;
		int minq=Integer.MIN_VALUE/10;
		int minr=Integer.MIN_VALUE%10;

		int res=0;
		int cur=0;
		for(int i=neg?1:0;i<str.length;i++){

			//str[i]=0    cur=0
			//str[i]=1    cur=-1
			//str[i]=3    cur=-3
			cur='0'-str[i];

			//中途转换过程中，溢出的时候
			if((res<minq)||(res==minq&&cur<minr)){
				throw new RuntimeException("can not convert");
			}

			res=res*10+cur;
		}
		
		if(!neg&&res==Integer.MIN_VALUE){//如果是int最小值，且是正数，那么溢出；如果res<Integr.MINVALUE，那么已经throw异常
			throw new RuntimeException("can nnot convert");
		}

		return neg?res:-res;
	}



	public static void main(String[] args){
		System.out.println(str2int1("-1323534643"));
		System.out.println(str2int1("2353645"));
		System.out.println(str2int1("1242385467658534654745856"));
		System.out.println(str2int1("-23578346579823572386543643"));
		System.out.println(str2int1("3464tsge23534fds"));
		System.out.println("\n\n\n");


		System.out.println(str2int2("-1323534643"));
		System.out.println(str2int2("2353645"));
		System.out.println(str2int2("1242385467658534654745856"));
		System.out.println(str2int2("-23578346579823572386543643"));
		System.out.println(str2int2("3464tsge23534fds"));
		System.out.println("\n\n\n");

		System.out.println(convert("-1323534643"));
		System.out.println(convert("2353645"));
		System.out.println(convert("1242385467658534654745856"));
		System.out.println(convert("-23578346579823572386543643"));
		System.out.println(convert("3464tsge23534fds"));
		System.out.println("\n\n\n");

		System.out.println("hello world");
	}
}



