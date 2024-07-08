/*************************************************************************
    > File Name: question22.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun May 12 19:13:03 2024
 ************************************************************************/



public class question22{

	public static class Pet{
		private String type;
		public Pet(String type){
			this.type=type;
		}
		
		public String getPetType(){
			return this.type;
		}
	}

	public static class Dog extends Pet{
		public Dog(){
			super("Dog");
		}
	}

	public static class Cat extends Pet{
		public Cat(){
			super("cat");
		}
	}
	



	public static void main(String[] args){



		System.out.println("hello world");
	}
}
