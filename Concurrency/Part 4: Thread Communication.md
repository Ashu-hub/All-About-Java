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

One of the most important concurrent collections.

It **handles producer-consumer coordination automatically**.

Producer

```java
queue.put(item);
```

If full:

```
Producer waits automatically.
```

Consumer

```java
queue.take();
```

If empty:

```
Consumer waits automatically.
```

No need for:

- wait()
- notify()
- manual synchronization

Example

```java
BlockingQueue<Integer> queue =
        new ArrayBlockingQueue<>(10);
```

Popular implementations:

| Implementation | Best For |
|---------------|----------|
| ArrayBlockingQueue | Fixed-size queue |
| LinkedBlockingQueue | Variable-size queue |
| PriorityBlockingQueue | Priority ordering |
| DelayQueue | Scheduled tasks |
| SynchronousQueue | Direct handoff between threads |

In modern Java applications, prefer `BlockingQueue` over writing your own `wait()/notify()` logic.

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
