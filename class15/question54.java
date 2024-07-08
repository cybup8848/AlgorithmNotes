/*************************************************************************
    > File Name: question54.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 24 11:32:20 2024
 ************************************************************************/


public class question54{

	public static String minDictionary(String s){
		if(s==null||s.length()<2){
			return s;
		}
		
		char[] str=s.toCharArray();
		int len=str.length;

		int[] map=new int[256];
		for(char x:str){
			++map[x];
		}

		int minASCIndex=0;
		for(int i=0;i<len;i++){
			minASCIndex=str[minASCIndex]>str[i]?i:minASCIndex;
			--map[str[i]];
			if(map[str[i]]==0){
				break;
			}
		}
		
		return String.valueOf(str[minASCIndex])+minDictionary(s.substring(minASCIndex+1).replaceAll(String.valueOf(str[minASCIndex]),""));
	}	

	public static void main(String[] args){
		
		String s="acbc";
		System.out.println(minDictionary(s));
		System.out.println("\n\n\n");

		s="dbcacbca";
		System.out.println(minDictionary(s));



		System.out.println("hello world");
	}
}




