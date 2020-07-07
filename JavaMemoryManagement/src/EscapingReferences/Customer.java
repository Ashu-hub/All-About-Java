package EscapingReferences;

public class Customer implements CutomerReadOnly {
	private String name;

	public Customer(String name) {
		this.name = name;
	}

	public Customer(Customer oldCustomer) {
		this.name = oldCustomer.name;
	}
	
	/* (non-Javadoc)
	 * @see EscapingReferences.CutomerReadOnly#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see EscapingReferences.CutomerReadOnly#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
