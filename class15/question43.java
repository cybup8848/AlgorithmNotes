/*************************************************************************
    > File Name: question43.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 21 09:43:35 2024
 ************************************************************************/



public class question43{


	//自己实现
	public static void getNumChiExp1(int num){
		if(num==0){
			System.out.println("零");
			return;
		}

		System.out.print(num<0?"负":"");
		
		int[] nums=new int[10];
		int cn=0;
		while(num!=0){
			nums[cn++]=Math.abs(num%10);
			num/=10;
		}

		boolean preHaveNum=false;//前面是否有数字
		
		String[] chNums={
			"零","一","二","三","四","五","六","七","八","九"
		};

		String[] units={
			"","十","百","千"
		};

		String[] units4={
			"","万","亿"
		};

		
		boolean haveZero=true;

		int haveNumIndex=-1;
		int i=cn-1;
		while(i>=0){
			if(nums[i]==0){
				if(haveNumIndex==-1||i==0){
					--i;
				} else {
					if(haveNumIndex-i>0){
						System.out.print("零");
						haveZero=true;
					}
					while(i>=0&&nums[i]==0){
						--i;
					}
				}
			} else if((i==9||i==5||i==1)&&nums[i]==1&&nums[i-1]==0&&haveZero){
				System.out.print("十");
				System.out.print(units4[(i-1)/4]);
				i-=2;
				haveNumIndex=i-1;
				haveZero=false;
			} else if((i==cn-1&&(i==9||i==5))&&(nums[i]==1)){
				System.out.print("十");
				--i;
			} else {
				System.out.print(chNums[nums[i]]+units[i%4]);
				haveZero=false;
				haveNumIndex=i;
				if(i>0&&i%4==0){
					System.out.print(units4[i/4]);
				}
				--i;
			}

		}

		System.out.println();
	}

	

	//左程云实现
	public static String num1To19(int num){
		if(num<1||num>19){
			return "";
		}

		String[] names={
			"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen",
			"Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"
		};

		return names[num-1];
	}

	public static String num1To99(int num){
		if(num<1||num>99){
			return "";
		}

		if(num<20){
			return num1To19(num);
		}

		int high=num/10;

		String[] tyNames={
			"Twenty","Thirty","Fourty","Fifty","Sixty","Seventy","Eighty","Ninety"
		};

		return tyNames[high-2]+num1To19(num%10);
	}

	public static String num1To999(int num){
		if(num<1||num>999){
			return "";
		}

		if(num<100){
			return num1To99(num);
		}

		int high=num/100;

		return num1To19(high)+"Hundred "+num1To99(num%100);
	}

	public static String getNumEngExp(int num){
		if(num==0){
			return "Zero";
		}
		
		String res="";
		if(num<0){
			res+="Negative,";
		}
		
		if(num==Integer.MIN_VALUE){
			res+="Two Billion,";
			num%=-2000000000;
		}

		num=Math.abs(num);

		int high=1000000000;

		//21.230,123,456
		// 1,000,000,000
		//21 Billion---> 230,123,456
		//                 1,000,000
		//230 Billion   ----> 123,456
		//                      1,000
		
		//123 Thousand ---->456
		//                    1
		//456
		
		int highIndex=0;
		String[] names={
			"Billion","Million","Thousand",""
		};

		while(num!=0){
			int cur=num/high;
			num%=high;

			if(cur!=0){
				res+=num1To999(cur);
				res+=names[highIndex]+(num==0?" ":", ");
			}
			
			high/=1000;
			highIndex++;
		}

		return res;
	}

	
	//搞定中文
	//只接收范围是 1~9
	public static String num1To9_ch(int num){
		if(num<1||num>9){
			return "";
		}
		
		String[] names={
			"一","二","三","四","五","六","七","八","九"
		};
		
		return names[num-1];
	}

	//只接收范围是 1~99
	public static String num1To99_ch(int num,boolean hasBai){
		if(num<1||num>99){
			return "";
		}

		if(num<10){
			return num1To9_ch(num);
		}

		//有十位
		int shi=num/10;
		if(shi==1&&(!hasBai)){
			return "十"+num1To9_ch(num%10);
		} else {
			return num1To9_ch(shi)+"十"+num1To9_ch(num%10);
		}
	}

	public static String num1To999_ch(int num){
		if(num<1||num>999){
			return "";
		}
		if(num<100){
			return num1To99_ch(num,false);
		}
		
		// >=100
		String res=num1To9_ch(num/100)+"百";
		int rest=num%100;
		if(rest==0){
			return res;
		} else if(rest>=10){
			res+=num1To99_ch(rest,true);
		} else {
			res+="零"+num1To9_ch(num);
		}

		return res;
	}

	public static String num1To9999_ch(int num){
		if(num<1||num>9999){
			return "";
		}

		if(num<1000){
			return num1To999_ch(num);
		}

		String res=num1To9_ch(num/1000)+"千";
		int rest=num%1000;
		if(rest==0){
			return res;
		} else if(rest>=100){
			res+=num1To999_ch(rest);
		} else {
			res+="零"+num1To99_ch(rest,false);
		}
		
		return res;
	}

	public static String num1To99999999_ch(int num){
		if(num<1||num>99999999){
			return "";
		}

		int wan=num/10000;
		int rest=num%10000;

		if(wan==0){
			return num1To9999_ch(rest);
		}

		String res=num1To9999_ch(wan)+"万";
		if(rest==0){
			return res;
		} else {
			if(rest<1000){
				return res+"零"+num1To999_ch(rest);
			} else {
				return res+num1To9999_ch(rest);
			}
		}
	}


	public static String getNumChiExp(int num){
		if(num==0){
			return "零";
		}

		String res="";
		if(num<0){
			res+="负";
		}
		
		int yi=Math.abs(num/100000000);
		int rest=Math.abs(num%100000000);

		if(yi==0){
			return res+num1To99999999_ch(rest);
		}
		
		res+=num1To9999_ch(yi)+"亿";
		if(rest==0){
			return res;
		} else {
			if(rest<10000000){
				return res+"零"+num1To99999999_ch(rest);
			} else {
				return res+num1To99999999_ch(rest);
			}
		}
	}




	public static void main(String[] args){

		int num=-2353765;
		while(num!=0){
			System.out.println(num%10000);
			num/=10000;
		}


		System.out.println(getNumChiExp(2147483647));
		System.out.println(getNumChiExp(-2147483648));

		System.out.println(getNumChiExp(1124263345));

		System.out.println(getNumChiExp(0));

		System.out.println(getNumChiExp(-2009956038));

		System.out.println(getNumChiExp(-720774515));

		System.out.println(getNumChiExp(1626177776));

		System.out.println(getNumChiExp(10));

		System.out.println(getNumChiExp(110));

		System.out.println(getNumChiExp(1010));

		System.out.println(getNumChiExp(10010));
		
		System.out.println(getNumEngExp(2353754));

		System.out.println("hello world");
	}
}



