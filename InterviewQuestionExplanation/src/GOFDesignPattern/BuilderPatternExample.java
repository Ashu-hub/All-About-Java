package GOFDesignPattern;

class Phone {
	String OS;
	String processor;
	int battery;

	public Phone(String oS, String processor, int battery) {
		super();
		OS = oS;
		this.processor = processor;
		this.battery = battery;
	}

}

class PhoneBuilder {
	String OS;
	String processor;
	int battery;

	public PhoneBuilder setOS(String oS) {
		OS = oS;
		return this;
	}

	public PhoneBuilder setProcessor(String processor) {
		this.processor = processor;
		return this;
	}

	public PhoneBuilder setBattery(int battery) {
		this.battery = battery;
		return this;
	}

	public Phone getPhone() {
		return new Phone(processor, OS, battery);

	}
}

public class BuilderPatternExample {

	public static void main(String[] args) {
	Phone p = new PhoneBuilder().setBattery(1000).setOS("MAc").getPhone();
		

	}

}
