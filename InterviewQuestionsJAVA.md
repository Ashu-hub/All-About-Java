## Java Overview:-
**1. What is JDK, JRE, JVM?**

		 Ans. 
		 JDK(Java Development Kit) : JDK is intended for software developers and includes development tools such as the Java compiler, Javadoc, Jar, and a debugger.
		 JRE(Java Runtime Environment) : JRE contains the parts of the Java libraries required to run Java programs and is intended for end users. JRE can be view as a subset of JDK.
		 JVM: JVM (Java Virtual Machine) is an abstract machine. It is a specification that provides runtime environment in which java bytecode can be executed. JVMs are available for many hardware and software platforms. 
			JDK = JRE + Development Tools(Java compiler, Javadoc, Jar, debugger.)
			JRE = JVM + Library Classes(JDBC, AWT, JNDI, class library lke rt.jar)
	
**2. Can you brifely tell me about JVM Architecture?**

		Ans:- Class Loader SubSystem, Different Memory, Execution Engine, Native method interface?	

**3. Is main() compulsory in java?

		Ans: Brfore Java 7, it is not mandatory to write main(). You can have full code inside static block and run that,
		However after Java 7, main() is compulsory in java, if not presenet ,Complier will throw an error saying- 'main method not found in the class'

**4.  Explain Implicit casting/promotion of primitive Data type in java?**

		Ans:- 

**5. What is Differences between Instance initialization block and Static initialization block?**

**Static initialization block:-**

		Static blocks are also called Static initialization blocks in java.
		Static block **executes when class is loaded in java.**
		static blocks executes before instance blocks in java.
		Only static variables can be accessed inside static block in java
		static blocks can be used for initializing static variables or calling any static method in java.
		this keyword cannot be used in static block in java.

**Instance initialization block:-**

		Instance blocks are also called instance initialization blocks in java
		Instance block **executes when instance of class is created in java.**
		Also known as non-static initialization block in java.
		instance blocks executes after static blocks in java.
		Static and non-static variables (instance variables) can be accessed inside instance block in java.
		instance blocks can be used for initializing instance variables or calling any instance method in java.
		this keyword can be used in instance block in java.

		**Flow of Calling:- Static Blocks-> Instance blocks-> Construtors**

**6. How to define your own data type in java?**

		Ans:- Using enums

**7. Can we use '_' as a variable name in java?**

		Ans:- Till java 8 yes, from java 9 -NO

**8. What is Currying Function in java?**

		Function Currying is a concept of breaking a function with many arguments into many functions with single argument in such a way, that the output is same.

**9. Is it compulsory to use atleast one public class in a java file? **

		Ans: No. Java file have default modifier also and it will compile fine.

**10. Can A class be protected?**

		Ans- No, A normal Class can be public, default, abstract or final. **Inner Classes can be protected or private.**

**11	What is the scope of static Variable?
		Ans- Class level.
		
**12 What are native methods in java**
	A native method is a Java method whose implementations are written in another language such as C/C++.
	
**13. 	Does finally block always execute? Will finally block execute when System.exit is called?**

		Ans- finally is not executed when System.exit is called, finally block is also not executed when JVM crashes because of some java.util.Error. 
		The only times finally won't be called are:
		1.if you call System.exit()
		2. if the JVM crashes first
		3. if there is an infinite loop in the try block
		4. if the power turns off

**14. 	 What is cloning in java?**

		Cloning is done for **copying the object**, cloning can be done using shallow or deep copy.
		1) Definition of clone method -
			protected native Object clone() throws CloneNotSupportedException;
			Clone is a protected method - clone method can’t be called outside class without inheritance.
			Clone is native method, if not overridden its implementation is provided by JVM.
			It returns Object - Means explicitly cast is needed to convert it to original object.
		2)  By default clone method do shallow copy.
		3)  Class must implement marker interface java.lang.Cloneable. If class doesn’t implement Cloneable than calling clone method on its object will throw CloneNotSupportedException.
		
		[ShallowVsDeepCopy](https://www.javamadesoeasy.com/2015/05/cloning-in-java-using-clone-shallow-and.html )
		
	**Shallow Copy**- A shallow copy of an object copies the **‘main’ object, but doesn’t copy the inner objects.** The ‘inner objects’ are shared between the original object and its copy.
					The default implementation of the clone method creates a shallow copy of the source object.
```java
	public class Person {
		private Name name;
		private Address address;

    public Person(Person originalPerson) {
         this.name = originalPerson.name;
         this.address = originalPerson.address;
    	}
	}
	
```	
	The problem with the shallow copy is that the two objects are not independent. If you modify the Name object of one Person, the change will be reflected in the other Person object.	
	
```java
Person mother = new Person(new Name(…), new Address(…));
[…]
Person son  = new Person(mother);
[…]
son.moveOut(new Street(…), new City(…));
```
	This occurs because our mother and son objects share the same Address object, as you can see in above example . When we change the Address in one object, it changes in both!
	
	**Deep Copy**- A deep copy is a fully independent copy of an object.  If we copied our Person object, we would copy the entire object structure.
	A change in the Address object of one Person wouldn’t be reflected in the other object
```java
public class Person {
    private Name name;
    private Address address;

    public Person(Person otherPerson) {
         this.name    =  new Name(otherPerson.name);
         this.address =  new Address(otherPerson.address);
    }
[…]
}
```	
	For Primitive value, it is simple, a value that can't be shared, So By creating second instance variable, we are automatically creating an independent copy.	
		
**15	What is reflection in java? Have you ever used reflection directly or directly?**

		Reflection is used to load java classes at runtime.
		Frameworks like struts, spring and hibernate uses reflection for loading classes at runtime.
	
**16 	Difference between inner class ans static innner class?
		Inner Class requires Instance of Outer Class for Initilization and they are always associated with Instance of enclosing class. While Static Inner Class is not associated with enclosing class.
		
		// accessing a static nested class 
        OuterClass.StaticNestedClass nestedObject = new OuterClass.StaticNestedClass(); 
		
		// accessing an inner class 
        OuterClass outerObject = new OuterClass(); 
        OuterClass.InnerClass innerObject = outerObject.new InnerClass(); 

		
**17	Difference between Syncronized block and static syncronized block?

		Synchronization in Java is basically an implementation of monitors .
		When synchronizing a non static method, the monitor belongs to the instance. When synchronizing on a static method , the monitor belongs to the class. 


## OOPS 
**1. Principal concepts of OOPS?**
		Ans:- [Ans](https://github.com/Ashu-hub/Books-to-Prepare-Oracle-Java-Certification-Exams/blob/master/interview_questions_java.md#general-answers )

**2. Can We Overload a function by changing only return Type?**
		Ans: [Yes](https://github.com/Ashu-hub/Books-to-Prepare-Oracle-Java-Certification-Exams/blob/master/interview_questions_java.md#general-answers )
		Overloaded method can change the return type.

**3. What is Encapsulation and give one example?**

		Ans: Encapsulation is defined as the wrapping up of data under a single unit. 
				OR 
		It is a protective shield that prevents the data from being accessed by the code outside this shield.
		Technically in encapsulation, the variables or data of a class is hidden from any other class and can be accessed only through any member function of own class in which they are declared.
		As in encapsulation, the data in a class is hidden from other classes using the data hiding concept which is achieved by making the members or methods of class as private and the class is exposed to the end user or the world without providing any details behind implementation using the abstraction concept, 
		so it is also known as combination of data-hiding and abstraction..
		Encapsulation can be achieved by: **Declaring all the variables in the class as private and writing public methods in the class to set and get the values of variables.**

**4. What is Data Hiding?**

		The user will have no idea about the inner implementation of the class. 
		It will not be visible to the user that how the class is storing values in the variables. He only knows that we are passing the values to a setter method and variables are getting initialized with that value.
		How do you do that?- By using Access modifiers.

**5. What is Abstraction in Java?**

		Ans:- Showing the Essential Feature and not showing the non- essential features to the world/user.
		How to acheive it? -> In java, abstraction is achieved by interfaces and abstract classes. We can achieve 100% abstraction using interfaces.
			An abstract class can have parametrized constructors and default constructor is always present in an abstract class.
		Why can't we have instance of Abstract Class?
		in Java, an instance of an abstract class cannot be created, we can have references of abstract class type though.

**6. Can Abstract classes have final methods?**
		Yes, Consider this:-

```java
		// An abstract class with a final method 
		abstract class Base { 
			final void fun() { System.out.println("Derived fun() called"); } 
		} 

		class Derived extends Base {} 

		class Main { 
			public static void main(String args[]) { 
			Base b = new Derived(); 
			b.fun(); 
			} 
		} 
		//Output: Derived fun() called
```

**7. Is it possible to create abstract and final class in Java?** - No, Final and Abstract Can't be used Together.

**8. Is it possible to have an abstract method in a final class?** -  No, for any abstract method inside class, the class must be abstract.

**9. Is it possible to inherit from multiple abstract classes in Java?** - NO,Java does't support multiple inheritance in the classes. Cause for ambiguity

**10. What is Inheritance in Java and how to acheive it?**

		Ans: It is the mechanism by which one class acquire the property of another class.
		It can be acheive by using **extends** or **implements** keyword.

**11. what is the difference between super and super()?**

	| 	super																										| 	super()		|
	| 	---------------																								|:--------------:|
	|	The super keyword in Java is a reference variable that is used to refer parent class objects.				|	The super() in Java is a reference variable that is used to refer parent class constructors.|
	| 	super can be used to call parent class’ variables and methods.												| 	super() can be used to call parent class’ constructors only.								|
	| 	The variables and methods to be called through super keywordd can be done at any time.						| 	Call to super() must be first statement in Derived(Student) Class constructor.|
	| 	If one does not explicitly invoke a superclass variables/methods, by using super keyword,nothing happens	| 	If a constructor does not explicitly invoke a superclass constructor by using super(), the Java compiler automatically inserts a call to the no-argument constructor of the superclass.|

**12. What is Association, Composition, and Aggregation in Java. Give example**

		Association:-
		Association is relation between two separate classes which establishes through their Objects. Association can be one-to-one, one-to-many, many-to-one, many-to-many.
		In Object-Oriented programming, an Object communicates to other Object to use functionality and services provided by that object. Composition and Aggregation are the two forms of association.
		
		Composition:-
		Composition is a restricted form of Aggregation in which two entities are *highly dependent on each other.*
		It represents **part-of relationship.**
		In composition, both the entities are dependent on each other.
		When there is a composition between two entities, the composed object **cannot exist without the other entity**.
		
		Aggregation
		It is a special form of Association where:
		It represents **Has-A relationship.**
		It is a unidirectional association i.e. a one way relationship. For example, department can have students but vice versa is not possible and thus unidirectional in nature.
		In Aggregation, both the **entries can survive individually** which means ending one entity will not effect the other entity
		eg:
		[AggregationandCompostionExample](https://github.com/Ashu-hub/All-About-Java/tree/master/InterviewQuestionExplanation/src/JavaOverview/aggregationComposition)
		
**13. Why to favor composition over Inheritance?**		

	1. One reason of favoring Composition over Inheritance in Java is fact that Java doesn't support multiple inheritance.
		Since you can only extend one class in Java, but if you need multiple functionality like e.g. for reading and writing character data into file, 
		you need Reader and Writer functionality and having them as private members makes your job easy. That’s called composition.
	2. When behavior of super class changes, functionality in sub class may get broken, without any change on its part; by using composition, one can avoid such fragility.
	3. Composition offers better test-ability of a class than Inheritance.
	
	When to use What?
	Does TypeB want to expose the complete interface (all public methods no less) of TypeA such that TypeB can be used where TypeA is expected? Indicates **Inheritance.**
	Does TypeB want only some/part of the behavior exposed by TypeA? Indicates need for **Composition.**

## String
**1. Where does String Pool resides in memory?**
		Ans:- From java 7 String pool is a storage area in java heap memory, where all the other objects are created. 
				Prior to Java 7 String pool was created in permgen space of heap. 
		[Refer this](https://github.com/Ashu-hub/All-About-Java/blob/master/images/StringConstantPool.png)

**3. 	What does intern() do?**

		When the intern method is invoked, if the string pool already contains a string equal to this String object( as determined by the equals(Object) method,) then the string from the pool is returned. 
		Otherwise, this String object is added to the string pool and a reference to this String object is returned.
		
		eg:- 
		String s1 = "abc";    
		"abc" is created in string pool i.e. String object is added to the string pool		
		String s5 = new String("abc").intern();  
		What happens internally When above statement is executed ?   
		When the intern method is invoked, if the string pool already contains a string equal to this String object will be determined by the equals(Object) method.
		As “abc” already exists in string pool (Because of  String s1 = "abc").
		So, "abc".equals("abc") will return true and s5 will be a reference variable which will refer to "abc" in string pool.

		**So, s1 == s5 will always return true.**
```java
	  String s1 = new String("GFG"); // Line-1  
  
        // S2 refers to Object in SCP Area 
        String s2 = s1.intern(); // Line-2  
          
        // Comparing memory locations 
        // s1 is in Heap 
        // s2 is in SCP 
        System.out.println(s1 == s2); 
          
        // Comparing only values 
        System.out.println(s1.equals(s2)); 
          
        // S3 refers to Object in the SCP Area  
        String s3 = "GFG"; // Line-3  
  
        System.out.println(s2 == s3);
		
		//o/p :- 
		false
		true
		true
````	
**4.	Why String pool in java?**

		Ans: Save memory and increase performance. less number of strings are created in java heap and hence leaving less work for garbage collector to be done.

**2.	What does Immuatable means?**

		Ans:- It means Constant. Once Created can not be changed. In java, objects of String are immutable. Immutability is useful in MultiThreadng.
		
**5.	Create a immutable class?**
		Immutable simply means unmodifiable or unchangeable.
		Like Once string object is created its data or state can't be changed but a new string object is created.
		
		1. Make Class as FINAL.
		2. Make class variable as private- so that it cant be accessed outside the world.
		3. Constructor:- to iniitalized all class variables
		4. Don't provide setter methods in class/ provide only getter methods.
		6. Object of immutable class - Any change made to object of immutable class produces new object.
   			object of mutable class -  Any change made to object of mutable class doesn't produces new object.
			 - **Integer, String** are immutable class, 
				   any changes made to object of these classes produces new object.
				 so return reference variable of Integer.
			 - **HashMap** is mutable class, 
				   any changes made to HashMap object won't produce new HashMap object.
				   so return copy/clone of object, not reference variable of HashMap.
		
		**Passing Mutable Objects to Immutable Class**
		1. Apart from above creteria, Use clone Object in the constructor for initializing other class reference object.
		2. For getting the object reference return cloned object.
		
```java
public final class ImmutableStudent {
    private final int id;
    private final String name;
    private final Age age;
    
	public ImmutableStudent(int id, String name, Age age) {
		this.name = name;
		this.id = id;
		//this.age = age;
	
	Age cloneAge = new Age();
		cloneAge.setDay(age.getDay());
		cloneAge.setMonth(age.getMonth());
		cloneAge.setYear(age.getYear());
		this.age = cloneAge;
    
	}
	
    public int getId() {
    	return id;
    }
    public String getName() {
	    return name;
    }
	
	
	public Age getAge() {
		Age cloneAge = new Age();
		cloneAge.setDay(this.age.getDay());
		cloneAge.setMonth(this.age.getMonth());
		cloneAge.setYear(this.age.getYear());
		return cloneAge;
	}

   /* public Age getAge() {
    return age;
    }*/
}
```

	Advantages of using immutable objects:- 
		Thread safety
		Prevention of identity mutation
		Better encapsulation
		Simpler to test
		
**6.	What is difference between final vs Immutability in Java?**

		final means that you can’t change the object’s reference to point to another reference or another object, but you can still mutate its state (using setter methods e.g). 
		Whereas immutable means that the object’s actual value can’t be changed, but you can change its reference to another one.
		final ensures that the address of the object remains the same whereas the Immutable suggests that we can’t change the state of the object once created.
		[FinalVsImmutable](https://github.com/Ashu-hub/All-About-Java/blob/master/InterviewQuestionExplanation/src/JavaOverview/FinalVsImmutable.java )
		
	
		
**7. 	What is the difference between + and concat()?**

		1. Number of arguments the concat() method and + operator takes:
		
			concat() method takes only one argument of string and concat it with other string.
			+ operator takes any number of arguments and concatenates all the strings.
```java
public class GFG { 
	public static void main(String[] args) 
	{ 
		String s = "Geeks", t = "for", g = "geeks"; 

		System.out.println(s + t + g); 
		System.out.println(s.concat(t)); 
	} 
} 
```			
		2. Type of arguments :
		
		concat() method takes only string arguments, if there is any other type is given in arguments then it will raise an error.
		+ operator takes any type and converts to string type and then concatenates the strings.
		
		
		3. concat() method raises java.lang.NullPointer Exception
		
		concat() method throws NullPointer Exception when string is concatenated with null
		+ operator did not raise any Exception when the string is concatenated with null.

	```java
		public class GFG { 
		public static void main(String[] args) 
		{ 
			String s = "Geeks"; 
			String r = null; 
			System.out.println(s + r); 

			// It raises an NullPointer Exception 
			System.out.println(s.concat(r)); 
		} 
	} 

	```
		
		4. Creates a new String object
		
		concat() method takes concatenates two strings and return new string object only string length is greater than 0, otherwise it returns same object.
		+ operator creates a new string object every time irrespective of length of string

```java
	public class GFG { 
		public static void main(String[] args) 
		{ 

			String s = "Geeks", g = ""; 
			String f = s.concat(g); 
			if (f == s) 
				System.out.println("Both are same"); 
			else
				System.out.println("not same"); 
			String e = s + g; 
			if (e == s) 
				System.out.println("Both are same"); 
			else
				System.out.println("not same"); 
		} 
	} 

```

**8. What is difference between String, StringBuffer and StringBuilder in java ?**

		Ans:- StringBuilder is not Thread Safe, StringBuffer is Thread Safe, String is Thread Safe

## Collections

**1. 	What is fail-fast and fail-safe?**
		
		Ans- 
		Iterator returned by few Collection framework Classes are fail-fast, means any structural modification made to these classes during iteration will throw ConcurrentModificationException in java.

		Iterator returned by few Collection framework Classes are fail-safe, means any structural modification made to these classes during iteration won’t throw any Exception in java.
		
		
		[ConcurrentModificationArrayListExample](https://github.com/Ashu-hub/All-About-Java/tree/master/InterviewQuestionExplanation/src/JavaOverview/collection/ConcurrentModificationArrayListExample)
		
		
		[ConcurrentModificationCopyOnWriteArrayListExample](https://github.com/Ashu-hub/All-About-Java/tree/master/InterviewQuestionExplanation/src/JavaOverview/collection/ConcurrentModificationCopyOnWriteArrayListExample)
		
**2.	what will happen if add() and remove() methods are called subsequently in java? Will Exception be avoided? Like:- **

```java
 while(iterator.hasNext()){
	         //iterator.remove(); will throw IllegalStateException
	         String str = iterator.next();
	         System.out.print(str+" ");
	         
	         if(str.equals("b"))
	           list.add("b2");   //will throw ConcurrentModificationException
	         	list.remove("b2");
	        }
```
		Ans:-
		No, Subsequent calls made to add() and remove() methods won’t avoid ConcurrentModificationException. Because in this case modCount will be incremented twice by 1.

**3. 	Which method does not cause structural modifications in java?**

		set().

**4. 	How CopyOnWriteArrayList not thorw concurrentModificationException?**

		In CopyOnWriteArrayList cursor is maintained on snapshot(it is copy of original CopyOnWriteArrayList), rather than on list as in case of ArrayList
		Because when list.iterator() is called, a variable called snapshot is created which is copy of list (not the original list).
		Hence, iteration does not care about structural modifications made to list.
		
**5. 	What are classes which as fail-fast?**

		ArrayList
		LinkedList
		vector
		HashSet
		EnumSet
		LinkedHashSet
		TreeSet
		
		The iterators returned by the iterator() method of the collections returned by all three Map's “collection view methods" are fail-fast >
		map.keySet().iterator(), map.values().iterator(), map.entrySet().iterator()
		
		HashMap
		Hashtable
		LinkedHashMap
		TreeMap
		IdentityHashMap
		
**6. 	What are classes which as fail-safe?**
		
		Ans:- 
		
		CopyOnWriteArrayList
		CopyOnWriteArraySet
		ConcurrentSkipListSet
		
		The iterators returned by the iterator() method of the collections returned by all three Map's “collection view methods" are fail-safe >
		map.keySet().iterator(), map.values().iterator(), map.entrySet().iterator()
		
		ConcurrentHashMap
		ConcurrentSkipListMap
		
**7.	How ConcurrentHashMap works? Can 2 threads on same ConcurrentHashMap object access it concurrently?**
	
		Ans: ConcurrentHashMap is divided into different segments based on concurrency level. So different threads can access different segments concurrently in java.
		
		Can threads read the segment locked by some other thread?
		Yes. When thread locks one segment for updation it does not block it for retrieval (done by get method) hence some other thread can read the segment (by get method),
		but it will be able to read the data before locking in java.
		
		[ConncurrantHashMap](https://github.com/Ashu-hub/All-About-Java/blob/master/images/ConncurrantHashMap.png )

**8 	Satck vs ArrayDeque?**
		
**9		why-hashtable-does-not-allow-null-keys-or-values?**
		
		To successfully store and retrieve objects from a Hashtable, the objects used as keys must implement the hashCode method and the equals method.
		Since null isn't an object, you can't call .equals() or .hashCode() on it, so the Hashtable can't compute a hash to use it as a key.
		While In hashMap, Hash(null) = 0
		
**Q		Why ConcurrentHashMap does not allow null?
		
		The main reason that nulls aren't allowed in ConcurrentMaps (ConcurrentHashMaps, ConcurrentSkipListMaps) is that ambiguities that may be just barely tolerable in non-concurrent maps can't be accommodated. 
		The main one is that if map.get(key) returns null, you can't detect whether the key explicitly maps to null vs the key isn't mapped. 
		In a non-concurrent map, you can check this via map.contains(key), but in a concurrent one, the map might have changed between calls.
		
**10	Internal Working OF TreeMap?**
		
		TreeMap Stores elements as Key Value Pair. It extends AbstractMap, implements NavigableMap, cloneable and Serializable.
		TreeMap Sotores the elements in Sorted manner(natural sorting order). 
		TreeMap does not using hashing technique for storing the elements, while it uses **RED- BLack Tree** for storing elements.
		**Red Black Tree** Has node str as - 
		Parent - key - value pair
		Left - all elemtns smallar then root
		Right - all elements greater then root

**11	Can I instantiate Abstract class and Does it have constructor?  **

		No, You can't. It has constructors(either default or parametrized). Reason for this is Constructor Chaining, the constructor of  Subclass class invokes the constructor of Base Class. It is imperative that all classes has constructors.

**12 	What is difference between using instanceOf operator and getClass() in equals method?**

		If we use instanceOf it will return true for comparing current class with its subclass as well,
		but getClass() will return true only if exactly same class is compared. Comparison with any subclass will return false.		
		
**13 Why does iterator.remove does not throw ConcurrentModificationException?**

	Ans:- Javadoc says it is permitted way to modify any collection while Iterating.
	Reason:- before callng .remove(int), remove() checks for modification, if it found any change in original list it will throw ConcurrentModificationException otherwise it will ececute normally. in a way it has reference to internal State of any object.

**14 Assertion Error is SubClass of Error? Why is it so?**

**15 When StackOverFlow Error can come?**- I think when the stack is full and there is no other location to store new variable. Generally in case of Recurssion.

**16 What is Automatic Resource Management?  Order of calling to this.close()?

	Ans: Java provides a feature to make the code more robust and to cut down the lines of code, this is known as Try with Resource. The try-with-resources statement is a try statement that declares one or more resources.
		Avaiable after 1.7 version.
  (a) What is a resource?
	Ans: A resource is an object that must be closed after the program is finished using it. Any object that implements java.lang.AutoCloseable, which includes all objects which implement java.io.Closeable, can be used as a resource.
		eg.
		```java
		static String readFirstLineFromFile(String path) throws IOException
{
    try (BufferedReader br = new BufferedReader(new FileReader(path)))
    {
        return br.readLine();
    }
}
```
**17 What is effective final?**

	Ans. Def.- Objects or primitive values are effectively final if we do not change their values after initialization.
	 if we remove the final modifier from a method parameter or a local variable without introducing compile-time errors, then it's effectively final
	```java
	@FunctionalInterface
	public interface FunctionalInterface {
		void testEffectivelyFinal();
		default void test() {
			int effectivelyFinalInt = 10;
			FunctionalInterface functionalInterface 
				= () -> System.out.println("Value of effectively variable is : " + effectivelyFinalInt);
		}
}
```
**18 why does generics does not support primitive type?**

	Object is superclass of all objects and can represent any user defined object. Since all primitives doesn't inherit from "Object" so we can't use it as a generic type.

**19. What is the difference between Serialization and Externalization.
	Serialization - To serialize an object means to convert its state to a byte stream so that the byte stream can be reverted back into a copy of the object.
	Externalization - Externalization in Java is used whenever you need to customize the serialization mechanism.
	
	1. Serializable is a marker Interface. while Externalizable is having 2 mehotds called writeExternal(), readExternal()
	2. Default Serialization will take place for classes implementing Serializable interface, while programmer needs to defined the Serilization process for the classes.
	3. Serializable uses reflection to construct object and does not require no arg constructor. But Externalizable requires public **no-arg constructor.**
	
**20. Discuss Object Serialization with Inheritence in java.
		
	Case1: **If superclass is serializable then subclass is automatically serializable.**
	Case2: **If a superclass is not serializable then subclass can still be serialized :**
	 Even though superclass doesn’t implements Serializable interface, we can serialize subclass object if subclass itself implements Serializable interface.
			Serialization: At the time of serialization, if any instance variable is inheriting from non-serializable superclass, then JVM ignores original value of that instance variable and save default value to the file.
			De-Serialization: At the time of de-serialization, if any non-serializable superclass is present, then JVM will execute instance control flow in the superclass. To execute instance control flow in a class, JVM will always invoke default(no-arg) constructor of that class. 
			So every **non-serializable superclass must necessarily contain default constructor,** otherwise we will get runtime-exception.
			
	Case3:	**If the superclass is serializable but we don’t want the subclass to be serialized**
			There is no direct way to prevent subclass from serialization in java. One possible way by which a programmer can achieve this is by implementing the **writeObject() and readObject()** methods in the subclass and needs to throw NotSerializableException from these methods. 
			These methods are executed during serialization and de-serialization respectively. By overriding these methods, we are just implementing our own custom serialization.
	```java
	private void writeObject(ObjectOutputStream out) throws IOException 
	{ 
		throw new NotSerializableException(); 
	}
	```
	[Ref](https://www.geeksforgeeks.org/object-serialization-inheritance-java/)
	
**	What is Custom Serialization in java?
	During serialization, there may be data loss if we use the ‘transient’ keyword. ‘Transient’ keyword is used on the variables which we don’t want to serialize. But sometimes, 
	it is needed to serialize them in a different manner than the default serialization (such as encrypting before serializing etc.), in that case, we have to use custom serialization and deserialization.
	
**21	What is ShallowVsDeepCopy	**
	Ans- See Above
	
**22 What is equals and hascode Contract?**
		
		It says that- If two objects are equal according to the equals(Object) method, then calling the hashCode method on each of the two objects must produce the same integer result.

**22a Why Immutable classes are prefereable as a key in Hashmap?**

 	Immutability allows you to get same hash code every time, for a key object. Also These classes must honor the equals and hashCode Contract.
	If immutable, the object's hashcode wont change and it allows caching the hashcode of different keys which makes the overall retrieval process very fast. Also for mutable objects ,the hashCode() might be dependent on fields that could change, if this happens you wont be able to find the key (and its value) in the HashMap since hashCode() returns different value.

**23 Discuss Generics in java?**
	
	**Why to Choose Generics?**
	1. Generics add stronger compile time type safety to our code, thus reducing the number of production bugs.
	2. It tries to eliminate explicit type casting to a greater extent, making our code more readable.
	3. Generics enable types (classes and interfaces) to be parameters when defining classes, interfaces and methods.
	
	eg:- class Classname<T1, T2, ..., Tn> { /* ... */ }
	
	**Can we add a Double value to List<Number> ?**
		Because an Integer is a kind of Number, so this is perfectly allowed due to inheritance.
		Box<Number> box = new Box<Number>();
		box.add(new Integer(10)); // OK
		box.add(new Double(10.1)); // OK

	**Why List<String> can not be assigned to List<Object> ?**
		Let's consider the following example,
			List<String> stringList =null ;
			List<Object> objectList = stringList; //ERROR
	
	**What is Type Erasure ?**
	Generics provide compile time safety to our Java code. Type erasure happens at compile time, to remove
	those generic type information from source and adds casts needed and deliver the byte code. Thus the java
	byte code will be no different than that the non-generic Java code
		
	**What are Upper and Lower bounds in Generics? Where to choose one?**
	Upper and Lower bounded wildcard are used in Generics to relax the restriction on a variable.
	
	Upper Bounded Wildcards:
		Upper bounded wildcard restricts the unknown type to be a specific type or
		subtype of that type. For example, If we want to write a method that accepts
		List<Number> and its subtypes i.e. List<Double> and List<Integer>, etc then
		we can use Upper bounded wildcard. Below is the sample signature of upper
		bounded wildcard method.
		
		public static void process(List<? extends Number> list) { /* ... */ }
		
	Lower Bounded Wildcards:
		Lower bounded wildcard restricts the unknown type to be a specific type or
		super type of that type. Say you want to write a method that puts Integer
		objects into a list. To maximize flexibility, you may like the method to work on
		List<Integer>, List<Number>, but not List<Double> - 
		anything that can hold Integer values. The Syntax in that case would be -
		
		public static void addNumbers(List<? super Integer> list) {/*.....*/}
	
**24 Difference between HashMap and WeakHashMap?
	
	1) Strong Ref/ Weak Reference:- 
	In HashMap, Key Obejcts has Strong Reference.
	 This is the default type/class of Reference Object. Any object which has an active strong reference are not eligible for garbage collection.
	 
	In WeakHashMap, Key Obejcts has Weak Reference.
	Weak Reference Objects are not the default type/class of Reference Object and they should be explicitly specified while using them.
	
	2) Role Of Garbage collection:-
	In HashMap , entry object(entry object stores key-value pairs) is not eligible for garbage collection i.e Hashmap is dominant over Garbage Collector.
	In WeakHashmap, when a key is discarded then its entry is automatically removed from the map , in other words, garbage collected.
	
	3) Clone Method Implementation:-
	HashMap implements Cloneable interface .
	WeakHashMap does not implement Cloneable interface , it only implements Map interface. Hence , there is no clone() method in the WeakHashMap class.
	
**Q) How to create demeon Thread?** 
	
	It can be created by using setDaemon(boolean isDaemon) method to true.

```java
	 Thread daemonThread = new Thread(daemonRunner);
        daemonThread.setDaemon(true);
        daemonThread.start();
```


**Q) Why to choose generics?**
	Generics gives strong complie-time type Safety to our code. Eliminated explicit type casting and make more readable.
	Generics allow classes and Interfaces to be paramterized , so that we can implement generic algorithm which works for different types.
	eg: className<T1,T2....Tn>
	
**Q) Why List<String> can not be assigned to List<Object> ?**
	List<String> stringList =null ;
	List<Object> objectList = stringList; //ERROR	
	Here List<String> is not a sub type of List<Object>, hence as per inheritance rule, this is not allowed.

**Q) Inheritance in Generics - Can we assign List<String> to Collection<String> ?**
	If the type parameter is same, then we can very well extend the classes as per Java Inheritance rules. So it is
	perfectly legal to assign List<String> to Collection<String> reference.
	
**Q) What is Type Erasure ?**
	Generics provide compile time safety to our Java code. Type erasure happens at compile time, to remove
	those generic type information from source and adds casts needed and deliver the byte code. Thus the java
	byte code will be no different than that the non-generic Java code

**Q) Oracle JDK Vs OpenJDK?**
	Release Schedule:
	Oracle will deliver releases every three years.
	OpenJDK will be released every six months.
	
	Licenses:-
	Public updates for Oracle Java SE 8 released after January 2019 will not be available for business, commercial, or production use without a commercial license. 
	However, OpenJDK is completely open source and can be used it freely.
	
	Oracle JDK 11 will continue to include installers, branding, and JRE packaging, whereas OpenJDK builds are currently available as zip and tar.gz files.
	The output of the java –version and java -fullversion commands will distinguish Oracle's builds from OpenJDK builds
	

**Q) How to implement case insensitive map?**
	like -
	Map m = new MyMap();
	m.put("Russia", "VALUE");
	m.get("russiA"); --> this should return "VALUE". If we use HashMap instead of MyMap it will return null
	
```java	
	import java.util.HashMap;

	public class CaseInsensitiveMap extends HashMap<String, String> {

    @Override
    public String put(String key, String value) {
       return super.put(key.toLowerCase(), value);
    }

    // not @Override because that would require the key parameter to be of type Object
    public String get(String key) {
       return super.get(key.toLowerCase());
    }
	}	
```
	OR
```java
	Map<String, String> nodeMap = 
		new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
```

**Q) Compare IO vs NIO**
	IO:-
	Java IO stands for Java Input Output
	Java IO operates inside java.io package
	Java IO is Stream oriented
	Blocking IO operation
	Channels are not available
	It deals with data in stream
	Does not contain the concept of Selectors
	
	NIO:-
	Java NIO stands for Java New Input Output
	Java NIO operates inside java.nio package
	Java NIO is Buffer oriented
	Non-blocking IO operation
	Channels are available
	It deals with data in blocks
	Contains the concept of Selectors
	[Ref](https://www.geeksforgeeks.org/difference-between-java-io-and-java-nio/)
	
**Q) When to throw checked Exception and when to throw Unchecked Exception?
	If a client can reasonably be expected to recover from an exception, make it a checked exception. 
	If a client cannot do anything to recover from the exception, make it an unchecked exception.
	
	Checked Exception **must** be handled.
	like FileNotFoundException 
	
	Handling unchecked exceptions is not mandatory
	like NullPointerException.
	
	[Ref](https://howtodoinjava.com/java/exception-handling/throw-vs-throws/)
	
**Q) Java  11 features?**
	1. New String methods: -
		isBlank() return boolean, 
		lines():return a collection of strings which are divided by line terminators
		repeat(n): repeats the given String n times.
		strip() : remove the white-spaces which are in-front and back of the string
		
	2. Optional.isEmpty(): This method returns true if the value of any object is null and else returns false.
	
	3. Local-Variable Syntax for Lambda Parameters:
		JDK 11 allows ‘var’ to be used in lambda expressions.
		 IntStream.of(1, 2, 3, 5, 6, 7) 
               .filter((var i) -> i % 2 == 0) 
               .forEach(System.out::println); 
	
	4. Epsilon Garbage Collector: 
	This handles memory allocation but does not have actual memory reclamation mechanism. Once the available Java heap is exhausted, JVM will shut down.
		Its goals are:-
		Performance testing
		Memory pressure testing
		last drop latency improvements
		
**Q) Java  14 features?**	
	
	1. Switch Expressions :-
	eg : 
```java
	boolean isTodayHoliday = switch (day) {
    case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> false;
    case "SATURDAY", "SUNDAY" -> true;
    default -> throw new IllegalArgumentException("What's a " + day);
}```
	
	2. Text Blocks:
	text blocks now have two new escape sequences:-
	\	: to indicate the end of the line, so that a new line character is not introduced
	\s	: to indicate a single space
	
	**3. Now Java has made this easier by adding the capability to point out what exactly was null in a given line of code.**
	For example, consider this simple snippet:
```java
	int[] arr = null;
	arr[0] = 1;
	```
	Earlier, on running this code, the log would say:
	
```java
	Exception in thread "main" java.lang.NullPointerException
	at com.baeldung.MyClass.main(MyClass.java:27)
	```
	But now, given the same scenario, the log might say:
```java
	java.lang.NullPointerException: Cannot store to int array because "arr" is null
	```
	
	4. Concurrent Mark Sweep (CMS) Garbage Collector (JEP 363) – deprecated by Java 9, 
	this GC has been succeeded by **G1 as the default GC.** Also, there are other more performant alternatives to use now, such as ZGC and Shenandoah, hence the removal
	
**Q) Java 16 features?**
	
	1. Text block: 
		is to provide clarity by way of minimizing the Java syntax required to render a string that spans multiple lines.
	```java 
	// ORIGINAL
	String message = "'The time has come,' the Walrus said,\n" +
                 "'To talk of many things:\n" +
                 "Of shoes -- and ships -- and sealing-wax --\n" +
                 "Of cabbages -- and kings --\n" +
                 "And why the sea is boiling hot --\n" +
                 "And whether pigs have wings.'\n";
				 ```
				 
```java
	// BETTER
	String message = """
    'The time has come,' the Walrus said,
    'To talk of many things:
    Of shoes -- and ships -- and sealing-wax --
    Of cabbages -- and kings --
    And why the sea is boiling hot --
    And whether pigs have wings.'
    """;
```