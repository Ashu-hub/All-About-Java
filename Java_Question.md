	These Questions may look bogus, as it is primarly from my mind. 
Q) Why does iterator.remove does not throw ConcurrentModificationException?
	Ans:- Javadoc says it is permitted way to modify any collection while Iterating.
Reason:- before callng .remove(int), remove() checks for modification,ifit found any change in original list it will throw ConcurrentModificationException otherwise it will ececute normally. in a way it has reference to internal State of any object.

Q) Assertion Error is SubClass of Error? Why is it so?
Q) When StackOverFlow Error can come?- I think when the stack is full and there is no other location to store new variable. Generally in case of Recurssion.
Q) What is Automatic Resource Management?  Order of calling to this.close()?
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
Q) What is effective final?
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
Q) why does generics does not support primitive type?
	Object is superclass of all objects and can represent any user defined object. Since all primitives doesn't inherit from "Object" so we can't use it as a generic type.

Q) What is Type References in java?
	Ans. 
