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

		Ans: Brfore Java 7, it is not mandatory to write main(). You canhave full code inside static block and run that,
		However after Java 7, main() is compulsory in java, if not presenet ,Complier will throw an error saying- 'main method not found in the class'

**4.  Explain Implicit casting/promotion of primitive Data type in java?**

		Ans:- 

**5. What is Differences between Instance initialization block and Static initialization block?**

**Static initialization block:-**

		Static blocks are also called Static initialization blocks in java.
		Static block executes when class is loaded in java.
		static blocks executes before instance blocks in java.
		Only static variables can be accessed inside static block in java
		static blocks can be used for initializing static variables or calling any static method in java.
		this keyword cannot be used in static block in java.

**Instance initialization block:-**

		Instance blocks are also called instance initialization blocks in java
		Instance block executes when instance of class is created in java.
		Also known as non-static initialization block in java.
		instance blocks executes after static blocks in java.
		Static and non-static variables (instance variables) can be accessed inside instance block in java.
		instance blocks can be used for initializing instance variables or calling any instance method in java.
		this keyword can be used in instance block in java.

		Flow of Calling:- Static Blocks-> Instance blocks-> Construtors

**6. How to define your own data type in java?**

		Ans:- Using enums

**7. Can we use '_' as a variable name in java?**

		Ans:- Till java 8 yes, from java 9 -NO

**8. What is Currying Function in java?**

		Function Currying is a concept of breaking a function with many arguments into many functions with single argument in such a way, that the output is same.

**9. Is it compulsory to use atleast one public class in a java file? **

		Ans: No. Java file have default modifier also and it will compile fine.

**10. Can A class be protected?**

		Ans- No, A normal Class can be public, default, abstract or final. Inner Classes can be protected or private.

**11	What is the scope of static Variable?
		Ans- Class level.
	
**12	What is Cyclomatic Complexity of a code in java?**
		Ans- Cyclomatic complexity of a code section is the quantitative measure of the number of linearly independent paths in it. It is a software metric used to indicate the complexity of a program.
		It is computed using the Control Flow Graph of the program.
		For example, if source code contains no control flow statement then its cyclomatic complexity will be 1 and source code contains a single path in it.
		Similarly, if the source code contains one if condition then cyclomatic complexity will be 2 because there will be two paths one for true and the other for false
		**Formula- M = E – N + 2P**
		where,
		E = the number of edges in the control flow graph
		N = the number of nodes in the control flow graph
		P = the number of connected components
		
		**Use of Cyclomatic Complexity?**
		
		Determining the independent path executions thus proven to be very helpful for Developers and Testers.
		It can make sure that every path have been tested at least once.
		Thus help to focus more on uncovered paths.
		Code coverage can be improved.
		Risk associated with program can be evaluated.

**13. 	Does finally block always execute? Will finally block execute when System.exit is called?**

		Ans- finally is not executed when System.exit is called, finally block is also not executed when JVM crashes because of some java.util.Error. 
		The only times finally won't be called are:
		if you call System.exit()
		if the JVM crashes first
		if there is an infinite loop in the try block
		if the power turns off

**14. 	 What is cloning in java?**

		Cloning is done for copying the object, cloning can be done using shallow or deep copy.
		1) Definition of clone method -
			protected native Object clone() throws CloneNotSupportedException;
			Clone is a protected method - clone method can’t be called outside class without inheritance.
			Clone is native method, if not overridden its implementation is provided by JVM.
			It returns Object - Means explicitly cast is needed to convert it to original object.
		2)  By default clone method do shallow copy
		3)  Class must implement marker interface java.lang.Cloneable. If class doesn’t implement Cloneable than calling clone method on its object will throw CloneNotSupportedException.
		[ShallowCopy](https://github.com/Ashu-hub/All-About-Java/tree/master/InterviewQuestionExplanation/src/JavaOverview/ShallowCopy)
		[DeepCopy](https://github.com/Ashu-hub/All-About-Java/tree/master/InterviewQuestionExplanation/src/JavaOverview/DeepCopy)
		
		[ShallowVsDeepCopy](https://www.javamadesoeasy.com/2015/05/cloning-in-java-using-clone-shallow-and.html)
		
**15	What is reflection in java? Have you ever used reflection directly or directly?**

		Reflection is used to load java classes at runtime.
		Frameworks like struts, spring and hibernate uses reflection for loading classes at runtime.
		
## OOPS 
**1. Principal concepts of OOPS?**
		Ans:- [Ans](https://github.com/Ashu-hub/Books-to-Prepare-Oracle-Java-Certification-Exams/blob/master/interview_questions_java.md#general-answers)

**2. Can We Overload a function by changing return Type?**
		Ans: [Yes](https://github.com/Ashu-hub/Books-to-Prepare-Oracle-Java-Certification-Exams/blob/master/interview_questions_java.md#general-answers)

**3. What is Encapsulation and give one example?**

		Ans: Encapsulation is defined as the wrapping up of data under a single unit. 
				OR 
		It is a protective shield that prevents the data from being accessed by the code outside this shield.
		Technically in encapsulation, the variables or data of a class is hidden from any other class and can be accessed only through any member function of own class in which they are declared.
		As in encapsulation, the data in a class is hidden from other classes using the data hiding concept which is achieved by making the members or methods of class as private and the class is exposed to the end user or the world without providing any details behind implementation using the abstraction concept, so it is also known as combination of data-hiding and abstraction..
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
		Composition is a restricted form of Aggregation in which two entities are highly dependent on each other.
		It represents part-of relationship.
		In composition, both the entities are dependent on each other.
		When there is a composition between two entities, the composed object **cannot exist without the other entity**.
		Aggregation
		It is a special form of Association where:
		It represents Has-A relationship.
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

**2.	What does Immuatable means?**

		Ans:- It means Constant. Once Created can notbe changed. In java, objects of String are immutable.

**3. 	What does intern() do?**

		When the intern method is invoked, if the string pool already contains a string equal to this String object as determined by the equals(Object) method, then the string from the pool is returned. 
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
	
**4.	Why String pool in java?**

		Ans: Save memory and increase performance. less number number of strings are created in java heap and hence leaving less work for garbage collector to be done.

**5.	Create a immutable class?**
		
		1. Make Class as FINAL.
		2. make class variable as private- so that it cant be accessed outside the world.
		3. Constructor:- to iniitalized all class variables
		4. Don't provide setter methods in class/ provide only getter methods.
		6.  object of immutable class - Any change made to object of immutable class produces new object.
   			object of mutable class -  Any change made to object of mutable class doesn't produces new object.
			 - **Integer, String** are immutable class, 
				   any changes made to object of these classes produces new object.
				 so return reference variable of Integer.
			 - **HashMap** is mutable class, 
				   any changes made to HashMap object won't produce new HashMap object.
				   so return copy/clone of object, not reference variable of HashMap.
			
**6.	What is difference between final vs Immutability in Java?**

		final means that you can’t change the object’s reference to point to another reference or another object, but you can still mutate its state (using setter methods e.g). 
		Whereas immutable means that the object’s actual value can’t be changed, but you can change its reference to another one.
		final ensures that the address of the object remains the same whereas the Immutable suggests that we can’t change the state of the object once created.
		eg:- 
	[FinalVsImmutable](https://github.com/Ashu-hub/All-About-Java/blob/master/InterviewQuestionExplanation/src/JavaOverview/FinalVsImmutable.java)
		
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
		
		[ConncurrantHashMap](https://github.com/Ashu-hub/All-About-Java/blob/master/images/ConncurrantHashMap.png)

