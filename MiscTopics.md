ApiSero	questions:

 # SSL-(Secure Socket Layer):-
 
	- Secure Sockets Layer (SSL) is a security protocol used  
		for establishing an encrypted link between a server and a client—typically a web server (website) and a browser, or a mail server and a mail client (e.g., Outlook).
	- SSL allows sensitive information such as credit card numbers, social security numbers, and login credentials to be transmitted securely.
	- Normally, data sent between browsers and web servers is sent in plain text—leaving you vulnerable to eavesdropping. 
	- If an attacker is able to intercept all data being sent between a browser and a web server,	they can see and use that information.
	- All browsers have the capability to interact with secured web servers using the SSL protocol. 
		However, the browser and the server need what is called an SSL Certificate to be able to establish a secure connection
		
# 1-Way SSL:-

	- Only Client authenticate server
	- Server  doesnot care about who is the client
	- eg:- when you hit https://google.com from you computer, the server(google.com) responds and the client is responsible for authenticate the server.
	
# 2- Way SSL:-

	- Both Client and Sever authenticate each other.
	- Certificate exchange between both the parties,
	- Used to B2B/ Server to Server Communication.
	- 
		Step 1: Client Hello:- (Client to Server) 
			It will have client version ,client random, Session id, Compression method, Cipher suite. 
			
			(Server to client)
		Step 2:Server response will have Server version, server random, Session ID, Compression method, Cipher suite.
		Step 3: Server also send its Certificate to the client containing public key of the server. This acts as a Identity of the server. Client validated this certificate to ensures it is not expired.
		Step 4: Server sends Certificate request  to the client
		Step 5: Server confirm that the Hello Message is finished.

****************************************************************************************
# Cloud Computing:

	Simply put, cloud computing is the delivery of computing services—including servers, storage, databases, networking, software, analytics, and intelligence—over the Internet (“the cloud”) 
	to offer faster innovation, flexible resources, and economies of scale.
	What is the need of cloud?
	 * Accessibility; Cloud computing facilitates the access of applications and data from any location worldwide and from any device with an internet connection *
	 * Cost savings; Cloud computing offers businesses with scalable computing resources hence saving them on the cost of acquiring and maintaining them.*
		These resources are paid for on a **pay-as-you-go** basis which means businesses pay only for the resources they use. This has proven to be much cheaper than acquiring the resources on their own.
	* Security; Cloud providers especially those offering private cloud services, have strived to implement the best security standards and procedures in order to protect client’s data saved in the cloud.*	
	* Disaster recovery; Cloud computing offers the most efficient means for small, medium and even large enterprises to backup and restore their data and applications in a fast and reliable way.*
	
# Cloud Security:

	Cloud security is the protection of data stored online via cloud computing platforms from theft, leakage, and deletion
	
# SaaS vs PaaS, IaaS

	IaaS, SaaS and PaaS are three main categories of cloud computing.
	IaaS:
		IaaS (Infrastructure as a Service) is a model by which computing resources are provided virtually.
		An IaaS cloud provider can give you the entire range of resources needed for an enterprise. It provides servers, storage and networking hard drive. It also provides maintenance and support. 
		Businesses can fulfill there requirements without installing any hardware. It provides resources on outsourced basis for enterprise operations. It also provides data center space and network components.
		
			Following are the examples of IaaS:	Digital Ocean, AWS (Amazon Web Services)
	
	SaaS:
		SaaS (Software as a Service) is a model in which software is used and purchased by an online subscription rather than getting license, installing and using it as desktop software. SaaS is centrally hosted. It is also called “on-demand-software” and “software plus services”. 
		In this model a third party provider hosts the application and make this application available to subscribed users over the internet. 
		This model gives a quick access to web applications. Maintenance and support is provided by the service provider. Following are some of the examples of SaaS
		Google Apps, Salesforce, Slack, Drop Box.
		
	PaaS:
	PaaS (Platform as a Service) is a cloud computing model which provides a cloud base where you can test and run your applications. 
	It simplifies the process of software development. Basically it is a model which provides hardware and software tools which are needed for application development process on the internet to the users. 
	A Platform as a Service provider hosts hardware and software on its own infrastructure. Following are some of the examples of PaaS.
	Windows Azure, AWS Elastic Beanstalk, Google App Engine