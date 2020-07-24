## Java Overview:-
Q) What is JDK, JRE, JVM?
Ans. 
JDK(Java Development Kit) : JDK is intended for software developers and includes development tools such as the Java compiler, Javadoc, Jar, and a debugger.
JRE(Java Runtime Environment) : JRE contains the parts of the Java libraries required to run Java programs and is intended for end users. JRE can be view as a subset of JDK.
JVM: JVM (Java Virtual Machine) is an abstract machine. It is a specification that provides runtime environment in which java bytecode can be executed. JVMs are available for many hardware and software platforms. 
	JDK = JRE + Development Tools(Java compiler, Javadoc, Jar, debugger.)
	JRE = JVM + Library Classes(JDBC, AWT, JNDI, class library lke rt.jar)
	
Q) Can you brifely tell me about JVM Architecture?
Ans:- Class Loader SubSystem, Different Memory, Execution Engine, Native method interface?

Q) Is main() compulsory in java?
Ans: Brfore Java 7, it is not mandatory to write main(). You canhave full code inside static block and run that,
However after Java 7, main() is compulsory in java, if not presenet ,Complier will throw an error saying- 'main method not found in the class'

Q)  Explain Implicit casting/promotion of primitive Data type in java?
Ans:- 

Q) What is Differences between Instance initialization block and Static initialization block?
Ans: 
**Static initialization block:-
Static blocks are also called Static initialization blocks in java.
Static block executes when class is loaded in java.
static blocks executes before instance blocks in java.
Only static variables can be accessed inside static block in java
static blocks can be used for initializing static variables or calling any static method in java.
this keyword cannot be used in static block in java.

**Instance initialization block
Instance blocks are also called instance initialization blocks in java
Instance block executes when instance of class is created in java.
Also known as non-static initialization block in java.
instance blocks executes after static blocks in java.
Static and non-static variables (instance variables) can be accessed inside instance block in java.
instance blocks can be used for initializing instance variables or calling any instance method in java.
this keyword can be used in instance block in java.

Flow of Calling:- Static Blocks-> Instance blocks-> Construtors

Q) Howo to define your own data type in java?
Ans:- Using enums

Q) Can we use '_' as a variable name in java?
Ans:- Till java 8 yes, from java 9 -NO

LessImpQ) What is Currying Function in java?
Function Currying is a concept of breaking a function with many arguments into many functions with single argument in such a way, that the output is same.

## OOPS 
Q) 