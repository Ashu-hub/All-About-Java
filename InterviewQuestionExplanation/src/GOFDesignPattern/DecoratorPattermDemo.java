package GOFDesignPattern;

interface Shape1 {
	void draw();
}

class Circle1 implements Shape1 {

	@Override
	public void draw() {
		System.out.println("Circle method");

	}

}

class Rectangle1 implements Shape1 {

	@Override
	public void draw() {
		System.out.println("Circle method");

	}

}

abstract class ShapeDecorator implements Shape1 {
	protected Shape1 decoratorShape;

	public ShapeDecorator(Shape1 decoratoeShape) {
		this.decoratorShape = decoratorShape;
	}
	
	@Override
	public void draw() {
		decoratorShape.draw();
		
	}
}

class RedShapeDecorator extends ShapeDecorator{

	public RedShapeDecorator(Shape1 decoratoeShape) {
		super(decoratoeShape);
	}
	
	@Override
	public void draw() {
		decoratorShape.draw();
		setRedColor(decoratorShape);

	}

	 void setRedColor(Shape1 decoratorShape2) {
		 System.out.println("Applied Red Color");
	}

	
}

public class DecoratorPattermDemo {

	public static void main(String[] args) {
		Shape1 circle = new Circle1();
		
		Shape1 redCircle = new RedShapeDecorator(new Circle1());
		Shape1 redRectangle = new RedShapeDecorator(new Rectangle1());
		
		circle.draw();
		redCircle.draw();
		redRectangle.draw();
	}

}
