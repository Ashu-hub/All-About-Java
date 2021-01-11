package JavaOverview;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class find2ndNonrepeatingChar {

	public static void main(String[] args) {

		String str = "aaaabbbccdeeefffghhhijj";
		// o/p = g
		findNonRepeating2(str);
	}

	// 1st way
	private static void findNonRepeating(String str) {

		Map<Character, Integer> countMap = new HashMap<>();

		for (char c : str.toCharArray()) {
			countMap.put(c, (countMap.containsKey(c) ? countMap.get(c) + 1 : 1));
		}

		System.out.println(countMap);
		int count = 0;
		for (Entry<Character, Integer> entry : countMap.entrySet()) {
			if (entry.getValue() == 1) {
				count++;
				if (count == 2) {
					System.out.println(entry.getKey());
				}
			}
		}
	}

	// 2nd way
	private static void findNonRepeating2(String str) {
		Map<Character, Integer> countMap = new HashMap<>();
		
		for (char c : str.toCharArray()) {
			countMap.put(c, countMap.containsKey(c) ? countMap.get(c) + 1 : 1);
		}
		System.out.println(countMap);
		Map result = countMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o,n) -> o , HashMap::new ));
		System.out.println(result);
	}

}
