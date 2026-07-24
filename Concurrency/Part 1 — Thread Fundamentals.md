# Java Concurrency Interview Handbook
# Part 1 – Thread Fundamentals

---

# Table of Contents

1. Process vs Thread
2. Thread Lifecycle
3. Creating Threads
4. start() vs run()
5. sleep() vs yield() vs join()
6. Thread Priority
7. Daemon Threads

---

# 1. Process vs Thread

## Quick Revision

- **Process** = Independent program with its own memory.
- **Thread** = Lightweight execution unit inside a process.
- Multiple threads share the same heap but have separate stacks.

### Why do we need Threads?

Running tasks concurrently improves responsiveness and CPU utilization.

Example:
- One thread handles HTTP requests.
- Another writes logs.
- Another sends notifications.

---

## Interview Questions

### Q1. Difference between Process and Thread?

| Process | Thread |
|----------|---------|
| Independent program | Smallest execution unit |
| Separate memory | Shared heap memory |
| Heavyweight | Lightweight |
| Costly context switch | Faster context switch |
| Communication via IPC | Shared memory |

---

### Q2. Why are threads faster than processes?

Because they share memory and require less overhead during context switching.

---

### Q3. Can one process have multiple threads?

Yes. A JVM process can contain many threads.

Examples:
- Main Thread
- GC Thread
- Finalizer Thread
- Virtual Threads (Java 21)

---

## Real Project Usage

A Spring Boot application serves thousands of requests using multiple threads from a thread pool.

---

## Interview Trap

❌ Each thread has its own Heap.

✔ Threads share Heap but have separate Stacks.

---

## One-Line Revision

> **Process owns resources; Thread executes work.**

---

# 2. Thread Lifecycle

## Quick Revision

```
NEW
  │
start()
  │
RUNNABLE
  │
Running
  │
┌──────────────┐
│ BLOCKED      │
│ WAITING      │
│ TIMED_WAITING│
└──────────────┘
  │
TERMINATED
```

---

## Thread States

| State | Meaning |
|--------|---------|
| NEW | Created but not started |
| RUNNABLE | Ready or running |
| BLOCKED | Waiting for monitor lock |
| WAITING | Waiting indefinitely |
| TIMED_WAITING | Waiting for specified time |
| TERMINATED | Execution completed |

---

## Interview Questions

### Q1. What are the lifecycle states of a thread?

NEW → RUNNABLE → BLOCKED / WAITING / TIMED_WAITING → TERMINATED

---

### Q2. Difference between BLOCKED and WAITING?

| BLOCKED | WAITING |
|----------|----------|
| Waiting for a lock | Waiting for another thread/event |
| Example: synchronized | Example: join(), wait() |

---

### Q3. Can a terminated thread be restarted?

No.

Calling `start()` again throws:

```
IllegalThreadStateException
```

---

## Interview Trap

❌ RUNNABLE means thread is executing.

✔ RUNNABLE means ready **or** executing.

---

## One-Line Revision

> **A thread can be started only once.**

---

# 3. Creating Threads

## Quick Revision

Java provides three common approaches:

- Thread class
- Runnable
- Callable + ExecutorService

---

## Thread vs Runnable vs Callable

| Feature | Thread | Runnable | Callable |
|----------|---------|-----------|-----------|
| Returns value | ❌ | ❌ | ✅ |
| Throws checked exception | ❌ | ❌ | ✅ |
| Preferred today | ❌ | ✅ | ✅ |

---

## Interview Questions

### Q1. What are the ways to create a thread?

1. Extend Thread
2. Implement Runnable
3. Implement Callable
4. Executor Framework (Recommended)

## Creating Threads - Examples

### 1. Extending Thread

```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Running...");
    }
}

new MyThread().start();
```

---

### 2. Implementing Runnable ⭐ (Preferred)

```java
Runnable task = () -> System.out.println("Running...");
new Thread(task).start();
```

---

### 3. Using Callable (Returns Result)

```java
ExecutorService executor = Executors.newSingleThreadExecutor();

Future<Integer> future = executor.submit(() -> 100);

System.out.println(future.get());

executor.shutdown();
```

---

### 4. Using ExecutorService ⭐⭐⭐ (Best Practice)

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

executor.execute(() -> System.out.println("Running..."));

executor.shutdown();
```

> **Interview Tip:** In real applications, prefer **ExecutorService** over creating threads manually.


---

### Q2. Why is Runnable preferred over Thread?

Java supports single inheritance.

Implementing Runnable keeps inheritance available.

---

### Q3. When should you use Callable?

When a task:

- Returns a value
- Throws checked exceptions

---

## Interview Trap

❌ Creating a Thread means a new thread starts.

✔ Only `start()` creates a new thread.

---

## One-Line Revision

> **Use ExecutorService with Runnable/Callable in real applications.**

---

# 4. start() vs run()

## Quick Revision

`start()` creates a new thread.

`run()` executes normally in the current thread.

---

## Comparison

| start() | run() |
|----------|--------|
| Creates new thread | No new thread |
| Calls run() internally | Normal method call |
| Executes asynchronously | Executes synchronously |

---

## Interview Questions

### Q1. What happens when `start()` is called?

JVM creates a new thread and eventually invokes `run()`.

---

### Q2. What happens if `run()` is called directly?

No new thread is created.

Everything executes in the caller thread.

---

### Q3. Can `start()` be called twice?

No.

Throws:

```
IllegalThreadStateException
```

---

## Interview Trap

❌ start() immediately runs the thread.

✔ It only schedules the thread.

---

## One-Line Revision

> **Never call `run()` to start concurrency.**

---

# 5. sleep() vs yield() vs join()

## Quick Revision

| Method | Purpose |
|----------|----------|
| sleep() | Pause current thread |
| yield() | Hint scheduler to switch threads |
| join() | Wait for another thread |

---

## Interview Questions

### Q1. Does sleep() release the lock?

No.

The thread sleeps while still holding the monitor lock.

---

### Q2. What is join()?

Makes the current thread wait until another thread completes.

Example:

```java
thread.start();
thread.join();
System.out.println("Completed");
```

---

### Q3. Is yield() guaranteed to switch threads?

No.

It is only a scheduling hint.

---

## Comparison

| sleep() | wait() |
|-----------|---------|
| Doesn't release lock | Releases lock |
| Time-based | Event-based |

---

## Interview Trap

❌ sleep() releases monitor lock.

✔ Only `wait()` releases the monitor.

---

## One-Line Revision

> **join() waits, sleep() pauses, yield() suggests.**

---

# 6. Thread Priority

## Quick Revision

Thread priority suggests which thread should receive CPU preference.

Range:

```
1 (MIN_PRIORITY)
5 (NORM_PRIORITY)
10 (MAX_PRIORITY)
```

---

## Interview Questions

### Q1. Does higher priority guarantee execution first?

No.

Scheduling depends on the operating system and JVM.

---

### Q2. Should business logic depend on priority?

No.

Never rely on priority for correctness.

---

## Interview Trap

❌ Priority determines execution order.

✔ It is only a scheduling hint.

---

## One-Line Revision

> **Thread priority is a hint, not a guarantee.**

---

# 7. Daemon Threads

## Quick Revision

Daemon threads run in the background and support user threads.

Examples:

- Garbage Collector
- JIT Compiler

---

## Interview Questions

### Q1. What is a daemon thread?

A background thread that automatically terminates when all user threads finish.

---

### Q2. How do you create one?

```java
Thread t = new Thread(task);
t.setDaemon(true);
t.start();
```

Must be called **before** `start()`.

---

### Q3. Can the JVM exit while daemon threads are running?

Yes.

Daemon threads do not keep the JVM alive.

---

## User Thread vs Daemon Thread

| User Thread | Daemon Thread |
|--------------|---------------|
| Keeps JVM alive | Doesn't keep JVM alive |
| Business tasks | Background tasks |

---

## Interview Trap

❌ Daemon threads always complete execution.

✔ JVM may terminate them abruptly.

---

## One-Line Revision

> **Only user threads keep the JVM alive.**

---

# Part 1 Summary

## Must Remember

- Process owns resources; Thread executes tasks.
- Thread lifecycle: NEW → RUNNABLE → BLOCKED/WAITING → TERMINATED.
- Prefer `Runnable`/`Callable` with `ExecutorService`.
- `start()` creates a new thread; `run()` does not.
- `sleep()` pauses, `join()` waits, `yield()` is only a hint.
- Thread priority is not guaranteed.
- Daemon threads stop when all user threads finish.

---

# Top Interview Questions

1. Process vs Thread?
2. Explain the Thread Lifecycle.
3. Runnable vs Callable?
4. Why is Runnable preferred over Thread?
5. start() vs run()?
6. What happens if start() is called twice?
7. sleep() vs wait()?
8. sleep() vs join()?
9. Does sleep() release the monitor lock?
10. What is a daemon thread?
11. User Thread vs Daemon Thread?
12. Does thread priority guarantee execution order?

---
**Next Part:** Synchronization & Java Memory Model
