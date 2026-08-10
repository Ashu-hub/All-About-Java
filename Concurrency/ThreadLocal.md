# ThreadLocal in Java - Technical Notes
*Based on the video tutorial by Defog Tech*

---

## What is `ThreadLocal`?
`ThreadLocal` in Java enables you to create variables that can only be read and written by the same thread. If two threads execute the same code and reference a `ThreadLocal` variable, neither thread can see the other's variable, ensuring **thread confinement** without synchronization overhead `[03:41]`.

---

## Key Use Cases

### Use Case 1: Per-Thread Object Creation (Thread-Safety + Performance) `[04:04]`
* **Problem:** 
  * Classes like `SimpleDateFormat` are **not thread-safe** `[02:45]`. 
  * Instantiating an object inside every task (e.g., 1,000 tasks) leads to excessive memory consumption `[02:10]`.
  * Sharing a single global instance with `synchronized` blocks/locks degrades performance due to thread contention `[03:10]`.
* **Solution:** 
  * Assign **one instance per thread** in a thread pool (e.g., a pool of 10 threads gets exactly 10 instances of `SimpleDateFormat`) `[03:42]`.
  * Each thread reuses its own instance across multiple tasks safely and efficiently without locking `[03:54]`.

#### Implementation Examples:
* **Java 7 / Pre-Java 8 Approach:** `[04:19]`
  ```java
  class ThreadSafeFormatter {
      public static final ThreadLocal<SimpleDateFormat> dateFormatter = 
          new ThreadLocal<SimpleDateFormat>() {
              @Override
              protected SimpleDateFormat initialValue() {
                  return new SimpleDateFormat("yyyy-MM-dd");
              }
          };
  }
