# Comprehensive Notes: Concurrency vs Parallelism

**Video:** [Concurrency vs Parallelism](http://www.youtube.com/watch?v=FChZP09Ba4E) by **Defog Tech**

### Core Distinction (Rob Pike Quotes)

- **Parallelism:** *"Parallelism is about doing a lot of things at once."* [[01:26](https://www.youtube.com/watch?v=FChZP09Ba4E&t=86)]
- **Concurrency:** *"Concurrency is about dealing with a lot of things at once."* [[06:48](https://www.youtube.com/watch?v=FChZP09Ba4E&t=408)]

## 1. Parallelism [[00:00](https://www.youtube.com/watch?v=FChZP09Ba4E&t=0)]

### Key Concepts

- **Definition:** Executing multiple tasks simultaneously at the exact same physical moment to speed up program execution [[01:31](https://www.youtube.com/watch?v=FChZP09Ba4E&t=91)].
- **Hardware Requirement:** Requires **more than 1 CPU core** (multi-core architecture) [[02:28](https://www.youtube.com/watch?v=FChZP09Ba4E&t=148)].

### Code Examples in Java [[00:03](https://www.youtube.com/watch?v=FChZP09Ba4E&t=3)]

Java

```
// Java 8 Lambda Style - Raw Threads
public static void main(String[] args) {
    new Thread(() -> processTax(user1)).start(); // Task 1
    new Thread(() -> processTax(user2)).start(); // Task 2
    heavyCalculations();                        // Task 3 (Main thread)
}

// Java ThreadPool Approach
public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(4);
    es.submit(() -> processTax(user1)); // Task 1
    es.submit(() -> processTax(user2)); // Task 2
    heavyCalculations();                // Task 3
}

```

### Visual Architecture Diagram [[00:46](https://www.youtube.com/watch?v=FChZP09Ba4E&t=46)]

```
                 [ OS Scheduler ]
                        |
        +---------------+---------------+
        |               |               |
        v               v               v
    [ Core 1 ]      [ Core 2 ]      [ Core 3 ]     [ Core 4 ]
   Main Thread       Thread 2        Thread 1        (Idle)
 (Heavy Calcs)     (User 2 Tax)    (User 1 Tax)
        |               |               |
        +---------------+---------------+
                        |
              [ Parallel Execution ]

```

### Tools in Java to Enable Parallelism [[02:03](https://www.youtube.com/watch?v=FChZP09Ba4E&t=123)]

- **Threads** (`java.lang.Thread`)
- **ThreadPools:**
  - `ExecutorService`
  - `ForkJoinPool`
  - Custom Web Server ThreadPools (e.g., Tomcat, Jetty)

## 2. Concurrency [[02:43](https://www.youtube.com/watch?v=FChZP09Ba4E&t=163)]

### Key Concepts

- **Definition:** Managing and coordinating multiple tasks that access shared resources or require execution coordination [[06:56](https://www.youtube.com/watch?v=FChZP09Ba4E&t=416)].
- **Hardware:** Can run on **single-core** systems (via time-slicing/interleaving) or **multi-core** systems [[03:35](https://www.youtube.com/watch?v=FChZP09Ba4E&t=215)].
- **Primary Focus:** Program correctness, state management, and avoiding race conditions [[08:06](https://www.youtube.com/watch?v=FChZP09Ba4E&t=486)].

### The Problem: Race Condition (Unsynchronized Code) [[02:46](https://www.youtube.com/watch?v=FChZP09Ba4E&t=166)]

Java

```
// Unsafe Code - Ticket Booking
new Thread(() -> {
    if (ticketsAvailable > 0) {
        bookTicket();
        ticketsAvailable--;
    }
}).start();

new Thread(() -> {
    if (ticketsAvailable > 0) {
        bookTicket();
        ticketsAvailable--;
    }
}).start();

```

### Execution Diagrams [[03:34](https://www.youtube.com/watch?v=FChZP09Ba4E&t=214)]

#### Single-Core Interleaving (Race Condition Bug) [[05:12](https://www.youtube.com/watch?v=FChZP09Ba4E&t=312)]

```
Single Core Interleaving Timeline:
-----------------------------------------------------------
[ Thread 1 ] check (ticketsAvailable > 0) [Reads 1]
[ Thread 2 ] check (ticketsAvailable > 0) [Reads 1]
[ Thread 1 ] bookTicket()                 [Booked!]
[ Thread 2 ] bookTicket()                 [Double Booked! Bug!]
[ Thread 1 ] ticketsAvailable--           [Count = 0]
[ Thread 2 ] ticketsAvailable--           [Count = -1 !]
-----------------------------------------------------------

```

#### Multi-Core Race Condition [[05:40](https://www.youtube.com/watch?v=FChZP09Ba4E&t=340)]

```
 Core 1 (Thread 1)                  Core 2 (Thread 2)
-------------------                -------------------
if (ticketsAvailable > 0)          if (ticketsAvailable > 0)
       |                                  |
  bookTicket()                        bookTicket()
       |                                  |
ticketsAvailable--                 ticketsAvailable--
       \                                 /
        +------------> [ Race Condition! ] <----+

```

### The Solution: Synchronization via Locks [[06:14](https://www.youtube.com/watch?v=FChZP09Ba4E&t=374)]

Java

```
Lock lock = new ReentrantLock();

new Thread(() -> {
    lock.lock();
    try {
        if (ticketsAvailable > 0) {
            bookTicket();
            ticketsAvailable--;
        }
    } finally {
        lock.unlock();
    }
}).start();

```

### Tools to Deal with Concurrency in Java [[07:10](https://www.youtube.com/watch?v=FChZP09Ba4E&t=430)]

- **Locks /** **`synchronized`** **Keyword**
- **Atomic Classes:** `AtomicInteger`, `AtomicLong`, `AtomicReference`
- **Concurrent Data Structures:** `ConcurrentHashMap`, `BlockingQueue`
- **`CompletableFuture`** (introduced in Java 8)
- **Synchronizers:** `CountDownLatch`, `Phaser`, `CyclicBarrier`, `Semaphore`

## 3. Combining Concurrency + Parallelism [[07:42](https://www.youtube.com/watch?v=FChZP09Ba4E&t=462)]

1. **Split Sequential Flow:** Break long processes into independent sub-components [[07:43](https://www.youtube.com/watch?v=FChZP09Ba4E&t=463)].
2. **Parallelize:** Use threads or thread pools to execute independent tasks across multiple CPU cores [[07:48](https://www.youtube.com/watch?v=FChZP09Ba4E&t=468)].
3. **Synchronize (Concurrency):** Use locks or atomic variables whenever threads update shared state or coordinate [[07:56](https://www.youtube.com/watch?v=FChZP09Ba4E&t=476)].
