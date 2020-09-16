# SOLID
	S - Single Responsibilty principle
	O - Open/Closed Principle
	L - LisKov's Substitution principle
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
```

** 	We should always look for loose coupling.**

	**WrapUp**

	1. Aim for high Cohesion and loose coupling.
	2. Following SRP can lead to considerable Software maintenance costs.
	
	
# 	Open Closed Principle

	Software components should be closed for modification but open for extension.
	Closed for modification means- New features getting added to the software component, should NOT have to modify existing code.
	Open for Extension means - Software components should be able to add a new feature
	
 	Example:- 
	1. Suppose there is an insurance company A,which primarly deals with Health Insurance.
	Company supports discount for Loyal Customer of the company.
	So code goes like :-
```java
public class InsurancePremiunDiscountCalculator{
	public int calculateDiscount(HealthInsuranceCustomerProfile customer){
		if(customer.isLoyalCustomer()){
			return 20;
		}
	return 0;
	}
}

public class HealthInsuranceCustomerProfile{
 public boolean isLoyalCustomer(){
 	return true;// or false
 }
}
```	

2. Suppose this company A, accquires Vehicle Insurance company as well. So We need to support vehicle discounts as well.
	And it has decided that discount calculation is going to be the same as ie. discount is always based on Royality regardless of its Health/Vehicle or anything else.
	So to adhere this we add new class in out design.
	
```java
public class VehicleInsuranceCustomerProfile{
 public boolean isLoyalCustomer(){
 	return true;// or false
 }
}
// we have to modify calculator class to support this as currently the calculator class method takes HealthInsuranceCustomerProfile object as Parameter.
// The only way out is - to add an overloaded method which takes VehicleInsuranceCustomerProfile as a parameter.like-
public class InsurancePremiunDiscountCalculator{
	public int calculateDiscount(HealthInsuranceCustomerProfile customer){
		if(customer.isLoyalCustomer()){
			return 20;
		}
	return 0;
	}
	
	public int calculateDiscount(VehicleInsuranceCustomerProfile customer){
		if(customer.isLoyalCustomer()){
			return 20;
		}
	return 0;
	}
}
```
3. This is just the beginning. What if we need to add Home Insurance too. We need to add another code to out class.
	Why bad- To support new feature we need to touch the existing code , which goes against our Open/Closed principle - Closed for Extension.
	What to do?

4. Create an Interface CustomerProfile which has method	isLoyalCustomer.
5. Make Classes HealthInsuranceCustomerProfile & VehicleInsuranceCustomerProfile implements this common Interface.
6. In Calculator class we will change the method paramter as CustomerProfile instead of HealthInsuranceCustomerProfile. like-
```java
public interface CustomerProfile{
	public boolean isLoyalCustomer();
}

public class InsurancePremiunDiscountCalculator{
	public int calculateDiscount(HealthInsuranceCustomerProfile customer){
		if(customer.isLoyalCustomer()){
			return 20;
		}
	return 0;
	}
}

public class HealthInsuranceCustomerProfile implements CustomerProfile{
@Override
 public boolean isLoyalCustomer(){
 	return true;// or false
 }
}

public class VehicleInsuranceCustomerProfile implements CustomerProfile{
@Override
 public boolean isLoyalCustomer(){
 	return true;// or false
 }
}
```

	It handles future Extension very easily with even touch the calculator class.

	Advantages:-
	Ease of adding new features.
	lead to minimal cost of developing and testing softwares.
	OCP often requires decoupling which inturns follow SRP.
	
# LisKov Substitution Principle:
	Named after a scientist BarBara LisKov, Who explained this principle in her Book.
	It goes like **Objects should be replaceable with their subtypes without affecting the correctness of the program.**
	
	** Breaking the pattern:**
	1. Suppose There is a Car, which has getCabinWidth() method.
	2. There is a RacingCar class which extends Car(As RacingCar IS A Car). But RacingCar Does not has CabinWidth, it has cockpitWidth. So it leaves getCabinWidth() method unImplemented.
		Rather it has its own getCockPitWidth() method.
```java
 public class Car {
 	public double getCabinWidth(){
		//return cabin width
	}
 }
 
 public class RacingCar extends Car{
 @Override
 public double getCabinWidth(){
		//Unimplemented
	}
	
	public double getCockPitWidth(){
		//return CockPit width
	}
 }	

public class CarUtils{
	public static void main(String[] args){
		Car first = new Car();
		Car second = new Car();
		Car third = new RacingCar();
		
		List<Car> myCars = new ArrayList<>();
		myCars.add(first);
		myCars.add(second);
		myCars.add(third);
		
		for(Car car : myCars){
			System.out.println(car.getCabinWidth()); //this code will not work correctly because of the unImplemented method.
		}
	}
}

```		
	This is the design Hole, to sole this we should break the Inheritance.
	Sol: - Apply "breaking the hierarchy pattern" of LisKov Substitution
		RacingCar should not extends Car.
		We should come up with comman parent for Both. i.e. Vehicle.
		So we create a new class Called Vehicle which is very generic class and both Car and RacingCar should extends Vehicle class.
		So code goes like- 
		
```java
public class Vehicle{
	public double getInteriorWidth(){
		//returns Interior Width
	}
}
 public class Car extends Vehicle{
 @Override
 public double getInteriorWidth(){
		return this.getCabinWidth();
	}
 
 	public double getCabinWidth(){
		//return cabin width
	}
 }
  public class RacingCar extends Vehicle{
 @Override
 public double getInteriorWidth(){
		return this.getCockPitWidth();
	}
	
	public double getCockPitWidth(){
		//return CockPit width
	}
 }	


public class CarUtils{
	public static void main(String[] args){
		Vehicle first = new Car();
		Vehicle second = new Car();
		Vehicle third = new RacingCar();
		
		List<Vehicle> myVehicles = new ArrayList<>();
		myVehicles.add(first);
		myVehicles.add(second);
		myVehicles.add(third);
		
		for(Vehicle vehicle : myVehicles){
			System.out.println(vehicle.getInteriorWidth()); 
		}
	}
}
```
	**Tell, Don't Ask**
	
eg:-
```java
public class  Product{
	protected double discount;
	
	public double getDiscount(){
	return discount;
	}
}


public class InHouseProduct extends Product{
	
	public void applyExtraDiscount(){
	discount = discount *1.5;
	}
}

public class PricingUtils{
	public static void main(String[] args){
		Product p1 = new Product();
		Product p2 = new Product();
		Product p1 = new InHouseProduct();
		
		List<Product> myProducts = new ArrayList<>();
		myProducts.add(p1);
		myProducts.add(p2);
		myProducts.add(p3);
		
		for(Product product : myProducts){
		
		if(product instanceOf InHouseProduct){
			((InHouseProduct)product).applyExtraDiscount();
		}
			System.out.println(product.getDiscount()); 
		}
		
	}	
}
```
	Above is not a good design as it is not following LSP.
	We sould be able to for all the products instead of typeCasting some of them.
	Sol:
	
```java
public class  Product{
	protected double discount;
	
	public double getDiscount(){
	return discount;
	}
}


public class InHouseProduct extends Product{
	@Override
	public double getDiscount(){
		this.applyExtraDiscount();
		return discount;
	}
	
	public void applyExtraDiscount(){
		discount = discount *1.5;
	}
}

public class PricingUtils{
	public static void main(String[] args){
		Product p1 = new Product();
		Product p2 = new Product();
		Product p1 = new InHouseProduct();
		
		List<Product> myProducts = new ArrayList<>();
		myProducts.add(p1);
		myProducts.add(p2);
		myProducts.add(p3);
		
		for(Product product : myProducts){
			System.out.println(product.getDiscount()); 
		}
		
	}	
}

```
	In the above example the Util class does not need to ask anything while in earlier version it asks for Subtype in util class.
	