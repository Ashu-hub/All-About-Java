# Java Concurrency Interview Handbook
# Part 2 – Synchronization & Java Memory Model (JMM)

> **Goal:** Understand how Java safely shares data between multiple threads.

---

# 📚 Table of Contents

- [1. Synchronization (`synchronized`)](#1-synchronization-synchronized)
- [2. Monitor Lock](#2-monitor-lock)
- [3. Object Lock vs Class Lock](#3-object-lock-vs-class-lock)
- [4. volatile](#4-volatile)
- [5. Java Memory Model (JMM)](#5-java-memory-model-jmm)
- [6. Happens-Before Relationship](#6-happens-before-relationship)
- [7. Visibility vs Atomicity vs Ordering](#7-visibility-vs-atomicity-vs-ordering)
- [8. Race Condition](#8-race-condition)
- [9. Deadlock](#9-deadlock)
- [10. Livelock & Starvation](#10-livelock--starvation)
- [11. Thread Safety](#11-thread-safety)
- [12. Immutable Objects](#12-immutable-objects)

---

# 1. Synchronization (`synchronized`)

> [!NOTE]
> Ensures **only one thread** executes the critical section at a time.

### Common Usage

```java
synchronized(this){
    count++;
}
```

### Interview Questions

**Q. Why use synchronization?**

To prevent race conditions while accessing shared mutable data.

**Q. Does synchronized guarantee visibility?**

✅ Yes.

**Q. Is it reentrant?**

✅ Yes.

> [!WARNING]
> `synchronized` doesn't prevent deadlocks.

---

# 2. Monitor Lock

> [!NOTE]
> Every Java object has an **intrinsic monitor lock**.

- Enter synchronized → Acquire monitor
- Exit synchronized → Release monitor

> [!TIP]
> Monitor locks are managed automatically by JVM.

---

# 3. Object Lock vs Class Lock

| Object Lock | Class Lock |
|-------------|------------|
| `synchronized` instance method | `static synchronized` method |
| One lock per object | One lock per class |

---

# 4. volatile

> [!NOTE]
> Guarantees **visibility** and prevents **instruction reordering**.

```java
volatile boolean running = true;
```

### Interview Questions

**Q. Does volatile provide atomicity?**

❌ No

**Q. When should you use it?**

- Stop flags
- Configuration refresh
- Status indicators

> [!WARNING]
> `count++` is **not** thread-safe even if `count` is volatile.

---

# 5. Java Memory Model (JMM)

> [!NOTE]
> Defines how threads interact through memory.

JMM guarantees:
- Visibility
- Ordering
- Synchronization rules

---

# 6. Happens-Before Relationship

If **A Happens-Before B**, then B sees all changes made by A.

Examples:

- Unlock → Lock
- volatile Write → volatile Read
- `Thread.start()`
- `Thread.join()`

> [!TIP]
> Happens-Before guarantees **visibility**, not execution order.

---

# 7. Visibility vs Atomicity vs Ordering

| Property | Meaning |
|----------|---------|
| Visibility | Latest value is visible |
| Atomicity | Operation cannot be interrupted |
| Ordering | Execution order is preserved |

---

# 8. Race Condition

> [!NOTE]
> Two or more threads modify shared data simultaneously, causing unpredictable results.

```java
count++;
```

Actually performs:

```text
Read → Increment → Write
```

---

# 9. Deadlock

> [!WARNING]
> Two or more threads wait forever for each other's locks.

### Prevention

- Consistent lock ordering
- `tryLock()`
- Timeout

---

# 10. Livelock & Starvation

| Problem | Meaning |
|----------|---------|
| Livelock | Threads keep responding but make no progress |
| Starvation | Thread never gets CPU/lock |

---

# 11. Thread Safety

A class is thread-safe if multiple threads can use it safely without data corruption.

### Achieved By

- Synchronization
- Immutable objects
- Concurrent collections
- Atomic classes

---

# 12. Immutable Objects

> [!NOTE]
> Immutable objects cannot change after creation.

Examples:

- `String`
- Java Records
- Custom immutable classes

### Benefits

- Thread-safe
- No synchronization required

---

# Part 2 Summary

- synchronized
- Monitor Lock
- Object vs Class Lock
- volatile
- JMM
- Happens-Before
- Visibility / Atomicity / Ordering
- Race Condition
- Deadlock
- Livelock
- Starvation
- Thread Safety
- Immutable Objects

---

# Top Interview Questions

1. synchronized vs volatile?
2. What is a monitor lock?
3. Object lock vs Class lock?
4. What is Happens-Before?
5. Explain JMM.
6. Visibility vs Atomicity?
7. Why isn't `count++` atomic?
8. How do you prevent deadlocks?
9. What is a race condition?
10. Why are immutable objects thread-safe?

**Next:** Part 3 – Locks & Atomic Operations
