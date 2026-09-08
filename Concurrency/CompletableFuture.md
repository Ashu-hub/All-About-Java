# CompletableFuture — Complete Java Guide

`CompletableFuture` is a Java API for writing **asynchronous and composable workflows**.

It was introduced in **Java 8** as part of `java.util.concurrent`.

Is is a class which implements Future & CompletionStage. 
like: public class CompletableFuture<T> implements Future<T>, CompletionStage<T>

The main purpose is to:

* Execute tasks asynchronously
* Process results when they are available
* Chain asynchronous operations
* Combine multiple asynchronous operations
* Handle exceptions
* Handle timeouts
* Control the executor used for asynchronous tasks

---

# 1. Why CompletableFuture?

Suppose an application needs to call three independent services:

```text
                    ┌── User Service
                    │
Request ────────────┼── Order Service
                    │
                    └── Payment Service
```

A traditional sequential approach:

```java
User user = userService.getUser();                 // 500 ms
Order order = orderService.getOrder();             // 700 ms
Payment payment = paymentService.getPayment();     // 400 ms
```

Total:

```text
500 + 700 + 400 = 1600 ms
```

Since the calls are independent, we can execute them concurrently:

```text
User Service       ─────── 500 ms
Order Service      ─────────── 700 ms
Payment Service    ──── 400 ms

Total ≈ 700 ms
```

This is one of the major use cases for `CompletableFuture`.

---

# 2. Creating a CompletableFuture

There are two commonly used (static) methods:

```text
runAsync()
supplyAsync()
```

---

## 2.1 runAsync()

Use `runAsync()` when the task **does not return a result**.

```java
CompletableFuture<Void> future =
        CompletableFuture.runAsync(() -> {
            System.out.println("Running asynchronously");
        });
```

Return type:

```java
CompletableFuture<Void>
```

Example:

```java
CompletableFuture.runAsync(() -> {
    sendEmail();
});
```

---

## 2.2 supplyAsync()

Use `supplyAsync()` when the task **returns a result**.

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });
```

Return type:

```java
CompletableFuture<String>
```

Example:

```java
CompletableFuture<User> future =
        CompletableFuture.supplyAsync(() -> {
            return userService.getUser();
        });
```

### Easy rule

```text
Need a result?
        ↓
supplyAsync()

Don't need a result?
        ↓
runAsync()
```

---

# 3. Getting the Result

There are two common methods:

```java
get()
join()
```

---

## 3.1 get()

```java
String result = future.get();
```

`get()` is **blocking**.

The current thread waits until the future completes.

It throws checked exceptions:

```java
try {
    String result = future.get();
} catch (InterruptedException | ExecutionException e) {
    // Handle exception
}
```

---

## 3.2 join()

```java
String result = future.join();
```

`join()` is also blocking.

However, it throws an unchecked exception:

```text
CompletionException
```

### get() vs join()

| Feature              | `get()`              | `join()`              |
| -------------------- | -------------------- | --------------------- |
| Blocks               | Yes                  | Yes                   |
| Checked exceptions   | Yes                  | No                    |
| Main exception       | `ExecutionException` | `CompletionException` |
| InterruptedException | Yes                  | No                    |

### Interview answer

> `get()` throws checked exceptions such as `InterruptedException` and `ExecutionException`, while `join()` throws an unchecked `CompletionException`. Both can block while waiting for completion.

---

# 4. thenApply()

`thenApply()` is used to **transform the result** of a completed future.

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "hello");

CompletableFuture<String> result =
        future.thenApply(value -> value.toUpperCase());
```

Flow:

```text
hello
  ↓
thenApply()
  ↓
HELLO
```

Think of it as:

```text
T → R
```

Examples:

```java
User → String
String → Integer
Order → Payment
```

Example:

```java
CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(() -> getUser());

CompletableFuture<String> nameFuture =
        userFuture.thenApply(user -> user.getName());
```

---

# 5. thenAccept()

`thenAccept()` is used when you want to **consume the result**, but don't want to return another result.

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "Hello");

future.thenAccept(value -> {
    System.out.println(value);
});
```

The resulting future is:

```java
CompletableFuture<Void>
```

Think:

```text
T → void
```

---

# 6. thenRun()

`thenRun()` executes an action after the previous stage completes.

It does **not receive the previous result**.

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "Hello");

future.thenRun(() -> {
    System.out.println("Completed");
});
```

The `"Hello"` value is not available inside `thenRun()`.

---

# 7. thenApply vs thenAccept vs thenRun

| Method         | Receives Result | Returns Result |
| -------------- | --------------: | -------------: |
| `thenApply()`  |             Yes |            Yes |
| `thenAccept()` |             Yes |             No |
| `thenRun()`    |              No |             No |

### Easy way to remember

```text
thenApply()
    ↓
Transform

thenAccept()
    ↓
Consume

thenRun()
    ↓
Just execute something
```

---

# 8. thenCompose()

`thenCompose()` is one of the **most important CompletableFuture methods for interviews**.

It is used to chain **dependent asynchronous operations**.

Suppose:

```java
CompletableFuture<User> userFuture =
        getUser();
```

And:

```java
CompletableFuture<List<Order>> getOrders(Long userId)
```

We want:

```text
getUser()
    ↓
getOrders(userId)
```

Use:

```java
CompletableFuture<List<Order>> ordersFuture =
        userFuture.thenCompose(user ->
                getOrders(user.getId())
        );
```

---

# 9. Why not thenApply()?

Suppose:

```java
CompletableFuture<List<Order>> getOrders(Long userId)
```

returns a `CompletableFuture`.

If we use:

```java
userFuture.thenApply(user ->
        getOrders(user.getId())
);
```

we get:

```text
CompletableFuture<
    CompletableFuture<List<Order>>
>
```

In other words:

```java
CompletableFuture<CompletableFuture<List<Order>>>
```

This is called a **nested future**.

`thenCompose()` flattens it:

```text
CompletableFuture<CompletableFuture<List<Order>>>
                    ↓
               thenCompose()
                    ↓
CompletableFuture<List<Order>>
```

---

# 10. thenApply vs thenCompose

### thenApply()

Use when the function returns a normal value:

```java
future.thenApply(value -> transform(value));
```

Conceptually:

```text
T → R
```

### thenCompose()

Use when the function returns another `CompletableFuture`:

```java
future.thenCompose(value -> asyncOperation(value));
```

Conceptually:

```text
T → CompletableFuture<R>
```

### Interview shortcut

> `thenApply()` is used for transformation, whereas `thenCompose()` is used to chain dependent asynchronous operations and flatten nested `CompletableFuture`s.

---

# 11. thenCombine()

`thenCombine()` is used when you have **two independent futures** and need both results.

Example:

```java
CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(() -> getUser());

CompletableFuture<Account> accountFuture =
        CompletableFuture.supplyAsync(() -> getAccount());
```

Both operations are independent.

We can combine them:

```java
CompletableFuture<UserAccount> result =
        userFuture.thenCombine(
                accountFuture,
                (user, account) ->
                        new UserAccount(user, account)
        );
```

Flow:

```text
User Service       ───────────┐
                              │
                              ├── thenCombine()
                              │
Account Service    ───────────┘
                                      ↓
                                UserAccount
```

---

# 12. thenCompose vs thenCombine

This is a very common interview question.

## thenCompose()

Used when there is a **dependency**.

```text
A
↓
B
```

Example:

```text
getUser()
   ↓
getOrders(userId)
```

---

## thenCombine()

Used when operations are **independent**.

```text
A ───────┐
         ├── C
B ───────┘
```

Example:

```text
getUser()
getAccount()
    ↓
combine
```

### Remember

```text
Dependent
    ↓
thenCompose()

Independent
    ↓
thenCombine()
```

---

# 13. allOf()

Suppose we have three independent futures:

```java
CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(() -> getUser());

CompletableFuture<Order> orderFuture =
        CompletableFuture.supplyAsync(() -> getOrder());

CompletableFuture<Payment> paymentFuture =
        CompletableFuture.supplyAsync(() -> getPayment());
```

We want to wait until **all three complete**.

Use:

```java
CompletableFuture<Void> all =
        CompletableFuture.allOf(
                userFuture,
                orderFuture,
                paymentFuture
        );
```

Then:

```java
all.join();
```

After `all.join()` completes, all three futures have completed.

We can retrieve their results:

```java
User user = userFuture.join();

Order order = orderFuture.join();

Payment payment = paymentFuture.join();
```

---

# 14. Important Point About allOf()

A common interview question:

> Does `allOf()` return all the results?

No.

It returns:

```java
CompletableFuture<Void>
```

Example:

```java
CompletableFuture<Void> all =
        CompletableFuture.allOf(
                future1,
                future2,
                future3
        );
```

You need to retrieve the results separately.

---

# 15. anyOf()

`anyOf()` completes when **the first future completes**.

```java
CompletableFuture<Object> result =
        CompletableFuture.anyOf(
                future1,
                future2,
                future3
        );
```

Example:

```text
Future 1 ───────────── 1000 ms

Future 2 ───── 300 ms  ← First

Future 3 ───────── 700 ms
```

`anyOf()` completes after approximately:

```text
300 ms
```

The result type is:

```java
CompletableFuture<Object>
```

because the supplied futures can have different types.

---

# 16. allOf vs anyOf

| Feature     | `allOf()`                 | `anyOf()`                   |
| ----------- | ------------------------- | --------------------------- |
| Waits for   | All futures               | First completed future      |
| Return type | `CompletableFuture<Void>` | `CompletableFuture<Object>` |
| Common use  | Aggregate multiple calls  | First response / race       |

---

# 17. Exception Handling

Exceptions are an important part of `CompletableFuture`.

Suppose:

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Something went wrong");
        });
```

We can handle the exception using:

```text
exceptionally()
handle()
whenComplete()
```

---

# 18. exceptionally()

Use `exceptionally()` for **error recovery / fallback values**.

```java
CompletableFuture<String> result =
        future.exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return "DEFAULT";
        });
```

Flow:

```text
Success
   ↓
Result

Failure
   ↓
exceptionally()
   ↓
Fallback
```

Example:

```java
CompletableFuture<User> userFuture =
        getUser()
        .exceptionally(ex -> {
            return defaultUser();
        });
```

---

# 19. handle()

`handle()` receives **both the result and the exception**.

```java
CompletableFuture<String> result =
        future.handle((value, exception) -> {

            if (exception != null) {
                return "DEFAULT";
            }

            return value;
        });
```

Conceptually:

```text
                 ┌── value
Future ──────────┤
                 └── exception
                       ↓
                    handle()
                       ↓
                    result
```

`handle()` is useful when you want to process both success and failure and produce a new result.

---

# 20. whenComplete()

`whenComplete()` is generally used for **side effects**, such as:

* Logging
* Metrics
* Auditing
* Monitoring

Example:

```java
future.whenComplete((value, exception) -> {

    if (exception != null) {
        System.out.println("Failed: " + exception);
    } else {
        System.out.println("Success: " + value);
    }
});
```

It observes the outcome rather than primarily being used to recover from an error.

---

# 21. exceptionally vs handle vs whenComplete

| Method            | Main Purpose                                 |
| ----------------- | -------------------------------------------- |
| `exceptionally()` | Recover from failure                         |
| `handle()`        | Process success/failure and produce a result |
| `whenComplete()`  | Observe success/failure                      |

### Easy rule

```text
Recover
   ↓
exceptionally()

Transform success/failure
   ↓
handle()

Log / Metrics / Observe
   ↓
whenComplete()
```

---

# 22. Async Methods

Many CompletableFuture methods have an `Async` version.

Examples:

```java
thenApply()
thenApplyAsync()

thenAccept()
thenAcceptAsync()

thenCompose()
thenComposeAsync()

thenCombine()
thenCombineAsync()
```

---

# 23. thenApply vs thenApplyAsync

### thenApply()

```java
future.thenApply(value ->
        process(value)
);
```

The continuation may execute in the thread that completes the previous stage.

### thenApplyAsync()

```java
future.thenApplyAsync(value ->
        process(value)
);
```

The continuation is scheduled asynchronously, typically using:

```text
ForkJoinPool.commonPool()
```

unless an executor is explicitly supplied.

---

# 24. Custom Executor

You can provide your own executor.

```java
ExecutorService executor =
        Executors.newFixedThreadPool(10);
```

Then:

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
                () -> callExternalService(),
                executor
        );
```

You can also specify it for continuation:

```java
future.thenApplyAsync(
        result -> process(result),
        executor
);
```

---

# 25. Why Use a Custom Executor?

Suppose your application makes blocking external API calls:

```text
Application
     ↓
CompletableFuture
     ↓
External API
     ↓
Blocking
```

If all such operations use:

```text
ForkJoinPool.commonPool()
```

you may create contention with other tasks using the common pool.

A better design can separate workloads:

```text
Application
     │
     ├── CPU-intensive work
     │       ↓
     │   CPU Executor
     │
     └── Blocking I/O
             ↓
         I/O Executor
```

This provides better control over:

* Thread count
* Queueing
* Rejection policy
* Resource isolation
* Monitoring

---

# 26. Complete Example

Suppose an e-commerce application needs:

```text
User
Orders
Recommendations
```

All three calls are independent.

```java
CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(
                () -> userService.getUser(userId),
                executor
        );

CompletableFuture<List<Order>> ordersFuture =
        CompletableFuture.supplyAsync(
                () -> orderService.getOrders(userId),
                executor
        );

CompletableFuture<List<Product>> recommendationsFuture =
        CompletableFuture.supplyAsync(
                () -> recommendationService.getRecommendations(userId),
                executor
        );
```

Wait for all:

```java
CompletableFuture.allOf(
        userFuture,
        ordersFuture,
        recommendationsFuture
).join();
```

Get results:

```java
User user = userFuture.join();

List<Order> orders = ordersFuture.join();

List<Product> recommendations =
        recommendationsFuture.join();
```

Create response:

```java
return new Dashboard(
        user,
        orders,
        recommendations
);
```

Architecture:

```text
                 ┌── User Service ──────────┐
                 │                          │
Request ─────────┼── Order Service ─────────┼── allOf()
                 │                          │
                 └── Recommendation ────────┘
                                              ↓
                                          Dashboard
```

---

# 27. Sequential + Parallel Example

A more realistic backend scenario:

First we need the user.

Then, based on the user's ID, we need:

```text
Orders
Profile
Recommendations
```

The second three operations are independent.

```text
                    User
                     │
          ┌──────────┼──────────┐
          ↓          ↓          ↓
       Orders     Profile   Recommendations
          │          │          │
          └──────────┼──────────┘
                     ↓
                 Dashboard
```

Implementation:

```java
CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(
                () -> getUser(),
                executor
        );

CompletableFuture<Dashboard> dashboardFuture =
        userFuture.thenCompose(user -> {

            CompletableFuture<List<Order>> orders =
                    CompletableFuture.supplyAsync(
                            () -> getOrders(user.getId()),
                            executor
                    );

            CompletableFuture<Profile> profile =
                    CompletableFuture.supplyAsync(
                            () -> getProfile(user.getId()),
                            executor
                    );

            CompletableFuture<List<Product>> recommendations =
                    CompletableFuture.supplyAsync(
                            () -> getRecommendations(user.getId()),
                            executor
                    );

            return CompletableFuture.allOf(
                    orders,
                    profile,
                    recommendations
            ).thenApply(v ->
                    new Dashboard(
                            user,
                            orders.join(),
                            profile.join(),
                            recommendations.join()
                    )
            );
        });
```

The important concepts here are:

```text
thenCompose()
    ↓
Dependent operation

supplyAsync()
    ↓
Parallel execution

allOf()
    ↓
Wait for multiple operations

join()
    ↓
Retrieve completed results
```

---

# 28. CompletableFuture State

Conceptually, a future can be thought of as:

```text
              CompletableFuture
                     │
              Not Completed
                     │
                     ↓
                 Completed
                 /        \
                /          \
           Success        Failure
```

You can check completion:

```java
future.isDone();
```

Returns `true` when the future has completed.

It can be either successful or exceptional.

---

# 29. isCompletedExceptionally()

```java
future.isCompletedExceptionally();
```

Returns `true` if the future completed with an exception.

Example:

```java
if (future.isCompletedExceptionally()) {
    System.out.println("Future failed");
}
```

---

# 30. cancel()

You can attempt to cancel a future:

```java
future.cancel(true);
```

Check cancellation:

```java
future.isCancelled();
```

---

# 31. orTimeout()

Java 9 introduced:

```java
orTimeout()
```

Example:

```java
future.orTimeout(
        2,
        TimeUnit.SECONDS
);
```

If the future doesn't complete within two seconds, it completes exceptionally due to timeout.

Useful for external service calls.

---

# 32. completeOnTimeout()

Instead of failing on timeout, you can return a fallback value:

```java
future.completeOnTimeout(
        "DEFAULT",
        2,
        TimeUnit.SECONDS
);
```

Flow:

```text
External Service
       │
       ↓
    2 seconds
       │
       ↓
   No response
       │
       ↓
    DEFAULT
```

---

# 33. complete()

A `CompletableFuture` can also be manually completed.

```java
CompletableFuture<String> future =
        new CompletableFuture<>();

future.complete("Hello");
```

Now:

```java
future.join();
```

returns:

```text
Hello
```

This is useful when integrating callback-based or event-driven APIs.

---

# 34. completeExceptionally()

A future can also be manually completed with an exception:

```java
future.completeExceptionally(
        new RuntimeException("Failed")
);
```

Consumers of the future will see the exceptional completion.

---

# 35. Future vs CompletableFuture

This is a very common interview question.

## Future

```java
Future<String> future =
        executor.submit(() -> "Hello");

String result = future.get();
```

Problems:

* Difficult to chain
* Difficult to combine
* Limited exception handling
* No convenient callback composition
* Often leads to blocking with `get()`

---

## CompletableFuture

Provides:

```text
Chaining
Combining
Callbacks
Exception handling
Timeouts
Manual completion
Asynchronous composition
```

### Comparison

| Feature              |  Future | CompletableFuture |
| -------------------- | ------: | ----------------: |
| Async execution      |     Yes |               Yes |
| Callback composition |      No |               Yes |
| Chaining             |      No |               Yes |
| Combining            |      No |               Yes |
| Exception handling   | Limited |               Yes |
| Timeout APIs         | Limited |               Yes |
| Manual completion    |      No |               Yes |

---

# 36. Important: CompletableFuture Isn't Automatically Non-Blocking

This is a **trick interview question**.

It is incorrect to simply say:

> CompletableFuture is non-blocking.

For example:

```java
future.get();
```

is blocking.

And:

```java
future.join();
```

is also blocking.

Also:

```java
CompletableFuture.supplyAsync(
        () -> databaseCall()
);
```

does not magically make `databaseCall()` non-blocking.

If `databaseCall()` blocks a thread, the thread is still blocked.

### Correct interview answer

> CompletableFuture provides asynchronous execution and composition, but it does not automatically make the underlying operation non-blocking. If the underlying API is blocking, a thread will still be blocked while executing it.

---

# 37. ForkJoinPool.commonPool()

If you don't specify an executor:

```java
CompletableFuture.supplyAsync(
        () -> task()
);
```

the asynchronous task generally uses:

```text
ForkJoinPool.commonPool()
```

Similarly:

```java
future.thenApplyAsync(
        result -> process(result)
);
```

uses the common pool unless a custom executor is supplied.

---

# 38. thenApply() Thread Behavior

Consider:

```java
CompletableFuture
        .supplyAsync(() -> task())
        .thenApply(result -> process(result));
```

The `thenApply()` continuation may run in the thread that completes the previous stage.

With:

```java
CompletableFuture
        .supplyAsync(() -> task())
        .thenApplyAsync(result -> process(result));
```

the continuation is scheduled asynchronously.

---

# 39. Common CompletableFuture Pattern

A very common pattern is:

```java
CompletableFuture
        .supplyAsync(() -> callService(), executor)
        .thenApply(result -> transform(result))
        .thenCompose(result -> callAnotherService(result))
        .thenApply(result -> buildResponse(result))
        .exceptionally(ex -> fallback());
```

Conceptually:

```text
Async Service Call
        ↓
Transform
        ↓
Async Dependent Call
        ↓
Build Response
        ↓
Error Recovery
```

---

# 40. Important Methods Cheat Sheet

| Method                    | Purpose                                  |
| ------------------------- | ---------------------------------------- |
| `runAsync()`              | Async task without result                |
| `supplyAsync()`           | Async task with result                   |
| `thenApply()`             | Transform result                         |
| `thenAccept()`            | Consume result                           |
| `thenRun()`               | Execute action                           |
| `thenCompose()`           | Chain dependent async operations         |
| `thenCombine()`           | Combine two independent futures          |
| `allOf()`                 | Wait for all futures                     |
| `anyOf()`                 | First completed future                   |
| `exceptionally()`         | Recover from failure                     |
| `handle()`                | Handle success/failure                   |
| `whenComplete()`          | Observe success/failure                  |
| `orTimeout()`             | Fail after timeout                       |
| `completeOnTimeout()`     | Return fallback after timeout            |
| `complete()`              | Manually complete                        |
| `completeExceptionally()` | Manually complete with error             |
| `get()`                   | Blocking result                          |
| `join()`                  | Blocking result with unchecked exception |
| `cancel()`                | Cancel future                            |

---

# 41. Most Important Interview Concepts

For a Java Backend/Lead interview, make sure you understand these particularly well:

```text
1. CompletableFuture vs Future

2. runAsync() vs supplyAsync()

3. thenApply() vs thenCompose()

4. thenCompose() vs thenCombine()

5. allOf() vs anyOf()

6. thenApply() vs thenApplyAsync()

7. CommonPool vs Custom Executor

8. exceptionally() vs handle()

9. handle() vs whenComplete()

10. CompletableFuture and blocking operations

11. Exception propagation

12. Timeout handling

13. Parallel service calls

14. Sequential + parallel async workflows
```

---

# 42. Golden Rules

Memorize these four:

```text
DEPENDENT OPERATION
        ↓
thenCompose()
```

```text
TRANSFORM RESULT
        ↓
thenApply()
```

```text
TWO INDEPENDENT FUTURES
        ↓
thenCombine()
```

```text
MANY FUTURES
        ↓
allOf()
```

For exception handling:

```text
RECOVER
    ↓
exceptionally()
```

```text
SUCCESS OR FAILURE → NEW RESULT
    ↓
handle()
```

```text
OBSERVE / LOG / METRICS
    ↓
whenComplete()
```

---

# 43. One-Page Interview Summary

```text
CompletableFuture
│
├── Create
│   ├── runAsync()
│   └── supplyAsync()
│
├── Transform
│   └── thenApply()
│
├── Consume
│   └── thenAccept()
│
├── Execute Action
│   └── thenRun()
│
├── Sequential Async
│   └── thenCompose()
│
├── Combine
│   └── thenCombine()
│
├── Multiple Futures
│   ├── allOf()
│   └── anyOf()
│
├── Exception Handling
│   ├── exceptionally()
│   ├── handle()
│   └── whenComplete()
│
├── Timeout
│   ├── orTimeout()
│   └── completeOnTimeout()
│
└── Result
    ├── get()
    └── join()
```

## The four most important rules

```text
thenApply()
    → T → R

thenCompose()
    → T → CompletableFuture<R>

thenCombine()
    → Future<A> + Future<B> → Future<C>

allOf()
    → Wait for multiple futures
```

**If you understand these four relationships, you understand the core of `CompletableFuture`.**
