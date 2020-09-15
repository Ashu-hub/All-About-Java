package GOFDesignPattern;

interface Image {
	void display();
}

class RealImage implements Image {

	public RealImage() {
		System.out.println("Real Image Created");
	}

	@Override
	public void display() {
		System.out.println("Inside Display mehtod of Real Image");
	}

}

class ProxyImage implements Image {

	RealImage realImage;

	@Override
	public void display() {
		System.out.println("Inside Proxy");
		if (realImage == null) {
			realImage = new RealImage();
		}
		realImage.display();
	}

}

public class ProxyPatternExample {

	public static void main(String[] args) {
		Image img = new ProxyImage();
		img.display();
		System.out.println("******");
		img.display();
	}

}
