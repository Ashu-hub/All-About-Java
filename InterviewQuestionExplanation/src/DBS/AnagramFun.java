package DBS;

import java.util.Scanner;
import java.util.TreeSet;

/*We define an anagram to be a word whose characters can be rearranged to create another word. Given two strings,  we want to know the minimum number of characters already in either string that we must modify to make the two strings anagrams; if it's not possible to make the two strings anagrams, we consider this number to be -1. For example:
*/
public class AnagramFun {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int lenA = sc.nextInt();
		String a[] = new String[lenA];
		for (int i = 0; i < lenA; i++) {
			a[i] = sc.next();
		}

		int lenB = sc.nextInt();
		String b[] = new String[lenB];
		for (int i = 0; i < lenB; i++) {
			b[i] = sc.next();
		}
		findAnagram(a, b);

	}

//a = [a, jk, abb, mn, abc] and b = [bb, kj, bbc, op, def], 
	private static void findAnagram(String[] a, String[] b) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(findAnaramforindex(a[i], b[i]));
		}
	}

	private static int findAnaramforindex(String a, String b) {
		if (a.length() != b.length()) {
			return -1;
		} else {
			if (new StringBuffer(a).reverse().toString().equals(b)) {
				return 0;
			}
			return countdifferntChar(a, b);

		}
	}

	private static int countdifferntChar(String a, String b) {
		TreeSet aSet = new TreeSet();
		TreeSet bSet = new TreeSet();
		
		for (char c : a.toCharArray())
			aSet.add(c);
		
		for(char c : b.toCharArray())
			bSet.add(c);
		
		aSet.retainAll(bSet);
		
		return (a.length() - aSet.size());
		
	}

}
