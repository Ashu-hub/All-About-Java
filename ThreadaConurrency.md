# Executor Framework

## Why do we need Executor?

Before Java 5, we used to create threads manually.

```java
Thread t = new Thread(() -> {
    System.out.println("Task");
});
t.start();
```

### Problems

- Creating a thread is expensive.
- Too many threads can exhaust memory.
- No thread reuse.
- Difficult to manage thread lifecycle.

Java introduced the **Executor Framework** (`java.util.concurrent`) to separate:

- **What to execute** (Task)
- **How to execute** (Thread Management)

Instead of creating threads yourself, you submit tasks to an executor, which decides **when**, **where**, and **how** to execute them.

---

# Executor Framework

```
Task (Runnable/Callable)
          │
          ▼
      Executor Framework
          │
          ▼
     Thread Pool / Threads
```

---

# Interface Hierarchy

```
Executor (Interface)
        │
        ▼
ExecutorService (Interface)
        │
        ▼
ScheduledExecutorService (Interface)
        │
        ▼
-------------------------------------
Implementations
-------------------------------------
ThreadPoolExecutor
ScheduledThreadPoolExecutor
ForkJoinPool
```

The `Executors` class is a **factory class** that creates these implementations.

Example:

```java
ExecutorService service = Executors.newFixedThreadPool(4);
```

Here:

- `ExecutorService` → Interface
- `Executors` → Factory Class
- `ThreadPoolExecutor` → Actual implementation returned

---

# 1. Executor (Interface)

`Executor` is the **base interface** of the Executor Framework.

```java
public interface Executor
```

It contains only one method:

```java
void execute(Runnable task);
```

### Responsibility

Its only responsibility is:

> Accept a task and execute it.

It **does not** provide:

- Returning results
- Thread pool shutdown
- Task cancellation
- Task status
- Waiting for task completion

It simply defines **how tasks are submitted**.

---

# 2. ExecutorService (Interface)

`ExecutorService` **extends** `Executor`.

```java
public interface ExecutorService extends Executor
```

It inherits `execute()` and adds thread pool management capabilities.

### Responsibilities

- Execute tasks
- Submit tasks that return results
- Manage thread pool lifecycle
- Cancel running tasks
- Wait for task completion
- Execute multiple tasks together

This is the interface you'll use in **95% of real-world applications**.

---

# Main Methods of ExecutorService

## Task Submission

| Method | Description |
|---------|-------------|
| `execute(Runnable)` | Executes a task; no return value |
| `submit(Runnable)` | Executes a task and returns a `Future<?>` |
| `submit(Callable<T>)` | Executes a task that returns a value |

## Lifecycle Management

| Method | Description |
|---------|-------------|
| `shutdown()` | Stops accepting new tasks and finishes existing ones |
| `shutdownNow()` | Attempts to stop running tasks immediately |
| `awaitTermination()` | Waits until all tasks complete or timeout occurs |

## Batch Execution

| Method | Description |
|---------|-------------|
| `invokeAll()` | Executes all tasks and waits for all to finish |
| `invokeAny()` | Returns the result of the first successfully completed task |

---

# Common Implementations

Although we program against the `ExecutorService` interface, the actual object is usually one of these implementations.

| Class | Purpose |
|--------|---------|
| `ThreadPoolExecutor` | General-purpose thread pool |
| `ScheduledThreadPoolExecutor` | Executes tasks after a delay or periodically |
| `ForkJoinPool` | Optimized for recursive and parallel tasks |

---

# Executors (Factory Class)

We rarely create `ThreadPoolExecutor` directly.

Instead, we use the `Executors` factory class.

```java
ExecutorService fixed =
        Executors.newFixedThreadPool(4);

ExecutorService single =
        Executors.newSingleThreadExecutor();

ExecutorService cached =
        Executors.newCachedThreadPool();

ScheduledExecutorService scheduled =
        Executors.newScheduledThreadPool(2);
```

---

# Interview Summary

| Type | Kind | Responsibility |
|------|------|----------------|
| `Executor` | Interface | Execute tasks |
| `ExecutorService` | Interface | Execute tasks and manage thread pool |
| `ScheduledExecutorService` | Interface | Schedule delayed and periodic tasks |
| `Executors` | Factory Class | Creates executor implementations |
| `ThreadPoolExecutor` | Class | Main implementation of `ExecutorService` |
| `ScheduledThreadPoolExecutor` | Class | Main implementation of `ScheduledExecutorService` |
| `ForkJoinPool` | Class | Specialized pool for recursive and parallel tasks |

---

# Typical Flow

```
Create ExecutorService
        │
        ▼
Submit Runnable/Callable
        │
        ▼
Executor picks an available thread
        │
        ▼
Task executes
        │
        ▼
(Optional) Future returns result
        │
        ▼
Shutdown ExecutorService
```

---

# Top Interview Questions

| Interview Question                                                                                            | 1-Liner Answer                                                                                                                                                                                                                                           |
| ------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **What is the difference between `Executor` and `ExecutorService`?**                                          | `Executor` only executes tasks, whereas `ExecutorService` adds task management, lifecycle control, and result handling.                                                                                                                                  |
| **What is the difference between `execute()` and `submit()`?**                                                | `execute()` is fire-and-forget (`void`), while `submit()` returns a `Future` to retrieve results or exceptions.                                                                                                                                          |
| **What is the difference between `Runnable` and `Callable`?**                                                 | `Runnable` cannot return a value or throw checked exceptions; `Callable` can do both.                                                                                                                                                                    |
| **What is `Future`, and what are its limitations?**                                                           | `Future` represents the result of an asynchronous task, but `get()` blocks until the task completes and it cannot be manually completed or chained.                                                                                                      |
| **Explain `shutdown()` vs `shutdownNow()`.**                                                                  | `shutdown()` allows existing tasks to finish, whereas `shutdownNow()` attempts to interrupt running tasks and returns pending tasks.                                                                                                                     |
| **What are `invokeAll()` and `invokeAny()`, and when would you use each?**                                    | `invokeAll()` waits for all tasks to complete; `invokeAny()` returns the first successful result and cancels the rest.                                                                                                                                   |
| **What types of thread pools does the `Executors` class provide, and what are their trade-offs?**             | Fixed (bounded, stable), Cached (scalable but unbounded), Single (sequential execution), Scheduled (delayed/periodic execution), Work-Stealing (parallelism for CPU-bound tasks), Virtual Thread Per Task (lightweight for I/O-bound tasks in Java 21+). |
| **What happens if tasks are submitted faster than a fixed-size thread pool can execute them?**                | Tasks are queued, increasing latency, and may eventually be rejected if the queue is bounded and full.                                                                                                                                                   |
| **Why is creating a new thread for every request considered a bad practice in high-throughput applications?** | Thread creation is expensive and excessive threads cause high memory usage, context switching, and poor scalability.                                                                                                                                     |
| **What is the lifecycle of an `ExecutorService`?**                                                            | **Created → Accepts Tasks → Executes Tasks → `shutdown()`/`shutdownNow()` → Terminated.**                                                                                                                                                                |
