# Spring Boot Interview questions:-

- What is Dispatcher Servlet? Front Controller for Spring MVC framework.

- Who Configures Dispatcher Servlet?- Springboot AutoConfiguration
	
- What does Dispatcher Servlet do? - It know all the mappings.It will find right controller for the request.

- How beans gets converted into JSON? - Because of Springboot AutoConfiguration. As JacksonBeans(MessageConverter) are getting initialized in classPath.

- Who is configuring error mapping?- Springboot AutoConfiguration. It created default error page for us.

- What is Content negotiaiton?

- What is Idempotence?
	Property of Certain operation in Computer Science, that can be applied multiple times without changing the result beyond initial application .
	So- Get, PUT, DELETE are- **Idempotent**
				POST - **Non Idempotent.** Resouce creation should always by POST method.

- API Documentation? Springfox-Swagger2?
	Swagger2 is an open source project used to generate the **REST API documents for RESTful web services.**
	Steps:
	1) Add 2 dependencies- springfox-swagger2 and springfox-swagger-ui
	2) Now, add the @EnableSwagger2 - It will enable the Swagger2 in application.
	3) Create Docket Bean to configure Swagger2 for your Spring Boot application. We need to define the base package to configure REST API(s) for Swagger2
		like:-
```java		
		@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.exl.dca"))
                .paths(regex("/quotes.*"))
                .build()
```

- What is Actuator?
	Spring boot Actuator provides secured endpoints for monitoring and managing you spring boot application.
	Setps:-
	 1. Add Dependency spring-boot-starter-actuator
	 2. In the application.properties file, we need to disable the security for actuator endpoints. 
		management.security.enabled = false
		management.port = 9090 - this will enable diff port for actuator
		
- How do you handle Logging in the applcation:
	Inbuilt logging functionality in Springboot.(slf4j)
	