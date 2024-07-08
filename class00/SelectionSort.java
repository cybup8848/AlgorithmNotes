package class00;
public class Code1_SelectionSort{
	public static void selectionSort(int[] arr){
		if(arr==null||arr.length<2){
			return;
		}
		int len=arr.length;
		for(int i=0;i<len-1;i++){
			int minIndex=i;
			for(int j=i+1;j<len;j++){
				minIndex=arr[j]<arr[minIndex]?j:minIndex;
			}
			swap(arr,i,minIndex);
		}
		return;
	}
	
	public static void swap(int[] arr,int i,int j){
		int temp=arr[i];
		arr[i]=arr[j];
		arr[j]=temp;
	}

	public static void comparator(int[] arr){
		Arrays.sort(arr);
	}
	
	
	



}
