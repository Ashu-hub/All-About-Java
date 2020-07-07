package EscapingReferences;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerRecords {

	private Map<String, Customer> records;

	public CustomerRecords() {
		this.records = new HashMap<String, Customer>();
	}

	public void addCustomer(Customer c) {
		this.records.put(c.getName(), c);
	}

	public Map<String, Customer> getCustomer() {
		// return this.records;// records being it a private field can be modified from
		// outside world. to resolve this we can use below options.

		return Collections.unmodifiableMap(this.records);// Best solution.

		// return new HashMap<>(this.records);// alternate Solutions.
	}

	// For other type of Data
	public Customer getCustomerByName(String name) {
		// return this.records.get(name); // we have provide the way to escaping ref
		// by this anyone can call setName method and can alter the private fields.
		// we can resolve this by using trick used by Collection in line number 24. By
		// creating the copy constructor.
		return new Customer(this.records.get(name));
		// Issue with this approach is that It might confusing that it might changing
		// the original copy.
		// Best approach is to use on Interface. that is Only Providing ReadOnly
		// Customer Obj.
	}

	// Using Interface
	public CutomerReadOnly getCustomerByNameUsingInterface(String name) {
		return this.records.get(name);
	}

}
