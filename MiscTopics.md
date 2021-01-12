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

******************************************************************************************************************************88

# agile

*Q) What is agile?*
	Agile is an Iterative approach to project management and software development that helps teams deliver values to their customer faster and in effective way.
	Instead of betting everything on a "big bang" launch, an agile team delivers work in small, but consumable, increments. Requirements, plans, and results are evaluated continuously so teams have a natural mechanism for responding to change quickly.
	
*Q) Which thing you like and dnt like about Agile?*
	 3 things like primirly:-
	 a) Flexibility- Agile ensures continuous product delivery and improvement. It helps rapidly adapt products to changing market requirements
	 b) Teamwork - The methodology increases the product team efficiency.
	 c) continous feedback- Due to continous feedback in every iteration, users get to see quick improvements and feels ownership.
	 
	 3 things dnt like primirly:-
	 a) Planning and/or prediction. Frequent changes in the workflow can affect project planning and on-time task completion.
	 b) Control and documentation. - There is less focus on documentation in Agile. Thus, in some cases, nobody knows what (and why) was developed.
	 c) 
	 
Q)  What is Velocity?
	 It is rate at which team progress sprint by sprint.
	 
Q) What do you know about Impediments in Scrum?
	Obstacles or issues faces by scrum team which slow down their speeed of work.
	eg:- Resource missing or sick team member, Technical, operational, organizational problems, Lack of Infrastructure(not Laptop issue).
	
	way to remove Impediements:- 
	
Q) What is the difference and similarity between Agile and Scrum?
	Agile is the methodology used for project management, while Scrum is the ust a form  of the Agile the describe the process and its steps more concisely. Agile is a **practice** whereas scrum is a procedure to **pursue this practice**.	
	
Q)  What is the increment?
	An increment is the total of all the product backlogs items completed during a sprint.

Q) What is the “build-breaker”?
	The build-breaker is a situation that arises when there is a bug in the software. Due to this sudden unexpected bug, compilation process stops or execution fails or a warning is generated.
	
Q) What are the values of Agile?
	(WRIC)
	Individuals and interactions over processes and tools
	Working software over comprehensive documentation
	Customer collaboration over contract negotiation
	Responding to change over following a plan
	
Q) Define Zero Sprint and Spike in Agile?
	Spike – Spike is the type of story that can be taken between the sprints. Spikes are commonly used for the activities related to the design or technical issues such as research, design, prototyping, and exploration. There are two types of spikes – functional spikes and technical spikes.
	
	Zero sprint – Zero Sprint can be defined as the preparation step of the first sprint in Agile. There are some activities that are required to be done before actually starting the project. These activities are considered as the Zero sprint; the examples of such activities are – setting the environment for development, preparation of backlogs etc.
	
Q) What do you know about “Planning Poker” technique?
	Planning poker, also known as **Scrum Poker.**
	All participants use numbered playing cards and estimate the items.
	Voting is done anonymous and discussion is raised when there are large differences.
	Voting is repeated till the whole team reached consensus about the accurate estimation.
	Planning poker works well when you have to estimate a relative small number of items (max 10) in a small team (5-8 people).
	Tip: try to keep the voting between affordable numbers. Maximize the highest card to 13 points.
	
Q) Is it ever suggested to use waterfall over Scrum? If yes, explain when?
	Yes, sometimes it is suggested to use a waterfall model over Scrum. It is done when the customer requirements are simple, well-defined, fully understood, predictable, and are not subjected to change until the completion of the projec

Q) What is the difference between the agile & traditional way of working?
	The traditional way is sequential where design->Development->Testing etc. happens one after another whereas in agile all of this is done in every iteration/sprint
	Changes are welcomed in agile as Scope is flexible whereas in traditional manner scope is fixed in the beginning due to which changes have to follow change request path
	Progress is measured with % completion traditionally whereas working software is the measure of progress in agile
	Project Manager as a central controlling authority is traditionally driving the project whereas Self-motivated and self-organizing teams drive the projects in agile.
	
Q) 	You are the SM on the project. PO wants to put a large story into the sprint in the middle. How would you handle it?
	Identify What and Why(Why now, why not in next)
	Identify its Buisness criticality. If it is very critical - whether this large can be divided into smallar chunks. If not atlease try to divide it into 2.
	So that onecan deliver in this spirint and another in next.
	
Q) What is SAFE (Scaled Agile Framework)?
	The Scaled Agile Framework (SAFe) is a set of organization and workflow patterns intended to guide enterprises in scaling lean and agile practices.
	
Q)  How do Agile and SAFe Frameworks differ? What are the four core values of SAFe? 
	Agile is a broad term that covers several frameworks, out of which one is SAFe. This framework, established by Dean Leffingwell, is specifically for large-scale enterprise projects as it scales up other models like Scrum to an enterprise level. 
	It is based on three fundamental principles: Agile Development, Lean Product Development and Systems Thinking. 
	
	The four core values of SAFe are:(ABTP)
	
	Alignment - Keeping pace with the rapid changes
	Built-in quality - Every element must be up to the quality standards
	Transparency - Trust and reliability
	Program Execution - Deliver continuously and efficiently
	
Q) What metrics are used in SAFe? 
	Metrics are pre-decided parameters which are used to measure how well an organisation is performing and progressing to achieve its objectives. In SAFe, four metrics are considered:

	Portfolio
	Large solution
	Program
	Team
	
Q)  What is the role of a Release Train Engineer (RTE)?
	he RTE is the coach of an Agile Release Train (ART). Their primary responsibility is to accelerate the ART events and assist in delivering the value. RTEs must have excellent communication skills, and they are the ones who usually interact with the stakeholders as well. 
	
Q) What do you know about the Innovation and Planning (IP) Iteration in SAFe?
	IP is also aided by constant learning. This way, the people get time to innovate and explore beyond the iterations dedicated towards the delivery of the product
	
Q)  What's the difference between user stories and enabler stories? 
	User stories are stories which deliver functionality directly to the end-user. These are usually written in simple language that the user can understand, and this language will also help the Agile team appreciate what the user wants. 

     Enabler stories give an insight into the work items needed to support exploration, architecture, infrastructure and compliance. These may never be seen by the end-user, and are often written in technical language.
	 