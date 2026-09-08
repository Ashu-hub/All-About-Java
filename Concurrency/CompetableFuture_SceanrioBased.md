# CompletableFuture — Scenario-Based Interview Questions

## 1. Parallel Microservice Calls

### Scenario

You are building an **Order Details API** that needs data from three independent microservices:

```text
Order API
   |
   +----> User Service
   |
   +----> Payment Service
   |
   +----> Product Service
```

All three calls are independent and can execute in parallel.

### Interview Question

How would you implement this using `CompletableFuture`?

What would you consider regarding:

* Parallel execution
* `allOf()`
* `join()` vs `get()`
* Exception handling
* Custom executor
* Timeout

### Expected Approach

Use:

```java
CompletableFuture.supplyAsync()
```

for each independent call.

Then wait for all:

```java
CompletableFuture.allOf(userFuture, paymentFuture, productFuture)
```

Example:

```java
ExecutorService executor = Executors.newFixedThreadPool(20);

CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(
                () -> userService.getUser(userId),
                executor
        );

CompletableFuture<Payment> paymentFuture =
        CompletableFuture.supplyAsync(
                () -> paymentService.getPayment(orderId),
                executor
        );

CompletableFuture<Product> productFuture =
        CompletableFuture.supplyAsync(
                () -> productService.getProducts(orderId),
                executor
        );

CompletableFuture.allOf(
        userFuture,
        paymentFuture,
        productFuture
).join();

User user = userFuture.join();
Payment payment = paymentFuture.join();
Product product = productFuture.join();
```

### Lead-Level Discussion

Do **not** blindly use:

```java
ForkJoinPool.commonPool()
```

for blocking I/O calls.

For database calls or REST calls, prefer a dedicated executor.

---

# 2. Sequential Dependent API Calls

### Scenario

You need to:

1. Get customer details.
2. Use the customer ID to get the customer's account.
3. Use the account ID to get transactions.

```text
Customer
   |
   v
Account
   |
   v
Transactions
```

Each call depends on the previous result.

### Interview Question

Which method would you use?

```java
thenApply()
```

or

```java
thenCompose()
```

### Expected Answer

Use:

```java
thenCompose()
```

because the next operation itself returns a `CompletableFuture`.

### Example

```java
CompletableFuture<Customer> customerFuture =
        getCustomer();

CompletableFuture<Account> accountFuture =
        customerFuture.thenCompose(
                customer -> getAccount(customer.getId())
        );

CompletableFuture<List<Transaction>> transactionFuture =
        accountFuture.thenCompose(
                account -> getTransactions(account.getId())
        );
```

### Why Not `thenApply()`?

If you use:

```java
thenApply(customer -> getAccount(customer.getId()))
```

you get:

```text
CompletableFuture<CompletableFuture<Account>>
```

With `thenCompose()`:

```text
CompletableFuture<Account>
```

### Interview Shortcut

> `thenApply()` → transform a result.

> `thenCompose()` → chain dependent asynchronous operations.

---

# 3. `get()` vs `join()` in Production Code

### Scenario

You have:

```java
CompletableFuture<User> userFuture = getUser();
```

You need the result.

### Interview Question

Would you use:

```java
userFuture.get();
```

or:

```java
userFuture.join();
```

### Expected Answer

Both are blocking.

The main difference is exception handling.

### `get()`

```java
try {
    User user = userFuture.get();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
} catch (ExecutionException e) {
    // handle failure
}
```

`get()` throws checked exceptions:

```text
InterruptedException
ExecutionException
```

### `join()`

```java
User user = userFuture.join();
```

Failure is wrapped in:

```text
CompletionException
```

### When I Prefer `join()`

Inside a CompletableFuture aggregation flow:

```java
CompletableFuture.allOf(
        userFuture,
        paymentFuture,
        orderFuture
).join();

User user = userFuture.join();
Payment payment = paymentFuture.join();
Order order = orderFuture.join();
```

It keeps the code cleaner.

### Important

Neither method is non-blocking.

```text
get()  → blocking
join() → blocking
```

---

# 4. Slow Microservice — 2 Second Timeout

### Scenario

Your API calls a Recommendation Service.

The service normally responds within 500 ms, but sometimes takes 10 seconds.

Your API has a strict SLA of **2 seconds**.

### Interview Question

How would you handle this using `CompletableFuture`?

### Expected Approach

Use:

```java
orTimeout()
```

Example:

```java
CompletableFuture<Recommendation> future =
        CompletableFuture
                .supplyAsync(() -> recommendationService.get())
                .orTimeout(2, TimeUnit.SECONDS);
```

If the operation doesn't complete within 2 seconds:

```text
TimeoutException
```

will complete the future exceptionally.

### Alternative: `completeOnTimeout()`

If you want a fallback value:

```java
CompletableFuture<Recommendation> future =
        CompletableFuture
                .supplyAsync(() -> recommendationService.get())
                .completeOnTimeout(
                        Recommendation.empty(),
                        2,
                        TimeUnit.SECONDS
                );
```

### Difference

```text
orTimeout()
       ↓
Exception

completeOnTimeout()
       ↓
Fallback value
```

### Lead-Level Consideration

Timeouts should generally be combined with:

* Connection timeout
* Read timeout
* Circuit breaker
* Retry policy
* Fallback
* Monitoring

---

# 5. Optional Service Failure

### Scenario

Your API calls:

```text
User Service
Payment Service
Recommendation Service
```

User and payment information are mandatory.

Recommendations are optional.

If Recommendation Service fails, the API should still return the response.

### Interview Question

How would you handle this?

### Expected Approach

Use:

```java
exceptionally()
```

for graceful recovery.

```java
CompletableFuture<Recommendation> recommendationFuture =
        CompletableFuture
                .supplyAsync(() ->
                        recommendationService.getRecommendations(userId)
                )
                .exceptionally(ex -> {
                    log.error(
                        "Recommendation service failed",
                        ex
                    );

                    return Recommendation.empty();
                });
```

Now:

```text
Recommendation Service
        |
        X
        |
        v
Recommendation.empty()
```

The main API can still respond.

### Interview Point

Do not treat every downstream failure equally.

Classify dependencies as:

```text
Mandatory
   ↓
Failure → API failure

Optional
   ↓
Failure → Fallback
```

---

# 6. `allOf()` vs `thenCombine()`

### Scenario

You have two independent asynchronous calls:

```text
User Service
     |
     +----> User

Order Service
     |
     +----> Orders
```

You need both results to create a response.

### Interview Question

Would you use:

```java
allOf()
```

or:

```java
thenCombine()
```

### `thenCombine()`

When combining **two** futures and directly producing a result:

```java
CompletableFuture<UserOrderResponse> response =
        userFuture.thenCombine(
                orderFuture,
                (user, orders) ->
                        new UserOrderResponse(user, orders)
        );
```

Result:

```text
CompletableFuture<UserOrderResponse>
```

### `allOf()`

Useful when waiting for multiple futures:

```java
CompletableFuture<Void> all =
        CompletableFuture.allOf(
                userFuture,
                orderFuture,
                paymentFuture
        );
```

Important:

```text
allOf() → CompletableFuture<Void>
```

It does not automatically return all results.

### Interview Shortcut

```text
2 futures + combine results
        ↓
thenCombine()

Many futures + wait for completion
        ↓
allOf()
```

---

# 7. Blocking DB/API Call Inside `CompletableFuture`

### Scenario

A developer writes:

```java
CompletableFuture.supplyAsync(() ->
        repository.findCustomer(customerId)
);
```

The repository performs a blocking JDBC call.

### Interview Question

Is this a good implementation?

### Expected Answer

Not necessarily.

By default:

```java
supplyAsync()
```

uses:

```text
ForkJoinPool.commonPool()
```

Blocking database or HTTP calls can occupy common-pool threads.

This can cause:

```text
Thread starvation
      ↓
Reduced throughput
      ↓
Increased latency
```

### Better Approach

Use a dedicated executor:

```java
ExecutorService ioExecutor =
        Executors.newFixedThreadPool(20);

CompletableFuture<Customer> future =
        CompletableFuture.supplyAsync(
                () -> repository.findCustomer(customerId),
                ioExecutor
        );
```

### Lead-Level Discussion

The executor size should be chosen based on:

* Number of concurrent requests
* I/O latency
* Database connection pool size
* CPU cores
* Downstream capacity
* Expected traffic

Do not simply create a huge thread pool.

---

# 8. `thenApply()` vs `thenApplyAsync()`

### Scenario

You have:

```java
CompletableFuture<User> userFuture = getUser();
```

Then you want to transform the user:

```java
User → UserResponse
```

### Interview Question

What's the difference between:

```java
thenApply()
```

and:

```java
thenApplyAsync()
```

### `thenApply()`

```java
CompletableFuture<UserResponse> response =
        userFuture.thenApply(
                user -> convertToResponse(user)
        );
```

The callback may execute on the thread that completes the previous stage.

### `thenApplyAsync()`

```java
CompletableFuture<UserResponse> response =
        userFuture.thenApplyAsync(
                user -> convertToResponse(user)
        );
```

The callback is scheduled asynchronously, normally using the common pool unless an executor is supplied.

### With Custom Executor

```java
CompletableFuture<UserResponse> response =
        userFuture.thenApplyAsync(
                user -> convertToResponse(user),
                executor
        );
```

### Interview Shortcut

```text
thenApply()
     ↓
Possibly same completion thread

thenApplyAsync()
     ↓
Asynchronous execution
```

---

# 9. Exception Propagation in a CompletableFuture Chain

### Scenario

You have:

```java
CompletableFuture<User> future =
        getUser()
        .thenApply(this::validateUser)
        .thenApply(this::createResponse);
```

Suppose:

```java
validateUser()
```

throws an exception.

### Interview Question

What happens to the next stage?

### Expected Answer

The future becomes exceptional.

The next normal:

```java
thenApply()
```

stage will not execute.

Example:

```text
getUser()
   |
   v
validateUser()
   |
   X Exception
   |
   v
Exceptional Completion
   |
   X
createResponse()
```

### Recover with `exceptionally()`

```java
CompletableFuture<Response> future =
        getUser()
        .thenApply(this::validateUser)
        .thenApply(this::createResponse)
        .exceptionally(ex -> {
            log.error("Processing failed", ex);
            return fallbackResponse();
        });
```

### `handle()`

If you need both success and failure:

```java
.handle((result, exception) -> {

    if (exception != null) {
        return fallbackResponse();
    }

    return result;
});
```

### `whenComplete()`

If you only want to observe/log:

```java
.whenComplete((result, exception) -> {

    if (exception != null) {
        log.error("Failed", exception);
    }

});
```

### Interview Shortcut

```text
exceptionally()
     ↓
Recover from failure

handle()
     ↓
Success OR failure → new result

whenComplete()
     ↓
Observe/log success or failure
```

---

# 10. Complete Production Scenario

### Scenario

You are designing a **Customer Dashboard API**.

The workflow is:

```text
                 Customer
                    |
                    v
              Account Service
                    |
             +------+------+
             |      |      |
             v      v      v
        Transaction Rewards Recommendation
```

Requirements:

1. Customer must be retrieved first.
2. Account depends on customer.
3. Transactions, Rewards and Recommendations are independent.
4. Transactions are mandatory.
5. Rewards are optional.
6. Recommendations are optional.
7. API timeout is 2 seconds.
8. Downstream calls are blocking HTTP calls.
9. You want maximum parallelism.

### Interview Question

Design this using `CompletableFuture`.

---

## Expected Design

### Step 1 — Get Customer

```java
CompletableFuture<Customer> customerFuture =
        CompletableFuture.supplyAsync(
                () -> customerService.getCustomer(customerId),
                executor
        );
```

---

## Step 2 — Get Account

Account depends on Customer.

Use:

```java
thenCompose()
```

```java
CompletableFuture<Account> accountFuture =
        customerFuture.thenCompose(
                customer ->
                        CompletableFuture.supplyAsync(
                                () -> accountService.getAccount(
                                        customer.getId()
                                ),
                                executor
                        )
        );
```

---

## Step 3 — Start Independent Calls in Parallel

Once the account is available:

```java
CompletableFuture<List<Transaction>> transactions =
        accountFuture.thenCompose(
                account ->
                        CompletableFuture.supplyAsync(
                                () -> transactionService.getTransactions(
                                        account.getId()
                                ),
                                executor
                        )
        );

CompletableFuture<Rewards> rewards =
        accountFuture.thenCompose(
                account ->
                        CompletableFuture.supplyAsync(
                                () -> rewardService.getRewards(
                                        account.getId()
                                ),
                                executor
                        )
        );

CompletableFuture<Recommendation> recommendations =
        accountFuture.thenCompose(
                account ->
                        CompletableFuture.supplyAsync(
                                () -> recommendationService.getRecommendations(
                                        account.getId()
                                ),
                                executor
                        )
        );
```

The three operations can execute in parallel:

```text
                    Account
                       |
          +------------+------------+
          |            |            |
          v            v            v
    Transactions    Rewards    Recommendations
```

---

## Step 4 — Add Fallback for Optional Services

```java
CompletableFuture<Rewards> rewards =
        accountFuture
                .thenCompose(account ->
                        CompletableFuture.supplyAsync(
                                () -> rewardService.getRewards(
                                        account.getId()
                                ),
                                executor
                        )
                )
                .exceptionally(ex -> {
                    log.error("Rewards failed", ex);
                    return Rewards.empty();
                });
```

Same approach can be used for recommendations.

---

## Step 5 — Add Timeout

```java
CompletableFuture<Recommendation> recommendations =
        accountFuture
                .thenCompose(account ->
                        CompletableFuture.supplyAsync(
                                () -> recommendationService
                                        .getRecommendations(account.getId()),
                                executor
                        )
                )
                .completeOnTimeout(
                        Recommendation.empty(),
                        2,
                        TimeUnit.SECONDS
                );
```

---

## Step 6 — Combine Results

For the final response:

```java
CompletableFuture<CustomerDashboard> dashboard =
        transactions
                .thenCombine(
                        rewards,
                        (transactionList, rewardData) ->
                                new PartialDashboard(
                                        transactionList,
                                        rewardData
                                )
                )
                .thenCombine(
                        recommendations,
                        (partial, recommendationData) ->
                                new CustomerDashboard(
                                        partial.transactions(),
                                        partial.rewards(),
                                        recommendationData
                                )
                );
```

---

# Interviewer's Expected Discussion

A Lead-level candidate should be able to discuss the following.

| Topic                         | Expected Answer                   |
| ----------------------------- | --------------------------------- |
| Independent calls             | Execute in parallel               |
| Dependent calls               | `thenCompose()`                   |
| Transform result              | `thenApply()`                     |
| Combine two futures           | `thenCombine()`                   |
| Wait for many futures         | `allOf()`                         |
| Optional failure              | `exceptionally()`                 |
| Success + failure handling    | `handle()`                        |
| Logging/metrics               | `whenComplete()`                  |
| Timeout with exception        | `orTimeout()`                     |
| Timeout with fallback         | `completeOnTimeout()`             |
| Blocking I/O                  | Dedicated executor                |
| Common pool                   | Avoid for blocking I/O            |
| Result retrieval              | Prefer `join()` in CF aggregation |
| Checked interruption handling | `get()`                           |
| Thread control                | Custom executor                   |
| Mandatory dependency failure  | Fail request                      |
| Optional dependency failure   | Graceful fallback                 |

---

# Top 10 Interview Shortcuts

## 1. `thenApply()`

```text
T → R
```

Use when you already have the result and want to transform it.

---

## 2. `thenCompose()`

```text
T → CompletableFuture<R>
```

Use for dependent asynchronous operations.

---

## 3. `thenCombine()`

```text
Future<A> + Future<B>
        ↓
Future<C>
```

Use when two independent futures need to be combined.

---

## 4. `allOf()`

```text
Future<A>
Future<B>
Future<C>
    ↓
allOf()
    ↓
Void
```

Use to wait for multiple futures.

---

## 5. `anyOf()`

```text
Future<A>
Future<B>
Future<C>
    ↓
anyOf()
    ↓
First completed result
```

Use when the first completed result is sufficient.

---

## 6. `exceptionally()`

```text
Failure
   ↓
Fallback
```

Use for simple error recovery.

---

## 7. `handle()`

```text
Success ──┐
          ├──> New Result
Failure ──┘
```

Use when you need to process both success and failure.

---

## 8. `whenComplete()`

```text
Success ──┐
          ├──> Observe / Log / Metrics
Failure ──┘
```

Use for side effects without changing the result.

---

## 9. `get()` vs `join()`

```text
get()
 ├── Blocking
 ├── Checked exceptions
 └── Supports interruption

join()
 ├── Blocking
 └── Unchecked CompletionException
```

---

## 10. `thenApply()` vs `thenApplyAsync()`

```text
thenApply()
    ↓
Possibly completion thread

thenApplyAsync()
    ↓
Asynchronous executor
```

---

# Final Interview Cheat Sheet

```text
                    CompletableFuture
                           |
          +----------------+----------------+
          |                |                |
       Transform        Compose          Combine
          |                |                |
    thenApply()      thenCompose()    thenCombine()
          |
    Async version
          |
  thenApplyAsync()


          Multiple Futures
                 |
          +------+------+
          |             |
       allOf()       anyOf()
          |             |
      Wait all       First one


          Error Handling
                 |
      +----------+----------+
      |          |          |
exceptionally() handle() whenComplete()
      |          |          |
   Recover    Transform    Observe


          Timeout
             |
      +------+------+
      |             |
 orTimeout() completeOnTimeout()
      |             |
   Exception      Fallback


          Result Retrieval
                 |
          +------+------+
          |             |
        get()         join()
          |             |
     Checked        Unchecked
     exceptions    CompletionException
```

## Golden Interview Rule

> **Independent operations → parallelize them.**

> **Dependent asynchronous operation → `thenCompose()`.**

> **Transform a completed result → `thenApply()`.**

> **Combine two independent results → `thenCombine()`.**

> **Wait for many futures → `allOf()`.**

> **Recover from failure → `exceptionally()`.**

> **Need success + failure → `handle()`.**

> **Only observe/log → `whenComplete()`.**

> **Blocking I/O → use a dedicated executor.**

> **Never assume `CompletableFuture` automatically means non-blocking.**
