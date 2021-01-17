# Generic:

1. What is Refactoring?
	Ans:- The main idea of refactoring is to fight technical debt. IT transform a mess into a clean code.

2. What is technical debt?
	Ans:- It is a concept that reflects the implied code of additional rework cause by choosing easy(limited) solution now instead of using a better approach.

3. What is Clean code?
	Ans:- Clean code has following characteristics:-
		1) Clean code is obvious for other programmers.
		2) CC does not contain duplication.
		3) CC has minimal number of classes.
		4) CC have and passes all tests.
		5)} CC is easy to maintain.
		
4. What is code smells?
	Ans:- 	It is any characteristics of code that indicates deeper problem.
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
	Issue type:- Bug, Smell, Vulnerability.

###	Issue Severity:- 

	1) Blocker - Bug with high probablity to impact behaviour of applicaton in prod. The Code must fix immediately.
	2) Critical - Either Bug with low probablity to impact behaviour of applicaton in prod or issues which represents secutiry flaws. like empty catch block, SQL injection.The code must of Reviewed immediately.
	3) Major - Quality Flaw that highly impact  developer productivity. eg- uncovered piece of code, duplicate code, unused parameter etc.
	4) Minor - Quality flaw that slightly impact developer productivity.  eg- lines should not be too long, switch should have atleast 3 cases etc.
	5) Info - Niether bug or quality flaq, Just a finding.	
	
	
		
	

