# Java Concurrency Interview Handbook
# Part 3 – Locks

> **Goal:** Learn Java's explicit locking mechanisms, when to use them over `synchronized`, and how they work internally.

---

# 📚 Table of Contents

- [1. Why Locks?](#1-why-locks)
- [2. Lock Interface](#2-lock-interface)
- [3. ReentrantLock](#3-reentrantlock)
- [4. Fair vs Unfair Lock](#4-fair-vs-unfair-lock)
- [5. ReadWriteLock](#5-readwritelock)
- [6. StampedLock](#6-stampedlock)
- [7. Lock-Free Programming](#7-lock-free-programming)
- [8. Compare-And-Swap (CAS)](#8-compare-and-swap-cas)
- [9. Atomic Classes](#9-atomic-classes)
- [10. LongAdder](#10-longadder)
- [Part 3 Summary](#part-3-summary)
- [Top Interview Questions](#top-interview-questions)

---

# 1. Why Locks?

> [!NOTE]
> `synchronized` is simple but has limited flexibility. The **Lock API** provides more control over locking.
    In Java, a lock is a synchronization mechanism used to control access to shared resources when multiple threads are running concurrently. A lock ensures that only one thread can execute a critical section of code at a time, preventing race conditions and data corruption.
> Types of Locks in Java:
> 1. Intrinsic Lock (Monitor Lock): Every Java object has a built-in lock called an intrinsic lock or monitor. The synchronized keyword uses this lock.
> 2. Explicit Lock (Lock Interface): Java provides the Lock interface in the java.util.concurrent.locks package for more advanced locking features.


```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();  // Acquire lock
        try {
            count++;
        } finally {
            lock.unlock(); // Always release lock
        }
    }
}
```

### Why use Locks?

- Try to acquire a lock without waiting.
- Wait for a limited time.
- Interrupt a waiting thread.
- Fair lock scheduling.
- Better suited for complex concurrency.

---

## synchronized vs Lock

| synchronized | Lock |
|--------------|------|
| JVM managed | Programmer managed |
| Automatically released | Must release manually |
| No timeout | Supports timeout |
| No interruptible lock | Supports interruptible locking |
| No fairness | Supports fairness |

---

## Interview Question

### When should you prefer `Lock` over `synchronized`?

Use `Lock` when you need:
- Timeout
- Fairness
- Interruptible locking
- Multiple lock conditions

---

# 2. Lock Interface

> [!NOTE]
> `Lock` (`java.util.concurrent.locks.Lock`) provides explicit lock operations.

### Common Methods

| Method | Purpose |
|---------|---------|
| `lock()` | Acquire lock |
| `unlock()` | Release lock |
| `tryLock()` | Acquire if available |
| `tryLock(timeout)` | Wait for limited time |
| `lockInterruptibly()` | Acquire unless interrupted |

---

## Example

```java
Lock lock = new ReentrantLock();

lock.lock();
try {
    // Critical Section
} finally {
    lock.unlock();
}
```

> [!WARNING]
> Always call `unlock()` inside a `finally` block.

---

# 3. ReentrantLock

> [!NOTE]
> `ReentrantLock` works like `synchronized` but provides additional features.

---

## Why "Reentrant"?

The same thread can acquire the same lock multiple times.

```java
Lock lock = new ReentrantLock();

lock.lock();

try {
    // work
} finally {
    lock.unlock();
}
```

---

## Features

- ✅ Reentrant
- ✅ Fair/Unfair mode
- ✅ Timeout support
- ✅ Interruptible
- ✅ Multiple Conditions

---

## Interview Questions

### Is `ReentrantLock` thread-safe?

Yes.

### Is it reentrant?

Yes.

### Does it automatically release the lock?

❌ No.

You must call `unlock()`.

---

> [!TIP]
> Forgetting `unlock()` can easily lead to deadlocks.

---

# 4. Fair vs Unfair Lock

## Fair Lock

Threads acquire the lock in the order they requested it (FIFO).

```java
Lock lock = new ReentrantLock(true);
```

```
T1 → T2 → T3
```

---

## Unfair Lock (Default)

A newly arrived thread may acquire the lock before waiting threads.

```java
Lock lock = new ReentrantLock();
```

```
T1
↑
T4 acquires lock before T2
```

---

## Comparison

| Fair | Unfair |
|------|---------|
| FIFO | No order |
| Avoids starvation | Higher throughput |
| Slightly slower | Faster |

---

## Interview Question

### Which is the default?

✅ Unfair lock.

---

# 5. ReadWriteLock

> [!NOTE]
> Allows **multiple readers** but only **one writer**.

```
Readers

T1 ✔

T2 ✔

T3 ✔

Writer

T4 ❌ Wait
```

---

## Example

```java
ReadWriteLock lock = new ReentrantReadWriteLock();

lock.readLock().lock();

lock.writeLock().lock();
```

---

## Use Cases

- Cache
- Configuration
- Product Catalogue
- Reports

---

## Interview Question

### When should you use ReadWriteLock?

When reads are much more frequent than writes.

---

# 6. StampedLock

> [!NOTE]
> Introduced in Java 8 to improve performance for read-heavy workloads.

Supports:

- Read Lock
- Write Lock
- Optimistic Read

---

## Optimistic Read

```
Read

↓

Validate

↓

Success?
```

If validation fails, retry using a read lock.

---

## Interview Question

### Why is StampedLock faster?

Because optimistic reads often avoid locking.

> [!WARNING]
> `StampedLock` is **not reentrant**.

---

# 7. Lock-Free Programming

> [!NOTE]
> Lock-free programming avoids blocking by using atomic CPU instructions.

Advantages:

- Better scalability
- Lower contention
- No deadlocks caused by locks

Examples:

- AtomicInteger
- ConcurrentLinkedQueue

---

# 8. Compare-And-Swap (CAS)

> [!NOTE]
> CAS updates a value only if it hasn't changed since it was read.

```
Expected = 10

Current = 10

↓

Update to 11 ✅
```

If another thread changes the value first:

```
Expected = 10

Current = 11

↓

Retry
```

---

## Used By

- AtomicInteger
- AtomicLong
- AtomicReference

---

## Interview Question

### Does CAS use locks?

❌ No.

It is a lock-free operation.

---

# 9. Atomic Classes

> [!NOTE]
> Atomic classes perform thread-safe operations using CAS.

Examples:

- AtomicInteger
- AtomicLong
- AtomicBoolean
- AtomicReference

---

## Example

```java
AtomicInteger counter = new AtomicInteger();

counter.incrementAndGet();
```

---

## AtomicInteger vs synchronized

| AtomicInteger | synchronized |
|---------------|--------------|
| Lock-free | Uses lock |
| Faster for counters | Better for complex logic |
| CAS based | Monitor based |

---

# 10. LongAdder

> [!NOTE]
> `LongAdder` is designed for high-contention counters.

Instead of one counter:

```
Counter
```

It uses multiple internal counters:

```
Cell1

Cell2

Cell3

↓

Sum
```

Multiple threads update different cells, reducing contention.

---

## Example

```java
LongAdder counter = new LongAdder();

counter.increment();

System.out.println(counter.sum());
```

---

## LongAdder vs AtomicInteger

| LongAdder | AtomicInteger |
|------------|---------------|
| Better under heavy contention | Better under low contention |
| Multiple counters | Single counter |
| Higher throughput | Simpler |

---

## Interview Question

### When should you prefer LongAdder?

For frequently updated counters with many concurrent threads.

---

# 📝 Part 3 Summary

- Lock API provides more control than `synchronized`.
- `ReentrantLock` supports timeout, fairness and interruptible locking.
- Fair locks prevent starvation but reduce throughput.
- `ReadWriteLock` allows multiple readers and one writer.
- `StampedLock` improves read-heavy performance using optimistic reads.
- CAS enables lock-free programming.
- Atomic classes use CAS for thread safety.
- `LongAdder` performs better than `AtomicInteger` under heavy contention.

---

# ⭐ Top Interview Questions

1. Why use `Lock` instead of `synchronized`?
2. What is `ReentrantLock`?
3. Why is it called "Reentrant"?
4. Fair vs Unfair Lock?
5. What happens if `unlock()` is not called?
6. When should you use `ReadWriteLock`?
7. What is `StampedLock`?
8. Why is `StampedLock` not reentrant?
9. What is CAS?
10. How does `AtomicInteger` work internally?
11. `AtomicInteger` vs `LongAdder`?
12. When should you use `LongAdder`?
13. What is lock-free programming?
14. Does CAS use locks?
15. `synchronized` vs `ReentrantLock`?

---

➡️ **Next Part:** Thread Communication
