package GOFDesignPattern.adapterdesignpattern;

public class MovableAdapterImpl implements MovableAdapter {

	private Movable luxryCars;
	
	public MovableAdapterImpl(Movable bugattiVeyron) {
		this.luxryCars = bugattiVeyron;
	}

	@Override
	public double getSpeed() {
		return convertMPHtoKMPH(luxryCars.getSpeed());
	}

	private double convertMPHtoKMPH(double mph) {
		return mph * 1.60934;
	}

}
