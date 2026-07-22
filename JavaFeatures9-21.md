# Java 9 to Java 21 Features Guide

> **Goal:** A concise interview-ready guide covering all major features introduced after Java 8, with examples and summaries.

---

# Table of Contents

1. Java 9 – Modules, JShell, Collection Factory Methods, Stream & Optional APIs
2. Java 10 – Local Variable Type Inference (`var`)
3. Java 11 (LTS) – String APIs, HTTP Client, Files API
4. Java 12 – Switch Expressions
5. Java 13 – Text Blocks (Preview)
6. Java 14 – Records (Preview), Helpful NullPointerException
7. Java 15 – Text Blocks Final, Sealed Classes (Preview)
8. Java 16 – Records Final, Pattern Matching for `instanceof`
9. Java 17 (LTS) – Sealed Classes Final, Strong Encapsulation
10. Java 18 – UTF-8 by Default, Simple Web Server
11. Java 19 – Virtual Threads (Preview), Structured Concurrency
12. Java 20 – Preview Feature Enhancements
13. Java 21 (LTS) – Virtual Threads, Record Patterns, Pattern Matching for Switch
14. Java Version Comparison
15. Interview Focus Areas

---

# Java 9 (2017)

## 1. Module System (Project Jigsaw)

### Why?

Before Java 9, all JARs were placed on the classpath.

Problems:
- No encapsulation
- Dependency conflicts
- Large JDK size
- Slow startup

### Solution

```java
module inventory {
    requires java.sql;
    exports com.inventory.service;
}
```

### Benefits

- Better encapsulation
- Smaller runtime
- Faster startup
- Easier dependency management

---

## 2. JShell

Interactive Java REPL.

```text
jshell> 10 + 20
30
```

Useful for:
- Learning Java
- Quick testing
- Trying APIs

---

## 3. Collection Factory Methods

Before

```java
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Spring");
```

After

```java
List<String> list = List.of("Java", "Spring");
Set.of(...)
Map.of(...)
```

Creates immutable collections.

---

## 4. Stream API Enhancements

Added:

- `takeWhile()`
- `dropWhile()`
- `iterate()`
- `ofNullable()`

Example

```java
Stream.of(1,2,3,4,5)
      .takeWhile(i -> i < 4);
```
When it encounters 4, it stops immediately.

The remaining elements (4, 5) are not processed, even though they satisfy the condition.

Output

```
1
2
3
```
- dropWhile()

The opposite of takeWhile().

It skips elements from the beginning while the condition is true, then returns the rest of the stream

```
List<Integer> numbers = List.of(1, 2, 3, 4, 2, 1);

numbers.stream()
       .dropWhile(n -> n < 4)
       .forEach(System.out::println);
```
o/p: 
4
2
1

- Stream.ofNullable()
  Sometimes an object may be null.

    Before Java 9
    ```
    String name = null;
    
    Stream.of(name);
    ```
    Throws
    
    NullPointerException

  Java 9
  ```
    String name = null;
    
    Stream.ofNullable(name)
          .forEach(System.out::println);
    ```
    Output
    
    (no output)
    
    If the object is
    
    not null → creates a stream with one element
    null → creates an empty stream

---

## 5. Optional Enhancements

Added

- `ifPresentOrElse()`
- `or()`
- `stream()`

```java
optional.ifPresentOrElse(
    System.out::println,
    () -> System.out.println("Empty")
);
```

---

## 6. Private Methods in Interfaces

```java
interface Calculator {

    private void log() {}

    default void add() {
        log();
    }
}
```

**Helps avoid duplicate code inside interfaces.**

- Access rule:
  
    | Method Type      | Can be called by implementing class?  | Can be called inside interface? |
    | ---------------- | ------------------------------------- | ------------------------------- |
    | `abstract`       | ✅ Yes                                 | ❌ No implementation             |
    | `default`        | ✅ Yes                                 | ✅ Yes                           |
    | `static`         | ❌ (must use `InterfaceName.method()`) | ✅ Yes                           |
    | `private`        | ❌ No                                  | ✅ Yes                           |
    | `private static` | ❌ No                                  | ✅ Yes                           |


---

## Java 9 Summary

| Feature | Purpose |
|----------|----------|
| Modules | Better dependency management |
| JShell | Interactive Java shell |
| Collection Factory | Immutable collections |
| Stream APIs | Better stream processing |
| Optional APIs | Cleaner null handling |
| Private Interface Methods | Reusable interface logic |

---

# Java 10

## Local Variable Type Inference (`var`)

Before

```java
HashMap<String,Integer> map =
        new HashMap<>();
```

After

```java
var map = new HashMap<String,Integer>();
```

Compiler infers the type.

### Important

`var` **is not dynamic typing**.

The variable type is fixed at compile time.

---

var is a reserved type name, not a primitive type or a class. It tells the compiler to infer the type at compile time.
var can only be used for local variables, loop variables, and try-with-resources variables, and it requires an initializer because the compiler must determine the type at compile time.
---

## Java 10 Summary

| Feature | Purpose |
|----------|----------|
| var | Reduce boilerplate while keeping static typing |

---

# Java 11 (LTS)

## 1. String API Enhancements

Added

- `isBlank()`
- `strip()`
- `lines()`
- `repeat()`

Example

```java
"Java".repeat(3);
```

Output

```
JavaJavaJava
```

---

## 2. New HTTP Client

Replaces `HttpURLConnection`.

```java
HttpClient client = HttpClient.newHttpClient();
```

Supports

- HTTP/2
- Async requests
- WebSocket

---

## 3. Files.readString()

Before

```java
Files.readAllBytes(path);
```

After

```java
Files.readString(path);
```

---

## 4. Lambda Parameter `var`

```java
(var x, var y) -> x + y
```

Useful when annotations are required.

---

## Java 11 Summary

| Feature | Purpose |
|----------|----------|
| String APIs | Better string manipulation |
| HTTP Client | Modern HTTP support |
| Files.readString | Simplified file reading |
| Lambda var | Improved lambda readability |

---

# Java 12

## Switch Expressions

Before

```java
switch(day){
    case MONDAY:
        result="Work";
        break;
}
```

After

```java
String result = switch(day){
    case MONDAY -> "Work";
    default -> "Holiday";
};
```

Advantages

- No `break`
- Returns value
- Cleaner syntax

---

## Java 12 Summary

| Feature | Purpose |
|----------|----------|
| Switch Expressions | Cleaner and safer switch statements |

---

# Java 13

## Text Blocks (Preview)

Before

```java
String json="{\"id\":1}";
```

After

```java
String json = """
{
   "id":1
}
""";
```

Useful for

- JSON
- SQL
- XML

---

## Java 13 Summary

| Feature | Purpose |
|----------|----------|
| Text Blocks | Multi-line strings |

---

# Java 14

## Records (Preview)

Before

```java
class Employee{
    private int id;
    private String name;
}
```

After

```java
record Employee(int id, String name){}
```

Compiler automatically creates

- Constructor
- Getters
- equals()
- hashCode()
- toString()

---

## Helpful NullPointerException

Old

```
NullPointerException
```

New

```
Cannot invoke getName()
because employee is null
```

Much easier debugging.

---

## Java 14 Summary

| Feature | Purpose |
|----------|----------|
| Records | Immutable data carrier |
| Helpful NPE | Better debugging |

---

# Java 15

## Text Blocks Final

Text Blocks became a standard feature.

---

## Sealed Classes (Preview)

```java
sealed class Animal
permits Dog, Cat {}
```

Only permitted classes can extend it.

---

## Java 15 Summary

| Feature | Purpose |
|----------|----------|
| Text Blocks | Official feature |
| Sealed Classes | Controlled inheritance |

---

# Java 16

## Records Final

Records became an official feature.

---

## Pattern Matching for instanceof

Before

```java
if(obj instanceof String){
    String s = (String)obj;
}
```

After

```java
if(obj instanceof String s){
    System.out.println(s.length());
}
```

No explicit casting required.

---

## Java 16 Summary

| Feature | Purpose |
|----------|----------|
| Records | Official immutable data class |
| Pattern Matching | Removes manual casting |

---

# Java 17 (LTS)

## Sealed Classes Final

```java
sealed class Shape
permits Circle, Rectangle {}
```

Useful for

- Domain models
- Finite hierarchies

---

## Strong Encapsulation

Internal JDK APIs are strongly protected.

Improves

- Security
- Maintainability

---

## Enhanced Random Generator

New APIs for better random number generation.

---

## Java 17 Summary

| Feature | Purpose |
|----------|----------|
| Sealed Classes | Controlled inheritance |
| Strong Encapsulation | Better security |
| Random Generator | Improved randomness APIs |

---

# Java 18

## UTF-8 by Default

UTF-8 became the default character encoding.

No platform-specific encoding issues.

---

## Simple Web Server

Run

```text
jwebserver
```

Useful for

- Testing
- Static websites
- Local development

---

## Java 18 Summary

| Feature | Purpose |
|----------|----------|
| UTF-8 Default | Consistent encoding |
| Web Server | Quick local server |

---

# Java 19

## Virtual Threads (Preview)

Traditional

```
1 Java Thread
↓

1 OS Thread
```

Virtual Threads

```
100000 Virtual Threads
↓

Few OS Threads
```

Ideal for

- REST APIs
- Microservices
- High concurrency

---

## Structured Concurrency (Preview)

Treat multiple concurrent tasks as one unit.

Improves

- Cancellation
- Error handling
- Readability

---

## Java 19 Summary

| Feature | Purpose |
|----------|----------|
| Virtual Threads | Lightweight concurrency |
| Structured Concurrency | Better async programming |

---

# Java 20

Mostly refinement of preview features.

Improved

- Virtual Threads
- Record Patterns
- Pattern Matching

---

## Java 20 Summary

| Feature | Purpose |
|----------|----------|
| Preview Refinements | Stabilized upcoming Java 21 features |

---

# Java 21 (LTS)

## 1. Virtual Threads (Final)

```java
Thread.startVirtualThread(() ->
    System.out.println("Hello"));
```

Benefits

- Millions of threads
- Low memory
- High scalability

---

## 2. Record Patterns

Before

```java
Point p;

int x = p.x();
int y = p.y();
```

After

```java
if(obj instanceof Point(int x, int y)){
}
```

Automatically extracts components.

---

## 3. Pattern Matching for Switch

```java
switch(obj){
    case Integer i -> System.out.println(i);
    case String s -> System.out.println(s);
    default -> {}
}
```

---

## 4. Sequenced Collections

New interfaces

- `SequencedCollection`
- `SequencedSet`
- `SequencedMap`

Useful methods

```java
list.getFirst();
list.getLast();
list.reversed();
```

---

## 5. String Templates (Preview)

```java
String name = "John";

STR."Hello \{name}"
```

Simplifies string interpolation.

---

## Java 21 Summary

| Feature | Purpose |
|----------|----------|
| Virtual Threads | Massive concurrency |
| Record Patterns | Easier data extraction |
| Pattern Matching for Switch | Cleaner switch statements |
| Sequenced Collections | First/Last/Reversed operations |
| String Templates | Safer string interpolation |

---

# Java Version Comparison

| Version | Major Features |
|----------|----------------|
| Java 9 | Modules, JShell, Collection Factory, Stream APIs |
| Java 10 | var |
| Java 11 (LTS) | HTTP Client, String APIs, Files.readString |
| Java 12 | Switch Expressions |
| Java 13 | Text Blocks |
| Java 14 | Records (Preview), Helpful NPE |
| Java 15 | Text Blocks Final, Sealed Classes |
| Java 16 | Records Final, Pattern Matching |
| Java 17 (LTS) | Sealed Classes Final, Strong Encapsulation |
| Java 18 | UTF-8 Default, Simple Web Server |
| Java 19 | Virtual Threads (Preview) |
| Java 20 | Preview Refinements |
| Java 21 (LTS) | Virtual Threads, Record Patterns, Pattern Matching for Switch |

---

# Interview Focus Areas

⭐ Highest Priority

1. Module System
2. `var`
3. HTTP Client API
4. New String APIs
5. Switch Expressions
6. Text Blocks
7. Records
8. Pattern Matching (`instanceof`)
9. Sealed Classes
10. Virtual Threads
11. Pattern Matching for Switch
12. Sequenced Collections

> **Tip:** Most modern Spring Boot 3.x applications target **Java 17 or Java 21**, making Records, Sealed Classes, Pattern Matching, and Virtual Threads the most valuable features for interviews.
