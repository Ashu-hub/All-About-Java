# Part 4 — Thread Communication

> **Goal:** Learn how threads coordinate with each other instead of just protecting shared data.
>
> Synchronization prevents **multiple threads from corrupting data**, whereas thread communication allows **threads to coordinate their work**.

---

# Table of Contents

1. Why Thread Communication?
2. Producer-Consumer Problem
3. Object Monitor
4. wait(), notify(), notifyAll()
5. wait() vs sleep()
6. Lost Notification Problem
7. Spurious Wakeup
8. Guarded Blocks
9. Thread Join
10. CountDownLatch
11. CyclicBarrier
12. Phaser
13. Exchanger
14. Semaphore
15. BlockingQueue
16. Interview Questions
17. Summary

---

# 1. Why Thread Communication?

Imagine two threads:

- Producer thread creates data.
- Consumer thread processes data.

Question:

> What happens if the consumer runs before the producer?

Without communication:

```
Consumer -> Queue Empty
Producer -> Adds Data Later
```

Consumer either:

- keeps checking repeatedly (busy waiting ❌)
- crashes
- receives incorrect result

Instead, the consumer should simply wait until data becomes available.

That's exactly what thread communication solves.

---

## Real-life Example

Restaurant analogy:

```
Chef (Producer)
        |
        | Food Ready
        v
Waiter (Consumer)
```

Without communication:

```
Waiter:
"Food ready?"

"No."

"Food ready?"

"No."

"Food ready?"

"No."
```

This wastes CPU.

Better approach:

```
Waiter:
I'll wait.

Chef:
Food is ready!

Waiter:
Thanks!
```

---

# 2. Producer-Consumer Problem

This is the classic interview problem.

Suppose we have a queue of size 5.

```
Producer ---> Queue ---> Consumer
```

Producer:

```
Add Item
```

Consumer:

```
Remove Item
```

Problems:

### Queue Full

Producer should wait.

```
Queue

[A]
[B]
[C]
[D]
[E]

Producer:
Cannot add.
Wait.
```

---

### Queue Empty

Consumer waits.

```
Queue

(empty)

Consumer:
Nothing to consume.
Wait.
```

This is solved using:

- wait()
- notify()

or modern classes like

- BlockingQueue

---

# 3. Object Monitor

Every Java object has an associated monitor (lock).

When you write:

```java
synchronized(lock){
}
```

Thread acquires that object's monitor.

That same monitor also provides communication methods:

```
wait()

notify()

notifyAll()
```

These methods belong to:

```java
java.lang.Object
```

Not Thread.

---

## Why Object?

Because communication happens **through a shared lock**.

Example

```
Producer

synchronized(queue){
    queue.wait();
}
```

Consumer

```
synchronized(queue){
    queue.notify();
}
```

Both communicate through the same monitor.

---

# 4. wait(), notify(), notifyAll()

These are the fundamental communication methods.

---

## wait()

Definition:

> Releases the lock and suspends the current thread until another thread wakes it.

Syntax

```java
lock.wait();
```

Important:

- Releases monitor
- Goes into WAITING state
- Must be inside synchronized block

Example

```java
synchronized(lock){

    while(queue.isEmpty()){
        lock.wait();
    }

}
```

Timeline

```
Thread A

Acquire Lock

↓

wait()

↓

Release Lock

↓

Waiting

↓

notify()

↓

Competes for Lock

↓

Continues
```

Notice:

After notify(), the thread does NOT immediately continue.

It first needs to re-acquire the monitor.

---

## notify()

Definition:

> Wakes one waiting thread.

Example

```java
synchronized(lock){

    queue.add(item);

    lock.notify();

}
```

Suppose:

```
Waiting Threads

T1

T2

T3
```

notify() wakes only one.

Which one?

Java does NOT guarantee.

---

## notifyAll()

Wakes every waiting thread.

```
Waiting

T1

T2

T3

↓

notifyAll()

↓

All become RUNNABLE
```

Only one acquires the monitor first.

Others continue waiting for the monitor.

---

## Why use notifyAll()?

Imagine:

```
Producer

Consumer A

Consumer B
```

Producer adds one item.

If notify() wakes the wrong thread, progress may stop.

notifyAll() is generally safer.

---

# 5. wait() vs sleep()

Very common interview question.

| Feature | wait() | sleep() |
|----------|---------|----------|
| Class | Object | Thread |
| Releases Lock? | ✅ Yes | ❌ No |
| Requires synchronized? | ✅ Yes | ❌ No |
| Purpose | Communication | Delay |
| State | WAITING | TIMED_WAITING |

---

Example

### sleep()

```java
synchronized(lock){

    Thread.sleep(5000);

}
```

Lock is still held.

Other threads cannot enter.

---

wait()

```java
synchronized(lock){

    lock.wait();

}
```

Lock released immediately.

Other threads may proceed.

---

Restaurant analogy

sleep()

```
Chef locks kitchen

Sleeps

Nobody can enter
```

wait()

```
Chef unlocks kitchen

Waits outside

Others can work
```

---

# 6. Lost Notification Problem

Suppose:

Consumer

```
notify()
```

runs BEFORE

Producer

```
wait()
```

Timeline

```
notify()

↓

No thread waiting

↓

Signal lost

↓

Later

wait()

↓

Forever waiting
```

The notification disappeared.

---

Solution

Always check the condition.

```java
while(queue.isEmpty()){

    queue.wait();

}
```

Never assume notify() means the condition is true.

---

# 7. Spurious Wakeup

Java allows threads to wake up without:

- notify()
- notifyAll()
- interrupt()

Yes, this is legal.

Therefore:

Never do:

```java
if(queue.isEmpty()){

    queue.wait();

}
```

Instead

```java
while(queue.isEmpty()){

    queue.wait();

}
```

Why?

Thread wakes unexpectedly.

Queue still empty.

Without while:

```
remove()

↓

Exception
```

---

# 8. Guarded Blocks

A Guarded Block is a loop that waits until a condition becomes true.

Pattern:

```java
synchronized(lock){

    while(!condition){

        lock.wait();

    }

}
```

This pattern protects against:

- lost notification
- spurious wakeup
- race conditions

It is the recommended idiom whenever `wait()` is used.

---

# 9. Thread Join

join() allows one thread to wait until another thread completes.

Example

```java
Thread worker = new Thread(() -> {
    System.out.println("Working...");
});

worker.start();

worker.join();

System.out.println("Finished");
```

Output

```
Working...

Finished
```

Without join()

```
Finished

Working...
```

Possible because scheduling is unpredictable.

---

# 10. CountDownLatch

Introduced in Java 5.

Used when one or more threads must wait until a set of operations completes.

Example:

Application startup

```
Load Config

Load Cache

Connect DB

↓

Application Starts
```

Code

```java
CountDownLatch latch = new CountDownLatch(3);

Thread A -> latch.countDown();

Thread B -> latch.countDown();

Thread C -> latch.countDown();

Main Thread

latch.await();
```

After count reaches zero:

```
await()

↓

continues
```

Characteristics

- One-time use
- Count cannot be reset

---

# 11. CyclicBarrier

Unlike CountDownLatch:

Barrier can be reused.

Example:

Five players must be ready before game starts.

```
P1

P2

P3

P4

P5

↓

All Ready

↓

Start Match
```

Code

```java
CyclicBarrier barrier = new CyclicBarrier(5);

barrier.await();
```

Only when all five threads call await()

everyone proceeds.

---

## CountDownLatch vs CyclicBarrier

| CountDownLatch | CyclicBarrier |
|---------------|---------------|
| One-time | Reusable |
| One thread waits | All threads wait |
| Count decreases | Barrier trips |
| Cannot reset | Automatically resets |

---

# 12. Phaser

Phaser is an advanced synchronization utility.

Imagine a project with multiple phases.

```
Phase 1

↓

Phase 2

↓

Phase 3
```

Threads wait at each phase.

Useful when:

- number of participants changes dynamically
- multiple synchronization points exist

Unlike CyclicBarrier:

Participants can register or deregister while the program is running.

---

# 13. Exchanger

Allows two threads to exchange objects.

Example

```
Thread A

Data A

↓

Exchange

↑

Thread B

Data B
```

Code

```java
Exchanger<String> exchanger = new Exchanger<>();

Thread A:
exchanger.exchange("Hello");

Thread B:
exchanger.exchange("World");
```

After exchange:

```
Thread A -> World

Thread B -> Hello
```

Useful for:

- double buffering
- producer-consumer handoffs
- pipeline stages

---

# 14. Semaphore

Semaphore controls **how many threads may access a resource simultaneously**.

Example

Parking lot with 3 spaces.

```
🚗
🚗
🚗

Full

Fourth car waits.
```

Code

```java
Semaphore semaphore = new Semaphore(3);

semaphore.acquire();

try {

    // use resource

} finally {

    semaphore.release();

}
```

Common use cases:

- Database connection pools
- Rate limiting
- Limiting concurrent API calls
- Printer or hardware access

---

# 15. BlockingQueue

# BlockingQueue in Java (Complete Guide)

> **Package:** `java.util.concurrent`
>
> **Interface:** `BlockingQueue<E>`
>
> A `BlockingQueue` is a **thread-safe queue** that automatically blocks producer or consumer threads when necessary. It eliminates the need for manually using `synchronized`, `wait()`, and `notify()`.

---

# Table of Contents

1. What is BlockingQueue?
2. Why Do We Need It?
3. Package & Hierarchy
4. How BlockingQueue Works
5. Internal Working
6. BlockingQueue Implementations
7. Important Methods
8. Four Types of Queue Operations
9. Producer-Consumer Example
10. BlockingQueue vs Queue
11. BlockingQueue vs ConcurrentLinkedQueue
12. Internal Locking
13. Memory Visibility
14. Best Practices
15. Interview Questions
16. Summary

---

# 1. What is BlockingQueue?

A **BlockingQueue** is a queue designed specifically for concurrent programming.

It provides automatic coordination between:

- Producer threads
- Consumer threads

without requiring developers to write synchronization code.

Imagine a restaurant.

```
Chef (Producer)
      |
      |  Food
      ▼
BlockingQueue
      |
      ▼
Waiter (Consumer)
```

The chef keeps preparing food.

The waiter keeps serving food.

If there is no food, the waiter waits.

If the kitchen is full, the chef waits.

Java handles all of this automatically.

---

# 2. Why Do We Need It?

Suppose two threads share a normal queue.

```
Producer
      |
      ▼
Queue
      |
      ▼
Consumer
```

Problems:

- Multiple threads may modify the queue simultaneously.
- Queue may become corrupted.
- Consumer may try to remove from an empty queue.
- Producer may insert into a full queue.
- Developers must manually write synchronization logic.

Example using traditional synchronization:

```java
synchronized(queue) {

    while (queue.isEmpty()) {
        queue.wait();
    }

    Integer value = queue.remove();

    queue.notifyAll();
}
```

This code is:

- Difficult to read
- Easy to get wrong
- Difficult to maintain

With `BlockingQueue`:

```java
queue.put(value);
```

and

```java
Integer value = queue.take();
```

That's all.

No manual synchronization.

---

# 3. Package & Hierarchy

Package

```java
java.util.concurrent
```

Import

```java
import java.util.concurrent.BlockingQueue;
```

Hierarchy

```
Iterable
    │
Collection
    │
Queue
    │
BlockingQueue
```

Important point:

`BlockingQueue` is an **interface**.

Java provides multiple implementations.

---

# 4. How BlockingQueue Works

Suppose queue capacity is 3.

```
Queue

A
B
C
```

Producer tries to insert D.

Instead of throwing an exception or continuously checking the queue...

```
Producer

↓

WAITING
```

Consumer removes an item.

```
Queue

B
C
```

Producer automatically resumes.

Similarly,

If the queue is empty

```
Consumer

↓

take()

↓

WAITING
```

Producer inserts data.

Consumer automatically wakes up.

---

# 5. Internal Working

Developers never need to call:

```java
wait();

notify();

notifyAll();
```

Internally, BlockingQueue uses:

- `ReentrantLock`
- `Condition`
- `await()`
- `signal()`
- Atomic operations
- Memory visibility guarantees

Simplified internal flow

```
Producer

↓

Queue Full

↓

await()

↓

Consumer removes

↓

signal()

↓

Producer resumes
```

Java handles all synchronization internally.

---

# 6. BlockingQueue Implementations

## ArrayBlockingQueue

A fixed-capacity queue backed by an array.

```java
BlockingQueue<Integer> queue =
        new ArrayBlockingQueue<>(5);
```

Characteristics

- Fixed size
- Predictable memory usage
- Faster for bounded queues

Example

```
Capacity = 5

[1]

[2]

[3]

[4]

[5]
```

When full

```
put()

↓

Thread waits
```

---

## LinkedBlockingQueue

Implemented using a linked list.

```java
BlockingQueue<Integer> queue =
        new LinkedBlockingQueue<>();
```

Default capacity

```
Integer.MAX_VALUE
```

Can also specify capacity.

```java
new LinkedBlockingQueue<>(100);
```

Best suited for

- Producer-consumer applications
- Dynamic workloads

---

## PriorityBlockingQueue

Orders elements according to their priority.

Insertion order

```
10

2

50

1
```

Removal order

```
1

2

10

50
```

Useful for

- Task schedulers
- Priority-based processing

---

## DelayQueue

Stores elements until a specified delay expires.

Useful for

- Retry mechanisms
- Cache expiration
- Scheduled processing

---

## SynchronousQueue

Capacity is always zero.

Nothing is stored.

```
Producer

↓

Direct handoff

↓

Consumer
```

Used extensively by thread pools.

---

# 7. Important Methods

---

## put(E element)

Inserts an element.

If queue is full

Thread blocks.

```java
queue.put("A");
```

Flow

```
Queue Full

↓

WAITING

↓

Consumer removes

↓

Insertion succeeds
```

---

## take()

Removes an element.

If queue is empty

Thread blocks.

```java
String item = queue.take();
```

Flow

```
Queue Empty

↓

WAITING

↓

Producer inserts

↓

Returns item
```

---

## offer(E element)

Attempts insertion.

Never waits.

```java
queue.offer(item);
```

Returns

```java
true
```

or

```java
false
```

---

## offer(E element, timeout)

Waits for a specified time.

```java
queue.offer(item, 5, TimeUnit.SECONDS);
```

Meaning

```
Wait up to 5 seconds.

If queue is still full

Return false.
```

---

## poll()

Removes head.

Never waits.

Returns

- element
- null

```java
Integer value = queue.poll();
```

---

## poll(timeout)

Waits for a limited duration.

```java
queue.poll(10, TimeUnit.SECONDS);
```

Returns

- element
- null

---

## add()

Adds immediately.

If queue is full

Throws

```
IllegalStateException
```

---

## remove()

Removes immediately.

If queue is empty

Throws exception.

---

## peek()

Reads first element.

Does not remove it.

Queue

```
10

20

30
```

peek()

```
10
```

Queue remains unchanged.

---

## element()

Same as `peek()`.

Difference

Throws exception if queue is empty.

---

## remainingCapacity()

Returns available space.

Example

```
Capacity = 10

Current = 7
```

Returns

```
3
```

---

## drainTo()

Transfers all available elements to another collection.

```java
List<Integer> list = new ArrayList<>();

queue.drainTo(list);
```

Instead of

```java
while (!queue.isEmpty()) {
    list.add(queue.take());
}
```

Bulk transfer is significantly faster.

---

# 8. Four Types of Queue Operations

Every queue operation has four versions.

| Operation | Throws Exception | Returns Value | Blocks | Timeout |
|------------|------------------|---------------|---------|----------|
| Insert | `add()` | `offer()` | `put()` | `offer(timeout)` |
| Remove | `remove()` | `poll()` | `take()` | `poll(timeout)` |
| Read Head | `element()` | `peek()` | — | — |

### Easy way to remember

| Method | Behaviour |
|----------|-----------|
| add() | Throw exception |
| offer() | Return `false` |
| put() | Wait forever |
| offer(timeout) | Wait for some time |

---

# 9. Producer-Consumer Example

Producer

```java
BlockingQueue<Integer> queue =
        new ArrayBlockingQueue<>(5);

Thread producer = new Thread(() -> {

    try {

        for (int i = 1; i <= 10; i++) {

            queue.put(i);

            System.out.println("Produced : " + i);

        }

    } catch (InterruptedException e) {

        Thread.currentThread().interrupt();
    }

});
```

Consumer

```java
Thread consumer = new Thread(() -> {

    try {

        while (true) {

            Integer value = queue.take();

            System.out.println("Consumed : " + value);

        }

    } catch (InterruptedException e) {

        Thread.currentThread().interrupt();
    }

});
```

Notice

No

- `synchronized`
- `wait()`
- `notify()`
- explicit locking

---

# 10. BlockingQueue vs Queue

| Queue | BlockingQueue |
|--------|---------------|
| Usually not thread-safe | Thread-safe |
| No waiting support | Supports blocking |
| Manual synchronization | Automatic synchronization |
| Suitable for single-threaded code | Designed for multithreading |
| `add()` and `remove()` | `put()` and `take()` |

---

# 11. BlockingQueue vs ConcurrentLinkedQueue

| BlockingQueue | ConcurrentLinkedQueue |
|---------------|-----------------------|
| Can block threads | Never blocks |
| Optional bounded capacity | Unbounded |
| Producer-consumer coordination | High-throughput non-blocking queue |
| `put()` and `take()` | `offer()` and `poll()` |
| Uses locks internally | Lock-free CAS implementation |

### When to use ConcurrentLinkedQueue

Use it when:

- maximum throughput is required
- producers should never wait
- consumers can handle empty queues

### When to use BlockingQueue

Use it when:

- producers should wait if full
- consumers should wait if empty
- task coordination is required

---

# 12. Internal Locking

One common interview question is:

> **Does BlockingQueue use synchronized?**

Mostly **No**.

Most implementations use `ReentrantLock`.

For example, `ArrayBlockingQueue` maintains:

```
putLock
takeLock
```

along with `Condition` objects:

```
notFull

notEmpty
```

Producer

```
Queue Full

↓

await(notFull)
```

Consumer

```
Queue Empty

↓

await(notEmpty)
```

When the queue state changes, waiting threads are signalled.

---

# 13. Memory Visibility

Another interview question:

> **How does the consumer always see the latest data?**

Because all queue operations establish **happens-before** relationships.

Example

Producer

```java
queue.put(data);
```

Consumer

```java
queue.take();
```

Everything written before `put()` becomes visible to the thread that successfully returns from `take()`.

You don't need `volatile`.

You don't need extra synchronization.

---

# 14. Best Practices

✅ Prefer `put()` and `take()` for producer-consumer systems.

✅ Use bounded queues (`ArrayBlockingQueue`) to prevent unlimited memory growth.

✅ Handle `InterruptedException` properly by restoring the interrupt status:

```java
catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

✅ Prefer `LinkedBlockingQueue` when queue size is unpredictable.

✅ Use `drainTo()` for bulk processing.

❌ Do not mix external synchronization with `BlockingQueue`; it is already thread-safe.

---

# 15. Top Interview Questions

### Why is BlockingQueue thread-safe?

Because it internally uses locks, conditions, and proper memory visibility guarantees.

---

### Why use BlockingQueue instead of wait()/notify()?

It provides:

- Cleaner code
- Fewer bugs
- Better performance
- Easier maintenance
- Well-tested concurrency primitives

---

### What happens when put() is called on a full queue?

The producer thread blocks until space becomes available.

---

### What happens when take() is called on an empty queue?

The consumer thread blocks until an element is inserted.

---

### Difference between offer() and put()

| offer() | put() |
|----------|--------|
| Never waits | Waits indefinitely |
| Returns `false` if full | Blocks until space is available |

---

### Difference between poll() and take()

| poll() | take() |
|----------|--------|
| Returns `null` if empty | Waits until data is available |

---

### Which BlockingQueue is used by ThreadPoolExecutor?

By default, `Executors.newFixedThreadPool()` uses an **unbounded `LinkedBlockingQueue`** to store submitted tasks.

---

# 16. Summary

| Feature | BlockingQueue |
|----------|---------------|
| Package | `java.util.concurrent` |
| Thread-safe | ✅ Yes |
| Supports Blocking | ✅ Yes |
| Producer-Consumer | ✅ Excellent |
| Uses wait()/notify() internally? | Indirectly, via `Lock` and `Condition` implementations |
| Explicit synchronization required | ❌ No |
| Common implementations | `ArrayBlockingQueue`, `LinkedBlockingQueue`, `PriorityBlockingQueue`, `DelayQueue`, `SynchronousQueue` |

---

# Key Takeaways

- `BlockingQueue` is the standard choice for producer-consumer problems.
- It eliminates manual synchronization and thread communication code.
- `put()` blocks when the queue is full, while `take()` blocks when it is empty.
- Choose `ArrayBlockingQueue` for bounded queues and predictable memory usage.
- Choose `LinkedBlockingQueue` for dynamic workloads.
- `PriorityBlockingQueue`, `DelayQueue`, and `SynchronousQueue` serve specialised use cases.
- Most implementations rely on `ReentrantLock` and `Condition` rather than `synchronized`.
- Understanding the differences between `put()`, `offer()`, `take()`, and `poll()` is essential for Java concurrency interviews.

---

# 16. Top Interview Questions

## Why must wait() be called inside synchronized?

Because the thread must own the object's monitor before it can release it. Otherwise, Java throws:

```
IllegalMonitorStateException
```

---

## Why is wait() inside a while loop?

To handle:

- Spurious wakeups
- Lost notifications
- Condition changes by other threads before the waiting thread reacquires the lock

---

## Can notify() wake a specific thread?

No.

The JVM chooses an arbitrary waiting thread.

---

## Does notify() release the lock?

No.

It only signals a waiting thread. The awakened thread can continue **only after** the notifying thread exits the synchronized block and releases the monitor.

---

## When should I use notifyAll()?

Use `notifyAll()` when multiple waiting threads may depend on different conditions or when you're unsure which thread should proceed. It is generally safer than `notify()`, though it may wake more threads than necessary.

---

## Why use BlockingQueue instead of wait()/notify()?

Because it is:

- Simpler
- Less error-prone
- Highly optimized
- Thread-safe
- Easier to maintain

---

## When should I use CountDownLatch?

When one or more threads need to wait for a fixed number of tasks to finish, such as application startup or parallel processing.

---

## Semaphore vs synchronized?

- `synchronized` allows **only one thread** into a critical section.
- `Semaphore` can allow **N threads** concurrently based on the number of permits.

---

# 17. Summary

| Mechanism | Purpose |
|-----------|---------|
| wait() | Suspend thread and release lock until notified |
| notify() | Wake one waiting thread |
| notifyAll() | Wake all waiting threads |
| join() | Wait for another thread to finish |
| CountDownLatch | Wait for a fixed number of tasks |
| CyclicBarrier | Synchronize threads at a common barrier |
| Phaser | Multi-phase synchronization with dynamic participants |
| Exchanger | Exchange data between two threads |
| Semaphore | Limit concurrent access to a resource |
| BlockingQueue | Built-in producer-consumer communication |

---

# Key Takeaways

- **Synchronization** protects shared data; **communication** coordinates thread execution.
- Always call `wait()`, `notify()`, and `notifyAll()` while holding the object's monitor (`synchronized`).
- Always use `wait()` inside a **`while` loop**, not an `if`, to guard against spurious wakeups and changing conditions.
- Prefer higher-level concurrency utilities (`BlockingQueue`, `CountDownLatch`, `Semaphore`, etc.) over low-level `wait()/notify()` in production code.
- Know the classic interview differences: `wait()` vs `sleep()`, `notify()` vs `notifyAll()`, and `CountDownLatch` vs `CyclicBarrier`.
