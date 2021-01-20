# Generic:

1. What is Refactoring?
	Ans:- The main idea of refactoring is to fight technical debt. IT transform a mess into a clean code.

2. What is technical debt?
	Ans:- It is a concept that reflects the implied code of additional rework cause by choosing easy(limited) solution now instead of using a better approach.
	
	Types of Technical debt:-
	a.	Source code formatting
	b.	Low test coverage
	c.	lack of modularity 
	d.	Bad code design 
	e.	lack of documentation
	
2.1. What causes technical debt?

	a.	poor code review
	b.	no use of static code analysis tool like sonar or lint
	c.	poor test coverage
	d.	bad development practices
	e.	Use of outdated tools or technology

2.2. Tools to find technical debts - Sonar, Jacoco

3. What is Clean code?
	Ans:- Clean code has following characteristics:-
		1) Clean code is obvious for other programmers.
		2) CC does not contain duplication.
		3) CC has minimal number of classes.
		4) CC have and passes all tests.
		5)} CC is easy to maintain.
		
4. What is code smells?
	Ans:- 	It is any characteristics of code that indicates deeper problem.
				OR
		A maintainability issue that makes your code confusing and difficult to maintain.
		
		Comments
		A method is filled with explanatory comments.

		Duplicate Code
		Two code fragments look almost identical.

		Lazy Class
		Understanding and maintaining classes always costs time and money. So if a class doesn’t do enough to earn your attention, it should be deleted.

		Data Class
		A data class refers to a class that contains only fields and crude methods for accessing them (getters and setters). These are simply containers for data used by other classes. These classes don’t contain any additional functionality and can’t independently operate on the data that they own.

		Dead Code
		A variable, parameter, field, method or class is no longer used (usually because it’s obsolete).

		Speculative Generality
		There’s an unused class, method, field or parameter. Sometimes code is created "just in case " to support anticipated future feature thats never gets implemented,as a result code becomes hard to understand and support.
		
5. Cyclomatic complexity:-
		Cyclomatic complexity is a software metric used to indicate the complexity of a program. It is a quantitative measure of the number of linearly independent paths through a program's source code
		
# Code qality metrics-
	Sonarqube:- It does standard static code analysis. It anayze code, find bugs, vulnerability and test Code Coverage.
	
	eg-
	Come Smell:-
	A maintainability issue that makes your code confusing and difficult to maintain.
					OR
	IT a charcteristics of code which indicates depper problem.
	eg:- to many comments, Duplicate codes, Lazy classes etc.
	
	Bug – A coding error that will break your code and needs to be fixed immediately.
	
	Vulnerability – A point in your code that's open to attack.
	
	While Running Analysis, Sonar raises an issues everytime a piece of code breaks a coding rule.
	
	**Issue type:- Bug, Smell, Vulnerability.**

###	Issue Severity:- 

	1) Blocker - Bug with high probablity to impact behaviour of applicaton in prod. The Code must fix immediately.
	2) Critical - Either Bug with low probablity to impact behaviour of applicaton in prod or issues which represents secutiry flaws. like empty catch block, SQL injection.The code must of Reviewed immediately.
	3) Major - Quality Flaw that highly impact  developer productivity. eg- uncovered piece of code, duplicate code, unused parameter etc.
	4) Minor - Quality flaw that slightly impact developer productivity.  eg- lines should not be too long, switch should have atleast 3 cases etc.
	5) Info - Niether bug or quality flaq, Just a finding.	
	
# What is DRY.
	DRY stand for "Don't Repeat Yourself," a basic principle of software development aimed at reducing repetition of information.
	The DRY principle is stated as, **"Every piece of knowledge or logic must have a single, unambiguous representation within a system."**
	
	How to Achieve DRY
	To avoid violating the DRY principle, divide your system into pieces. 
	Divide your code and logic into smaller reusable units and use that code by calling it where you want. 
	Don't write lengthy methods, but divide logic and try to use the existing piece in your method.
	
	DRY Benefits:-
	Less code is good: It saves time and effort, is easy to maintain, and also reduces the chances of bugs
	
	Is code duplication always bad?
	
	No. Sometimes code duplication can be allowed in situations when you want to remove dependencies between modules. 
	Removing every duplication in your codebase introduces dependencies in your project. 
	
# KISS: Keep It Simple, Stupid
	The KISS principle is descriptive to keep the code simple and clear, making it easy to understand. 
	After all, programming languages are for humans to understand — computers can only understand 0 and 1 — so keep coding simple and straightforward. Keep your methods small. 
	Each method should never be more than 40-50 lines
	Each method should only solve one small problem, not many use cases
	
	How to Achieve KISS
	Try to write simple code.
	Whenever you find lengthy code, divide that into multiple methods
	
	Benefit of KISS
	Easy To understand, debug, maintain. 
	Increased readibility

# The Hollywood Principle

# YAGNI principle	
	

