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
	
# Garbage Collection
In java, Programmer need not to care about destruction of objects, as it is taken care automatically. 
Garbage Collection is a best example of Deamon Thread, that runs in the background. Main objective of GC is to free memory by deleting unreachable objects.
*Unreachable objects*- Are those objects which does not contain any reference to it. Objects which are part of **Island of Isolation** is also unreachable objects.
**Island of Isolation** - Object 1 references Object 2 and Object 2 references Object 1. Neither Object 1 nor Object 2 is referenced by any other object. That’s an island of isolation.
Basically, an island of isolation is a group of objects that reference each other but they are not referenced by any active object in the application. Strictly speaking, even a single unreferenced object is an island of isolation too.
eg:-
```java
public class Test 
{ 
    Test i; 
    public static void main(String[] args)   
    { 
        Test t1 = new Test(); 
        Test t2 = new Test(); 
           
        // Object of t1 gets a copy of t2 
        t1.i = t2; 
       
        // Object of t2 gets a copy of t1 
        t2.i = t1; 
        // Till now no object eligible 
        // for garbage collection  
        t1 = null; 
        //now two objects are eligible for 
        // garbage collection  
        t2 = null; 
        // calling garbage collector 
        System.gc();
    } 
    @Override
    protected void finalize() throws Throwable  
    {  
        System.out.println("Finalize method called");  
    } 
} 
Output:

Finalize method called
Finalize method called
```

### Eligibility for garbage collection?
	Unreachability

## Scenerio: Implementation of Cache(Internally a Map). 

	Suppose in an application, one needs to fetch data from a MASTER TABLE. 
	As a clever developer, we certainly don’t want the application to call the database every time, it will degrade application performance.
	Obviously, the choice is to use the cache. Cache is a class (Internally a Map), and first an application will check the cache to check if data is available there, 
	otherwise it will check the database and put the entry in cache, so next time it can be found in the cache to skip a database call.
	
	**Is it Going to Improve Performance?
	It will depend on the situation, If the master table has fewer entries this will work fine and certainly increase the performance. But if Master Table has huge entries, 
	it will create a problem as the Cache map is growing as entries loads from Master Table. Now instead of providing better performance it may lead to  out of memory. 
	
	** So what should we do?
	One thing we can do is to restrict the number of entries in the Cache and delete old entries.
	It will partially solve the problem, but it will take constant memory in JVM, although some of the objects in the cache will not be used for a long time.
	
	** Ideal Solution:-
	The ideal solution would be if we can make a type of cache which is dynamic in nature, and can grow and shrink as needed. So we need some kind of technique where we can delete those entries sitting in the cache which will not be used for a long time.
	To achieve it in Java, we provide different types of reference in the java.lang.ref package.

## Type of References:

In Java there are four types of references differentiated on the way by which they are garbage collected:-
	. Strong References
	. Weak References
	. Soft References
	. Phantom References

1. **Strong References:-**

	This is the default type/class of Reference Object.  Any object which has an active strong reference are not eligible for garbage collection. 
	The object is garbage collected **only when** the variable which was strongly referenced points to null.

2. **Weak Reference:-**

	They are not default type and they should be explicitly specified while using them. If JVM detects an object with **only weak references, this object will be marked for garbage collection.**
	To create such references java.lang.ref.WeakReference class is used.
	A weakly referenced object is cleared by the Garbage Collector when it's weakly reachable. Weak reachability means that an object has neither strong nor soft references pointing to it. The object can be reached only by traversing a weak reference.
	The most known use of these references is the WeakHashMap class. It's the implementation of the Map interface where every key is stored as a weak reference to the given key. When the Garbage Collector removes a key, the entity associated with this key is deleted as well.
	First off, the Garbage Collector clears a weak reference, so the referent is no longer accessible. Then the reference is placed in a reference queue (if any associated exists) where we can obtain it from.

3. **Soft References:- **

	If an object has no strong reference but has a soft reference, then the garbage collector **reclaims this object’s memory when GC needs to free up some memory. **
	To get Object from a soft reference, one can invoke the get() method. If the object is not GCed, it returns the object, otherwise , it returns null.

4. **Phantom Reference: **

	If an object does not have any of the above references then it may have a phantom reference. Phantom references can’t be accessed directly. When using a get() method it will always return null. 
	Phantom Reference can be used in situations, where sometimes using finalize() is not  sensible. This is a special reference which says that the object was already finalized, and the garbage collector is ready to reclaim its memory.
	"Phantom references are the weakest references of all.
	We can't get a referent of a phantom reference. The referent is never accessible directly through the API and this is why we need a reference queue to work with this type of references

```java
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceExample {
       private String status ="Hi I am active";
       public String getStatus() {
              return status;
       }
       public void setStatus(String status) {
              this.status = status;
       }
       @Override
       public String toString() {
              return "ReferenceExample [status=" + status + "]";
       }
       public void strongReference()
       {
              ReferenceExample ex = new ReferenceExample();
              System.out.println(ex);
       }
       public void softReference()
       {
              SoftReference<ReferenceExample> ex = new SoftReference<ReferenceExample>(getRefrence());
              System.out.println("Soft refrence :: " + ex.get());
       }
       public void weakReference()
       {
              int counter=0;
              WeakReference<ReferenceExample> ex = new WeakReference<ReferenceExample>(getRefrence());
              while(ex.get()!=null)
              {
                     counter++;
                     System.gc();
                     System.out.println("Weak reference deleted  after:: " + counter + ex.get());
              }
       }
       public void phantomReference() throws InterruptedException
       {
              final ReferenceQueue queue = new ReferenceQueue();
              PhantomReference<ReferenceExample> ex = new PhantomReference<ReferenceExample>(getRefrence(),queue);
              System.gc();
              queue.remove();
              System.out.println("Phantom reference deleted  after");
       }
       private ReferenceExample getRefrence()
       {
              return new ReferenceExample();
       }
       public static void main(String[] args) {
              ReferenceExample ex = new ReferenceExample();
              ex.strongReference();
              ex.softReference();
              ex.weakReference();
              try {
                     ex.phantomReference();
              } catch (InterruptedException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
       }
}
Output :
ReferenceExample [status=Hi I am active]
Soft refrence :: ReferenceExample [status=Hi I am active]
Weak reference deleted  after:: 1null
Phantom reference deleted  after
```

[More About Phantom References](https://codegym.cc/quests/lectures/questcollections.level04.lecture07)
### Q) So where could we pratically use finalize()?
	Ans:- As a check that all resources has been closed.
	Before Destructing an object, GC calls finalize() at most one time on that object,
	finalize() is present in Object Class and prototype is :-
	protected void finalize() throws Throwable
	
	Note:-
	1. The finalize() method called by Garbage Collector not JVM. Although Garbage Collector is one of the module of JVM.
	2. Object class finalize() method has empty implementation, thus it is recommended to override finalize() method to dispose of system resources or to perform other cleanup.
	3. The finalize() method is never invoked more than once for any given object
	4. If an uncaught exception is thrown by the finalize() method, the exception is ignored and finalization of that object terminates.
		
	**From Java 9 finalize() has been deprecaed**
	
	
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