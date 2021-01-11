package JavaOverview;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectorsExamples {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("a", "b", "c", "d");
		List<String> names = Arrays.asList("Adam", "Badam", "Aakash", "Devil");

		System.out.println(list.stream().collect(Collectors.joining(",")));
		names.stream().filter(s -> s.startsWith("A")).forEach(System.out::print);
		String i = Stream.of("A","B","C","D","E","F").max(Comparator.comparing(String::valueOf)).get();
		
		System.out.println("********"+i);
		System.out.println(fact(5));
		fibonacci(10);
	}

	private static void fibonacci(int i) {
		int first = 0;
		int second = 1;
		int temp = 0;
		System.out.print(first+" "+second+" ");
		for (int j = 0; j < i; j++) {
			temp = first + second;
			first = second;
			second = temp;
			System.out.print(temp+" ");
		}
	}

	private static int fact(int i) {
		if (i == 0 || i == 1) {
			return i;
		} else {
			return i* fact(i-1);
		}
	}

}
