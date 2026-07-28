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

➡️ **Next Chapter:** **ConcurrentHashMap (Deep Dive)** — Internal Architecture, CAS, Bucket-Level Locking, Treeification, Resizing, Java 7 vs Java 8, Performance, and Interview Questions.
