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

		The user will have no idea about the inner implementation of the class. It will not be visible to the user that how the class is storing values in the variables. He only knows that we are passing the values to a setter method and variables are getting initialized with that value.
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
		
		Composition:-
		..*Composition is a restricted form of Aggregation in which two entities are highly dependent on each other.
		..*It represents part-of relationship.
		..*In composition, both the entities are dependent on each other.
		..*When there is a composition between two entities, the composed object cannot exist without the other entity.
		
**13. Why to favor composition over Inheritance?**		