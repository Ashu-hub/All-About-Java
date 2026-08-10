md_content = """# Concurrency vs Parallelism — Detailed Study Notes

**Source Video:** [Defog Tech - Concurrency vs Parallelism](https://www.youtube.com/watch?v=FChZP09Ba4E)  
**Channel:** Defog Tech  
**Key Takeaway Quotes (Rob Pike):**
* *"Parallelism is about doing a lot of things at once."* [01:26]
* *"Concurrency is about dealing with a lot of things at once."* [06:48]

---

## 1. Parallelism

### Definition & Key Concepts
* **Parallelism** focuses on executing multiple computations simultaneously at the exact same physical moment [00:00].
* **Primary Goal:** Speeding up execution and increasing overall program throughput [01:31].
* **Hardware Requirement:** Requires **more than 1 CPU core** (multi-core system) [02:28].

---

### Code Examples (Java)

#### A. Manual Thread Creation (Java 7 vs Java 8)
```java
// Java 7 Style
public static void main(String[] args) {
    // Task 1: Run on separate thread
    new Thread(new Runnable() {
        @Override
        public void run() {
            processTax(user1);
        }
    }).start();

    // Task 2: Run on separate thread
    new Thread(new Runnable() {
        @Override
        public void run() {
            processTax(user2);
        }
    }).start();

    // Task 3: Run on main thread
    heavyCalculations();
}
