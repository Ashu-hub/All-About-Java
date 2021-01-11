package JavaOverview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;



class A{
	private void m1() {
		System.out.println("A");
	}
	
}

class B extends A{
	private void m1() {
		System.out.println("B");
	}
	
	public static void main(String[] args) {
		A a = new A();
	//	a.m1();
	}
}

public class StreamExample {

	public static void main(String[] args) {
		
		
		List<Integer> list  = Arrays.asList(5, 13, 4, 21, 13, 27, 2, 59, 59, 34);
		
		
		/*
		 * Stream<Integer> stream = Stream.of(5, 13, 4, 21, 13, 27, 2, 59, 59, 34);
		 */
		
		//1. Find Distincts:
		//stream.distinct().forEach(System.out::println);
		
		//2. Find Dulicates:
		/*
		 * A. Using Set
		 * Set items = new HashSet(); stream.filter(n -> !items.add(n)).forEach(System.out::println);
		 */
		
		//B. Using Collectors.groupingBy():- 
		/*
		 * Map<Integer, Long> s =
		 * stream.collect(Collectors.groupingBy(Function.identity(),
		 * Collectors.counting() )); System.out.println(s);
		 */
		
		
		Set items = new HashSet();
		Map<Integer, Integer> a= list.stream().filter(n -> !items.add(n)).collect(Collectors.toMap(Function.identity(), Function.identity()));
		System.out.println(a);
	}

}

