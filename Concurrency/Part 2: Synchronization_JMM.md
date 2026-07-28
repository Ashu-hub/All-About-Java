# Java Concurrency Interview Handbook
# Part 2 – Synchronization & Java Memory Model (JMM)

# 📚 Table of Contents

- [1. Why Synchronization?](#1-why-synchronization)
- [2. Critical Section](#2-critical-section)
- [3. Race Condition](#3-race-condition)
- [4. synchronized Keyword](#4-synchronized-keyword)
- [5. Monitor Lock](#5-monitor-lock)
- [6. Reentrant Synchronization](#6-reentrant-synchronization)
- [7. Object Lock vs Class Lock](#7-object-lock-vs-class-lock)
- [8. volatile](#8-volatile)
- [9. Java Memory Model (JMM)](#9-java-memory-model-jmm)
- [10. Happens-Before Relationship](#10-happens-before-relationship)
- [11. Visibility vs Atomicity vs Ordering](#11-visibility-vs-atomicity-vs-ordering)
- [12. Deadlock](#12-deadlock)
- [13. Livelock](#13-livelock)
- [14. Starvation](#14-starvation)
- [15. Thread Safety](#15-thread-safety)
- [16. Immutable Objects](#16-immutable-objects)
- [17. Atomic Interger](#17-AtomicInterger)

---

# 1. Why Synchronization?

> [!NOTE]
> **Synchronization ensures that only one thread accesses a critical section at a time**, preventing inconsistent or corrupted data.

## Problem

Suppose two threads update the same bank account balance.

```java
balance = balance - 100;
```

If both threads execute simultaneously, the final balance may become incorrect.

```
Thread A                Thread B

Read 1000
                      Read 1000
Write 900
                      Write 900
```

Expected Balance = **800**

Actual Balance = **900**

This problem is called a **Race Condition**.

---

## Why do we need Synchronization?

Synchronization provides:

- ✅ Data Consistency
- ✅ Thread Safety
- ✅ Visibility
- ✅ Mutual Exclusion

---

## Real Project

Updating:

- Inventory
- Bank Account
- In-memory Cache
- Order Status

should never happen concurrently without synchronization.

---

## Interview Questions

### Why is synchronization needed?

To prevent multiple threads from modifying shared data simultaneously.

### Does synchronization improve performance?

❌ No.

It improves **correctness**, sometimes at the cost of performance.

---

> [!TIP]
> Synchronization is required only when **multiple threads share mutable data**.

---

# 2. Critical Section

> [!NOTE]
> A **Critical Section** is the part of code that accesses shared mutable resources.

Example:

```java
count++;
```

Only one thread should execute this code at a time.

```
Thread A
     │
     ▼
Critical Section
     ▲
     │
Thread B waits
```

---

## Interview Question

### What is a Critical Section?

A block of code that accesses shared resources and therefore must not be executed by multiple threads simultaneously.

---

## Real Project

```
Updating account balance

↓

Critical Section

↓

Needs synchronization
```

---

# 3. Race Condition

> [!NOTE]
> A **Race Condition** occurs when multiple threads access and modify shared data concurrently, and the final result depends on the execution order.

Example

```java
count++;
```

Internally

```
Read

↓

Increment

↓

Write
```

If two threads perform these steps together:

```
Thread A : Read 10

Thread B : Read 10

Thread A : Write 11

Thread B : Write 11
```

Expected = **12**

Actual = **11**

---

## How to Prevent?

- synchronized
- Lock
- AtomicInteger
- Concurrent Collections

---

> [!WARNING]
> Race Condition = Shared Data + Multiple Threads + No Synchronization

---

# 4. synchronized Keyword

> [!NOTE]
> `synchronized` allows only **one thread** to execute a critical section at a time.

Example

```java
public synchronized void increment() {
    count++;
}
```

Or

```java
synchronized(this){
    count++;
}
```

---

## How it Works

```
Thread-1

↓

Acquire Monitor Lock

↓

Execute

↓

Release Lock

↓

Thread-2 enters
```

---

## Guarantees

| Feature | Supported | Definition |
|----------|-----------|-----------|
| Mutual Exclusion | ✅ || 
| Visibility | ✅ | |
| Atomicity | ✅ | Atomicity means an operation is executed as one indivisible unit—it either completes entirely or doesn't happen at all.|
| Reentrant | ✅ | Reentrant means the same thread can acquire the same lock multiple times without blocking itself |

---

## Interview Questions

### Why use synchronized?

To make shared data thread-safe.

### Does synchronized guarantee visibility?

✅ Yes.

### Is synchronized reentrant?

✅ Yes.

### Can constructors be synchronized?

❌ No.

### Can synchronized methods deadlock?

✅ Yes.

---

> [!WARNING]
> `synchronized` prevents race conditions but **cannot prevent deadlocks**.

---

# 5. Monitor Lock

> [!NOTE]
> Every Java object has an **intrinsic monitor lock**.

Entering a synchronized block:

```
Acquire Monitor

↓

Execute

↓

Release Monitor
```

Only one thread owns a monitor at a time.

---

## Example

```java
synchronized(this){
    count++;
}
```

Here,

```
this

↓

Monitor Lock
```

---

## Interview Question

### Is the monitor lock explicit?

No.

JVM manages it automatically.

---

# 6. Reentrant Synchronization

> [!NOTE]
> **Reentrant** means **a thread that already owns a lock can acquire the same lock again without getting blocked.**

---

## Why is it needed?

Suppose a synchronized method calls another synchronized method of the **same object**.

Without reentrancy, the thread would wait for a lock that it already owns, causing a **self-deadlock**.

With reentrancy, Java allows the same thread to enter the lock multiple times.

---

## Example

```java
class Demo {

    public synchronized void methodA() {
        System.out.println("Inside methodA");
        methodB();      // Same thread enters the same lock again
    }

    public synchronized void methodB() {
        System.out.println("Inside methodB");
    }
}
```

### Execution Flow

```text
Thread-1
   │
   ▼
methodA()
   │
Acquire Lock (Count = 1)
   │
   ▼
methodB()
   │
Acquire Same Lock Again (Count = 2)
   │
   ▼
methodB() Returns
Release Lock (Count = 1)
   │
   ▼
methodA() Returns
Release Lock (Count = 0)
```

The lock is released **only when the hold count becomes 0**.

---

## How does it work?

Every monitor lock maintains:

- **Owner Thread** → Which thread currently owns the lock.
- **Hold Count** → Number of times the owner has acquired the lock.

Example:

| Step | Hold Count |
|------|-----------:|
| Enter `methodA()` | 1 |
| Enter `methodB()` | 2 |
| Exit `methodB()` | 1 |
| Exit `methodA()` | 0 (Lock Released) |

---

## Why is it useful?

Imagine a service method calling another synchronized helper method.

```java
public synchronized void processOrder() {
    validateOrder();    // Also synchronized
}

public synchronized void validateOrder() {
    // Validation logic
}
```

Without reentrant locks, this code would deadlock because `processOrder()` already holds the lock.

---

## Interview Questions

### Q1. What is a reentrant lock?

A lock that allows the **same thread** to acquire it multiple times without blocking.

---

### Q2. Is `synchronized` reentrant?

✅ Yes.

The JVM keeps a **hold count** for each monitor lock.

---

### Q3. Is `ReentrantLock` also reentrant?

✅ Yes.

It provides the same behaviour as `synchronized`, with additional features like fairness, timeout, and interruptible locking.

---

### Q4. Can another thread acquire the lock while the hold count is greater than 0?

❌ No.

Only the owning thread can re-enter the lock. Other threads remain blocked until the hold count reaches **0**.

---

> [!WARNING]
> **Reentrant** does **not** mean multiple threads can enter a synchronized block together.
>
> It only means **the same thread** can acquire the **same lock** multiple times.

---

> [!TIP]
> **Remember:**
>
> - Same thread + Same lock → ✅ Allowed (Reentrant)
> - Different thread + Same lock → ❌ Must wait

---

## One-Line Revision

> **Reentrant = A thread can safely acquire the same lock multiple times; the lock is released only after all acquisitions are matched by exits.**
---

# 7. Object Lock vs Class Lock

| Object Lock | Class Lock |
|-------------|------------|
| Instance method | Static method |
| One lock/object | One lock/class |

Example

```java
public synchronized void m1(){}
```

```
Locks

this
```

```java
public static synchronized void m2(){}
```

```
Locks

Demo.class
```

---

# 8. volatile

> [!NOTE]
> `volatile` guarantees **Visibility**, not **Atomicity**.

Without volatile

```
Thread A

↓

CPU Cache

↓

Main Memory

↓

Thread B

May read stale value
```

With volatile

```
Main Memory

↓

Latest value

↓

All Threads
```

---

## Guarantees

✅ Visibility

✅ Prevents Instruction Reordering

❌ Atomicity

---

## Example

```java
volatile boolean running = true;
```

---

## Interview Trap

❌ volatile makes `count++` thread-safe.

✔ No.

---

# 9. Java Memory Model (JMM)

> [!NOTE]
> JMM defines how threads read and write shared variables.

It guarantees:

- Visibility
- Ordering
- Synchronization

```
Thread

↓

Working Memory

↓

Main Memory

↓

Another Thread
```

---

# 10. Happens-Before Relationship

> [!NOTE]
> If **A Happens-Before B**, then B sees all changes made by A.

Examples

- Unlock → Lock
- volatile Write → volatile Read
- Thread.start()
- Thread.join()

---

# 11. Visibility vs Atomicity vs Ordering

| Property | Meaning |
|-----------|---------|
| Visibility | Latest value is visible |
| Atomicity | Complete operation or nothing |
| Ordering | Instructions execute in expected order |

# Visibility vs Atomicity

| Visibility | Atomicity |
|------------|-----------|
| Ensures a thread sees the **latest value** of a variable. | Ensures an operation completes as **one indivisible unit**. |
| Solved by `volatile`, `synchronized`. | Solved by `synchronized`, `Lock`, `AtomicInteger`. |
| Concerned with **reading the latest value**. | Concerned with **preventing partial/interleaved execution**. |

### Example

```java
volatile boolean running = true;
```

If one thread changes `running` to `false`, all other threads immediately see the latest value.

---

```java
count++;
```

Although it looks like one statement, it is **not atomic**.

It actually performs:

```text
Read count
    ↓
Increment
    ↓
Write count
```

Another thread can interrupt between these steps, leading to incorrect results.

---

## Interview Question

### Why is `count++` not atomic?

Because `count++` is a **Read → Modify → Write** operation.

Suppose `count = 10`.

```text
Thread A               Thread B

Read 10
                      Read 10
Increment → 11
                      Increment → 11
Write 11
                      Write 11
```

### Expected

```text
12
```

### Actual

```text
11 ❌
```

One update is lost because both threads read the same old value.

---

## How to make it atomic?

### Option 1: synchronized

```java
synchronized (this) {
    count++;
}
```

### Option 2: AtomicInteger

```java
AtomicInteger count = new AtomicInteger();

count.incrementAndGet();
```

---

> [!TIP]
> **Memory Trick**
>
> - **Visibility** → "Can other threads see my latest value?"
> - **Atomicity** → "Can another thread interrupt this operation?"
---

# 12. Deadlock

> [!WARNING]
> Two or more threads wait forever for each other's locks.

```
Thread A

Lock1

↓

Waiting Lock2

Thread B

Lock2

↓

Waiting Lock1
```

### Prevention

- Lock ordering
- tryLock()
- Timeout

---

# 13. Livelock

> [!NOTE]
> Threads keep responding to each other but never make progress.

Example:

Both threads repeatedly release locks to let the other proceed, so neither finishes.

---

# 14. Starvation

> [!NOTE]
> A thread never gets CPU time or the required lock because other threads continuously take precedence.

Example:

A low-priority thread waits indefinitely while higher-priority threads keep running.

---

# 15. Thread Safety

A class is **Thread-Safe** if multiple threads can use it safely without corrupting data.

Achieved by:

- synchronized
- Lock
- Atomic Classes
- Concurrent Collections
- Immutable Objects

---

# 16. Immutable Objects

> [!NOTE]
> Immutable objects cannot change after creation.

Examples

- String
- Record
- LocalDate

Benefits

- Thread-safe
- No synchronization required
- Easy to share across threads

---

# 17. AtomicInteger:

> [!NOTE]
> `AtomicInteger` is a thread-safe class (`java.util.concurrent.atomic`) that performs atomic operations **without using `synchronized`**.

---

## Why do we need it?

Consider:

```java
int count = 0;

count++;
```

This is **not thread-safe**.

Internally:

```text
Read
  ↓
Increment
  ↓
Write
```

If two threads execute these steps simultaneously, one update may be lost.

---

## Solution

```java
AtomicInteger count = new AtomicInteger(0);

count.incrementAndGet();
```

Now every increment is **atomic**.

---

## How does AtomicInteger work?

`AtomicInteger` uses **CAS (Compare-And-Swap)**, a CPU-supported atomic instruction.

Instead of locking the entire code, it says:

```text
"If the current value is still what I expect,
update it.
Otherwise, try again."
```

---

## CAS Flow

Suppose:

```text
count = 10
```

Thread A wants to increment.

```
Read Current Value = 10

↓

Expected = 10

↓

New Value = 11

↓

Is Current Value still 10?

↓

YES

↓

Update to 11 ✅
```

---

Now suppose Thread B also read **10**.

```
Current Value = 11

↓

Expected = 10

↓

Match?

↓

NO ❌

↓

Read Again

↓

Retry
```

Eventually Thread B updates:

```
11

↓

12 ✅
```

Final Result:

```
12
```

No update is lost.

---

## Why is it Thread-Safe?

Because **only one thread can successfully update the value for a given expected value**.

Other threads automatically retry until they succeed.

No data corruption occurs.

---

## Why is it Faster than synchronized?

### synchronized

```
Thread A

↓

Acquire Lock

↓

Execute

↓

Release Lock

↓

Thread B waits
```

Only one thread executes while others are blocked.

---

### AtomicInteger

```
Thread A

↓

CAS

↓

Success

Thread B

↓

CAS Failed

↓

Retry
```

No thread is blocked.

---

## Common Methods

| Method | Purpose |
|---------|---------|
| `get()` | Returns current value |
| `set()` | Updates value |
| `incrementAndGet()` | Increment then return |
| `getAndIncrement()` | Return then increment |
| `decrementAndGet()` | Decrement then return |
| `addAndGet(n)` | Add and return |
| `compareAndSet(old, new)` | Update only if current value equals `old` |

---

## compareAndSet()

```java
AtomicInteger count = new AtomicInteger(10);

boolean updated = count.compareAndSet(10, 20);

System.out.println(updated); // true
System.out.println(count.get()); // 20
```

If the current value were **11**, the update would fail.

---

## Real Project Example

Count the number of API requests safely.

```java
AtomicInteger requestCount = new AtomicInteger();

public void processRequest() {
    requestCount.incrementAndGet();
}
```

Thousands of threads can increment the counter safely without explicit locks.

---

## Interview Questions

### Why is AtomicInteger thread-safe?

Because it uses **CAS (Compare-And-Swap)** to update the value atomically without locks.

---

### Does AtomicInteger use synchronized?

❌ No.

It relies on CPU-level atomic instructions.

---

### Is AtomicInteger lock-free?

✅ Yes.

It is **lock-free**, but it may retry multiple times if CAS fails.

---

### AtomicInteger vs synchronized

| AtomicInteger | synchronized |
|---------------|--------------|
| Lock-free | Uses monitor lock |
| Faster for simple operations | Better for complex critical sections |
| Uses CAS | Uses locking |
| Suitable for counters | Suitable for multiple shared variables |

---

> [!WARNING]
> `AtomicInteger` makes **a single variable** atomic.
>
> It **cannot** make multiple operations or multiple variables atomic together.

Example:

```java
if (count.get() > 10) {
    count.incrementAndGet();
}
```

This entire block is **not atomic**.

Use `synchronized` or `Lock` if multiple operations must execute together.

---

## One-Line Revision

> **AtomicInteger uses CAS (Compare-And-Swap) to perform lock-free, thread-safe atomic operations on a single integer.**
---
# 📝 Part 2 Summary

- Understand why synchronization is required.
- Identify critical sections.
- Prevent race conditions.
- Know how `synchronized` and monitor locks work.
- Understand reentrancy.
- Differentiate object lock and class lock.
- Know when to use `volatile`.
- Understand JMM and Happens-Before.
- Distinguish visibility, atomicity and ordering.
- Recognize deadlock, livelock and starvation.
- Write thread-safe code.

---

# ⭐ Top Interview Questions

1. Why do we need synchronization?
2. What is a critical section?
3. Explain race condition with an example.
4. How does `synchronized` work internally?
5. What is a monitor lock?
6. Why is `synchronized` reentrant?
7. Object lock vs Class lock?
8. `volatile` vs `synchronized`?
9. Explain Java Memory Model.
10. What is Happens-Before?
11. Visibility vs Atomicity?
12. Why is `count++` not atomic?
13. How do you prevent deadlocks?
14. Thread-safe vs Immutable?
15. Why is `String` thread-safe?

---

➡️ **Next Part:** Locks, Atomic Classes & Concurrent Collections
