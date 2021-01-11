# Volatile
	It is a non-access specifier, used only for variables. This ensures **variable visibilty to all other threads.**
	Visibility: It means that changes made by one thread to shared data are visible to other threads.
	Volatile variables are loaded in shared memory, not in the local memory, thats how it can able to give Visibility feature.
	
# AtomicInteger:
	Supppose there is a variable, int i = 0;
	If two threads do **Compound Operation**(like reading and writting) on this variable. there might be chance that Reading and writting operation clashes for both thread.
	Solution:-
	1) lock
	2) Synchronization.
	Both may lead to performance issue.
	In this case One can use AtomicInteger, which has single methods like increment(), **which does the compound operation Atomically.**
	Other methods are:-
	incrementAndGet()
	decrementAndGet
	addAndGet(int delta)
	CompareAndSet(int expected, int new)
	
# ThreadLocal:
	ThreadLocal is class which provides Tread local Variables. This is another way to provide thread safety.
	ThreadLocal enables us for for use:
	- One Object per Thread.(Thread Confinement)
	- Per thread context
	
	Set ThreadLocal Value:-
	private ThreadLocal threadLocal = new ThreadLocal();
	Get ThreadLocal Value:-
	String threadLocalValue = (String) threadLocal.get();
	Remove ThreadLocal Value:-
	threadLocal.remove();
	
# Parallelism:-
	Parallelism is **doing** a lot of things at once.
	Tools to enables Parallelism:-
	Threads, ThreadPool(Executor Service, Fork Join pool, Custom Thread Pool)
	Parallelism requires more than 1CPU cores.
	
# Concurrency:- 
	It is **dealing** a lot of work at once.
	Typically it is apllied when we have shared variable for multiple threads.
	Tools for Concurrency:-
	Locks/Synchronized, Atomic Classes, Concurrent Data Struc(ConcurrentHashMap,BlockingQueue), CompletableFuture, CoundownLatch, Phaser, CyclicBarrier, Semaphore etc
	
# Executor Framework:-
	Executor and ExecutorService(both are Interfaces) are used for  following purposes in java 
	creating thread,
	starting threads,
	managing whole life cycle of Threads.
	Executor creates pool of threads and manages life cycle of all threads in it in java
	
	How to create:-
	   ExecutorService executor = Executors.newFixedThreadPool(nThreads)
	   for(int i=0;i <100; i++){
		executor.execute(new MyrunnableClass()); // it will call run method. As executes() takes Ruunable as parameter.
		}
		
## How to return value from run(Thread) Method?
	Ans:- By the help of **Callable. **
	A task that returns a result and may throw an exception. To hande this scenerio we use Callable.
	Callable interface has one method, Sign as - V call() throws Exception;
	
	Executor service has Submit method for Callable Interface.
	.submit() return a Future result.
	Once we got result. One can use future.get().

## Differences between Callable and Runnable.
	Class implementing Callable interface must override call() method. call() method **returns computed result or throws an exception if unable to do so.**
	Class implementing Runnable interface must override run() method. A Runnable **does not return a result and can neither throw a checked exception**

## Future
	A Future represents the result of an asynchronous computation.
	Methods are provided to check if the computation is complete(isDone()), to wait for its completion, and to retrieve the result of the computation(get().
	The result can only be retrieved using method get() when the computation has completed, **blocking until it is ready**
	
## Note:- 
		Future.get() is a blocking operation. means until the thread executes and the result does not get publish, the other operation is blocked.
		TO Resolve this Java come up with Asynchronous API- CompletableFuture.

# CompletableFuture:-
	This is a class introduced in 1.8 in Concurrent API.
	Why?- To support Asynchronous computation.
	Java 5 introduced Future interface for handling Asynchronous computation but it does not have any methods to combine these computation or handle possible errors.
	CompletableFuture is a framework with 50 diff methods for composing, combining, executing Asynchronous computation steps and handling errors
	
	It provide a way to execute multiple threads at once, without blocking the main thread. 
	It provide methods like supplyAsync(Supplier), thenApply(), thenAccept() etc
	
## Differences between execute() and submit() of Executor Service
	execute() does not returns anything.
	submit method returns Future Reference.
	
# Condition :-
	This is a Interface in Java. Condition Instances are similar to Wait, notify and notifyAll.
	Imp Methods:-
	- void await()  - Similar to wait() of object class
	- boolean await(long time, TimeUnit unit)
	- void Signal() - similar to Notify()
	- void SignalAll() - similar to NotifyaLL()

# Locks:-
	Lock is an interface in java. It helps in controlling the access to a shared Variable by multiple thread. Only one thread can acquire a lock at a time.
	A lock is a more flexible and sophisticated thread synchronization mechanism than the standard synchronized block.

## 	Difference between Lock and Synchronized Block:

	A synchronized block is fully contained **within a method** ; we can have Lock API's lock() and unlock() operation in **separate methods.**
	A synchronized block **doesn't support the fairness**, any thread can acquire the lock once released, no preference can be specified. 
		We can achieve fairness within the Lock APIs by specifying the fairness property. It makes sure that **longest waiting thread **is given access to the lock
	A thread gets blocked if it can't get an access to the synchronized block. The Lock API provides **tryLock()** method. The thread acquires lock only if it's available and not held by any other thread. 
	A thread which is in “waiting” state to acquire the access to synchronized block, **can't be interrupted.** The Lock API provides a method lockInterruptibly() which can be used to interrupt the thread when it's waiting for the lock
	Synchronized has implicit locks and locks are explicit as we need to create variable for it.
	
	NOTE:- Always adivisibe to use try catch finally or try finally with locks as there might be some exception to it.
	
	Let's take a look at the methods in the Lock interface:

	void lock() – acquire the lock if it's available; if the lock isn't available a thread gets blocked until the lock is released
	void lockInterruptibly() – this is similar to the lock(), but it allows the blocked thread to be interrupted and resume the execution through a thrown java.lang.InterruptedException
	boolean tryLock() – this is a non-blocking version of lock() method; it attempts to acquire the lock immediately, return true if locking succeeds
	boolean tryLock(long timeout, TimeUnit timeUnit) – this is similar to tryLock(), except it waits up the given timeout before giving up trying to acquire the Lock
	void unlock() – unlocks the Lock instance

	In addition to the Lock interface, we have a ReadWriteLock interface which maintains a pair of locks, one for read-only operations, and one for the write operation. 
	**The read lock may be simultaneously held by multiple threads as long as there is no write.**

	ReadWriteLock declares methods to acquire read or write locks:

	Lock readLock() – returns the lock that's used for reading
	Lock writeLock() – returns the lock that's used for writing	

## Lock Implementation:-

1. ReentrantLock:-(literal mean- describes a computer program or routine that is written so that the same copy in memory can be shared by multiple user )
	Gives Same semantics as **Synchronized with extended functionality.**
	ReentrantLock locks **allow you to call lock multiple times(getHoldCount()- to know the number of lock.)**
	It allow Fair lock by - new ReentrantLock(true); - It means longest waiting thread can acquire the lock.
	
```java
	public class SharedObject{
	//..
	ReentrantLock lock = new ReentrantLock();
	int c = 0;
	
	public void perform(){
	lock.lock();
	try{
		c++;
	}
	finally{
		lock.unlock();
	}
	//..
	}
```	

2. ReentrantReadWriteLock:-
	This class implements ReadWriteLock interface.
	Rules for Acquiring Read/ Write Lock:-
	a) Read Lock:- If **no thread acquired the write lock or requested for it**, then **multiple threads** can acquire the read lock.
	b) Write Lock:- If no thread is **reading or writing** then only **one thread** can acquire the write lock.
	
3. StampedLock:-
	It is introduced in Java 8.
	It also supports both read or write locks. However, lock acquisition method returns a stamp that is used to release a lock or to check if the lock is still valid.
```java
public class StampedLockDemo {
    Map<String,String> map = new HashMap<>();
    private StampedLock lock = new StampedLock();
 
    public void put(String key, String value){
        long stamp = lock.writeLock();
        try {
            map.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
 
    public String get(String key) throws InterruptedException {
        long stamp = lock.readLock();
        try {
            return map.get(key);
        } finally {
            lock.unlockRead(stamp);
        }
    }
}
```

# Semaphore:
	A Semaphore(is a class in util.concurrent pkg) controls the **access to shared resource using permits in java.**
	If permits are greater than zero, then semaphore allow access to shared resource.
	If permits are zero or less than zero, then semaphore does not allow access to shared resource.
	These permits are sort of counters, which allow access to the shared resource. Thus, to access the resource, a thread must be granted a permit from the semaphore
	
	Imp methods:
	Constructors:-
	-	Semaphore(int permits)   - permits is the initial number of permits available
	-	Semaphore(int permits, boolean fair) - By setting fair to true, we ensure that waiting threads are granted a permit in the order in which they requested access.
	
	- 	void acquire( ) throws InterruptedException - Acquires a permit if one is available and decrements the number of available permits by 1.
	- 	void acquire(int permits) throws InterruptedException - Acquires permits number of permits if available and decrements the number of available permits by permits.
	
# CoundownLatch: 
	A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
	There might be situation where we might like our thread to wait until one or more threads completes certain operation. 

	A CountDownLatch(class in Concurrent pkg) is initialized with a given count. 
	count specifies the number of events that must occur before latch is released.
	Every time a event happens count is reduced by 1. Once count reaches 0 latch is released.
	
# Fork Join Pool
	
	Fork Join Pool enables Parallel programming. Parallel programming means taking advantage of two or more processor in computer.
	It improves the program performance.
	It also manages whole life cycle if Threads.
	
	**Difference between MultiThreading and Parallel programming?**
	
	
	
# Cyclic Barirer
	There might be situation where we might have to trigger event only when one or more threads completes certain operation in java, we can use cyclic barrier in tha case.
	Two or more threads wait for each other to reach a common barrier point. When all threads have reached common barrier point (i.e. when all threads have called await() method) >
	All waiting threads are released, and 
	Event can be triggered as well.
	
	Real World example - 
	Another very interesting question. It will test your real time thread concurrency implementation skills. Let’s say 10 friends (friends are threads) have planned for picnic on place A (Here place A is common barrier point). And they all decided to play certain game (game is event) only on everyones arrival at place A. So, all 10 friends must wait for each other to reach place A before launching event. 
	Now, when all threads have reached common barrier point (i.e. all friends have reached place A) >
	All waiting threads are released  (All friends can play game), and 
	Event can be triggered (they will start playing game).