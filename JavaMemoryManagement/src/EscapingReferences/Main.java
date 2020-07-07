package EscapingReferences;

public class Main {

	public static void main(String[] args) {

		CustomerRecords c = new CustomerRecords();
		
		c.addCustomer(new Customer("John"));
		c.addCustomer(new Customer("Simon"));
		
//		c.getCustomer().clear(); // Escaping reference.
		
		for(CutomerReadOnly next : c.getCustomer().values() ) {
			System.out.println(next);
		}
		
		Customer john = c.getCustomerByName("John");
		System.out.println(john.getName());
		
		john.setName("Derrick");
		System.out.println(john.getName());
		
		//to check original name has bee changed or not
		for(CutomerReadOnly next : c.getCustomer().values() ) {
			System.out.println(next);
		}
		
		//Best way to prevent ER
		CutomerReadOnly simon = c.getCustomerByNameUsingInterface("Simon");
		System.out.println(simon.getName());
		//simon.setName("Simonee"); Compilation error- full proof for ER.
	}

}
