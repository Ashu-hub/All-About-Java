# SOLID
	S - Single Responsibilty principle
	O - Open/Closed Principle
	L - Liscov's Substitution principle
	I- Interface Segregation Principle
	D - Dependency Injection Principle

## Single Responsibilty principle - 
	Every Software Component should have one and only one responsibility.
	OR
	Every Software Component should have one and only one reason to change.
	Software Component in OOPs are- It can be class, method or a module.
	
#### **Cohesion**  - 
	is the degree to which various parts of a software components are related. or it is a degree of relation.
	eg:- 
	Garbage lying at one place vs Garbage segrerated as plastics, papaer, metal, glass. etc.
	The cohesion is high in plastics as they are related by a relation.
eg:-
```java
public class square{
	int side = 2;
	public int calculateArea(){
	 	return side*side;
	}
	public int calculatePerimeter(){
 		return side*4;
	}

	public void draw(){
		if(highResolution){
			//Render high Resolution image
		}
		else{
			//render real image
	}
	
	public void rotate(int degree){
		//rotate the image clockwise by specified degree.
	}
}
```
	
	The above code has very low cohesion overall. calculateArea and calculatePerimeter are closely related(and high cohension),draw() and rotate() are interelated (high cohension), but overall they cohension is low.

Sol:
```java
public class square{
	int side = 2;
	public int calculateArea(){
	 	return side*side;
	}
	public int calculatePerimeter(){
 		return side*4;
	}
}

// Respponsibility :- Measurement of Square

public class squareUI{
	public void draw(){
		if(highResolution){
			//Render high Resolution image
		}
		else{
			//render real image
	}
	
	public void rotate(int degree){
		//rotate the image clockwise by specified degree.
	}
}
// Respponsibility :- Rendering of image of square.
```
	So we can safely say that 1st class square has responsibility related to measurement of square.
	Similary 2nd class is related to 
	
**One aspect of SRP is that- we should always aim to achieve high cohension within the components(class in this case)**	

#### Coupling  - 
	Coupling is defined as the level of inter-dependency between various software components.
	like:
```java
public class Student{
	
	private String studentID;
	private String studentDOB;
	private String address;
	
	public void save(){
	String objStr = MyUtils.serializeIntoString(this);
	Connection conn = null;
	Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		....
	//code for saving into DB.
	}
	}
	
	public String getStudentId(){
		return studentID;
	}
	public void setStudentId(Stinng studentID{
	this.studentID = studentID;
	)
}
```

In this there save method is tightly couple with Student class.

Better Approach

```java
public class Student{
	
	private String studentID;
	private String studentDOB;
	private String address;
	
	public String getStudentId(){
		return studentID;
	}
	public void setStudentId(Stinng studentID{
	this.studentID = studentID;
	)
}

public class StudentRepo{
public void save(){
	String objStr = MyUtils.serializeIntoString(this);
	Connection conn = null;
	Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		....
	//code for saving into DB.
	}
	}
}
```
## Example of SRP
```java
public class Student{
	
	private String studentID;
	private String studentDOB;
	private String address;
	
	public void save({
		new StudentRepo().save(this);
	)
	
	public void calculateTax(){
	new TaxCalculator().calculateTax(this);
	}
	public String getStudentId(){
		return studentID;
	}
	public void setStudentId(Stinng studentID{
	this.studentID = studentID;
	)
}

public class StudentRepo{
public void save(Student student){
	String objStr = MyUtils.serializeIntoString(student);
	Connection conn = null;
	Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		....
	//code for saving into DB.
	}
	}
}

public class TaxCalculator{
	public void calculateTax(Student student){
		//.... code to calculate tax for Student.
	}
}


** 	We should always look for loose coupling.**

	**WrapUp**

	1. Aim for high Cohesion and loose coupling.
	2. Following SRP can lead to considerable Software maintenance costs.