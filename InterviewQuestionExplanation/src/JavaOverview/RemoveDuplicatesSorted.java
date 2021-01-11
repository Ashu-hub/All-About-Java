package JavaOverview;

import java.util.Arrays;

public class RemoveDuplicatesSorted {

	public static void main(String[] args) {
		int [] arr= {1, 3, 3, 7, 8, 9, 14, 16, 16, 17, 17};
		removeDuplicate(arr);
		
	}
	
	private static void removeDuplicate(int[] arr) {
		int [] newArr = null ;
		int  j=0;
		for (int i = 0; i < arr.length-1; i++) {
			if(arr[i] != arr[i+1]) {
				newArr[j++] = arr[i];
			}
		}
		System.out.println(Arrays.toString(newArr));
		
	}

}
