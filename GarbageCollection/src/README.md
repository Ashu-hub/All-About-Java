* JVM Allocates memory from the CPU whenever it is initialized. There are two parameters called 
		** -Xms - which starting heap size
		** -Xmx - can request memory upto this size,
		So when eclipse starts is requests for -Xms size and over time it may request more memory untill it reaches -Xmx size.
		If for any reason eclipse needs more memory than -Xmx size that it is goin to crash.
	* Stack or Heap is the part of that particular memory.
	* Java is a managed language, so the garbage collection is automatic(JVM's work).
	* We Can Request JVM to run GC, but it is up to JVM. It might not run GC At all.
	* It might happen that you application Finished(JVM will be destroyed and removed from memory) and the GC never run.
	* [gc defination by Oracle](https:*docs.oracle.com/javase/7/docs/api/java/lang/System.html#gc())

### Q) So where could we pratically use finalize()?
	Ans:- As a check that all resources has been closed.*/
	*eg:-
```java
	public void finalize() {
		if (file.isOpen()) {
			Logger.warn("Is looks like all resources has not been closed.");
		}
	}
```
* What is Unreferenced Objects?
	Those Objects which does not have referenced form Stack. These Objects are elegible for GC.

## Soft Leak:-
An object is referenced from stack even thogh it will never be used again.
[Soft Leak examples]()
In the Above example, you may want to lower the max heap size by including following in vm argument of your run cofiguration:-
```java
-Xmx10m
```
Above line of code wil set max heap size as 10 mb.
By running this, you will able to see **java.lang.OutOfMemoryError: Java heap space

To monitor what's going on in this application as at this point we are not really sure that why this out of memory error comes.
We need to use JVisualM that comes with JDK.