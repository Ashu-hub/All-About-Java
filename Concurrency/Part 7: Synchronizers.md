# Part 7 — Synchronizers

## Table of Contents

- [1. CountDownLatch ⭐](#1-countdownlatch-)
- [2. CyclicBarrier ⭐](#2-cyclicbarrier-)
- [3. Phaser](#3-phaser)
- [4. Semaphore ⭐](#4-semaphore-)
- [5. Exchanger](#5-exchanger)
- [Synchronizers Comparison ⭐](#synchronizers-comparison-)
- [CountDownLatch vs CyclicBarrier ⭐](#countdownlatch-vs-cyclicbarrier-)
- [Phaser vs CyclicBarrier](#phaser-vs-cyclicbarrier)
- [Semaphore vs Lock](#semaphore-vs-lock)
- [Top Interview Questions](#top-interview-questions)
- [Key Takeaways](#key-takeaways)

---

# 1. CountDownLatch ⭐

## What is it?

`CountDownLatch` is a synchronization utility that allows one or more threads to wait until a set of operations performed by other threads completes.

It is initialized with a count. Every time a task finishes, it decreases the count using `countDown()`. Once the count reaches **0**, all waiting threads are released.

> Think of it as waiting for all team members to finish before starting the meeting.

---

## How it works

1. Create latch with a count.
2. Worker threads perform tasks.
3. Each worker calls `countDown()`.
4. Main thread calls `await()`.
5. When count becomes zero, waiting thread continues.

```
Count = 3

Worker1 ---- countDown()
Worker2 ---- countDown()
Worker3 ---- countDown()

Main Thread
     |
   await()
     |
Count becomes 0
     |
 Continues execution
```

---

## Example

```java
CountDownLatch latch = new CountDownLatch(3);

Runnable worker = () -> {
    System.out.println(Thread.currentThread().getName() + " finished");
    latch.countDown();
};

new Thread(worker).start();
new Thread(worker).start();
new Thread(worker).start();

latch.await();

System.out.println("All workers completed");
```

---

## Important Methods

| Method | Purpose |
|---------|----------|
| `await()` | Wait until count reaches zero |
| `await(timeout)` | Wait with timeout |
| `countDown()` | Decrease count |
| `getCount()` | Current remaining count |

---

## Use Cases

- Wait for multiple API calls
- Wait for services to initialize
- Parallel processing
- Integration testing

---

## Interview Points

- One-time use only.
- Cannot reset.
- Once count reaches zero, it cannot be reused.

---

# 2. CyclicBarrier ⭐

## What is it?

`CyclicBarrier` lets a fixed number of threads wait for each other before continuing.

Unlike `CountDownLatch`, it **can be reused** after all threads reach the barrier.

> Think of friends waiting at a checkpoint during a marathon before moving together.

---

## How it works

```
Thread1 ----\
Thread2 ----- > Barrier
Thread3 ----/

All arrive
     ↓
Barrier opens
     ↓
All continue together
```

---

## Example

```java
CyclicBarrier barrier = new CyclicBarrier(3);

Runnable task = () -> {
    System.out.println(Thread.currentThread().getName() + " reached barrier");

    try {
        barrier.await();
    } catch (Exception e) {}

    System.out.println(Thread.currentThread().getName() + " continues");
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start();
```

---

## Important Methods

| Method | Purpose |
|---------|----------|
| `await()` | Wait until all parties arrive |
| `reset()` | Reset barrier |
| `getNumberWaiting()` | Waiting threads |
| `isBroken()` | Check barrier state |

---

## Use Cases

- Parallel algorithms
- Multiplayer games
- Batch processing
- Simulation systems

---

## Interview Points

- Reusable.
- All threads wait for each other.
- Can execute an optional barrier action after everyone arrives.

---

# 3. Phaser

## What is it?

`Phaser` is a more flexible version of `CyclicBarrier`.

It supports:
- Multiple phases
- Dynamic registration of threads
- Threads joining or leaving while execution is running

---

## Why Phaser?

Imagine a project with multiple stages:

```
Phase 1 -> Load Data
Phase 2 -> Process Data
Phase 3 -> Save Data
```

All threads complete one phase before moving to the next.

---

## Example

```java
Phaser phaser = new Phaser(3);

Runnable task = () -> {

    System.out.println(Thread.currentThread().getName() + " Phase 1");
    phaser.arriveAndAwaitAdvance();

    System.out.println(Thread.currentThread().getName() + " Phase 2");
    phaser.arriveAndAwaitAdvance();

    System.out.println(Thread.currentThread().getName() + " Finished");
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start();
```

---

## Important Methods

| Method | Purpose |
|---------|----------|
| `arrive()` | Arrive without waiting |
| `arriveAndAwaitAdvance()` | Arrive and wait |
| `register()` | Add participant |
| `arriveAndDeregister()` | Remove participant |

---

## Use Cases

- Multi-stage workflows
- Complex simulations
- Dynamic thread management

---

## Interview Points

- More flexible than `CyclicBarrier`.
- Supports multiple phases.
- Threads can register or deregister dynamically.

---

# 4. Semaphore ⭐

## What is it?

A `Semaphore` controls how many threads can access a shared resource simultaneously.

It works using **permits**.

If permits are exhausted, additional threads wait until one is released.

---

## Example

Suppose a database connection pool has **3 connections**.

```
Semaphore(3)

Thread1 -> gets permit ✔
Thread2 -> gets permit ✔
Thread3 -> gets permit ✔

Thread4 -> waits...

One thread releases permit

Thread4 -> proceeds
```

---

## Example Code

```java
Semaphore semaphore = new Semaphore(2);

Runnable task = () -> {
    try {
        semaphore.acquire();

        System.out.println(Thread.currentThread().getName() + " acquired permit");

        Thread.sleep(2000);

    } catch (Exception e) {
    } finally {
        semaphore.release();
    }
};

for (int i = 0; i < 5; i++) {
    new Thread(task).start();
}
```

---

## Important Methods

| Method | Purpose |
|---------|----------|
| `acquire()` | Obtain permit |
| `release()` | Return permit |
| `tryAcquire()` | Acquire if available |
| `availablePermits()` | Remaining permits |

---

## Use Cases

- Database connection pools
- Rate limiting
- Resource throttling
- Limiting concurrent requests

---

## Interview Points

- Controls concurrent access.
- Can be binary (1 permit) or counting (multiple permits).
- Prevents resource exhaustion.

---

# 5. Exchanger

## What is it?

`Exchanger` allows **two threads** to exchange objects at a synchronization point.

Both threads wait until the other arrives, then swap data.

---

## How it works

```
Producer ---- Data A ----\
                           > Exchange
Consumer ---- Data B ----/

Producer gets Data B
Consumer gets Data A
```

---

## Example

```java
Exchanger<String> exchanger = new Exchanger<>();

Thread producer = new Thread(() -> {
    try {
        String response = exchanger.exchange("Data from Producer");
        System.out.println(response);
    } catch (Exception e) {}
});

Thread consumer = new Thread(() -> {
    try {
        String response = exchanger.exchange("Data from Consumer");
        System.out.println(response);
    } catch (Exception e) {}
});

producer.start();
consumer.start();
```

---

## Use Cases

- Producer-Consumer pipelines
- Data swapping
- Genetic algorithms
- Parallel computations

---

## Interview Points

- Works only between two threads.
- Both threads block until exchange happens.
- Thread-safe data exchange.

---

# Synchronizers Comparison ⭐

| Feature | CountDownLatch | CyclicBarrier | Phaser | Semaphore | Exchanger |
|----------|----------------|---------------|---------|-----------|-----------|
| Reusable | ❌ No | ✅ Yes | ✅ Yes | ✅ Yes | ✅ Yes |
| Wait for Threads | ✅ | ✅ | ✅ | ❌ | ✅ (2 threads) |
| Controls Resource Access | ❌ | ❌ | ❌ | ✅ | ❌ |
| Multiple Phases | ❌ | ❌ | ✅ | ❌ | ❌ |
| Dynamic Participants | ❌ | ❌ | ✅ | ❌ | ❌ |
| Exchanges Data | ❌ | ❌ | ❌ | ❌ | ✅ |

---

# CountDownLatch vs CyclicBarrier ⭐

| CountDownLatch | CyclicBarrier |
|----------------|---------------|
| One-time use | Reusable |
| Waiting thread may be different from worker threads | All participating threads wait |
| Count only decreases | Barrier resets automatically |
| Cannot be reused | Can be reused for multiple cycles |

---

# Phaser vs CyclicBarrier

| Phaser | CyclicBarrier |
|---------|---------------|
| Supports multiple phases | Single barrier point |
| Threads can register dynamically | Fixed number of threads |
| More flexible | Simpler API |

---

# Semaphore vs Lock

| Semaphore | Lock |
|-----------|------|
| Multiple threads can enter (based on permits) | Only one thread owns the lock |
| Used to limit resource access | Used for mutual exclusion |
| Doesn't require ownership to release (though misuse is dangerous) | Only owning thread should unlock |

---

# Top Interview Questions

### Basic

1. What is a synchronizer in Java?
2. What is CountDownLatch?
3. Why can't CountDownLatch be reused?
4. Explain CyclicBarrier.
5. What is Phaser?
6. What is Semaphore?
7. What is Exchanger?

---

### Intermediate

8. CountDownLatch vs CyclicBarrier.
9. Phaser vs CyclicBarrier.
10. Semaphore vs Lock.
11. Binary Semaphore vs Counting Semaphore.
12. How does Semaphore prevent resource exhaustion?
13. When would you use Exchanger?

---

### Scenario-Based

**Q:** You have five microservices that must all finish startup before accepting traffic. Which synchronizer would you use?

**Answer:** `CountDownLatch`.

---

**Q:** Four worker threads must complete each processing stage together before starting the next stage.

**Answer:** `Phaser` (or `CyclicBarrier` if there is only one synchronization point per iteration).

---

**Q:** You have a pool of only ten database connections but hundreds of incoming requests.

**Answer:** `Semaphore` to limit concurrent access to the connection pool.

---

## Key Takeaways

- **CountDownLatch** → Wait for one-time completion of multiple tasks.
- **CyclicBarrier** → All threads wait for each other and continue together.
- **Phaser** → Flexible synchronizer for multiple phases with dynamic participants.
- **Semaphore** → Limit concurrent access to shared resources using permits.
- **Exchanger** → Safely exchange data between exactly two threads.
