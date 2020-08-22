# Functional Interface:-(SAM)
## Predicate:- 

	is a Functional Interface presents in java.util.function package. Represents a predicate (boolean-valued function) of one argument.

	Methods:-  

		a)boolean test(T t) -> Evaluates this predicate on the given  t input argument.
		b)default Predicate<T> and(Predicate<? super T> other) -> Returns a predicate that represents a logical AND of this predicate and another.
		c)default Predicate<T> negate() -> Returns a predicate that represents the logical negation of this predicate.
		d)default Predicate<T> or(Predicate<? super T> other) -> eturns a predicate that represents a logical OR of this predicate and another.
		e)static <T> Predicate<T> isEqual(Object targetRef)-> Returns a predicate that tests if two arguments are equal according given Objects.
		eg:- 
		Predicate<String> acquirerMatcher = p -> p.equalsIgnoreCase( "acquirerId" );
		acquirerMatcher.test("acquirerId");
	
## Function<T, R>:- 

		Represents a function that accepts one argument and produces a result. The primary purpose for which Function<T, R> has been created is for mapping scenarios i.e when an object of a type is taken as input and it is converted(or mapped) to another type

		example:- 
			Function<Integer, Double> half = a -> a / 2.0; 
			System.out.println(half.apply(10)); //output -> 5.0(since output is in double)

		Mehtods:-

		a)  R apply(T t); Applies this function to the given argument.
		b) default <V> Function<V, R> compose(Function<? super V, ? extends T> before) -> It returns a composed function wherein the parameterized function will be executed **FIRST** and then the first one.
			eg:- 
			Function<Integer, Double> half = a -> a / 2.0;
			half = half.compose(a -> 3 * a); 
			System.out.println(half.apply(5)); //OUTPUT:- 7.5

		c) default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) -> It returns a composed function wherein the parameterized function will be executed **AFTER** the first one. 
			eg:-
			Function<Integer, Double> half = a -> a / 2.0; 
		  
				// Now treble the output of half function 
				half = half.andThen(a -> 3 * a); 
		  
				// apply the function to get the result 
				System.out.println(half.apply(10)); //outPUT= 15.0
			} 

		d) static <T> Function<T, T> identity()  -> This method returns a function which returns its only argument.
			eg:- // Function which takes in a number and 
				// returns it 
				Function i = Function.identity(); 
		  
				System.out.println(i); 
				//output -> java.util.function.Function$$Lambda$1/250421012@119d7047
				//https://javabydeveloper.com/java-8-identity-function-examples/

##  Consumer<T> -> Represents an operation that accepts a single input argument and returns no result.
methods:- 
	a) void accept(T t):-  Performs this operation on the given argument
	eg:-
	// Consumer to display a number 
        Consumer<Integer> display = a -> System.out.println(a); 
  
        // Implement display using accept() 
        display.accept(10); 
		
	b) default Consumer<T> andThen(Consumer<? super T> after):- It returns a composed Comsumer wherein the parameterized Consumer will be executed after the first one.
	eg:- 
	Consumer<List<Integer> > modify = list -> 
        { 
            for (int i = 0; i < list.size(); i++) 
                list.set(i, 2 * list.get(i)); 
        }; 
  
        // Consumer to display a list of integers 
        Consumer<List<Integer> > 
            dispList = list -> list.stream().forEach(a -> System.out.print(a + " ")); 
  
        List<Integer> list = new ArrayList<Integer>(); 
        list.add(2); 
        list.add(1); 
        list.add(3); 
  
        // using addThen() 
        modify.andThen(dispList).accept(list); 
        ; 
		//output:-
		4 2 6
		
->4) Supplier<T>:- . It represents a function which does not take in any argument but produces a value of type T.
methods:-
 a) T get()--> gets a result.
 eg:-
  // This function returns a random value. 
        Supplier<Double> randomValue = () -> Math.random(); 
  
        // Print the random value using get() 
        System.out.println(randomValue.get())


-> what's a Streams:-
One of the major new features in Java 8 is the introduction of the stream functionality – java.util.stream – which contains classes for processing sequences of elements.
Stream is itself a class in java.util.stream package

-> How to create Stream?
Streams can be created from different element sources e.g. collection or array with the help of stream() and of() methods:

String[] arr = new String[]{"a", "b", "c"};
1) Stream<String> stream = Arrays.stream(arr);
2) stream = Stream.of("a", "b", "c");

A stream() default method is added to the Collection interface and allows creating a Stream<T> using any collection as an element source:
3) Stream<String> stream = list.stream();

->What are Stream Operations?
They are divided into intermediate operations (returns Stream<T>) and terminal operations (returns a result of definite type). Intermediate operations allow chaining.
It's also worth noting that operations on streams don't change the source.
All Stream operations are divided into intermediate and terminal operations and are combined to form stream pipelines.
A stream pipeline consists of a source (such as a Collection, an array, a generator function, an I/O channel, or infinite sequence generator); followed by zero or more intermediate operations and A terminal operation.

->What are Intermediate Operations?
Intermediate operations are not executed unit until some terminal operation is invoked.

filter()
map()
flatMap()
distinct()
sorted()
peek()
limit()
skip()

Basically, intermediate operations return a new stream. Executing an intermediate operation does not actually perform any operation, but instead creates a new stream that, when traversed, contains the elements of the initial stream that match the given predicate.

->filter():-
Returns a stream consisting of the elements of this stream that match the given predicate.
Signature:- Stream<T> filter(Predicate<? super T> predicate)

->map():-
Stream map(Function mapper) returns a stream consisting of the results of applying the given function to the elements of this stream.
Signature:- Stream<R> map(Function<? super T, ? extends R> mapper);
eg:-list.stream().map(number -> number * 3).forEach(System.out::println); 

->flatMap():-
Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.
Signature:- Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

->Map vs flatMap:-
Stream.flatMap, as it can be guessed by its name, is the combination of a map and a flat operation. That means that you first apply a function to your elements, and then flatten it. Stream.map only applies a function to the stream without flattening the stream.
To understand what flattening a stream consists in, consider a structure like [ [1,2,3],[4,5,6],[7,8,9] ] which has "two levels". Flattening this means transforming it in a "one level" structure : [ 1,2,3,4,5,6,7,8,9 ]
When we collect two lists, given below
Without flat map => [1,2],[1,1] => [[1,2],[1,1]] Here two lists are placed inside a list, so the output will be list containing lists
With flat map => [1,2],[1,1] => [1,2,1,1] Here two lists are flattened and only the values are placed in list, so the output will be list containing only elements

->distinct():-
Is a method in java.util.stream.IntStream. This method returns a stream consisting of the distinct element.
Signature:- Stream<T> distinct();
eg:-
IntStream stream = IntStream.of(2, 3, 3, 5, 6, 6, 8); 
stream.distinct().forEach(System.out::println);
O/P- 2,3,5,6,8

->sorted():-
Returns a stream consisting of the elements of this stream in sorted order.
Signature:- Stream<T> sorted() /  Stream<T> sorted(Comparator<? super T> comparator);

->peek():-
Returns a stream consisting of the elements of this stream, additionally performing the provided action on each element as elements are consumed from the resulting stream.
Signature:- Stream<T> peek(Consumer<? super T> action);
eg:- stream.peek(System.out::println).sum(); //First Display and then perform Sum.

->limit():-
returns a stream consisting of the elements of this stream, truncated to be no longer than maxSize in length
Signature:-  Stream<T> limit(long maxSize);
eg:-IntStream stream = IntStream.of(2, 4, 6, 8, 10);  
        stream.limit(3).forEach(System.out::println);// O/P- 2,4,6

->skip():-
 Returns a stream consisting of the remaining elements of this stream after discarding the first {@code n} elements of the stream.
Signature:- Stream<T> skip(long n);
eg:-
// Creating an IntStream of numbers [5, 6, .. 11] 
        IntStream stream = IntStream.range(5, 12); 
        // Using skip() to skip first 4 values in range 
        // and displaying the rest of elements 
        stream.skip(4).forEach(System.out::println); 
		
--Basically, intermediate operations return a new stream. Executing an intermediate operation does not actually perform any operation, but instead creates a new stream that, when traversed, contains the elements of the initial stream that match the given predicate.
As such, traversal of the Stream doesn't begin until the terminal operation of the pipeline is executed.

->What are Terminal Operations?
Terminal operations may traverse the stream to produce a result or a side effect. Terminal operations are eager, completing their traversal of the data source and processing of the pipeline before returning.
Terminal operations are:

forEach()
forEachOrdered()
toArray()
reduce()
collect()
min()
max()
count()
anyMatch()
allMatch()
noneMatch()
findFirst()
findAny()

Each of these operations will trigger execution of all intermediate operations.

->forEach():-
performs an action for each element of the stream.
Signature:-
void forEach(Consumer<? super T> action);

->forEachOrdered():-
performs an action for each element of this stream, in the encounter order of the stream if the stream has a defined encounter order.
Signature:-void forEachOrdered(Consumer<? super T> action);
eg:- same as foreach with minute diff.

->forEach vs forEachOrdered:-
forEach() method performs an action for each element of this stream. For parallel stream, this operation does not guarantee to maintain order of the stream.
forEachOrdered() method performs an action for each element of this stream, guaranteeing that each element is processed in encounter order for streams that have a defined encounter order.

take below example:

    String str = "sushil mittal";
    System.out.println("****forEach without using parallel****");
    str.chars().forEach(s -> System.out.print((char) s));
	
    System.out.println("\n****forEach with using parallel****");
    str.chars().parallel().forEach(s -> System.out.print((char) s));
	
    System.out.println("\n****forEachOrdered with using parallel****");

    str.chars().parallel().forEachOrdered(s -> System.out.print((char) s));
Output:
****forEach without using parallel****
sushil mittal
****forEach with using parallel****
mihul issltat
****forEachOrdered with using parallel****
sushil mittal

-> toArray():-
Returns an array containing the elements of this stream
Signature:-Object[] toArray();

->reduce():-
performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function, and returns the reduced value.
Signature:- reduce(T identity, BinaryOperator<T> accumulator);
eg:-??

->collect():-
Conversion from Stream to Collection, like List/Set.
Signature:- collect(Collector<? super T, A, R> collector);

->min():-
Returns the minimum element of this stream according to the provided Comparator.
Signature:- Optional<T> min(Comparator<? super T> comparator);
eg:-
List<Integer> list = Arrays.asList(2, 4, 1, 3, 7, 5, 9, 6, 8);
        Optional<Integer> minNumber = list.stream().min((i, j) -> i.compareTo(j));//O/p- 1
		
->max():-
Returns the maximun element of this stream according to the provided Comparator.
Signature:-Optional<T> max(Comparator<? super T> comparator);
eg:-List<Integer> list = Arrays.asList(2, 4, 1, 3, 7, 5, 9, 6, 8);
        Optional<Integer> minNumber = list.stream().max((i, j) -> i.compareTo(j));//O/p- 9
		
->count():-
Returns the count of elements in this stream.
Signature:-long count();
eg:-
Stream.of("how","to","do","in","java").count();//O/p- 5

->anyMatch():-
Returns whether any elements of this stream match the provided predicate.
Signature:- boolean anyMatch(Predicate<? super T> predicate);
eg:-
 Stream<String> stream = Stream.of("one", "two", "three", "four");
        boolean match = stream.anyMatch(s -> s.contains("four")); //true

-->Difference between anyMatch() vs contains():-
Theoretically, there is no diff betw these two. Can be use interchangebly.

->allMatch():-
Returns whether all elements of this stream match the provided predicate.
Signature:-boolean allMatch(Predicate<? super T> predicate);
eg:-
we can use Java Stream allMatch() function on a stream of employee objects to validate that all employees are above a certain age.

->noneMatch():-
Returns whether no elements of this stream match the provided predicate.
Signature:- boolean noneMatch(Predicate<? super T> predicate);
eg:- we can use Java Stream noneMatch() function on a stream of employee objects to validate that all employees are NOT below a certain age.

->findFirst():-
Returns an {@link Optional} describing the first element of this stream
Signature:- Optional<T> findFirst();
eg:-
 Stream.of("one", "two", "three", "four")
                .findFirst()
                .ifPresent(System.out::println);//O/p- one

->findAny():-
Returns an {@link Optional} describing some element of the stream, or an empty {@code Optional} if the stream is empty.
Signature:-  Optional<T> findAny();


----
-->Java Stream reduce:-
A reduction is a terminal operation that aggregates a stream into a type or a primitive. The Java 8 Stream API contains a set of predefined reduction operations(build in reduction method), such as average(), sum(), min(), max(), and count(), which return one value by combining the elements of a stream.
->Stream.reduce():-
Stream.reduce() is a general-purpose method for generating our custom reduction operations.

Signature:- Optional<T> reduce(BinaryOperator<T> accumulator)
This method performs a reduction on the elements of this stream, using an associative accumulation function. It returns an Optional describing the reduced value, if any.

Signature:- T reduce(T identity, BinaryOperator<T> accumulator)
This method takes two parameters: the identity and the accumulator. The identity element is both the initial value of the reduction and the default result if there are no elements in the stream. The accumulator function takes two parameters: a partial result of the reduction and the next element of the stream. It returns a new partial result. The Stream.reduce() method returns the result of the reduction.

eg:-
 Optional<Car> car = persons.stream().reduce((c1, c2)
                -> c1.getPrice() > c2.getPrice() ? c1 : c2);


-->Optional Class:-
Where- In Java.util package
Java Optional Class : Every Java Programmer is familiar with NullPointerException. It can crash your code. And it is very hard to avoid it without using too many null checks.
Java 8 has introduced a new final class Optional in java.util package. It can help in writing a neat code without using too many null checks. By using Optional, we can specify alternate values to return or alternate code to run. This makes the code more readable because the facts which were hidden are now visible to the developer.
eg:- 
// Java program without Optional Class 
public class OptionalDemo{ 
	public static void main(String[] args) { 
		String[] words = new String[10]; 
		String word = words[5].toLowerCase(); 
		System.out.print(word); 
	} 
} 
O/P:- Exception in thread "main" java.lang.NullPointerException

// Java program with Optional Class 
import java.util.Optional; 
public class OptionalDemo{ 
	public static void main(String[] args) { 
		String[] words = new String[10]; 
		Optional<String> checkNull = 
					Optional.ofNullable(words[5]); 
		if (checkNull.isPresent()) { 
			String word = words[5].toLowerCase(); 
			System.out.print(word); 
		} else
			System.out.println("word is null"); 
	} 
} 
->Creating Optional Objects:-
a) we simply need to use its empty() static method:-
eg:- Optional<String> empty = Optional.empty();

b) We can also create an Optional object with the static method of():
eg:- Optional<String> opt = Optional.of(name); // name must be NOT-NULL	, else we got NullPointerException

c) in case we expect some null values, we can use the ofNullable() method:-
eg:-  Optional<String> opt = Optional.ofNullable(name); //	if we pass in a null reference, it doesn't throw an exception but rather returns an empty Optional object.

-> Checking Value Presence: isPresent() :-
Syntax:- public void ifPresent(Consumer<? super T> consumer)

When we have an Optional object , we can check if there is a value in it or not with the isPresent() method
This method returns true if the wrapped value is not null.
EG:-  Optional<String> opt = Optional.of("Baeldung");
    assertTrue(opt.isPresent());
	
->ifPresent():-  ifPresent() method enables us to run some code on the wrapped value if it's found to be non-null. Before Optional, we'd do:

if(name != null) {
    System.out.println(name.length());
}
This code checks if the name variable is null or not before going ahead to execute some code on it. This approach is lengthy and that's not the only problem, it's also prone to error.	
use:- 
    Optional<String> opt = Optional.of("baeldung");
    opt.ifPresent(name -> System.out.println(name.length()));}

->orElse():- he orElse() method returns the wrapped value if it's present
	String nullName = null;
    String name = Optional.ofNullable(nullName).orElse("john");
    assertEquals("john", name);
	
->orElseGet():-
Syntax:-  public T orElseGet(Supplier<? extends T> other)
Similar to orElse just difference is it takes Supplier<> as a Argument

-> Diff bet orElse and orElseGet()------------------

->get():- If a value is present in this {@code Optional}, returns the value, otherwise throws {@code NoSuchElementException}
	Optional<String> opt = Optional.of("baeldung");
    String name = opt.get();
    assertEquals("baeldung", name);
	
-> filter()
Syntax:- public Optional<T> filter(Predicate<? super T> predicate) 
Integer year = 2016;
    Optional<Integer> yearOptional = Optional.of(year);
    boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
    assertTrue(is2016);
    boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
    assertFalse(is2017);

-> map():-
Syntax:-  public<U> Optional<U> map(Function<? super T, ? extends U> mapper) 
eg:- String name = "baeldung";
    Optional<String> nameOptional = Optional.of(name);
 
    int len = nameOptional
     .map(String::length)
     .orElse(0);
    assertEquals(8, len);
	
------------------
1) Java 8 features regarding memory:-
Until Java 7 there was an area in JVM memory called PermGen, where JVM used to keep its classes. In Java 8 it was removed and replaced by area called Metaspace.
What are the most important differences between PermGen and Metaspace?
The only difference I know is that java.lang.OutOfMemoryError: PermGen space can no longer be thrown and the VM parameter MaxPermSize is ignored.

The main difference from a user perspective - which I think the previous answer does not stress enough - is that Metaspace by default auto increases its size (up to what the underlying OS provides), 
while PermGen always has a fixed maximum size. You can set a fixed maximum for Metaspace with JVM parameters, but you cannot make PermGen auto increase
To a large degree it is just a change of name. Back when PermGen was introduced, there was no Java EE or dynamic class(un)loading, so once a class was loaded it was stuck in memory until the JVM shut down - thus Permanent Generation. Nowadays classes may be loaded and unloaded during the lifespan of the JVM, so Metaspace makes more sense for the area where the metadata is kept.
Both of them contain the java.lang.Class instances and both of them suffer from ClassLoader leaks. Only difference is that with Metaspace default settings, it takes longer until you notice the symptoms (since it auto increases as much as it can), i.e. you just push the problem further away without solving it. OTOH I imagine the effect of running out of OS memory can be more severe than just running out of JVM PermGen, so I'm not sure it is much of an improvement.
Whether you're using a JVM with PermGen or with Metaspace, if you are doing dynamic class unloading, you should to take measures against classloader leaks,
	
====================================
SUMMARY:-
With the introduction of Java 8, functional Programming(Style of programming that treats computation as functions/expressions and avoid changing state) has been introduced in the Java Langauge . 
Java 8 has introduce Lambda Expression to bring the benefits of functional Programming in java, Lambda is just a nameless function, which means it does not have name, return Type and access modifier. 
Java 8 has introduce Functional Interface to bring the use of lambda expression. Functional Interface is having Single Abstract Method(SAM) in it. To mark an interface Function an annotation has been introduce i.e. @FunctionInterface. 
Java 8 has also added method Reference which enables us to define lambda expressions by referring to methods name directly.
Java 8 has also introduce default concrete methods and static methods inside interface. 
Java 8 has introduce java.util.function package which consititue all functional interfaces like Predicate, Function, Supplier, Consumer, BinaryOperator, UnaryOperator.
Java 8 has introduce stream functionality with java.util.stream package– which contains classes for processing sequence of elements. This is done by Stream pipeline chaining - Syntax:- source. 0 or more Intermidiate Operation. Terminal operation. Intermediate operation like - filter, map, skip, peek, sorted, distinct etc while  Terminal operation like for each(Consumer), reduce, collect, toArray, findFirst, findAny, count etc.
Java 8 has added a final class Optional in java.util package, which helps us to write neat code without worrying about too many null checks.
Java 8(HotSpot 1.8) has also removed PremGen memory space and introduce MetaSpace just to avoid java.lang.OutOfMemoryError.
Java 8 has introduced Nashorn javascript engine, which is a java based engine for executing and evaluting java Script Code.
Java 8 has also added java.time packages to enhance java Date and Time Api features.

 