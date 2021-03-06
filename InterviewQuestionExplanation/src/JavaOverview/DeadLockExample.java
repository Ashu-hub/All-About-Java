package JavaOverview;

class A1 extends Thread {

	@Override
	public void run() {
		synchronized (Object.class) {
			System.out.println("Accquired lock on Object class ");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			synchronized (String.class) {
				
				System.out.println("Accquired lock on String class");
			}
		}
		
	}
}

class B1 extends Thread {

	@Override
	public void run() {
		
		synchronized (String.class) {
			System.out.println("Accquired lock on String class");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			synchronized (Object.class) {
				
				System.out.println("Accquired lock on Object class");
			}
		}
		
	}
}

/** Copyright (c), AnkitMittal JavaMadeSoEasy.com */
public class DeadLockExample {

	public static void main(String[] args) {
		Thread thread1 = new Thread(new A1(), "Thread-1");
		Thread thread2 = new Thread(new B1(), "Thread-2");
		thread1.start();
		thread2.start();
	}

}