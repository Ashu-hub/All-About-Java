package GOFDesignPattern.adapterdesignpattern;


public class MovableAdapterImplTest {
	public static void main(String[] args) {
		Movable bugattiVeyron = new BugattiVeyron();
		MovableAdapter bugattiVeyronAdapter = new MovableAdapterImpl(bugattiVeyron);
		
		System.out.println(bugattiVeyronAdapter.getSpeed()); //should come as 431.30312
	}
	
}
