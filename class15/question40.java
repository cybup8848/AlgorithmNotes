/*************************************************************************
    > File Name: question40.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 20 16:01:09 2024
 ************************************************************************/



public class question40{


	//s中hi有'.'，或者'X' 两种
	//路灯可以影响左中右三个位置
	//至少需要多少灯，可以把'.'都点亮

	//左程云实现
	public static int minLight1(String s){
		if(s==null||s.length()==0){
			return 0;
		}
		
		char[] str=s.toCharArray();
		int len=str.length;

		int light=0;

		int index=0;

		//当你来到i位置，一定要保证之前的灯，彻底不会影响i位置
		while(index<len){
			if(str[index]=='X'){
				++index;
			} else { //str[index]=='.'
				++light;
				if(index+1==len){
					break;
				} else {//如果下面有位置
					if(str[index+1]=='X'){
						index+=2;
					} else {//下一个位置是'.'，..
						index+=3;
					}
				}
			}
		}

		return light;
	}
	

	public static void main(String[] args){

		String s="..X....X.X.XX...X.";
		System.out.println(minLight1(s));

		
		s="............................";
		System.out.println(minLight1(s));


		s=".X.X.X.X.X.";
		System.out.println(minLight1(s));


		s="X...X..X";
		System.out.println(minLight1(s));

		System.out.println("hello world");
	}


}




