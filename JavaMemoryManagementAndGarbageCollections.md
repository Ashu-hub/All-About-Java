# Java Memeory Management:-

*Q) Why to study?* - 

	If you have a poorly perfotmance application(having issue like outOfMemory or is becoming unresponsive frequently). 
					Or you want to study Java as a **Execution env** (not java as a programming lang).
					
*Q) ByteCode:-* 

	main.java is being complied by complier and  produces main.class file which is nothing but a files containing bytecode of the class.
	This Bytecode is being run by JVM or is being interpreted by JVM. (cross-platform execution feature).
	
*Q) JIT(Just in Time) compilation:- *
	
	Way to converting ByteCode to native machine language for execution, it has a huge amount of impact on the Speed. These ByteCode have to be interpreted further in machine instructions.
	Interpreting the bytecode affects the speed of execution.
	In order to improve performance JIT compilers interacts with JVM at runtime and complied suitable bytecode sequence into native machine code.
	Some of the Optimization perfored by JIT are:-
	**Elimination of common sub-expressions** , ** translation from stack operations to register operations**, **data-analysis**, **reduction of memory accesses by register allocation** etc
	[More Read](https://www.geeksforgeeks.org/just-in-time-compiler/)

Q) 	How to find out which method are being complied in our application?
	
	** -XX:+printCompilation **
	-XX -> Advance option
	
Q) Types of JIT compliers:-
	
	C1 and C2 compliers.
	C1 = able to do 1st 3 level of compliation (Native level 1, Native level 2, Native level 3)(Native level 1 usually means cached code)
	C2= do the4th level (Native level 4)
	The VM decides which level of compilation needs to be applied on a perticular level of code based on how that code is being utilized and how complex and time consuming it is.
	This is called profiling the code
	
	**-XX:+UnlockDiagonostiVMOptions -XXLogCompilation** --> Generated a detialed log file about which complier beong used, what level beong used etc

q) Tuning the code cache size?
	
	Sometimes you may see like this:- 
	VM Warning: CodeCache is full. Complier hasbeen disabled
	This means that default code cache size is not sufficient and incresed this size is recommanded.
	
	**-XX:+PrintCodeCache** -> this will shows you how many memory is used and are left in code cache
	**-XX:ReservedCodeCacheSize=25m** -> This will change the reserve code cache size to 25megabytes
	
	
### Selecting a JVM
	
	32 bit JVM:-
	might be faster if the heap size < 3 GB
	
	64 bit JVM:-
	Necessary if heap size > 4GB
	**Incomplete**	

## How memory management works in java?

	Memory management is the process of allocating new objects and removing unused objects to make memory for the newly created objects.

## Heap

	Objects resides in the area called heap. Heap is created when the JVM starts up and **may increase or decrease** during the applicaton runs.
	When the heap size is full, garbage is collected. During garbage collection objects that are no longer used are cleared and making space for the newly created objects.
	 
	Note that the JVM uses more memory than just the heap. For example Java methods, thread stacks and native handles are allocated in memory separate from the heap, as well as JVM internal data structures.
	
	Heap is divided into two areas(or generation):- 
	1) Nursery(or young space)
	2) Old space.
	
	The nursery is a part of the heap reserved for allocation of new objects. 
	When the nursery becomes full, garbage is collected by running a special **young collection**, where all objects that have lived long enough in the nursery are promoted (moved) to the old space, thus freeing up the nursery for more object allocation.
	When the old space becomes full, garbage is collected there, a process called an **old collection.**
	The reasoning behind a nursery is that most objects are temporary and short lived. 
	A young collection is designed to be swift at finding newly allocated objects that are still alive and moving them away from the nursery. 
	Typically, a young collection frees a given amount of memory much faster than an old collection or a garbage collection of a single-generational heap (a heap without a nursery)
	 A part of the nursery is reserved as a **keep area**. The keep area contains the most recently allocated objects in the nursery and is not garbage collected until the next young collection. 
	 This prevents objects from being promoted just because they were allocated right before a young collection started.

# Garbage Collection

	Garbage collection is the process of freeing space in the heap for allocation of new objects.
	
##	1) The Mark and Sweep Model:-
	 JVM uses the mark and sweep garbage collection model for performing garbage collections of the whole heap.
	 A mark and sweep garbage collection consists of two phases:- 
	 1) The mark phase.
	 2) The sweep phase.
	 
	 The Mark Phase:- 
	 During the mark phase all objects that are reachable from Java threads, native handles and other root sources are marked as alive, as well as the objects that are reachable from these objects and so forth. This process identifies and marks all objects that are still used, and the rest can be considered garbage.
	 
	 The sweep phase:-
	 During the sweep phase the heap is traversed to find the gaps between the live objects. These gaps are recorded in a free list and are made available for new object allocation.
	 
	 JVM uses two improved versions of the mark and sweep model:- 
	 One is **mostly concurrent mark and sweep **and the other is **parallel mark and sweep**
	 
	 1. Mostly Concurrent Mark and Sweep:- 
	 The mostly concurrent mark and sweep strategy (often simply called **concurrent garbage collection**) allows the Java threads to continue running during large portions of the garbage collection. The threads must however be stopped a few times for synchronization.
	 
	 The mostly concurrent mark phase is divided into four parts:
	 
	..* Initial marking, where the root set of live objects is identified. This is done while the Java threads are paused.
	..* Concurrent marking, where the references from the root set are followed in order to find and mark the rest of the live objects in the heap. This is done while the Java threads are running.
	..* Precleaning, where changes in the heap during the concurrent mark phase are identified and any additional live objects are found and marked. This is done while the Java threads are running.
	..* Final marking, where changes during the precleaning phase are identified and any additional live objects are found and marked. This is done while the Java threads are paused.
	
	The mostly concurrent sweep phase consists of four parts:- 
	
	..* Sweeping of one half of the heap. This is done while the Java threads are running and are allowed to allocate objects in the part of the heap that isn’t currently being swept.
	..* A short pause to switch halves.
	..* Sweeping of the other half of the heap. This is done while the Java threads are running and are allowed to allocate objects in the part of the heap that was swept first.
	..* A short pause for synchronization and recording statistics.
	
	Parallel Mark and Sweep:-
	
	The parallel mark and sweep strategy (also called the parallel garbage collector) uses **all available CPUs in the system** for performing the garbage collection as fast as possible. All Java threads are paused during the entire parallel garbage collection.
	
####	Side Note : 
	Concurrent = Two queues and one coffee machine.
	Parallel = Two queues and two coffee machines.
	 
##	2) 	Generational Garbage Collection
	
	The nursery, when it exists, is garbage collected with a special garbage collection called a young collection. A garbage collection strategy which uses a nursery is called a generational garbage collection strategy, or simply generational garbage collection.

	The young collector used in the JRockit JVM identifies and promotes all live objects in the nursery that are outside the keep area to the old space. This work is done in parallel 	using all available CPUs. The Java threads are paused during the entire young collection.
	
## 3) Dynamic and Static Garbage Collection Modes
	
	By default, the JRockit JVM uses a dynamic garbage collection mode that automatically selects a garbage collection strategy to use, aiming at optimizing the application throughput.
	
## 4) Compaction
	
	Objects that are allocated next to each other will not necessarily become unreachable (“die”) at the same time.
	This means that the heap may become fragmented after a garbage collection, so that the free spaces in the heap are many but small, making allocation of large objects hard or even impossible.
	To reduce fragmentation, the JRockit JVM compacts a part of the heap at every garbage collection (old collection). 
	Compaction moves objects closer together and further down in the heap, thus creating larger free areas near the top of the heap. 
	Compaction is performed at the beginning of or during the sweep phase and while all Java threads are paused.
	
	External and Internal Compaction:-
	JVM uses two compaction methods called external compaction and internal compaction. 
	External compaction moves (evacuates) the objects within the compaction area to free positions outside the compaction area and as far down in the heap as possible. 
	Internal compaction moves the objects within the compaction area as far down in the compaction area as possible, thus moving them closer together.
	
	The JVM selects a compaction method depending on the current garbage collection mode and the position of the compaction area. External compaction is typically used near the top of the heap, while internal compaction is used near the bottom where the density of objects is higher.
	
	Sliding Window Schemes:-
	The position of the compaction area changes at each garbage collection, using one or two sliding windows to determine the next position. Each sliding window moves a notch up or down in the heap at each garbage collection, until it reaches the other end of the heap or meets a sliding window that moves in the opposite direction, and starts over again. Thus the whole heap is eventually traversed by compaction over and over again.
	
	Compaction Area Sizing:-
	The size of the compaction area depends on the garbage collection mode used. In throughput mode the compaction area size is static, while all other modes, including the static mode, adjust the compaction area size depending on the compaction area position, aiming at keeping the compaction times equal throughout the run.
	
	[Ref](https://docs.oracle.com/cd/E13150_01/jrockit_jvm/jrockit/geninfo/diagnos/garbage_collect.html#wp1086917)
	
	
## Advantages of GC:

	..* No manual memory allocation/deallocation handling because unused memory space is automatically handled by GC
	..* No overhead of handling Dangling Pointer
	..* Automatic Memory Leak management (GC on its own can't guarantee the full proof solution to memory leaking, however, it takes care of a good portion of it)
	
## Disadvantages of GC:
	
	..* Since JVM has to keep track of object reference creation/deletion, this activity requires more CPU power besides the original application. It may affect the performance of requests which required large memory. 
	..* Using some GC implementations might result in application stopping unpredictably.
	
# GC Implementations:-
	
	VM has four types of GC implementations:

	1. Serial Garbage Collector
	2. Parallel Garbage Collector
	3. CMS Garbage Collector
	4. G1 Garbage Collector
	
## Serial Garbage Collector:-
	
	This is the simplest GC implementation, as it basically works with a **single thread**. 
	As a result, this GC implementation freezes all application threads when it runs. 
	Hence, it is not a good idea to use it in multi-threaded applications like server environments.
	
	To enable Serial Garbage Collector, we can use the following argument:
	java -XX:+UseSerialGC -jar Application.java
	
## Parallel Garbage Collector:-

	It's the default GC of the JVM and sometimes called **Throughput Collectors.**
	Unlike Serial Garbage Collector, this uses **multiple threads** for managing heap space. 
	But it also freezes other application threads while performing GC.	
	If we use this GC, we can specify maximum garbage collection threads and pause time, throughput, and footprint (heap size).
	
	To enable Parallel Garbage Collector, we can use the following argument:
	java -XX:+UseParallelGC -jar Application.java
	
## CMS Garbage Collector:-

	The Concurrent Mark Sweep (CMS) implementation uses multiple garbage collector threads for garbage collection. 
	Designed for applications that prefer shorter garbage collection pauses, and that can afford to share processor resources with the garbage collector while the application is running.
	
	To enable the CMS Garbage Collector, we can use the following flag:
	java -XX:+UseParNewGC -jar Application.java
	
	As of Java 9, the CMS garbage collector has been deprecated.
	Moreover, Java 14 completely dropped the CMS support:
	
##  G1 Garbage Collector:-

	G1 (Garbage First) Garbage Collector is designed for applications running on multi-processor machines with large memory space.
	G1 collector will replace the CMS collector since it's more performance efficient.
	Unlike other collectors, G1 collector partitions the heap into a **set of equal-sized heap regions**, each a contiguous range of virtual memory. When performing garbage collections,
	G1 shows a concurrent global marking phase (i.e. phase 1 known as Marking) to determine the liveness of objects throughout the heap.

	After the mark phase is completed, G1 knows which regions are mostly empty. 
	It collects in these areas first, which usually yields a significant amount of free space (i.e. phase 2 known as Sweeping). It is why this method of garbage collection is called Garbage-First.

	To enable the G1 Garbage Collector, we can use the following argument:
	java -XX:+UseG1GC -jar Application.java
	
# Java Profiling tool:-
	
	Java VisualVM is a profiling tool used for Java application. By Default it is bundled with JDK.
	Java VisualVM supports local and remote profiling as well.
	It is present in jdk/bin folder.
	It allow us to view details information of our java application while they are executing. 
	
**Q) What are the two modes in JProfiler for CPU Profiling?**
	sampling and instrumentation.

###	How to detect/debug deadlock?
	using jvisuwalVM. Open Your process id and click on Thread Tab. It will show you with clear meassage about where the deadlock is. 
	It can be  also seen bt command line tool using - jstack processId. 
	
	Heap Dump :- It is a snapshot of memeory of a java process. This snapshot coontians info about java classes, feilds, referece, class loader, superclass, static feilds etc.
**Q) How to take heap dump.**
	you can take heap dump using monitor tab right side button.
	You can aslo take heap dump while in debug mode. This will give you clear picture of which line causing error.
	
**Q) How to anayze outOfMemory error?**
	Add vm aruguments- -XX:+HeapDumpOnOutOfMemoryError
	It will generate the heap dump file-> load it in jvisuwalVM UI. Now you can clearly see the details about what went wrong.
	
**Q) what is Memory leak?**
	The standard definition of a memory leak is a scenario that occurs when objects are no longer being used by the application, 
	but the Garbage Collector is unable to remove them from working memory – because they're still being referenced.
	
	Using Profiling (memory or CPU) we can monitor and diagonse the memory leaks.