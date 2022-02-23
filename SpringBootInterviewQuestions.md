# Spring Boot Interview questions:-
- AOP:
	Aspect oriented programming(AOP) as the name suggests uses aspects in programming. 
	It can be defined as the breaking of code into different modules, also known as modularisation, where the aspect is the key unit of modularity.
	
	Aspect: The class which implements the JEE application's cross-cutting concerns(is a concern which is applicable throughout the application- transaction, logger etc) is known as the aspect.
	Advice: The job which is meant to be done by an Aspect or it can be defined as the action taken by the Aspect at a particular point. 
	
	There are five types of Advice namely: Before, After, Around, AfterThrowing and AfterReturning

- IOC -  Inversion of control- 
 	IoC inverts the flow of control as compared to traditional control flow, to achieve loose coupling. Here, controls refer to any additional responsibilities a class has, other than its main responsibility 
	Like in Spring it means giving the **control of creating and instantiating the spring beans to the Spring IOC container. **
		and the only work the developer does is configuring the beans in the spring xml file.

- Dependency Injections- 

	Flavor of Inversion of Control. Let's look at these two words separately. Here the dependency part translates into an association between two classes. For example, class A is dependent of class B.
	Now, let's look at the second part, injection. All this means is, class B will get injected into class A by the IoC.
	
	Dependency Injection (or sometime called wiring) helps in gluing classes together and at the same time keeping them independent.

-	Circular Depency in Spring?
	https://www.baeldung.com/circular-dependencies-in-spring
	
- What is Dispatcher Servlet? Front Controller for Spring MVC framework.

- Who Configures Dispatcher Servlet?- Springboot AutoConfiguration
	
- What does Dispatcher Servlet do? - It know all the mappings.It will find right controller for the request.

- How beans gets converted into JSON? - Because of Springboot AutoConfiguration. As JacksonBeans(MessageConverter) are getting initialized in classPath.

- Who is configuring error mapping?- Springboot AutoConfiguration. It created default error page for us.

- What is Content negotiaiton?
	As a server you have options to provide data in different format like json/xml.
	You need to provide the dependency for supporting your data format(like for json - jackson core, xml - jackson dataformat xml).
	Now client can ask for any format(by Accept application/xml or json in Postman).
	We can also restrict it by using produces in the RequestMapping attribute.
	
- What is Idempotence?
	Property of Certain operation in Computer Science, that can be applied multiple times without changing the result beyond initial application.
	So- Get, PUT, DELETE are- **Idempotent**
				POST - **Non Idempotent.** Resouce creation should always by POST method.

- API Documentation? Springfox-Swagger2?
	Swagger2 is an open source project used to generate the **REST API documents for RESTful web services.**
	Steps:
	1) Add 2 dependencies- springfox-swagger2 and springfox-swagger-ui
	2) Now, add the @EnableSwagger2 in the main class - It will enable the Swagger2 in application.
	3) Create Docket Bean to configure Swagger2 for your Spring Boot application. We need to define the base package to configure REST API(s) for Swagger2
		like:-
```java		
		@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hello.dcaa"))
                .paths(regex("/quotes.*"))
                .build()
```
	4) Can user  @ApiOperation(value = "Add a book", response = Book.class) with methods and 
				@ApiParam(value = "Book object store in database table", required = true, with paramaters.
	  
- What is Actuator?
	Spring boot Actuator provides secured endpoints for monitoring and managing you spring boot application.
	Setps:-
	 1. Add Dependency spring-boot-starter-actuator
	 2. In the application.properties file, we need to disable the security for actuator endpoints. 
		management.security.enabled = false
		management.port = 9090 - this will enable diff port for actuator
		
- How do you handle Logging in the applcation:
	Inbuilt logging functionality in Springboot.(slf4j)
	
**Q) What happen when you write server.port = 0 in application property file?**
	
	Spring boot will assign any random available port to the application.