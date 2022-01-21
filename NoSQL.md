
# No Sql    

A NoSQL originally referring to non SQL or non relational is a database that provides a mechanism for storage and retrieval of data.
NoSQL databases are used in real-time web applications and big data and their use are increasing over time.
NoSQL systems are also sometimes called Not only SQL to emphasize the fact that they may support SQL-like query languages.
A NoSQL database includes simplicity of design, simpler horizontal scaling to clusters of machines and finer control over availability. 

## Advantages of NoSQL:

There are many advantages of working with NoSQL databases such as MongoDB and Cassandra. The main advantages are high scalability and high availability.

1. **High scalability :-**
NoSQL database use sharding for horizontal scaling. Partitioning of data and placing it on multiple machines in such a way that the order of the data is preserved is sharding. Vertical scaling means adding more resources to the existing machine whereas horizontal scaling means adding more machines to handle the data. Vertical scaling is not that easy to implement but horizontal scaling is easy to implement. Examples of horizontal scaling databases are MongoDB, Cassandra etc. NoSQL can handle huge amount of data because of scalability, as the data grows NoSQL scale itself to handle that data in efficient manner.

2. **High availability :–**
Auto replication feature in NoSQL databases makes it highly available because in case of any failure data replicates itself to the previous consistent state.

3. All the relevant data is contain in one blob. Insertion and retrival is easier.

4. Schema is very flexiable. If Address is null in a table, we dnt send Address objects in say json. And also adding a new column in sql DB is an expensive operation, as we need to have locks on the table first and it is also risky to maintain consistency at that time.

5. Build for finding metrics/getting intelligent data/aggregation. If you need to find age, you can directly get them.

## Disadvantages of NoSQL:

1. Not Build for Updates:- consistency is a problem i.e. ACID is not guarenteed.
If ACID is not guarenteed, you cant have Transactions using NoSQL DB.
Thats why financial systems dont use NoSql dbs.

2. Not Read optimize:- Compartively slower from RDBMS as it need to go through all chunk of data.

3. Relations are not implicit- Means two tables does not have a constriant for relation.

4. Joins are hard.

5. **Narrow focus**
NoSQL databases have very narrow focus as it is mainly designed for storage but it provides very little functionality. Relational databases are a better choice in the field of Transaction Management than NoSQL.

6. **Open-source/Non-Reliable standard** 
NoSQL is open-source database. There is no reliable standard for NoSQL yet. In other words two database systems are likely to be unequal.

7. **GUI is not available**
GUI mode tools to access the database is not flexibly available in the market.

4. **Backup**
Backup is a great weak point for some NoSQL databases like MongoDB. MongoDB has no approach for the backup of data in a consistent manner.

## When to use NoSQL:
If you data is a block, you are making few Updates, wanted to keep it togehter, and is write Optimized(lots of write comming to that)- use NoSQL.
If you want inherent aggregation- use NoSQL.

## Types of NoSQL database:
1. **Key value store**: Memcached, Redis, Coherence
2. **Tabular**: Hbase, Big Table, Accumulo
3. **Document based**: MongoDB, CouchDB, Cloudant



## Demo

### Mongo DB
Mongo DB is a document based with scalability and flexibilty that you want, with the querying and  indexing that you need.  
Means this DB stored Documents. Documents like json.

### Mongo DB example with Spring Boot:-
1. take dependecy of Mongo db from start.spring.io
2. Create Mongo config class:
this commandLineRunner will be invoke by spring when spring starts-up
```java
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig {


    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return strings -> {
            userRepository.save(new Users(1, "Peter", "Development", 3000L));
            userRepository.save(new Users(2, "Sam", "Operations", 2000L));
        };
    }

}

```
3. Create UserRepository:
```java
public interface UserRepository extends MongoRepository<Users, Integer> {
}
```
4. Create User model
```java
@Data
@Document
public class Users {

    @Id
    private Integer id;
    private String name;
    private String teamName;
    private Long salary;

```

5. Create Controller
```java
@RestController
@RequestMapping("/rest/users")
public class UsersResource {

    private UserRepository userRepository;

    public UsersResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Users> getAll() {
        return userRepository.findAll();
    }
}
```

6. Create Application prop files:
```java
spring.application.name=Mongo DB Example


#Mongo Config
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=mongo
spring.data.mongodb.repositories.enabled=true


server.port=8095
```

7. Start mongodb command:
```
mongod --dbpath=.
```
## Acknowledgements

 - [InstallMongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)
  