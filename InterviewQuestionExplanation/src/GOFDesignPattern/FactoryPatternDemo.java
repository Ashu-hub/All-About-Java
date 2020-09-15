package GOFDesignPattern;

interface Shape {
	void draw();
}

class Circle implements Shape {

	@Override
	public void draw() {
		System.out.println("Inside Shape Circle");
	}
}

class Rectangle implements Shape {

	@Override
	public void draw() {
		System.out.println("Inside Shape Rectangle");

	}
}

class Square implements Shape {

	@Override
	public void draw() {
		System.out.println("Inside Shape Square");
	}
}

class ShapeFactory {
	public Shape getShape(String shape) {

		if ("Circle".equals(shape)) {
			return new Circle();
		} else if ("Rectangle".equals(shape)) {
			return new Rectangle();
		} else if ("Square".equals(shape)) {
			return new Rectangle();
		}
		return null;
	}
}

public class FactoryPatternDemo {
	public static void main(String[] args) {

		ShapeFactory sf = new ShapeFactory();
		Shape c = sf.getShape("Circle");
		c.draw();
		
	}
}