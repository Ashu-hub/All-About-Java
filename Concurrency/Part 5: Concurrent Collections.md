# Part 5 — Concurrent Collections

> **Goal:** Learn how Java provides high-performance, thread-safe collection classes that allow multiple threads to access and modify shared data safely without requiring explicit synchronization.

---

# Table of Contents

- [1. Why Concurrent Collections?](#1-why-concurrent-collections)
- [2. Problems with Normal Collections](#2-problems-with-normal-collections)
- [3. Synchronized Collections vs Concurrent Collections](#3-synchronized-collections-vs-concurrent-collections)
- [4. How Concurrent Collections Work](#4-how-concurrent-collections-work)
- [5. Memory Visibility & Thread Safety](#5-memory-visibility--thread-safety)
- [6. Summary](#6-summary)

---

# 1. Why Concurrent Collections?

Before Java 5 introduced the **java.util.concurrent** package, developers had only two choices when sharing collections between multiple threads:

- Use normal collections like `ArrayList` or `HashMap` (unsafe)
- Wrap them using `Collections.synchronizedXXX()` (safe but slow)

Neither option scaled well for highly concurrent applications.

Java introduced **Concurrent Collections** to solve this problem.

They provide:

- Thread safety
- Better scalability
- Higher throughput
- Reduced locking
- Better CPU utilisation

Instead of locking an entire collection, most concurrent collections lock only the part being modified or avoid locks altogether.

---

## Real-world Example

Imagine an online shopping application.

Thousands of users are simultaneously:

- Searching products
- Adding products to cart
- Updating inventory
- Viewing recommendations

All these operations access shared collections.

```
          Thread 1
             │
             ▼
        Product Cache
             ▲
             │
          Thread 2

             ▲
             │
          Thread 3

             ▲
             │
        Thousands more...
```

If every operation locked the entire collection, performance would drop dramatically.

Concurrent Collections allow many threads to work simultaneously.

---

## Why Normal Collections Fail

Consider a shared `HashMap`.

```java
Map<Integer, String> map = new HashMap<>();
```

Thread A

```java
map.put(1, "Laptop");
```

Thread B

```java
map.put(2, "Phone");
```

Both threads modify the internal buckets simultaneously.

Possible results:

- Lost updates
- Corrupted bucket links
- Infinite loops (older JDKs during resize)
- Incorrect size
- Unexpected exceptions

A normal collection has **no internal synchronization**.

---

## Busy Server Example

Imagine a payment service.

```
100 Requests

↓

100 Threads

↓

HashMap
```

Every request updates the same cache.

Without thread safety:

```
Customer A

↓

Updates balance

Customer B

↓

Updates balance

↓

Incorrect final balance
```

This is called a **Race Condition**.

---

> 🟢 **Key Point**
>
> Concurrent Collections are designed to eliminate these race conditions while maintaining high performance.

---

# 2. Problems with Normal Collections

Let's understand why traditional collections are unsafe.

---

## Problem 1 — Race Condition

Suppose two threads update the same list.

```java
List<String> list = new ArrayList<>();
```

Thread A

```java
list.add("A");
```

Thread B

```java
list.add("B");
```

Internally, `ArrayList` performs several operations.

Simplified process:

```
Read current size

↓

Store element

↓

Increase size
```

If both threads execute simultaneously:

```
Initial Size = 0

Thread A → Reads 0

Thread B → Reads 0

Thread A → Stores A at index 0

Thread B → Stores B at index 0

Final Size = 1
```

Element `"A"` is lost.

---

## Problem 2 — Data Corruption

Multiple writes can corrupt the internal structure.

Example:

```
Thread A

↓

Resize Array

Thread B

↓

Insert Element

↓

Corrupted Array
```

This can result in:

- Missing data
- Invalid indexes
- Exceptions

---

## Problem 3 — ConcurrentModificationException

Suppose one thread iterates.

```java
for(String s : list){

}
```

Another thread modifies.

```java
list.add("Java");
```

Result

```
ConcurrentModificationException
```

This is known as a **Fail-Fast Iterator**.

The iterator detects structural modification and immediately fails.

---

## Problem 4 — Visibility Issues

Suppose Thread A updates a collection.

```
Shared List

↓

Add "ABC"
```

Thread B immediately reads it.

Without proper synchronization:

Thread B may still see the old state.

Why?

Because CPUs maintain local caches.

```
CPU Core 1 Cache

CPU Core 2 Cache

↓

Memory
```

Changes are not always immediately visible.

Concurrent collections establish the proper **happens-before** relationships to guarantee visibility.

---

## Problem 5 — HashMap Resize (Java 7)

One of the most famous interview questions.

During resizing, two threads may rearrange bucket links simultaneously.

```
Bucket

↓

Node A

↓

Node B

↓

Node C
```

Incorrect re-linking could create a cycle.

```
A

↓

B

↓

C

↑
│
└───────
```

Traversal becomes:

```
A

↓

B

↓

C

↓

B

↓

C

↓

B

↓

Forever...
```

Older JDKs could enter an infinite loop.

Modern JDKs have improved the implementation, but **HashMap is still not thread-safe**.

---

> 🔴 **Interview Trap**
>
> HashMap's resize algorithm was improved in Java 8, but **HashMap is still not safe for concurrent writes**.

---

# 3. Synchronized Collections vs Concurrent Collections

Before Java 5, developers used wrappers.

Example

```java
List<String> list =
Collections.synchronizedList(new ArrayList<>());
```

This makes the list thread-safe.

But how?

By synchronizing every method.

Internally

```java
public synchronized boolean add(E e){

}
```

Only one thread can access the collection at a time.

```
Thread A

↓

LOCK

↓

Collection

↓

UNLOCK

↓

Thread B
```

Works correctly.

But performance suffers.

---

## Why Is It Slow?

Suppose 100 threads only want to read.

```
Read

Read

Read

Read

Read
```

Even reads must wait.

```
Thread 1

↓

Lock

↓

Read

↓

Unlock

↓

Thread 2
```

Only one thread proceeds at a time.

This creates a bottleneck.

---

## Concurrent Collections

Instead of locking everything,

Concurrent Collections use techniques like:

- Fine-grained locking
- Lock striping
- Compare-And-Swap (CAS)
- Lock-free algorithms
- Copy-on-write
- Atomic operations

This allows multiple threads to work simultaneously.

Example:

```
Thread A

↓

Bucket 1

Thread B

↓

Bucket 7

Thread C

↓

Bucket 20
```

No interference.

---

## Comparison

| Feature | Synchronized Collection | Concurrent Collection |
|----------|-------------------------|-----------------------|
| Thread-safe | ✅ | ✅ |
| Entire collection locked | ✅ | ❌ |
| Multiple readers | ❌ | ✅ |
| Better scalability | ❌ | ✅ |
| High throughput | ❌ | ✅ |
| Modern applications | Rarely used | Preferred |

---

> 🟢 **Best Practice**
>
> Prefer `ConcurrentHashMap`, `CopyOnWriteArrayList`, or `BlockingQueue` over `Collections.synchronizedXXX()` in modern applications.

---

# 4. How Concurrent Collections Work

Different concurrent collections use different strategies.

| Collection | Strategy |
|------------|----------|
| ConcurrentHashMap | Bucket-level locking + CAS |
| CopyOnWriteArrayList | Copy entire array on modification |
| ConcurrentLinkedQueue | Lock-free CAS |
| BlockingQueue | Locks + Conditions |
| ConcurrentSkipListMap | Lock-free skip list |

Each strategy is chosen based on the type of workload.

---

## Fine-Grained Locking

Instead of locking the whole collection,

only a small part is locked.

Example

```
Hash Table

+-----+-----+-----+-----+
| B1  | B2  | B3  | B4  |
+-----+-----+-----+-----+

Thread A locks Bucket 1

Thread B locks Bucket 3

Both continue simultaneously.
```

This dramatically improves throughput.

---

## Lock-Free Algorithms

Some collections avoid locks completely.

Example

```
ConcurrentLinkedQueue
```

Instead of locking,

they use **CAS (Compare-And-Swap)**.

```
Expected Value

↓

Compare

↓

Still Same?

↓

Yes

↓

Update

↓

Done
```

No blocking.

No waiting.

Much higher scalability.

---

## Copy-On-Write

Used by

```
CopyOnWriteArrayList
```

Whenever data changes:

```
Old Array

↓

Create Copy

↓

Modify Copy

↓

Replace Reference
```

Readers continue using the old array.

No locks are required for reading.

Excellent for read-heavy applications.

---

# 5. Memory Visibility & Thread Safety

Concurrent Collections don't just prevent data corruption.

They also guarantee memory visibility.

Producer

```java
map.put(1, "Java");
```

Consumer

```java
map.get(1);
```

The consumer is guaranteed to observe the latest successfully published value.

No additional `volatile` or synchronization is required for individual collection operations.

---

## Thread Safety Doesn't Mean Atomic Sequences

Each individual operation is thread-safe.

Example

```java
map.put(id, value);
```

Safe.

```java
map.get(id);
```

Safe.

But this is **not** atomic:

```java
if (!map.containsKey(id)) {
    map.put(id, value);
}
```

Two threads can execute it simultaneously.

Correct solution:

```java
map.computeIfAbsent(id, k -> value);
```

---

> 🔴 **Interview Trap**
>
> **Thread-safe operations do not automatically make a sequence of operations thread-safe.** Use atomic methods such as `computeIfAbsent()`, `compute()`, `merge()`, or `putIfAbsent()` when multiple steps must be treated as one operation.

---

# 6. Summary

| Collection Type | Thread-safe | Performance | Typical Use |
|-----------------|------------|-------------|-------------|
| ArrayList | ❌ | Excellent (single-threaded) | General applications |
| HashMap | ❌ | Excellent (single-threaded) | General applications |
| Collections.synchronizedList | ✅ | Moderate | Legacy code |
| Collections.synchronizedMap | ✅ | Moderate | Legacy code |
| ConcurrentHashMap | ✅ | Excellent | Shared caches, session storage |
| CopyOnWriteArrayList | ✅ | Excellent reads | Read-heavy applications |
| ConcurrentLinkedQueue | ✅ | Excellent | Non-blocking queues |
| BlockingQueue | ✅ | Excellent | Producer–Consumer systems |

---

# Key Takeaways

- Normal collections are **not thread-safe**.
- Concurrent Collections provide **high-performance thread safety**.
- They minimise locking by using techniques such as **fine-grained locking**, **CAS**, **copy-on-write**, and **lock-free algorithms**.
- `Collections.synchronizedXXX()` locks the entire collection and scales poorly under heavy concurrency.
- Thread safety guarantees individual operations, **not compound operations**.
- Use atomic methods such as `computeIfAbsent()` when multiple steps must execute as a single atomic action.

---

# Part 5 — Concurrent Collections (Concise Notes)

---

## 5. ConcurrentHashMap ⭐

**Package**

```java
java.util.concurrent
```

### What is it?

A high-performance, thread-safe implementation of `Map` designed for concurrent access.

Unlike `Hashtable`, it does **not** lock the entire map.

### Key Features

- Thread-safe
- High concurrency
- Lock-free reads
- Fine-grained locking for writes
- Does not allow `null` keys or values
- Weakly consistent iterator
- Atomic operations (`compute()`, `merge()`, etc.)

### Internal Working

- Data stored in buckets.
- Reads are mostly lock-free.
- Writes lock only the affected bucket/node.
- Uses **CAS (Compare-And-Swap)** before locking.
- Buckets with many collisions are converted into Red-Black Trees.

```
Hash

↓

Bucket

↓

Node

↓

TreeBin (after threshold)
```

### Time Complexity

| Operation | Complexity |
|------------|------------|
| get() | O(1) |
| put() | O(1) |
| remove() | O(1) |

### Best Use Cases

- Caching
- Session storage
- Shared configuration
- Counters
- In-memory lookup tables

---

## 6. CopyOnWriteArrayList ⭐

### What is it?

A thread-safe `List` where every write operation creates a new copy of the underlying array.

### Internal Working

```
Old Array

↓

Copy Array

↓

Modify Copy

↓

Replace Reference
```

Readers continue reading the old array without blocking.

### Advantages

- Lock-free reads
- No ConcurrentModificationException
- Excellent for read-heavy applications

### Disadvantages

- Expensive writes
- Higher memory usage

### Best Use Cases

- Configuration lists
- Event listeners
- Cached reference data
- Read-heavy systems

---

## 7. CopyOnWriteArraySet

### What is it?

A thread-safe `Set` built on top of `CopyOnWriteArrayList`.

### Characteristics

- No duplicates
- Snapshot iterator
- Lock-free reads
- Expensive writes

### Best Use Cases

- Listener registration
- Observer pattern
- Read-mostly collections

---

## 8. ConcurrentLinkedQueue

### What is it?

A lock-free, thread-safe FIFO queue.

### Internal Working

Uses CAS instead of locks.

```
Head

↓

Node

↓

Node

↓

Tail
```

### Characteristics

- Non-blocking
- High throughput
- Unbounded queue

### Best Use Cases

- Task queues
- Logging
- Event processing

---

## 9. ConcurrentLinkedDeque

### What is it?

A thread-safe double-ended queue.

Supports insertion and removal from both ends.

### Methods

```java
addFirst()

addLast()

pollFirst()

pollLast()
```

### Best Use Cases

- Work stealing
- Undo/Redo
- Double-ended processing

---

## 10. BlockingQueue Family

Provides automatic producer-consumer synchronization.

### Popular Implementations

| Queue | Purpose |
|---------|----------|
| ArrayBlockingQueue | Fixed capacity |
| LinkedBlockingQueue | Dynamic capacity |
| PriorityBlockingQueue | Priority ordering |
| DelayQueue | Delayed execution |
| SynchronousQueue | Direct thread handoff |

### Important Methods

```java
put()

take()

offer()

poll()

peek()

drainTo()
```

### Best Use Cases

- Thread pools
- Producer-consumer
- Job scheduling
- Messaging systems

---

## 11. ConcurrentSkipListMap

### What is it?

A thread-safe, sorted implementation of `NavigableMap`.

### Internal Working

Uses a **Skip List** instead of a Red-Black Tree.

```
Level 3

↓

Level 2

↓

Level 1

↓

Data
```

### Characteristics

- Sorted keys
- Lock-free reads
- Scalable

### Best Use Cases

- Leaderboards
- Ranking systems
- Time-based ordering

---

## 12. ConcurrentSkipListSet

### What is it?

A thread-safe sorted implementation of `NavigableSet`.

Internally backed by `ConcurrentSkipListMap`.

### Characteristics

- Sorted
- Thread-safe
- No duplicates

### Best Use Cases

- Ordered unique values
- Scheduling
- Ranking

---

## 13. Fail-Fast vs Weakly Consistent Iterators

| Feature | Fail-Fast | Weakly Consistent |
|----------|-----------|-------------------|
| ConcurrentModificationException | Yes | No |
| Safe during modification | No | Yes |
| Iterator reflects latest changes | No | May reflect some changes |

### Fail-Fast Collections

- ArrayList
- HashMap
- HashSet

### Weakly Consistent Collections

- ConcurrentHashMap
- ConcurrentLinkedQueue
- ConcurrentSkipListMap

---

## 14. Atomic Compound Operations

Individual operations are thread-safe.

```
put()

get()

remove()
```

But multiple operations together are **not**.

Unsafe

```java
if (!map.containsKey(key)) {
    map.put(key, value);
}
```

Safe

```java
map.computeIfAbsent(key, k -> value);
```

### Atomic Methods

- putIfAbsent()
- compute()
- merge()
- replace()
- remove(key,value)

---

## 15. compute(), computeIfAbsent(), computeIfPresent() & merge()

### compute()

Always executes.

```java
map.compute(key, (k,v) -> ...);
```

---

### computeIfAbsent()

Executes only if key is missing.

```java
map.computeIfAbsent(key, k -> value);
```

---

### computeIfPresent()

Executes only if key exists.

```java
map.computeIfPresent(key, (k,v) -> newValue);
```

---

### merge()

Adds or combines values atomically.

```java
map.merge(key, value, Integer::sum);
```

Perfect for counters.

---

## 16. Java 7 vs Java 8 ConcurrentHashMap

| Java 7 | Java 8 |
|----------|---------|
| Segment locking | Bucket-level locking |
| Fixed segments | Dynamic buckets |
| More locks | Fewer locks |
| No tree bins | Red-Black Trees |
| Lower scalability | Higher scalability |

### Improvements in Java 8

- Better concurrency
- Better resizing
- Better collision handling
- Less memory overhead

---

## 17. Performance Comparison

| Collection | Read | Write | Memory | Best For |
|-------------|------|--------|--------|----------|
| HashMap | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Single-threaded |
| Hashtable | ⭐ | ⭐ | ⭐⭐⭐ | Legacy |
| ConcurrentHashMap | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | General concurrency |
| CopyOnWriteArrayList | ⭐⭐⭐⭐⭐ | ⭐ | ⭐ | Read-heavy |
| ConcurrentLinkedQueue | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | High throughput |
| BlockingQueue | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐ | Producer-Consumer |

---

## 18. Which Concurrent Collection Should You Use?

| Requirement | Collection |
|--------------|------------|
| Thread-safe Map | ConcurrentHashMap |
| Read-heavy List | CopyOnWriteArrayList |
| Read-heavy Set | CopyOnWriteArraySet |
| FIFO Queue | ConcurrentLinkedQueue |
| Double-ended Queue | ConcurrentLinkedDeque |
| Producer-Consumer | BlockingQueue |
| Sorted Map | ConcurrentSkipListMap |
| Sorted Set | ConcurrentSkipListSet |

---

## 19. Real-World Use Cases

| Scenario | Recommended Collection |
|-----------|-----------------------|
| Cache | ConcurrentHashMap |
| Spring Bean Cache | ConcurrentHashMap |
| User Sessions | ConcurrentHashMap |
| ThreadPool Tasks | BlockingQueue |
| Kafka Consumers | BlockingQueue |
| Event Bus | ConcurrentLinkedQueue |
| Logging | ConcurrentLinkedQueue |
| Notification Queue | BlockingQueue |
| Configuration Data | CopyOnWriteArrayList |
| Event Listeners | CopyOnWriteArraySet |
| Leaderboards | ConcurrentSkipListMap |

---

## 20. Top Interview Questions

1. Why is ConcurrentHashMap faster than Hashtable?
2. Why are `null` keys not allowed?
3. Explain CAS.
4. What is bucket-level locking?
5. Explain treeification.
6. Difference between `putIfAbsent()` and `computeIfAbsent()`.
7. Why is CopyOnWriteArrayList slow for writes?
8. BlockingQueue vs ConcurrentLinkedQueue?
9. Fail-Fast vs Weakly Consistent iterator?
10. Java 7 vs Java 8 ConcurrentHashMap?
11. What happens during ConcurrentHashMap resizing?
12. Which collection is best for read-heavy applications?
13. Can ConcurrentHashMap guarantee atomic compound operations?
14. Which concurrent collection would you use for caching?
15. Why doesn't ConcurrentHashMap throw ConcurrentModificationException?

---

## 21. Summary

| Collection | Thread-Safe | Ordered | Blocking | Lock-Free Reads | Best Use |
|-------------|-------------|---------|----------|-----------------|----------|
| ConcurrentHashMap | ✅ | ❌ | ❌ | ✅ | Shared Map |
| CopyOnWriteArrayList | ✅ | ✅ | ❌ | ✅ | Read-heavy List |
| CopyOnWriteArraySet | ✅ | ✅ | ❌ | ✅ | Read-heavy Set |
| ConcurrentLinkedQueue | ✅ | FIFO | ❌ | ✅ | Task Queue |
| ConcurrentLinkedDeque | ✅ | Double End | ❌ | ✅ | Work Stealing |
| BlockingQueue | ✅ | FIFO/Priority | ✅ | ❌ | Producer-Consumer |
| ConcurrentSkipListMap | ✅ | Sorted | ❌ | ✅ | Ordered Map |
| ConcurrentSkipListSet | ✅ | Sorted | ❌ | ✅ | Ordered Set |

---

# 💡 Key Takeaways

- Use **ConcurrentHashMap** for shared mutable maps.
- Use **CopyOnWriteArrayList** when reads greatly outnumber writes.
- Use **ConcurrentLinkedQueue** for high-throughput, non-blocking queues.
- Use **BlockingQueue** when producers and consumers need to wait for each other.
- Use **ConcurrentSkipListMap/Set** when sorted data is required concurrently.
- Prefer atomic methods like `computeIfAbsent()` over manual check-then-act logic.
- Concurrent collections improve scalability through **fine-grained locking**, **CAS**, **copy-on-write**, and **lock-free algorithms**.
