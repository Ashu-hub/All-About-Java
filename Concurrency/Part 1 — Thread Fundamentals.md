# Java Concurrency Interview Handbook
## Part 1 – Thread Fundamentals

---

# 📚 Table of Contents

- [1. Process vs Thread](#1-process-vs-thread)
- [2. Thread Lifecycle](#2-thread-lifecycle)
- [3. Creating Threads](#3-creating-threads)
- [4. start() vs run()](#4-start-vs-run)
- [5. sleep() vs yield() vs join()](#5-sleep-vs-yield-vs-join)
- [6. Thread Priority](#6-thread-priority)
- [7. Daemon Threads](#7-daemon-threads)
- [Part 1 Summary](#part-1-summary)
- [Top Interview Questions](#top-interview-questions)

---

# 1. Process vs Thread

> [!NOTE]
> **Quick Revision**
> - **Process** → Independent program with its own memory.
> - **Thread** → Lightweight execution unit inside a process.
> - Threads share the **Heap** but have separate **Stacks**.

### Why Threads?
- Handle multiple tasks concurrently.
- Improve responsiveness.
- Better CPU utilization.

**Example**
- Request Processing
- Logging
- Notification Sending

## Interview Questions

### Q1. Process vs Thread?

| Process | Thread |
|---------|--------|
| Own memory | Shared Heap |
| Heavyweight | Lightweight |
| IPC | Shared Memory |

### Q2. Why are threads faster?
Because they share memory and have lower context-switch overhead.

> [!WARNING]
> **Interview Trap**
>
> ❌ Every thread has its own Heap.
>
> ✅ Threads share Heap; each has its own Stack.

> [!TIP]
> **Revision:** Process owns resources; Thread executes work.

---

# 2. Thread Lifecycle

```text
NEW
  │ start()
RUNNABLE
  │
BLOCKED / WAITING / TIMED_WAITING
  │
TERMINATED
```

| State | Meaning |
|------|---------|
| NEW | Created |
| RUNNABLE | Ready/Running |
| BLOCKED | Waiting for lock |
| WAITING | Waiting indefinitely |
| TIMED_WAITING | Waiting with timeout |
| TERMINATED | Finished |

> [!WARNING]
> ❌ RUNNABLE doesn't always mean executing.
>
> ✅ It means ready **or** running.

---

# 3. Creating Threads

> [!NOTE]
> **Preferred Order**
>
> ExecutorService > Callable > Runnable > Thread

| Feature | Thread | Runnable | Callable |
|---------|---------|----------|----------|
| Return Value | ❌ | ❌ | ✅ |
| Checked Exception | ❌ | ❌ | ✅ |

### Runnable

```java
Runnable task = () -> System.out.println("Running");
new Thread(task).start();
```

### Callable

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(() -> 100);
System.out.println(future.get());
executor.shutdown();
```

### ExecutorService ⭐ Best Practice

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.execute(() -> System.out.println("Running"));
executor.shutdown();
```

> [!TIP]
> In production, prefer **ExecutorService** instead of creating threads manually.

---

# 4. start() vs run()

| start() | run() |
|---------|-------|
| Creates new thread | Normal method call |
| Asynchronous | Synchronous |

> [!WARNING]
> Calling `run()` directly does **not** create a new thread.

---

# 5. sleep() vs yield() vs join()

| Method | Purpose |
|---------|---------|
| sleep() | Pause current thread |
| yield() | Hint scheduler |
| join() | Wait for another thread |

> [!WARNING]
> `sleep()` **does not** release the monitor lock.
>
> `wait()` releases it.

---

# 6. Thread Priority

Priority Range:

```text
1  -> MIN_PRIORITY
5  -> NORM_PRIORITY
10 -> MAX_PRIORITY
```

> [!WARNING]
> Priority is only a **hint**, not a guarantee.

---

# 7. Daemon Threads

Background threads like:
- Garbage Collector
- JIT Compiler

```java
Thread t = new Thread(task);
t.setDaemon(true);
t.start();
```

> [!TIP]
> Call `setDaemon(true)` **before** `start()`.

| User Thread | Daemon Thread |
|-------------|---------------|
| Keeps JVM alive | Doesn't keep JVM alive |

---

# Part 1 Summary

- Process vs Thread
- Thread Lifecycle
- Creating Threads
- start() vs run()
- sleep() / yield() / join()
- Thread Priority
- Daemon Threads

---

# Top Interview Questions

1. Process vs Thread?
2. Thread Lifecycle?
3. Runnable vs Callable?
4. start() vs run()?
5. sleep() vs wait()?
6. sleep() vs join()?
7. Why ExecutorService?
8. What is a Daemon Thread?

**Next:** Part 2 – Synchronization & Java Memory Model
