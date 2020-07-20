```java
Actual Vs Formal Parameters:
Actual Parameters are Values that are passes to a fnction when it is invoked.
Formal Parameters are the variables defined by the function tha receives values when function is called.

	public static void main(String args[]){
	add(1,2); //1,2 are actual Parameters
	}
	static void add(int a , int b){ //a,b are Formal parameters
	System.out.println(a+b);
	}

```

# Generics in java
In a nutshell, generics enable types (classes and interfaces) to be parameters when defining classes, interfaces and methods. 
**Much like the more familiar formal parameters used in method declarations, type parameters provide a way for you to re-use the same code with different inputs.
The difference is that the inputs to formal parameters are values, while the inputs to type parameters are types.


## Benefits of Generics :
Code that uses generics has many benefits over non-generic code:
1. Stronger type checks at compile time.
2. Elimination of casts.
```java
//The following code snippet without generics requires casting:
	List list = new ArrayList();
	list.add("hello");
	String s = (String) list.get(0);
	
//When re-written to use generics, the code does not require casting:
	List<String> list = new ArrayList<String>();
	list.add("hello");
	String s = list.get(0);   // no cast
```

3. Enabling programmers to implement generic algorithms:-
By using generics, programmers can implement generic algorithms that work on collections of different types, can be customized, and are type safe and easier to read.

## Generic Types
A generic type is a generic class or interface that is parameterized over types. 
Eg:-
A Simple Box Class
```java
public class Box {
    private Object object;

    public void set(Object object) { this.object = object; }
    public Object get() { return object; }
}
```
** Generic Version
```java
public class Box <T> {
	private T t;
	
	public void ser (T t) { this.t = t; }
	publilc T get(){ return this.t; }
```

### Invoking and Instantiating a Generic Type
To reference the generic Box class from within your code, you must perform a generic type invocation, which replaces T with some concrete value, such as Integer:
Box<Integer> integerBox;
It simply declares that integerBox will hold a reference to a "Box of Integer", which is how Box<Integer> is read.

To instantiate this class, use the new keyword, as usual, but place <Integer> between the class name and the parenthesis:
Box<Integer> integerBox = new Box<Integer>();

Java 7 or later above code can be re-written as, as long as the complieer can determine/infer:-
Box<Integer> integerBox = new Box<>();

** Note Generics can only work with Objects and does not support primitive types.

## Multiple Type Parameters
A generic class can have multiple type parameters. 
```java
public interface Pair<K, V> {
    public K getKey();
    public V getValue();
}

public class OrderedPair<K, V> implements Pair<K, V> {

    private K key;
    private V value;

    public OrderedPair(K key, V value) {
	this.key = key;
	this.value = value;
    }

    public K getKey()	{ return key; }
    public V getValue() { return value; }
}
//The following statements create two instantiations of the OrderedPair class:

Pair<String, Integer> p1 = new OrderedPair<String, Integer>("Even", 8);
Pair<String, String>  p2 = new OrderedPair<String, String>("hello", "world");
						//OR
OrderedPair<String, Integer> p1 = new OrderedPair<>("Even", 8);
OrderedPair<String, String>  p2 = new OrderedPair<>("hello", "world");
```

## Parameterized Types
You can also substitute a type parameter (i.e., K or V) with a parameterized type (i.e., List<String>). For example, using the OrderedPair<K, V> example:
```java
OrderedPair<String, Box<Integer>> p = new OrderedPair<>("primes", new Box<Integer>(...));
```

## Raw Type
A raw type is the name of a generic class or interface without any type arguments. For example, given the generic Box class:
```java
public class Box<T> {
    public void set(T t) { /* ... */ }
    // ...
}
```
To create a parameterized type of Box<T>, you supply an actual type argument for the formal type parameter T:

```java
Box<Integer> intBox = new Box<>();
```
If the actual type argument is omitted, you create a raw type of Box<T>:
```java
Box rawBox = new Box();
```
Therefore, Box is the raw type of the generic type Box<T>. However, a non-generic class or interface type is not a raw type.
Raw types show up in legacy code because lots of API classes (such as the Collections classes) were not generic prior to JDK 5.0. When using raw types, you essentially get pre-generics behavior — a Box gives you Objects. For backward compatibility, assigning a parameterized type to its raw type is allowed:
```java 
Box<String> stringBox = new Box<>();
Box rawBox = stringBox;               // OK
But if you assign a raw type to a parameterized type, you get a warning:

Box rawBox = new Box();           // rawBox is a raw type of Box<T>
Box<Integer> intBox = rawBox;     // warning: unchecked conversion
You also get a warning if you use a raw type to invoke generic methods defined in the corresponding generic type:

Box<String> stringBox = new Box<>();
Box rawBox = stringBox;
rawBox.set(8);  // warning: unchecked invocation to set(T)

The warning shows that raw types bypass generic type checks, deferring the catch of unsafe code to runtime. Therefore, you should avoid using raw types.
```

