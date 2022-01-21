- **Stateless vs StateFul?**

	In Stateless, server is not needed to keep the server information or session details to itself. eg:- HTTP
	In stateful, a server is required to maintain the current state and session information. eg:- FTP
	In stateless, server and client are loosely coupled and can act independently.
	In stateful, server and client are tightly bound.
	Statelessness means that every HTTP request happens in complete isolation.
	
- **What Is Restful?**

	REST is an acronym for REpresentational State Transfer. It is architectural style for distributed hypermedia systems.
	
- **Why Rest Is Stateless?**

	Simply put: In REST applications, each request must contain all of the information necessary to be understood by the server, rather than be dependent on the server remembering prior requests.
	Storing session state on the server violates the stateless constraint of the REST architecture. So the session state must be handled entirely by the client.

- **Why Rest Lightweight?**

	It is lightweighted as it does not have envelope-style of payload transport like SOAP.

- ** What are REST Architectural Constraints?**
	
	Uniform interface
	Client–server
	Stateless
	Cacheable
	Layered system
	Code on demand (optional)
	[Ref](https://restfulapi.net/rest-architectural-constraints/)

- **API Design Principle?**
	
	Generally, a good API design should meet below 2 requirement:
	1. Platform Independency. Each client can consume API without knowing how it is implemented. API should follow standard protocol and message format to provide service
	2. System Reliability. In the circumstance that API has been delivered and non-API version changes, API should be responsible to API contract,  no any changes which might cause destructive data.
	
	List some simple design principle:

- **What are principles API- FIRST Design?**

	1. Your API is the first user interface of your application
	2. Your API comes first, then the implementation
	3. Your API is described (and maybe even self-descriptive)
	
	Description: Supposse 3 different teams are developing 3 different mocroservices, which interact with each other in order to get a valid response.
	Like 
	
	Transaction -----> User ----> db
		|
		--------------> Stocks ----> stock API
	
	3 different teams should know in advance, what other APIs should look like, in order to work in parrallel. 
	In this scenerio, API firt Design should work effectively.
	
	How do you do that:- swaggerHub (Platform for API design and documentations) ->There you can find template to create basic API design by the help on simple ymal file or you can create one from scratch.

- **REST is not always best, Comment?**
	
	

- **How to maintian session in HTTP?**

	HTTPSession?
	There is something called SpringSession in Spring framework which can be used to creating a ID to every user. So It aatached that ID to particular user.

-** When I hit a req I got response in XML,I want it  in JSON, how to modify the request to get that?**

	You need to use Use the Accept: application/json in HTTP Header.
	HTTP Headers are not part of URL.
	
	* One way to do is use Application/Json in Postman or change the API to produces application.json
	
- **Custom Datatype?**

	Enum

- **What is HTTP E-Tag?**

	Entity Tags. This is an HTTP Response Header. This is used for HTTP Caching or Conditional Requests
	
- **Idempotent and Non Idempotent:-**

	Property of Certain operation in Computer Science, that can be applied multiple times without changing the result.
	like x=1 , if we run multiple time x will remian 1, SO this operation is Idempotent
	Consider x++, if we run multiple times, x will keep changing its value. So this operation is NON- idempotent.
	
-	**Put Vs Post:-**

	PUT request is made to a particular resource. If the Request-URI refers to an already existing resource, an update operation will happen, otherwise create operation should happen.
	eg:- PUT /article/{article-id}
	POST method is used to request that the origin server accept the entity enclosed in the request as a new subordinate of the resource identified by the Request-URI in the Request-Line. It essentially means that POST request-URI should be of a collection URI.
	eg:- POST /articles
	
	PUT method is idempotent. So if you send retry a request multiple times, that should be equivalent to single request modification.
	POST is **NOT idempotent.** So if you retry the request N times, you will end up having N resources with N different URIs created on server.
	
	Generally, in practice, always use PUT for UPDATE operations.
	Always use POST for CREATE operations.

- **Can we send POST for Updation?*
	
	Yes.
	
-	**Put Vs Patch?**
	When a client needs to replace an existing Resource entirely, they can use PUT. When they're doing a partial update, they can use HTTP PATCH.
	Put is Idempotent. PATCH is used to make minor updates to resources and it’s not required to be idempotent(If there is no parameters matching for PATCH, it will simply fail, will not create any new like PUT)
	
- 	**HTTP(Rest) Methods And Which One Is Idempotent?**
	
	HTTPS Methods are:
	GET PUT DELETE - Idempotent
	POST- Non- Idempotent
	
-	**How Did You Handle CORS?**
	Cross Origin Resource Sharing.
	
	What is Same Origin? - Two URLs have same origin if they have idetical Schemes, hosts and ports.
		Scheme = protocol (Like http and https are two diff schemes so different origin)
		host = Host is a domain. Like http://sample.com and http://sample.net is having different domain, so different origin.
		port = port is a place where the application listen to.
		
	Unless is request is from same origin , the request will be rejected by the browser. As By defaut, browser prevent any AJAX request form another Origin.
	Sol:- CORS, it the satnadard to relax the same origin policy.
	
	How to enable in REST? - Add @CrossOrigin(Origin = "*") in specific method or  add WebMvcCofidurerAdapter method(bean) in Application class, with mapping and allowed origin.


- **Exception Handling In Rest?**

	1) Create a class with @ControllerAdvice annotation which enables its method to shared among all controller available. And Extends ResponseEntityExceptionHandler, it provide centralized exception handling for all RequestMapping methods.
	2) Create a method with annotation @ExceptionaHandler(CustomExceptionClass.class) which handle the particular exception(in our case CustomExceptionClass.class)
	3) Create a class CustomExceptionClass which extends RuntimeException and simply put a paramertized constructor and call super(paramter).
	4) One can throw this custom exception as throw new CustomExceptionClass(message)

-** What is What is the serialVersionUID?**

		The serialVersionUID is a universal version identifier for a Serializable class. Deserialization uses this number to ensure that a loaded class corresponds exactly to a serialized object.
		If no match is found, then an InvalidClassException is thrown

-** Whats HTTPS Response status Code?**
	
	1. 1xx Informational
	2. 2xx Success
	3. 3xx Redirection
	4. 4xx Client Error
	5. 5xx Server Error
	
-** SOAP Vs REST**
	1) SOAP stands for Simple Object Access Protocol
	2) SOAP is a protocol.
	3) SOAP can't use REST because it is a protocol.
	4) SOAP uses service interfaces like @WebService to expose the business logic.
	5) SOAP permits XML data format only.
	6) SOAP requires more bandwidth and resource than REST.(SOAP that uses XML for the creation of Payload and results in the large sized file.)
	7) SOAP API used Web Services Description language for describing the functionalities being offered by web services.
	
	1) REST stands for REpresentational State Transfer.
	2) REST is an architectural style.
	3) REST can use SOAP web services because it is a concept and can use any protocol like HTTP, SOAP.
	4) REST uses URI to expose business logic.
	5) REST permits different data format such as Plain text, HTML, XML, JSON etc.
	6) REST requires less bandwidth and resource than SOAP.(As REST API deploys multiple standards, so it takes fewer resources and bandwidth )(does not have envelope-style of payload transport like SOAP.)
	7) REST API uses Web Application Description Language
	
-** Web Application Description Language  Vs Web Services Description language
	WSDL The Web Service Description Language (WSDL) is an XML vocabulary used to describe SOAP-based web services. 
	A client can load a WSDL file and know exactly which RPCstyle methods it can call, what arguments those methods expect, and which data types they return.

	WADL The Web Application Description Language (WADL) is an XML vocabulary used to describe RESTful web services. 
	As with WSDL, a generic client can load a WADL file and be immediately equipped to access the full functionality of the corresponding web service.
	WADL The Web Application Description Language is an XML vocabulary for expressing the behavior of HTTP resources

**Q) How to pass header parameters as method inputs?**

**Q) Write a simple API to upload a file.**

**Q)Different HTTP codes?**

**Q)How to call Rest Services asynchronously?**

Which Framework You Have Used And Why?
How Did You Configure The Said Framework?
Implement Security In Rest?
Authentication In Rest? OAUTH/OAuth2/Basic
Custom Response 200 Even If There Is Exception? - No We send HTTP BAD_REQUEST
Code For Any Of The Rest Call?
What Are The Annotations That Are From Spring And Which Are Form JAX-RS ?

