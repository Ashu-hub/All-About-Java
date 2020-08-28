package JavaOverview.String;

// WAP to find Anagram/Permutation of Goven String
//Permutations of inputString(ABC) are: [ACB, ABC, BCA, CBA, CAB, BAC]
public class AnagramExample {

	public static void main(String[] args) {
		String s = "ABC";	
		generateAnagram(s, 0, s.length());

	}

	private static void generateAnagram(String s, int l, int h) {

		if (l == h - 1) {
			System.out.println(s);
		} else {
			for (int i = l; i < h; i++) {

				s = swap(s, l, i);
				generateAnagram(s, l+1, h);
				s= swap(s, l,i);
			}
		}
	}

	private static String swap(String s, int l, int h) {
		char [] ch = s.toCharArray();
		char temp;
		temp = ch[l];
		ch[l] = ch[h];
		ch[h] = temp;
		
		return String.valueOf(ch);
	}

}
